package io;

import exceptions.InputFileException;
import util.Cost;
import util.Graph;
import util.Path;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class FileReader {

    private String ALL_PLACES_TABLE_NAME = "### Miejsca podróży";
    private String OBLIGATORY_PLACES_TABLE_NAME = "### Wybrane miejsca podróży";
    private String PATHS_TABLE_NAME = "### Czas przejścia";
    private String PLACE_COL = "ID_miejsca";
    private String START_COL = "ID_miejsca_początkowego (S)";
    private String END_COL = "ID_miejsca_końcowego (E)";
    private String COST_COL = "Jednorazowa opłata za przejście trasą (zł)";
    private String SE_COL = "Czas S -> E";
    private String ES_COL = "Czas E -> S";
    private String VB = Pattern.quote("|");
    private String FILE_FOLDER = "src/io/files/";

    private String configFilePath = null;
    private String placesFilePath = null;
    private Graph graph;

    public FileReader(String config) {
        this.graph = new Graph();
        this.configFilePath = config;
    }

    public FileReader(String config, String places) {
        this.graph = new Graph();
        this.configFilePath = config;
        this.placesFilePath = places;
    }

    public void readFromFile() throws IOException {

        BufferedReader br = new BufferedReader(new java.io.FileReader(FILE_FOLDER + configFilePath));
        String line;
        boolean places_table_flag = false;
        boolean paths_table_flag = false;

        while ((line = br.readLine()) != null) {
            if (line.strip().startsWith(ALL_PLACES_TABLE_NAME)) {
                places_table_flag = true;
                readPlaces(br, ALL_PLACES_TABLE_NAME);
            }
            if (line.strip().startsWith(PATHS_TABLE_NAME)) {
                paths_table_flag = true;
                if (!places_table_flag)
                    throw new InputFileException("Brak tabeli " + ALL_PLACES_TABLE_NAME + " w pliku konfiguracyjnym");
                readPaths(br);
            }
        }


        if (!places_table_flag)
            throw new InputFileException("Brak tabeli " + ALL_PLACES_TABLE_NAME + " w pliku konfiguracyjnym");

        if (!paths_table_flag)
            throw new InputFileException("Brak tabeli " + PATHS_TABLE_NAME + " w pliku konfiguracyjnym");

        if (placesFilePath != null) { //jeżeli podano plik z wybranymi miejscami

            br = new BufferedReader(new java.io.FileReader(FILE_FOLDER + placesFilePath));
            places_table_flag = false;

            while ((line = br.readLine()) != null) {
                if (line.strip().startsWith(OBLIGATORY_PLACES_TABLE_NAME)) {
                    places_table_flag = true;
                    readPlaces(br, OBLIGATORY_PLACES_TABLE_NAME);
                }
            }
            if (!places_table_flag)
                throw new InputFileException("Brak tabeli " + OBLIGATORY_PLACES_TABLE_NAME + " w podany pliku.");
        }
    }

    private void readPlaces(BufferedReader br, String tableName) throws IOException {

        int ID_miejsca = 0;

        String line = br.readLine();
        String[] columns = line.split(VB);
        int n_cols = columns.length;

        for (String s : columns) {

            if (s.trim().equals(PLACE_COL)) {
                break;
            }
            ID_miejsca++;
        }

        if (ID_miejsca == columns.length) {
            throw new InputFileException("Brak kolumny " + PLACE_COL + " w tabeli " + tableName);
        }

        line = br.readLine();
        int cnt = 0;

        while ((line != null && !line.strip().startsWith(PATHS_TABLE_NAME))) {
            br.mark(1000);
            String[] values = line.split(VB);
            if (values.length != n_cols) {
                line = br.readLine();
                continue;
            }
            if (tableName.equals(ALL_PLACES_TABLE_NAME)) {
                if (Graph.getAllPlaces().indexOf(values[ID_miejsca].trim()) != -1) {
                    Graph.getAllPlaces().set(Graph.getAllPlaces().indexOf(values[ID_miejsca].trim()), values[ID_miejsca].trim());

                } else {
                    Graph.getAllPlaces().add(values[ID_miejsca].trim());
                }

                cnt++;
                if (cnt == Graph.getMaxPlaces()) {
                    throw new InputFileException("Przekroczono limit miejsc");
                }


            } else { //jeżeli czytamy tabelke z wybranymi miejscami
                if (Graph.getAllPlaces().indexOf(values[ID_miejsca].trim()) == -1)
                    throw new InputFileException("Nieznane ID miejsca.");
                graph.getObligatoryPlaces().add(Graph.getAllPlaces().indexOf(values[ID_miejsca].trim()));
            }
            line = br.readLine();
        }

        if (Graph.getAllPlaces().isEmpty()) {
            throw new InputFileException("Nie podano żadnych miejsc w tabeli " + ALL_PLACES_TABLE_NAME);
        }

        br.reset();
    }

    private void readPaths(BufferedReader br) throws IOException {

        int start = -1;
        int end = -1;
        int se_time = -1;
        int es_time = -1;
        int cost = -1;

        String line = br.readLine();

        if (line == null) {
            throw new InputFileException("Brak tabeli " + PATHS_TABLE_NAME + " w pliku wejściowym");
        }

        String[] columns = line.split(VB);
        int n_cols = columns.length;

        int cnt = 0;

        for (String s : columns) {
            s = s.trim();
            if (s.equals(START_COL)) {
                start = cnt;
            }
            if (s.equals(END_COL)) {
                end = cnt;
            }
            if (s.equals(SE_COL)) {
                se_time = cnt;
            }
            if (s.equals(ES_COL)) {
                es_time = cnt;
            }
            if (s.equals(COST_COL)) {
                cost = cnt;
            }
            cnt++;
        }

        if (start < 0) {
            throw new InputFileException("Brak kolumny " + START_COL + " w tablicy " + PATHS_TABLE_NAME);
        }
        if (end < 0) {
            throw new InputFileException("Brak kolumny " + END_COL + " w tablicy " + PATHS_TABLE_NAME);
        }
        if (se_time < 0) {
            throw new InputFileException("Brak kolumny " + SE_COL + " w tablicy " + PATHS_TABLE_NAME);
        }
        if (es_time < 0) {
            throw new InputFileException("Brak kolumny " + ES_COL + " w tablicy " + PATHS_TABLE_NAME);
        }
        if (cost < 0) {
            throw new InputFileException("Brak kolumny " + COST_COL + " w tablicy " + PATHS_TABLE_NAME);
        }

        line = br.readLine();

        while (line != null) {

            String[] values = line.split(VB);

            int len = values.length;
            if (len != n_cols) {
                line = br.readLine();
                continue;
            }

            if (values[start].trim().equals(values[end].trim()))
                throw new InputFileException("Nie można utworzyć ścieżki między jednym miejscem (" + values[start].trim() + ").");

            int start_id = Graph.getAllPlaces().indexOf(values[start].trim());
            int end_id = Graph.getAllPlaces().indexOf(values[end].trim());

            if (start_id < 0) {
                throw new InputFileException("Nieokreślone ID miejsca (" + values[start].trim() + ") w kolumnie " + START_COL);
            }

            if (end_id < 0) {
                throw new InputFileException("Nieokreślone ID miejsca (" + values[end].trim() + ") w kolumnie " + END_COL);
            }

            ArrayList<Integer> course_se = new ArrayList<>();
            course_se.add(start_id);
            course_se.add(end_id);

            ArrayList<Integer> course_es = new ArrayList<>();
            course_es.add(end_id);
            course_es.add(start_id);

            int costVal = getCost(values[cost]);

            Path se = new Path(course_se, costVal, getMinutes(values[se_time]));
            Path es = new Path(course_es, costVal, getMinutes(values[es_time]));

            graph.setPath(se);
            graph.setPath(es);

            if (costVal != 0) {
                Graph.getPathsWithCost().add(new Cost(start_id, end_id, costVal));
                Graph.getPathsWithCost().add(new Cost(end_id, start_id, costVal));
            }


            line = br.readLine();
        }
    }

    private int getMinutes(String s) {
        s = s.trim();
        String[] values = s.split(":");
        if (!s.matches("^[0-9]+:[0-5][0-9]$")) {
            throw new InputFileException("Niepoprawny format czasu przejścia");
        }
        return Integer.parseInt(values[0]) * 60 + Integer.parseInt(values[1]);
    }

    private int getCost(String s) {
        s = s.trim();
        if (s.equals("--"))
            return 0;
        if (!s.matches("[0-9]+")) {
            throw new InputFileException("Niepoprawny format danych w kolumnie " + COST_COL);
        }
        return Integer.parseInt(s);
    }

    public Graph getGraph() {
        return graph;
    }

}
