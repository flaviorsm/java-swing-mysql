/**
 * Author:  flavio.moreira
 * Created: 15/06/2018
 */
CREATE TABLE IF NOT EXISTS  localidade (
    CODLOCAL    INT NOT NULL AUTO_INCREMENT,
    NOME        VARCHAR(35) NOT NULL,
    ENDERECO    VARCHAR(80) NULL,
    TELEFONE    VARCHAR(14) NULL,
    PRIMARY KEY (CODLOCAL)
);


