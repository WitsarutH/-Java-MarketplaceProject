package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.shop.models.Product;
import ku.cs.shop.models.ProductList;
import ku.cs.shop.services.DataSource;
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

public class EditProductController {
    private DataSource<ProductList> productListDataSource;
    private ProductList productList;
    private Product getProduct;
    private Product product;

    @FXML private ImageView productImage;
    @FXML private TextField productNameTextField;
    @FXML private TextArea productDetailTextArea;
    @FXML private TextField productPriceTextField;
    @FXML private Label alertLabel;

    final FileChooser fileChooser = new FileChooser();
    private File file;
    private File profilePicture;
    private BufferedImage newImage;
    private Boolean isImageUpload;

    public void initialize() throws FileNotFoundException {
        getProduct = (Product) com.github.saacsos.FXRouter.getData();
        productListDataSource = new ProductDataSource();
        productList = productListDataSource.readData();
        product = productList.checkProductObject(getProduct.getName());

        Image profilePic = new Image(new FileInputStream("data" + File.separator + "products" + File.separator + product.getImage()));
        productImage.setImage(profilePic);

        productNameTextField.setText(product.getName());
        productDetailTextArea.setText(product.getDetail());
        productPriceTextField.setText(product.getPrice());
        alertLabel.setVisible(false);
        isImageUpload = false;
    }
    public void handleChangeImage(ActionEvent actionEvent) throws IOException {
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
    public void handleEditProduct(ActionEvent actionEvent) throws IOException {
        String name = productNameTextField.getText().trim();
        String detail = productDetailTextArea.getText().replaceAll("\n"," ").trim();
        String price = productPriceTextField.getText().trim();

        if(name.isEmpty() || detail.isEmpty() || price.isEmpty()) {
            alertLabel.setText("กรุณาใส่ข้อมูลให้ครบถ้วน");
            alertLabel.setVisible(true);
        }else if( name.contains(",") || detail.contains(",")){
            alertLabel.setText("กรุณาไม่ใช้ comma");
            alertLabel.setVisible(true);
        }
        else {
            if(!price.matches("\\d+\\.\\d+|\\d+")){
                alertLabel.setText("ราคาไม่ถูกต้อง กรุณาลองใหม่");
                alertLabel.setVisible(true);
            }
            else if(price.matches("\\d+\\.\\d+|\\d+")){
                if(!isImageUpload){
                    product.changeData(name,detail,Double.parseDouble(price),product.getImage());
                    productListDataSource.writeData(productList);
                }
                else{
                    product.changeData(name,detail,Double.parseDouble(price),setTime() +".jpg");
                    productListDataSource.writeData(productList);
                    profilePicture = new File("data" + File.separator + "products" + File.separator + setTime() +".jpg");
                    ImageIO.write(newImage,"jpg",profilePicture);
                }
                try{
                    com.github.saacsos.FXRouter.goTo("shop");
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

    public void handleBackButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("shop");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า shop ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
    public String setTime(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dMMyyykkmmss");
        return now.format(formatter);
    }
}
