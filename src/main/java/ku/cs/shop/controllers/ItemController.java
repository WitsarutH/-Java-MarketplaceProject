package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import ku.cs.App;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ProductDataSource;
import ku.cs.shop.services.ShopDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label price;
    @FXML
    private ImageView imgSrc;
    @FXML
    private Label amountLabel;
    @FXML Button buyButton;

    private Product product;
    private String username;
    private DataSource<ProductList> productDataSource;
    private ProductList productList;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount memberAccount;
    private DataBox dataBox;

    public void initialize() {
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        productDataSource = new ProductDataSource();
        productList = productDataSource.readData();
    }

    public void setData(Product product,String username) throws FileNotFoundException {
        this.product = product;
        nameLabel.setText(product.getName());
        if(product.getAmount() > 0) {
            amountLabel.setText(product.getAmount() + " ชิ้น");
            amountLabel.setTextFill(Paint.valueOf("#000000"));
            buyButton.setVisible(true);
        }else{
            amountLabel.setText("สินค้าหมด");
            amountLabel.setTextFill(Paint.valueOf("#f50606"));
            buyButton.setVisible(false);
        }
        price.setText(product.getPrice() + App.CURRENCY);
        price.setTextFill(Paint.valueOf("#36ab16"));
        Image profilePic = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + product.getImage()));
        imgSrc.setImage(profilePic);
        this.username = username;
        memberAccount = accountList.checkMemberAccount(username);
        if(memberAccount.getRole().equals("admin")){
            buyButton.setText("รายละเอียด");
        }
        this.dataBox = new DataBox(username,product.getFromShop(),product.getName());
    }

    public void handleBuyButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("buy",dataBox);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า buy ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
