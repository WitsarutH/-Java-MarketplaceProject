package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ProductDataSource;

import java.io.*;
import java.net.URL;
import java.util.*;

public class HomeController {
    private MemberAccount memberAccount;
    private MemberAccount account;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private DataSource<ProductList> productDataSource;
    private ProductList productList;
    private ArrayList<Product> products;
    private ArrayList<Product> sortedProducts;
    private ArrayList<Product> sortedProductsByTime;

    @FXML private Button adminButton;
    @FXML private Button shopButton;
    @FXML private Label nameLabel;
    @FXML private ImageView profileImage;
    @FXML private ScrollPane scroll;
    @FXML private GridPane grid;
    @FXML private ChoiceBox<String> choiceBox;
    @FXML private Button historyButton;

    public void handleLogoutButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("welcome");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void initialize() throws FileNotFoundException {
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        memberAccount = (MemberAccount) FXRouter.getData();
        account = accountList.checkMemberAccount(memberAccount.getUsername());
        nameLabel.setText(memberAccount.getName());
        productDataSource = new ProductDataSource();
        productList = productDataSource.readData();
        products = productList.getAllProducts();
        sortedProductsByTime = new ArrayList<>(products);
        sortedProducts = new ArrayList<>(products);
        setProfile();
        checkRole();
        setChoiceBox();

        setProductOnMarketplace(products);
    }

    public void handleProfileButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("profile",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleAdminButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("admin",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleOrderHistoryButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("orderHistory",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า orderHistory ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleShopButton(ActionEvent actionEvent){
        if(account.getShopName().equals("noShop")){
            try {
                com.github.saacsos.FXRouter.goTo("registerShop",memberAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า registerShop ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }else{
            try {
                com.github.saacsos.FXRouter.goTo("shop",memberAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า shop ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
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
                itemController.setData(list.get(i),memberAccount.getUsername());

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

    public void checkRole(){
        if(!memberAccount.getRole().equals("admin")){
            adminButton.setVisible(false);
        }
        if(memberAccount.getRole().equals("admin")){
            historyButton.setVisible(false);
            shopButton.setVisible(false);
        }
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
                sortedProducts = productList.sortLowToHighPrice();
                setProductOnMarketplace(sortedProducts);
                break;
            case "ราคามาก -> น้อย":
                clearGridPane();
                sortedProducts = productList.sortHighToLowPrice();
                setProductOnMarketplace(sortedProducts);
                break;
        }
    }
}
