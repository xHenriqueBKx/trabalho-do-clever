package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

import org.mindrot.jbcrypt.BCrypt;

public class App {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().load();      
        String url = dotenv.get("DB_URL");
        String usuario = dotenv.get("DB_USER");
        String senha = dotenv.get("DB_PASSWORD");
        
        System.out.println(url + "\n" + usuario + "\n" + senha);
        // try {
        // Class.forName("org.postgresql.Driver");
        // Connection conexao = DriverManager.getConnection(url, usuario, senha);
        // } catch (ClassNotFoundException e) {
        // // Erro caso o driver JDBC não foi instalado
        // e.printStackTrace();
        // } catch (SQLException e) {
        // // Erro caso haja problemas para se conectar ao banco de dados
        // e.printStackTrace();
        // }
        String senhaOriginal = "1234";
        String hashSenha = BCrypt.hashpw(senhaOriginal, BCrypt.gensalt(12));
        System.out.println("Senha Original: " + senhaOriginal);
        System.out.println("Hash da Senha: " + hashSenha);
        System.out.println("Hash length: " + hashSenha.length());

        // Verifique se a senha corresponde ao hash
        boolean senhaCorreta = BCrypt.checkpw("senhaIncorreta", hashSenha);
        System.out.println("Senha Correta? " + senhaCorreta);
    }
}