package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ShopDataSource;

import java.io.FileWriter;
import java.io.IOException;

public class SetLowProductController {
    @FXML private TextField setLowProductTextField;
    @FXML private Label alertLabel;
    @FXML private Label amountLabel;


    private MemberAccount memberAccount;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount account;

    private Shop shop;
    private ShopList shopList;
    private DataSource<ShopList> shopDataSource;


    public void initialize()  {
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();

        account = accountList.checkMemberAccount(memberAccount.getUsername());

        shopDataSource = new ShopDataSource();
        shopList = shopDataSource.readData();

        shop = shopList.checkShopName(account.getShopName());
        alertLabel.setVisible(false);
        amountLabel.setText("( จำนวนปัจจุบัน = " + shop.getAlertOnLowGoods() + " ชิ้น )");

    }

    public void handleSetLowProductButton(ActionEvent actionEvent) throws IOException {
        String setLow = setLowProductTextField.getText().trim();
        if (setLow.trim().isEmpty()) {
            alertLabel.setText("กรุณากรอกข้อมูล");
            alertLabel.setTextFill(Paint.valueOf("#ee0707"));
            alertLabel.setVisible(true);
        }else if(!setLow.matches("\\d+") || Integer.parseInt(setLow)<=0) {
            alertLabel.setText("กรุณากรอกจำนวนเต็มบวก");
            alertLabel.setTextFill(Paint.valueOf("#ee0707"));
            alertLabel.setVisible(true);
        }else{
            shop.setAlertOnLowGoods(Integer.parseInt(setLow));
            shopDataSource.writeData(shopList);
            alertLabel.setText("เปลี่ยนจำนวนเรียบร้อย");
            alertLabel.setTextFill(Paint.valueOf("#11ab51"));
            alertLabel.setVisible(true);
            amountLabel.setText("( จำนวนปัจจุบัน = " + shop.getAlertOnLowGoods() + " ชิ้น )");
        }
    }


    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("shop",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
