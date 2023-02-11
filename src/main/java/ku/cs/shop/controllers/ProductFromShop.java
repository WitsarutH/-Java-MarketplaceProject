package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ProductDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class ProductFromShop {
    private DataBox dataBox;
    private MemberAccount account;
    private DataSource<MemberAccountList> memberAccountListDataSource;
    private MemberAccountList accountList;
    private DataSource<ProductList> productListDataSource;
    private ProductList productList;
    private ProductList productListFromShop;

    private ArrayList<Product> products;
    private ArrayList<Product> sortedProducts;
    private ArrayList<Product> sortedProductsByTime;

    @FXML private Label nameLabel;
    @FXML private Label shopNameLabel;
    @FXML private ImageView profileImage;
    @FXML private GridPane grid;
    @FXML private ChoiceBox<String> choiceBox;



    public void initialize() throws FileNotFoundException {
        dataBox = (DataBox) com.github.saacsos.FXRouter.getData();
        memberAccountListDataSource = new MemberAccountDataSource();
        accountList = memberAccountListDataSource.readData();
        account = accountList.checkMemberAccount(dataBox.getUsername());
        nameLabel.setText(account.getName());
        shopNameLabel.setText(dataBox.getShopName());

        productListDataSource = new ProductDataSource();
        productList = productListDataSource.readData();
        productListFromShop = productList.getProductListFromShop(dataBox.getShopName());
        products = productListFromShop.getAllProducts();
        sortedProductsByTime = new ArrayList<>(products);
        sortedProducts = new ArrayList<>(products);

        setProfile();
        setProductOnMarketplace(sortedProductsByTime);
        setChoiceBox();
    }

    public void setProfile() throws FileNotFoundException {
        Image profilePic = new Image(new FileInputStream("data" + File.separator + "profiles" + File.separator + account.getProfilePic()));
        profileImage.setImage(profilePic);
    }
    public void setProductOnMarketplace(ArrayList<Product> list){
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < list.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                ItemController itemController = fxmlLoader.getController();
                itemController.setData(list.get(i),account.getUsername());

                if (column == 2) {
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
    public void clearGridPane(){
        grid.getChildren().clear();
    }

    public void setChoiceBox(){
        choiceBox.getItems().addAll("สินค้าล่าสุด","ราคาน้อย -> มาก","ราคามาก -> น้อย");
        choiceBox.setValue("สินค้าล่าสุด");
        choiceBox.setOnAction(this::getChoice);
    }

    public void getChoice(ActionEvent actionEvent){
        String choice = choiceBox.getValue();
        switch (choice) {
            case "สินค้าล่าสุด":
                clearGridPane();
                setProductOnMarketplace(sortedProductsByTime);
                break;
            case "ราคาน้อย -> มาก":
                clearGridPane();
                sortedProducts = productListFromShop.sortLowToHighPrice();
                setProductOnMarketplace(sortedProducts);
                break;
            case "ราคามาก -> น้อย":
                clearGridPane();
                sortedProducts = productListFromShop.sortHighToLowPrice();
                setProductOnMarketplace(sortedProducts);
                break;
        }
    }

    public void handleHomeButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("home",account);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
