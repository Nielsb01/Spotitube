package nl.han.ica.oose.dea.niels.datalayer.datamappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IDataMapper<T> {
    T toDTO(ResultSet rs) throws SQLException;
}
