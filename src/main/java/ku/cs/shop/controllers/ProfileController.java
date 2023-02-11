package ku.cs.shop.controllers;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ProfileController {

    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount memberAccount;
    private MemberAccount account;

    @FXML Label nameLabel;
    @FXML Label usernameLabel;
    @FXML Label shopNameLabel;
    @FXML Label roleLabel;
    @FXML Label alertLabel;
    @FXML Button changePasswordBtn;
    @FXML PasswordField newPassword;
    @FXML PasswordField confirmNewPassword;
    @FXML Button cancelBtn;
    @FXML Button confirmChangePasswordBtn;
    @FXML ImageView profileImage;
    @FXML Button resetToDefaultBtn;
    final FileChooser fileChooser = new FileChooser();

    public void initialize() throws IOException {
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();
        account = accountList.checkMemberAccount(memberAccount.getUsername());

        nameLabel.setText(memberAccount.getName());
        usernameLabel.setText(memberAccount.getUsername());

        setMemberDetail();
        setProfileImage();

        newPassword.setVisible(false);
        confirmNewPassword.setVisible(false);
        cancelBtn.setVisible(false);
        confirmChangePasswordBtn.setVisible(false);
        alertLabel.setVisible(false);

    }
    public void setMemberDetail(){
        if(account.getShopName().equals("noShop")){shopNameLabel.setText("ไม่มี");}
        else {shopNameLabel.setText(account.getShopName());}

        if(account.getRole().equals("admin")){
            roleLabel.setText("ผู้ดูแลระบบ");
        }
        else if(account.getRole().equals("user")){
            roleLabel.setText("ผู้ใช้งาน");}
        else{
            roleLabel.setText("ผู้ขายสินค้า");
        }
    }

    public void setProfileImage() throws FileNotFoundException {
        Image profilePic = new Image(new FileInputStream("data" + File.separator + "profiles" + File.separator + account.getProfilePic()));
        profileImage.setImage(profilePic);
        if(account.getProfilePic().equals("default.jpg")){
            resetToDefaultBtn.setVisible(false);
        }else{
            resetToDefaultBtn.setVisible(true);
        }
    }

    public void handleInputImageButton(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Upload your profile image");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null){
            //เขียนไฟล์ และ ถ้าเป็นไฟล์ png จะเปลี่ยนเป็น jpg
            BufferedImage image = ImageIO.read(file);
            BufferedImage newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
            newImage.createGraphics().drawImage(image,0,0, Color.WHITE,null);
            File profilePicture = new File("data" + File.separator + "profiles" + File.separator + memberAccount.getUsername() + ".jpg");
            ImageIO.write(newImage,"jpg",profilePicture);

            account.setProfilePic();
            dataSource.writeData(accountList);
            setProfileImage();

        }else{
            System.out.println("A File is invalid");
        }
    }
    public void handleDefaultImage(ActionEvent actionEvent) throws IOException {
        account.resetProfilePic();
        dataSource.writeData(accountList);
        setProfileImage();
    }
    public void handleLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("welcome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleHomeButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("home",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleChangePasswordMenu(ActionEvent actionEvent) {
        changePasswordBtn.setVisible(false);
        newPassword.setVisible(true);
        confirmNewPassword.setVisible(true);
        cancelBtn.setVisible(true);
        confirmChangePasswordBtn.setVisible(true);
        newPassword.clear();
        confirmNewPassword.clear();
        alertLabel.setTextFill(Paint.valueOf("#f24141"));
        alertLabel.setVisible(false);
    }
    public void handleCancelButton(ActionEvent actionEvent) {
        changePasswordBtn.setVisible(true);
        newPassword.setVisible(false);
        confirmNewPassword.setVisible(false);
        cancelBtn.setVisible(false);
        confirmChangePasswordBtn.setVisible(false);
        alertLabel.setVisible(false);

    }
    public void handleChangePasswordButton(ActionEvent actionEvent) throws IOException {
        String password = newPassword.getText().trim();
        String confirmPassword = confirmNewPassword.getText().trim();

        if(password.isEmpty() || confirmPassword.isEmpty()){
            alertLabel.setText("กรุณากรอกข้อมูลให้ครบถ้วน");
            alertLabel.setVisible(true);
        }else{
            if(password.equals(confirmPassword)){

                account.changePassword(password);
                dataSource.writeData(accountList);
                alertLabel.setText("เปลี่ยนรหัสผ่านเรียบร้อย");
                alertLabel.setTextFill(Paint.valueOf("#36ab16"));
                alertLabel.setVisible(true);
                changePasswordBtn.setVisible(true);
                newPassword.setVisible(false);
                confirmNewPassword.setVisible(false);
                cancelBtn.setVisible(false);
                confirmChangePasswordBtn.setVisible(false);
            }else{
                alertLabel.setText("ยืนยันรหัสผ่านไม่ถูกต้อง");
                alertLabel.setVisible(true);
            }
        }
    }
}
