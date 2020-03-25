package control;

import java.util.Map;

public interface Controller {

    void printString(String input);

    boolean validateInput(String start, String end, String outputFileName) throws Exception;

    Map getData(String start, String end) throws Exception;

    String formXMLString(Map dataMap, String start, String end) throws Exception;

    boolean writeToOutputFile(String fileName, String content);

    boolean validateXML(String fileName);

}
