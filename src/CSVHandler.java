import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CSVHandler {

    public static Map<String, Team> readFile(String path) {
        Map<String, Team> teams = new HashMap<>();

        try (var br = new BufferedReader(new FileReader(path))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = serializeLine(line);
                String teamName =  data[1];
                Team team;

                if (!teams.containsKey(teamName)){
                    team =  new Team(teamName);
                    teams.put(teamName, team);
                } else {
                    team = teams.get(teamName);
                }

                team.putPlayer(new PlayerIndicators(data[0], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), Double.parseDouble(data[5])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return teams;
    }

    private static String[] serializeLine(String line){
        String[] splited = line.split(",");

        for (int i = 0; i < splited.length; i++){
            if (splited[i].startsWith("\" \"")){
                splited[i] = splited[i].substring(3);
            }

            if (splited[i].endsWith("\"\"")){
                splited[i] = splited[i].substring(0, splited[i].length() - 2);
            }
        }

        return splited;
    }
}
