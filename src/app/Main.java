package app;

import app.models.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/MainMenu.fxml"));
        Scene home = new Scene(root);

        primaryStage.setScene(home);
        primaryStage.show();
    }


    public static void main(String[] args) {
        ObservableList<Part> associatedParts = FXCollections.observableArrayList();

        //Initial Data
        InHouse part1 = new InHouse(1, "Hard Drive - IH", 112.00, 6, 10, 0, 19);
        Outsourced part2 = new Outsourced(2, "LCD Display - OS", 89.00, 7, 0, 10, "Tech Time");
        Outsourced part3 = new Outsourced(3, "1 GB RAM - OS", 299.00, 2, 0, 10, "TeleTech");



        Product product1 = new Product(1, "Desktop", 400.00, 5, 0, 10);

        Product product2 = new Product(2, "Laptop", 800.00, 10, 0, 10);



        Inventory.addPart(part1);
        Inventory.addPart(part2);
        Inventory.addPart(part3);
        Inventory.addProduct(product1);
        Inventory.addProduct(product2);
        launch(args);
    }
}
