package com.testcase.klasemenliga.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsPayload {
    private Long homeTeamGoal;
    private Long awayTeamGoal;
}
