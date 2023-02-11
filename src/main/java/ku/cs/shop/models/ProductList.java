package ku.cs.shop.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ProductList {
    private ArrayList<Product> products;
    private ArrayList<Product> sortedProducts;

    public ProductList(){ products = new ArrayList<>();}

    public void addProduct(Product product) {
        products.add(product);
    }
    public void addProductToFirst(Product product) {
        products.add(0,product);
    }

    public int count(){
        return this.products.size();
    }

    public ArrayList<Product> getAllProducts(){
        return products;
    }

    public String toCsv(){
        String result = "";
        for(Product product : this.products){
            result += product.toCsv() + "\n";
        }
        return result;
    }
    public boolean checkProductNameEqual(String productName){
        for (Product product : products) {
            if (product.checkProductName(productName)) {
                return false;
            }
        }
        return true;
    }
    public Product checkProductObject(String name) {
        for (Product product : products) {
            if (product.checkProductName(name)) {
                return product;
            }
        }
        return null;
    }

    public ProductList getProductListFromShop(String shop){
        ProductList productList1 = new ProductList();
        for(Product product : products){
            if(product.getFromShop().equals(shop)){
                productList1.addProduct(product);
            }
        }
        return productList1;
    }

    public ArrayList<Product> sortLowToHighPrice(){
        Collections.sort(products,Product.lowToHighPriceComparator);
        return products;
    }

    public ArrayList<Product> sortHighToLowPrice(){
        Collections.sort(products,Product.HighToLowPriceComparator);
        return products;
    }
}
