package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**FUTURE ENHANCEMENT - To improve upon this project one could combine the modify and add screen for parts and products in
 * to two fxml files rather than four. Also, to increase the usability of the application, one could add tooltips that popup
 * whenever the user hovers over a control in a window.
 *
 * This class creates an application that lets the user manage an inventory of parts and products.
 * */
public class Main extends Application {
    /**
     * This method loads the Main stage.
     * @param primaryStage The main stage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root;
        root = FXMLLoader.load(getClass().getResource("/sample/View_Controller/Main.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();

    }

    /** This is the main method. This is the first method that gets called when you run your java program. */
    public static void main(String[] args) {
        launch(args);
    }
}
