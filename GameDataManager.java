import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONObject;

public class GameDataManager {

    private static final String FILE_PATH = "player_data.json";

    // SAVE: Converts Player to JSON string and writes to file
    public static void savePlayer(Player player) {
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(player.toJSON().toString(4)); // Indent factor 4 for pretty print
            file.flush();
            System.out.println(">> Game Saved: " + player.getName());
        } catch (IOException e) {
            System.out.println("Error saving player data: " + e.getMessage());
        }
    }

    // LOAD: Reads file string, converts to JSON Object, then to Player
    public static Player loadPlayer() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONObject json = new JSONObject(content);
            return Player.fromJSON(json);
        } catch (IOException e) {
            System.out.println(">> No previous save found. Creating new player.");
            return new Player("NewUser"); // Default if no file exists
        }
    }
}