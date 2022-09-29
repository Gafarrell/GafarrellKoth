package io.github.gafarrell.database.column;

import io.github.gafarrell.database.SQLColumn;

public class IntColumn extends SQLColumn<Integer> {

    public IntColumn(String name) {
        super(name);
    }

    @Override
    public void create(boolean primaryKey, boolean notNull) {
        StringBuilder sql = new StringBuilder();
        sql.append(columnName)
                .append(" int")
                .append(notNull ? " NOT NULL" : "")
                .append(primaryKey ? " PRIMARY KEY" : "");

        SQLString = sql.toString();
    }

    @Override
    public void where(Integer equals) {
        StringBuilder sql = new StringBuilder();
        sql.append("WHERE")
                .append(columnName)
                .append("=")
                .append(equals);

        SQLString = sql.toString();
    }
}
