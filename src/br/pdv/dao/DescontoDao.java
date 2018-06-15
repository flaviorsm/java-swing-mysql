package br.pdv.dao;

import br.pdv.connection.crud;
import br.pdv.connection.dao;
import br.pdv.model.Desconto;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flavio.moreira
 */
public class DescontoDao extends dao<Desconto> implements crud<Desconto>{

    private final ProdutoDao produtoDao;
    
    public DescontoDao() {
        this.produtoDao = new ProdutoDao();
    }

    @Override
    protected Desconto preencherEntidade(ResultSet rs) throws SQLException {
        Desconto entity = new Desconto();
        entity.setCodigo(rs.getInt("ID_DESCONTO"));
        entity.setProduto(produtoDao.obterPorId(rs.getInt("CODPROD")));
        entity.setPercentual(rs.getDouble("PERCENTUAL"));
        entity.setQtdMinima(rs.getInt("QTD_MIN"));  
        entity.setQtdMaxima(rs.getInt("QTD_MAX"));
        return entity;
    }

    @Override
    public void inserir(Desconto entity) throws SQLException {
        String query = "INSERT INTO desconto (CODPROD, PERCENTUAL, QTD_MIN, QTD_MAX) VALUES (%d, %f, %d, %d)";        
        query = String.format(query, entity.getProduto().getCodigo(), entity.getPercentual(), entity.getQtdMinima(), entity.getQtdMaxima());        
        save(query);
    }

    @Override
    public void alterar(Desconto entity) throws SQLException {
        String query = "UPDATE desconto SET CODPROD = %d, PERCENTUAL = %f, QTD_MIN = %d, QTD_MAX = %d WHERE ID_DESCONTO = %d";        
        query = String.format(query, entity.getProduto().getCodigo(), entity.getPercentual(), entity.getQtdMinima(), entity.getQtdMaxima(), entity.getCodigo());         
        update(query);
    }

    @Override
    public void excluir(int id) throws SQLException {
        String query = "DELETE FROM desconto WHERE ID_DESCONTO = " + id;        
        delete(query);
    }

    @Override
    public Desconto obterPorId(int id) throws SQLException {
        String query = "SELECT * FROM desconto WHERE ID_DESCONTO = " + id;
        select(query);
        return preencherEntidade(getResultSet());
    }

    @Override
    public List obterTodos() throws SQLException {
        List lista = new ArrayList();        
        String query = "SELECT * FROM desconto";
        select(query);
        ResultSet rs = getResultSet();
        while(rs.next()){            
            lista.add(preencherEntidade(rs));
        }        
        return lista;
    }
    
}
