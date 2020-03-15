//Object to store the address of Customer and Supplier

import java.lang.reflect.Method;

public class Address {

    //Street address of the address
    private String streetAddress;

    //City name of the address
    private String city;

    //Region of the address
    private String region;

    //Postal code of the address
    private String postalCode;

    //Country name of the address
    private String country;

    /*
    getStreetAddress method
    returns the streetAddress of this object
     */
    public String getStreetAddress() {
        return this.streetAddress;
    }

    /*
    setStreetAddress method
    gets the streetAddress as input
    sets the input into streetAddress of this object
     */
    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    /*
    getCity method
    returns the city name of this object
     */
    public String getCity() {
        return this.city;
    }

    /*
    setCity method
    gets the city name as input
    sets the input into city of this object
     */
    public void setCity(String city) {
        this.city = city;
    }

    /*
    getRegion  method
    returns the region name of this object
    */
    public String getRegion() {
        return this.region;
    }

    /*
    setRegion method
    gets the region name as input
    sets the input into region of this object
    */
    public void setRegion(String region) {
        this.region = region;
    }

    /*
    getPostalCode method
    returns the postal code of this object
    */
    public String getPostalCode() {
        return this.postalCode;
    }

    /*
    setPostalCode method
    gets the postal code as input
    sets the input into postal code of this object
    */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /*
    getCountry method
    returns the country name of this object
    */
    public String getCountry() {
        return this.country;
    }

    /*
    setCountry method
    gets the country name as input
    sets the input into country of this object
    */
    public void setCountry(String country) {
        this.country = country;
    }

}
