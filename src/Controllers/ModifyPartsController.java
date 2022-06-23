package Controllers;

import Model.Inhouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;

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

public class ModifyPartsController implements Initializable {

    Stage stage;
    Parent scene;


    @FXML private RadioButton modINrb;
    @FXML private RadioButton modOUTrb;
    @FXML private TextField modPartCosttxt;
    @FXML private TextField modPartID;
    @FXML private TextField modPartInvtxt;
    @FXML private TextField modPartMaxtxt;
    @FXML private TextField modPartMintxt;
    @FXML private TextField modPartNametxt;
    @FXML private Label modPartSpecialLbl;
    @FXML private TextField modPartSpecialtxt;
    @FXML public Part selectedPart;
    private int partId;


    @FXML public void inORoutRB (ActionEvent event) {
        if (modOUTrb.isSelected())
            this.modPartSpecialLbl.setText("Company Name");
        else
            this.modPartSpecialLbl.setText("Machine ID");
    }
    @FXML
    void onActionDisplayMainMenu(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /*
    Same validations as adding a part
     */
    @FXML void onActionSaveModPart(ActionEvent event) {

        try {
            String name = modPartNametxt.getText();
            double cost = Double.parseDouble(modPartCosttxt.getText());
            int partInv = Integer.parseInt(modPartInvtxt.getText());
            int maxPart = Integer.parseInt(modPartMaxtxt.getText());
            int minPart = Integer.parseInt(modPartMintxt.getText());
            String partSpecial = modPartSpecialtxt.getText();

            if(modPartNametxt.getText().isEmpty() || modPartMaxtxt.getText().isEmpty() || modPartMintxt.getText().isEmpty() || modPartSpecialtxt.getText().isEmpty()|| modPartInvtxt.getText().isEmpty() ){
                MainMenuController.errorBox("Input error.", "Fields cannot be blank, check fields.");
                return;
            }

            if (minPart > maxPart){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Max must be greater than min.");
                alert.showAndWait();
                return;
            } else {
                if (partInv < minPart || maxPart < partInv) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Inventory must be within min and max bounds.");
                    alert.showAndWait();
                    return;
                } if (modOUTrb.isSelected()) {
                    Outsourced outPart = new Outsourced(partId, name, cost, partInv, minPart, maxPart, partSpecial);
                    Inventory.amendPartList(partId, outPart);
                }
                if (modINrb.isSelected()){
                    Inhouse inPart = new Inhouse( partId, name, cost, partInv, minPart, maxPart, Integer.parseInt(modPartSpecialtxt.getText()));
                    Inventory.amendPartList(partId, inPart);
                }
            }

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene (scene));
            stage.show();

        }

        catch (NumberFormatException | IOException e) {
            MainMenuController.errorBox("Error", "Check fields.");
            return;
        }


    }

    /*Method to retrieve the selected part to modify*/
    public void retrievePart (Part selectedPart){

        this.selectedPart = selectedPart;
         partId = Inventory.getallParts().indexOf(selectedPart);
         modPartID.setText(Integer.toString(selectedPart.getId()));
         modPartNametxt.setText(selectedPart.getName());
         modPartInvtxt.setText(Integer.toString(selectedPart.getStock()));
         modPartCosttxt.setText(Double.toString(selectedPart.getPrice()));
         modPartMaxtxt.setText(Integer.toString(selectedPart.getMax()));
         modPartMintxt.setText(Integer.toString(selectedPart.getMin()));

         if(selectedPart instanceof Inhouse){
             Inhouse in = (Inhouse) selectedPart;
             modINrb.setSelected(true);
             this.modPartSpecialLbl.setText("Machine ID");
             modPartSpecialtxt.setText(Integer.toString(in.getMachineID()));
         } else{
             Outsourced out =(Outsourced) selectedPart;
             modOUTrb.setSelected(true);
             this.modPartSpecialLbl.setText("Company Name");
             modPartSpecialtxt.setText(out.getCompanyName());
         }
    }

    /*
    Initializes the controller class
    * */
    public void initialize(URL url, ResourceBundle rb){
        modPartSpecialtxt.setEditable(false);
        modINrb.setSelected(true);
        modPartSpecialLbl.setText("Machine ID");
    }


}