package com.testcase.klasemenliga.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "trans_klasemen", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransKlasemen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "klasemen_id", unique = true, nullable = false)
    private Long klasemenId;

    @JoinColumn(name = "team_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MasterTeam teamId;

    @Column(name = "win")
    private Boolean win;

    @Column(name = "lost")
    private Boolean lost;

    @Column(name = "draw")
    private Boolean draw;

    @Column(name = "home_goal")
    private Long homeGoal;

    @Column(name = "away_goal")
    private Long awayGoal;

    @Column(name = "schedule")
    private Date schedule;
}
