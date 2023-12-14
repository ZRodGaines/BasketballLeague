package basketball.league.service;

import java.util.LinkedList;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basketball.league.controller.model.PlayerData;
import basketball.league.controller.model.StatData;
import basketball.league.controller.model.TeamData;
import basketball.league.dao.PlayerDao;
import basketball.league.dao.StatDao;
import basketball.league.dao.TeamDao;
import basketball.league.entity.Player;
import basketball.league.entity.Stat;
import basketball.league.entity.Team;

@Service
public class BasketballLeagueService {

	@Autowired
	private TeamDao teamDao;

	 @Autowired
	 private PlayerDao playerDao;

	 @Autowired
	 private StatDao statDao;

	@Transactional(readOnly = false)
	public TeamData saveTeam(TeamData teamData) {
		Long teamId = teamData.getTeamId();
		Team team = findOrCreateTeam(teamId);

		setFieldsInTeam(team, teamData);
		return new TeamData(teamDao.save(team));
	}
	
	@Transactional(readOnly = false)
	public TeamData saveTeamWithId(Long teamId, TeamData teamData) {
		Team team = findTeamById(teamId);

		setFieldsInTeam(team, teamData);
		return new TeamData(teamDao.save(team));
	}

	private void setFieldsInTeam(Team team, TeamData teamData) {
		team.setTeamName(teamData.getTeamName());
		team.setTeamCity(teamData.getTeamCity());
		team.setTeamColors(teamData.getTeamColors());
		team.setTeamRecord(teamData.getTeamRecord());

	}

	private Team findOrCreateTeam(Long teamId) {
		Team team;

		if (Objects.isNull(teamId)) {
			team = new Team();
		} else {
			team = findTeamById(teamId);
		}
		return team;
	}

	private Team findTeamById(Long teamId) {
		return teamDao.findById(teamId)
				.orElseThrow(() -> new NoSuchElementException("Team with ID =" + teamId + " does not exist"));
	}

	@Transactional(readOnly = true)
	public List<TeamData> retrieveAllTeams() {
		List<Team> teams = teamDao.findAll();
		List<TeamData> response = new LinkedList<>();
		
		for(Team team : teams) {
			response.add(new TeamData(team));
		}
		return response;
	}
	
	@Transactional(readOnly = false)
	public void deleteTeamById(Long teamId) {
		Team team = findTeamById(teamId);
		teamDao.delete(team);
		
	}
	
	@Transactional(readOnly = false)
	public PlayerData savePlayer(Long teamId, PlayerData playerData) {
		Team team = findTeamById(teamId);
		
		Player player = findOrCreatePlayer(teamId, playerData.getPlayerId());
	
		setFieldsInPlayer(player, playerData);
		
		player.setTeam(team);
		team.getPlayers().add(player);

		Player dbPlayer = playerDao.save(player);
		return new PlayerData(playerDao.save(dbPlayer));
	}
	
	public PlayerData savePlayerWithId(Long playerId, PlayerData playerData) {
		Team team = findPlayerTeam(playerId);
		Long teamId = team.getTeamId();
		Player player = findOrCreatePlayer(teamId, playerId);
		
		setFieldsInPlayer(player, playerData);
		
		player.setTeam(team);
		team.getPlayers().add(player);

		Player dbPlayer = playerDao.save(player);
		return new PlayerData(playerDao.save(dbPlayer));
	}

	private Team findPlayerTeam(Long playerId) {
		Player player = playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException("Player with ID =" + playerId + " does not exist"));
		Team team = player.getTeam();
		return team;
	}

	private void setFieldsInPlayer(Player player, PlayerData playerData) {
		player.setPlayerFirstName(playerData.getPlayerFirstName());
		player.setPlayerLastName(playerData.getPlayerLastName());
		player.setPlayerPosition(playerData.getPlayerPosition());
		player.setPlayerHeight(playerData.getPlayerHeight());
		
	}

	private Player findOrCreatePlayer(Long teamId, Long playerId) {
		Player player;

		if (Objects.isNull(playerId)) {
			player = new Player();
			System.out.println("Creating player");
		} else {
			player = findPlayerById(teamId, playerId);
			System.out.println("found player =" + playerId);
		}
		return player;
	}

	private Player findPlayerById(Long teamId, Long playerId) {
		Player player = playerDao.findById(playerId)
				.orElseThrow(() -> new NoSuchElementException("Player with ID =" + playerId + " does not exist"));
		
		boolean found = false;
		
		Team team = player.getTeam();
		if (team.getTeamId() == teamId) {
				found = true;
		}
		if(!found) {
			throw new IllegalArgumentException("The Player with ID = " + playerId  + " does not player for the team with ID=" + teamId);
		}
		return player;
	}

	@Transactional(readOnly = true)
	public List<PlayerData> retrieveAllPlayers() {
		List<Player> players = playerDao.findAll();
		List<PlayerData> response = new LinkedList<>();
		
		for(Player player : players) {
			response.add(new PlayerData(player));
		}
		return response;
	}

	@Transactional(readOnly = false)
	public void deletePlayerById(Long teamId, Long playerId) {
		Player player = findPlayerById(teamId, playerId);
		playerDao.delete(player);
		
	}

	@Transactional(readOnly = false)
	public StatData saveStat(Long playerId, StatData statData) {
		Long teamId = findPlayerTeam(playerId).getTeamId();
		Player player = findPlayerById(teamId, playerId);
		
		Stat stat = findOrCreateStat(playerId , statData.getStatId());
			setStatfields(stat, statData);
			
			stat.getPlayers().add(player);
			player.getStats().add(stat);
			
			Stat dbStat = statDao.save(stat);
			return new StatData(dbStat);
	}

	private void setStatfields(Stat stat, StatData statData) {
		stat.setStatName(statData.getStatName());
		stat.setStatValue(statData.getStatValue());
		//stat.setStatId(statData.getStatId());
		
	}

	private Stat findOrCreateStat(Long playerId, Long statId) {
		Stat stat;

		if (Objects.isNull(statId)) {
			stat = new Stat();
		} else {
			stat = findStatById(playerId, statId);
		}
		return stat;
	}

	private Stat findStatById(Long playerId, Long statId) {
		Stat stat = statDao.findById(statId)
				.orElseThrow(() -> new NoSuchElementException("Stat with ID= " + statId + " Does not Exist"));
		
		boolean found = false;
		
		for(Player player : stat.getPlayers()) {
			if(player.getPlayerId() == playerId) {
				found = true;
				break;
			}
}
		
		if(!found) {
			throw new IllegalArgumentException("The stat with ID = " + statId  + " does not belong to player with ID=" + playerId);
		}
		return stat;
	}

	public StatData saveStatWithId(Long statId, Long playerId, StatData statData) {
		Long teamId = findPlayerTeam(playerId).getTeamId();
		Player player = findPlayerById(teamId, playerId);
		
		Stat stat = findStatById(playerId , statId);
			setStatfields(stat, statData);
			
			stat.getPlayers().add(player);
			player.getStats().add(stat);
			
			Stat dbStat = statDao.save(stat);
			return new StatData(dbStat);
	}
	

}
