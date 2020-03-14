package peca.org.demo.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peca.org.demo.model.LiveCoronaModel;
import peca.org.demo.service.LiveStatsService;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping(path = "/live")
public class LiveCtrl {

    @Autowired
    private LiveStatsService service;

    @GetMapping
    public LiveCoronaModel getLive(){
        return service.getLive();
    }

}
