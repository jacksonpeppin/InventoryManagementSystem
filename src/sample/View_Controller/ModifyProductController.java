package sample.View_Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Model.Inventory;
import sample.Model.Part;
import sample.Model.Product;

import javax.swing.*;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.View_Controller.MainController.*;

/**
 * This class is the controller for the modify product window.
 */
public class ModifyProductController implements Initializable {

    @FXML
    private Label modifyProdLabel;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private Button saveButton;

    @FXML
    private Label idLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label invLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label maxLabel;

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
    private TextField minTextField;

    @FXML
    private TableView<Part> selectedProdPartTableView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button removePartButton;

    @FXML
    private Button addAssociatedPartButton;

    @FXML
    private TextField partSearchTextField;
    private Product selectedProduct;
    private Product newProduct;
    private int newProductIndex;

    /**
     * Populate table views with data from the selected product.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        selectedProduct = getSelectedProduct();
        newProduct = new Product(selectedProduct.getId(), "", 0, 0, 0, 0);
        for (Part p: selectedProduct.getAllAssociatedParts())
        {
            newProduct.addAssociatedPart(p);
        }
        newProductIndex = getSelectedProductIndex();
        //initialize part table columns
        TableColumn partIDTableColumn = new TableColumn("Part ID");
        TableColumn partNameTableColumn = new TableColumn("Part Name");;
        TableColumn partInvLevelTableColumn = new TableColumn("Inventory Label");;
        TableColumn partPriceTableColumn = new TableColumn("Price/Cost per Unit");;
        partTableView.getColumns().addAll(partIDTableColumn, partNameTableColumn, partInvLevelTableColumn, partPriceTableColumn);
        //initialize product table columns
        TableColumn selectedProdIDTableColumn = new TableColumn("Part ID");
        TableColumn selectedProdNameTableColumn = new TableColumn("Part Name");;
        TableColumn selectedProdInvLevelTableColumn = new TableColumn("Inventory Label");;
        TableColumn selectedProdPriceTableColumn = new TableColumn("Price/Cost per Unit");;
        selectedProdPartTableView.getColumns().addAll(selectedProdIDTableColumn, selectedProdNameTableColumn, selectedProdInvLevelTableColumn, selectedProdPriceTableColumn);


        //associate part data with table
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //associate product data with table
        selectedProdIDTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        selectedProdNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        selectedProdInvLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        selectedProdPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //populate both tableviews
        partTableView.setItems(Inventory.getAllParts());
        selectedProdPartTableView.setItems(selectedProduct.getAllAssociatedParts());
        //get selected product and populate textfields with selected product

        idTextField.setText(String.valueOf(selectedProduct.getId()));
        nameTextField.setText(selectedProduct.getName());
        invTextField.setText(String.valueOf(selectedProduct.getStock()));
        priceTextField.setText(String.valueOf(selectedProduct.getPrice()));
        maxTextField.setText(String.valueOf(selectedProduct.getMax()));
        minTextField.setText(String.valueOf(selectedProduct.getMin()));
    }

    /**
     * Take user back to the main window.
     * @param actionEvent cancel button is clicked
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
     * update the product information of the selected product if valid and go back to main window.
     * @param actionEvent save button is clicked
     * @throws IOException
     * @throws ValidationException
     */
    public void addProductButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException, ValidationException
    {
        newProduct.setName(nameTextField.getText());
        try
        {
            newProduct.setPrice(Double.parseDouble(priceTextField.getText()));
            newProduct.setStock(Integer.parseInt(invTextField.getText()));
            newProduct.setMin(Integer.parseInt(minTextField.getText()));
            newProduct.setMax(Integer.parseInt(maxTextField.getText()));
            if (isValidProduct(newProduct))
            {

                Inventory.updateProduct(newProductIndex, newProduct);
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

    /**
     * associate selected part with the selected product.
     * @param actionEvent add part button pushed
     * @throws IOException
     */
    public void addAssociatedPartButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException
    {
        newProduct.addAssociatedPart(partTableView.getSelectionModel().getSelectedItem());
        selectedProdPartTableView.setItems(newProduct.getAllAssociatedParts());
    }

    /**
     * disassociate the selected part from the product.
     * @param actionEvent remove part button pushed
     * @throws IOException
     */
    public void removeAssociatedPartButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException
    {
        int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete the associated part?", "Delete Associated Part",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (dialogButton == JOptionPane.YES_OPTION) {
            newProduct.deleteAssociatedPart(selectedProdPartTableView.getSelectionModel().getSelectedItem());
            selectedProdPartTableView.setItems(newProduct.getAllAssociatedParts());
        }
    }

    /**
     * remove the default text from the search text field.
     * @param mouseEvent text field is clicked
     */
    public void partSearchBoxClearHandler(MouseEvent mouseEvent)
    {
        partSearchTextField.setText("");
        partSearchTextField.setOpacity(1);
    }
    /**
     * Check if there is a part with a matching name or id.
     * @param keyEvent the user presses the enter key
     */
    public void getPartsResultsHandler(javafx.scene.input.KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            String q = partSearchTextField.getText();

            ObservableList<Part> parts = Inventory.lookupPart(q);

            if(parts.size() == 0)
            {
                try
                {
                    int id = Integer.parseInt(q);
                    Part p = Inventory.lookupPart(id);
                    if (p != null)
                        parts.add(p);
                }
                catch (NumberFormatException e)
                {
                    //ignore
                }
            }

            if (parts.size() == 0)
            {
                JOptionPane.showMessageDialog(null, "No parts found.");
                partTableView.setItems(Inventory.getAllParts());
            }
            else
            {
                partTableView.setItems(parts);
            }
            partSearchTextField.setText("");
        }
    }
}