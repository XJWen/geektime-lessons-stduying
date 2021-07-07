package org.geektime.spring.jdbc;

import org.springframework.jdbc.core.PreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SimplePreparedStatementSetter implements PreparedStatementSetter {


    @Override
    public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setLong(1,1L);
    }
}
