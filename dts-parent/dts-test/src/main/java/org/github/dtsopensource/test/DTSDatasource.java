package org.github.dtsopensource.test;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author ligaofeng 2016年12月20日 下午2:20:19
 */
public class DTSDatasource {

    public static void main(String[] args) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://rds6fbqrf6fbqrf.mysql.rds.aliyuncs.com:3306/investment_batch_00");
        dataSource.setUsername("za_dev_ib");
        dataSource.setPassword("za_dev_ib_4ee2bc");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(10);
        System.out.println(dataSource);
    }
}
