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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddProductController implements Initializable {


    //Add Product Input Fields
    @FXML
    private TextField ProductMaxInvtxt;
    @FXML
    private TextField ProductMinInvtxt;
    @FXML
    private TextField ProductCosttxt;
    @FXML
    private TextField ProductIDtxt;
    @FXML
    private TextField ProductNametxt;
    @FXML
    private TextField ProductStocktxt;

    //Parts Table View
    @FXML
    private TableView<Part> PartsTableView;
    @FXML
    private TableColumn<Part, String> ProductAddNameCol;
    @FXML
    private TableColumn<Part, Double> ProductPartCostCol;
    @FXML
    private TableColumn<Part, Integer> ProductPartIDCol;
    @FXML
    private TableColumn<Part, Integer> ProductPartInvCol;

    //Associated Parts Table
    @FXML
    private TableColumn<Part, Double> AssociatedPartCostCol;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartIDCol;
    @FXML
    private TableColumn<Part, Integer> AssociatedPartInvCol;
    @FXML
    private TableColumn<Part, String> AssociatedPartNameCol;
    @FXML
    private TableView<Part> AssociatedPartsTableView;

    //Other necessary fields
    @FXML
    TextField ProductSearchBar;
    private int productId;
    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;


    /*
    Method to update the associated parts table with added parts
     */
    public void updateAssociatedPartTable () {
        AssociatedPartsTableView.setItems(associatedParts);
    }

    /*
     *Below initializes the controller class
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productId = Inventory.getProductIdNum();
        ProductIDtxt.setText(Integer.toString(productId));
        updateAssociatedPartTable();
        ProductIDtxt.setEditable(false);

        //Full table of parts
        PartsTableView.setItems(Inventory.getallParts());
        ProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductAddNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Table for associated parts
        AssociatedPartsTableView.setItems(associatedParts);
        AssociatedPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssociatedPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssociatedPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssociatedPartCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    //To remove the associated part from the associated parts table
    @FXML void OnActionRemoveAssociatedPart(ActionEvent event) {
        Part selectedPart = AssociatedPartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            MainMenuController.confirmChoice("Removing associated part.", "Are you sure you want to remove the associated part?");
            associatedParts.remove(selectedPart);
            updateAssociatedPartTable();
        } else {
            MainMenuController.errorBox("No selection.", "You must select a part to remove.");
        }
    }

    //Searchbar for parts
    @FXML void getResultsBar(ActionEvent event) {
        String sField = ProductSearchBar.getText();
        ObservableList<Part> partOptions = Inventory.searchParts(sField);
        if (partOptions.isEmpty()) {
            MainMenuController.errorBox("No Match", "Unable to locate part.");
        } else {
            PartsTableView.setItems(partOptions);
        }
    }

    /*Adds part to the associated parts table*/
    @FXML void onActionAddPartToAssociatedTable(ActionEvent event) {
        Part selectedPart = PartsTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            associatedParts.add(selectedPart);
            updateAssociatedPartTable();
        } else {
            MainMenuController.errorBox("Part not selected.", "Select a part to add to the table.");
        }
    }

    @FXML void onActionDisplayMainMenu(ActionEvent event) throws IOException {
        if (MainMenuController.confirmChoice("Cancel add product", "Are you sure you want to cancel?"))
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Validates that the input fields are correct, that max is greater than min, etc. Also validates that the associated parts field has parts in it.
     * NUMBERFORMATEXCEPTION
     * Ran into a number format exception when a field has the wrong input format entered, so I implemented a try/catch to address the problem
     *
     * **/
    @FXML void onActionSaveAddProd(ActionEvent event) throws IOException {

        try {
                String name = ProductNametxt.getText();
                double price = Double.parseDouble(ProductCosttxt.getText());
                int stock = Integer.parseInt(ProductStocktxt.getText());
                int max = Integer.parseInt(ProductMaxInvtxt.getText());
                int min = Integer.parseInt(ProductMinInvtxt.getText());

            if (associatedParts.isEmpty()){
                MainMenuController.errorBox("Parts error.","You must have parts associated.");
                return;
            }

            if(ProductCosttxt.getText().isEmpty() || ProductStocktxt.getText().isEmpty() || ProductNametxt.getText().isEmpty() || ProductMinInvtxt.getText().isEmpty() || ProductMaxInvtxt.getText().isEmpty()){
                MainMenuController.errorBox("Input error.","Fields cannot be blank, check fields.");
            }

                if (min > max) {
                    MainMenuController.errorBox("Input error.", "Max must be greater than min.");
                    return;
                } else {
                    if (stock < min || max < stock) {
                       MainMenuController.errorBox("Stock error.", "Inventory must be within min and max bounds.");
                       return;
                    } else {
                        Product newProd = new Product();
                        newProd.setId(productId);
                        newProd.setName(name);
                        newProd.setPrice(price);
                        newProd.setStock(stock);
                        newProd.setMax(max);
                        newProd.setMin(min);
                        newProd.addAssociatedPart(associatedParts);
                        Inventory.addProduct(newProd);
                    }
                }
            } catch(NumberFormatException e) {
            MainMenuController.errorBox("Error.","Input error, check fields.");
            return;
            }

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

        }

    }


