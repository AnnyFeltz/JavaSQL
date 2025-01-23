package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;
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

    public Handler adicionarProduto = (Context ctx) -> {

        ctx.render("produtoAdicionar.html");
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
