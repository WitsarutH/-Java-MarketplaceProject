package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.models.MemberAccountList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoginController {
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount memberAccount;


    @FXML private TextField usernameTextField;
    @FXML private PasswordField passwordField;
    @FXML private Label loginAlertTextLabel;

    public void initialize() {
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        loginAlertTextLabel.setVisible(false);
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            loginAlertTextLabel.setVisible(true);
            loginAlertTextLabel.setText("กรุณากรอกข้อมูลให้ครบถ้วน");
        } else {
            if (accountList.checkLogin(username, password)) {
                try {
                    memberAccount = accountList.checkMemberAccount(username);
                    com.github.saacsos.FXRouter.goTo("home", memberAccount);
                    //เขียนข้อมูล เวลาการ login ลงใน csv
                    memberAccount.setTime();
                    accountList.removeMemberAccount(memberAccount);
                    accountList.addToFront(memberAccount);
                    dataSource.writeData(accountList);

                } catch (IOException e) {
                    System.err.println("ไปที่หน้า home ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            } else {
                loginAlertTextLabel.setVisible(true);
                loginAlertTextLabel.setText("Username หรือ Password ไม่ถูกต้อง");
                usernameTextField.clear();
                passwordField.clear();
            }
        }

    }
    public void handleWelcomeButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("welcome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
