package ku.cs.shop.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController {
    @FXML
    public void handleDeveloperButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("developer");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า profile ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleLoginButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleRegisterButton(ActionEvent actionEvent) {
        try {
            com.github.saacsos.FXRouter.goTo("register");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า register ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    public void handleInstructionButton(ActionEvent actionEvent){
        try {
            com.github.saacsos.FXRouter.goTo("instruction");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า instruction ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}