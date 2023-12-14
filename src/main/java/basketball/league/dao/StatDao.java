package basketball.league.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import basketball.league.entity.Stat;

public interface StatDao extends JpaRepository<Stat, Long> {

}
