package ku.cs.shop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.TextAlignment;
import ku.cs.shop.models.OrderHistory;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class OrderController {
    @FXML Label timeLabel;
    @FXML Label productNameLabel;
    @FXML Label amountLabel;
    @FXML Label priceLabel;
    @FXML ImageView imageView;


    public void setData(OrderHistory orderHistory) throws FileNotFoundException {
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

