package com.testcase.klasemenliga.repositories;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.testcase.klasemenliga.dtos.DataKlasemenListDto;
import com.testcase.klasemenliga.models.TransKlasemen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransKlasemenRepository extends JpaRepository<TransKlasemen,Long> {

    @Query(value = "SELECT tk.team_id FROM trans_klasemen tk " +
            "GROUP BY tk.team_id ORDER BY (count(CASE WHEN win THEN 1 END) * 3) + count(CASE WHEN draw THEN 1 END) DESC, " +
            "(sum(tk.home_goal)+sum(tk.away_goal))/2 DESC, " +
            "sum(away_goal), " +
            "count(tk.team_id)",nativeQuery = true)
    List<Long> findAllKlasemen();

    @Query(value = "SELECT count(CASE WHEN win THEN 1 END) FROM trans_klasemen WHERE team_id = :teamId", nativeQuery = true)
    Long countAllWinByTeamId(Long teamId);

    @Query(value = "SELECT count(CASE WHEN lost THEN 1 END) FROM trans_klasemen WHERE team_id = :teamId", nativeQuery = true)
    Long countAllLoseByTeamId(Long teamId);

    @Query(value = "SELECT count(CASE WHEN draw THEN 1 END) FROM trans_klasemen WHERE team_id = :teamId", nativeQuery = true)
    Long countAllDrawByTeamId(Long teamId);

    @Query(value = "SELECT SUM(home_goal) FROM trans_klasemen WHERE team_id = :teamId", nativeQuery = true)
    Long sumAllHomeGoalByTeamId(Long teamId);

    @Query(value = "SELECT SUM(away_goal) FROM trans_klasemen WHERE team_id = :teamId", nativeQuery = true)
    Long sumAllAwayGoalByTeamId(Long teamId);

    @Query(value = "SELECT COUNT(team_id) FROM trans_klasemen WHERE team_id = :teamId", nativeQuery = true)
    Long countTotalMatchByTeamId(Long teamId);

    @Query(value = "SELECT * FROM trans_klasemen WHERE team_id IN (:ids) AND schedule = :schedule",nativeQuery = true)
    List<TransKlasemen> findAllByTeamIdsAndSchedule(List<Long> ids, Date schedule);
}
