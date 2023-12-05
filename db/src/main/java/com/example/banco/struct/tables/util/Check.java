package com.example.banco.struct.tables.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Check {
    private final static String regex = "^[A-Z]{3}\\d[A-Z]\\d{2}$";

    public static boolean checkCPF(String cpf) {
        if (cpf == null || cpf.length() != 11)
            return false;

        char str_digit;
        int first_digit;
        int second_digit;
        int rem;
        int sum;

        sum = 0;

        for (int i = 0; i < 9; i++)
            sum += (10 - i) * Character.getNumericValue(cpf.charAt(i));

        rem = sum % 11;

        switch (rem) {
            case 0:
            case 1:
                first_digit = 0;
                break;
            default:
                first_digit = 11 - rem;
                break;
        }

        str_digit = Character.forDigit(first_digit, 10);

        if (Character.compare(cpf.charAt(9), str_digit) != 0)
            return false;
        sum = 0;

        for (int i = 0; i < 10; i++)
            sum += (11-i) * Character.getNumericValue(cpf.charAt(i));

        rem = sum % 11;

        switch (rem) {
            case 0:
            case 1:
                second_digit = 0;
                break;
            default:
                second_digit = 11 - rem;
        }

        str_digit = Character.forDigit(second_digit, 10);

        if (Character.compare(cpf.charAt(10), str_digit) != 0)
            return false;

        return true;
    }

    public static boolean checkPlate(String license_plate) 
    {
        if (license_plate.length() != 7) return false;

        Pattern pattern = Pattern.compile(Check.regex);
        Matcher matcher = pattern.matcher(license_plate);

        return matcher.matches();
    }

    public static boolean checkDateValidity(String birth_date) {
        if (birth_date.length() != 8) return false;

        int ano = Integer.parseInt(birth_date.substring(0, 4));
        int mes = Integer.parseInt(birth_date.substring(4, 6));
        int dia = Integer.parseInt(birth_date.substring(6, 8));

        LocalDate dateLimit = LocalDate.now().minusYears(18);
        LocalDate dateInput = LocalDate.parse(birth_date, DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (dateInput.isAfter(dateLimit)) return false; // Menor com carro!!!

        if (dia < 1 || mes < 1) return false;

        switch (mes) {
            case 1: // Janeiro
            case 3: // Março
            case 5: // Maio
            case 7: // Julho
            case 8: // Agosto
            case 10: // Outubro
            case 12: // Dezenbro
                if (dia > 31)
                    return false;
                break;

            case 4: // Abril
            case 6: // Junho
            case 9: // Setembto
            case 11: // Novembro
                if (dia > 30)
                    return false;
                break;
            case 2: // Fevereiro
                if ((ano & 0b11) == 0) {
                    if (dia > 29)
                        return false;
                    break;
                }

                if (dia > 28)
                    return false;
                break;

            default:
                return false;

        }
        return true;
    }
}
