package ku.cs.shop.services;

import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;

import java.io.*;
import java.io.FileWriter;
import java.lang.reflect.Member;

public class MemberAccountDataSource implements DataSource<MemberAccountList>{
    private MemberAccountList AccountList;

    public MemberAccountDataSource() {
        AccountList = new MemberAccountList();
    }

    public MemberAccountList readData() {
        try {
            FileReader file = new FileReader("data"+ File.separator+"accounts.csv");
            BufferedReader buffer = new BufferedReader(file);
            String line = null;
            while ((line = buffer.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0].trim();
                String username = data[1].trim();
                String password = data[2].trim();
                String role = data[3].trim();
                String shopName = data[4].trim();
                String profilePic = data[5].trim();
                String time = data[6].trim();

                MemberAccount account = new MemberAccount(name, username, password, role, shopName, profilePic,time);
                AccountList.addMemberAccount(account);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Cannot read file accounts.csv");
        } catch (IOException e) {
            System.err.println("Error reading from file");
        }
        return AccountList;
    }
    public void writeData(MemberAccountList accountList) throws IOException{
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter("data" + File.separator + "accounts.csv");
            BufferedWriter file = new BufferedWriter(fileWriter);

            file.write(accountList.toCsv());
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


