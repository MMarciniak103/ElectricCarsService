package app.database.mapping;

/**
 * Script that you can use to populate your db with vehicles that were parsed from json.
 */
public class PopulateDatabase {
    public static void main(String[] args) {

        DataMapper dataMapper = new DataMapper();
        String path = "src/main/resources/json/vozilla.json";
        dataMapper.populateDatabase(path);

    }
}
