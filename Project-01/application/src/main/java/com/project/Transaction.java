package com.project;

import java.time.LocalDateTime;

public class Transaction  implements Comparable<Transaction>{
    private double value;
    private TransactionType type;
    private LocalDateTime date;
    
    public Transaction(double value, TransactionType type, LocalDateTime date){
        this.value = value;
        this.type = type;
        this.date = date;
    }

    public double GetValue() {
        return value;
    }

    public TransactionType GetType() {
        return type;
    }
    
    public LocalDateTime getDate() {
        return date;
    }

    public String GetDate() {
        return date.getDayOfMonth()+"/"+date.getMonthValue()+"/"+date.getYear()+" Hour: "+date.getHour()+":"+date.getMinute()+":"+date.getSecond();
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Value: "+value+" | Type: "+type+" | Date: "+GetDate()+"\n";
    }

    @Override
    public int compareTo(Transaction other) {
        if (this.date.isAfter(other.date) ) {
            return -1;
        }
        else if (this.date.equals(other.date)) {
            return 0;
        }
        else{
            return 1;
        }
    }
}
