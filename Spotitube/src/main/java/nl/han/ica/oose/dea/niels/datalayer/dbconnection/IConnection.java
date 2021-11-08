package nl.han.ica.oose.dea.niels.datalayer.dbconnection;

import java.sql.*;

public interface IConnection {
    Connection getConnection() throws SQLException, SQLTimeoutException;
}
