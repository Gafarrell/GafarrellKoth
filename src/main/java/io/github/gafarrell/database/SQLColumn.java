package io.github.gafarrell.database;

public abstract class SQLColumn<T>{
    protected String columnName;
    protected String SQLString;

    public abstract void create(boolean primaryKey, boolean notNull);
    public abstract void where(T equals);

    public SQLColumn(String name){ columnName = name; }

    @Override
    public String toString(){ return getSQLString();}

    public String getSQLString() { return SQLString; }
    public String getColumnName() { return columnName; }
}
