package com.testcase.klasemenliga.repositories;

import com.testcase.klasemenliga.models.MasterTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterTeamRepository extends JpaRepository<MasterTeam, Long> {
}
