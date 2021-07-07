package org.geektime.spring.jdbc;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimplePreparedStatementCreator implements PreparedStatementCreator {
    @Override
    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        return connection.prepareStatement("select name from user where id=?");
    }
}
