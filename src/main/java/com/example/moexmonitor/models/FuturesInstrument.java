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
    private double prevPrice; //цена открытия (в секции <data id="securities">)
    public double last; //текущая цена (в секции <data id="marketdata">)

    public void setOpen(double open) {
        this.prevPrice = open;
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

    public double getOpen() {
        return prevPrice;
    }
}