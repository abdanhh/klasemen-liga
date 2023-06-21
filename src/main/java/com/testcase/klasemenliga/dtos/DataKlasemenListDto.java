package com.testcase.klasemenliga.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataKlasemenListDto {
    @JsonProperty("id")
    private Long teamId;
    private String name;
    private String city;
    private Integer rank;
    private Long points;
    private Long win;
    private Long lost;
    private Long draw;
    @JsonProperty("number_of_match")
    private Long numberOfMatch;
    @JsonProperty("home_goal")
    private Long homeGoal;
    @JsonProperty("away_goal")
    private Long awayGoal;
}
