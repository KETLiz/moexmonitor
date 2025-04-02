// FuturesInstrument.java
package com.example.moexmonitor.models;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
public class FuturesInstrument {
    @Getter
    public String ticker;
    private double open; //цена открытия
    public double price; //текущая цена

    public void setOpen(double open) {
        this.open = open;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getTicker() {
        return ticker;
    }


    public double getPrice() {
        return price;
    }

    public double getOpen() {
        return open;
    }
}