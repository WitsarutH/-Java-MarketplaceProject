package ku.cs.shop.services;

import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.models.ShopList;

import java.io.*;
import java.text.DecimalFormat;

public class ProductDataSource implements DataSource<ProductList>{
    private ProductList productList;

    public ProductDataSource(){
        productList = new ProductList();
    }

    @Override
    public ProductList readData() {
        try{
            FileReader file = new FileReader("data"+ File.separator+ "products.csv");
            BufferedReader buffer = new BufferedReader(file);
            String line = null;
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String name = data[0].trim();
                String detail = data[1].trim();
                double price = Double.parseDouble(data[2].trim());
                int amount = Integer.parseInt(data[3].trim());
                String image = data[4].trim();
                String fromShop = data[5].trim();

                Product product = new Product(name, detail, price, amount, image, fromShop);
                productList.addProduct(product);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public void writeData(ProductList productList) throws IOException {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("data" + File.separator + "products.csv");
            BufferedWriter file = new BufferedWriter(fileWriter);

            file.write(productList.toCsv());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileWriter != null)
                    fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
