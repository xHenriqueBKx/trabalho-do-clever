package com.example.banco.struct.tables.schemas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.banco.struct.tables.util.Check;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RentingRegistry
{
    private String      id;
    private String      cpf;
    private String      plate;
    private String      take_date;
    private String      return_date;
    private Integer     rent_value;
    private RentStatus  rent_status;

    @JsonCreator
    public RentingRegistry
    (
        String      id,
        String      cpf,
        String      plate,
        String      take_date,
        String      return_date,
        Integer     rent_value,
        RentStatus  rent_status
    )
    {
        this.id             = id;
        this.cpf            = cpf;
        this.plate          = plate;
        this.take_date      = take_date;
        this.return_date    = return_date;
        this.rent_value     = rent_value;
        this.rent_status    = rent_status;
    }

    private boolean checkTakeDate() {
        if (!Check.checkDateValidity(this.take_date)) return false;
        return true;
    }

    private boolean checkReturnDate() {
        if (!Check.checkDateValidity(this.return_date)) return false;

        LocalDate dateReturn    = LocalDate.parse(this.return_date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate dateTake      = LocalDate.parse(this.take_date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        LocalDate dateLimit     = LocalDate.now();
        
        
        if (dateLimit.isAfter(dateReturn)) return false; // retorno antes de agora
        if (dateTake.isAfter(dateReturn)) return false;  // retorno antes de retirada

        return true;
    }

    private boolean checkRentValue() {
        if (this.rent_value<0) return false;
        
        return true;
    }

    public boolean Check() {
        if (
            this.id             == null ||
            this.cpf            == null ||
            this.plate          == null ||
            this.take_date      == null ||
            this.return_date    == null ||
            this.rent_value     == null ||
            this.rent_status    == null 
        ) { return false; }

        if (!Check.checkCPF(this.cpf))      return false; 
        if (!Check.checkPlate(this.plate))  return false;
        if (!this.checkTakeDate())          return false;
        if (!this.checkReturnDate())        return false;
        if (!this.checkRentValue())         return false;


        return true;
    }

    @JsonProperty("id")
    public String GetId()
    {
        return this.id;
    }
    public void SetId(String new_id)
    {
        this.id = new_id;
    }

    @JsonProperty("cpf")
    public String GetClientCPF()
    {
        return this.cpf;
    }
    public void SetClientCPF(String cpf)
    {
        this.cpf = cpf;
    }

    @JsonProperty("plate")
    public String GetPlate()
    {
        return this.plate;
    }
    public void SetVehiclePlate(String new_vehicle)
    {
        this.plate = new_vehicle;
    }

    @JsonProperty("take_date")
    public String GetRetrievalDate()
    {
        return this.take_date;
    }
    public void SetRetrievalDate(String new_take_date)
    {
        this.take_date = new_take_date;
    }

    @JsonProperty("return_date")
    public String GetReturnDate()
    {
        return this.return_date;
    }
    public void SetReturnDate(String new_return_date)
    {
        this.return_date = new_return_date;
    }

    @JsonProperty("rent_value")
    public int GetRentValue()
    {
        return this.rent_value;
    }
    public void SetRentValue(int new_rent)
    {
        this.rent_value = new_rent;
    }

    @JsonProperty("rent_status")
    public RentStatus GetRentStatus()
    {
        return this.rent_status;
    }
    public void SetRentStatus(RentStatus new_rent_status)
    {
        this.rent_status = new_rent_status;
    }
}