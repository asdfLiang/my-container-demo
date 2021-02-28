package com.lagou.edu.dao;

import com.lagou.edu.spring.factory.annotation.Component;
import com.lagou.edu.spring.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author liangzj
 * @date 2021/2/27 22:28
 */
@Component
public class AccountDAO {

    public int update(String cardNo, Double money) throws SQLException {
        String sql = "UPDATE account SET amount = (amount + ?) WHERE card_no = ?";

        Connection connection = ConnectionUtil.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1, money);
        preparedStatement.setString(2, cardNo);

        return preparedStatement.executeUpdate();
    }

}
