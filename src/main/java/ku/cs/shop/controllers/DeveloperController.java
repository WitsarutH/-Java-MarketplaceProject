package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import com.github.saacsos.FXRouter;

import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeveloperController {

    @FXML private ImageView lucky;
    @FXML private ImageView levis;
    @FXML private ImageView pure;

    public void initialize() {

        String path = getClass().getResource("/ku/cs/image/lucky.jpg").toExternalForm();
        lucky.setImage(new Image(path));
        String path2 = getClass().getResource("/ku/cs/image/levis.jpg").toExternalForm();
        levis.setImage(new Image(path2));
        String path3 = getClass().getResource("/ku/cs/image/pure.jpg").toExternalForm();
        pure.setImage(new Image(path3));
    }

    @FXML
    public void HandleWelcomeButton(ActionEvent actionEvent){
        try {
            FXRouter.goTo("welcome");
        } catch (IOException e){
            System.err.println("ไปหน้า welcome ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
