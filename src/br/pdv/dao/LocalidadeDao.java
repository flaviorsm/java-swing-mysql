package br.pdv.dao;

import br.pdv.connection.crud;
import br.pdv.connection.dao;
import br.pdv.model.Localidade;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flavio.moreira
 */
public class LocalidadeDao extends dao<Localidade> implements crud<Localidade> {

    @Override
    protected Localidade preencherEntidade(ResultSet rs) throws SQLException {
        Localidade entity = new Localidade();
        entity.setCodigo(rs.getInt("CODLOCAL"));
        entity.setNome(rs.getString("NOME"));
        entity.setEndereco(rs.getString("ENDERECO"));
        entity.setTelefone(rs.getString("TELEFONE"));      
        return entity;
    }

    @Override
    public void inserir(Localidade entity) throws SQLException {
        String query = "INSERT INTO localidade (NOME, ENDERECO, TELEFONE) VALUES (%s, %s, %s)";        
        query = String.format(query, entity.getNome(), entity.getEndereco(), entity.getTelefone());        
        save(query);
    }

    @Override
    public void alterar(Localidade entity) throws SQLException {
        String query = "UPDATE localidade SET NOME = %s, ENDERECO = %s, TELEFONE = %s WHERE CODLOCAL = %d";        
        query = String.format(query, entity.getNome(), entity.getEndereco(), entity.getTelefone(), entity.getCodigo());         
        update(query);
    }

    @Override
    public void excluir(int id) throws SQLException {
        String query = "DELETE FROM localidade WHERE CODLOCAL = " + id;        
        delete(query);
    }

    @Override
    public Localidade obterPorId(int id) throws SQLException {
        String query = "SELECT * FROM localidade WHERE CODLOCAL = " + id;
        select(query);
        return preencherEntidade(getResultSet());
    }

    @Override
    public List obterTodos() throws SQLException {
        List lista = new ArrayList();        
        String query = "SELECT * FROM localidade";
        select(query);
        ResultSet rs = getResultSet();
        while(rs.next()){            
            lista.add(preencherEntidade(rs));
        }        
        return lista;    
    }
    
}
