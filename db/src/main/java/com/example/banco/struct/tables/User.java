package com.example.banco.struct.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.banco.struct.DataBase;
import com.example.banco.struct.tables.schemas.ClientRegistry;

public class User extends DataBase {
    private String tableName = "Users";
    private String primaryKey = "CPF";
    
    public User() {
        super();
    }

    public Boolean Create(ClientRegistry client) throws SQLException {
        if (this.Read(client.GetCPF()) != null) {
            return false;
        }

        if (!client.Check()) {
            return false;
        }

        
        String sql = String.format("INSERT INTO %s (CPF, Name, BirthDate, Email, MobileNumber) VALUES (?, ?, ?, ?, ?)", tableName);        
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, client.GetCPF());
        preparedStatement.setString(2, client.GetName());
        preparedStatement.setString(3, client.GetBirthDate());
        preparedStatement.setString(4, client.GetEmail());
        preparedStatement.setString(5, client.GetMobileNumber());
        
        try { 
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            return false;
        }

        return true;
    }

    public ClientRegistry Read(String cpf) throws SQLException {
        String query = "SELECT * FROM " + tableName + " WHERE " + primaryKey + " = ?";
        String name             = null;
        String birth_date       = null;
        String email            = null;
        String mobile_number    = null;


        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, cpf);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            name             = resultSet.getString("Name");
            birth_date       = resultSet.getString("BirthDate");
            email            = resultSet.getString("email");
            mobile_number    = resultSet.getString("MobileNumber");
        } else { return null; }
        
        
        ClientRegistry UserObject = 
          new ClientRegistry(
            cpf, 
            name, 
            birth_date, 
            email, 
            mobile_number
          );

        resultSet.close();
        preparedStatement.close();

        return UserObject;
    }

    public boolean Update(ClientRegistry client) throws SQLException {
        if (!client.Check()) {
            return false;
        }

        String sql = String.format(
            "UPDATE %s SET CPF=?, Name=?, BirthDate=?, Email=?, MobileNumber=?",
            tableName
        );

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, client.GetCPF());
        preparedStatement.setString(2, client.GetName());
        preparedStatement.setString(3, client.GetBirthDate());
        preparedStatement.setString(4, client.GetEmail());
        preparedStatement.setString(5, client.GetMobileNumber());

        try {
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            return false;
        }

        return true;
    }

    public boolean Delete(String cpf) throws SQLException {
        if (this.Read(cpf) == null) {
            return false;
        }

        String sql = String.format("DELETE FROM %s WHERE %s = ?", tableName, primaryKey);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, cpf);
        
        try {
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            return false;
        }

        return true;
    }

    public List<ClientRegistry> ReadMore(String condition) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE %s", tableName, condition);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<ClientRegistry> response = new ArrayList<ClientRegistry>();
        
        while (resultSet.next()) {
            ClientRegistry registry = new ClientRegistry(
                resultSet.getString("CPF"),
                resultSet.getString("Name"),
                resultSet.getString("BirthDate"),
                resultSet.getString("Email"),
                resultSet.getString("MobileNumber")
            );
            response.add(registry);
        }

        return response;
    }
}