package sample;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.prefs.Preferences;

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

            RootLayoutController controller = loader.getController();
            controller.setMain(this);

            primaryStage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

        // Try to load last opened person file.
        File file = getInventoryFilePath();
        if (file != null) {
            loadProductDataFromFile(file);
        }
    }
    //this function loads the inventory view
    public void showShopInventory(){
        try{
            //Loading overview from fxml
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            SplitPane shopInventory = loader.load();

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

    public File getInventoryFilePath(){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        String path = prefs.get("filePath", null);
        if(path != null){
            return new File(path);
        } else {
            return null;
        }
    }

    public void setInventoryFilePath(File file){
        Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if(file != null){
            prefs.put("filePath", file.getPath());

            primaryStage.setTitle("Inwentarz - " + file.getName());
        } else {
            prefs.remove("filePath");

            primaryStage.setTitle("Inwentarz");
        }
    }

    public void saveProductDataToFile(File file){
        try{
            JAXBContext context = JAXBContext.newInstance(ProductListWrapper.class);

            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping product data
            ProductListWrapper wrapper = new ProductListWrapper();
            wrapper.setProducts(productData);
            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);
            // Save the file path to the registry.
            setInventoryFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    public void loadProductDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(ProductListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            // Reading XML from the file and unmarshalling.
            ProductListWrapper wrapper = (ProductListWrapper) um.unmarshal(file);

            productData.clear();
            productData.addAll(wrapper.getProducts());

            // Save the file path to the registry.
            setInventoryFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

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
