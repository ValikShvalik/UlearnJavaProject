import java.util.List;
import java.util.ArrayList;

public class Team {
    private final String name;
    private final List<PlayerIndicators> players = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }
    public void putPlayer(PlayerIndicators player) {
        players.add(player);
    }
    public PlayerIndicators[] getPlayers() {
        PlayerIndicators[] array = new PlayerIndicators[players.size()];
        players.toArray(array);
        return array;
    }
    public String getName() {
        return name;
    }

}