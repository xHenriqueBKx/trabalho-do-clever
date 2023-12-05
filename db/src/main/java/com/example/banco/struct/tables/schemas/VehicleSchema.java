package com.example.banco.struct.tables.schemas;

import java.time.LocalDate;

import com.example.banco.struct.tables.util.Check;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class VehicleSchema
{
    private String  license_plate;
    private String  builder;
    private String  model;
    private String  colour;
    private Integer year_of_fabrication;
    private Group   group;
    private Status  status;
    
    @JsonCreator
    public VehicleSchema(
        String  license_plate,
        String  builder,
        String  model,
        String  colour,
        Integer year_of_fabrication,
        Group   group,
        Status  status
    ) 
    {
        this.license_plate          = license_plate;
        this.builder                = builder;
        this.model                  = model;
        this.colour                 = colour;
        this.year_of_fabrication    = year_of_fabrication;
        this.group                  = group;
        this.status                 = status;
    }

    private boolean checkYear() {
        if (this.year_of_fabrication > 1886) return false;        
        
        int actualYear = LocalDate.now().getYear();
        
        if (this.year_of_fabrication > actualYear) return false;

        return true;
    }

    public boolean Check() {
        if (
            this.license_plate          == null || this.license_plate.length()          == 0 ||
            this.builder                == null || this.builder.length()                == 0 ||
            this.model                  == null || this.model.length()                  == 0 ||
            this.colour                 == null || this.colour.length()                 == 0 ||
            this.year_of_fabrication    == null ||
            this.group                  == null ||
            this.status                 == null 
        ) 
            return false;
        

        if (!Check.checkPlate(this.license_plate)) return false;
        if (!checkYear()) return false;

        return true;
    }

    @JsonProperty("license_plate")
    public String GetLicensePlate()
    {
        return this.license_plate;
    }
    public void SetLicensePlate(String new_plate)
    {
        this.license_plate = new_plate;
    }

    @JsonProperty("builder")
    public String GetBuilder()
    {
        return this.builder;
    }
    public void SetBuilder(String new_builder)
    {
        this.builder = new_builder;
    }

    @JsonProperty("model")
    public String GetModel()
    {
        return this.model;
    }
    public void SetModel(String new_model)
    {
        this.model = new_model;
    }

    @JsonProperty("colour")
    public String GetColour()
    {
        return this.colour;
    }
    public void SetColour(String new_colour)
    {
        this.colour = new_colour;
    }

    @JsonProperty("year_of_fabrication")
    public Integer GetYearOfFabrication()
    {
        return this.year_of_fabrication;
    }
    public void SetYearOfFabrication(Integer new_year)
    {
        this.year_of_fabrication = new_year;
    }

    @JsonProperty("group")
    public Group GetGroup()
    {
        return this.group;
    }
    public void SetGroup(Group new_group)
    {
        this.group = new_group;
    }

    @JsonProperty("status")
    public Status GetStatus()
    {
        return this.status;
    }
    public void SetStatus(Status new_status)
    {
        this.status = new_status;
    }
}