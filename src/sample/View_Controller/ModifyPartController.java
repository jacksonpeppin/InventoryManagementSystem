package sample.View_Controller;

import com.sun.org.apache.xerces.internal.impl.dv.xs.IntegerDV;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sample.Model.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.View_Controller.MainController.*;

/**
 * This class is the controller for the modify part window.
 */
public class ModifyPartController implements Initializable
{

    @FXML
    private AnchorPane addPartPane;

    @FXML
    private HBox addPartHBox;

    @FXML
    private Label addOrModifyPartLabel;

    @FXML
    private Group addPartRadioButtonGroup;

    @FXML
    private RadioButton outsourcedRadioButton;

    @FXML
    private ToggleGroup part;

    @FXML
    private RadioButton inHouseRadioButton;

    @FXML
    private GridPane addPartGridPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label invLAbel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label maxLabel;

    @FXML
    private Label machineOrCompanyID;

    @FXML
    private Label minLabel;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField invTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextField maxTextField;

    @FXML
    private TextField machineOrCompanyIDTextField;

    @FXML
    private TextField minTextField;

    private Part selectedPart;
    private InHouse inHousePart;
    private Outsourced outsourcedPart;
    private int selectedPartIndex;

    /**
     * Take user back to the main window.
     * @param actionEvent cancel button is pushed
     * @throws IOException
     */
    public void changeScreenButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException
    {
        int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you would like to cancel modification?", "Cancel Modification",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (dialogButton == JOptionPane.YES_OPTION)
        {
            Parent addPartParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            //This line gets the stage information
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            window.setScene(addPartScene);
            window.show();
        }
    }

    /**
     * Initialize modify part screen with selected part's data in the text fields.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        selectedPartIndex = getSelectedPartIndex();
        selectedPart = getSelectedPart();
        if (isInHouse(selectedPart)) {
            inHousePart = (InHouse) selectedPart;
            inHouseRadioButton.setSelected(true);
            machineOrCompanyIDTextField.setText(String.valueOf(inHousePart.getMachineID()));
        }
        if (!isInHouse(selectedPart))
        {
            machineOrCompanyID.setText("Company Name");
            outsourcedPart = (Outsourced) selectedPart;
            outsourcedRadioButton.setSelected(true);
            machineOrCompanyIDTextField.setText(outsourcedPart.getCompanyName());
        }
        idTextField.setText(String.valueOf(selectedPart.getId()));
        nameTextField.setText(selectedPart.getName());
        invTextField.setText(String.valueOf(selectedPart.getStock()));
        priceTextField.setText(String.valueOf(selectedPart.getPrice()));
        maxTextField.setText(String.valueOf(selectedPart.getMax()));
        minTextField.setText(String.valueOf(selectedPart.getMin()));
    }

    /**
     * save modified part to AllParts observable list when save button is pushed.
     * @param actionEvent save button is pushed
     * @throws IOException
     */
    public void savePart(javafx.event.ActionEvent actionEvent) throws IOException
    {
        if (outsourcedRadioButton.isSelected())
        {
            Outsourced newOutsourcedPart = new Outsourced(0, "", 0, 0, 0, 0, "");
            newOutsourcedPart.setName(nameTextField.getText());
            if ((machineOrCompanyIDTextField.getText().isEmpty()))
                JOptionPane.showMessageDialog(null, "Outsourced Part must have a name");
            else
                newOutsourcedPart.setCompanyName(machineOrCompanyIDTextField.getText());
            try
            {
                newOutsourcedPart.setPrice(Double.parseDouble(priceTextField.getText()));
                newOutsourcedPart.setStock(Integer.parseInt(invTextField.getText()));
                newOutsourcedPart.setMin(Integer.parseInt(minTextField.getText()));
                newOutsourcedPart.setMax(Integer.parseInt(maxTextField.getText()));
                newOutsourcedPart.setId(selectedPart.getId());
                if (isValidPart(newOutsourcedPart))
                {
                    Inventory.updatePart(selectedPartIndex, newOutsourcedPart);
                    Parent addPartParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Scene addPartScene = new Scene(addPartParent);
                    //This line gets the stage information
                    Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                    window.setScene(addPartScene);
                    window.show();
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Text field may not be empty or contain non-numbers.");
            }

        }
        else
            {
            InHouse newInHousePart = new InHouse(0, "", 0, 0, 0, 0, 0);
            newInHousePart.setName(nameTextField.getText());
            try
            {
                newInHousePart.setPrice(Double.parseDouble(priceTextField.getText()));
                newInHousePart.setStock(Integer.parseInt(invTextField.getText()));
                newInHousePart.setMin(Integer.parseInt(minTextField.getText()));
                newInHousePart.setMax(Integer.parseInt(maxTextField.getText()));
                newInHousePart.setMachineID(Integer.parseInt(machineOrCompanyIDTextField.getText()));
                newInHousePart.setId(selectedPart.getId());
                if (isValidPart(newInHousePart))
                {
                    Inventory.updatePart(selectedPartIndex, newInHousePart);
                    //change back to main screen
                    Parent addPartParent = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Scene addPartScene = new Scene(addPartParent);
                    //This line gets the stage information
                    Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

                    window.setScene(addPartScene);
                    window.show();
                }
            }
            catch (NumberFormatException e)
            {
                JOptionPane.showMessageDialog(null, "Text field may not be empty or contain non-numbers.");
            }
        }

    }

    /**
     * Dynamically update label based on which radio button is selected.
     * @param actionEvent user clicks a radio button
     * @throws IOException
     */
    public void changeIDOrCompanyLabel(javafx.event.ActionEvent actionEvent) throws IOException
    {
        if(outsourcedRadioButton.isSelected())
        {
            machineOrCompanyID.setText("Company Name");
        }
        else
            {
            machineOrCompanyID.setText("Machine ID");
        }
    }
}