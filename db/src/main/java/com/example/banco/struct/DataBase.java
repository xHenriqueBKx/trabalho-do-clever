package com.example.banco.struct;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public abstract class DataBase {
    protected static Connection connection = null;

    protected DataBase() {
        if (connection != null) {
            return;
        }

        String url;
        String user;
        String password;
        Dotenv dotenv;

        try {
            dotenv      = Dotenv.configure().load();   
        
        } catch (Exception e) {
            System.err.println("\nFalha ao carregar as variáveis de ambiente");
            System.err.println(e);
            return;
        
        }

        url             = dotenv.get("DB_URL");
        user            = dotenv.get("DB_USER");
        password        = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
            connection  = DriverManager.getConnection(url, user, password);
        
        } catch (ClassNotFoundException e) {
            // Erro caso o driver JDBC não foi instalado
            System.out.println("\nDeve instalar o driver JDBC no pom.xml");
            System.out.println("<groupId>org.postgresql</groupId>");
            System.out.println("<artifactId>postgresql</artifactId>");
            System.out.println();

        } catch (SQLException e) {
            // Erro caso haja problemas para se conectar ao banco de dados
            e.printStackTrace();
        
        }

    }
}
