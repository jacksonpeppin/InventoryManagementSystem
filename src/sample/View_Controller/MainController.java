package sample.View_Controller;

import javafx.application.Platform;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Model.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * RUNTIME ERROR The most time consuming errors I faced in this project were due to tiny little mistakes that took a lot
 * longer than they should have for me to solve. Prior to this class, I had only used Netbeans, but the IntelliJ IDE
 * seemed more modern, so I decided to try it out for this project. I was not aware that you had to set the controller for
 * fxml files in scene builder, so after I had created the general outline of this project I spent multiple hours just trying
 * to get it to open. Eventually, I realized all I had to do was set the controller and uncheck "Use fx:root construct"
 *
 * This class is the controller for the main window.
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane invManagementAnchorPane;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private Button partDeleteButton;

    @FXML
    private Button partModifyButton;

    @FXML
    private Button partAddButton;

    @FXML
    private Label partsLabel;

    @FXML
    private TextField partTextField;

    @FXML
    private TableView<Product> prodTableView;

    @FXML
    private Button prodAddButton;

    @FXML
    private Button prodDeleteButton;

    @FXML
    private Button prodModifyButton;

    @FXML
    private Label prodLabel;

    @FXML
    private TextField prodTextField;

    @FXML
    private Label invManagementLabel;

    @FXML
    private TextField partSearchTextField;

    @FXML
    private Button exitButton;

    //static variables to pass to other controllers
    private static Part selectedPart;
    private static Product selectedProduct;
    private static int selectedPartIndex;
    private static int selectedProductIndex;



    /**
     * This method keeps track of part currently selected in the main window's parts tableview.
     * @param part
     */
    public static void setSelectedPart(Part part){
        MainController.selectedPart = part;
    }

    /**
     * This method returns the part that was most recently selected in the parts table view.
     * @return the selected part
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     * This method keeps track of the selected part in the allParts observable list.
     */
    public static void setSelectedPartIndex() { selectedPartIndex = Inventory.getAllParts().indexOf(getSelectedPart()); }

    /**
     * this method returns the index of the part that is currently selected.
     * @return
     */
    public static int getSelectedPartIndex() {
        return selectedPartIndex;
    }

    /**
     * This method determines if a part is an inhouse object or outsourced object.
     * @param part
     * @return true if the part is an inhouse object.
     */
    public static boolean isInHouse(Part part)
    {
        if (part instanceof InHouse)
        {
            return true;
        }
        else
            return false;
    }

    /**
     * This method determines if the part meets the criteria to be determined a valid part.
     * @param part
     * @return true if valid
     */
    public static boolean isValidPart(Part part) {
        if (part.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Error: Part must have a name.");
            return false;
        }
        if (part.getPrice() < 0) {
            JOptionPane.showMessageDialog(null, "Error: Part must cost more than $0.00.");
            return false;
        }
        if (part.getStock() < 0) {
            JOptionPane.showMessageDialog(null, "There must be at least one of this part in stock.");
            return false;
        }
        if (part.getMin() > part.getMax()) {
            JOptionPane.showMessageDialog(null, "Error: The maximum must be greater than the minimum.");
            return false;
        }
        if (part.getMin() > part.getStock() || part.getMax() < part.getStock()) {
            JOptionPane.showMessageDialog(null, "Error: This part's stock must be between it's minimum and maximum.");
            return false;
        }
        if (isInHouse(part)) {
            InHouse inHousePart = (InHouse) part;
            if (inHousePart.getMachineID() < 0) {
                JOptionPane.showMessageDialog(null, "Error: Enter valid machine ID.");
                return false;
            }
        if (!isInHouse(part)) {
            Outsourced outsourcedPart = (Outsourced) part;
                if (outsourcedPart.getCompanyName().equals("")) {
                    JOptionPane.showMessageDialog(null, "Error: Company must have a name.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * This method determines whether a product meets the criteria to be considered a valid product.
     * @param product
     * @return true if valid
     */
    public static boolean isValidProduct(Product product){
        //add together thr price of every part associated with the product
        double priceSum = 0;
        for (Part part : product.getAllAssociatedParts()) {
            priceSum += part.getPrice();
        }
        if(priceSum > product.getPrice()) {
            JOptionPane.showMessageDialog(null, "Error: Please enter a price higher than the sum of it's associated parts.");
            return false;
        }
        if (product.getName().equals("")) {
            JOptionPane.showMessageDialog(null, "Error: The product must have a name.");
            return false;
        }
        if (product.getPrice() < 0) {
            JOptionPane.showMessageDialog(null, "Error: The product must cost more than $0.00");
            return false;
        }
        if (product.getStock() < 0) {
            JOptionPane.showMessageDialog(null, "Error: There must be more than one of this product in stock.\"");
            return false;
        }
        if (product.getMin() > product.getMax()) {
            JOptionPane.showMessageDialog(null, "Error: The maximum must be greater than the minimum.");
            return false;
        }
        if (product.getMin() > product.getStock() || product.getMax() < product.getStock()) {
            JOptionPane.showMessageDialog(null, "Error: The inventory level of this product must be between it's minimum and maximum.");
            return false;
        }
        return true;
    }


    /**
     * set selectedProduct variable to the product currently being selected in the product table view
     * @param product
     */
    public static void setSelectedProduct(Product product){

        MainController.selectedProduct = product;
    }

    /**
     * return selected product
     * @return
     */
    public static Product getSelectedProduct() {

        return selectedProduct;
    }

    /**
     * keep track of the index of the selected product in the product table view
     */
    public static void setSelectedProductIndex() {
        selectedProductIndex = Inventory.getAllProducts().indexOf(getSelectedProduct());
    }

    /**
     * return index of selected product in Inventory's product observable list
     * @return
     */
    public static int getSelectedProductIndex() {
        return selectedProductIndex;
    }

    /**
     * add table columns to tableviews and populate with data from part and product observable list.
     * @param url
     * @param resources
     */
    @Override
    public void initialize(URL url, ResourceBundle resources) {


        //initialize part table columns
        TableColumn partIDTableColumn = new TableColumn("Part ID");
        TableColumn partNameTableColumn = new TableColumn("Part Name");;
        TableColumn partInvLevelTableColumn = new TableColumn("Inventory Label");;
        TableColumn partPriceTableColumn = new TableColumn("Price/Cost per Unit");;
        partTableView.getColumns().addAll(partIDTableColumn, partNameTableColumn, partInvLevelTableColumn, partPriceTableColumn);
        //initialize product table columns
        TableColumn prodIDTableColumn = new TableColumn("Product ID");
        TableColumn prodNameTableColumn = new TableColumn("Part Name");;
        TableColumn prodInvLevelTableColumn = new TableColumn("Inventory Label");;
        TableColumn prodPriceTableColumn = new TableColumn("Price/Cost per Unit");;
        prodTableView.getColumns().addAll(prodIDTableColumn, prodNameTableColumn, prodInvLevelTableColumn, prodPriceTableColumn);

        //associate part data with table
        partIDTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        partNameTableColumn.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        partInvLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        partPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        //associate product data with table
        prodIDTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("id"));
        prodNameTableColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name"));
        prodInvLevelTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("stock"));
        prodPriceTableColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("price"));
        //add data to tables
        partTableView.setItems(Inventory.getAllParts());
        prodTableView.setItems(Inventory.getAllProducts());
    }

    /**
     * When this method is called it will change to the add part screen.
     * @param actionEvent Add part button is pushed
     * @throws IOException
     */
    public void changeScreenAddPartHandler(javafx.event.ActionEvent actionEvent) throws IOException
    {
        Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
        Scene addPartScene = new Scene(addPartParent);
        //This line gets the stage information
        Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        window.setScene(addPartScene);
        window.show();
    }

    /**
     * When this method is called it will change to the modify part screen if a part is selected from the parts table view.
     * @param actionEvent modify part button is pushed
     * @throws IOException
     */
    public void changeScreenModifyPartHandler(javafx.event.ActionEvent actionEvent) throws IOException
    {
        //send data to modify part screen

        setSelectedPart(partTableView.getSelectionModel().getSelectedItem());
        setSelectedPartIndex();
        if (selectedPart != null)
        {
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartScene = new Scene(modifyPartParent);
            //This line gets the stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(modifyPartScene);
            window.show();
        }
        else
            JOptionPane.showMessageDialog(null, "Error: Select a part to modify");

    }

    /**
     * When this method is called it will change to the add product screen.
     * @param actionEvent add product button is pushed
     * @throws IOException
     */
    public void changeScreenAddProductHandler(javafx.event.ActionEvent actionEvent) throws IOException
    {
        Parent addProductParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene addProductScene = new Scene(addProductParent);
        //This line gets the stage information
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        window.setScene(addProductScene);
        window.show();

    }

    /**
     * When this method is called it will change to the modify product screen if a product is selected from the product
     * table view.
     * @param actionEvent modify product button is pushed
     * @throws IOException
     */
    public void changeScreenModifyProductHandler(javafx.event.ActionEvent actionEvent) throws IOException
    {
        setSelectedProduct(prodTableView.getSelectionModel().getSelectedItem());
        setSelectedProductIndex();
        if (selectedProduct != null)
        {
            //show modify part screen
            Parent modifyProductParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modifyProductScene = new Scene(modifyProductParent);
            //This line gets the stage information
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            window.setScene(modifyProductScene);
            window.show();
            //send selected part to modify part screen
        } else
            JOptionPane.showMessageDialog(null, "Error: please select a product to modify");

    }


    /**
     * delete part from inventory if a part from the table view is selected.
     * @param actionEvent delete part button is pushed
     * @throws IOException
     */
    public void deleteSelectedPartHandler(javafx.event.ActionEvent actionEvent) throws  IOException
    {
        selectedPart = partTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null)
        {
            int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete the selected part?", "Delete Part",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
                    if (dialogButton == JOptionPane.YES_OPTION)
                    {
                        Inventory.deletePart(partTableView.getSelectionModel().getSelectedItem());
                        partTableView.setItems(Inventory.getAllParts());
                        selectedPart = null;
                    }
        }
        else
            {
            JOptionPane.showMessageDialog(null, "Please select a part to delete.");
        }
    }
    /**
     * delete product from inventory if a product from the table view is selected.
     * @param actionEvent delete product button is pushed
     * @throws IOException
     */
    public void deleteSelectedProductHandler(javafx.event.ActionEvent actionEvent) throws IOException
    {
        selectedProduct = prodTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null)
        {
            int dialogButton = JOptionPane.showConfirmDialog(null, "Are you sure you would like to delete the selected Product?", "Delete Product",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);
            if (dialogButton == JOptionPane.YES_OPTION)
            {
                Inventory.deleteProduct(prodTableView.getSelectionModel().getSelectedItem());
                prodTableView.setItems(Inventory.getAllProducts());
                selectedProduct = null;
            }

        }
        else
            {
            JOptionPane.showMessageDialog(null, "Please select a product to delete.");
        }
    }

    /**
     * Remove default text from the text field.
     * @param mouseEvent part search text field is clicked
     */
    public void partSearchBoxClearHandler(MouseEvent mouseEvent)
    {
        partTextField.setText("");
        partTextField.setOpacity(1);
    }

    /**
     * Check if there is a part with a matching name or id.
     * @param keyEvent the user presses the enter key
     */
    public void getPartsResultsHandler(javafx.scene.input.KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            String q = partTextField.getText();

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
            partTextField.setText("");
        }
    }

    /**
     * Remove default text from the text field.
     * @param mouseEvent product search text field is clicked
     */
    public void productSearchBoxClearHandler(MouseEvent mouseEvent)
    {
        prodTextField.setText("");
        prodTextField.setOpacity(1);
    }

    /**
     * Check if there is a product with a matching name or id.
     * @param keyEvent the user presses the enter key
     */
    public void getProductsResultsHandler(javafx.scene.input.KeyEvent keyEvent)
    {
        if (keyEvent.getCode() == KeyCode.ENTER)
        {
            String q = prodTextField.getText();

            ObservableList<Product> products = Inventory.lookupProduct(q);

            if(products.size() == 0)
            {
                try
                {
                    int id = Integer.parseInt(q);
                    Product p = Inventory.lookupProduct(id);
                    if (p != null)
                        products.add(p);
                }
                catch (NumberFormatException e)
                {
                    //ignore
                }
            }

            if (products.size() == 0)
            {
                JOptionPane.showMessageDialog(null, "No products found.");
                prodTableView.setItems(Inventory.getAllProducts());
            }
            else
            {
                prodTableView.setItems(products);
            }
            prodTextField.setText("");
        }
    }

    /**
     * Exit the application.
     * @param e
     */
    public void exitButtonHandler(javafx.event.ActionEvent e)
    {
        Platform.exit();
        System.exit(0);
    }
}