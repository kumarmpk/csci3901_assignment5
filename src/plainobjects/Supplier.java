package plainobjects;
//stores the supplier information

import plainobjects.Address;

public class Supplier {

    //supplier name
    private String supplierName;

    //supplier address
    private Address address;

    //number of products supplied by the supplier
    private String numProducts;

    //total cost of the products supplied by the supplier
    private String productValue;

    /*
    getSupplierName method
    returns the supplier name of this object
     */
    public String getSupplierName() {
        return supplierName;
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
    getAddress method
    returns the address object of this object
     */
    public Address getAddress() {
        return address;
    }

    /*
    setAddress method
    gets the address object as input
    sets the input into the address of this object
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /*
    getNumProducts method
    returns the number of products supplied of this object
     */
    public String getNumProducts() {
        return this.numProducts;
    }

    /*
    setNumProducts method
    gets the number of products supplied as input
    sets the input into the number of products supplied of this object
     */
    public void setNumProducts(String numProducts) {
        this.numProducts = numProducts;
    }

    /*
    getProductValue method
    returns the total cost of the products supplied of this object
     */
    public String getProductValue() {
        return productValue;
    }

    /*
    setProductValue method
    gets the total cost of the products supplied as input
    sets the input into the total cost of the products of this object
     */
    public void setProductValue(String productValue) {
        this.productValue = productValue;
    }

}
