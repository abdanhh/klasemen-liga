package com.testcase.klasemenliga.services;

import com.testcase.klasemenliga.dtos.DataKlasemenListDto;
import com.testcase.klasemenliga.models.MasterTeam;
import com.testcase.klasemenliga.models.Statistic;
import com.testcase.klasemenliga.models.TransKlasemen;
import com.testcase.klasemenliga.payloads.AddMatchPayload;
import com.testcase.klasemenliga.repositories.MasterTeamRepository;
import com.testcase.klasemenliga.repositories.StatisticRepository;
import com.testcase.klasemenliga.repositories.TransKlasemenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor=@__(@Autowired))
public class TransKlasemenService {

    private final TransKlasemenRepository transKlasemenRepository;
    private final MasterTeamRepository masterTeamRepository;
    private final StatisticRepository statisticRepository;

    private AtomicInteger counter = new AtomicInteger(1);
    private final Integer winPoint = 3;

    public List<DataKlasemenListDto> findAllKlasemen(){
        List<DataKlasemenListDto> response = new ArrayList<>();
        for (Long listTeamId : transKlasemenRepository.findAllKlasemen()){
            DataKlasemenListDto dto = new DataKlasemenListDto();
            MasterTeam team = masterTeamRepository.findById(listTeamId).orElseThrow(()-> new ApplicationContextException("Team Not Found!"));
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
            teamIds.add(payload.getAwayTeam());
            teamIds.add(payload.getHomeTeam());
            List<TransKlasemen> data = transKlasemenRepository.findAllByTeamIdsAndSchedule(teamIds, payload.getSchedule());
            try {
                if (data.isEmpty()) {
                    Long awayTeamId = payload.getAwayTeam();
                    Long homeTeamId = payload.getHomeTeam();

                    if (payload.getStatistics().getAwayTeamGoal() > payload.getStatistics().getHomeTeamGoal()) {
                        TransKlasemen away = new TransKlasemen();

                        away.setSchedule(payload.getSchedule());
                        away.setTeamId(masterTeamRepository.findById(awayTeamId).orElseThrow(() -> new ApplicationContextException("Team Away not found!")));
                        away.setAwayGoal(payload.getStatistics().getAwayTeamGoal());
                        away.setHomeGoal(Long.valueOf(0));
                        away.setDraw(false);
                        away.setLost(false);
                        away.setWin(true);
                        transKlasemenRepository.save(away);

                        TransKlasemen home = new TransKlasemen();

                        home.setSchedule(payload.getSchedule());
                        home.setTeamId(masterTeamRepository.findById(homeTeamId).orElseThrow(() -> new ApplicationContextException("Team Home not found!")));
                        home.setHomeGoal(payload.getStatistics().getHomeTeamGoal());
                        home.setAwayGoal(Long.valueOf(0));
                        home.setDraw(false);
                        home.setLost(true);
                        home.setWin(false);
                        transKlasemenRepository.save(home);

                    } else if (payload.getStatistics().getHomeTeamGoal() > payload.getStatistics().getAwayTeamGoal()) {
                        TransKlasemen away = new TransKlasemen();

                        away.setSchedule(payload.getSchedule());
                        away.setTeamId(masterTeamRepository.findById(awayTeamId).orElseThrow(() -> new ApplicationContextException("Team Away not found!")));
                        away.setAwayGoal(payload.getStatistics().getAwayTeamGoal());
                        away.setHomeGoal(Long.valueOf(0));
                        away.setDraw(false);
                        away.setLost(true);
                        away.setWin(false);

                        transKlasemenRepository.save(away);

                        TransKlasemen home = new TransKlasemen();

                        home.setSchedule(payload.getSchedule());
                        home.setTeamId(masterTeamRepository.findById(homeTeamId).orElseThrow(() -> new ApplicationContextException("Team Home not found!")));
                        home.setHomeGoal(payload.getStatistics().getHomeTeamGoal());
                        home.setAwayGoal(Long.valueOf(0));
                        home.setDraw(false);
                        home.setLost(false);
                        home.setWin(true);
                        transKlasemenRepository.save(home);

                    } else {
                        TransKlasemen away = new TransKlasemen();

                        away.setSchedule(payload.getSchedule());
                        away.setTeamId(masterTeamRepository.findById(awayTeamId).orElseThrow(() -> new ApplicationContextException("Team Away not found!")));
                        away.setAwayGoal(payload.getStatistics().getAwayTeamGoal());
                        away.setHomeGoal(Long.valueOf(0));
                        away.setDraw(true);
                        away.setLost(false);
                        away.setWin(false);

                        transKlasemenRepository.save(away);

                        TransKlasemen home = new TransKlasemen();

                        home.setSchedule(payload.getSchedule());
                        home.setTeamId(masterTeamRepository.findById(homeTeamId).orElseThrow(() -> new ApplicationContextException("Team Home not found!")));
                        home.setHomeGoal(payload.getStatistics().getHomeTeamGoal());
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

    public List<DataKlasemenListDto> findKlasemens(){
        AtomicInteger increment = new AtomicInteger(1);
        var list = masterTeamRepository.findAll().stream().map(masterTeam -> {
            List<Statistic> listStatistics = statisticRepository.findAllByTeamId(masterTeam.getTeamId());
            Long totalMatch = Long.valueOf(listStatistics.size());
            Long totalDraw = listStatistics.stream().filter(statistic -> statistic.getHomeTeamGoal() == statistic.getAwayTeamGoal()).count();
            Long totalWin = listStatistics.stream().filter(statistic -> statistic.getAwayTeam().getTeamId().equals(masterTeam.getTeamId()) ? statistic.getAwayTeamGoal() > statistic.getHomeTeamGoal() : statistic.getHomeTeamGoal() > statistic.getAwayTeamGoal()).count();
            Long totalLose = totalMatch - totalWin - totalDraw;
            Long points = (totalWin * 3) + totalDraw;
            Long totalHomeGoal = listStatistics.stream().mapToLong(Statistic::getHomeTeamGoal).sum();
            Long totalAwayGoal = listStatistics.stream().mapToLong(Statistic::getAwayTeamGoal).sum();
            return DataKlasemenListDto.builder()
                    .teamId(masterTeam.getTeamId())
                    .name(masterTeam.getName())
                    .city(masterTeam.getCity())
                    .numberOfMatch(totalMatch)
                    .points(points).win(totalWin)
                    .win(totalWin)
                    .lost(totalLose)
                    .draw(totalDraw)
                    .homeGoal(totalHomeGoal)
                    .awayGoal(totalAwayGoal)
                    .build();
        }).sorted(Comparator.comparing(DataKlasemenListDto::getPoints).reversed())
                .sorted(Comparator.comparingInt(item -> (int) Math.abs(item.getHomeGoal() - item.getAwayGoal())))
                .collect(Collectors.toList());

        for (DataKlasemenListDto res : list){
            res.setRank(increment.getAndIncrement());
        }

        return list;
    }

    @Transactional
    public String saveDataStatistic(List<AddMatchPayload> listPayload) {
        for (AddMatchPayload payload : listPayload) {
            List<Statistic> data = statisticRepository.findAllByTeamIdsAndSchedule(payload.getAwayTeam(),payload.getHomeTeam(), payload.getSchedule());
            if (data.isEmpty()) {
                Statistic statistic = new Statistic();
                statistic.setSchedule(payload.getSchedule());
                statistic.setAwayTeam(masterTeamRepository.findById(payload.getAwayTeam()).orElseThrow(() -> new ApplicationContextException("Team Away Not Found!")));
                statistic.setHomeTeam(masterTeamRepository.findById(payload.getHomeTeam()).orElseThrow(() -> new ApplicationContextException("Team Home Not Found!")));
                statistic.setAwayTeamGoal(Math.toIntExact(payload.getStatistics().getAwayTeamGoal()));
                statistic.setHomeTeamGoal(Math.toIntExact(payload.getStatistics().getAwayTeamGoal()));
                statisticRepository.save(statistic);
            } else {
                return "Failed to Add Statistic, Duplicate Team Schedule";
            }
        }
        return "Success";
    }

}
