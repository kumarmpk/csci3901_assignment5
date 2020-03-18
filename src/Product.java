//object to store all product information

public class Product {

    //stores the product name
    private String productName;

    //stores the supplier name of the product
    private String supplierName;

    //stores the number of products sold
    private String unitsSold;

    //stores the total cost of the products
    private String saleValue;

    /*
    getProductName method
    returns the product name of this object
     */
    public String getProductName() {
        return this.productName;
    }

    /*
    setProductName method
    gets the product name as input
    sets the input into the product name of this object
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*
    getSupplierName method
    returns the supplier name of this object
     */
    public String getSupplierName() {
        return this.supplierName;
    }

    /*
    setSupplierName method
    gets the supplier name as input
    sets the input into the supplier name of this object
     */
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /*
    getUnitsSold method
    returns the number of units sold of this object
     */
    public String getUnitsSold() {
        return this.unitsSold;
    }

    /*
    setUnitsSold method
    gets the number of units sold as input
    sets the input into the number of units of this object
     */
    public void setUnitsSold(String unitsSold) {
        this.unitsSold = unitsSold;
    }

    /*
    getSalesValue method
    returns the total cost of the products sold of this object
     */
    public String getSaleValue() {
        return this.saleValue;
    }

    /*
    setSalesValue method
    gets the total cost of the products sold as input
    sets the input into total cost of the products of this object
     */
    public void setSaleValue(String saleValue) {
        this.saleValue = saleValue;
    }
}
