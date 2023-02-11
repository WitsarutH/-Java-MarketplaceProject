package ku.cs.shop.services;

import ku.cs.shop.models.OrderHistory;
import ku.cs.shop.models.OrderHistoryList;
import ku.cs.shop.models.Shop;

import java.io.*;

public class OrderHistoryDataSource implements DataSource<OrderHistoryList>{
    private OrderHistoryList orderHistoryList;

    public OrderHistoryDataSource(){
        orderHistoryList = new OrderHistoryList();
    }
    @Override
    public OrderHistoryList readData() {
        try {
            FileReader file = new FileReader("data"+ File.separator+"orderHistory.csv");
            BufferedReader buffer = new BufferedReader(file);
            String line = null;
            while ((line = buffer.readLine()) != null){
                String[] data = line.split(",");
                String customerUserName = data[0].trim();
                String imgName = data[1].trim();
                String fromShop = data[2].trim();
                String productName = data[3].trim();
                String price = data[4].trim();
                String amount = data[5].trim();
                String time = data[6];

                OrderHistory orderHistory = new OrderHistory(customerUserName,imgName,fromShop,productName,price,amount,time);
                orderHistoryList.addOrderHistory(orderHistory);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  orderHistoryList;
    }

    @Override
    public void writeData(OrderHistoryList orderHistoryList) throws IOException {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("data" + File.separator + "orderHistory.csv");
            BufferedWriter file = new BufferedWriter(fileWriter);

            file.write(orderHistoryList.toCsv());
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
