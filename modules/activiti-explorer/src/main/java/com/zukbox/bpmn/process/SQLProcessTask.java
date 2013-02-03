package com.zukbox.bpmn.process;import java.util.logging.Logger;import org.activiti.engine.delegate.DelegateExecution;import org.activiti.engine.delegate.JavaDelegate;import org.activiti.engine.impl.el.FixedValue;import org.activiti.engine.impl.el.JuelExpression;import com.zukbox.bpmn.process.base.DataSourceTask;public abstract class SQLProcessTask extends DataSourceTask implements JavaDelegate {    private static Logger log = Logger.getLogger(SQLProcessTask.class.getName());    protected FixedValue fixedSQL;    protected JuelExpression expressionSQL;    public void execute(DelegateExecution execution) {        this.executeSQL(execution);    }    protected abstract void executeSQL(DelegateExecution execution);    public void setFixedSQL(FixedValue fixedSQL) {        this.fixedSQL = fixedSQL;    }    public void setExpressionSQL(JuelExpression expressionSQL) {        this.expressionSQL = expressionSQL;    }    public String getSQL(DelegateExecution execution) {        String sql = null;        if (expressionSQL != null) {            sql = expressionSQL.getValue(execution).toString();        } else if (fixedSQL != null) {            sql = fixedSQL.getValue(execution).toString();        }        log.info("Executing sql: " + sql);        return sql;    }}