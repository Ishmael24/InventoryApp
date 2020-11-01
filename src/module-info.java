module InventoryApp {
    requires javafx.fxml;
    requires javafx.controls;

    opens app;
    opens app.controller to javafx.fxml;
    opens app.models to javafx.base;
}