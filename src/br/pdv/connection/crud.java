package br.pdv.connection;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author flavio.moreira
 * @param <T>
 */
public interface crud<T> {
    void inserir(T entity) throws SQLException;
    void alterar(T entity) throws SQLException;
    void excluir(int id) throws SQLException;    
    T obterPorId(int id) throws SQLException;
    List obterTodos() throws SQLException;    
}
