package peca.org.demo.model;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter@Setter
@Builder
@AllArgsConstructor@NoArgsConstructor
@ToString
@Component
public class NewsModel {

    private String title;
    private String description;
    private String link;
    private String picture;
    private String date;



}
