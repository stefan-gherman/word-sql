package com.codecool.worldsqldojo;

import org.junit.jupiter.api.*;

import javax.xml.transform.Result;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldDBCreatorTest {
    private static WorldDBCreator worldDBCreator;

    @BeforeAll
    public static void setUp() {
        worldDBCreator = new WorldDBCreator();
    }

    @AfterAll
    public static void tearDown() {
        // delete test data
        try {
            worldDBCreator.executeUpdate("TRUNCATE TABLE city CASCADE;");
            worldDBCreator.executeUpdate("TRUNCATE TABLE country CASCADE;");
            worldDBCreator.executeUpdate("TRUNCATE TABLE countrylanguage CASCADE;");
        } catch (SQLException e) {
            System.err.println("ERROR: Data cleanup failed.");
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertDataIntoTables() {
        try {
            worldDBCreator.executeUpdate("TRUNCATE TABLE city CASCADE;");
            worldDBCreator.executeUpdate("TRUNCATE TABLE country CASCADE;");
            worldDBCreator.executeUpdate("TRUNCATE TABLE countrylanguage CASCADE;");
            worldDBCreator.copyDataFromFile("city", "src/test/resources/city_data.txt");
            worldDBCreator.copyDataFromFile("country", "src/test/resources/country_data.txt");
            worldDBCreator.copyDataFromFile("countrylanguage", "src/test/resources/countrylanguage_data.txt");
        } catch (SQLException e) {
            e.printStackTrace();
            Assertions.fail("ERROR: Data insertion failed.");
        }
    }

    @Test
    public void testIsFirstColumnOfCityTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES('a', 'a', 'a', 'a', 0)");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsThirdColumnOfCityTable3CharLong() {
        String expected = "22001";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(0 , 'a', 'aaaa', 'a', 0)");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsFifthColumnOfCityTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(0 , 'a', 'a', 'a', 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsPopulationSummable() {
        ResultSet resultSet = null;
        Integer result = null;
        Integer expected = new Integer(1429559884);

        try {
            resultSet = worldDBCreator.executeQuery("SELECT SUM(population) FROM city;");
            while (resultSet.next()) {
                result = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expected, result);
    }

    @Test
    public void testIsFirstColumnOfCityTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(null, 'a', 'a', 'a', 0);");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsSecondColumnOfCityTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(0, null, 'a', 'a', 0);");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsThirdColumnOfCityTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(0, 'a', null, 'a', 0);");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFourthColumnOfCityTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(0, 'a', 'a', null, 0);");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFifthColumnOfCityTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(0, 'a', 'a', 'a', null);");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFirstColumnOfCityTableUnique() {
        String expected = "23505";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO city VALUES(1, 'a', 'a', 'a', 0);");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFirstColumnOfCountryTable3CharLong() {
        String expected = "22001";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('aaaa', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsFifthColumnOfCountryTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 'a', 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsSixthColumnOfCountryTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 'a', 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }
    @Test
    public void testIsSeventhColumnOfCountryTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 'a', 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsEighthColumnOfCountryTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 'a', 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsNinthColumnOfCountryTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 0, 'a', 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsTenthColumnOfCountryTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 'a', 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsFirstColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES(null, 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsSecondColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', null, 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsThirdColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', null, 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFourthColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', null, 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFifthColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', null, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsSeventhColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, null, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsEleventhColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, null, 'a', 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsTwelvthColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', null, 'a', 1, 'a')");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFifteenthColumnOfCountryTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, null)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFirstColumnOfCountryTableUnique() {
        String expected = "23505";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('AFG', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");

        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFourteenthColumnOfCountryTableForeignKeyToCityTable() {
        String expected = "23503";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'Europe', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 0, 'a')");

        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsThirdColumnOfCountryTableCheckConstraint() {
        String expected = "23514";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO country" +
                    " VALUES('a', 'a', 'a', 'a', 0, 0, 0, 0, 0, 0, 'a', 'a', 'a', 1, 'a')");

        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFourthColumnOfCountrylanguageTableNumber() {
        String expected = "22P02";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES('AFG', 'a', true, 'a')");
        });

        assertEquals(expected, ((SQLException) exception).getSQLState());
    }

    @Test
    public void testIsSecondColumnOfCountrylanguageTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES('AFG', null, true, 0)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsThirdColumnOfCountrylanguageTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES('AFG', 'a', null, 0)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFourthColumnOfCountrylanguageTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES('AFG', 'a', true, null)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFirstColumnOfCountrylanguageTableNotNull() {
        String expected = "23502";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES(null, 'a', true, 0)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFirstAndSecondColumnOfCountrylanguageTableUnique() {
        String expected = "23505";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES('AFG', 'Pashto', true, 0)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testIsFirstColumnOfCountrylanguageTableForeignKeyToCountryTable() {
        String expected = "23503";

        Throwable exception = assertThrows(SQLException.class, () -> {
            worldDBCreator.executeUpdate("INSERT INTO countrylanguage VALUES('a', 'a', true, 0)");
        });

        assertEquals(expected, ((SQLException)exception).getSQLState());
    }

    @Test
    public void testSomeQueryBetweenCountryAndCountrylanguage() {
        String query =
                "SELECT\n" +
                    "cl.language, cl.percentage, co.name, co.region\n" +
                "FROM\n" +
                    "countrylanguage cl\n" +
                    "JOIN country co\n" +
                        "ON cl.countrycode = co.code\n" +
                "WHERE\n" +
                    "cl.isofficial IS TRUE\n" +
                "LIMIT 1\n";

        ResultSet resultSet = null;
        List<String> result = new ArrayList<>();
        List<String> expected = new ArrayList<>(Arrays.asList(
                "Pashto", "52.4000015", "Afghanistan", "Southern and Central Asia"));

        try {
            resultSet = worldDBCreator.executeQuery(query);
            while (resultSet.next()) {
                result.add(resultSet.getString("language"));
                result.add(resultSet.getString("percentage"));
                result.add(resultSet.getString("name"));
                result.add(resultSet.getString("region"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expected, result);
    }



    @Test
    public void testSomeQueryBetweenCountryAndCity() {
        String query =
                "SELECT\n" +
                        "co.name, " +
                        "co.continent, " +
                        "co.region, " +
                        "co.surfacearea, " +
                        "co.governmentform, " +
                        "ci.name as capital_name, " +
                        "ci.population as capital_population\n" +
                        "FROM\n" +
                        "country co\n" +
                        "JOIN city ci\n" +
                        "ON co.capital = ci.id\n" +
                        "LIMIT 1\n";

        ResultSet resultSet = null;
        List<String> result = new ArrayList<>();
        List<String> expected = new ArrayList<>(Arrays.asList(
                "Afghanistan", "Asia", "Southern and Central Asia", "652090", "Islamic Emirate", "Kabul", "1780000"));

        try {
            resultSet = worldDBCreator.executeQuery(query);
            while (resultSet.next()) {
                result.add(resultSet.getString("name"));
                result.add(resultSet.getString("continent"));
                result.add(resultSet.getString("region"));
                result.add(resultSet.getString("surfacearea"));
                result.add(resultSet.getString("governmentform"));
                result.add(resultSet.getString("capital_name"));
                result.add(resultSet.getString("capital_population"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        assertEquals(expected, result);
    }
}
