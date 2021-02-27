package com.lagou.transaction;

import com.lagou.factory.annotation.Autowired;
import com.lagou.factory.annotation.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author liangzj
 * @date 2021/2/27 17:04
 */
@Component
public class TransactionalTemplate {

    private Connection connection;

    public ResultSet query(String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return null;
    }

    public Integer update(String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeUpdate(sql);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return 0;
    }

}
