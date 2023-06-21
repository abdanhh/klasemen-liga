package com.testcase.klasemenliga.controllers;

import com.testcase.klasemenliga.dtos.DataKlasemenListDto;
import com.testcase.klasemenliga.payloads.AddMatchPayload;
import com.testcase.klasemenliga.services.TransKlasemenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trans")
public class TransKlasemenController {

    @Autowired
    TransKlasemenService service;

    @GetMapping("/klasemen")
    public List<DataKlasemenListDto> findAllKlasemen() {
        return service.findAllKlasemen();
    }

    @GetMapping("/klasemen/v1")
    public List<DataKlasemenListDto> findAllKlasemens() {
        return service.findKlasemens();
    }

    @PostMapping("/match")
    public String saveDataMatch(@RequestBody List<AddMatchPayload> payloads){
        return service.saveDataMatch(payloads);
    }

    @PostMapping("/statistic")
    public String saveDataStatistic(@RequestBody List<AddMatchPayload> payloads){
        return service.saveDataStatistic(payloads);
    }
}
