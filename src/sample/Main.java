package sample;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Inwentarz");
        primaryStage.getIcons().add(new Image("http://icons.iconarchive.com/icons/chrisl21/minecraft/256/Chest-icon.png"));

        initRootLayout();

        showShopInventory();
    }

    //this function loads just the root view
    public void initRootLayout(){
        try{
            //Loading from fxml file
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("RootLayout.fxml"));
            rootLayout = loader.load();

            //Showing scene containing the root layout
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    //this function loads the inventory view
    public void showShopInventory(){
        try{
            //Loading overview from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            GridPane shopInventory = loader.load();

            //Setting shop overview in center of root layout
            rootLayout.setCenter(shopInventory);

            //giving the controller access to the main
            Controller controller = loader.getController();
            controller.setMain(this);
        }
        catch (IOException e){

        }
    }

    public Stage getPrimaryStage(){
        return primaryStage;
    }

    public boolean showProductEditDialog(Product product){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ProductEditDialog.fxml"));
            AnchorPane page = loader.load();

            //create the dialog stage
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit product");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            //set the person into the controller
            ProductEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setProduct( product);

            //showing dialog and waiting to close it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        }catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    private ObservableList<Product> productData = FXCollections.observableArrayList();

    public Main(){
        productData.add(new Product("Kluski", 15, ProductType.FOOD, true));
        productData.add(new Product("Piwo", 24, ProductType.DRINK, true));
        productData.add(new Product("Paracetamol", 5, ProductType.DRUG, false));
        productData.add(new Product("Banan", 10, ProductType.FOOD, false));
        productData.add(new Product("Jogurt", 40, ProductType.FOOD, true));
        productData.add(new Product("Sok Trymbark", 40, ProductType.DRINK, false));
        productData.add(new Product("Lody Grymcan", 20, ProductType.FOOD, true));
        productData.add(new Product("Lizaki ChukaChups", 70, ProductType.SWEETS, false));
        productData.add(new Product("Woda", 30, ProductType.DRINK, false));

    }
    public ObservableList<Product> getProductData(){
        return productData;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
