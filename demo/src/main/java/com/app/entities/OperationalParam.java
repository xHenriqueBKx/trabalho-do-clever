package com.app.entities;

public class OperationalParam
{
    private Group group;
    private int   dailOperationalParamy_rent_price;
    private int   full_tank_price; 
    private int   out_cleaning_price;
    private int   in_cleaning_price;
    private int   insurance_daily_price;
    private int   daily_rent_price;

    public OperationalParam()
    {

    }


    public Group GetGroup()
    {
        return this.group;
    }
    public void SetGroup(Group new_group)
    {
        this.group = new_group;
    }

    public int GetDailyRentPrice()
    {
        return this.daily_rent_price;
    }
    public void SetDailyRentPrice(int new_rent)
    {
        this.daily_rent_price = new_rent;
    }

    public int GetFullTankPrice()
    {
        return this.full_tank_price;
    }
    public void SetFullTankPrice(int new_tank_price)
    {
        this.full_tank_price = new_tank_price;
    }

    public int GetOutCleaningPrice()
    {
        return this.out_cleaning_price;
    }
    public void SetOutCleaningPrice(int new_out_cleaning_price)
    {
        this.out_cleaning_price = new_out_cleaning_price;
    }

    public int GetInCleaningPrice()
    {
        return this.in_cleaning_price;
    }
    public void SetInCleaningPrice(int new_in_cleaning_price)
    {
        this.in_cleaning_price = new_in_cleaning_price;
    }

    public int GetInsurancePrice()
    {
        return this.insurance_daily_price;
    }
    public void SetInsurancePrice(int new_insurance_price)
    {
        this.insurance_daily_price = new_insurance_price;
    }
}