package sample.View_Controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.stage.WindowEvent;
import sample.Model.InHouse;
import sample.Model.Inventory;
import sample.Model.Outsourced;

import javax.swing.*;
import java.io.IOException;

import static sample.View_Controller.MainController.*;

/**
 * This class is the controller for the main window.
 */
public class AddPartController {

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


    /**
     * This method changes the scene from the add part window to the main window.
     * @param actionEvent The cancel button is pushed
     * @throws IOException
     */
    public void changeScreenButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException
    {
        //allows the user to confirm if they would like to cancel
        int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you would like to cancel adding a part?", "Cancel Addition",
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
     * This method dynamically updates the changeIDOrCompanyLabel based on which radiobutton is currently selected.
     * @param actionEvent a radiobutton is selected
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

    /**
     * Determine whether a part is in-house or outsourced and add that part to the inventory if it is a valid entry
     * @param actionEvent The save button is pushed
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
                 if (isValidPart(newOutsourcedPart))
                 {
                     newOutsourcedPart.setId(Inventory.generatePartID());
                     Inventory.addPart(newOutsourcedPart);
                     //go back to main screen
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
                if (isValidPart(newInHousePart))
                {
                    newInHousePart.setId(Inventory.generatePartID());
                    Inventory.addPart(newInHousePart);

                    //go back to main screen
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
}