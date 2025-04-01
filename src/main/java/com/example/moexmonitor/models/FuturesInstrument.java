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
    public String underlyingAsset;
    public double price;
    public LocalDate expirationDate;

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setUnderlyingAsset(String underlyingAsset) {
        this.underlyingAsset = underlyingAsset;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTicker() {
        return ticker;
    }

    public String getUnderlyingAsset() {
        return underlyingAsset;
    }

    public double getPrice() {
        return price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getCurrency() {
        return currency;
    }

    public String currency;

}