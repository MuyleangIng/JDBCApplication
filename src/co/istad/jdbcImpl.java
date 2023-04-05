package co.istad;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;

public class jdbcImpl {
    public DataSource dataSource(){
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser("postgres");
        dataSource.setPassword("080720");
        dataSource.setDatabaseName("senpai");
        return dataSource;
    }
}
