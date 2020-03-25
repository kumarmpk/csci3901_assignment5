package db;

import java.util.Map;

//interface between the ControllerClass and the DBHandlerClass

public interface DBHandler {

    void printString(String input);

    Map getData(String start, String end) throws Exception;

}
