package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.models.OrderHistory;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SellOrderController {
    private MemberAccount account;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;

    @FXML Label customerNameLabel;
    @FXML Label timeLabel;
    @FXML Label productNameLabel;
    @FXML Label amountLabel;
    @FXML Label priceLabel;
    @FXML ImageView imageView;

    public void initialize(){
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
    }
    public void setData(OrderHistory orderHistory) throws FileNotFoundException {
        account = accountList.checkMemberAccount(orderHistory.getCustomerUserName());
        customerNameLabel.setText(account.getName());
        timeLabel.setText(orderHistory.getTime());
        productNameLabel.setText(orderHistory.getProductName());
        productNameLabel.setTextAlignment(TextAlignment.CENTER);
        productNameLabel.setWrapText(true);
        amountLabel.setText(orderHistory.getAmount() + " ชิ้น");
        priceLabel.setText(orderHistory.getPrice() + " Baht");
        Image image = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + orderHistory.getImgName()));
        imageView.setImage(image);
    }
}
