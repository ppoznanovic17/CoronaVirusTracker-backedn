package peca.org.demo.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import peca.org.demo.model.LocationStats;

import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient ;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoronaVirusDataService {

    public static List<LocationStats> allStats = new ArrayList<>();

    @Scheduled(fixedDelay = 28512000, initialDelay = 0)
    public void fetchVirusData() throws IOException, InterruptedException {
        //System.out.println("zarazeno");
        List<LocationStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        int i = 0;
        for (CSVRecord record : records) {

            LocationStats locationStats = new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));

            locationStats.setLatitude(Double.parseDouble(record.get("Lat")));
            locationStats.setLongitude(Double.parseDouble(record.get("Long")));


            try{

                 locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-1)));
                int pom = Integer.parseInt(record.get(record.size()-1));
                //System.out.print(pom+ "  ");
                int pom2 = Integer.parseInt(record.get(record.size()-2));
                // System.out.println(pom2+ "         " +(pom-pom2));

                locationStats.setCasesToday(pom-pom2);
            } catch(NumberFormatException ex){

                locationStats.setLatestTotalCases(Integer.parseInt(record.get(record.size()-2)));
                locationStats.setCasesToday(0);
            }



            newStats.add(locationStats);
        }
        allStats = newStats;


    }

    @Scheduled(fixedDelay = 28512000, initialDelay = 6000)
        public void fetchVirusDataDeaths() throws IOException, InterruptedException {

            //System.out.println("umrlo");
            ArrayList<LocationStats> newStats = new ArrayList(allStats);

            HttpClient client = HttpClient.newHttpClient();
        String VIRUS_DATA_URL_DEATHS = "https://raw.githubusercontent.com/ppoznanovic17/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
        HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(VIRUS_DATA_URL_DEATHS))
                    .build();

            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


            StringReader csvBodyReader = new StringReader(httpResponse.body());
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

            int i = 0 ;
            for (CSVRecord record : records) {
                try{

                    int pom = Integer.parseInt(record.get(record.size()-1));
                    //System.out.print(pom+ "  ");
                    int pom2 = Integer.parseInt(record.get(record.size()-2));

                    newStats.get(i).setDeathsToday(pom - pom2);

                    newStats.get(i).setLatestTotalDeaths(pom);

                } catch(NumberFormatException ex){ // handle your exception

                    newStats.get(i).setDeathsToday(0);

                    newStats.get(i).setLatestTotalDeaths(Integer.parseInt(record.get(record.size()-2)));


                }

                i++;


            }
            allStats = newStats;

    }

    @Scheduled(fixedDelay = 28512000, initialDelay = 12000)
    public void fetchVirusDataRecovered() throws IOException, InterruptedException {



        //System.out.println("oporavljeno");
        List<LocationStats> newStats = new ArrayList(allStats);

        HttpClient client = HttpClient.newHttpClient();
        String VIRUS_DATA_URL_RECOVERS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Recovered.csv";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL_RECOVERS))
                .build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());


        StringReader csvBodyReader = new StringReader(httpResponse.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        int i = 0;
        for (CSVRecord record : records) {

            try{

                int pom = Integer.parseInt(record.get(record.size()-1));
                //System.out.print(pom+ "  ");
                int pom2 = Integer.parseInt(record.get(record.size()-2));

                newStats.get(i).setRecoveriesToday(pom - pom2);

                newStats.get(i).setLatestTotalRecovered(pom);

            } catch(NumberFormatException ex){ // handle your exception

                newStats.get(i).setRecoveriesToday(0);

                newStats.get(i).setLatestTotalRecovered(Integer.parseInt(record.get(record.size()-2)));


            }




            i++;
        }
        allStats = newStats;
    }

    @Scheduled(fixedDelay = 28512000, initialDelay = 24000)
    public void calculateAll() throws IOException, InterruptedException {


        //System.out.println("racunaj");
        for(LocationStats x: allStats){
            LocationStats.increaseValue(x.getLatestTotalCases(), x.getLatestTotalDeaths(), x.getLatestTotalRecovered(), x.getCasesToday(), x.getDeathsToday(), x.getRecoveriesToday());
            LocationStats.calculate(x);
        }


    }

    public static ArrayList<LocationStats> sortedByNumberOfCases(){
        ArrayList<LocationStats> list = new ArrayList(allStats);
        int length = list.size();
        for (int i = 0; i < length-1 ; i++) {
            for (int j = 0; j < length-i-1; j++) {
                if(list.get(j).getLatestTotalCases() < list.get(j+1).getLatestTotalCases()){
                    LocationStats temp1 = list.get(j);
                    LocationStats temp2 = list.get(j+1);
                    list.remove(j);
                    list.add(j, temp2);
                    list.remove(j+1);
                    list.add(j+1, temp1);
                }
            }

        }

        return list;
    }



}
