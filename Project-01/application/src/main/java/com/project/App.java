package com.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {
    private static List<Account> Accounts;
    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("PROJECT IT-ACADEMY19");
        scene = new Scene(loadFXML("MainView"), 600, 400);
        stage.setScene(scene);
        stage.show();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void Init(){
        Accounts.add(new Account("Bradesco",1234, 5555999));
        Accounts.add(new Account("Santander",7894, 3294567));
        
        Accounts.get(0).AddTransaction(new Transaction(1000, TransactionType.DEPOSIT, LocalDateTime.of(2024, 01, 3, 11, 3, 15)));
        Accounts.get(0).AddTransaction(new Transaction(150, TransactionType.DEPOSIT, LocalDateTime.of(2023, 02, 7, 12, 4, 55)));
        Accounts.get(0).AddTransaction(new Transaction(200, TransactionType.WITHDRAW, LocalDateTime.of(2014, 03, 24, 1, 34, 25)));
        Accounts.get(0).AddTransaction(new Transaction(150, TransactionType.WITHDRAW, LocalDateTime.of(2015, 04, 22, 9, 50, 13)));
        Accounts.get(0).AddTransaction(new Transaction(50, TransactionType.DEPOSIT, LocalDateTime.of(2023, 05, 13, 15, 14, 15)));
        Accounts.get(0).AddTransaction(new Transaction(600, TransactionType.DEPOSIT, LocalDateTime.of(2024, 06, 17, 00, 13, 47)));
        Accounts.get(0).AddTransaction(new Transaction(500, TransactionType.TRANSFER, LocalDateTime.of(2022, 07, 21, 13, 21, 59)));
        Accounts.get(0).AddTransaction(new Transaction(200, TransactionType.DEPOSIT, LocalDateTime.of(2021, 12, 20, 23, 33, 23)));
        Accounts.get(0).AddTransaction(new Transaction(1000, TransactionType.DEPOSIT, LocalDateTime.of(2017, 11, 30, 22, 36, 12)));
        Accounts.get(0).AddTransaction(new Transaction(500, TransactionType.WITHDRAW, LocalDateTime.of(2018, 10, 14, 12, 34, 9)));

        Accounts.get(1).AddTransaction(new Transaction(976, TransactionType.DEPOSIT, LocalDateTime.of(2024, 8, 1, 01, 12, 13)));
        Accounts.get(1).AddTransaction(new Transaction(5412, TransactionType.DEPOSIT, LocalDateTime.of(2024, 9, 30, 02, 23, 34)));
        Accounts.get(1).AddTransaction(new Transaction(120, TransactionType.WITHDRAW, LocalDateTime.of(2024, 6, 29, 03, 15, 56)));
        Accounts.get(1).AddTransaction(new Transaction(350, TransactionType.WITHDRAW, LocalDateTime.of(2023, 11, 15, 04, 35, 34)));
        Accounts.get(1).AddTransaction(new Transaction(244, TransactionType.DEPOSIT, LocalDateTime.of(2023, 11, 13, 05, 43, 59)));
        Accounts.get(1).AddTransaction(new Transaction(657, TransactionType.DEPOSIT, LocalDateTime.of(2023, 12, 23, 06, 31, 23)));
        Accounts.get(1).AddTransaction(new Transaction(500, TransactionType.TRANSFER, LocalDateTime.of(2022, 1, 17, 07, 56, 54)));
        Accounts.get(1).AddTransaction(new Transaction(11000, TransactionType.DEPOSIT, LocalDateTime.of(2022, 04, 25, 9, 54, 35)));
        Accounts.get(1).AddTransaction(new Transaction(100, TransactionType.DEPOSIT, LocalDateTime.of(2021, 02, 4, 8, 23, 35)));
        Accounts.get(1).AddTransaction(new Transaction(2550, TransactionType.WITHDRAW, LocalDateTime.of(2021, 07, 1, 12, 34, 24)));
    }

    public static List<Account> getAccounts() {
        return Accounts;
    }

    public App(){
        Accounts = new ArrayList<>();
        Init();
    }

    public static void main(String[] args) {
        launch();
    }

}