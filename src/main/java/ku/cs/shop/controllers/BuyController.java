package ku.cs.shop.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.shop.models.*;
import ku.cs.shop.services.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BuyController {
    private Product product;
    private Product getProduct;
    private DataSource<ProductList> productDataSource;
    private ProductList productList;
    private DataSource<MemberAccountList> memberAccountListDataSource;
    private MemberAccountList accountList;
    private MemberAccount account;
    private DataSource<OrderHistoryList> orderHistoryListDataSource;
    private OrderHistoryList orderHistoryList;
    private OrderHistory orderHistory;

    @FXML Label nameLabel;
    @FXML Label detailLabel;
    @FXML Label priceLabel;
    @FXML Button shopNameButton;
    @FXML Label amountLabel;
    @FXML ImageView productImage;
    @FXML Spinner<Integer> amountSpinner;
    @FXML Label totalPriceLabel;
    @FXML Label inputAmountLabel;
    @FXML Label totalLabel;
    @FXML Button buyButton;
    private int currentAmount;
    private String price;
    private DataBox dataBox;

    public void initialize() throws FileNotFoundException {
        dataBox = (DataBox) com.github.saacsos.FXRouter.getData();
        productDataSource = new ProductDataSource();
        productList = productDataSource.readData();
        product = productList.checkProductObject(dataBox.getProductName());

        memberAccountListDataSource = new MemberAccountDataSource();
        accountList = memberAccountListDataSource.readData();
        account = accountList.checkMemberAccount(dataBox.getUsername());

        orderHistoryListDataSource = new OrderHistoryDataSource();
        orderHistoryList = orderHistoryListDataSource.readData();


        totalPriceLabel.setText(product.getPrice() + " Baht");
        price = product.getPrice();
        setProductDetail();
        currentAmount = 1;
        detailLabel.setMaxWidth(620);
        detailLabel.setWrapText(true);
        if(account.getRole().equals("admin")){
            inputAmountLabel.setVisible(false);
            amountSpinner.setVisible(false);
            totalLabel.setVisible(false);
            totalPriceLabel.setVisible(false);
            buyButton.setVisible(false);
        }

        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,product.getAmount());
        valueFactory.setValue(1);
        amountSpinner.setValueFactory(valueFactory);
        amountSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                currentAmount = amountSpinner.getValue();
                //price = String.format("%.2f",product.getPriceInDouble() * currentAmount);
                price = String.format("%.2f", new OrderCalculator(product.getPriceInDouble(),currentAmount).MultiplyProduct());
                totalPriceLabel.setText(price + " Baht");
            }
        });
    }

    public void setProductDetail() throws FileNotFoundException {
        nameLabel.setText(product.getName());
        detailLabel.setText(product.getDetail());
        priceLabel.setText(product.getPrice() + " Baht");
        shopNameButton.setText(product.getFromShop());
        amountLabel.setText(product.getAmount() + " ชิ้น");

        Image image = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + product.getImage()));
        productImage.setImage(image);
    }
    public void handleBackButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleBuyButton(ActionEvent actionEvent) throws IOException {
        product.removeAmount(currentAmount);
        productDataSource.writeData(productList);

        orderHistory = new OrderHistory(account.getUsername(), product.getImage(), product.getFromShop(), product.getName(), price,currentAmount+"","time");
        orderHistory.setTime();
        orderHistoryList.addOrderHistoryToFirst(orderHistory);
        orderHistoryListDataSource.writeData(orderHistoryList);
        try {
            com.github.saacsos.FXRouter.goTo("home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleViewShopButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("productFromShop",dataBox);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
