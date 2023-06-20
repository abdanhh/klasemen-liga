package com.testcase.klasemenliga.services;

import com.testcase.klasemenliga.dtos.DataKlasemenListDto;
import com.testcase.klasemenliga.models.MasterTeam;
import com.testcase.klasemenliga.models.TransKlasemen;
import com.testcase.klasemenliga.payloads.AddMatchPayload;
import com.testcase.klasemenliga.repositories.MasterTeamRepository;
import com.testcase.klasemenliga.repositories.TransKlasemenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransKlasemenService {

    @Autowired
    TransKlasemenRepository transKlasemenRepository;
    @Autowired
    MasterTeamRepository masterTeamRepository;

    private AtomicInteger counter = new AtomicInteger(1);
    private final Integer winPoint = 3;

    public List<DataKlasemenListDto> findAllKlasemen(){
        List<DataKlasemenListDto> response = new ArrayList<>();
        for (Long listTeamId : transKlasemenRepository.findAllKlasemen()){
            DataKlasemenListDto dto = new DataKlasemenListDto();
            MasterTeam team = masterTeamRepository.findById(listTeamId).get();
            dto.setTeamId(team.getTeamId());
            dto.setName(team.getName());
            dto.setCity(team.getCity());
            dto.setRank(counter.getAndIncrement());

            Long teamId = team.getTeamId();
            Long totalWin = transKlasemenRepository.countAllWinByTeamId(teamId);
            Long totalLose = transKlasemenRepository.countAllLoseByTeamId(teamId);
            Long totalDraw = transKlasemenRepository.countAllDrawByTeamId(teamId);
            dto.setPoints((totalWin * winPoint) + totalDraw);
            dto.setWin(totalWin);
            dto.setLost(totalLose);
            dto.setDraw(totalDraw);

            Long totalAwayGoal = transKlasemenRepository.sumAllAwayGoalByTeamId(teamId);
            Long totalHomeGoal = transKlasemenRepository.sumAllHomeGoalByTeamId(teamId);
            dto.setAwayGoal(totalAwayGoal);
            dto.setHomeGoal(totalHomeGoal);

            dto.setNumberOfMatch(transKlasemenRepository.countTotalMatchByTeamId(teamId));

            response.add(dto);

        }
        return response;
    }

    @Transactional
    public String saveDataMatch(List<AddMatchPayload> listPayload) {
        for (AddMatchPayload payload : listPayload) {
            List<Long> teamIds = new ArrayList<>();
            teamIds.add(payload.getAway_team());
            teamIds.add(payload.getHome_team());
            List<TransKlasemen> data = transKlasemenRepository.findAllByTeamIdsAndSchedule(teamIds, payload.getSchedule());
            try {
                if (data.isEmpty()) {
                    Long awayTeamId = payload.getAway_team();
                    Long homeTeamId = payload.getHome_team();

                    if (payload.getStatistics().getAway_team_goal() > payload.getStatistics().getHome_team_goal()) {
                        TransKlasemen away = new TransKlasemen();

                        away.setSchedule(payload.getSchedule());
                        away.setTeamId(masterTeamRepository.findById(awayTeamId).orElseThrow(() -> new ApplicationContextException("Team Away not found!")));
                        away.setAwayGoal(payload.getStatistics().getAway_team_goal());
                        away.setHomeGoal(Long.valueOf(0));
                        away.setDraw(false);
                        away.setLost(false);
                        away.setWin(true);
                        transKlasemenRepository.save(away);

                        TransKlasemen home = new TransKlasemen();

                        home.setSchedule(payload.getSchedule());
                        home.setTeamId(masterTeamRepository.findById(homeTeamId).orElseThrow(() -> new ApplicationContextException("Team Home not found!")));
                        home.setHomeGoal(payload.getStatistics().getHome_team_goal());
                        home.setAwayGoal(Long.valueOf(0));
                        home.setDraw(false);
                        home.setLost(true);
                        home.setWin(false);
                        transKlasemenRepository.save(home);

                    } else if (payload.getStatistics().getHome_team_goal() > payload.getStatistics().getAway_team_goal()) {
                        TransKlasemen away = new TransKlasemen();

                        away.setSchedule(payload.getSchedule());
                        away.setTeamId(masterTeamRepository.findById(awayTeamId).orElseThrow(() -> new ApplicationContextException("Team Away not found!")));
                        away.setAwayGoal(payload.getStatistics().getAway_team_goal());
                        away.setHomeGoal(Long.valueOf(0));
                        away.setDraw(false);
                        away.setLost(true);
                        away.setWin(false);

                        transKlasemenRepository.save(away);

                        TransKlasemen home = new TransKlasemen();

                        home.setSchedule(payload.getSchedule());
                        home.setTeamId(masterTeamRepository.findById(homeTeamId).orElseThrow(() -> new ApplicationContextException("Team Home not found!")));
                        home.setHomeGoal(payload.getStatistics().getHome_team_goal());
                        home.setAwayGoal(Long.valueOf(0));
                        home.setDraw(false);
                        home.setLost(false);
                        home.setWin(true);
                        transKlasemenRepository.save(home);

                    } else {
                        TransKlasemen away = new TransKlasemen();

                        away.setSchedule(payload.getSchedule());
                        away.setTeamId(masterTeamRepository.findById(awayTeamId).orElseThrow(() -> new ApplicationContextException("Team Away not found!")));
                        away.setAwayGoal(payload.getStatistics().getAway_team_goal());
                        away.setHomeGoal(Long.valueOf(0));
                        away.setDraw(true);
                        away.setLost(false);
                        away.setWin(false);

                        transKlasemenRepository.save(away);

                        TransKlasemen home = new TransKlasemen();

                        home.setSchedule(payload.getSchedule());
                        home.setTeamId(masterTeamRepository.findById(homeTeamId).orElseThrow(() -> new ApplicationContextException("Team Home not found!")));
                        home.setHomeGoal(payload.getStatistics().getHome_team_goal());
                        home.setAwayGoal(Long.valueOf(0));
                        home.setDraw(true);
                        home.setLost(false);
                        home.setWin(false);
                        transKlasemenRepository.save(home);
                    }
                } else {
                    return "Duplicate Team Schedule";
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
        }
        return "Success";
    }
}
