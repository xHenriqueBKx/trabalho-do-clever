package com.example;


import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;

import com.example.banco.struct.tables.User;
import com.example.banco.struct.tables.schemas.ClientRegistry;
import com.example.banco.struct.tables.schemas.VehicleSchema;
import com.example.banco.struct.tables.util.Check;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;

public class App {
    private static void testDb() throws SQLException {

        User userTable = new User();
        
        ClientRegistry a =  userTable.Read("00011122233");
    
        ClientRegistry b = new ClientRegistry("09053991964", "henrique", "20051002", "henreidelas@gmail.com", "45933002382");
    
        boolean ok = userTable.Create(b);
    
        boolean oh = userTable.Delete(b.GetCPF());
    
        System.out.println("Add------");
        System.out.println();
        System.out.println(ok);
        System.out.println(b.GetCPF());
        System.out.println();
        System.out.println("---------");
    
        System.out.println("Delete---");
        System.out.println();
        System.out.println(oh);
        System.out.println();
        System.out.println("---------");
        
        System.out.println("Read-----");
        System.out.println();
        System.out.println(a.GetName());
        System.out.println();
        System.out.println("---------");

    } 
    
    
    private static void hash(String input) {
        XXHashFactory factory = XXHashFactory.fastestInstance();
        XXHash64 hash64 = factory.hash64();

        byte[] dataBytes = input.getBytes();

                                //   Valor      comeco    fim             salt
        long hashValue = hash64.hash(dataBytes, 0, dataBytes.length, 0);
        String bin = Long.toBinaryString(hashValue);


        BigInteger a = new BigInteger(bin, 2);
        System.out.println(a);

    }

    public static void main(String[] args) throws SQLException {
        try {
            ClientRegistry b = new ClientRegistry("10000000019", "Jorjin", "20001010", "jorjimjimjim@tanke", "45911221122");
            String a = Formatter.Request("User", "Create", b);
            System.out.println(a);
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }

}