package com.example;

import java.util.Optional;

import com.example.banco.Connect;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Optional<Connection> connection = Connect.stablishConnection();
        
        if (connection.isPresent()) {
            System.out.println("Beleza");
        } else {
            System.out.println("Deu errado");
        }

    }

    //     String senhaOriginal = "1234";
    //     String hashSenha = BCrypt.hashpw(senhaOriginal, BCrypt.gensalt(12));
    //     System.out.println("Senha Original: " + senhaOriginal);
    //     System.out.println("Hash da Senha: " + hashSenha);
    //     System.out.println("Hash length: " + hashSenha.length());

    //     // Verifique se a senha corresponde ao hash
    //     boolean senhaCorreta = BCrypt.checkpw("senhaIncorreta", hashSenha);
    //     System.out.println("Senha Correta? " + senhaCorreta);
    // }

}