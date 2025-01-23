import controllers.IndexController;
import models.Manager;
import utils.JavalinUtils;

public class App {
    public static void main( String[] args ){
        var app = JavalinUtils.makeApp(1234);
        
        Manager manager = new Manager();
        IndexController indexController = new IndexController(manager);
        
        app.get("/", indexController.get);
        app.get("/produto-adicionar", indexController.adicionarProduto);
        app.get("/produto-atualizar", indexController.atualizarProduto);
        app.get("/produto-listar", indexController.listarProduto);
        app.get("/produto-vendido", indexController.consultarProduto);
        app.get("/venda-total", indexController.consultarVenda);
        app.get("/estoque-visualizar", indexController.visualizarEstoque);
        app.get("/venda-visualizar", indexController.visualizarVenda);
    }
}
