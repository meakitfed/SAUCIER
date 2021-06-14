package saucier.chienbot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saucier.chienbot.domain.classes.CandleStickData;
import saucier.chienbot.repositories.GraphRepository;

import java.util.List;

@RestController
@RequestMapping("api/")
public class GraphController {

    @Autowired
    private GraphRepository graphRepository;

    @GetMapping("data")
    public List<CandleStickData> getTestData(){
        return graphRepository.getTestEvents();
    }

    @GetMapping("test/data")
    public List<CandleStickData> getData(){
        return graphRepository.getEvents();
    }
}
