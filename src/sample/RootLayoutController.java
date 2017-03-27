package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by staho on 27.03.2017.
 */
public class RootLayoutController {

    private Main main;

    public void setMain(Main main){
        this.main = main;
    }

    @FXML
    private void handleNew(){
        main.getProductData().clear();
        main.setInventoryFilePath(null);
    }

    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();

        // Setting default file extension
        FileChooser.ExtensionFilter extensionFil = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");

        fileChooser.getExtensionFilters().add(extensionFil);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(main.getPrimaryStage());

        if (file != null) {
            main.loadProductDataFromFile(file);
        }
    }

    @FXML
    private void handleSave() {
        File inventoryFile = main.getInventoryFilePath();
        if (inventoryFile != null) {
            main.saveProductDataToFile(inventoryFile);
        } else {
            handleSaveAs();
        }
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "XML files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(main.getPrimaryStage());

        if (file != null) {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".xml")) {
                file = new File(file.getPath() + ".xml");
            }
            main.saveProductDataToFile(file);
        }
    }

    @FXML
    private void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inventory");
        alert.setHeaderText("About");
        alert.setContentText("Author: Kamil Stachowicz\nWebsite: http://facebook.com/staho688");

        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }

}
