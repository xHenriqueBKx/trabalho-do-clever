package com.example.banco.struct.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.banco.struct.DataBase;
import com.example.banco.struct.tables.schemas.RentStatus;
import com.example.banco.struct.tables.schemas.RentingRegistry;

public class Registry extends DataBase {
    private String tableName = "Registry";
    private String primaryKey = "Id";

    public Registry() {
        super();
    }

    public RentingRegistry Read(String id) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = " + id;
        
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        String      cpf         = resultSet.getString("CPF");
        String      plate       = resultSet.getString("LicensePlate");
        String      take_date   = resultSet.getString("TakeDate");
        String      return_date = resultSet.getString("ReturnDate");
        Integer     rent_value  = resultSet.getInt("RentValue");
        RentStatus rent_status  = RentStatus.valueOf(resultSet.getString("RentStatus"));
        
        RentingRegistry registry = new RentingRegistry(id, cpf, plate, take_date, return_date, rent_value, rent_status);
        return registry;
    }


    public boolean NewRegistry() {
        return true;
    }
}