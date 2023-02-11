package ku.cs.shop.models;

import java.util.ArrayList;

public class OrderHistoryList {
    private ArrayList<OrderHistory> orderHistories;

    public OrderHistoryList(){
        orderHistories = new ArrayList<>();
    }

    public void addOrderHistory(OrderHistory orderHistory){
        orderHistories.add(orderHistory);
    }

    public void addOrderHistoryToFirst(OrderHistory orderHistory){
        orderHistories.add(0,orderHistory);
    }

    public int count(){
        return this.orderHistories.size();
    }

    public boolean checkCustomerUsernameEqual(String username){
        for (OrderHistory orderHistory : orderHistories) {
            if (orderHistory.checkCustomerUsername(username)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkShopNameEqual(String username){
        for (OrderHistory orderHistory : orderHistories) {
            if (orderHistory.checkShopName(username)) {
                return false;
            }
        }
        return true;
    }

    public OrderHistoryList getOrderHistoryListByUsername(String username){
        OrderHistoryList orderHistoryList = new OrderHistoryList();
        for(OrderHistory orderHistory : orderHistories){
            if(orderHistory.getCustomerUserName().equals(username)){
                orderHistoryList.addOrderHistory(orderHistory);
            }
        }
        return orderHistoryList;
    }
    public OrderHistoryList getOrderHistoryListFromShop(String shop){
        OrderHistoryList orderHistoryList = new OrderHistoryList();
        for(OrderHistory orderHistory : orderHistories){
            if(orderHistory.getFromShop().equals(shop)){
                orderHistoryList.addOrderHistory(orderHistory);
            }
        }
        return orderHistoryList;
    }

    public ArrayList<OrderHistory> getAllOrderHistory(){
        return orderHistories;
    }

    public String toCsv(){
        String result = "";
        for(OrderHistory orderHistory : this.orderHistories){
            result += orderHistory.toCsv() + "\n";
        }
        return result;
    }
}
