package app.controller;

import app.models.InHouse;
import app.models.Inventory;
import app.models.Outsourced;
import app.models.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * This ModifyPart class is the controller ModifyPart view.
 * If the input on a field is not valid, the part will not be save and
 * a custom alert will tell the user which fields to fix.
 *
 * @author Ishmauel Martin
 */
public class ModifyPart implements Initializable {

    Stage stage;
    Parent scene;

    int index;

    // in house radio button
    @FXML private RadioButton inHouse;

    // out source radio button
    @FXML private RadioButton outSource;

    // toggle group
    private ToggleGroup toggleGroup;

    // part id text field
    @FXML private TextField partId;

    // part name text field
    @FXML private TextField partName;

    // inventory text field
    @FXML private TextField inventory;

    // part price text field
    @FXML private TextField partPrice;

    // part max text field
    @FXML private TextField partMax;

    // part min text field
    @FXML private TextField partMin;

    // machine id text field
    @FXML private TextField machineId;
    @FXML private Label machineIdLabel;

    public ModifyPart(){}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toggleGroup = new ToggleGroup();
        this.inHouse.setToggleGroup(toggleGroup);
        this.outSource.setToggleGroup(toggleGroup);
        this.partId.setDisable(true);
    }

    /**
     * This method the text field values with the values of
     * the part that the use wants to modify.
     *
     * @param index
     * @param part
     */
    public void setPart(int index, Part part){
        this.index = index;
        partId.setText(Integer.toString(part.getId()));
        partName.setText(part.getName());
        inventory.setText(Integer.toString(part.getStock()));
        partPrice.setText(Double.toString(part.getPrice()));
        partMax.setText(Integer.toString(part.getMax()));
        partMin.setText(Integer.toString(part.getMin()));

        if (part instanceof InHouse){
            inHouse.setSelected(true);
            machineIdLabel.setText("Machine ID");
            machineId.setText(Integer.toString(((InHouse) part).getMachineId()));
        }else {
            outSource.setSelected(true);
            machineIdLabel.setText("Company Name");
            machineId.setText(((Outsourced) part).getCompanyName());
        }
    }


    /**
     * This method sets the text of the label for the of the
     * machineID/companyName text field.
     *
     * @param event
     */
    public void radioBtnSelection(ActionEvent event){
        if (toggleGroup.getSelectedToggle().equals(this.inHouse)){
            machineIdLabel.setText("Machine ID");
        }else if (toggleGroup.getSelectedToggle().equals(this.outSource)){
            machineIdLabel.setText("Company Name");
        }
    }

    /**
     * This method saves the new Part to the inventory.
     * If the input of a field, that field will be highlighted
     * and a custom massage will be presented.
     * The runtime is 0(1)
     *
     * @param event
     * @return nothing
     * @exception IOException On input error.
     */
    public void savePart(ActionEvent event) throws IOException {

        int length = Inventory.getAllParts().toArray().length;
        int id = Integer.parseInt(partId.getText());
        String name = partName.getText();

        String priceInput = partPrice.getText();
        boolean priceIsValid = isDouble(priceInput);

        String stockInput = inventory.getText();
        boolean stockIsValid = isInt(stockInput);

        String minInput = partMin.getText();
        boolean minIsValid = isInt(minInput);

        String maxInput = partMax.getText();
        boolean maxIsValid = isInt(maxInput);

        if (priceIsValid == true & stockIsValid == true & minIsValid == true & maxIsValid == true) {

            double price = Double.parseDouble(partPrice.getText());
            int stock = Integer.parseInt(inventory.getText());
            int min = Integer.parseInt(partMin.getText());
            int max = Integer.parseInt(partMax.getText());

            if (min > max){
                Alert alert = new Alert(Alert.AlertType.ERROR,  "Min value can not be greater than max value.", ButtonType.OK);
                partMax.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                partMin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                alert.showAndWait();
                return;
            }

            if (toggleGroup.getSelectedToggle().equals(this.inHouse)) {
                String machineIdInput = machineId.getText();
                boolean machineIdValid = isInt(machineIdInput);
                if (machineIdValid == true) {
                    int mId = Integer.parseInt(machineIdInput);
                    InHouse part = new InHouse(id, name, price, stock, min, max, mId);
                    Inventory.updatePart(index, part);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/app/view/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }else{
                    machineId.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                    Alert alert = new Alert(Alert.AlertType.ERROR,  "Machine ID input is invalid.", ButtonType.OK);
                    alert.showAndWait();
                }

            } else {
                String companyName = machineId.getText();
                Outsourced part = new Outsourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(index,part);

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/app/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }else{
            StringBuilder statement = new StringBuilder();
            if (priceIsValid == false){
                partPrice.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The price input is invalid. ");
            }
            if (stockIsValid == false){
                inventory.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The inventory input is invalid. ");
            }
            if (minIsValid == false){
                partMin.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The min input is invalid. ");
            }
            if (maxIsValid == false){
                partMax.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                statement.append("The max input is invalid. ");
            }
            Alert alert = new Alert(Alert.AlertType.ERROR,  statement.toString(), ButtonType.OK);
            alert.showAndWait();

            return;
        }

    }

    /**
     * This method returns the user to the main menu,
     * because they do not want to add a new part.
     *
     * @exception  IOException On input error
     * @param event
     */
    public void cancelAddPart(ActionEvent event) throws IOException {
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
