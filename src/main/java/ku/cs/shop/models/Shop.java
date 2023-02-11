package ku.cs.shop.models;

public class Shop {
    private String nameOfShop;
    private String owner;
    private int alertOnLowGoods;

    public Shop(String name, String owner, int alertLowGoods){
        this.nameOfShop = name;
        this.owner = owner;
        this.alertOnLowGoods = alertLowGoods;
    }

    public void setNameOfShop(String name){
        nameOfShop = name;
    }
    public boolean checkShopName(String shopName) { return this.nameOfShop.equals(shopName); }

    public void setAlertOnLowGoods(int num){ alertOnLowGoods = num; }

    public String getNameOfShop() {
        return nameOfShop;
    }

    public String getOwner() {
        return owner;
    }

    public int getAlertOnLowGoods() {
        return alertOnLowGoods;
    }

    public String toCsv(){
        return nameOfShop + "," + owner + "," + alertOnLowGoods ;
    }
}
