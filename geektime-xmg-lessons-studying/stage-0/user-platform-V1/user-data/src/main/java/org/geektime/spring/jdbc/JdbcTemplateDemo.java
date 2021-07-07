package org.geektime.spring.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcTemplateDemo {

    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String userName = jdbcTemplate.query(
                // 创建 PreparedStatement（SQL）
                new SimplePreparedStatementCreator(),
                // 为 PreparedStatement 设置参数(配置)
                // setLong
                new SimplePreparedStatementSetter(),
                // 将 ResultSet 转换成 String 类型
                new UserNameResultSetExtractor()
        );
        String sql = "SELECT name FROM users WHERE id=?";
        userName = jdbcTemplate.query(
                connection -> connection.prepareStatement(sql),
                preparedStatement -> {preparedStatement.setLong(1,1);},
                resultSet -> resultSet.getString("name")
        );

    }

    private static void rawJdbcApi(){
        String sql = "SELECT name FROM users WHERE id=?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //传统jdbc的SQL操作
            preparedStatement = connection.prepareCall(sql);
            preparedStatement.setLong(1,1L);
            resultSet = preparedStatement.executeQuery();
        }catch (SQLException e){

        }finally {
            //关闭顺序 resultSet->preparedStatement->Connection
            /*if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {

                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {

                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {

                }
            }*/
            close(resultSet);
            close(preparedStatement);
            close(connection);
        }

    }

    private static void close(AutoCloseable autoCloseable){
        if (autoCloseable!=null){
            try {
                autoCloseable.close();
            }catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}
