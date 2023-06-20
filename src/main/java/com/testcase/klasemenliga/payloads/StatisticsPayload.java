package com.testcase.klasemenliga.payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatisticsPayload {
    private Long home_team_goal;
    private Long away_team_goal;
}
