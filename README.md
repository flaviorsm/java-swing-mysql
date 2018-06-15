# java-swing-mysql

## DESENVOLVIMENTO / ALGORITMOS

Desenvolver uma aplicação de ponto de venda, utilizando JAVA Swing e MySQL,  
para efetuar a INCLUSÃO e a EXCLUSÃO na venda de produtos.

## Classes / Tabelas

### PRODUTO
### Atibutos / Tipo / Chave e Descrição
CODCLI / Autonumeração / (PK)
NOME / Texto(35)
BONUS / Numero / Somente valores superiores a 100 pontos
PERFIL / Texto(1) / P - pequeno; M - médio ou G - Grande
STATUS / Texto(1) / A - ativo ou I - Inativo

### VENDA
### Atibutos / Tipo / Chave e Descrição  
CODPROD / Autonumeração / (PK)    
CODLOCAL / Numero / (FK)  
DESCRICAO / Texto(35)   
QTD_ESTOQUE / Numero  
PRECO_UNITARIO / Moeda    

### VENDA
### Atibutos / Tipo / Chave e Descrição  
CODCLI / Autonumeração / (PK) e (FK)  
CODPROD / Numero /(PK) e (FK)  
CODLOCAL / Numero / (PK) e (FK)  
QTD_VENDA / Numero  
VALOR_TOTAL / Moeda  
DATA_VENDA / Data  

## CADASTRAR VENDA

Início do Programa INCLUIR_VENDA;  

*** OBTER DADOS JUNTO AO USUÁRIO ***  

Ler CodCli, Codprod, CodLocal, Quantidade  

*** TRATAR ESTOQUE E A EXISTÊNCIA DO PRODUTO ***  

EXEC SQL SELECT descricao, local_fab, qtd_estoque, preco_unitario From Produto Where Codprod = :Xcodprod  

Se SQL Not Ok! então   
     Escreva “Produto não encontrado!”;  
     Sair;  
Fim se  

Se (qtd_estoque < Quantidade) então  
      Escreva “Produto sem estoque!”;  
      Sair;  
Fim se  
  
EXEC SQL UPDATE Produto set qtd_estoque = qtd_estoque - :QuantidadeVendida  
                    WHERE Codprod = :Xcodprod;  

*** CALCULAR O PREÇO TOTAL ***  

total = total + (Quantidade * preço);  
Escreva “Preço calculado: ”, total  

*** CALCULAR DESCONTO 1: Com base no bônus e na tabela de Descontos ***  

EXEC SQL EXECUTE  
           BEGIN  
                 total ← CALCULAR_DESCONTO(CodProd, CodCli, Quantidade: Inteiro; Total: Real): Real;  
           END;  
END-EXEC  

*** CALCULAR DESCONTO 2: Compras no mesmo local de fabricação tem 10% de desconto ***  
Se (CodLocal = LNPROD) então  
      total = total – (total * 10/100);  
      Escreva “Valor calculado da venda: ”, total;   
Fim se   

*** GRAVAR VENDA ***  

EXEC SQL INSERT INTO Venda (CodCli, CodProd, CodLocal, Qtd_venda, valor_total, data_venda)  
                    VALUES (:XCodcli, :XCodPro, :XCodLocal, :XQuantidade, :Xtotal, :Xdatasistema);  

Se SQL = OK então  
      Escreva “Venda incluída com sucesso!”;  
      Commit;  
    Senão  
      Escreva “Erro na gravação da venda!”;  
      Rollback;  
Fim se  

*** LISTAR ITENS VENDIDOS ***  

EXEC SQL SELECT b.descricao, a.Qtd_venda, a.preco_unitario, a.valor_total  
                   From Venda a, Produto b, Cliente c  
                   Where a.Codprod = b.CodProd  
	        And c.CodCli = :Xcodcli;    
       
Fim do Programa INCLUIR_VENDA  

## EXCLUIR VENDA

*** DELETAR VENDA ***
Ler codcli, codprod, dt_venda

Exec SQL Select Total Into VTOT, QTDE Into VQTDE
                             Codlocal Into Vlocalvenda
                  From Venda
                  Where codcli = :codcli And codpro = :codprod and
                              Datavenda = :dt_venda FOR UPDATE;

Se SQLCA.SQLCODE != 0 then
      Escreva “Venda Inexistente!”;
      Exec SQL Rollback;
      Continue;
Fim se;

*** TRATAR ESTOQUE ***
Exec SQL Update Produto SET QTDE = QTDE + VQTDE
                  Where PN = :codProd;

Se SQLCA.SQLCOD != 0 then
      Exec SQL Rollback;
      Continue;
Fim se;

*** DEVOLVER BONUS DO CLIENTE ***
Exec SQL Select precounitario Into VPRECO, codlocal Into Vlocalprod
                 From produto Where PN = :codpro;

VLRTOTALREAL = VPRECO * VQTDE;

Se Vlocalvenda == Vlocalprod then
     VLRDESCLOCAL = VLRTOTALREAL * 0.10;
     VTOT = :VTOT + VLRDESCLOCAL;
Fim se;

Exec SQL Update Cliente SET BONUS = BONUS + 100
                  Where CN = :codcli And :VTOT < :VLRTOTALREAL;

*** DELETAR A VENDA ***
Exec SQL Delete From Venda Where cód_cli = :codcli and datavenda = :dt_venda;

Se SQLCA.SQLCODE != 0 then
        Escreva “Venda não excluída”!  
        Exec SQL Rollback;  
        Continue;  
    Else  
        Escreva “Venda excluída com sucesso!”;  
        Exec SQL COMMIT;  
Fim se;  
