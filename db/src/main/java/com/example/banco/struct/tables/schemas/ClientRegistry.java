package com.example.banco.struct.tables.schemas;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import com.example.banco.struct.tables.util.Check;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientRegistry {
    private String  cpf;
    private String  name;
    private String  birth_date;
    private String  email;
    private String  mobile_number;

    @JsonCreator
    public ClientRegistry(
            String  cpf,
            String  name,
            String  birth_date,
            String  email,
            String  mobile_number
    ) {
        this.cpf            = cpf;
        this.name           = name;
        this.birth_date     = birth_date; // YYYY-MM-DD
        this.email          = email;
        this.mobile_number  = mobile_number;
    }

    private boolean checkDDD() {
        char first_digit = this.mobile_number.charAt(0);
        char second_digit = this.mobile_number.charAt(1);

        switch (first_digit) {
            case '2':
                switch (second_digit) {
                    case '1':
                    case '2':
                    case '4':
                    case '7':
                    case '8':
                        return false;
                    default:
                        break;
                }
                break;

            case '3':
                if (second_digit == '9')
                    return false;
                break;

            case '5':
                switch (second_digit) {
                    case '1':
                    case '3':
                    case '4':
                    case '5':
                        break;
                    default:
                        return false;
                }
                break;

            case '7':
                switch (second_digit) {
                    case '1':
                    case '3':
                    case '4':
                    case '5':
                    case '7':
                    case '9':
                        break;
                    default:
                        return false;
                }
                break;

            case '1':
            case '4':
            case '6':
            case '8':
            case '9':
                switch (second_digit) {
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        break;

                    default:
                        return false;
                }
                break;

            default:
                break;

        }

        return true;
    }

    private boolean checkMobileNumber() {
        if (this.mobile_number.length() != 11)
            return false;

        if (!checkDDD())
            return false;

        return true;
    }

    private boolean otherDateCheck() {
        try {
            // Utilizar um DateTimeFormatter com ResolverStyle.STRICT para garantir um
            // parsing estrito
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
                    .withResolverStyle(ResolverStyle.STRICT);

            // Tentar converter a string para LocalDate
            LocalDate data = LocalDate.parse(this.birth_date, formatter);

            // Se o parsing for bem-sucedido, a data é válida
            return true;
        } catch (DateTimeParseException e) {
            // Se ocorrer uma exceção, a data não é válida
            return false;
        }
    }

    private static boolean checkBirthDate(String birth_date) {
        int year = Integer.parseInt(birth_date.substring(0, 4));

        if (!Check.checkDateValidity(birth_date)) return false;

        LocalDate dateLimit = LocalDate.now().minusYears(18);
        LocalDate dateInput = LocalDate.parse(birth_date, DateTimeFormatter.ofPattern("yyyyMMdd"));
        
        if (dateInput.isAfter(dateLimit)) return false; // Menor com carro!!!

        if (year < 1900) return false;

        return true;
    }

    public boolean Check() {
        if (!Check.checkCPF(this.cpf))
            return false;
        if (!ClientRegistry.checkBirthDate(this.birth_date))
            return false;
        if (!this.checkMobileNumber())
            return false;

        return true;
    }
    
    @JsonProperty("cpf")
    public String GetCPF() {
        return this.cpf;
    }

    public void SetCPF(String new_cpf) {
        this.cpf = new_cpf;
    }

    @JsonProperty("name")
    public String GetName() {
        return this.name;
    }

    public void SetName(String new_name) {
        this.name = new_name;
    }

    @JsonProperty("birth_date")
    public String GetBirthDate() {
        return this.birth_date;
    }
    
    public void SetBirthDate(String new_birth_date) {
        this.birth_date = new_birth_date;
    }

    @JsonProperty("email")
    public String GetEmail() {
        return this.email;
    }

    public void SetEmail(String newm_email) {
        this.email = newm_email;
    }

    @JsonProperty("mobile_number")
    public String GetMobileNumber() {
        return this.mobile_number;
    }

    public void SetMobileNumber(String new_mobile_number) {
        this.mobile_number = new_mobile_number;
    }
}