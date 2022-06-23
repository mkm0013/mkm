package Controllers;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {
    Stage stage;
    Parent scene;

    @FXML private TextField modProdCost;
    @FXML private TextField modProdId;
    @FXML private TextField modProdInv;
    @FXML private TextField modProdMax;
    @FXML private TextField modProdMin;
    @FXML private TextField modProdName;
    @FXML private TextField modProdfindme;
    @FXML private TableColumn<Part, Integer> modprodaddpartIDCol;
    @FXML private TableColumn<Part, Double> modprodaddpartcostCol;
    @FXML private TableColumn<Part, Integer> modprodaddpartinvlvlCol;
    @FXML private TableColumn<Part, String> modprodaddpartnameCol;
    @FXML private TableView<Part> modprodaddparttblview;
    @FXML private TableColumn<Part, Integer> modprodassopartIDCol;
    @FXML private TableColumn<Part, String> modprodassopartnameCol;
    @FXML private TableView<Part> modprodassoparttblview;
    @FXML private TableColumn<Part, Double> modprodpartassocostCol;
    @FXML private TableColumn<Part, Integer> modprodpartassoinvlvlCol;
    private int productId;
    public Product selectedProduct;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();


    /*
    Initializes the controller class
     */
    public void initialize(URL url, ResourceBundle resourceBundle){

        //full parts table
        modprodaddpartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modprodaddpartnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modprodaddpartinvlvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modprodaddpartcostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modprodaddparttblview.setItems(Inventory.getallParts());

        //Table for associated parts
        modprodassopartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modprodassopartnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modprodpartassoinvlvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modprodpartassocostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        modprodassoparttblview.setItems(associatedParts);

        updateModAssociatedPartTable();
    }

    /*
    * Method to retrieve the selected product to modify it
    */
    public void retrieveProduct (Product selectedProduct){

        this.selectedProduct = selectedProduct;
        productId = Inventory.getallProducts().indexOf(selectedProduct);
        modProdId.setText(Integer.toString(selectedProduct.getId()));
        modProdName.setText(selectedProduct.getName());
        modProdInv.setText(Integer.toString(selectedProduct.getStock()));
        modProdCost.setText(Double.toString(selectedProduct.getPrice()));
        modProdMax.setText(Integer.toString(selectedProduct.getMax()));
        modProdMin.setText(Integer.toString(selectedProduct.getMin()));
        associatedParts.addAll(selectedProduct.getAllAssociatedParts());

    }
    @FXML
    void onActionModProdAddPart(ActionEvent event) {

        Part selectedPart = modprodaddparttblview.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            associatedParts.add(selectedPart);
            updateModAssociatedPartTable();
        } else{
            MainMenuController.errorBox("Part not selected.", "Select a part to add to the table.");
        }
    }

    @FXML void onActionDisplayMainMenu(ActionEvent event)  throws IOException {

        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML void onActionModProdRemoveAssoPart(ActionEvent event) {

        Part selectedPart = modprodassoparttblview.getSelectionModel().getSelectedItem();
        if(selectedPart != null) {
            MainMenuController.confirmChoice("Removing associated part.", "Are you sure you want to remove the associated part?");
            associatedParts.remove(selectedPart);
            updateModAssociatedPartTable();
        } else{
            MainMenuController.errorBox("No selection.","You must select a part to remove.");
        }
    }

    /*
    Same validations as adding a product
     */
    @FXML void onActionModProdSave(ActionEvent event) throws IOException {

        if (modProdName.getText().isEmpty() || modProdCost.getText().isEmpty() || modProdInv.getText().isEmpty() || modProdMax.getText().isEmpty()|| modProdMin.getText().isEmpty() ){
            MainMenuController.errorBox("Input error.", "Fields cannot be blank, check fields.");
        }

        String name = modProdName.getText();
        double price = Double.parseDouble(modProdCost.getText());
        int stock = Integer.parseInt(modProdInv.getText());
        int max = Integer.parseInt(modProdMax.getText());
        int min = Integer.parseInt(modProdMin.getText());

        Product temp = new Product(productId, name, price, stock, min, max);
        Inventory.amendProductList(productId, temp);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene (scene));
        stage.show();
    }

    @FXML void modProdSearch(ActionEvent event) {
        String sField = modProdfindme.getText();
        ObservableList<Part> partOptions = Inventory.searchParts(sField);
        if(partOptions.isEmpty()) {
            MainMenuController.errorBox("No Match", "Unable to locate part.");
        } else {
            modprodaddparttblview.setItems(partOptions);
        }
    }

    /*
     Method to update associated parts table with added parts
     */
    public void updateModAssociatedPartTable(){
        modprodassoparttblview.setItems(associatedParts);
    }
    public void getAssociatedParts (){ modprodassoparttblview.getItems();}


}

