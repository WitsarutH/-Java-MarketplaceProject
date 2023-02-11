package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ku.cs.shop.models.*;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.OrderHistoryDataSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OrderHistoryController {
    private MemberAccount memberAccount;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount account;

    private DataSource<OrderHistoryList> orderHistoryListDataSource;
    private OrderHistoryList orderHistoryList;
    private OrderHistoryList orderHistoryListByThisUser;

    @FXML
    private GridPane grid;
    @FXML
    private Label nameLabel;
    @FXML
    private ImageView profileImage;
    @FXML private  Label noHistoryAlertLabel;

    public void initialize() throws FileNotFoundException {
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();

        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        account = accountList.checkMemberAccount(memberAccount.getUsername());

        orderHistoryListDataSource = new OrderHistoryDataSource();
        orderHistoryList = orderHistoryListDataSource.readData();
        noHistoryAlertLabel.setVisible(false);
        nameLabel.setText(memberAccount.getName());
        Image image = new Image(new FileInputStream("data" + File.separator + "profiles" + File.separator + memberAccount.getProfilePic()));
        profileImage.setImage(image);

        orderHistoryListByThisUser = orderHistoryList.getOrderHistoryListByUsername(memberAccount.getUsername());

        if (orderHistoryListByThisUser.count() < 1){
            noHistoryAlertLabel.setVisible(true);
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < orderHistoryListByThisUser.count(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/order.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                OrderController orderController = fxmlLoader.getController();

                orderController.setData(orderHistoryListByThisUser.getAllOrderHistory().get(i));


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
    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("home",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
