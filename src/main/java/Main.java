import controller.Controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import model.Model;
import model.Music;
import view.View;


public class Main {
    public static void main(String[] args) throws IOException {
        Controller ctrl = new Controller(new Model(), new View());
        ctrl.workWithFirstLvlMenu();
    }
}