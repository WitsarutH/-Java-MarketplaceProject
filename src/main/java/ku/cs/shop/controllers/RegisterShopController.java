package ku.cs.shop.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.models.Shop;
import ku.cs.shop.models.ShopList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ShopDataSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterShopController{
    @FXML private TextField shopNameTextField;
    @FXML private Label AlertLabel;


    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount memberAccount;
    private MemberAccount account;

    private DataSource<ShopList> shopDataSource;
    private ShopList shopList;
    private Shop shop;


    public void initialize()  {
        memberAccount = (MemberAccount) FXRouter.getData();
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        account = accountList.checkMemberAccount(memberAccount.getUsername());

        shopDataSource = new ShopDataSource();
        shopList = shopDataSource.readData();

        AlertLabel.setVisible(false);
    }


    public void handleShopButton(ActionEvent actionEvent){
        String shopName = shopNameTextField.getText();
        FileWriter fileWriter = null;
        if(shopName.trim().isEmpty()){
            AlertLabel.setText("กรุณากรอกข้อมูลให้ครบถ้วน");
            AlertLabel.setVisible(true);
        }
        else {
            if(shopName.contains(",")){
                AlertLabel.setText("กรุณาไม่ใช้ comma");
                AlertLabel.setVisible(true);
            }
            else if (shopList.checkShopNameEqual(shopName)) {
                try {
                    account.changeShopName(shopName);
                    account.setRoleToSeller();
                    dataSource.writeData(accountList);

                    fileWriter = new FileWriter("data"+ File.separator+"shops.csv", true);
                    BufferedWriter file = new BufferedWriter(fileWriter);
                    file.write(shopName + ",");
                    file.write(account.getUsername() + ",");
                    file.write("1");
                    file.newLine();
                    file.flush();

                    com.github.saacsos.FXRouter.goTo("shop",memberAccount);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า Shop ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }finally {
                    try {
                        if (fileWriter != null)
                            fileWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                AlertLabel.setText("มีร้านค้าชื่อนี้อยู่แล้ว");
                AlertLabel.setVisible(true);
                }
        }
    }

    public void handleHomeButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleLogoutButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("welcome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
