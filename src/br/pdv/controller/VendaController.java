
package br.pdv.controller;

import br.pdv.dao.*;
import br.pdv.model.Produto;
import br.pdv.model.Venda;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author flavio.moreira
 */
public class VendaController {

    private final VendaDao vendaDao;
    private final ProdutoDao produtoDao;
    
    public VendaController() {
        this.vendaDao = new VendaDao();
        this.produtoDao = new ProdutoDao();
    }
    
    private Date formatarData(String data) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return new Date( formatter.parse(data).getTime() );
    }
    
    public String IncluirVenda(Venda venda) throws SQLException {
        Produto produto = produtoDao.obterPorId(venda.getProduto().getCodigo());
        
        if(produto == null){
            return "Produto não encontrado!";
        }
        else if(produto.getQtdEstoque() < venda.getQtdVenda()){
            return "Produto sem estoque!";
        }
        else {            
            int qtd = produto.getQtdEstoque() - venda.getQtdVenda();
            produto.setQtdEstoque(qtd);
            vendaDao.inserir(venda);
            produtoDao.alterar(produto);
            return "Venda incluida com sucesso!";
        }        
    }    
}
