package com.testcase.klasemenliga.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "statistic", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistic implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "statistic_id", unique = true, nullable = false)
    private Long statisticId;

    @JoinColumn(name = "home_team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MasterTeam homeTeam;

    @JoinColumn(name = "away_team_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MasterTeam awayTeam;

    @Column(name = "schedule")
    private Date schedule;

    @Column(name = "home_team_goal")
    private Integer homeTeamGoal;

    @Column(name = "away_team_goal")
    private Integer awayTeamGoal;
}
