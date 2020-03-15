import java.util.Properties;

//class used to store the database connection details

public class ConnectionProperties {

    public void setIdentity(Properties prop) {
        prop.setProperty("database", "csci3901");   //db name
        prop.setProperty("user", "manoharan");  //username
        prop.setProperty("password", "B00837436");  //password
    }

}
