package db;

import java.util.Map;

public interface DBHandler {

    void printString(String input);

    Map getData(String start, String end) throws Exception;

}
