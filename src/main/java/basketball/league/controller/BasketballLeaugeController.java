package basketball.league.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import basketball.league.controller.model.PlayerData;
import basketball.league.controller.model.StatData;
import basketball.league.controller.model.TeamData;
import basketball.league.service.BasketballLeagueService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/basketball_league")
@Slf4j

public class BasketballLeaugeController {

	@Autowired
	private BasketballLeagueService basketballLeagueService;

	@PostMapping("/team")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TeamData insertTeam(@RequestBody TeamData teamData) {
		log.info("Creating Team { }", teamData);
		return basketballLeagueService.saveTeam(teamData);
	}
	
	@PutMapping("/team/{teamId}")
	public TeamData updateTeam(@PathVariable Long teamId, @RequestBody TeamData teamData) {
		log.info("Updating Team { }", teamData);
		return basketballLeagueService.saveTeamWithId(teamId, teamData);
	}
	
	@GetMapping("/team")
	public List<TeamData> retrieveAllTeams() {
		log.info("Retrieve all Teams called.");
		return basketballLeagueService.retrieveAllTeams();
	}
	
	
	@DeleteMapping("/team/{teamId}")
	public Map<String, String> deleteTeamById(@PathVariable Long teamId) {
		log.info("Deleting Team with ID={}", teamId);

		basketballLeagueService.deleteTeamById(teamId);

		return Map.of("message", "Deletion of Team with ID=" + teamId + " was" + " successful.");
	}
	
	@PostMapping("/team/{teamId}/player")
	@ResponseStatus(code = HttpStatus.CREATED)
	public PlayerData insertPlayer(@PathVariable Long teamId, @RequestBody PlayerData playerData) {
		log.info("Creating PLayer { }", playerData);
		return basketballLeagueService.savePlayer(teamId, playerData);
	}
	
	@PutMapping("/player/{playerId}")
	public PlayerData updatePlayer(@PathVariable Long playerId, @RequestBody PlayerData playerData) {
		log.info("Updating Player { }", playerData);
		return basketballLeagueService.savePlayerWithId(playerId, playerData);
	}
	
	@GetMapping("/player")
	public List<PlayerData> retrieveAllPlayers() {
		log.info("Retrieve all Players called.");
		return basketballLeagueService.retrieveAllPlayers();
	}
	
	@DeleteMapping("/team/{teamId}/{playerId}")
	public Map<String, String> deletePlayerById(@PathVariable Long teamId, @PathVariable Long playerId) {
		log.info("Deleting Player with ID={}", playerId);

		basketballLeagueService.deletePlayerById(teamId, playerId);

		return Map.of("message", "Deletion of Player with ID=" + playerId + " was" + " successful.");
	}
	
	@PostMapping("/player/stat/{playerId}")
	public StatData InsertStat(@PathVariable Long playerId, @RequestBody StatData statData) {
		
		log.info("Creating Stat for Player with ID={}", playerId);
		
		return basketballLeagueService.saveStat(playerId, statData);
	}

	@PutMapping("/player/{statId}/{playerId}")
	public StatData updateStat(@PathVariable Long statId, @PathVariable Long playerId, @RequestBody StatData statData) {
		log.info("Updating Stat { }", statData);
		System.out.println(statId + " " + playerId + " ");
		return basketballLeagueService.saveStatWithId(statId, playerId, statData);
	}

}
