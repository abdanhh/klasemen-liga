package com.testcase.klasemenliga.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "master_team", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MasterTeam implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "team_id", unique = true, nullable = false)
    private Long teamId;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

}
