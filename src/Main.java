import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

public class Main {
    private static SQLHandler dataBase;

    public static void main(String[] args) throws SQLException, IOException {
        // парсим csv файл
        Map<String, Team> teams = CSVHandler.readFile("Показатели спортивных команд.csv");

        dataBase = new SQLHandler();
        // заполняем таблицу данными
        for (Team team : teams.values()) {
            dataBase.insertTeam(team);
        }

        System.out.println("Данные из базы данных:");
        System.out.println(String.join("\n", dataBase.getAllData()));
        System.out.println();

        //Выполнение заданий
        System.out.println("Задание 1:");
        createChart();
        System.out.println();

        List<String> teamsList = dataBase.getTeamsNames();
        System.out.println("Задание 2:");
        calculateCommandInfo(teamsList);
        System.out.println();

        System.out.println("Задание 3:");
        findTeam(teamsList);
    }

    public static void createChart() throws SQLException, IOException {
        //задаем данные
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (String name : dataBase.getTeamsNames()) {
            dataset.addValue(dataBase.getAverageAge(name), "age", name);
        }

        //создаем график
        JFreeChart chart = ChartFactory.createLineChart(
                "Средний возраст команд",
                "Возраст",
                "Команды",
                dataset
        );

        //сохраняем график
        ChartUtils.saveChartAsPNG(
                new File("chart.png"),
                chart,
                1920,
                1080
        );

        System.out.println("График сохранён в файл chart.png");
    }

    public static void calculateCommandInfo(List<String> teams) throws SQLException {
        String maxHeightTeam = "";
        double maxHeight = -1;
        //ищем команду с самым большим средним ростом
        for (String name : teams) {
            double height = dataBase.getAverageHeight(name);
            if (height > maxHeight){
                maxHeight = height;
                maxHeightTeam = name;
            }
        }

        System.out.println("Команда с самым большим средним ростом - " + maxHeightTeam + ", он составил: " + maxHeight);
        System.out.println("Самые выоские игроки: " +
                String.join(", ", dataBase.getMaxHeightPlayers(maxHeightTeam)));
    }

    public static void findTeam(List<String> teams) throws SQLException {
        String teamName = "";
        //ищем команду с самым большим средним возрастом
        String olderPlayersTeam = "";
        double maxAge = -1;
        for (String name : teams) {
            double age = dataBase.getAverageAge(name);
            if (age > maxAge){
                maxAge = age;
                olderPlayersTeam = name;
            }
        }

        //ищем нужную команду
        for (String name : teams) {
            double avgHeight = dataBase.getAverageHeight(name);
            double avgWeight = dataBase.getAverageWeight(name);
            if (74 <= avgHeight && avgHeight <= 78 && 190 <= avgWeight && avgWeight <= 210 && name == olderPlayersTeam) {
                teamName = name;
            }
        }

        System.out.println("Команда, со средним ростом равным от 74 до 78 inches"
                        + ", средним весом от 190 до 210 lbs и с самым высоким средним возрастом - " + teamName);
    }
}