package org.github.dtsopensource.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.ImportResource;

/**
 * Application
 * 
 * @author ligaofeng 2016年12月21日 下午4:11:22
 */
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class })
@SpringBootApplication
@ImportResource("classpath:spring/dts-admin-application.xml")
public class DTSAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DTSAdminApplication.class, args);
    }
}
