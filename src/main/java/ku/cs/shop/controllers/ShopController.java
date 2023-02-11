package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ProductDataSource;
import ku.cs.shop.services.ShopDataSource;

import java.io.IOException;

public class ShopController {

    private MemberAccount memberAccount;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount account;
    private DataSource<ProductList> productDataSource;
    private ProductList productList;
    private ProductList productFromShop;
    private Shop shop;
    private ShopList shopList;
    private DataSource<ShopList> shopDataSource;

    @FXML
    private Label shopNameLabel;
    @FXML
    private GridPane grid;

    public void initialize() {
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();

        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();

        account = accountList.checkMemberAccount(memberAccount.getUsername());
        shopNameLabel.setText(account.getShopName());

        productDataSource = new ProductDataSource();
        productList = productDataSource.readData();

        shopDataSource = new ShopDataSource();
        shopList = shopDataSource.readData();
        shop = shopList.checkShopName(account.getShopName());

        productFromShop = productList.getProductListFromShop(account.getShopName());

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < productFromShop.count(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/market.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                MarketController marketController = fxmlLoader.getController();

                if(productFromShop.getAllProducts().get(i).getAmount() <= shop.getAlertOnLowGoods()){
                    marketController.setDataOnLowAmount(productFromShop.getAllProducts().get(i), account.getUsername());
                }else{
                    marketController.setData(productFromShop.getAllProducts().get(i), account.getUsername());
                }

                if (column == 1) {
                    column = 0;
                    row++;
                }
                //ปรับขนาด
                grid.add(anchorPane, column++, row);
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);
                GridPane.setMargin(anchorPane, new Insets(10));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleHomeButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("home",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleSellHistory(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("sellOrderHistory",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า sellOrderHistory ไม่ได้");
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
    public void handleAddProductButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("addProduct",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า addProduct ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleLowProductButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("setLowProduct",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า setLowProduct ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}