package db;

//class to build the queries

public class Queries {

    //first static part to fetch customer details
    private String customerQueryPart1 = "select cust.companyname as customerName, cust.address as streetAddress, cust.city, cust.region, "
            .concat("cust.postalCode, cust.country, count(distinct ord.orderid) as numOrders, ")
            .concat("round(sum(dtls.unitprice * dtls.quantity), 2) as orderValue ")
            .concat("from customers cust, orders ord, orderdetails dtls ")
            .concat("where cust.customerid = ord.customerid and ord.orderdate between '");

    //second static part to fetch customer details
    private String customerQueryPart2 = "' and '";

    //last static part to fetch customer details
    private String customerQueryPart3 = "' and ord.orderid = dtls.orderid group by ord.customerid ;";

    //first static part to fetch product details
    private String productQueryPart1 = "select cat.categoryName, prod.productName, supp.companyname as supplierName, "
            .concat("sum(dtls.Quantity) as unitsSold, round(sum(dtls.Quantity * dtls.UnitPrice), 2) as saleValue ")
            .concat("from products prod, suppliers supp, categories cat, orders ord, orderdetails dtls ")
            .concat("where prod.SupplierID = supp.SupplierID and prod.CategoryID = cat.CategoryID ")
            .concat("and ord.orderid = dtls.orderid and dtls.ProductID = prod.productid ")
            .concat("and ord.orderdate between '");

    //second static part to fetch product details
    private String productQueryPart2 = "' and '";

    //last static part to fetch product details
    private String productQueryPart3 = "' group by dtls.productid order by prod.categoryid, prod.productid;";

    //first static part to fetch supplier details
    private String supplierQueryPart1 = "select supp.companyname as supplierName, supp.address as streetAddress, "
            .concat(" supp.city, supp.region, supp.postalCode, supp.country, sum(dtls.quantity) as numProducts, ")
            .concat("round(sum(dtls.unitprice * dtls.quantity), 2) as productValue ")
            .concat("From suppliers supp, products prod, orderdetails dtls, orders ord ")
            .concat("where supp.supplierid = prod.supplierid and prod.productid = dtls.productid ")
            .concat("and dtls.orderid = ord.orderid and ord.orderdate between '");

    //second static part to fetch supplier details
    private String supplierQueryPart2 = "' and '";

    //last static part to fetch supplier details
    private String supplierQueryPart3 = "' group by prod.supplierid;";

    /*
    buildCustomerQuery method
    gets the date range as input
    forms the query as string and returns the query
     */
    public String buildCustomerQuery(String start, String end){
        String query = null;

        query = customerQueryPart1.concat(start)
                .concat(customerQueryPart2).concat(end).concat(customerQueryPart3);

        return query;
    }

    /*
    buildProductQuery method
    gets the date range as input
    forms the query as string and returns the query
    */
    public String buildProductQuery(String start, String end){
        String query = null;

        query = productQueryPart1.concat(start)
                .concat(productQueryPart2).concat(end).concat(productQueryPart3);

        return query;
    }

    /*
    buildSupplierQuery method
    gets the date range as input
    forms the query as string and returns the query
    */
    public String buildSupplierQuery(String start, String end){
        String query = null;

        query = supplierQueryPart1.concat(start)
                .concat(supplierQueryPart2).concat(end).concat(supplierQueryPart3);

        return query;
    }

}
