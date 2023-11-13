package com.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class UserController {

    @GetMapping
    public String home(HttpServletRequest request){
        String ipAddress = request.getRemoteAddr();
        System.out.println("\n\n\n" + ipAddress + "\n\n\n");
        
        return "index";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criarRecurso(@RequestBody CorpoRequest MeuDTO) {

        System.out.println("> Requisicao recebida");
        System.out.println("Nome      : " + MeuDTO.getNome());
        System.out.println("Quantidade: " + MeuDTO.getQuantidade());
        System.out.println("Valor   : " + MeuDTO.getValor());

    }

}
