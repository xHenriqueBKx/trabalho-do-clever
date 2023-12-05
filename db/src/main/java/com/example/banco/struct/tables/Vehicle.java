package com.example.banco.struct.tables;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.banco.struct.DataBase;
import com.example.banco.struct.tables.schemas.*;


public class Vehicle extends DataBase {
    private String tableName = "Vehicle";
    private String primaryKey = "LicensePlate";

    public Vehicle() {
        super();
    }


    public int Create(VehicleSchema vehicle) throws SQLException {
        if (vehicle.GetLicensePlate() != null) { return -1; } // veiculo ja cadastrado
        if (!vehicle.Check()) { return -2; } // dados no formato invalido

        String sql = String.format("INSERT INTO %s (LicensePlate, Builder, Model, Color, YearFabrication, GroupVehicle, Status) VALUES (?, ?, ?, ?, ?, ?, ?)", tableName);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, vehicle.GetLicensePlate());
        preparedStatement.setString(2, vehicle.GetBuilder());
        preparedStatement.setString(3, vehicle.GetModel());
        preparedStatement.setString(4, vehicle.GetColour());
        preparedStatement.setString(5, Integer.toString( vehicle.GetYearOfFabrication() ));
        preparedStatement.setString(6, vehicle.GetGroup().toString());
        preparedStatement.setString(7, vehicle.GetStatus().toString());       

        try { 
            preparedStatement.executeUpdate();
        } catch (Exception error) { 
            return -3; // erro interno
        }


        return 0; // tudo correu
    }

    public VehicleSchema Read(String plate) throws SQLException {
        String  sql = String.format("SELECT * FROM %s WHERE %s = ?", tableName, primaryKey);
        String  builder                 = null;
        String  model                   = null;
        String  colour                  = null;
        Integer year_of_fabrication     = null;
        Group   group                   = null;
        Status  status                  = null;

        
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, plate);
        
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            builder             = resultSet.getString("Builder");
            model               = resultSet.getString("Model");
            colour              = resultSet.getString("Colour");
            year_of_fabrication = resultSet.getInt("YearFabrication");
            group               = Group.valueOf( resultSet.getString("Group") );
            status              = Status.valueOf( resultSet.getString("Status") );
        } else { return null; }
    
        VehicleSchema vehicle = 
          new VehicleSchema(
            plate, 
            builder, 
            model, 
            colour, 
            year_of_fabrication, 
            group, 
            status
          );
    
        resultSet.close();
        preparedStatement.close();
        
        return vehicle;
    }

    public boolean Update(VehicleSchema vehicle) throws SQLException {
        if (!vehicle.Check()) {
            return false;
        }

        String sql = String.format(
            "UPDATE %s SET LicensePlate=? Builder=?, Model=?, Colour=?, YearFabrication=?, GroupVehicle=?, Status=? WHERE %s = %s",
            tableName,
            primaryKey,
            vehicle.GetLicensePlate() 
        );

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, vehicle.GetLicensePlate());
        preparedStatement.setString(2, vehicle.GetBuilder());
        preparedStatement.setString(3, vehicle.GetModel());
        preparedStatement.setString(4, vehicle.GetColour());
        preparedStatement.setString(5, Integer.toString( vehicle.GetYearOfFabrication() ));
        preparedStatement.setString(6, vehicle.GetGroup().toString());
        preparedStatement.setString(7, vehicle.GetStatus().toString());
        
        try {
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Optional<?> Delete(String plate) throws SQLException {
        if (plate.isEmpty()) {
            return Optional.of(this.GetAll());
        }

        VehicleSchema vehicle = this.Read(plate);

        if (vehicle == null) { return Optional.of(false); }

        String sql = String.format("UPDATE %s SET Status=? WHERE %s=?", tableName, primaryKey);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, plate);
        
        try {
            preparedStatement.executeUpdate();
        } catch (Exception error) {
            return Optional.of(false);
        }

        return Optional.of(true);
    }

    public List<VehicleSchema> ReadAvailable() throws SQLException {
        String sql = String.format("SELECT * FROM Vehicle WHERE Status = DISPONIVEL");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        List<VehicleSchema> response = new ArrayList<VehicleSchema>();
        
        while (resultSet.next()) {
            VehicleSchema vehicle = new VehicleSchema(
                resultSet.getString("LiscensePlate"), 
                resultSet.getString("Builder"), 
                resultSet.getString("Model"), 
                resultSet.getString("Colour"), 
                resultSet.getInt("YearFabrication"),
                Group.valueOf( resultSet.getString("Group") ),
                Status.valueOf( resultSet.getString("Status") )
            );

            response.add(vehicle);
        }

        return response;
    }
    
    public List<VehicleSchema> ReadAvailableAndGroup(Group group) throws SQLException {
        String sql = String.format("SELECT * FROM Vehicle WHERE Status = DISPONIVEL AND GroupVehicle = ?");

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, group.toString());

        ResultSet resultSet = preparedStatement.executeQuery();
        List<VehicleSchema> response = new ArrayList<VehicleSchema>();
        
        while (resultSet.next()) {
            VehicleSchema vehicle = new VehicleSchema(
                resultSet.getString("LiscensePlate"), 
                resultSet.getString("Builder"), 
                resultSet.getString("Model"), 
                resultSet.getString("Colour"), 
                resultSet.getInt("YearFabrication"),
                group,
                Status.valueOf( resultSet.getString("Status") )
            );

            response.add(vehicle);
        }

        return response;
    }

    public List<VehicleSchema> GetAll() throws SQLException {
        String sql = String.format("SELECT * FROM %s", tableName);

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<VehicleSchema> response = new ArrayList<VehicleSchema>();

        while (resultSet.next()) {
            VehicleSchema vehicle = new VehicleSchema(
                resultSet.getString("LicensePlate"),
                resultSet.getString("Builder"),
                resultSet.getString("Model"),
                resultSet.getString("Colour"),
                resultSet.getInt("YearFabrication"),
                Group.valueOf( resultSet.getString("Group") ),
                Status.valueOf( resultSet.getString("Status") )
            );
            

            response.add(vehicle);
        }

        return response;
    }
}
