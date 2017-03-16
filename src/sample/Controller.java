package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javax.swing.text.TabableView;

public class Controller {
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, String> nameColumn;


    @FXML
    private Label productTypeLabel;

    @FXML
    private Label productCountLabel;

    @FXML
    private Label productFridgeLabel;

    @FXML
    private void handleDeleteProduct(){
        int selectedIndex = productTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex >= 0)
            productTable.getItems().remove(selectedIndex);
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Nie zaznaczono produktu do usunięcia");
            alert.setHeaderText("Brak wyboru");
            alert.setContentText("Zaznacz osobe do usuniecia");

            alert.showAndWait();
        }
    }


    private Main main;

    public Controller(){

    }

    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        showProductDetails(null);

        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProductDetails(newValue));
    }

    public void setMain(Main main){
        this.main = main;

        productTable.setItems(main.getProductData());
    }

    private void showProductDetails(Product product){
        if(product != null){
            //filling labels with the product records
            productTypeLabel.setText(product.getProductType().toString());
            productCountLabel.setText(Integer.toString(product.getProductCount()));
            productFridgeLabel.setText(product.doesProductNeedsFridgePl());
        }
        else {
            productTypeLabel.setText("null");
            productCountLabel.setText("null");
            productFridgeLabel.setText("null");
        }
    }
}
