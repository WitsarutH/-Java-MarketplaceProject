package ku.cs.shop.controllers;

import com.github.saacsos.FXRouter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class InstructionController {

    @FXML private ImageView instruction;

    public void initialize() {
        String path = getClass().getResource("/ku/cs/image/instruction.jpg").toExternalForm();
        instruction.setImage(new Image(path));
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
