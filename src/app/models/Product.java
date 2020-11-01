package app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Ishmauel Martin
 */
public class Product {
    // list of associated parts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    // product id
    private int id;

    // product name
    private String name;

    // product price
    private double price;

    // product stock
    private int stock;

    // product min amount
    private int min;

    // product max amount
    private int max;

    public Product(
            int id,
            String name,
            double price,
            int stock,
            int min,
            int max
    ){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    public Product(
            int id,
            String name,
            double price,
            int stock,
            int min,
            int max,
            ObservableList<Part> associatedParts
    ){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }

    /**
     * sets product id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * sets product name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets product price
     *
     * @param price
     */
    public void setPrice(double price){
        this.price = price;
    }

    /**
     * sets product stock amount
     *
     * @param stock
     */
    public void setStock(int stock){
        this.stock = stock;
    }

    /**
     * sets product min amount
     *
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * sets product max amount
     *
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * gets product id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * gets product name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * gets product price
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * gets product stock
     *
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * gets product min amount
     *
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * gets product max amount
     *
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * add parts to associated part list
     *
     * @param part
     * @return max
     */
    public void addAssociatedPart(Part part){}

    /**
     * deletes part from associatedPart list
     *
     * @return boolean value
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        boolean status = false;
        for (Part part: associatedParts){
            if(selectedAssociatedPart == part){
                associatedParts.remove(part);
                status = true;
            }
        }
        return status;
    }

    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}
