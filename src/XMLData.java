import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

//Class to generate the XML content

public class XMLData {

    //to store the getter method prefix
    private String GET_PREFIX = "get";

    /*
    prepareXML method
    gets the complete data and date range as inputs
    using stax parser convert the data into XML
     */
    public String prepareXML(List customerList, List productList, List supplierList, String startDate, String endDate){
        String xmlString = null;
        try {
            //string write is used to create the string
            StringWriter stringWriter = new StringWriter();

            //xml output factory to write the content in XML form
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

            //xml data is streamed to output factory
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);

            xmlStreamWriter.writeStartDocument();

            //initial output from input
            xmlStreamWriter.writeStartElement("year_end_summary");
            xmlStreamWriter.writeStartElement("year");

            xmlStreamWriter.writeStartElement("start_date");
            xmlStreamWriter.writeCharacters(startDate);
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("end_date");
            xmlStreamWriter.writeCharacters(endDate);
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeEndElement();

            //customer section of XML
            if(customerList != null && !customerList.isEmpty()){
                xmlStreamWriter.writeStartElement("customer_list");
                formXMLFromObject(xmlStreamWriter, customerList);
                xmlStreamWriter.writeEndElement();
            } else {
                xmlStreamWriter.writeStartElement("customer_list");
                xmlStreamWriter.writeStartElement("customer");
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndElement();
            }

            //product section of the XML
            if(productList != null && !productList.isEmpty()){
                xmlStreamWriter.writeStartElement("product_list");
                formXMLFromObject(xmlStreamWriter, productList);
                xmlStreamWriter.writeEndElement();
            } else {
                xmlStreamWriter.writeStartElement("product_list");
                xmlStreamWriter.writeStartElement("category");
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeEndElement();
            }

            //supplier section of the XML
            if(supplierList != null && !supplierList.isEmpty()){
                xmlStreamWriter.writeStartElement("supplier_list");
                formXMLFromObject(xmlStreamWriter, supplierList);
                xmlStreamWriter.writeEndElement();
            } else {
                xmlStreamWriter.writeStartElement("supplier_list");
                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.flush();
            xmlStreamWriter.close();

            xmlString = stringWriter.getBuffer().toString();

            stringWriter.close();

        } catch (XMLStreamException e){
            System.out.println("System faced XMLStreamException exception while creating XML file.");
        } catch (Exception e){
            System.out.println("System faced unexpected exception while creating XML file.");
        }
        return xmlString;
    }

    /*
    formXMLFromObject method
    gets the xml stream writer and list as inputs
    forms the xml content of that particular list
     */
    private XMLStreamWriter formXMLFromObject(XMLStreamWriter xmlStreamWriter, List list) throws Exception{

        //looping the list
        for(Object obj : list){

            //get the class of the object passed
            Class cls = obj.getClass();

            //gets the array of all the method of the class
            Method[] clsMethods = cls.getMethods();

            //number of methods of the class
            int clsMethodsCount = clsMethods.length;

            //class array
            Class[] clz = new Class[0];

            //looping all the methods
            for(int i = 0; i < clsMethodsCount; i++) {

                String methodName = clsMethods[i].getName();

                //looping only the getter methods except address
                if (methodName.startsWith(GET_PREFIX) && !methodName.equals("getClass")
                    && !methodName.equals("getAddress")) {

                    //form the start tag
                    xmlStreamWriter.writeStartElement(getPropertyNameFromMethod(methodName));

                    //get the details of the getter method
                    Method m = getMethod(obj, methodName, clz);

                    //get the value of the field
                    String value = (String) m.invoke(obj, new Object[0]);
                    if(value != null){
                        //if value is not null put in xml
                        xmlStreamWriter.writeCharacters(value);
                    }
                    xmlStreamWriter.writeEndElement();
                }

                else if (methodName.startsWith(GET_PREFIX) && methodName.equals("getAddress")){

                    //address class
                    Class addCls = Address.class;

                    //getAddress details
                    Method getAddress = getMethod(obj, methodName, clz);

                    //address object
                    Address add = (Address) getAddress.invoke(obj, new Object[0]);

                    //array of address methods
                    Method[] addClsMethods = addCls.getMethods();

                    //number of methods in address class
                    int addClsMethodsCount = addClsMethods.length;

                    //class array
                    Class[] addClzArray = new Class[0];

                    //looping all methods of address object
                    for(int j = 0; j < addClsMethodsCount; j++) {

                        String addMethodName = addClsMethods[i].getName();

                        if(methodName.startsWith(GET_PREFIX) && !methodName.equals("getClass")) {

                            //start tag
                            xmlStreamWriter.writeStartElement(getPropertyNameFromMethod(addMethodName));

                            //get the details of getter method
                            Method m = getMethod(add, addMethodName, addClzArray);

                            //get the value
                            String value = (String) m.invoke(add, new Object[0]);
                            if (value != null) {
                                //value is not null write to XML
                                xmlStreamWriter.writeCharacters(value);
                            }
                            xmlStreamWriter.writeEndElement();
                        }
                    }
                }
            }
        }
        return xmlStreamWriter;
    }

    /*
    getMethod method
    gets the object, method name and class array as inputs
    using inbuild class method get the details of the method
     */
    private Method getMethod(Object o, String methodName,
                             Class[] paramTypes) throws NoSuchMethodException {
        Class clz = o.getClass();
        return clz.getMethod(methodName, paramTypes);
    }

    /*
    getPropertyNameFromMethod method
    gets the method name as input
    returns the field name
     */
    private static String getPropertyNameFromMethod(String methodName){
        String fieldName = "";

        fieldName = fieldName.concat(String.valueOf(methodName.charAt(3)).toLowerCase()).concat(methodName.substring(4));

        return fieldName;
    }

    /*
    private XMLStreamWriter formProductPart(XMLStreamWriter xmlStreamWriter, List<Product> productList) throws Exception{

        for(Product prod : productList){

            Class cls = prod.getClass();
            Method[] methods = cls.getMethods();
            int count = methods.length;
            Class[] clz = new Class[0];

            for(int i = 0; i < count; i++) {

                String methodName = methods[i].getName();

                if (methodName.startsWith(GET_PREFIX) && !methodName.equals("getClass")) {
                    xmlStreamWriter.writeStartElement(getPropertyNameFromMethod(methodName));
                    Method m = getMethod(prod, methodName, clz);
                    String value = (String) m.invoke(prod, new Object[0]);
                    if(value != null){
                        xmlStreamWriter.writeCharacters(value);
                    }
                    xmlStreamWriter.writeEndElement();
                }
            }

        }

        return xmlStreamWriter;
    }

    private XMLStreamWriter formCustomerPart(XMLStreamWriter xmlStreamWriter, List<Customer> customerList) throws XMLStreamException{

        for(Customer cust : customerList){
            xmlStreamWriter.writeStartElement("customer");

            xmlStreamWriter.writeStartElement("customer_name");
            xmlStreamWriter.writeCharacters(cust.getCustomerName());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("address");

            xmlStreamWriter.writeStartElement("street_address");
            xmlStreamWriter.writeCharacters(cust.getAddress().getStreetAddress());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("city");
            xmlStreamWriter.writeCharacters(cust.getAddress().getCity());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("region");
            xmlStreamWriter.writeCharacters(cust.getAddress().getRegion());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("postal_code");
            xmlStreamWriter.writeCharacters(cust.getAddress().getPostalCode());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("country");
            xmlStreamWriter.writeCharacters(cust.getAddress().getCountry());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("num_orders");
            xmlStreamWriter.writeCharacters(String.valueOf(cust.getNumOrders()));
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeStartElement("order_value");
            xmlStreamWriter.writeCharacters(String.valueOf(cust.getOrderValue()));
            xmlStreamWriter.writeEndElement();

        }

        return xmlStreamWriter;
    }
    */


}
