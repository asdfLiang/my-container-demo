package com.lagou.edu.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.lagou.factory.annotation.Component;
import com.lagou.transaction.DataSourceConfig;
import com.lagou.utils.Resources;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author liangzj
 * @date 2021/2/27 18:14
 */
@Component
public class DruidDataSourceConfig implements DataSourceConfig {
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

    @Override
    public DataSource getDataSource() {
        return new DruidDataSource();
    }
}
