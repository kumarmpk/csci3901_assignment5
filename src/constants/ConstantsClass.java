package constants;

//one class to store all constants of the program

public class ConstantsClass {

    //constants
    public static final String CUSTOMER = "customer";
    public static final String CATEGORY = "category";
    public static final String SUPPLIER = "supplier";
    public static final String PRODUCT = "product";
    public static final String ADDRESS = "address";

    //to store the getter method prefix
    public static final String GET_PREFIX = "get";

    //to create content in next line in xml stream
    public static final String LINE_SEPARATOR = "line.separator";

    //setter method prefix
    public static final String SET_PREFIX = "set";

    //customer class getter methods
    public static final String[] custGetterMthds = {"getCustomerName", "getAddress", "getNumOrders", "getOrderValue"};

    //category class getter methods
    public static final String[] catGetterMthds = {"getCategoryName", "getProductList"};

    //supplier class getter methods
    public static final String[] suppGetterMthds = {"getSupplierName", "getAddress", "getNumProducts", "getProductValue"};

    //address class getter methods
    public static final String[] addGetterMthds = {"getStreetAddress", "getCity", "getRegion", "getPostalCode", "getCountry"};

    //product class getter methods
    public static final String[] prodGetterMthds = {"getProductName", "getSupplierName", "getUnitsSold", "getSaleValue"};

    //xml tags
    public static final String yearEndSummary = "year_end_summary";
    public static final String year = "year";
    public static final String startDate = "start_date";
    public static final String endDate = "end_date";
    public static final String custoemrList = "customer_list";
    public static final String productList = "product_list";
    public static final String supplierList = "supplier_list";

    //xml formatting assistors
    public static final String threeSpace = "   ";
    public static final String sixSpace = "      ";
    public static final String nineSpace = "         ";
    public static final String twelveSpace = "            ";

}
