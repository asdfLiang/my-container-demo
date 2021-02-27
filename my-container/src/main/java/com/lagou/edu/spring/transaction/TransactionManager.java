package com.lagou.edu.spring.transaction;

import com.lagou.edu.spring.factory.annotation.Component;
import com.lagou.edu.spring.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 事务管理器
 *
 * @author liangzj
 * @date 2021/2/25 0:37
 */
@Component
public class TransactionManager {

    public void beginTransaction() throws SQLException {
        Connection connection = ConnectionUtil.getInstance().getConnection();
        connection.setAutoCommit(false);
        System.out.println("begin transaction..........");
    }

    public void commitTransaction() throws SQLException {
        Connection connection = ConnectionUtil.getInstance().getConnection();
        connection.commit();
        System.out.println("commit transaction..........");
    }

    public void rollBack() throws SQLException {
        ConnectionUtil.getInstance().getConnection().rollback();
        System.out.println("rollback transaction..........");
    }
}
