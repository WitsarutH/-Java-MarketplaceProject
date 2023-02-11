package ku.cs.shop.services;

import ku.cs.shop.models.Shop;
import ku.cs.shop.models.ShopList;

import java.io.*;

public class ShopDataSource implements DataSource<ShopList>{
    private ShopList shopList;

    public ShopDataSource(){
        shopList = new ShopList();
    }


    public ShopList readData() {
        try{
            FileReader file = new FileReader("data"+ File.separator+"shops.csv");
            BufferedReader buffer = new BufferedReader(file);
            String line = null;
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String nameOfShop = data[0].trim();
                String owner = data[1].trim();
                int alertOnLowGoods = Integer.parseInt(data[2].trim());

                Shop shop = new Shop(nameOfShop,owner,alertOnLowGoods);
                shopList.addShop(shop);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shopList;
    }

    public void writeData(ShopList shopList) throws IOException {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("data" + File.separator + "shops.csv");
            BufferedWriter file = new BufferedWriter(fileWriter);

            file.write(shopList.toCsv());
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
