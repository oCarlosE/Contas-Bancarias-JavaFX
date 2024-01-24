package com.project;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.fxml.Initializable;

public class Core implements Initializable {
    private ObservableList<Account> ObsList;
    private ObservableList<TransactionType> ObsType;
    private static List<Account> Accounts;
    private List<TransactionType> TrTypes;

    @FXML private ComboBox<Account> CBAcc= new ComboBox<>();
    @FXML private ComboBox<Account> CBAccAux = new ComboBox<>();
    @FXML private ComboBox<TransactionType> CBTrType = new ComboBox<>();


    public Core(){
        TrTypes = new ArrayList<>();
        TrTypes.add(TransactionType.DEPOSIT);
        TrTypes.add(TransactionType.TRANSFER);
        TrTypes.add(TransactionType.WITHDRAW);
        Core.Accounts = App.getAccounts();
    }

    //Account Management
    @FXML private TextField txtRegAgc;
    @FXML private TextField txtRegAcc;
    @FXML private TextField txtRegBank;
    @FXML
    public void CadAcc(){//Register an Account
        try {
            int RegAcc = Integer.parseInt(txtRegAcc.getText());
            int RegAgc = Integer.parseInt(txtRegAgc.getText());

            Account aux = new Account(txtRegBank.getText().toString(),RegAgc, RegAcc);
            boolean exist = false;

            for (Account acc : App.getAccounts()) {
            
                if ((acc.GetId().equals(aux.GetId())) || (acc.GetAgency().equals(aux.GetAgency()))) {
                    exist = true;
                    Alerts.showAlert("Account Already Registered", null, "The Following Account is Already Registered: "+aux.toString(), AlertType.ERROR);
                }
            }
            if (!exist) {
                Core.Accounts.add(aux);
                Alerts.showAlert("Successful Process", null, "Your New Account has been Registered Successfully", AlertType.INFORMATION);
            }
        } catch (Exception e) {
            Alerts.showAlert("Process Error", null, "Only Numbers Can Be Entered", AlertType.ERROR);
        }
        
        
        
    }

    @FXML
    public void RemAcc(){//OK
        //Remove an Account
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText(null);
        alert.setContentText("Your Account Will Be Deleted, As Will All Its Data, Are You Sure?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Account aux = CBAcc.getSelectionModel().getSelectedItem();
            Core.Accounts.remove(aux);
            ObsList = FXCollections.observableArrayList(Accounts);
            CBAcc.setItems(ObsList);
        }
        else{
            Alerts.showAlert("Process Canceled", null, "Your Account Was Not Deleted", AlertType.ERROR);
        }
        
    }

    @FXML
    public void MerAcc(){
        //Merge two Accounts into one
        try {
            Account aux = new Account(CBAcc.getSelectionModel().getSelectedItem().getBank(),CBAcc.getSelectionModel().getSelectedItem().GetAgency(), CBAcc.getSelectionModel().getSelectedItem().GetId());
            aux.SetTransactions(CBAcc.getSelectionModel().getSelectedItem().GetTransactions());

            for (Transaction tr : CBAccAux.getSelectionModel().getSelectedItem().GetTransactions()) {
                aux.AddTransaction(tr);
            }
            App.getAccounts().add(aux);
            App.getAccounts().remove(CBAcc.getSelectionModel().getSelectedItem());
            App.getAccounts().remove(CBAccAux.getSelectionModel().getSelectedItem());
            Accounts = App.getAccounts();
            ObsList = FXCollections.observableArrayList(Accounts);
            CBAcc.setItems(ObsList);
            CBAccAux.setItems(ObsList);
            Alerts.showAlert("Process Successful", null, "The Accounts Were Merged", AlertType.INFORMATION);
        } catch (Exception e) {
            Alerts.showAlert("Process Error", null, "An Error Ocurred in the Process", AlertType.ERROR);
        }
    }

    //Trasactions Management
    @FXML private TextFlow txtExtAcc;
    @FXML
    public void ExtAcc(){
        StringBuilder str = new StringBuilder();
        Account aux = CBAcc.getSelectionModel().getSelectedItem();

        for (Transaction tr : aux.GetTransactions()) {
            str.append(tr.toString());
        }
        Text txt= new Text("\nTransactions: \n"+str.toString()+"Actual Balance: "+aux.GetBalance());
        txt.setFill(Color.BLACK);
        txtExtAcc.getChildren().add(txt);
        //Bank Statement of an Account
    }

    @FXML private TextField txtAddTr;
    @FXML
    public void AddTr(){
        //Add a transaction to an Account
        try {
            Account aux = CBAcc.getSelectionModel().getSelectedItem();
            App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).AddTransaction(new Transaction(Double.parseDouble(txtAddTr.getText()), CBTrType.getSelectionModel().getSelectedItem(), LocalDateTime.now()));
            Alerts.showAlert("Successful Process", null, "Your New Transaction has been Registered\n"+"Actual Balance: "+aux.GetBalance(), AlertType.INFORMATION);
        } catch (Exception e) {
            Alerts.showAlert("Process Error", null, "An Error Ocurred", AlertType.ERROR);
        }
        
    }

    @FXML TextField txtEditLTr;
    @FXML
    public void EditLastTr(){
        //Edit the last transaction of this Account
        try {
            Account aux = App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex());
            Transaction lastTr = aux.GetLastTransaction();

            switch (lastTr.GetType()) {
                case DEPOSIT:
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).setBalance(aux.GetBalance()-lastTr.GetValue());
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).GetTransactions().remove(lastTr);
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).AddTransaction(new Transaction(Double.parseDouble(txtEditLTr.getText()), 
                    CBTrType.getSelectionModel().getSelectedItem(), LocalDateTime.now()));
                    Alerts.showAlert("Process Sucessful", null, "The Last Transaction has been Edited", AlertType.INFORMATION);
                    break;
                case WITHDRAW:
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).setBalance(aux.GetBalance()+lastTr.GetValue());
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).GetTransactions().remove(lastTr);
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).AddTransaction(new Transaction(Double.parseDouble(txtEditLTr.getText()), 
                    CBTrType.getSelectionModel().getSelectedItem(), LocalDateTime.now()));
                    Alerts.showAlert("Process Sucessful", null, "The Last Transaction has been Edited", AlertType.INFORMATION);
                    break;
                case TRANSFER:
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).setBalance(aux.GetBalance()+lastTr.GetValue());
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).GetTransactions().remove(lastTr);
                    App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).AddTransaction(new Transaction(Double.parseDouble(txtEditLTr.getText()), 
                    CBTrType.getSelectionModel().getSelectedItem(), LocalDateTime.now()));
                    Alerts.showAlert("Process Sucessful", null, "The Last Transaction has been Edited", AlertType.INFORMATION);
                    break;
            }
        } catch (Exception e) {
            Alerts.showAlert("Process Error", null, "There was a Problem Editing the Last Transaction", AlertType.ERROR);
        }
    }

    @FXML TextField txtTranfer;
    @FXML
    public void TransferFunds(){
        //Transfer the finance of the Account 1 to Account 2
        try {
            Account Aux1 = CBAcc.getSelectionModel().getSelectedItem();
            Double trValue = Double.parseDouble(txtTranfer.getText());

            if(trValue <= Aux1.GetBalance()){
                App.getAccounts().get(CBAcc.getSelectionModel().getSelectedIndex()).AddTransaction(new Transaction(trValue, TransactionType.TRANSFER, LocalDateTime.now()));
                App.getAccounts().get(CBAccAux.getSelectionModel().getSelectedIndex()).AddTransaction(new Transaction(trValue, TransactionType.DEPOSIT, LocalDateTime.now()));
                Alerts.showAlert("Process Sucessful", null, "Transfer Made Successfully", AlertType.INFORMATION);
            }
            else{
                Alerts.showAlert("Process Failure", null, "Transfer it was not Made", AlertType.ERROR);
            }
        } catch (Exception e) {
            Alerts.showAlert("Process Failure", null, "Transfer it was not Made", AlertType.ERROR);
        }
        
    }

    //General Panel
    @FXML TextFlow txtARecAcc;
    @FXML
    public void ResAccs(){
        try {
            StringBuilder strAux = new StringBuilder();
            for (Account acc : App.getAccounts()) {
                strAux.append("-------------------------------------------------\n");
                strAux.append(acc.toString());
                strAux.append("Transactions: \n");
                for (Transaction tr : acc.GetTransactions()) {
                    strAux.append(tr.toString());
                }
            }
            Text txt = new Text(strAux.toString());
            txt.setFill(Color.BLACK);
            txtARecAcc.getChildren().add(txt);

        } catch (Exception e) {
            Alerts.showAlert("Process Error", null, "An Error Ocurred", AlertType.ERROR);
        }
    }

    @FXML TextFlow txtAResLMonth;
    @FXML
    public void ResLastMonth(){
        try {
            Month aux = LocalDateTime.now().getMonth();
            StringBuilder strAux = new StringBuilder();

            for (Account acc : App.getAccounts()) {
                strAux.append("-------------------------------------------------\n");
                strAux.append(acc.toString());
                strAux.append("Transactions: \n");
                for (Transaction tr : acc.GetTransactions()) {
                    if (tr.getDate().getMonth().equals(aux)) {
                        strAux.append(tr.toString());
                    }
                }
            }
            Text txt= new Text(strAux.toString());
            txt.setFill(Color.BLACK);
            txtAResLMonth.getChildren().add(txt);
        } catch (Exception e) {
            Alerts.showAlert("Process Error", null, "An Error Ocurred", AlertType.ERROR);
        }
        
    }

    @FXML TextFlow txtABal6M;
    @FXML
    public void Bal6Months(){
        try {
            Month mAux;
            StringBuilder strAux = new StringBuilder();
            LocalDateTime date = LocalDateTime.now();
            mAux = date.getMonth();
            for(int i=0; i<6;i++){
                for (Account acc : App.getAccounts()) {
                    strAux.append("-------------------------------------------------\n");
                    strAux.append(acc.toString());
                    double ActulBal = acc.GetBalance();
                    for (Transaction tr : acc.GetTransactions()) {
                        if (tr.getDate().getMonth().equals(mAux)) {
                            switch (tr.GetType()) {
                            case DEPOSIT:
                                ActulBal -=tr.GetValue(); 
                                break;
                            case WITHDRAW:
                                ActulBal +=tr.GetValue();
                                break;
                            case TRANSFER:
                                ActulBal +=tr.GetValue();
                                break;
                            }
                        }
                    }
                    strAux.append("Month: "+mAux.toString()+" | Balance for this Month: "+ActulBal+"\n");
                }
                mAux = mAux.minus(1);
            }
            Text txt = new Text(strAux.toString());
            txt.setFill(Color.BLACK);
            txtABal6M.getChildren().add(txt);
        } catch (Exception e) {
            Alerts.showAlert("Error", null, "An error has Ocurred", AlertType.ERROR);
        }     
    }

    @FXML private TextFlow txtRenPY;
    @FXML
    private void RenPerYear(){
        try {
            StringBuilder strAux = new StringBuilder();
            LocalDateTime date = LocalDateTime.now();
            LocalDateTime dateFuture = LocalDateTime.of(2024, 12, 30, 23, 59,59);
            int nmbMonths = dateFuture.getMonthValue() - date.getMonthValue();

            for (Account acc : App.getAccounts()) {
                double bal = acc.GetBalance();
                double balFinal = bal +(nmbMonths*(0.97)*bal);
                strAux.append("-------------------------------------------------\n");
                strAux.append(acc.toString());
                strAux.append("Actual Balance: "+acc.GetBalance()+"\n");
                strAux.append("Income Tax: 0,97/perMonth\n");
                strAux.append("Balance After One Year of Income: "+balFinal+"\n");
            }
            Text txt = new Text(strAux.toString());
            txt.setFill(Color.BLACK);
            txtRenPY.getChildren().add(txt);
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.showAlert("Error", null, "An error has Ocurred", AlertType.ERROR);
        }
    }

    @FXML
    private void switchToMainView() throws IOException {
        App.setRoot("MainView");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        ObsList = FXCollections.observableArrayList(Accounts);
        ObsType = FXCollections.observableArrayList(TrTypes);
        CBAcc.setItems(ObsList);
        CBAccAux.setItems(ObsList);
        CBTrType.setItems(ObsType);
    }
}
