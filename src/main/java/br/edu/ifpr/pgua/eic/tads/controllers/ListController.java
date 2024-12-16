package br.edu.ifpr.pgua.eic.tads.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.ifpr.pgua.eic.tads.models.Cadastro;
import br.edu.ifpr.pgua.eic.tads.models.Pessoa;
import io.javalin.http.Context;
import io.javalin.http.Handler;

public class ListController {
    //atributos
    private Cadastro cadastro;

    //construtor
    public ListController(Cadastro cadastro){
        this.cadastro = cadastro;
    }

    //metodos
    public Handler get = (Context ctx)->{
        List<Pessoa> lista = cadastro.getPessoas();

        Map<String, Object> dados = new HashMap<>();

        dados.put("lista", lista);
        ctx.render("lista.html", dados);
    };
}
