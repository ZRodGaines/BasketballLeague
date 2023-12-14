package basketball.league.controller.model;

import basketball.league.entity.Stat;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StatData {
	private Long statId;
	private String statName;
	private Integer statValue;

	public StatData(Stat stat) {
		statId = stat.getStatId();
		statName = stat.getStatName();
		statValue = stat.getStatValue();
	}
}
