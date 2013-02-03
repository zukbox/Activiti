package com.zukbox.bpmn.process.base;

import javax.sql.DataSource;

import org.activiti.engine.impl.context.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DataSourceTask {

    protected DataSource datasource;
    protected JdbcTemplate template;

    public DataSourceTask() {
        try {
            this.datasource = (DataSource) Context.getProcessEngineConfiguration().getBeans().get("dataSource.single");
        } catch (Exception e) {
            // do nothing
        }
        if (this.datasource == null) {
            this.datasource = new DriverManagerDataSource("jdbc:mysql://localhost:3306/zukbox?autoReconnect=true", "root", "");
        }
        this.template = new JdbcTemplate(datasource);
    }

    protected JdbcTemplate getTemplate() {
        return template;
    }

}
