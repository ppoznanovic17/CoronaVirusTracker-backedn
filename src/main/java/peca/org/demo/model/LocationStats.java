package peca.org.demo.model;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Comparator;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Component
public class LocationStats implements Comparable<LocationStats>{


    public static final double myLatitude = 44.787197; //Belgrade, Serbia
    public static final double myLongitude = 20.457273;

    public static int overallCases = 0;
    public static int overallRecovered = 0;
    public static int overallDeaths = 0;
    public static int overallActiveCases = 0;

    public static int todayAllCases = 0;
    public static int todayAllDeaths = 0;
    public static int todayAllRecoveries = 0;

    private static double percentOfDeath = 0;
    private static double percentOfRecovered = 0;



    private String state;
    private String country;

    private int latestTotalCases;
    private int latestTotalRecovered;
    private int latestTotalDeaths;
    private int latestActiveCases;

    private int casesToday;
    private int recoveriesToday;
    private int deathsToday;

    private double longitude;
    private double latitude;





    public static void increaseValue(int a, int b, int c, int d, int e, int f){
        overallCases += a;
        overallDeaths += b;
        overallRecovered += c;
        overallActiveCases = overallCases - (overallDeaths + overallRecovered);

        todayAllCases += d;
        todayAllDeaths += e;
        todayAllRecoveries += f;
    }

    public static void calculate(LocationStats ls){
        percentOfDeath =  overallDeaths /  (1.0 * overallCases);
        percentOfRecovered =  overallRecovered /  (1.0 * overallCases);
        ls.latestActiveCases = ls.latestTotalCases - ( ls.latestTotalRecovered + ls.latestTotalDeaths);
    }

    public static String allString(){
        return "overallCases = " + overallCases +System.lineSeparator() +
                "overallDeaths = " + overallDeaths + System.lineSeparator()+
                "overallRecovered = " + overallRecovered +System.lineSeparator() +
                "overallActiveCases = " + overallActiveCases + System.lineSeparator() +
                "todayAllCases = " + todayAllCases +System.lineSeparator() +
                "todayAllDeaths = " + todayAllDeaths + System.lineSeparator()+
                "todayAllRecoveries = " + todayAllRecoveries + System.lineSeparator() +
                "percentOfDeath = " + percentOfDeath + System.lineSeparator()+
                "percentOfRecovered = " + percentOfRecovered + System.lineSeparator();
    }

    private static double calcDist(LocationStats x){

        double xDist =   x.getLongitude() - myLongitude;
        double yDist = x.getLatitude() - myLatitude;
        double dist = Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));

        return dist;
    }

    @Override
    public int compareTo(LocationStats o) {
            return Double.compare(calcDist(this), calcDist(o));
        /*else
            return Integer.compare(this.getLatestTotalCases(),o.getLatestTotalCases());*/
    }

    public static Comparator<LocationStats> FruitNameComparator
            = (ls1, ls2) -> {

                Integer cases1 = ls1.getLatestTotalCases();
                Integer cases2 = ls2.getLatestTotalCases();


                return cases1.compareTo(cases2);


            };

}
