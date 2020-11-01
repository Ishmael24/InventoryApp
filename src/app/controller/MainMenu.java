package app.controller;

import app.models.Inventory;
import app.models.Part;
import app.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *The MainMenu class is the controller for the ManinMenu view.
 *
 * @author Ishmauel Martin
 */
public class MainMenu implements Initializable {

    Stage stage;
    Parent scene;

    @FXML private AnchorPane mainMenu;

    // Part search box
    @FXML private TextField partSearchBox;

    // Product search box
    @FXML private  TextField productSearchBox;

    // Configure Part table
    @FXML private TableView<Part> partTableView;
    @FXML private TableColumn<Part, Integer> partId;
    @FXML private TableColumn<Part, String> partName;
    @FXML private TableColumn<Part, Integer> partStock;
    @FXML private TableColumn<Part, Double> partPrice;

    // Configure Part table
    @FXML private TableView<Product> productTableView;
    @FXML private TableColumn<Product, Integer> productId;
    @FXML private TableColumn<Product, String> productName;
    @FXML private TableColumn<Product, Integer> productStock;
    @FXML private TableColumn<Product, Double> productPrice;

    public MainMenu(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView.setItems(Inventory.getAllParts());
        productTableView.setItems(Inventory.getAllProducts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }

    /**
     * This method search for a part by either the id or the name
     *
     * @param event
     */
    @FXML public void searchPart(ActionEvent event){
        String input = partSearchBox.getText();
        ObservableList<Part> results = FXCollections.observableArrayList();


        try{
            int id = Integer.parseInt(input);
            System.out.println("Is an Interger");
            Part result = Inventory.lookupPart(id);
            results.add(result);

        }
        catch (NumberFormatException e){
            System.out.println("Not an Integer");
            results = Inventory.lookupPart(input);

        }
        partTableView.setItems(results);
    }

    /**
     * This method search for a product by either id or name.
     *
     * @param event
     */
    @FXML public void searchProduct(ActionEvent event){
        String input = productSearchBox.getText();
        System.out.println(input);
        ObservableList<Product> results = FXCollections.observableArrayList();


        try{
            int id = Integer.parseInt(input);
            Product result = Inventory.lookupProduct(id);
            results.add(result);

        }
        catch (NumberFormatException e){
            results = Inventory.lookupProduct(input);

        }
        productTableView.setItems(results);

    }

    /**
     * This method navigates the user to the AddPart view
     *
     * @param event
     * @exception IOException On input error.
     */
    @FXML public void addPart(ActionEvent event) throws IOException {

        AnchorPane pane = FXMLLoader.load(getClass().getResource("/app/view/AddPart.fxml"));
        mainMenu.getChildren().setAll(pane);

    }

    /**
     * This method navigates the user to the AddProduct view
     *
     * @param event
     * @exception IOException On input error.
     */
    @FXML public void addProduct(ActionEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/app/view/AddProduct.fxml"));
        mainMenu.getChildren().setAll(pane);
    }

    /**
     * This method navigates the user to the ModifyPart view
     * and sends the Part object and the index of the part to the ModifyPart
     * controller.
     *
     * @param event
     * @exception IOException On input error.
     */
    @FXML public void modifyPart(ActionEvent event) throws IOException {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        int index = partTableView.getSelectionModel().getSelectedIndex();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/app/view/ModifyPart.fxml"));
        loader.load();


        ModifyPart modifyPart =loader.getController();
        modifyPart.setPart(index, part);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method navigates the user to the ModifyProduct view
     * and sends the Product object and the index of the part to the ModifyProduct
     * controller.
     *
     * @param event
     * @exception IOException On input error.
     */
    @FXML public void modifyProduct(ActionEvent event) throws IOException {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        int index = productTableView.getSelectionModel().getSelectedIndex();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/app/view/ModifyProduct.fxml"));
            loader.load();


            ModifyProduct modifyProduct =loader.getController();
            modifyProduct.setProduct(index, product);

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     *This method deletes selected part from inventory
     *
     * @param event
     */
    @FXML public void deletePart(ActionEvent event){
        Part part = partTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + part.getName() + "?", ButtonType.YES,ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if(alert.getResult()== ButtonType.YES){
            Inventory.deletePart(part);
        }


    }

    /**
     *This method deletes selected product from inventory
     *
     * @param event
     */
    @FXML public void deleteProduct(ActionEvent event){
        Product product = productTableView.getSelectionModel().getSelectedItem();
        ObservableList<Part> associatedParts = product.getAllAssociatedParts();
        System.out.println(associatedParts);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + product.getName() + "?", ButtonType.YES,ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if(alert.getResult()== ButtonType.YES){
            if (associatedParts.toArray().length != 0){
                Alert alert2 = new Alert(Alert.AlertType.ERROR,  product.getName() + " can not be deleted, because it has associated parts. Remove associated parts before deleting.", ButtonType.OK);
                alert2.showAndWait();
                return;
            }
            Inventory.deleteProduct(product);
        }
    }

    /**
     * This method exits the app.
     *
     * @param event
     */
    @FXML public void exitApp(ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Exit Application?", ButtonType.YES,ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if(alert.getResult()== ButtonType.YES){
            System.exit(0);
        }
    }
}
