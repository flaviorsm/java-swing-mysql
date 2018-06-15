package br.pdv.dao;

import br.pdv.connection.crud;
import br.pdv.connection.dao;
import br.pdv.model.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flavio.moreira
 */
public class ClienteDao extends dao<Cliente> implements crud<Cliente> {

    @Override
    protected Cliente preencherEntidade(ResultSet rs) throws SQLException {
        Cliente entity = new Cliente();
        entity.setCodigo(rs.getInt("CODCLI"));
        entity.setNome(rs.getString("NOME"));
        entity.setBonus(rs.getInt("BONUS"));
        entity.setStatus(rs.getString("STATUS"));
        entity.setPerfil(rs.getString("PERFIL"));       
        return entity;
    }

    @Override
    public void inserir(Cliente entity) throws SQLException {
        String query = "INSERT INTO cliente (NOME, BONUS, STATUS, PERFIL) VALUES (%s, %d, %s, %s)";        
        query = String.format(query, entity.getNome(), entity.getBonus(), entity.getStatus(), entity.getPerfil());        
        save(query);
    }

    @Override
    public void alterar(Cliente entity) throws SQLException {
        String query = "UPDATE cliente SET NOME = %s, BONUS = %d, STATUS = %s, PERFIL = %s WHERE CODCLI = %d";        
        query = String.format(query, entity.getNome(), entity.getBonus(), entity.getStatus(), entity.getPerfil(), entity.getCodigo());         
        update(query);
    }

    @Override
    public void excluir(int id) throws SQLException {
        String query = "DELETE FROM cliente WHERE CODCLI = " + id;        
        delete(query);
    }

    @Override
    public Cliente obterPorId(int id) throws SQLException {
        String query = "SELECT * FROM cliente WHERE CODCLI = " + id;
        select(query);
        return preencherEntidade(getResultSet());
    }

    @Override
    public List obterTodos() throws SQLException {
        List lista = new ArrayList();        
        String query = "SELECT * FROM cliente";
        select(query);
        ResultSet rs = getResultSet();
        while(rs.next()){            
            lista.add(preencherEntidade(rs));
        }        
        return lista;      
    }
    
}
