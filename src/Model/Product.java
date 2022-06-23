package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id ;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public Product(){
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id sets the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return name returns the name
     */

    public String getName() {
        return name;
    }

    /**
     *
     * @param name sets the name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return return the price
     */

    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price sets the price
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return stock returns the stock
     */

    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock sets the stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return min amount
     */

    public int getMin() {
        return min;
    }

    /**
     *
     * @param min sets the min amount
     */

    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return max amount
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max sets the max amount
     */
    public void setMax(int max) {
        this.max = max;
    }
    /*
    adds the associated part to the associated parts list for that product
     */
    public void addAssociatedPart(ObservableList<Part> part){
        this.associatedParts.addAll(part);
    }

    /**
   Returns all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {return associatedParts;}
}
