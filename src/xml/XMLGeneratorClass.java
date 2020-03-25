package xml;

import constants.ConstantsClass;
import plainobjects.Address;
import plainobjects.Customer;
import plainobjects.Category;
import plainobjects.Supplier;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;

//Class to generate the XML content

public class XMLGeneratorClass implements XMLGenerator{

    /*
    printString method
    gets string as input
    prints the input to user
    */
    public void printString(String input){
        System.out.println(input);
    }

    /*
    prepareXML method
    gets the complete data and date range as inputs
    using stax parser convert the data into XML
     */
    public String prepareXML(List customerList, List categoryList, List supplierList,
                             String startDate, String endDate) throws Exception{

        String xmlString = null;
        try {
            //string write is used to create the string
            StringWriter stringWriter = new StringWriter();

            //xml output factory to write the content in XML form
            XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
            xmlOutputFactory.setProperty("escapeCharacters", false);

            //xml data is streamed to output factory
            XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(stringWriter);

            xmlStreamWriter.writeStartDocument("UTF-8","1.0");
            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));

            //initial output from input
            xmlStreamWriter.writeStartElement(ConstantsClass.yearEndSummary);
            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
            xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
            xmlStreamWriter.writeStartElement(ConstantsClass.year);
            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));

            xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
            xmlStreamWriter.writeStartElement(ConstantsClass.startDate);
            xmlStreamWriter.writeCharacters(startDate);
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
            xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
            xmlStreamWriter.writeStartElement(ConstantsClass.endDate);
            xmlStreamWriter.writeCharacters(endDate);
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
            xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
            xmlStreamWriter.writeEndElement();

            //customer section of XML
            if(customerList != null && !customerList.isEmpty()){
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.custoemrList);
                formXMLFromObject(xmlStreamWriter, customerList);
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeEndElement();
            } else {
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.custoemrList);
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.CUSTOMER);
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeEndElement();
            }

            //product section of the XML
            if(categoryList != null && !categoryList.isEmpty()){
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.productList);
                formXMLFromObject(xmlStreamWriter, categoryList);
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeEndElement();
            } else {
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.productList);
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.CATEGORY);
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeEndElement();
            }

            //supplier section of the XML
            if(supplierList != null && !supplierList.isEmpty()){
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.supplierList);
                formXMLFromObject(xmlStreamWriter, supplierList);
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeEndElement();
            } else {
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.supplierList);
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.SUPPLIER);
                xmlStreamWriter.writeEndElement();
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.threeSpace);
                xmlStreamWriter.writeEndElement();
            }

            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.flush();
            xmlStreamWriter.close();

            xmlString = stringWriter.getBuffer().toString();



            stringWriter.close();

        } catch (XMLStreamException e){
            printString("System faced XMLStreamException exception while creating XML file.");
            throw e;
        } catch (Exception e){
            printString("System faced unexpected exception while creating XML file.");
            throw e;
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

            //gets the array of all the method of the class
            String[] clsMethods = null;

            if(obj instanceof Customer){
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.CUSTOMER);
                clsMethods = ConstantsClass.custGetterMthds;
            } else if(obj instanceof Category){
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.CATEGORY);
                clsMethods = ConstantsClass.catGetterMthds;
            } else if(obj instanceof Supplier){
                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
                xmlStreamWriter.writeStartElement(ConstantsClass.SUPPLIER);
                clsMethods = ConstantsClass.suppGetterMthds;
            }

            //number of methods of the class
            int clsMethodsCount = clsMethods.length;

            //class array
            Class[] clz = new Class[0];

            //looping all the methods
            for(int i = 0; i < clsMethodsCount; i++) {

                String methodName = clsMethods[i];

                //looping only the getter methods except address
                if (methodName.startsWith(ConstantsClass.GET_PREFIX) && !methodName.equals("getClass")
                    && !methodName.equals("getAddress") && !methodName.equals("getProductList")) {

                    xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                    xmlStreamWriter.writeCharacters(ConstantsClass.nineSpace);
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

                //to create the product related information in the xml format
                else if (methodName.startsWith(ConstantsClass.GET_PREFIX) && methodName.equals("getProductList")){

                    //product list details
                    Method getProductList = getMethod(obj, methodName, clz);

                    //list object
                    List productList = (List) getProductList.invoke(obj, new Object[0]);

                    //list is looped
                    for(Object prod : productList) {
                        xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                        xmlStreamWriter.writeCharacters(ConstantsClass.nineSpace);
                        xmlStreamWriter.writeStartElement(ConstantsClass.PRODUCT);

                        //array of product methods
                        String[] prodClsMethods = ConstantsClass.prodGetterMthds;

                        //number of methods in product class
                        int prodClsMethodsCount = prodClsMethods.length;

                        //class array
                        Class[] prodClzArray = new Class[0];

                        //looping all methods of product object
                        for (int j = 0; j < prodClsMethodsCount; j++) {

                            String prodMethodName = prodClsMethods[j];

                            if (prodMethodName.startsWith(ConstantsClass.GET_PREFIX) && !prodMethodName.equals("getClass")) {

                                xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                                xmlStreamWriter.writeCharacters(ConstantsClass.twelveSpace);
                                //start tag
                                xmlStreamWriter.writeStartElement(getPropertyNameFromMethod(prodMethodName));

                                //get the details of getter method
                                Method m = getMethod(prod, prodMethodName, prodClzArray);

                                //get the value
                                String value = (String) m.invoke(prod, new Object[0]);
                                if (value != null) {
                                    //value is not null write to XML
                                    xmlStreamWriter.writeCharacters(value);
                                }
                                xmlStreamWriter.writeEndElement();
                            }
                        }
                        xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                        xmlStreamWriter.writeCharacters(ConstantsClass.nineSpace);
                        xmlStreamWriter.writeEndElement();
                    }
                }
                //to create the address related information in the xml format
                else if (methodName.startsWith(ConstantsClass.GET_PREFIX) && methodName.equals("getAddress")){

                    xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                    xmlStreamWriter.writeCharacters(ConstantsClass.nineSpace);
                    xmlStreamWriter.writeStartElement(ConstantsClass.ADDRESS);

                    //getAddress details
                    Method getAddress = getMethod(obj, methodName, clz);

                    //address object
                    Address add = (Address) getAddress.invoke(obj, new Object[0]);

                    //array of address methods
                    String[] addClsMethods = ConstantsClass.addGetterMthds;

                    //number of methods in address class
                    int addClsMethodsCount = addClsMethods.length;

                    //class array
                    Class[] addClzArray = new Class[0];

                    //looping all methods of address object
                    for(int j = 0; j < addClsMethodsCount; j++) {

                        String addMethodName = addClsMethods[j];

                        if(addMethodName.startsWith(ConstantsClass.GET_PREFIX) && !addMethodName.equals("getClass")) {

                            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                            xmlStreamWriter.writeCharacters(ConstantsClass.twelveSpace);
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
                    xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
                    xmlStreamWriter.writeCharacters(ConstantsClass.nineSpace);
                    xmlStreamWriter.writeEndElement();
                }
            }
            xmlStreamWriter.writeCharacters(System.getProperty(ConstantsClass.LINE_SEPARATOR));
            xmlStreamWriter.writeCharacters(ConstantsClass.sixSpace);
            xmlStreamWriter.writeEndElement();
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

        //first letter is lower cased
        fieldName = fieldName.concat(String.valueOf(methodName.charAt(3)).toLowerCase());

        //converted into array of characters
        char[] methodNameArray = methodName.toCharArray();

        //looped from the 5th character
        for(int i=4; i<methodNameArray.length; i++){
            char currentChar = methodNameArray[i];
            if(Character.isUpperCase(currentChar)){
                fieldName = fieldName.concat("_").concat(String.valueOf(currentChar).toLowerCase());
            } else {
                fieldName = fieldName.concat(String.valueOf(currentChar));
            }
        }

        return fieldName;
    }

}
