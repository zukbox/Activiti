package com.zukbox.bpmn.process;

import java.util.Properties;

import org.activiti.engine.delegate.DelegateExecution;

public class UpdateSQLProcessTask extends SQLProcessTask {

    @Override
    protected void executeSQL(DelegateExecution execution) {
        Properties props = new Properties();
        int rows = this.getTemplate().update(this.getSQL(execution));
        props.setProperty("rows", String.valueOf(rows));
        execution.setVariableLocal("updateResult", props);
    }

}
