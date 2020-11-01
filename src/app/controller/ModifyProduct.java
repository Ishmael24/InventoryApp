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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *The ModifyProduct class is the controller for the ModifyProduct view.
 *
 * @author Ishmauel Martin
 */
public class ModifyProduct implements Initializable {
    Stage stage;
    Parent scene;

    int index;

    private ObservableList<Part> associatedParts;

    @FXML
    private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField inventory;
    @FXML private TextField productPrice;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
    @FXML private TextField partSearchBox;

    @FXML private TableView<Part> partTableView1;
    @FXML private TableColumn<Part, Integer> partId1;
    @FXML private TableColumn<Part, String> partName1;
    @FXML private TableColumn<Part, Integer> partStock1;
    @FXML private TableColumn<Part, Double> partPrice1;

    @FXML private TableView<Part> partTableView2;
    @FXML private TableColumn<Part, Integer> partId2;
    @FXML private TableColumn<Part, String> partName2;
    @FXML private TableColumn<Part, Integer> partStock2;
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
     * This method sets the text fields value with the values of the product that needs to be modified.
     * @param index is the index of product
     * @param product is the product object that needs to be modified
     */
    public void setProduct(int index, Product product){
        this.index = index;
        productId.setText(Integer.toString(product.getId()));
        productName.setText(product.getName());
        inventory.setText(Integer.toString(product.getStock()));
        productPrice.setText(Double.toString(product.getPrice()));
        productMax.setText(Integer.toString(product.getMax()));
        productMin.setText(Integer.toString(product.getMin()));
        partTableView2.setItems(product.getAllAssociatedParts());
        associatedParts = product.getAllAssociatedParts();
        if (associatedParts==null){
            associatedParts = FXCollections.observableArrayList();
        }
        System.out.println(associatedParts);
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

        String productName1 = productName.getText();
        int productId1 = Integer.parseInt(productId.getText());

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
            Inventory.updateProduct(index, product);

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
    public void cancelModifyProduct(ActionEvent event) throws IOException {
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
