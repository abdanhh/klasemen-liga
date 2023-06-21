package com.testcase.klasemenliga.repositories;

import com.testcase.klasemenliga.models.Statistic;
import com.testcase.klasemenliga.models.TransKlasemen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface StatisticRepository extends JpaRepository<Statistic,Long> {
    @Query("FROM Statistic s WHERE s.awayTeam.teamId = :teamId OR s.homeTeam.teamId = :teamId")
    List<Statistic> findAllByTeamId(Long teamId);

    @Query(value = "FROM Statistic s WHERE s.awayTeam =:awayTeamId OR s.homeTeam =:homeTeamId AND s.schedule = :schedule")
    List<Statistic> findAllByTeamIdsAndSchedule(Long awayTeamId, Long homeTeamId, Date schedule);
}
