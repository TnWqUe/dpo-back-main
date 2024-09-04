package ru.mpei.fqw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mpei.fqw.model.FaultCurrentModel;
import ru.mpei.fqw.service.ComtradeService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class Controller {

    @Autowired
    private ComtradeService service;

    @GetMapping("/current")
    public String getCurrent() {
        return service.getCurrent();
    }

    @GetMapping("/scopes")
    public String getJsonData(){
        return service.comtradeToJSON();
    }
    @GetMapping("/a1")
    public List<FaultCurrentModel> getFaultCurrentInfoA1(){
        return service.getFaultCurrentInfoA1();
    }

    @GetMapping("/b1")
    public List<FaultCurrentModel> getFaultCurrentInfoB1(){
        return service.getFaultCurrentInfoB1();
    }

    @GetMapping("/c1")
    public List<FaultCurrentModel> getFaultCurrentInfoC1(){
        return service.getFaultCurrentInfoC1();
    }

    @GetMapping("/a2")
    public List<FaultCurrentModel> getFaultCurrentInfoA2(){
        return service.getFaultCurrentInfoA2();
    }

    @GetMapping("/b2")
    public List<FaultCurrentModel> getFaultCurrentInfoB2(){
        return service.getFaultCurrentInfoB2();
    }

    @GetMapping("/c2")
    public List<FaultCurrentModel> getFaultCurrentInfoC2(){
        return service.getFaultCurrentInfoC2();
    }

    @GetMapping("/a3")
    public List<FaultCurrentModel> getFaultCurrentInfoA3(){
        return service.getFaultCurrentInfoA3();
    }

    @GetMapping("/b3")
    public List<FaultCurrentModel> getFaultCurrentInfoB3(){
        return service.getFaultCurrentInfoB3();
    }

    @GetMapping("/c3")
    public List<FaultCurrentModel> getFaultCurrentInfoC3(){
        return service.getFaultCurrentInfoC3();
    }

    @GetMapping("/info")
    public List<FaultCurrentModel> getFaultCurrentInfo(String nameCurrent){
        return service.getFaultCurrentInfo(nameCurrent);
    }

}
