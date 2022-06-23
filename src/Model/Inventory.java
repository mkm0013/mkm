package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * I tried to use both AtomicIntegers and Integer property for the id numbers, but in reality a simple counter variable worked much better
 */

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
    Sets part and product id's to zero (id numbers will start from 1)
    Had an issue with the id numbers skipping a number, but I had added an unnecessary "+1" to the "addpartIdtxtfield.getText() and the addproductIdtxtfield.getText()" in the add product/ add part controllers.
     */
    private static int partIdNum = 0;
    private static int productIdNum = 0;

    public Inventory() {
    }

    /*
    Returns the part id
     */
    public static int getPartIdNum() {partIdNum++; return partIdNum;}

    /*
    Returns the product id
     */
    public static int getProductIdNum() {productIdNum++; return productIdNum;}

    /*
    Adds new part to array
     */
    public static void addPart(Part newpart) {
        allParts.add(newpart);
    }

    /*
    adds new product to array
     */
    public static void addProduct(Product newproduct) {
        allProducts.add(newproduct);
    }

    /*
    Returns all parts in array
     */
    public static ObservableList<Part> getallParts() {
        return allParts;
    }

    /*
    Returns all products in array
     */
    public static ObservableList<Product> getallProducts() {
        return allProducts;
    }

    /*
        validate the product doesn't have parts
    */

    public static String deleteNoParts(Part part) {

        for (int i = 0; i < allParts.size(); i++) {
            if (allProducts.get(i).getAllAssociatedParts().contains(part)) {
                return allProducts.get(i).getName();
            }
        }
        return "";
    }



    /*
    To search parts list
     */
    public static ObservableList<Part> searchParts(String partialName) {
        ObservableList<Part> partOptions = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getallParts();
        if (partialName.length() == 0) {
            partOptions = allParts;
        } else {
            for (Part part : allParts) {
                if (part.getName().toLowerCase().contains(partialName.toLowerCase())) {
                    partOptions.add(part);
                }
            }
            return partOptions;
        }
        return partOptions;
    }

    /*
    To search product list
     */
    public static ObservableList<Product> productSearch(String partialProdName) {

        ObservableList<Product> productOptions = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getallProducts();
        if (partialProdName.length() == 0) {
            productOptions = allProducts;
        } else {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(partialProdName.toLowerCase())) {
                    productOptions.add(product);
                }
            }
            return productOptions;
        }
        return productOptions;
    }

    /*
    These methods update the list of parts and products
     */
    public static void amendPartList(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }
    public static void amendProductList(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

}

