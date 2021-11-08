package nl.han.ica.oose.dea.niels.datalayer.dbconnection;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class DatabaseProperties {
    private Properties properties = new Properties();

    public DatabaseProperties() {
        try {
            properties.load(Objects.requireNonNull(getClass().
                    getClassLoader().getResourceAsStream("database.properties")));
            Class.forName(getDriver());
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getConnectionString() { return properties.getProperty("connectionString"); }

    public String getDriver() { return properties.getProperty("driver"); }
}
