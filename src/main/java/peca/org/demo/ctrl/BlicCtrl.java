package peca.org.demo.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import peca.org.demo.model.NewsModel;
import peca.org.demo.service.BlicJSoupService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/blic")
public class BlicCtrl {

    @Autowired
    BlicJSoupService service;

    @GetMapping(path = "/offset={of}&limit={lim}")
    public List<NewsModel> blicLess(@PathVariable int of, @PathVariable int lim){
        return service.blicNews(of-1, lim);
    }



}
