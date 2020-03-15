import java.math.BigDecimal;

//stores the customer details

public class Customer {

    //customer name
    private String customerName;

    //address object stores all the address details of the custoemr
    private Address address;

    //number of orders done by the customer
    private int numOrders;

    //total value of the orders of the customer
    private BigDecimal orderValue;

    /*
    getAddress method
    returns the address of this object
     */
    public Address getAddress() {
        return address;
    }

    /*
    setAddress method
    gets the address object as input
    sets the input into address of this object
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /*
    setCustoemrName method
    gets the customer name as input
    sets the input into customer name of this object
     */
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    /*
    getCustomerName method
    returns the customer name of this object
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /*
    getNumOrders method
    returns the number of order of this object
     */
    public int getNumOrders() {
        return this.numOrders;
    }

    /*
    setNumOrders method
    gets the number of orders as input
    sets the input into number of orders of this object
     */
    public void setNumOrders(int numOrders) {
        this.numOrders = numOrders;
    }

    /*
    getOrderValue method
    returns the total value of the orders of this object
     */
    public BigDecimal getOrderValue() {
        return this.orderValue;
    }

    /*
    setOrderValue method
    gets the total order of the customer as input
    sets the input into the total order of this object
     */
    public void setOrderValue(BigDecimal orderValue) {
        this.orderValue = orderValue;
    }


}
