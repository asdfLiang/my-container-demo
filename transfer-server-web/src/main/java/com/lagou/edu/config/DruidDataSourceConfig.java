package com.lagou.edu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.lagou.edu.spring.factory.annotation.Component;
import com.lagou.edu.spring.utils.Resources;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * 数据库连接池配置
 *
 * @author liangzj
 * @date 2021/2/27 18:14
 */
@Component
public class DruidDataSourceConfig implements DataSource {
    static {
        try {
            Properties properties = new Properties();
            // 获取配置文件
            List<InputStream> propertiesList = Resources.getFileBySuffix(".properties");
            for (InputStream in : propertiesList) {
                properties.load(in);
            }

            // 数据库连接池配置
            Properties systemProperties = System.getProperties();
            systemProperties.put("druid.name", properties.getProperty("druid.name"));
            systemProperties.put("druid.url", properties.getProperty("druid.url"));
            systemProperties.put("druid.username", properties.getProperty("druid.username"));
            systemProperties.put("druid.password", properties.getProperty("druid.password"));

            System.out.println(System.getProperties());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private DataSource getDataSource() {
        return new DruidDataSource();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
