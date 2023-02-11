package ku.cs.shop.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderHistory {
    private String customerUserName;
    private String imgName;
    private String fromShop;
    private String productName;
    private String price;
    private String amount;
    private String time;

    public OrderHistory(String customerUserName, String imgName, String fromShop, String productName, String price, String amount, String time) {
        this.customerUserName = customerUserName;
        this.imgName = imgName;
        this.fromShop = fromShop;
        this.productName = productName;
        this.price = price;
        this.amount = amount;
        this.time = time;
    }
    public boolean checkCustomerUsername(String username){
        return this.customerUserName.equals(username);
    }

    public boolean checkShopName(String fromShop){
        return this.fromShop.equals(fromShop);
    }

    public void setTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyy - kk:mm:ss");
        this.time = now.format(formatter);
    }

    public String getFromShop() {
        return fromShop;
    }

    public String getCustomerUserName() {
        return customerUserName;
    }

    public String getImgName() {
        return imgName;
    }

    public String getProductName() {
        return productName;
    }

    public String getPrice() {
        return price;
    }

    public String getAmount() {
        return amount;
    }

    public String getTime() {
        return time;
    }

    public  String toCsv(){
        return customerUserName + "," + imgName + "," + fromShop + "," +  productName + "," + price + "," + amount + "," + time;
    }
}
