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

        try {
            preco = Double.parseDouble(ctx.formParam("preco"));
            quantidadeEstoque = Integer.parseInt(ctx.formParam("quantidadeEstoque"));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos (preço ou quantidade)");
            ctx.status(400).result("Erro ao converter valores numéricos.");
            return;
        }

        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque);

        manager.addProduto(produto);

        Map<String, Object> dados = new HashMap<>();
        dados.put("mensagem", "Produto cadastrado com sucesso!");
        dados.put("nome", produto.getNome());
        dados.put("descricao", produto.getDescricao());
        dados.put("preco", produto.getPreco());
        dados.put("quantidadeEstoque", produto.getQuantidadeEstoque());
        ctx.render("resposta.html", dados);
    };

    public Handler getProdutoAtualizar = (Context ctx) -> {
        int id = 0;
        try {
            // Mudança aqui: usamos pathParam ao invés de queryParam
            id = Integer.parseInt(ctx.pathParam("id"));  // Obtém o ID da URL (parte do caminho)
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do produto inválido.");
            return;
        }
        Produto produto = manager.getProdutoAtualizar(id);
        if (produto != null) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("produto", produto);
            // Redireciona para a página de atualização do produto
            ctx.render("produtoAtualizar.html", dados);
        } else {
            ctx.status(404).result("Produto não encontrado");
        }
    };
     

    public Handler postProdutoAtualizar = (Context ctx) -> {
        int id = 0;
        try {
            id = Integer.parseInt(ctx.formParam("id"));
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do produto inválido.");
            return;
        }
    
        String nome = ctx.formParam("nome");
        String descricao = ctx.formParam("descricao");
        double preco = 0.0;
        int quantidadeEstoque = 0;
    
        try {
            preco = Double.parseDouble(ctx.formParam("preco"));
            quantidadeEstoque = Integer.parseInt(ctx.formParam("quantidadeEstoque"));
        } catch (NumberFormatException e) {
            System.out.println("Erro ao converter valores numéricos (preço ou quantidade)");
            ctx.status(400).result("Erro ao converter valores numéricos.");
            return;
        }
    
        Produto produto = new Produto(nome, descricao, preco, quantidadeEstoque);
        produto.setId(id); // Definindo o ID do produto para atualização
        boolean sucesso = manager.updateProduto(produto);
    
        if (sucesso) {
            Map<String, Object> dados = new HashMap<>();
            dados.put("mensagem", "Produto atualizado com sucesso!");
            dados.put("nome", produto.getNome());
            dados.put("descricao", produto.getDescricao());
            dados.put("preco", produto.getPreco());
            dados.put("quantidadeEstoque", produto.getQuantidadeEstoque());
            ctx.render("respostaAtualizar.html", dados);
        } else {
            ctx.status(500).result("Erro ao atualizar produto");
        }
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
        List<Produto> lista = manager.getProduto();

        Map<String, Object> dados = new HashMap<>();

        dados.put("lista", lista);
        ctx.render("estoqueVisualizar.html", dados);
    };

    public Handler consultarProduto = (Context ctx) -> {
        ctx.render("produtoconsultar.html");
    };

    public Handler buscarProduto = (Context ctx) -> {
        ctx.render("produtoBuscar.html");  // Renderiza a página de busca do produto
    };
    
}
