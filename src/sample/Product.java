package sample;

import javafx.beans.property.*;

/**
 * Created by staho on 16.03.2017.
 */
public class Product {
    private StringProperty name;
    private IntegerProperty productCount;
    private ObjectProperty<ProductType> productType;
    private BooleanProperty doesProductNeedsFridge;


    public Product() {
        this.name.set("Produkt domyslny");
        this.productCount.set(0);
        this.productType.set(ProductType.DEFAULT);
        this.doesProductNeedsFridge.set(false);
    }

    public Product(String name, int productCount, ProductType productType, boolean doesProductNeedsFridge) {
        this.name = new SimpleStringProperty(name);
        this.productCount = new SimpleIntegerProperty(productCount);
        this.productType = new SimpleObjectProperty<>(productType);
        this.doesProductNeedsFridge = new SimpleBooleanProperty(doesProductNeedsFridge);
    }

    public String doesProductNeedsFridgePl() {
         if(doesProductNeedsFridge.get())
             return "Tak";
         else
             return "Nie";
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getProductCount() {
        return productCount.get();
    }

    public IntegerProperty productCountProperty() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount.set(productCount);
    }

    public ProductType getProductType() {
        return productType.get();
    }

    public ObjectProperty<ProductType> productTypeProperty() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType.set(productType);
    }

    public boolean isDoesProductNeedsFridge() {
        return doesProductNeedsFridge.get();
    }

    public BooleanProperty doesProductNeedsFridgeProperty() {
        return doesProductNeedsFridge;
    }

    public void setDoesProductNeedsFridge(boolean doesProductNeedsFridge) {
        this.doesProductNeedsFridge.set(doesProductNeedsFridge);
    }


}
