package br.edu.ifpr.pgua.eic.tads;

import br.edu.ifpr.pgua.eic.tads.controllers.IndexController;
import br.edu.ifpr.pgua.eic.tads.utils.JavalinUtils;
import io.javalin.Javalin;

public class App {
    public static void main( String[] args ){
        var app = JavalinUtils.makeApp(1234);
        
        IndexController indexController = new IndexController();
        
        app.get("/", indexController.get);
        app.get("/ola", indexController.ola);
    }
}
