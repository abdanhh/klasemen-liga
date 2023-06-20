package com.testcase.klasemenliga.payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddMatchPayload {
    private Long home_team;
    private Long away_team;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bangkok")
    private Date schedule;
    private StatisticsPayload statistics;
}
