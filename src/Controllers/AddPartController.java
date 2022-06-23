
package Controllers;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
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



public class AddPartController implements Initializable {

    @FXML
    private RadioButton inhouseRB;
    @FXML
    private RadioButton outsourceRB;
    @FXML
    private TextField addpartcosttxt;
    @FXML
    private TextField addpartinvIDtxt;
    @FXML
    private TextField addpartmaxtxt;
    @FXML
    private TextField addpartmintxt;
    @FXML
    private TextField addpartnametxt;
    @FXML
    private Label addpartspeciallbl;
    @FXML
    private TextField addPartIDTXTField;
    @FXML
    private TextField addPartSpecialTxt;

    private int partId;

    Stage stage;
    Parent scene;


    /*
    Initializes the controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partId = Inventory.getPartIdNum();
        addPartIDTXTField.setText(String.valueOf(partId));
        addPartIDTXTField.setEditable(false);
        inhouseRB.setSelected(true);
        addpartspeciallbl.setText("Machine ID");
    }

    /*
    Changes the label to either "Company Name" or "Machine ID"
     */
    @FXML public void onActionRadiob (ActionEvent event) {
            if (outsourceRB.isSelected())
                this.addpartspeciallbl.setText("Company Name");
            else
                this.addpartspeciallbl.setText("Machine ID");
        }
    @FXML void onActionDisplayMainMenu (ActionEvent event) throws IOException {
        if (MainMenuController.confirmChoice("Cancel add part?", "Are you certain?"))
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }

        /**
            *Below validates field inputs, that max is greater than min, that the inventory is
            within the bounds of max and min,
            that there are no blank fields, and that the correct types of inputs are in the fields.
            *
         * NULLPOINTEREXCEPTION
            * I ran into a lot of problems with the fxml loader, this was due to my resource folder being outside of the src folder.

         */
    @FXML void onActionSaveAddPart (ActionEvent event) {

        try {
            String name = addpartnametxt.getText();
            double cost = Double.parseDouble(addpartcosttxt.getText());
            int partInv = Integer.parseInt(addpartinvIDtxt.getText());
            int minPart = Integer.parseInt(addpartmintxt.getText());
            int maxPart = Integer.parseInt(addpartmaxtxt.getText());
            String partSpecial = addPartSpecialTxt.getText();

            if (addpartnametxt.getText().isEmpty() || addpartinvIDtxt.getText().isEmpty() || addpartmaxtxt.getText().isEmpty() || addPartSpecialTxt.getText().isEmpty() || addpartmintxt.getText().isEmpty() || addpartcosttxt.getText().isEmpty()) {
                MainMenuController.errorBox("Input error.", "Fields cannot be blank, check fields.");}

                if (minPart > maxPart) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than min.");
                    alert.showAndWait();
                    return;
                } else {
                    if (partInv < minPart || maxPart < partInv) {
                        MainMenuController.errorBox("Inventory error.", "Inventory must be within bounds of min and max.");
                        return;
                    }
                }
                if (outsourceRB.isSelected()) {
                    Outsourced outPart = new Outsourced(partId, name, cost, partInv,maxPart, minPart, partSpecial);
                    Inventory.addPart(outPart);
                } else {
                    Inhouse inPart = new Inhouse(partId, name, cost, partInv, maxPart, minPart, Integer.parseInt(partSpecial));
                    Inventory.addPart(inPart);
                }

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } catch(NumberFormatException | IOException e){
                MainMenuController.errorBox("Input error.", "Check fields.");
                return;
            }


        }
}

