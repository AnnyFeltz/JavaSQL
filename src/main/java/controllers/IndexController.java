package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import models.Manager;
import models.Produto;
import models.Venda;

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
        boolean ativo = true;
    
        try {
            preco = Double.parseDouble(ctx.formParam("preco"));
            quantidadeEstoque = Integer.parseInt(ctx.formParam("quantidadeEstoque"));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos (preço ou quantidade)");
            ctx.status(400).result("Erro ao converter valores numéricos.");
            return;
        }
    
        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque, ativo);
    
        // Adicionar o produto no banco
        manager.addProduto(produto);
    
        Map<String, Object> dados = new HashMap<>();
        dados.put("mensagem", "Produto cadastrado com sucesso!");
        dados.put("nome", produto.getNome());
        dados.put("descricao", produto.getDescricao());
        dados.put("preco", produto.getPreco());
        dados.put("quantidadeEstoque",produto.getQuantidadeEstoque());
        dados.put("ativo", produto.isAtivo());
        ctx.render("resposta.html", dados);
    };
    

    public Handler atualizarProduto = (Context ctx) -> {
        ctx.render("produtoAtualizar.html");
    };

        //listar produto
    public Handler listarProduto = (Context ctx) -> {
        List<Produto> lista = manager.getProduto();

        Map<String, Object> dados = new HashMap<>();

        dados.put("lista", lista);
        ctx.render("produtoListar.html", dados);
    };

    public Handler visualizarVenda = (Context ctx) -> {
        List<Venda> lista = manager.getVenda();

        Map<String, Object> dados = new HashMap<>();

        dados.put("lista", lista);
        ctx.render("vendaVisualizar.html", dados);
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
