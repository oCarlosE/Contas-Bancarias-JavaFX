package com.project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.Alert.AlertType;

public class Account implements Serializable{
    private static final long serialVersionUID = 1L;
    private String Bank;
    private int agency;
    private int id;
    private List<Transaction> transactions;
    private double balance;

    public Account(String Bank,int agency,int id){
        this.agency = agency;
        this.id = id;
        this.Bank = Bank;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getBank() {
        return Bank;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void AddTransaction(Transaction t){
        
        switch (t.GetType()) {
            case DEPOSIT:
                if (t.GetValue()> 0) {
                    this.balance += t.GetValue();
                    transactions.add(t);
                }
                else{
                    Alerts.showAlert("Invalid Value", null, "Negative Value Error", AlertType.ERROR);
                }
                break;

            case WITHDRAW:
                if (t.GetValue()<=balance) {
                    balance-= t.GetValue();
                    transactions.add(t);
                }
                else{
                    Alerts.showAlert("Invalid Value", null, "Amount Greater than the Available Balance", AlertType.ERROR);
                }
                break;

            case TRANSFER:
                if (t.GetValue()<=balance) {
                    balance-= t.GetValue();
                    transactions.add(t);
                }
                else{
                    Alerts.showAlert("Invalid Value", null, "Amount Greater than the Available Balance", AlertType.ERROR);
                }
        }
        Collections.sort(transactions);
    }

    public void SetTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> GetTransactions() {
        return transactions;
    }

    public Transaction GetLastTransaction() {
        return transactions.get(0);
    }

    public double GetBalance(){
        return balance;
    }

    public Integer GetAgency() {
        return agency;
    }

    public Integer GetId() {
        return id;
    }

    @Override
    public String toString(){
        return "Bank: "+Bank+" | Ag: "+agency+" | Acc: "+id+"\n";
    }
}
