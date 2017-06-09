package com.world.bolandian.xmlandjson.YnetPackage;

import com.world.bolandian.xmlandjson.StreamIO;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Bolandian on 10/06/2017.
 */

public class YnetDataSource {

    public interface OnYnetArrivedListener{
        public void onYnetArrvied(List<Ynet> data , Exception e);
    }

    public static void getYnet(final OnYnetArrivedListener listener){
        Executor service = Executors.newSingleThreadExecutor();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://www.ynet.co.il/Integration/StoryRss2.xml");
                    URLConnection con = url.openConnection();
                    InputStream in = con.getInputStream();
                    String xml = StreamIO.read(in, "Windows-1255");
                    List<Ynet> data = parse(xml);
                    listener.onYnetArrvied(data, null);
                }catch (Exception e){
                    e.printStackTrace();
                    listener.onYnetArrvied(null,e);
                }
            }
        });
    }

    private static List<Ynet> parse(String xml) {
        return null;
    }

    public static class Ynet{
        private String title;
        private String link;
        private String description;
        private String image;

        public Ynet(String title, String link, String description, String image) {
            this.title = title;
            this.link = link;
            this.description = description;
            this.image = image;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
