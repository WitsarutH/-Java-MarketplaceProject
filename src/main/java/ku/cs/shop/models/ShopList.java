package ku.cs.shop.models;

import java.util.ArrayList;

public class ShopList {
    private ArrayList<Shop> shops;

    public ShopList(){
        shops = new ArrayList<>();
    }

    public void addShop(Shop shop) {
        // เรียก method add จาก ArrayList เพื่อเพิ่มข้อมูล
        shops.add(shop);
    }
    public Shop checkShopName(String shopName){
        for (Shop shop : shops) {
            if (shop.checkShopName(shopName)) {
                return shop;
            }
        }
        return null;
    }

    public boolean checkShopNameEqual(String shopName){
        for (Shop shop : shops) {
            if (shop.checkShopName(shopName)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<Shop> getAllShops(){
        return shops;
    }

    public String toCsv(){
        String result = "";
        for(Shop shop : this.shops){
            result += shop.toCsv() + "\n";
        }
        return result;
    }
}
