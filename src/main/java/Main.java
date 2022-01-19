import controller.Controller;
import java.io.IOException;
import model.Model;
import view.View;


public class Main {
    public static void main(String[] args) throws IOException {
        Controller ctrl = new Controller(new Model(), new View());
        ctrl.workWithFirstLvlMenu();
    }
}