package io.github.gafarrell.database.column;

import io.github.gafarrell.database.SQLColumn;

import java.sql.Timestamp;

public class TimeColumn extends SQLColumn<Timestamp>{

    public TimeColumn(String name) {
        super(name);
    }

    @Override
    public void create(boolean primaryKey, boolean notNull) {
        StringBuilder sql = new StringBuilder();
        sql.append(columnName)
                .append(" DATETIME")
                .append(notNull ? " NOT NULL" : "")
                .append(primaryKey ? " PRIMARY KEY" : "");

        SQLString = sql.toString();
    }

    @Override
    public void where(Timestamp equals) {
        StringBuilder sql = new StringBuilder();
        sql.append("WHERE")
                .append(columnName)
                .append("=")
                .append(equals);

        SQLString = sql.toString();
    }
}
