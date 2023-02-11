package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.models.OrderHistoryList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.OrderHistoryDataSource;

import java.io.IOException;

public class SellOrderHistoryController {
    private MemberAccount memberAccount;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount account;

    private DataSource<OrderHistoryList> orderHistoryListDataSource;
    private OrderHistoryList orderHistoryList;
    private OrderHistoryList orderHistoryListFromShop;

    @FXML
    private GridPane grid;
    @FXML
    private Label shopNameLabel;

    public void initialize(){
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();
        account = accountList.checkMemberAccount(memberAccount.getUsername());

        orderHistoryListDataSource = new OrderHistoryDataSource();
        orderHistoryList = orderHistoryListDataSource.readData();
        shopNameLabel.setText(memberAccount.getShopName());

        orderHistoryListFromShop = orderHistoryList.getOrderHistoryListFromShop(memberAccount.getShopName());

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < orderHistoryListFromShop.count(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/sellOrder.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();
                SellOrderController sellOrderController = fxmlLoader.getController();

                sellOrderController.setData(orderHistoryListFromShop.getAllOrderHistory().get(i));


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
            com.github.saacsos.FXRouter.goTo("shop",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
