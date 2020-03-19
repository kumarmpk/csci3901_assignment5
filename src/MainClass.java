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
        //String start = "1997-02-01";

        System.out.println("Enter the ending date for the period to summarize:");
        String end = scan.next();
        //String end = "1997-02-03";

        //System.out.println("enter the output file name.");
        String outputFileName = scan.next();
        //String outputFileName = "abc.xml";

        //Controller class controls the flow of the program
        Controller controller = new ControllerClass();

        try{
            //check whether the inputs are valid
            boolean isValid = controller.validate(start, end, outputFileName);

            if(isValid){
                //get data from database
                Map dataMap = controller.getData(start, end);

                //check data is valid
                if(dataMap != null && !dataMap.isEmpty()){

                    //prepare the xml content with the data
                    String xmlContent = controller.formXMLString(dataMap, start, end);

                    if(xmlContent != null && !xmlContent.isEmpty()){
                        //writing to output file name
                        boolean isSuccess = controller.writeToOutputFile(outputFileName, xmlContent);
                        if(isSuccess){
                            System.out.println("Output file created successfully.");
                        } else {
                            System.out.println("Output file creation faced some issue.");
                        }
                    }
                }
            } else{
                System.out.println("Input is invalid.");
            }

        }
        catch (Exception e){
            //catch all exception from all methods
            System.out.println("System faced unexpected exception in main method.");
        }


    }





}
