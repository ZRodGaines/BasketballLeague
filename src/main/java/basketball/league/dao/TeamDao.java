package basketball.league.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import basketball.league.entity.Team;

public interface TeamDao extends JpaRepository<Team, Long> {

}
