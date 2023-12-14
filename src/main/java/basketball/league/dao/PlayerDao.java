package basketball.league.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import basketball.league.entity.Player;

public interface PlayerDao extends JpaRepository<Player, Long> {

}
