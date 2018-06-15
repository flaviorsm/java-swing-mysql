package br.pdv.dao;

import br.pdv.connection.crud;
import br.pdv.connection.dao;
import br.pdv.model.Produto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flavio.moreira
 */
public class ProdutoDao extends dao<Produto> implements crud<Produto>{
    private final LocalidadeDao localDao;
    
    public ProdutoDao() {
        this.localDao = new LocalidadeDao();
    }
    
    @Override
    protected Produto preencherEntidade(ResultSet rs) throws SQLException {        
        Produto entity = new Produto();
        entity.setCodigo(rs.getInt("CODPROD"));
        entity.setCodLocal(rs.getInt("CODLOCAL"));
        entity.setLocal(localDao.obterPorId(rs.getInt("CODLOCAL")));
        entity.setDescricao(rs.getString("DESCRICAO"));
        entity.setQtdEstoque(rs.getInt("QTD_ESTOQUE"));  
        entity.setPrecoUnitario(rs.getDouble("PRECO_UNITARIO"));
        return entity;
    }

    @Override
    public void inserir(Produto entity) throws SQLException {
        String query = "INSERT INTO produto (CODLOCAL, DESCRICAO, QTD_ESTOQUE, PRECO_UNITARIO) VALUES (%d, %s, %d, %f)";        
        query = String.format(query, entity.getCodLocal(), entity.getDescricao(), entity.getQtdEstoque(), entity.getPrecoUnitario());        
        save(query);
    }

    @Override
    public void alterar(Produto entity) throws SQLException {
        String query = "UPDATE produto SET CODLOCAL = %d, DESCRICAO = %s, QTD_ESTOQUE = %d, PRECO_UNITARIO = %f WHERE CODPROD = %d";        
        query = String.format(query, entity.getCodLocal(), entity.getDescricao(), entity.getQtdEstoque(), entity.getCodigo());         
        update(query);
    }

    @Override
    public void excluir(int id) throws SQLException {
        String query = "DELETE FROM produto WHERE CODPROD = " + id;        
        delete(query);
    }

    @Override
    public Produto obterPorId(int id) throws SQLException {
        String query = "SELECT * FROM produto WHERE CODPROD = " + id;
        select(query);
        return preencherEntidade(getResultSet());
    }

    @Override
    public List obterTodos() throws SQLException {
        List lista = new ArrayList();        
        String query = "SELECT * FROM produto";
        select(query);
        ResultSet rs = getResultSet();
        while(rs.next()){            
            lista.add(preencherEntidade(rs));
        }        
        return lista;
    }
    
}
