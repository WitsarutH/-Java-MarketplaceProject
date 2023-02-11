package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import com.github.saacsos.FXRouter;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;
    public static final String CURRENCY = " Baht";
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "LuckyPureLeviShop (2LP Shop)", 1000, 600);
        configRoute();
        FXRouter.goTo("welcome");
        Image image = new Image(getClass().getResourceAsStream("/ku/cs/image/icon_program.png"));
        stage.getIcons().add(image);
    }

    private static void configRoute() {
        String packageStr = "ku/cs/";
        FXRouter.when("home", packageStr+"home.fxml");
        FXRouter.when("developer", packageStr+"developer.fxml");
        FXRouter.when("login", packageStr+"login.fxml");
        FXRouter.when("register", packageStr+"register.fxml");
        FXRouter.when("welcome", packageStr+"welcome.fxml");
        FXRouter.when("profile", packageStr+"profile.fxml");
        FXRouter.when("admin", packageStr+"admin.fxml");
        FXRouter.when("registerShop", packageStr+"registerShop.fxml");
        FXRouter.when("shop", packageStr+"shop.fxml");
        FXRouter.when("addProduct", packageStr+"addProduct.fxml");
        FXRouter.when("setLowProduct", packageStr+"setLowProduct.fxml");
        FXRouter.when("buy", packageStr+"buy.fxml");
        FXRouter.when("productFromShop", packageStr+"productFromShop.fxml");
        FXRouter.when("instruction", packageStr+"instruction.fxml");
        FXRouter.when("orderHistory", packageStr+"orderHistory.fxml");
        FXRouter.when("sellOrderHistory", packageStr+"sellOrderHistory.fxml");
        FXRouter.when("editProduct", packageStr+"editProduct.fxml");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) { launch(); }

}