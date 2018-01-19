package sample;

import Lab5.Money;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.*;
import javafx.event.*;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import static sample.Main.client;
import static sample.Main.store;

public class Controller implements Initializable {

    final static private int EXIT_CODE = 1;

    @FXML
    Label balans;
    @FXML
    Label cash;
    @FXML
    TextField _100;
    @FXML
    TextField _500;
    @FXML
    TextField _1000;
    @FXML
    TextField _5000;
    @FXML
    Pane payment1;
    @FXML
    Button close;
    @FXML
    TextField _2_100;
    @FXML
    TextField _2_500;
    @FXML
    TextField _2_1000;
    @FXML
    TextField _2_5000;
    @FXML
    Label prCost;


    @FXML
    public void ChangeMoney(ActionEvent event) throws IOException {

        System.out.println("You clicked me!");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("screen1.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    public void Refresh(MouseEvent event)
    {
        balans.setText(client.bank.ChkMoney(client.myAcc).toString());
        cash.setText(Long.toString(client.CountCash()));
    }
    @FXML
    public void goToShop(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("screen2.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    public void goToMenu(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent home_page_parent = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene home_page_scene = new Scene(home_page_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app_stage.setScene(home_page_scene);
        app_stage.show();
    }

    @FXML
    public void Exit()
    {
        System.exit(EXIT_CODE);
    }

    @FXML
    public void getMoney(MouseEvent event) {
        try {
            client.GetMoney(100,Integer.parseInt(_100.getText()));
            client.GetMoney(500,Integer.parseInt(_500.getText()));
            client.GetMoney(1000,Integer.parseInt(_1000.getText()));
            client.GetMoney(5000,Integer.parseInt(_5000.getText()));
            cash.setText(Long.toString(Main.client.CountCash()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        balans.setText(client.bank.ChkMoney(client.myAcc).toString());
    }

    @FXML
    public void Buy1(MouseEvent event) throws Exception {
        payment1.setVisible(true);
        payment1.setDisable(false);
        payment1.setAccessibleText("500");
        prCost.setText(payment1.getAccessibleText());
    }


    @FXML
    public void Buy2(MouseEvent event)
    {
        payment1.setVisible(true);
        payment1.setDisable(false);
        payment1.setAccessibleText("12000");
        prCost.setText(payment1.getAccessibleText());
    }

    @FXML
    public void Close(MouseEvent event)
    {
        payment1.setVisible(false);
        payment1.setDisable(true);

    }

    @FXML
    public void Buy(MouseEvent event) throws Exception {

        prCost.setText(payment1.getAccessibleText());

        int cost = Integer.parseInt(payment1.getAccessibleText());
        int _100 = Integer.parseInt(_2_100.getText());
        int _500 = Integer.parseInt(_2_500.getText());
        int _1000 = Integer.parseInt(_2_1000.getText());
        int _5000 = Integer.parseInt(_2_5000.getText());
        LinkedList<Money> client_poket;
        client_poket = client.createPocket(_100, _500, _1000, _5000);
        int st = store.SellProduct(cost,client_poket,client);
        if(st!=0)
            System.out.println(st);
        else
            cash.setText(Long.toString(client.CountCash()));
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        balans.setText(client.bank.ChkMoney(client.myAcc).toString());
        cash.setText(Long.toString(client.CountCash()));

    }


}
