import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

//handles the database section of the program

public class DBHandler {

    private String CUSTOMER = "customer";
    private String PRODUCT = "product";
    private String SUPPLIER = "supplier";
    private String SET_PREFIX = "set";

    /*
    getConnect method
    creates a database connection
    returns the created connection
     */
    public Map getData(String start, String end) throws Exception{

        //the link to the database
        Connection connect = null;

        //a place to build up an SQL query
        Statement statement = null;

        //a data structure to receive results from an SQL query
        ResultSet resultSet = null;

        //Using a properties structure, just to hide info from other users.
        Properties identity = new Properties();

        //storing the db credentials and loading as properties
        ConnectionProperties connProp = new ConnectionProperties();

        Map<String, List> returnMap = new HashMap<>();

        String user;
        String password;
        String dbName;

        // Fill the properties structure with my information
        connProp.setIdentity(identity);
        user =identity.getProperty("user");
        password =identity.getProperty("password");
        dbName =identity.getProperty("database");

        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Setup the connection with the DB
            connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false", user, password);

            // Statements send queries to the database.
            statement = connect.createStatement();

            Queries query = new Queries();

            //fetches all customer information between the date ranges
            resultSet = statement.executeQuery(query.buildCustomerQuery(start, end));
            List customerList = convertDataIntoObj(resultSet, CUSTOMER);

            //fetches all product information between the date ranges
            resultSet = statement.executeQuery(query.buildQuery2(start, end));
            List productList = convertDataIntoObj(resultSet, PRODUCT);

            //fetches all supplier information between the date ranges
            resultSet = statement.executeQuery(query.buildSupplierQuery(start, end));
            List supplierList = convertDataIntoObj(resultSet, SUPPLIER);

            //creates map with string as key and list as value
            returnMap.put(CUSTOMER, customerList);
            returnMap.put(PRODUCT, productList);
            returnMap.put(SUPPLIER, supplierList);

        } catch (Exception e){
            //catches all exception and throws them back
            System.out.println("System faced exception while connecting to DB.");
            throw e;
        } finally {
            try{
                //closes the connection
                if(connect != null){
                    connect.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(resultSet != null){
                    resultSet.close();
                }
            } catch (Exception e){
                System.out.println("System faced unexpected exception in data fetching.");
                throw e;
            }
        }
        return returnMap;
    }

    /*
    convertDataIntoObj method
    gets the result set and object name as input
    creates the list from the data
     */
    private List convertDataIntoObj(ResultSet resultSet, String objectName){

        List returnList = new ArrayList();
        Class cls = null;

        //based on object name class is defined
        if(objectName.equals(CUSTOMER)){
            cls = Customer.class;
        } else if(objectName.equals(PRODUCT)){
            cls = Product.class;
        } else if(objectName.equals(SUPPLIER)){
            cls = Supplier.class;
        }

        //array of methods from class
        Method[] clsMethods = cls.getMethods();
        //number of methods
        int clsMethodsCount = clsMethods.length;

        try{
            //looping till the result set has value
            while(resultSet.next()){

                //looping for all methods
                for(int i=0; i<clsMethodsCount; i++ ) {

                    //method name
                    String methodName = clsMethods[i].getName();
                    //field name of the method
                    String fieldName = getFieldNameFromMethod(methodName);

                    //to set a value looping only the setter methods
                    if(methodName.startsWith(SET_PREFIX)) {
                        Object obj = null;
                        //based on the object name object is defined
                        if (objectName.equals(CUSTOMER)) {
                             obj = new Customer();
                        } else if (objectName.equals(PRODUCT)) {
                            obj = new Product();
                        } else if (objectName.equals(SUPPLIER)) {
                            obj = new Supplier();
                        }

                        //setAddress method has to be handled separately since it is itself an object
                        if(!methodName.equals("setAddress")) {
                            //getting the method details
                            Method method = getSetterMethodForField(obj,
                                    methodName, resultSet.getString(fieldName).getClass());
                            //setting the value into the object
                            method.invoke(obj, resultSet.getString(fieldName));
                        } else {
                            //address object details in the similar way
                            Address add = new Address();
                            Class addCls = Address.class;
                            Method[] addClsMethods = addCls.getMethods();
                            int addClsMethodsCount = addClsMethods.length;

                            //looping all address methods
                            for(int j=0; j<addClsMethodsCount; j++) {

                                String addMethodName = addClsMethods[i].getName();
                                String addFieldName = getFieldNameFromMethod(addMethodName);

                                //looping only setter methods
                                if (methodName.startsWith(SET_PREFIX)) {
                                    //getting the method details
                                    Method method = getSetterMethodForField(add,
                                            addMethodName, resultSet.getString(addFieldName).getClass());
                                    //setting the value into the object
                                    method.invoke(add, resultSet.getString(addFieldName));
                                }
                            }
                            //setting the address object into the main object
                            Method method = getSetterMethodForField(obj,
                                    methodName, resultSet.getString(fieldName).getClass());
                            method.invoke(obj, add);
                        }
                        //adding the object into the return object
                        returnList.add(obj);
                    }
                }
            }


        } catch (Exception e){
            System.out.println("System faced unexpected exception while fetching customer information.");
        }
        return returnList;
    }

    /*
    getSetterMethodForField method
    get all the method details
     */
    private static Method getSetterMethodForField(Object obj,
                                                 String fieldName, Class type) throws NoSuchMethodException {
        return obj.getClass().getDeclaredMethod(fieldName, type);
    }

    /*
    getFieldNameFromMethod method
    gets the method name
    returns the field name
     */
    private String getFieldNameFromMethod(String methodName){
        String fieldName = "";

        fieldName = fieldName.concat(String.valueOf(methodName.charAt(3)).toLowerCase()).concat(methodName.substring(4));

        return fieldName;
    }

    /*
    getCustomerList method
    gets the result set as input
    creates the customer list from the data
     */
    /*
    private List getCustomerList(ResultSet resultSet){

        List<Customer> customerList = new ArrayList<>();

        try{
            while(resultSet.next()){
                Customer cust = new Customer();
                cust.setCustomerName(resultSet.getString("companyname"));

                Address add = new Address();
                add.setStreetAddress(resultSet.getString("address"));
                add.setCity(resultSet.getString("city"));
                add.setCity(resultSet.getString("city"));
                add.setRegion(resultSet.getString("region"));
                add.setPostalCode(resultSet.getString("postalcode"));
                add.setCountry(resultSet.getString("country"));
                cust.setAddress(add);

                cust.setNumOrders(resultSet.getInt("numorders"));
                cust.setOrderValue(resultSet.getBigDecimal("value"));

                customerList.add(cust);
            }


        } catch (Exception e){
            System.out.println("System faced unexpected exception while fetching customer information.");
        }
        return customerList;
    }

    private List getProductList(ResultSet resultSet){

        List<Product> productList = new ArrayList<>();

        try{

            while(resultSet.next()){
                Product prod = new Product();
                prod.setCategoryId(resultSet.getString("CategoryID"));
                prod.setCategoryName(resultSet.getString("CategoryName"));
                prod.setProductId(resultSet.getString("ProductID"));
                prod.setProductName(resultSet.getString("ProductName"));
                prod.setSupplierId(resultSet.getString("SupplierID"));
                prod.setSupplierName(resultSet.getString("companyname"));
                prod.setUnitSold(resultSet.getString("unitsold"));
                prod.setSaleValue(resultSet.getString("value"));

                productList.add(prod);
            }


        } catch (Exception e){
            System.out.println("System faced unexpected exception while fetching product information.");
        }
        return productList;
    }


    private List getSupplierList(ResultSet resultSet){

        List<Supplier> supplierList = new ArrayList<>();

        try{
            while(resultSet.next()){
                Supplier supp = new Supplier();
                supp.setSupplierId(resultSet.getInt("supplierid"));
                supp.setSupplierName(resultSet.getString("companyname"));

                Address add = new Address();
                add.setStreetAddress(resultSet.getString("address"));
                add.setCity(resultSet.getString("city"));
                add.setRegion(resultSet.getString("region"));
                add.setPostalCode(resultSet.getString("postalcode"));
                add.setCountry(resultSet.getString("country"));
                supp.setAddress(add);

                supp.setNumProducts(resultSet.getInt("numOrders"));
                supp.setProductValue(resultSet.getBigDecimal("value"));

                supplierList.add(supp);
            }


        } catch (Exception e){
            System.out.println("System faced unexpected exception while fetching product information.");
        }
        return supplierList;
    }
    */


}
