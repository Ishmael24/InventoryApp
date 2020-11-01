package app.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *Inventory class manages the inventory of all parts and products
 *
 * @author Ishmauel Martin
 */
public class Inventory {
    // An ObservableList of all parts
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // An ObservableList of all products
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This method add a new part to the allParts Observable list.
     *
     * @param newPart is an instance of Part
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * This method add a new product to the allProduct Observable list.
     *
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * This method that takes a part id and looks for the part by
     * iterating through the allParts list.
     * If the part ID that is passed matches an ID
     * of a part that is in the list, that part object is returned.
     * The runtime of this method is O(n).
     *
     * @param partId the ID of the part being searched
     * @return foundPart is the part object that matches the ID that was passed
     */
    public static Part lookupPart(int partId){
        Part foundPart = null;
        for (Part part: allParts){
            if(part.getId() == partId){
                foundPart = part;
                break;
            }
        }
        return foundPart;
    }

    /**
     *This method that takes a product id and looks for the part by
     * iterating through the allProduct list.
     * If the product ID that is passed matches an ID
     * of a product that is in the list, that product object is returned.
     * The runtime of this method is O(n).
     *
     * @param productId the ID of the product being searched
     * @return foundProduct is the product object that matches the ID that was passed
     */
    public static Product lookupProduct(int productId){
        Product foundProduct = null;
        for(Product product: allProducts){
            if(product.getId() == productId){
                foundProduct = product;
                break;
            }
        }
        return foundProduct;
    }

    /**
     *This method that takes a string input and looks for any part by
     * iterating through the allParts list. If the part name contains the characters of
     * string input, that part is added to the ObservableArrayList named list.
     * The runtime of this method is O(n).
     *
     * @param input is the string input that is being searched
     * @return list is the ObservableArrayList that holds all parts that contain the characters of the input
     */
    public static ObservableList<Part> lookupPart(String input){
        ObservableList<Part> list = FXCollections.observableArrayList();
        String partName = input.toLowerCase();
        for(Part part: allParts){
            String partName1 = part.getName().toLowerCase();
            if(partName1.contains(partName)){
                list.add(part);
            }
        }
        return list;
    }

    /**
     * This method that takes a string input and looks for any product by
     * iterating through the allProducts list. If the product name contains the characters of
     * string input, that product is added to the ObservableArrayList named list.
     * The runtime of this method is O(n).
     *
     * @param input is the string input that is being searched
     * @return list is the ObservableArrayList that holds all products that contain the characters of the input
     */
    public static ObservableList<Product> lookupProduct(String input){
        ObservableList<Product> list = FXCollections.observableArrayList();
        String productName = input.toLowerCase();
        for(Product product: allProducts){
            String productName1 = product.getName().toLowerCase();
            if(productName1.contains(productName)){
                list.add(product);
            }
        }
        return list;
    }

    /**
     * This method updates a part by taken the modified part object,
     * and place it at the index of the previous part object.
     *
     * @param index is the index of where to place the modified part object
     * @param selectedPart is the modified part object
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }

    /**
     * This method updates a product by taken the modified product object,
     * and place it at the index of the previous product object.
     *
     * @param index is the index of where to place the modified product object
     * @param newProduct is the modified product object
     */
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index, newProduct);
    }

    /**
     * This method deletes a part by taking the part that needs to be deleted,
     * and then iterates through the all part list. When the part is found in the list, it is removed
     * and the status is set to true and is returned. If the part is not found and remove the status returns false.
     *
     * @param selectedPart is the part that needs to be deleted/removed
     * @return status is the boolean value that tells if the part has been removed or not
     */
    public static boolean deletePart(Part selectedPart){
        boolean status = false;
        for (Part part: allParts){
            if(selectedPart == part){
                allParts.remove(selectedPart);
                status = true;
            }
        }
        return status;
    }

    /**
     * This method deletes a part by taking the product that needs to be deleted,
     * and then iterates through the allProduct list. When the product is found in the list, it is removed
     * and the status is set to true and is returned. If the product is not found and remove the status returns false.
     *
     * @param selectedProduct is the product that needs to be deleted/removed
     * @return status is the boolean value that tells if the product has been removed or not
     */
    public static boolean deleteProduct(Product selectedProduct){
        boolean status = false;
        for (Product product: allProducts){
            if (selectedProduct == product){
                allProducts.remove(product);
                status = true;
            }
        }
        return status;
    }

    /**
     * This method returns the list of all the parts in inventory.
     *
     * @return allParts is the ObservableLIst of parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method returns the list of all the products in inventory.
     *
     * @return allProducts is the ObservableLIst of products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
