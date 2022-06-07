package sample.Model;
/**
 *
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;

/**
 * This class manages the list of all the products and parts.
 */
public class Inventory
{
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partCount = 0;
    private static int productCount = 0;

    /**
     * add part to the observablelist.
     * @param newPart
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * add product to the observablelist.
     * @param newProduct
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Find parts matching the string of characters from the query.
     * @param partName query
     * @return observable list of parts with matching names
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList<Part> namedParts = FXCollections.observableArrayList();
        for (Part p: allParts)
        {
            if (p.getName().contains(partName))
            {
                namedParts.add(p);
            }

        }
        return namedParts;
    }

    /**
     * find a part that has the same id as the query.
     * @param id query
     * @return part with matching id, null of none
     */
    public static Part lookupPart(int id)
    {
        for (int i = 0; i < allParts.size(); i++)
        {
            Part p = allParts.get(i);

            if (p.getId() == id)
                return p;
        }
        return null;
    }

    /**
     * find products with names matching the string of characters from the query.
     * @param productName query
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        {
            ObservableList<Product> namedProducts = FXCollections.observableArrayList();
            for (Product p : allProducts)
            {
                if (p.getName().contains(productName))
                {
                    namedProducts.add(p);
                }

            }
            return namedProducts;
        }
    }

    /**
     * find product with an id matching the query.
     * @param id query
     * @return product with matching id, null if none found
     */
    public static Product lookupProduct(int id)
    {
        for (int i = 0; i < allProducts.size(); i++)
        {
            Product p = allProducts.get(i);

            if (p.getId() == id)
                return p;
        }
        return null;

    }

    /**
     * update the list of all parts with the new updated part.
     * @param index of part
     * @param selectedPart part to be updated
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);

    }

    /**
     * update the list of products with the new updated product.
     * @param index of product
     * @param selectedProduct product to be updated
     */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index, selectedProduct);
    }



    /**
     * delete part from allparts list
     * @param selectedPart
     * @return true if found and removed
     */
    public static boolean deletePart(Part selectedPart)
    {
        if (allParts.contains(selectedPart))
        {
            allParts.remove(selectedPart);
            return true;
        }
        else
            return false;
    }

    /**
     * delete part from allproducts list
     * @param selectedProduct
     * @return true if found and removed
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        if (allProducts.contains(selectedProduct))
        {
            if (selectedProduct.getAllAssociatedParts().size() == 0)
            {
                allProducts.remove(selectedProduct);
                return true;
            }
            else
                {
                JOptionPane.showMessageDialog(null, "Error: Product may only be deleted if there " +
                        "are no parts associated with it.");
                return false;
            }
        }

        else
            return false;
    }

    /**
     * return list of allparts
     * @return
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * return all products
     * @return
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    /**
     * part ids will be generated simply by assigning partCount to the part's id, IDs will be unique because the int is
     *  increased every time a new part is created.
     * @return id
     */
    public static int generatePartID()
    {
        partCount++;
        return partCount;
    }

    /**
     * product ids will be generated simply by assigning productCount to the product's id, IDs will be unique because the int is
     *  increased every time a new product is created.
     * @return id
     */
    public static int generateProductID()
    {
        productCount++;
        return productCount;
    }

}
