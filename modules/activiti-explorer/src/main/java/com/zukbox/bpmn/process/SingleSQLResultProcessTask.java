package com.zukbox.bpmn.process;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;

import org.activiti.engine.delegate.DelegateExecution;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class SingleSQLResultProcessTask extends SQLProcessTask {

    @Override
    protected void executeSQL(DelegateExecution execution) {
        Properties property = executeQuery(execution);
        execution.setVariable("value", property);
        execution.setVariableLocal("value", property);        
    }

    protected Properties executeQuery(DelegateExecution execution) {
        return this.getTemplate().query(this.getSQL(execution), new ObjectResultSetExtractor());
    }

    class ObjectResultSetExtractor implements ResultSetExtractor<Properties> {

        @Override
        public Properties extractData(ResultSet rs) throws SQLException, DataAccessException {
            Properties props = null;
            if(rs.next()){
                props = new Properties();
                ResultSetMetaData rsmd = rs.getMetaData();
                
                for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String columnValue = String.valueOf(rs.getObject(columnName));
                    props.setProperty(columnName, columnValue);
                }
             }
            return props;
        }
    }
}
