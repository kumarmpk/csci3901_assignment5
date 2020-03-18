import java.util.ArrayList;
import java.util.List;

//stores all the category information in this object

public class Category {

    //category name of the product
    private String categoryName;

    //list of products in the category
    private List<Product> productList;

    /*
    getCategoryName method
    returns the category name of this object
     */
    public String getCategoryName() {
        return this.categoryName;
    }

    /*
    setCategoryName method
    gets the category name as input
    sets the input into the category name of this object
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /*
    getProductList method
    returns the list of products of this object
     */
    public List<Product> getProductList() {
        return this.productList;
    }

    /*
    setProductList method
    gets the list of products as input
    sets the input into the list of products of this object
     */
    public void setProductList(ArrayList productList) {
        this.productList = productList;
    }
}
