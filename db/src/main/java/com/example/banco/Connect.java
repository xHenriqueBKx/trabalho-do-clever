package com.example.banco;


import java.util.Optional;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class Connect {
    private static Connection connection;

    public static Optional<Connection> stablishConnection() {
        String url;
        String user;
        String password;
        Dotenv dotenv;


        // Obter Variaveis de Ambiente
        try {
            dotenv = Dotenv.configure().load();   
        
        } catch (Exception e) {
            System.err.println("Falha ao carregar as variáveis de ambiente");
            System.err.println(e);
            return Optional.empty();
        
        }
        
        // Obter Parametros de conexão
        url          = dotenv.get("DB_URL");
        user         = dotenv.get("DB_USER");
        password     = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
            return Optional.of(connection);
        
        } catch (ClassNotFoundException e) {
            // Erro caso o driver JDBC não foi instalado
            System.out.println("Deve instalar o driver JDBC no pom.xml");
            System.out.println("<groupId>org.postgresql</groupId>");
            System.out.println("<artifactId>postgresql</artifactId>");
            System.out.println();

        } catch (SQLException e) {
            // Erro caso haja problemas para se conectar ao banco de dados
            e.printStackTrace();
        
        }

        return Optional.empty();
    }
}
