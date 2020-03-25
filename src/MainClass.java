import control.Controller;
import control.ControllerClass;

import java.util.Map;
import java.util.Scanner;

//main class of the program

public class MainClass {

    //main method of the program
    //starting point of the program
    public static void main (String[] args){

        //gets all the input details
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the starting date for the period to summarize:");
        String start = scan.next();

        System.out.println("Enter the ending date for the period to summarize:");
        String end = scan.next();

        System.out.println("enter the output file name.");
        String outputFileName = scan.next();

        //creating controller object
        Controller controller = new ControllerClass();

        try{
            //check whether the inputs are valid
            boolean isValid = controller.validateInput(start, end, outputFileName);

            if(isValid){
                //get data from database for the date range
                Map dataMap = controller.getData(start, end);

                //check data is available
                if(dataMap != null && !dataMap.isEmpty()){

                    //prepare the xml content with the data
                    String xmlContent = controller.formXMLString(dataMap, start, end);

                    if(xmlContent != null && !xmlContent.isEmpty()){
                        //writing to output file name
                        boolean isSuccess = controller.writeToOutputFile(outputFileName, xmlContent);
                        if(isSuccess){
                            System.out.println("Output file created successfully.");
                            boolean isValidXML = controller.validateXML(xmlContent);
                            if(isValidXML){
                                System.out.println("Output file is a valid xml.");
                            }
                        } else {
                            System.out.println("Output file creation faced some issue.");
                        }
                    }
                }
            } else{
                //input is invalid then program ends
                System.out.println("Input is invalid.");
            }

        }
        catch (Exception e){
            //catch all exceptions
            System.out.println("System faced unexpected exception in main method.");
        }


    }





}
