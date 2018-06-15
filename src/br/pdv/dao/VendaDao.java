package br.pdv.dao;

import br.pdv.connection.crud;
import br.pdv.connection.dao;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.pdv.model.Venda;

/**
 *
 * @author flavio.moreira
 */
public class VendaDao extends dao<Venda> implements crud<Venda> {

    private final ClienteDao clienteDao;
    private final ProdutoDao produtoDao;
    private final LocalidadeDao localDao;
    
    public VendaDao() {
        this.clienteDao = new ClienteDao();
        this.produtoDao = new ProdutoDao();
        this.localDao = new LocalidadeDao();
    }
    
    @Override
    protected Venda preencherEntidade(ResultSet rs) throws SQLException{
        Venda entity = new Venda();
        entity.setCodVenda(rs.getInt("CODVENDA"));
        entity.setCliente(clienteDao.obterPorId(rs.getInt("CODCLI")));
        entity.setProduto(produtoDao.obterPorId(rs.getInt("CODPROD")));
        entity.setLocal(localDao.obterPorId(rs.getInt("CODLOCAL")));
        entity.setQtdVenda(rs.getInt("QTD_VENDA"));
        entity.setValorTotal(rs.getDouble("VALOR_TOTAL"));
        entity.setDataVenda(rs.getDate("DATA_VENDA"));        
        return entity;
    }
    
    @Override
    public void inserir(Venda entity) throws SQLException {
        String query = "INSERT INTO venda (CODCLI, CODPROD, CODLOCAL, QTD_VENDA, VALOR_TOTAL, DATA_VENDA)"
                                + " VALUES (%d, %d, %d, %d, %f, %t)";        
        query = String.format(query, entity.getCliente().getCodigo(), entity.getProduto().getCodigo(), 
                                     entity.getLocal().getCodigo(), entity.getQtdVenda(), 
                                     entity.getValorTotal(), entity.getDataVenda());        
        save(query);
    }

    @Override
    public void alterar(Venda entity) throws SQLException {
        String query = "UPDATE venda SET QTD_VENDA = %d, VALOR_TOTAL = %f, DATA_VENDA = %t "
                                + "WHERE CODCLI = %d, CODPROD = %d, CODLOCAL = %d";        
        query = String.format(query, entity.getQtdVenda(), entity.getValorTotal(), entity.getDataVenda(),
                                     entity.getCliente().getCodigo(), entity.getProduto().getCodigo(), entity.getLocal().getCodigo());        
        update(query);
    }

    @Override
    public void excluir(int id) throws SQLException {
        String query = "DELETE FROM venda WHERE CODVENDA = " + id;
        delete(query);
    }

    @Override
    public Venda obterPorId(int id) throws SQLException {
        String query = "SELECT * FROM venda WHERE CODVENDA = " + id;
        select(query);
        return preencherEntidade(getResultSet());
    }
    
    @Override
    public List obterTodos() throws SQLException{
        List lista = new ArrayList();        
        String query = "SELECT * FROM venda";
        select(query);
        ResultSet rs = getResultSet();
        while(rs.next()){            
            lista.add(preencherEntidade(rs));
        }        
        return lista;        
    }    
}
