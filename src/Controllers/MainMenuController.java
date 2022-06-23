package Controllers;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import java.util.Optional;
import java.util.ResourceBundle;

import static Controllers.AddProductController.associatedParts;


public class MainMenuController implements Initializable {
    Stage stage;
    Parent scene;


    @FXML
    private TableColumn<Part, Integer> mainpartinvlvlCol;
    @FXML
    private TableColumn<Part, Integer> mainpartIDCol;
    @FXML
    private TableColumn<Part, String> mainpartnameCol;
    @FXML
    private TableView<Part> mainpartsTableView;
    @FXML
    private TableColumn<Part, Double> mainpartscostCol;
    @FXML
    private TableColumn<Product, Integer> mainprodIDCol;
    @FXML
    private TableView<Product> mainprodTableView;
    @FXML
    private TableColumn<Product, Double> mainprodcostCol;
    @FXML
    private TableColumn<Product, Integer> mainprodinvlvlCol;
    @FXML
    private TableColumn<Product, String> mainprodnameCol;
    @FXML
    private TextField mainMenuPartSearch;
    @FXML
    private TextField mainMenuProductSearch;


    @FXML
    void mainMenuProductResults(ActionEvent event) {

        String sField = mainMenuProductSearch.getText();
        ObservableList<Product> products = Inventory.productSearch(sField);
        if (products.isEmpty()) {
            MainMenuController.errorBox("No Match", "Unable to locate product");
        } else {
            mainprodTableView.setItems(products);
        }
    }

    @FXML
    void mainMenuPartSearchbar(ActionEvent event) {

        String sField = mainMenuPartSearch.getText();
        ObservableList<Part> partOptions = Inventory.searchParts(sField);
        if (partOptions.isEmpty()) {
            MainMenuController.errorBox("No Match", "Unable to locate part");
        } else {
            mainpartsTableView.setItems(partOptions);
        }
    }

    @FXML
    void onActionAddPartMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddProdMain(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML
    void onActionDeletePartMain(ActionEvent event) {
        if (mainpartsTableView.getSelectionModel().isEmpty()) {
            errorBox("Error!", "No part selected.");
            return;
        }
        if (confirmChoice("Deleting Part.", "Are you sure you want to delete this part?")) ;
        Part selectedPart = mainpartsTableView.getSelectionModel().getSelectedItem();
        mainpartsTableView.getItems().remove(selectedPart);
    }

    @FXML
    void onActionDeleteProdMain(ActionEvent event) {
        Product selectedProduct = mainprodTableView.getSelectionModel().getSelectedItem();


            if (selectedProduct == null) {
                errorBox("Error", "You must select a product!");
                return;
            } else if (selectedProduct.getAllAssociatedParts().size() > 0) {
                errorBox("Error!", "You must remove any associated parts before deleting!");
                return;
            }
            confirmChoice("Delete product.", "Are you sure you want to delete this product?");
            mainprodTableView.getItems().remove(selectedProduct);
            }
        @FXML void onActionExitProgram (ActionEvent event){
            System.exit(0);
        }

        @FXML void onActionModifyPartMain (ActionEvent event){
            try {
                Part selectedPart = mainpartsTableView.getSelectionModel().getSelectedItem();
                if (mainpartsTableView.getSelectionModel().isEmpty()) {
                    MainMenuController.errorBox("Warning!", "You must select a part to modify.");
                    return;
                }
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ModifyPartsMenu.fxml")));
                scene = loader.load();
                ModifyPartsController controller = loader.getController();
                controller.retrievePart(selectedPart);
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(scene));
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @FXML void onActionModifyProdMain (ActionEvent event){
            try {
                Product selectedProduct = mainprodTableView.getSelectionModel().getSelectedItem();
                if (mainprodTableView.getSelectionModel().isEmpty()) {
                    MainMenuController.errorBox("Warning!", "You must select a product to modify.");
                    return;
                }
                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                FXMLLoader loader = new FXMLLoader((getClass().getResource("/view/ModifyProductMenu.fxml")));
                scene = loader.load();
                ModifyProductController controller = loader.getController();
                controller.retrieveProduct(selectedProduct);
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(scene));
                stage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    /*
    Confirmation messages
     */
        static boolean confirmChoice (String title, String content){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle(title);
            alert.setHeaderText("Confirm");
            alert.setContentText(content);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return true;
            } else {
                return false;
            }
        }

    /*
    Error messages
     */
        static boolean errorBox (String title, String content){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText("Error");
            alert.setContentText(content);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                return true;
            } else {
                return false;
            }
        }

        /*Initializes the controller class*/
        @FXML public void initialize (URL location, ResourceBundle rb){

            mainpartsTableView.setItems(Inventory.getallParts());
            mainpartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            mainpartnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            mainpartinvlvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            mainpartscostCol.setCellValueFactory(new PropertyValueFactory<>("price"));


            mainprodTableView.setItems(Inventory.getallProducts());
            mainprodIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
            mainprodnameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            mainprodinvlvlCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
            mainprodcostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        }


}

