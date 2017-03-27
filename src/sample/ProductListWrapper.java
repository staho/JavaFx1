package sample;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by staho on 27.03.2017.
 */

@XmlRootElement(name = "items")
public class ProductListWrapper {

    private List<Product> products;

    @XmlElement(name = "item")
    public List<Product> getProducts(){
        return products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }
}
