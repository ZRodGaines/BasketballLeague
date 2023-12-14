package basketball.league.controller.model;

import java.util.HashSet;
import java.util.Set;

import basketball.league.entity.Player;
import basketball.league.entity.Team;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamData {
	private Long teamId;
	private String teamName;
	private String teamCity;
	private String teamColors;
	private String teamRecord;

	Set<PlayerData> players = new HashSet<>();

	public TeamData(Team team) {
		teamId = team.getTeamId();
		teamName = team.getTeamName();
		teamCity = team.getTeamCity();
		teamColors = team.getTeamColors();
		teamRecord = team.getTeamRecord();

		for (Player player : team.getPlayers()) {
			players.add(new PlayerData(player));
		}
	}
}
