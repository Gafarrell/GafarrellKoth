package io.github.gafarrell.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;

public class SQLiteConnector {
    private Connection conn = null;
    private Logger logger;

    public SQLiteConnector(String dbPath, Logger serverLogger)
    {
        String url = "jbdc:sqlite:" + dbPath;
        logger = serverLogger;

        try {
            logger.info("Connecting to SQLite...");
            conn = DriverManager.getConnection(url);
            logger.info("Successfully connected to SQLite.");
        }
        catch (SQLException e) {
            logger.info("Unable to connect to SQLite.");
            logger.info(e.getMessage());
        }
    }

    public void createTable(String name, String[] tableVariables){
        if (conn == null) return;

        StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + name + "(");
        for (String s : tableVariables)
        {
            query.append(s).append(",\n");
        }
        query.append(");");

        try {
            Statement createStatement = conn.createStatement();

            createStatement.execute(query.toString());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
