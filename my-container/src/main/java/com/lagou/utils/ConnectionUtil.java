package com.lagou.utils;

import com.lagou.factory.annotation.WebApplicationContextUtil;
import com.lagou.factory.support.AnnotationConfigApplicationContext;
import com.lagou.transaction.DataSourceConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 获取连接
 *
 * @author liangzj
 * @date 2021/2/27 1:36
 */
public class ConnectionUtil {

    ThreadLocal<Connection> ctl = new ThreadLocal<>();

    private static ConnectionUtil connectionUtil = new ConnectionUtil();

    private ConnectionUtil() {
    }

    public static ConnectionUtil getInstance() {
        return connectionUtil;
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        if (ctl.get() == null) {
            AnnotationConfigApplicationContext applicationContext = WebApplicationContextUtil.getApplicationContext();
            DataSource dataSource = applicationContext.getBean(DataSource.class);
            // 线程绑定数据库连接
            Connection connection = dataSource.getConnection();
            ctl.set(connection);
            return connection;
        }

        return ctl.get();
    }

}
