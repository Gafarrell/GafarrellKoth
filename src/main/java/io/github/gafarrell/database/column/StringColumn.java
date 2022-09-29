package io.github.gafarrell.database.column;

import io.github.gafarrell.database.SQLColumn;

public class StringColumn extends SQLColumn<String> {

    public StringColumn(String name) {
        super(name);
    }

    @Override
    public void create(boolean primaryKey, boolean notNull) {
        StringBuilder sql = new StringBuilder();
        sql.append(columnName)
                .append(" VARCHAR(255)")
                .append(notNull ? " NOT NULL" : "")
                .append(primaryKey ? " PRIMARY KEY" : "");

        SQLString = sql.toString();
    }

    @Override
    public void where(String equals) {
        StringBuilder sql = new StringBuilder();
        sql.append("WHERE")
                .append(columnName)
                .append("=")
                .append(equals);

        SQLString = sql.toString();
    }
}
