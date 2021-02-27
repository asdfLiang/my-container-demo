package com.lagou.transaction;

import com.alibaba.druid.proxy.jdbc.DataSourceProxyConfig;

import javax.sql.DataSource;

/**
 * @author liangzj
 * @date 2021/2/27 19:42
 */
public interface DataSourceConfig {

    DataSource getDataSource();
}
