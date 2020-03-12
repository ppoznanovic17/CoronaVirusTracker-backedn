package peca.org.demo.model;

import lombok.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class LiveCoronaModel {

    private int mildCondition;
    private int seriousCondition;

    private int recovered;
    private int deaths;


    private int allCases;
    private int activeCases;
    private int closedCases;

    private int mildPercent;
    private int seriousPercent;
    private int recoveredPercent;
    private int deathPercent;



    public LiveCoronaModel(int mildCondition , int seriousCondition , int recovered, int deaths) {
        this.mildCondition = mildCondition;
        this.seriousCondition = seriousCondition;
        this.recovered = recovered;
        this.deaths = deaths;

        allCases = mildCondition + seriousCondition + recovered + deaths;
        activeCases = mildCondition + seriousCondition;
        closedCases = recovered + deaths;

        this.mildPercent = (int)Math.round((mildCondition*1.0)*100 / (mildCondition+seriousCondition));
        this.seriousPercent =  (int)Math.round((seriousCondition*1.0)*100 / (mildCondition+seriousCondition));
        this.recoveredPercent =  (int)Math.round((recovered*1.0)*100 / (recovered+deaths));
        this.deathPercent =  (int)Math.round((deaths*1.0)*100 / (recovered+deaths));

    }
}
