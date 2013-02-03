package com.zukbox.bpmn.process;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.jdbc.core.RowMapper;

public class MultipleSQLResultProcessTask extends SQLProcessTask {

    @Override
    protected void executeSQL(DelegateExecution execution) {
        List<Properties> properties = executeQuery(execution);
        execution.setVariable("objects", properties);
        execution.setVariable("size", properties.size());        
    }

    protected List<Properties> executeQuery(DelegateExecution execution) {
        return this.getTemplate().query(this.getSQL(execution), new ObjectRowMapper());
    }

    class ObjectRowMapper implements RowMapper<Properties> {
        @Override
        public Properties mapRow(ResultSet rs, int rowNum) throws SQLException {
            ResultSetMetaData rsmd = rs.getMetaData();
            Properties props = new Properties();

            for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                String columnName = rsmd.getColumnName(i);
                String columnValue = String.valueOf(rs.getObject(columnName));
                props.setProperty(columnName, columnValue);
            }
            return props;
        }
    }
}
