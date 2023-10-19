package backend.jdbc.service.dbms;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SQLScriptParser {

    public static String[] parseSQL(String sqlScriptFile) throws IOException {
        StringBuilder sqlScript = new StringBuilder();
        
        try (InputStream inputStream = backend.jdbc.Main.class.getResourceAsStream(sqlScriptFile);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sqlScript.append(line).append("\n");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + sqlScriptFile);
            throw e;
        } catch (IOException e) {
            System.err.println("Error reading create database SQL Script.");
            throw e;
        }

        return sqlScript.toString().split(";");
    }

}
