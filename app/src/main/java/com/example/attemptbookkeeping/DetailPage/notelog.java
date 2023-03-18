package com.example.attemptbookkeeping.DetailPage;

public class notelog {
    private String time;
    private String type;
    private  String typeS;
    private double amount;

    public double getAmount() {
        return amount;
    }

    public String getTypeS() {
        return typeS;
    }

    public String getTime() {
        return time;
    }

    public String getType() {
        return type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setType(String type) {
        this.type = type;
    }
}