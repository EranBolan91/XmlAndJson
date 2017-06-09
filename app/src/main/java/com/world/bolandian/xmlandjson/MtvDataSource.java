package com.world.bolandian.xmlandjson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Bolandian on 03/06/2017.
 */

public class MtvDataSource {

    public interface OnMtvNewsArrivedListener{
        void onMtvNewsArrived(ArrayList<Mtv> data , Exception e);
    }


    public static void getMtvNews(final OnMtvNewsArrivedListener listener){
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://www.mtv.com/news/feed/?redirected_from=www.mtv.com/rss/news/news_full.jhtml");
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    String xml = StreamIO.read(in);
                    ArrayList<Mtv> result = parse(xml);
                    listener.onMtvNewsArrived(result,null);

                }catch (Exception e){
                 e.printStackTrace();
                    listener.onMtvNewsArrived(null,e);
                }
            }
        });
        service.shutdown();
    }

    private static ArrayList<Mtv> parse(String xml) {
        Document document = Jsoup.parse(xml);
        ArrayList<Mtv> mtvs = new ArrayList<>();

        Elements allMtvNews = document.getElementsByTag("item");
        for (Element m: allMtvNews) {
            String title = m.getElementsByTag("title").get(0).text();
            String link = m.getElementsByTag("link").get(0).text();
            String date = m.getElementsByTag("pubDate").get(0).text().replace("+0000","");
            String description = m.getElementsByTag("description").get(0).text().replace("<![CDATA[","").replace("]]>","");

            Mtv mtv = new Mtv(title,link,date,description);
            mtvs.add(mtv);
        }
        return mtvs;
    }


    public static class Mtv {
        private String title;
        private String link;
        private String date;
        private String description;

        public Mtv(String title, String link, String date, String description) {
            this.title = title;
            this.link = link;
            this.date = date;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Mtv{" +
                    "title='" + title + '\'' +
                    ", link='" + link + '\'' +
                    ", date='" + date + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
