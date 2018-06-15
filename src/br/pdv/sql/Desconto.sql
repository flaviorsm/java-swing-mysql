/**
 * Author:  flavio.moreira
 * Created: 15/06/2018
 */
CREATE TABLE IF NOT EXISTS desconto (
    ID_DESCONTO INT NOT NULL AUTO_INCREMENT,
    CODPROD     INT
    PERCENTUAL  DOUBLE,
    QTD_MIN     INT,
    QTD_MAX     INT,
);

ALTER TABLE desconto ADD
 CONSTRAINT fk_produto
FOREIGN KEY (CODPROD) 
 REFERENCES produto (CODPROD);