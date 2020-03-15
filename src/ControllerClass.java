import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

//Controller class controls the flow of control and acts as the layer between
//user input class, database layer and xml forming class

public class ControllerClass {

    /*
    printString method
    gets string as input
    prints the input to user
     */
    private void printString(String input){
        System.out.println(input);
    }

    /*
    validate method
    gets the user inputted start date, end date and output file name
    validate the inputs
     */
    public boolean validate(String start, String end, String outputFileName) throws Exception{
        boolean isValid = true;
        try {
            //validates whether start date is valid string
            if (!stringValidator(start)) {
                printString("Start date is invalid.");
                isValid = false;
                return isValid;
            }

            //validates whether end date is valid string
            if (!stringValidator(end)) {
                printString("End date is invalid.");
                isValid = false;
                return isValid;
            }

            //validates whether output file name is valid string
            if (stringValidator(outputFileName)) {
                printString("output file name is invalid.");
                isValid = false;
                return isValid;
            }

            //check whether inputs are valid dates
            Date startDate = dateConverter(start);
            Date endDate = dateConverter(end);

        } catch (Exception e){
            isValid = false;
        }
        return isValid;
    }

    /*
    getData method
    gets the date range as inputs
    returns the data map
     */
    public Map getData(String start, String end) throws Exception{

        DBHandler dbHandler = new DBHandler();

        //fetching the data from database
        Map dataMap = dbHandler.getData(start, end);

        return dataMap;

    }

    /*
    formXMLString method
    gets the data map and date range as inputs
    form the XML as String
     */
    public String formXMLString(Map dataMap, String start, String end){
        String xmlContent = null;

        List customerList = (List) dataMap.get(ConstantsClass.CUSTOMER);
        List productList = (List) dataMap.get(ConstantsClass.PRODUCT);
        List supplierList = (List) dataMap.get(ConstantsClass.SUPPLIER);

        XMLData xmlData = new XMLData();
        //passing the list and date range to prepare the file content
        xmlContent = xmlData.prepareXML(customerList, productList, supplierList, start, end);

        return xmlContent;
    }

    /*
    stringValidator method
    validates the input is valid string or not
    returns boolean value
     */
    private boolean stringValidator(String inputString){
        boolean isValid = true;
        if(inputString == null || inputString.isEmpty()){
            isValid = false;
        }
        return isValid;
    }

    /*
    dateConverter method
    gets the string as input
    convert the string into date
    invalid date formats throws exception
     */
    private Date dateConverter(String dateString) throws Exception {
        Date returnDate = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false);

        try {
            //parsing the input to valid format
            returnDate = format.parse(dateString);
        } catch (ParseException e) {
            //if the input is invalid format then exception is thrown
            System.out.println("Inputted date is not in the expected format.");
            throw e;
        } catch (Exception e) {
            System.out.println("System faced unexpected exception in main method.");
            throw e;
        }
        return returnDate;
    }

    /*
    writeToOutputFile method
    gets the output file name and file content as input
    write the file and returns boolean value
     */
    public boolean writeToOutputFile(String fileName, String content){
        boolean isSuccess = true;

        File file = null;
        FileOutputStream fileOutputStream = null;
        try {
            //creates the file
            file = new File(fileName);

            //creates the output stream
            fileOutputStream = new FileOutputStream(file);

            //writes the content
            fileOutputStream.write(content.getBytes(), 0, content.length());
        } catch (Exception e) {
            //catches all exception
            isSuccess = false;
        }finally{
            try {
                //closing the stream
                fileOutputStream.close();
            } catch (Exception e) {
                isSuccess = false;
            }
        }

        return isSuccess;
    }


}
