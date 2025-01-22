package controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class IndexController {
    
    public Handler get = (Context ctx)->{
        ctx.render("index.html");
    };
    
    public Handler adicionarProduto = (Context ctx)->{
        ctx.render("produtoAdicionar.html");
    };

    public Handler atualizarProduto = (Context ctx)->{
        ctx.render("produtoAtualizar.html");
    };
    
    public Handler listarProduto = (Context ctx)->{
        ctx.render("produtoListar.html");
    };
    
    public Handler visualizarVenda = (Context ctx)->{
        ctx.render("vendaVisualizar.html");
    };

    public Handler consultarVenda = (Context ctx)->{
        ctx.render("vendaConsultar.html");
    };
    
    public Handler visualizarEstoque = (Context ctx)->{
        ctx.render("estoqueVisualizar.html");
    };
    
    public Handler consultarProduto = (Context ctx)->{
        ctx.render("produtoconsultar.html");
    };
    //Ju faz os handlers pros seguintes, por enquanto é só pra gente conseguir pra pagina, a parte ocmplicada a agente faz dps

    /*
     * adicionarProduto - produtoAdicionar.html
     * atualizarProduto - produtoAtualizar.html
     * listarProduto - produtoListar.html
     * visualizarVenda - vendaVisualizar.html
     * consultarVenda - vendaConsultar.html
     * visualizarEstoque - estoqueVisualizar.html
     * consultarProduto - produtoConsultar.html
    */


}
