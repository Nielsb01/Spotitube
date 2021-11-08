package nl.han.ica.oose.dea.niels.datalayer.dbconnection;

import javax.inject.Inject;
import java.sql.*;

public class ConnectionSQL implements IConnection {
    private DatabaseProperties databaseProperties;
    private Connection connection;

    @Inject
    public void setDatabaseProperties(DatabaseProperties databaseProperties) {
        this.databaseProperties = databaseProperties;
    }

    @Override
    public Connection getConnection() throws SQLException, SQLTimeoutException
    {
        return DriverManager.getConnection(databaseProperties.getConnectionString());
    }
}
