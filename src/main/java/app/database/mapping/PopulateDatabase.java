package app.database.mapping;

public class PopulateDatabase {
    public static void main(String[] args) {

        DataMapper dataMapper = new DataMapper();
        String path = "src/main/resources/json/vozilla.json";
        dataMapper.populateDatabase(path);

    }
}
