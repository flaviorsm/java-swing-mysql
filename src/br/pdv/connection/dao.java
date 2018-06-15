package br.pdv.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author flavio.moreira
 * @param <T>
 */
public abstract class dao<T> {
    
    private final Connection connection;
    private ResultSet resultSet;    
        
    protected abstract T preencherEntidade(ResultSet rs) throws SQLException;
    
    protected ResultSet getResultSet() {
        return resultSet;
    }

    protected void setResultSet(ResultSet resultSet) {
        this.resultSet = resultSet;
    }
    
    protected dao() {
        this.connection = ConnectionDataBase.getConnection();
    }
    
    protected Connection getConnection(){
        return connection;
    }
    
    protected void save(String insertSql) throws SQLException {
        try (PreparedStatement pstmt = getConnection().prepareStatement(insertSql)) {
            pstmt.execute();            
        } 
        connection.close();
    }

    protected void update(String updateSql) throws SQLException {
        try (PreparedStatement pstmt = getConnection().prepareStatement(updateSql)) {
            pstmt.execute();
        }
        connection.close();
    }

    protected void delete(String deleteSql) throws SQLException {
        try (PreparedStatement pstmt = getConnection().prepareStatement(deleteSql)) {
            pstmt.execute();
        }
        connection.close();
    }
    
    protected void select(String selectSql) throws SQLException {        
        try (PreparedStatement stmt = getConnection().prepareStatement(selectSql)) {
            setResultSet(stmt.executeQuery());
        }        
        connection.close();               
    }
}