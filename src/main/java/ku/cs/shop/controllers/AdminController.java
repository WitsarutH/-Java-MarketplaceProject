package ku.cs.shop.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AdminController {
    private MemberAccount memberAccount;
    @FXML ListView<MemberAccount> memberListView;
    @FXML Label usernameLabel;
    @FXML Label nameLabel;
    @FXML Label shopLabel;
    @FXML Label roleLabel;
    @FXML Label timeLabel;
    @FXML ImageView profileImage;

    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;

    public void initialize() {
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        showListView();
        clearSelectedAccount();
        handleSelectedListView();
    }

    private void showListView() {
        memberListView.getItems().addAll(accountList.getAllAccounts());
        memberListView.refresh();
    }

    private void handleSelectedListView() {
        memberListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<MemberAccount>() {
                    @Override
                    public void changed(ObservableValue<? extends MemberAccount> observable,
                                        MemberAccount oldValue, MemberAccount newValue) {
                        try {
                            showSelectAccount(newValue);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void showSelectAccount(MemberAccount account) throws FileNotFoundException {
        usernameLabel.setText(account.getUsername());
        nameLabel.setText(account.getName());
        if(account.getShopName().equals("noShop")){
            shopLabel.setText("ไม่มีร้านค้า");
        }else{
            shopLabel.setText(account.getShopName());
        }
        if(account.getRole().equals("admin")){
            roleLabel.setText("ผู้ดูแลระบบ");
            roleLabel.setTextFill(Paint.valueOf("#f22b2b"));
        }else if(account.getRole().equals("seller")){
            roleLabel.setText("ผู้ขายสินค้า");
            roleLabel.setTextFill(Paint.valueOf("#27a5ff"));
        }else{
            roleLabel.setText("ผู้ใช้ระบบ");
            roleLabel.setTextFill(Paint.valueOf("#27a5ff"));
        }
        if(account.getTime().equals("time")){
            timeLabel.setTextFill(Paint.valueOf("#f22b2b"));
            timeLabel.setText("ยังไม่เคยเข้าสู่ระบบ");
        }else {
            timeLabel.setTextFill(Paint.valueOf("#27a5ff"));
            timeLabel.setText(account.getTime());
        }
        Image profilePic = new Image(new FileInputStream("data" + File.separator + "profiles" + File.separator + account.getProfilePic()));
        profileImage.setImage(profilePic);
    }

    private void clearSelectedAccount() {
        usernameLabel.setText("");
        nameLabel.setText("");
        shopLabel.setText("");
        roleLabel.setText("");
        timeLabel.setText("");
    }

    public void handleBackButton(){
        try {
            com.github.saacsos.FXRouter.goTo("home",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
