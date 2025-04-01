// MoexApiClient.java
package com.example.moexmonitor.services;

import com.example.moexmonitor.models.FuturesInstrument;
import com.example.moexmonitor.models.SpotInstrument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MoexApiClient {
    private final RestTemplate restTemplate;

    @Value("${moex.api.base.url}")
    private String moexBaseUrl;

    @Value("${moex.spot.market}")
    private String spotMarket;

    @Value("${moex.futures.market}")
    private String futuresMarket;

    public MoexApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<SpotInstrument> fetchSpotInstruments() {
        String url = moexBaseUrl + "/engines/stock/markets/" + spotMarket + "/securities.xml";
        String response = restTemplate.getForObject(url, String.class);
        return parseSpotInstruments(response);
    }

    public List<FuturesInstrument> fetchFuturesInstruments() {
        String url = moexBaseUrl + "/engines/futures/markets/forts/securities.xml";
        String response = restTemplate.getForObject(url, String.class);
        return parseFuturesInstruments(response);
    }

    private List<SpotInstrument> parseSpotInstruments(String xml) {
        List<SpotInstrument> instruments = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                Element row = (Element) rows.item(i);
                SpotInstrument instrument = new SpotInstrument();
                instrument.setTicker(row.getAttribute("SECID"));
                instrument.setName(row.getAttribute("SHORTNAME"));
                if(row.getAttribute("LAST").isEmpty()) {
                    instrument.setPrice(0.0);
                } else {
                    instrument.setPrice(Double.parseDouble(row.getAttribute("LAST")));
                }

                instrument.setCurrency(row.getAttribute("CURRENCYID"));
                instrument.setBoardId(row.getAttribute("BOARDID"));
                instruments.add(instrument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instruments;
    }

    private List<FuturesInstrument> parseFuturesInstruments(String xml) {
        List<FuturesInstrument> instruments = new ArrayList<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            NodeList rows = doc.getElementsByTagName("row");
            for (int i = 0; i < rows.getLength(); i++) {
                Element row = (Element) rows.item(i);
                FuturesInstrument instrument = new FuturesInstrument();
                instrument.setTicker(row.getAttribute("SECID"));
                instrument.setUnderlyingAsset(row.getAttribute("SHORTNAME"));
                if(row.getAttribute("LAST").isEmpty()) {
                    instrument.setPrice(0.0);
                } else {
                    instrument.setPrice(Double.parseDouble(row.getAttribute("LAST")));
                }

                // Парсинг даты экспирации и других полей
                instruments.add(instrument);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instruments;
    }
}