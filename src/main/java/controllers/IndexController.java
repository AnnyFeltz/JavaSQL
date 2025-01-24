package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.http.*;
import models.Manager;
import models.Produto;

public class IndexController {

    Manager manager;

    public IndexController(Manager manager) {
        this.manager = manager;
    }

    public Handler get = (Context ctx) -> {
        ctx.render("index.html");

    };

    public Handler getProduto = (Context ctx) -> {

        ctx.render("produtoAdicionar.html");
    };

    public Handler postProduto = (Context ctx) -> {
        String nome = ctx.formParam("nome");
        String descricao = ctx.formParam("descricao");
        double preco = 0.0;
        int quantidadeEstoque = 0;

        try {
            preco = Double.parseDouble(ctx.formParam("preco"));
            quantidadeEstoque = Integer.parseInt(ctx.formParam("quantidadeEstoque"));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos (preço ou quantidade)");
        }

        
        Map<String, Object> dados = new HashMap<>();

        dados.putIfAbsent("mensagem", "");
        dados.put("mensagem", "Cadastrado!");
        
        ctx.render("produtoAdicionar.html", dados);
    };

    public Handler atualizarProduto = (Context ctx) -> {
        ctx.render("produtoAtualizar.html");
    };

    public Handler listarProduto = (Context ctx) -> {
        List<Produto> list = manager.getProduto();

        Map<String, Object> dados = new HashMap<>();

        dados.put("list", list);
        ctx.render("produtoListar.html", dados);
    };

    public Handler visualizarVenda = (Context ctx) -> {
        ctx.render("vendaVisualizar.html");
    };

    public Handler consultarVenda = (Context ctx) -> {
        ctx.render("vendaConsultar.html");
    };

    public Handler visualizarEstoque = (Context ctx) -> {
        ctx.render("estoqueVisualizar.html");
    };

    public Handler consultarProduto = (Context ctx) -> {
        ctx.render("produtoconsultar.html");
    };

}
