package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.models.MemberAccountList;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Label registerAlertTextLabel;

    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;

    public void initialize() {
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        registerAlertTextLabel.setVisible(false);
    }

    public void handleWelcomeButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("welcome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleRegisterButton(ActionEvent actionEvent) {
        String name = nameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        FileWriter fileWriter = null;
        if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            registerAlertTextLabel.setText("กรุณากรอกข้อมูลให้ครบถ้วน");
            registerAlertTextLabel.setVisible(true);
        }
        else {
            if (password.equals(confirmPassword)) {
                if(name.contains(",") || username.contains(",")){
                    registerAlertTextLabel.setText("กรุณาไม่ใช้ comma");
                    registerAlertTextLabel.setVisible(true);
                }
                else if (accountList.checkUsernameEqual(username)) {
                    try {
                        fileWriter = new FileWriter("data"+ File.separator+"accounts.csv", true);
                        BufferedWriter file = new BufferedWriter(fileWriter);
                        file.write(name + ",");
                        file.write(username + ",");
                        file.write(password + ",");
                        file.write("user" + "," );
                        file.write("noShop" + ",");
                        file.write("default.jpg" + ",");
                        file.write("time");
                        file.newLine();

                        file.flush();

                        com.github.saacsos.FXRouter.goTo("welcome");
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า welcome ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    } finally {
                        try {
                            if (fileWriter != null)
                                fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    registerAlertTextLabel.setText("มี Username นี้ในระบบแล้ว");
                    registerAlertTextLabel.setVisible(true);
                    usernameTextField.clear();
                    passwordField.clear();
                    confirmPasswordField.clear();
                }
            } else {
                registerAlertTextLabel.setText("ยืนยันรหัสผ่านไม่ถูกต้อง");
                registerAlertTextLabel.setVisible(true);
                passwordField.clear();
                confirmPasswordField.clear();
            }
        }
    }
}
