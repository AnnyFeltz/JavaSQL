import controllers.IndexController;
import utils.JavalinUtils;

public class App {
    public static void main( String[] args ){
        var app = JavalinUtils.makeApp(1234);
        
        IndexController indexController = new IndexController();
        
        app.get("/", indexController.get);
        app.get("/ola", indexController.ola);
    }
}
