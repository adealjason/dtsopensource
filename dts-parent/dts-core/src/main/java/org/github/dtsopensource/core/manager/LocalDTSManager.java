package org.github.dtsopensource.core.manager;

import javax.sql.DataSource;

import org.github.dtsopensource.core.store.impl.LocalDTSStore;
import org.github.dtsopensource.server.share.exception.DTSRuntimeException;
import org.github.dtsopensource.server.share.store.IDTSStore;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.Setter;
import lombok.ToString;

/**
 * Created by ligaofeng on 2016/10/31.
 */
@ToString
public class LocalDTSManager extends DTSManager {

    @Setter
    private DataSource          dataSource;

    private JdbcTemplate        jdbcTemplate;

    private TransactionTemplate transactionTemplate;

    @Override
    protected void check() {
        if (dataSource == null) {
            throw new DTSRuntimeException("local模式下必须指定DataSource数据源");
        }
    }

    @Override
    public IDTSStore initStore() {
        jdbcTemplate = new JdbcTemplate(dataSource);
        transactionTemplate = new TransactionTemplate(new DataSourceTransactionManager(dataSource));
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return new LocalDTSStore(jdbcTemplate, transactionTemplate);
    }

}
