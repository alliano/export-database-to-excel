package com.export.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;

import lombok.AllArgsConstructor;


@Configuration @AllArgsConstructor
public class ExecuteXlsx {

    private final DataSource dataSource;

    public void createSchema() throws SQLException{
        Connection connection = dataSource.getConnection();
        Statement statement = connection.createStatement();
        String ddl = """
                """;
        statement.execute(ddl);
        connection.close();
        statement.close();
    }

    public void batchInsert(){

    }
}
