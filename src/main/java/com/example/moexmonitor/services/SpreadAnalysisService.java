// SpreadAnalysisService.java
package com.example.moexmonitor.services;

import com.example.moexmonitor.models.FuturesInstrument;
import com.example.moexmonitor.models.SpotInstrument;
import com.example.moexmonitor.models.SpreadInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SpreadAnalysisService {
    private final MoexApiClient moexApiClient;
    private final TelegramNotificationService telegramService;

    private Map<String, SpreadInfo> spreadData = new HashMap<>();
    private LocalTime marketOpenTime = LocalTime.of(10, 0); // Время открытия биржи

    @Autowired
    public SpreadAnalysisService(MoexApiClient moexApiClient, TelegramNotificationService telegramService) {
        this.moexApiClient = moexApiClient;
        this.telegramService = telegramService;
    }

    public List<SpotInstrument> getSpots() {
        return moexApiClient.fetchSpotInstruments();
    }

    public List<FuturesInstrument> getFutures() {
        return moexApiClient.fetchFuturesInstruments();
    }

    @Scheduled(fixedRate = 60000) // Обновление каждую минуту
    public Map<String, SpreadInfo> updateSpreadData() {
        List<SpotInstrument> spots = moexApiClient.fetchSpotInstruments();
        List<FuturesInstrument> futures = moexApiClient.fetchFuturesInstruments();

        // Фильтрация фьючерсов на акции
        List<FuturesInstrument> stockFutures = futures.stream()
                .filter(f -> f.getTicker().matches("^[A-Z]{4}-\\d{2}\\.\\d{2}$"))
                .collect(Collectors.toList());

        for (FuturesInstrument future : stockFutures) {
            String underlyingTicker = future.getTicker().substring(0, 4);
            spots.stream()
                    .filter(spot -> spot.getTicker().equals(underlyingTicker))
                    .findFirst()
                    .ifPresent(spot -> {
                        String key = spot.getTicker() + "/" + future.getTicker();
                        SpreadInfo spreadInfo = spreadData.getOrDefault(key, new SpreadInfo());

                        double currentSpread = future.getPrice() - spot.getPrice();
                        double spreadDifference = currentSpread - spreadInfo.getOpeningSpread();

                        // Если это первое обновление за день, устанавливаем openingSpread
                        if (spreadInfo.getLastUpdated() == null ||
                                spreadInfo.getLastUpdated().toLocalTime().isBefore(marketOpenTime)) {
                            spreadInfo.setOpeningSpread(currentSpread);
                        }

                        spreadInfo.setSpotTicker(spot.getTicker());
                        spreadInfo.setFuturesTicker(future.getTicker());
                        spreadInfo.setCurrentSpread(currentSpread);
                        spreadInfo.setSpreadDifference(spreadDifference);
                        spreadInfo.setSpotPrice(spot.getPrice());
                        spreadInfo.setFuturesPrice(future.getPrice());
                        spreadInfo.setLastUpdated(LocalDateTime.now());

                        // Проверка сигналов
                        checkSpreadSignals(spreadInfo);

                        spreadData.put(key, spreadInfo);
                    });
        }
        return spreadData;
    }

    private void checkSpreadSignals(SpreadInfo spreadInfo) {
        // Здесь должна быть логика определения точек входа/выхода
        // Например, если спред превышает среднее историческое значение + 2 стандартных отклонения
        // Это упрощенный пример - в реальности нужна более сложная логика

        double threshold = spreadInfo.getOpeningSpread() * 1.5; // Примерный порог
        spreadInfo.setEntrySignal(spreadInfo.getCurrentSpread() > threshold);
        spreadInfo.setExitSignal(spreadInfo.getCurrentSpread() <= spreadInfo.getOpeningSpread());

        if (spreadInfo.isEntrySignal()) {
            telegramService.sendNotification("ENTRY SIGNAL: " + spreadInfo.getSpotTicker() +
                    "/" + spreadInfo.getFuturesTicker() + " spread: " + spreadInfo.getCurrentSpread());
        }

        if (spreadInfo.isExitSignal()) {
            telegramService.sendNotification("EXIT SIGNAL: " + spreadInfo.getSpotTicker() +
                    "/" + spreadInfo.getFuturesTicker() + " spread returned to: " + spreadInfo.getCurrentSpread());
        }
    }

    public List<SpreadInfo> getAllSpreadData() {
        return new ArrayList<>(spreadData.values());
    }
}