package ku.cs.shop.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

public class Product {
    private String name;
    private String detail;
    private double price;
    private int amount;
    private String image;
    private String fromShop;

    public Product(String name, String detail, double price, int amount, String image, String fromShop) {
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.amount = amount;
        this.image = image;
        this.fromShop = fromShop;
    }

    public void setTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyy - kk:mm:ss");
    }
    public void changeData(String name,String detail,double price,String image){
        this.name = name;
        this.detail = detail;
        this.price = price;
        this.image = image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public boolean checkProductName(String name){
        return this.name.equals(name);
    }
    public boolean checkFromShopName(String shop){
        return this.name.equals(shop);
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        String Price = String.format("%.2f",price);
        return Price;
    }
    public double getPriceInDouble(){
        return price;
    }

    public String getDetail(){
        return detail;
    }
    public String getImage() {
        return image;
    }

    public String getFromShop() { return fromShop; }

    public void addAmount(int amount){
        this.amount += amount;
    }

    public void removeAmount(int amount){
        this.amount -= amount;
    }

    public static Comparator<Product> lowToHighPriceComparator = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            if(p1.getPriceInDouble() > p2.getPriceInDouble()) return 1;
            if(p1.getPriceInDouble() < p2.getPriceInDouble()) return -1;
            return 0;
        }
    };

    public static Comparator<Product> HighToLowPriceComparator = new Comparator<Product>() {
        @Override
        public int compare(Product p1, Product p2) {
            if(p1.getPriceInDouble() < p2.getPriceInDouble()) return 1;
            if(p1.getPriceInDouble() > p2.getPriceInDouble()) return -1;
            return 0;
        }
    };

    public String toCsv(){
        return name + "," + detail + "," + price + "," + amount + "," + image + "," + fromShop ;
    }
}
