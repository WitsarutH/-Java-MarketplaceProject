package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import ku.cs.shop.models.MemberAccount;
import ku.cs.shop.models.MemberAccountList;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.DataSource;
import ku.cs.shop.services.MemberAccountDataSource;
import ku.cs.shop.services.ProductDataSource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddProductController {
    private MemberAccount memberAccount;
    private DataSource<MemberAccountList> dataSource;
    private MemberAccountList accountList;
    private MemberAccount account;
    private String shop;
    private Product product;
    private ProductList productList;
    private DataSource<ProductList> productDataSource;
    private File file;
    private File profilePicture;
    private BufferedImage newImage;
    private Boolean isImageUpload;

    @FXML ImageView productImage;
    @FXML TextField productNameTextField;
    @FXML TextArea productDetailTextArea;
    @FXML TextField productPriceTextField;
    @FXML TextField productAmountTextField;
    @FXML Label alertLabel;
    final FileChooser fileChooser = new FileChooser();

    public void initialize() throws FileNotFoundException {
        memberAccount = (MemberAccount) com.github.saacsos.FXRouter.getData();
        dataSource = new MemberAccountDataSource();
        accountList = dataSource.readData();

        productDataSource = new ProductDataSource();
        productList = productDataSource.readData();

        account = accountList.checkMemberAccount(memberAccount.getUsername());
        shop = account.getShopName();

        alertLabel.setVisible(false);
        setDefaultImage();
        isImageUpload = false;

    }

    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("shop",memberAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า Welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public void handleAddProductImageButton(ActionEvent actionEvent) throws IOException {
        fileChooser.setTitle("Upload your product image");
        fileChooser.getExtensionFilters().clear();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.jpeg"));
        file = fileChooser.showOpenDialog(null);

        if (file != null){
            //เขียนไฟล์ และ ถ้าเป็นไฟล์ png จะเปลี่ยนเป็น jpg
            BufferedImage image = ImageIO.read(file);
            newImage = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_RGB);
            newImage.createGraphics().drawImage(image,0,0, Color.WHITE,null);
            profilePicture = new File("data" + File.separator + "products" + File.separator + "Preview.jpg");
            ImageIO.write(newImage,"jpg",profilePicture);
            setProductImage();
            isImageUpload = true;

        }else{
            System.out.println("A File is invalid");
        }
    }
    public void handleAddProductButton(ActionEvent actionEvent) throws IOException {
        String name = productNameTextField.getText().trim();
        String detail = productDetailTextArea.getText().replaceAll("\n"," ").trim();
        String price = productPriceTextField.getText().trim();
        String amount = productAmountTextField.getText().trim();

        if(name.isEmpty() || detail.isEmpty() || price.isEmpty() || amount.isEmpty()) {
            alertLabel.setText("กรุณาใส่ข้อมูลให้ครบถ้วน");
            alertLabel.setVisible(true);
        }else if(!isImageUpload){
            alertLabel.setText("กรุณาใส่รูปภาพ");
            alertLabel.setVisible(true);
        }else if(name.contains(",") || detail.contains(",")){
            alertLabel.setText("กรุณาไม่ใช้ comma");
            alertLabel.setVisible(true);
        }
        else {
            if(price.matches("\\d+\\.\\d+|\\d+") && (!amount.matches("\\d+" ) || (Integer.parseInt(amount)<1))){
                alertLabel.setText("กรุณาใส่จำนวนสินค้าเป็นจำนวนเต็มบวก");
                alertLabel.setVisible(true);
            }else if(!price.matches("\\d+\\.\\d+|\\d+")){
                alertLabel.setText("ราคาไม่ถูกต้อง กรุณาลองใหม่");
                alertLabel.setVisible(true);
            }
            else if(price.matches("\\d+\\.\\d+|\\d+") && amount.matches("\\d+")) {
                product = new Product(name,detail,Double.parseDouble(price),Integer.parseInt(amount),setTime()+".jpg",shop);
                productList.addProductToFirst(product);
                productDataSource.writeData(productList);

                profilePicture = new File("data" + File.separator + "products" + File.separator + setTime() +".jpg");
                ImageIO.write(newImage,"jpg",profilePicture);
            try{
                com.github.saacsos.FXRouter.goTo("shop",memberAccount);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า shop ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
            }
        }
    }

    public void setProductImage() throws FileNotFoundException {
        Image productPic = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + "Preview.jpg"));
        productImage.setImage(productPic);
    }
    public void setDefaultImage() throws FileNotFoundException {
        Image defaultPic = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + "default.jpg"));
        productImage.setImage(defaultPic);
    }
    public String setTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dMMyyykkmmss");
        return now.format(formatter);
    }
}
