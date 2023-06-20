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

    @PostMapping("/match")
    public String saveDataMatch(@RequestBody List<AddMatchPayload> payloads){
        return service.saveDataMatch(payloads);
    }
}
