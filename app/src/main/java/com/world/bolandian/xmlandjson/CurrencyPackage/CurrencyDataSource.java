package com.world.bolandian.xmlandjson.CurrencyPackage;

import com.world.bolandian.xmlandjson.StreamIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bolandian on 03/06/2017.
 */

public class CurrencyDataSource {

    public interface OnCurrencyArrivedListener{
        void onCurrencyArrived(List<Currency> data , Exception e);
    }

    public static void getCurrencies(final OnCurrencyArrivedListener listener){
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://www.boi.org.il/currency.xml");
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    String xml = StreamIO.read(in);
                    ArrayList<Currency> currencies = parse(xml);
                    listener.onCurrencyArrived(currencies,null);
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onCurrencyArrived(null,e);
                }
            }
        });
    }

    private static ArrayList<Currency> parse(String xml) {
        Document document = Jsoup.parse(xml);
        ArrayList<Currency> currencies = new ArrayList<>();

        Elements allCurrencies = document.getElementsByTag("CURRENCY");
        for (Element c : allCurrencies) {
            String name = c.getElementsByTag("NAME").get(0).text();
            int unit = Integer.valueOf(c.getElementsByTag("UNIT").get(0).text());
            String currencyCode = c.getElementsByTag("CURRENCYCODE").get(0).text();
            String country = c.getElementsByTag("COUNTRY").get(0).text();
            double rate = Double.valueOf(c.getElementsByTag("RATE").get(0).text());
            double change = Double.valueOf(c.getElementsByTag("CHANGE").get(0).text());

            Currency currency = new Currency(name, unit, currencyCode, country, rate, change);
            currencies.add(currency);
        }
        return currencies;
    }

    public static class Currency{
        private String name;
        private int unit;
        private String currencyCode;
        private String country;
        private double rate;
        private double change;

        public Currency(String name, int unit, String currencyCode, String country, double rate, double change) {
            this.name = name;
            this.unit = unit;
            this.currencyCode = currencyCode;
            this.country = country;
            this.rate = rate;
            this.change = change;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getUnit() {
            return unit;
        }

        public void setUnit(int unit) {
            this.unit = unit;
        }

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public double getChange() {
            return change;
        }

        public void setChange(double change) {
            this.change = change;
        }
    }
}
