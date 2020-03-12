package peca.org.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import peca.org.demo.model.LiveCoronaModel;

import java.io.IOException;


@Service
public class LiveStatsService {

    private static String liveUrl = "https://www.worldometers.info/coronavirus/";

    private static LiveCoronaModel lcm = new LiveCoronaModel();

    @Scheduled(fixedDelay = 10000, initialDelay = 0)
    public static void live() throws IOException {


        Document doc = Jsoup.connect(liveUrl).get();


        Elements numbers = doc.getElementsByClass("number-table");


        lcm = new LiveCoronaModel(Integer.parseInt(numbers.get(0).html().replaceAll(",", "")),
                Integer.parseInt(numbers.get(1).html().replaceAll(",", "")), Integer.parseInt(numbers.get(2).html().replaceAll(",", ""))
                , Integer.parseInt(numbers.get(3).html().replaceAll(",", "")));
    }

    public LiveCoronaModel getLive(){
        return lcm;
    }

}
