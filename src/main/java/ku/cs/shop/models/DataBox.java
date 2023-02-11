package ku.cs.shop.models;

public class DataBox {
    private String username;
    private String shopName;
    private String productName;

    public DataBox(String username, String shopName, String productName) {
        this.username = username;
        this.shopName = shopName;
        this.productName = productName;
    }

    public String getUsername() {
        return username;
    }

    public String getShopName() {
        return shopName;
    }

    public String getProductName() {
        return productName;
    }
}
