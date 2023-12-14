package basketball.league.controller.model;

import java.util.HashSet;
import java.util.Set;

import basketball.league.entity.Player;
import basketball.league.entity.Stat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerData {
	private Long playerId;
	private String playerFirstName;
	private String playerLastName;
	private String playerHeight;
	private String playerPosition;

	Set<StatData> stats = new HashSet<>();

	public PlayerData(Player player) {
		playerId = player.getPlayerId();
		playerFirstName = player.getPlayerFirstName();
		playerLastName = player.getPlayerLastName();
		playerHeight = player.getPlayerHeight();
		playerPosition = player.getPlayerPosition();

		for (Stat stat : player.getStats()) {
			stats.add(new StatData(stat));
		}
	}
}
