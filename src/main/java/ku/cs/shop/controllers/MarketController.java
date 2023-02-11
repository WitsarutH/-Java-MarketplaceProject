package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
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

public class MarketController {
    @FXML Label nameLabel;
    @FXML Label price;
    @FXML ImageView imgSrc;
    @FXML Label amountLabel;
    @FXML Button addAmountButton;
    @FXML TextField addAmountTextField;
    @FXML Button cancelButton;
    @FXML Button confirmButton;
    @FXML Label alertLabel;
    @FXML Label guideLabel;
    @FXML Button editProductButton;


    private DataSource<ProductList> productDataSource;
    private ProductList productList;
    private Product getProduct;
    private Product product;
    private Shop shop;
    private ShopList shopList;
    private DataSource<ShopList> shopDataSource;
    private String username;


    public void initialize(){
        productDataSource = new ProductDataSource();
        productList = productDataSource.readData();
        shopDataSource = new ShopDataSource();
        shopList = shopDataSource.readData();


        addAmountTextField.setVisible(false);
        cancelButton.setVisible(false);
        confirmButton.setVisible(false);
        alertLabel.setVisible(false);
        guideLabel.setVisible(false);
    }
    public void setData(Product getProduct,String username) throws FileNotFoundException {
        this.username = username;
        this.getProduct = getProduct;
        nameLabel.setText(getProduct.getName());
        nameLabel.setMaxWidth(125);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setWrapText(true);
        amountLabel.setText(getProduct.getAmount() + " ชิ้น");
        amountLabel.setTextFill(Paint.valueOf("#000000"));
        price.setText(getProduct.getPrice() + App.CURRENCY);
        price.setTextFill(Paint.valueOf("#36ab16"));
        Image profilePic = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + getProduct.getImage()));
        imgSrc.setImage(profilePic);
    }

    public void setDataOnLowAmount(Product getProduct,String username) throws FileNotFoundException {
        this.username = username;
        this.getProduct = getProduct;
        nameLabel.setText(getProduct.getName());
        nameLabel.setMaxWidth(125);
        nameLabel.setTextAlignment(TextAlignment.CENTER);
        nameLabel.setWrapText(true);
        amountLabel.setText(getProduct.getAmount() + " ชิ้น");
        amountLabel.setTextFill(Paint.valueOf("#f50606"));
        price.setText(getProduct.getPrice() + App.CURRENCY);
        price.setTextFill(Paint.valueOf("#36ab16"));
        Image profilePic = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + getProduct.getImage()));
        imgSrc.setImage(profilePic);
    }

    public void handleAddProductAmount(ActionEvent actionEvent){
        addAmountTextField.setVisible(true);
        cancelButton.setVisible(true);
        confirmButton.setVisible(true);
        addAmountButton.setVisible(false);
        guideLabel.setVisible(true);
        editProductButton.setVisible(false);
    }
    public void handleCancelButton(ActionEvent actionEvent){
        addAmountTextField.setVisible(false);
        cancelButton.setVisible(false);
        confirmButton.setVisible(false);
        alertLabel.setVisible(false);
        addAmountButton.setVisible(true);
        guideLabel.setVisible(false);
        editProductButton.setVisible(true);
        addAmountTextField.clear();
    }
    public void handleEditProductButton(ActionEvent actionEvent){
        product = productList.checkProductObject(nameLabel.getText());
        try {
            com.github.saacsos.FXRouter.goTo("editProduct",product);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า editProduct ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleConfirmButton(ActionEvent actionEvent) throws IOException {
        String amount = addAmountTextField.getText().trim();
        product = productList.checkProductObject(nameLabel.getText());
        shop = shopList.checkShopName(product.getFromShop());

        if (amount.matches("\\d+")) {
            if (Integer.parseInt(amount) > 0) {
                product.addAmount(Integer.parseInt(amount));
                productDataSource.writeData(productList);
                addAmountTextField.setVisible(false);
                cancelButton.setVisible(false);
                confirmButton.setVisible(false);
                alertLabel.setVisible(false);
                addAmountButton.setVisible(true);
                editProductButton.setVisible(true);
                guideLabel.setVisible(false);
                addAmountTextField.clear();
                if(product.getAmount()>shop.getAlertOnLowGoods()) {
                    amountLabel.setText(product.getAmount() + " ชิ้น");
                    amountLabel.setTextFill(Paint.valueOf("#000000"));
                }else{
                    amountLabel.setText(product.getAmount() + " ชิ้น");
                    amountLabel.setTextFill(Paint.valueOf("#f50606"));
                }
            } else {
                alertLabel.setVisible(true);
                addAmountTextField.clear();
                alertLabel.setText("กรุณาใส่จำนวนเต็มบวก");
            }
        } else {
            alertLabel.setVisible(true);
            addAmountTextField.clear();
            alertLabel.setText("กรุณาใส่จำนวนเต็มบวก");
        }
    }
}
