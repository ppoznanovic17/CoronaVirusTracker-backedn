package peca.org.demo.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import peca.org.demo.model.NewsModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BlicJSoupService {

    private static ArrayList<NewsModel> news = new ArrayList<>();

    private static String blicUrl = "https://www.blic.rs/search?q=korona";

    @Scheduled(fixedDelay = 60000, initialDelay = 0)
    private static void blic()throws IOException {

        int numberOfNews = 23;

        Document doc = Jsoup.connect(blicUrl).get();
        //doc.select("p").forEach(System.out::println);

        Elements content = doc.getElementsByTag("article");
        Elements bestFive = new Elements();
        for (int i = 0; i < numberOfNews; i++) {
            bestFive.add(content.get(i));
        }

        Document list = Jsoup.parse(bestFive.html());
        Document pom;

        //get titles and link
        Elements titlesContainer = list.getElementsByTag("h3");
        pom = Jsoup.parse(titlesContainer.html());


        //title start
        List<String> titles = new ArrayList<>();
        for (Element element : titlesContainer) {
            Element title = element.getElementsByTag("a").first();
            titles.add(title.html());
        }


        //links start
        List<String> links = new ArrayList<>();
        for (Element element : titlesContainer) {
            Element title = element.getElementsByTag("a").first();
            links.add(title.attr("href"));
        }


        // dates start
        List<String> dates = new ArrayList();
        Elements datesEl = list.getElementsByTag("span");
        for (Element element : datesEl) {
            String date = element.html().toString().substring(0,17);
            dates.add(date);
        }

        //description start
        List<String> descriptions = new ArrayList();
        Elements descriptionEl = list.getElementsByTag("p");
        for (Element element : descriptionEl) {
            descriptions.add(element.html());
        }

        //picture link start
        List<String> pictureLinks = new ArrayList();
        Elements pictureLinksEl = list.getElementsByTag("img");
        for (Element element : pictureLinksEl) {
            pictureLinks.add(element.attr("src"));
        }


        // creating news items
        for (int i = 0; i < numberOfNews; i++) {
            String title = titles.get(i);
            String description = descriptions.get(i);
            String date = dates.get(i);
            String pictureLink = pictureLinks.get(i);
            String link = links.get(i);

            NewsModel model = NewsModel.builder()
                    .title(title)
                    .description(description)
                    .link(link)
                    .date(date).
                    picture(pictureLink).build();

            news.add(model);

        }



    }

    public List<NewsModel> blicNews(int of, int lim){
        return news.subList(of*lim, of*lim + lim);
    }

}
