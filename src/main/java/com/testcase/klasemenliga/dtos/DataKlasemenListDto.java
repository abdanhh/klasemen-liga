package com.testcase.klasemenliga.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataKlasemenListDto {
    private Long teamId;
    private String name;
    private String city;
    private Integer rank;
    private Long points;
    private Long win;
    private Long lost;
    private Long draw;
    private Long numberOfMatch;
    private Long homeGoal;
    private Long awayGoal;
}
