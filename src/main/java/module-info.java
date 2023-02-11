module ku.cs {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens ku.cs to javafx.fxml;
    exports ku.cs;
    exports ku.cs.shop.controllers;
    opens ku.cs.shop.controllers to javafx.fxml;
    exports ku.cs.shop.models;
    opens ku.cs.shop.models to javafx.fxml;
}
