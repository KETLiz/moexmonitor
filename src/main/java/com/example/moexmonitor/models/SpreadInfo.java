// SpreadInfo.java
package com.example.moexmonitor.models;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class SpreadInfo {
    public String spotTicker;
    public String futuresTicker;
    public double openingSpread;
    public double currentSpread;
    public double spreadDifference;
    public double spotPrice;
    public double futuresPrice;
    public LocalDateTime lastUpdated;
    public boolean entrySignal;
    public boolean exitSignal;



    public String getSpotTicker() {
        return spotTicker;
    }

    public String getFuturesTicker() {
        return futuresTicker;
    }

    public double getOpeningSpread() {
        return openingSpread;
    }

    public double getCurrentSpread() {
        return currentSpread;
    }

    public double getSpreadDifference() {
        return spreadDifference;
    }

    public double getSpotPrice() {
        return spotPrice;
    }

    public double getFuturesPrice() {
        return futuresPrice;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public boolean isEntrySignal() {
        return entrySignal;
    }

    public boolean isExitSignal() {
        return exitSignal;
    }

    public void setSpotTicker(String spotTicker) {
        this.spotTicker = spotTicker;
    }

    public void setFuturesTicker(String futuresTicker) {
        this.futuresTicker = futuresTicker;
    }

    public void setOpeningSpread(double openingSpread) {
        this.openingSpread = openingSpread;
    }

    public void setCurrentSpread(double currentSpread) {
        this.currentSpread = currentSpread;
    }

    public void setSpreadDifference(double spreadDifference) {
        this.spreadDifference = spreadDifference;
    }

    public void setSpotPrice(double spotPrice) {
        this.spotPrice = spotPrice;
    }

    public void setFuturesPrice(double futuresPrice) {
        this.futuresPrice = futuresPrice;
    }

    public void setEntrySignal(boolean entrySignal) {
        this.entrySignal = entrySignal;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setExitSignal(boolean exitSignal) {
        this.exitSignal = exitSignal;
    }

}