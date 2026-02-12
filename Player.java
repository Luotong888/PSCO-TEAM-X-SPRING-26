import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject; // json file we making instead of csv u can tall me if any doubts

public class Player {
    private final String name;
    private final Map<String, Integer> scores = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }

    // ===== Score management =====
    public void setScore(String game, int score) {
        scores.put(game, score);
    }

    public int getScore(String game) {
        return scores.getOrDefault(game, 0);
    }

    public Map<String, Integer> getAllScores() {
        return new HashMap<>(scores);
    }

    // ===== Serialization =====
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("scores", scores);
        return obj;
    }

    public static Player fromJSON(JSONObject obj) {
        String name = obj.getString("name");
        Player p = new Player(name);

        JSONObject scoresJson = obj.getJSONObject("scores");
        for (String game : scoresJson.keySet()) {
            int score = scoresJson.getInt(game);
            p.setScore(game, score);
        }

        return p;
    }
}
