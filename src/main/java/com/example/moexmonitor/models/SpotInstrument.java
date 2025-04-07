// SpotInstrument.java
package com.example.moexmonitor.models;

import lombok.Data;

@Data
public class SpotInstrument {
    public String ticker;
    private double open; //цена открытия (в секции <data id="marketdata">)
    public double last; //текущая цена



    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }


    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(double price) {
        this.last = price;
    }


    public String getTicker() {
        return ticker;
    }

    public double getPrice() {
        return last;
    }

    @Override
    public String toString() {
        return "{" +
                "ticker='" + ticker + '\'' +
                ", open=" + open +
                ", last=" + last +
                '}';
    }
}
