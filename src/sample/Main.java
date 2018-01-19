package sample;

import Lab5.Bank;
import Lab5.Client;
import Lab5.Store;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application {

    static Bank bank;
    static Client client;
    static Store store;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Font.loadFont(Main.class.getResource("united.ttf").toExternalForm(),10);
        bank = new Bank("MazeBank",512);
        client = new Client(bank);
        store = new Store(bank);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}
