package db;

import constants.ConnectionProperties;
import constants.ConstantsClass;
import plainobjects.Category;
import plainobjects.Address;
import plainobjects.Product;
import plainobjects.Supplier;
import plainobjects.Customer;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

//handles the database section of the program

public class DBHandlerClass implements DBHandler {

    /*
    printString method
    gets string as input
    prints the input to user
     */
    public void printString(String input){
        System.out.println(input);
    }


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

            //set the database name
            statement.executeQuery("use " + dbName + ";");

            Queries query = new Queries();

            //fetches all customer information between the date ranges
            resultSet = statement.executeQuery(query.buildCustomerQuery(start, end));
            List customerList = convertDataIntoObj(resultSet, ConstantsClass.CUSTOMER);

            //fetches all product information between the date ranges
            resultSet = statement.executeQuery(query.buildProductQuery(start, end));
            List categoryList = convertDataIntoObj(resultSet, ConstantsClass.CATEGORY);

            //fetches all supplier information between the date ranges
            resultSet = statement.executeQuery(query.buildSupplierQuery(start, end));
            List supplierList = convertDataIntoObj(resultSet, ConstantsClass.SUPPLIER);

            //creates map with string as key and list as value
            returnMap.put(ConstantsClass.CUSTOMER, customerList);
            returnMap.put(ConstantsClass.CATEGORY, categoryList);
            returnMap.put(ConstantsClass.SUPPLIER, supplierList);

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
                printString("System faced unexpected exception in data fetching.");
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
        String categoryName = null;
        String previousCategoryName = null;

        List productList = null;

        //based on object name class is defined
        if(objectName.equals(ConstantsClass.CUSTOMER)){
            cls = Customer.class;
        } else if(objectName.equals(ConstantsClass.CATEGORY)){
            cls = Category.class;
        } else if(objectName.equals(ConstantsClass.SUPPLIER)){
            cls = Supplier.class;
        }

        //array of methods from class
        Method[] clsMethods = cls.getMethods();
        //number of methods
        int clsMethodsCount = clsMethods.length;

        Object obj = null;

        try{
            //looping till the result set has value
            while(resultSet.next()){

                //based on the object name object is defined
                if (objectName.equals(ConstantsClass.CUSTOMER)) {
                    obj = new Customer();
                } else if (objectName.equals(ConstantsClass.CATEGORY)) {
                    categoryName = resultSet.getString("categoryName");
                    if(!categoryName.equals(previousCategoryName)) {
                        if(productList != null && !productList.isEmpty()) {
                            Method method = getSetterMethodForField(obj,
                                    "setProductList", productList.getClass());
                            method.invoke(obj, productList);
                            returnList.add(obj);
                        }
                        //obj = new Category();
                        obj = cls.newInstance();
                        productList = new ArrayList();
                    }
                } else if (objectName.equals(ConstantsClass.SUPPLIER)) {
                    obj = new Supplier();
                }

                String methodName = null;
                if(categoryName != null && categoryName.equals(previousCategoryName)) {
                    methodName = "setProductList";
                }

                //looping for all methods
                for(int i=0; i<clsMethodsCount; i++ ) {

                    if(methodName == null || !methodName.equals("setProductList")) {
                        //method name
                        methodName = clsMethods[i].getName();
                    }

                    //to set a value looping only the setter methods
                    if(methodName.startsWith(ConstantsClass.SET_PREFIX)) {

                        //setAddress method has to be handled separately since it is itself an object
                        if(!methodName.equals("setAddress") && !methodName.equals("setProductList")) {
                            //field name of the method
                            String fieldName = getFieldNameFromMethod(methodName);

                            String dbValue = resultSet.getString(fieldName);

                            if(fieldName.equals("categoryName")){
                                previousCategoryName = dbValue;
                            }

                            if(dbValue != null && !dbValue.isEmpty()) {
                                //getting the method details
                                Method method = getSetterMethodForField(obj,
                                        methodName, dbValue.getClass());
                                //setting the value into the object
                                method.invoke(obj, dbValue);
                            }
                        } else if(methodName.equals("setAddress")){
                            //address object details in the similar way
                            Address add = new Address();
                            Class addCls = Address.class;
                            Method[] addClsMethods = addCls.getMethods();
                            int addClsMethodsCount = addClsMethods.length;

                            //looping all address methods
                            for(int j=0; j<addClsMethodsCount; j++) {

                                String addMethodName = addClsMethods[j].getName();

                                //looping only setter methods
                                if (addMethodName.startsWith(ConstantsClass.SET_PREFIX)) {
                                    //field name of the method
                                    String addFieldName = getFieldNameFromMethod(addMethodName);

                                    String dbValue = resultSet.getString(addFieldName);

                                    if(dbValue != null && !dbValue.isEmpty()) {
                                        //getting the method details
                                        Method method = getSetterMethodForField(add,
                                                addMethodName, dbValue.getClass());
                                        //setting the value into the object
                                        method.invoke(add, dbValue);
                                    }
                                }
                            }
                            //setting the address object into the main object
                            Method method = getSetterMethodForField(obj,
                                    methodName, addCls);
                            method.invoke(obj, add);
                        } else if(methodName.equals("setProductList")){
                            Product prod = new Product();
                            Class prodCls = Product.class;
                            Method[] prodClsMethods = prodCls.getMethods();
                            int prodClsMethodsCount = prodClsMethods.length;

                            //looping all address methods
                            for(int j=0; j<prodClsMethodsCount; j++) {

                                String prodMethodName = prodClsMethods[j].getName();

                                //looping only setter methods
                                if (prodMethodName.startsWith(ConstantsClass.SET_PREFIX)) {
                                    //field name of the method
                                    String prodFieldName = getFieldNameFromMethod(prodMethodName);

                                    String dbValue = resultSet.getString(prodFieldName);

                                    if(dbValue != null && !dbValue.isEmpty()) {
                                        //getting the method details
                                        Method method = getSetterMethodForField(prod,
                                                prodMethodName, dbValue.getClass());
                                        //setting the value into the object
                                        method.invoke(prod, dbValue);
                                    }
                                }
                            }
                            productList.add(prod);
                            methodName = null;
                        }
                    }
                }
                //adding the object into the return object
                if(!objectName.equals(ConstantsClass.CATEGORY)) {
                    returnList.add(obj);
                }
            }

        } catch (Exception e){
            printString("System faced unexpected exception while fetching customer information.");
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

}
