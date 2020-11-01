package app.controller;

import app.models.*;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *AddProduct is the controller class for AddProduct view.
 *If the input on a field is not valid, the part will not be save and
 *a custom alert will tell the user which fields to fix.
 *
 * @author Ishmauel Martin
 */
public class AddProduct implements Initializable {
    Stage stage;
    Parent scene;

    // list of associated parts of product
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // product id text field
    @FXML private TextField productId;

    // product name text field
    @FXML private TextField productName;

    // inventory text field
    @FXML private TextField inventory;

    // product price text field
    @FXML private TextField productPrice;

    // product max text field
    @FXML private TextField productMax;

    // product min text field
    @FXML private TextField productMin;

    // part search box
    @FXML private TextField partSearchBox;

    // available part table view
    @FXML private TableView<Part> partTableView1;

    // part id column
    @FXML private TableColumn<Part, Integer> partId1;

    // part name column
    @FXML private TableColumn<Part, String> partName1;

    // part stock column
    @FXML private TableColumn<Part, Integer> partStock1;

    // part price column
    @FXML private TableColumn<Part, Double> partPrice1;

    // associated part table view
    @FXML private TableView<Part> partTableView2;

    // part id column
    @FXML private TableColumn<Part, Integer> partId2;

    // part name column
    @FXML private TableColumn<Part, String> partName2;

    // part stock column
    @FXML private TableColumn<Part, Integer> partStock2;

    // part price column
    @FXML private TableColumn<Part, Double> partPrice2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partTableView1.setItems(Inventory.getAllParts());

        partId1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice1.setCellValueFactory(new PropertyValueFactory<>("price"));

        partId2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice2.setCellValueFactory(new PropertyValueFactory<>("price"));

        this.productId.setDisable(true);
    }

    /**
     * This method search for a part to add to
     * the product associated part list.
     * If a part is not found, an alert/message will be
     * shown telling the user that the part was not found.
     *
     * @param event
     */
    @FXML public void searchPart(ActionEvent event){
        String input = partSearchBox.getText();

        ObservableList<Part> results = FXCollections.observableArrayList();
        try{
            int id = Integer.parseInt(input);
            Part result = Inventory.lookupPart(id);
            results.add(result);

        }
        catch (NumberFormatException e){
            results = Inventory.lookupPart(input);
        }

        if (results.toArray().length == 0 || results.toArray()[0]== null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No part was found.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        partTableView1.setItems(results);
    }

    /**
     * This method add a selected part to the
     * products associatedPart list.
     *
     * @param event
     */
    public  void addAssociatedPart(ActionEvent event){
        Part part = partTableView1.getSelectionModel().getSelectedItem();
        if (part == null){
            return;
        }
        associatedParts.add(part);
        partTableView2.setItems(this.associatedParts);
    }

    /**
     * This method removes a part from the product's associated part list.
     * Before the part is removed, a confirmation alert/message is presented
     * to the user to confirm they want to delete selected part.
     *
     * @param event
     */
    public  void removeAssociatedPart(ActionEvent event){
        Part part = partTableView2.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + part.getName() + "?", ButtonType.YES,ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();

        if(alert.getResult()== ButtonType.YES){
            associatedParts.remove(part);
            partTableView2.setItems(this.associatedParts);
        }
    }

    /**
     * This method saves the new Product to the inventory.
     * If the input of a field, that field will be highlighted
     * and a custom massage will be presented.
     * The runtime is 0(1)
     *
     * @param event
     * @return nothing
     * @exception IOException On input error.
     */
    public void saveProduct(ActionEvent event) throws IOException {
        int length = Inventory.getAllProducts().toArray().length;
        int productId1 = length+1;

        String productName1 = productName.getText();

        String inventoryInput = inventory.getText();
        boolean inventoryValid = isInt(inventoryInput);

        String priceInput = productPrice.getText();
        boolean priceValid = isDouble(priceInput);

        String maxInput = productMax.getText();
        boolean maxValid = isInt(maxInput);

        String minInput = productMin.getText();
        boolean minValid = isInt(minInput);

        // This if statement  checks and makes sure values are valid before saving
        if (inventoryValid == true & priceValid == true & maxValid == true & minValid == true) {
            int inventory1 = Integer.parseInt(inventoryInput);
            double productPrice1 = Double.parseDouble(priceInput);
            int productMax1 = Integer.parseInt(maxInput);
            int productMin1 = Integer.parseInt(minInput);

            // Makes sure min value is not greater than the max value
            if (productMin1 > productMax1){
                Alert alert = new Alert(Alert.AlertType.ERROR,  "Min value can not be greater than max value.", ButtonType.OK);
                productMin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                productMax.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                alert.showAndWait();
                return;
            }

            Product product = new Product(productId1, productName1, productPrice1, inventory1, productMin1, productMax1, this.associatedParts);
            Inventory.addProduct(product);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/app/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();

            // Is the values are not valid, a custom alert is create describing which fields need to be change
        }else {
            StringBuilder statement = new StringBuilder();
            if (inventoryValid == false){
                inventory.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The inventory input is invalid. ");
            }
            if (priceValid == false){
                productPrice.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The price input is invalid. ");
            }
            if (maxValid == false){
                productMax.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The max input is invalid. ");
            }
            if (minValid == false){
                productMin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The min input is invalid. ");
            }
            Alert alert = new Alert(Alert.AlertType.ERROR,  statement.toString(), ButtonType.OK);
            alert.showAndWait();

            return;
        }
    }

    /**
     * This method returns the user to the main menu,
     * because they do not want to add a new product.
     *
     * @exception  IOException On input error
     * @param event
     */
    public void cancelAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/app/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This method checks to see if the input is a integer.
     * Is the input is an int, a status of true is returned, else the status returned is false.
     *
     * @param input
     * @return status
     */
    public boolean isInt(String input){
        boolean status = true;
        try {
            int validInput = Integer.parseInt(input);
        }catch (NumberFormatException e){
            status = false;
        }
        return status;
    }

    /**
     * This method checks to see if the input is a double.
     * Is the input is an double, a status of true is returned, else the status returned is false.
     *
     * @param input
     * @return status
     */
    public boolean isDouble(String input){
        boolean status = true;
        try {
            double validInput = Double.parseDouble(input);
        }catch (NumberFormatException e){
            status = false;
        }
        return status;
    }
}
