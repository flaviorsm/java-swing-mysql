/**
 * Author:  flavio.moreira
 * Created: 15/06/2018
 */
CREATE TABLE IF NOT EXISTS  cliente (
    CODCLI    INT NOT NULL AUTO_INCREMENT,
    NOME      VARCHAR(35) NOT NULL,
    BONUS     INT NULL COMMENT 'Somente valores superiores a 100 pontos',
    STATUS    CHAR(1) NULL COMMENT 'P - pequeno; M - médio ou G - Grande',
    PERFIL    CHAR(1) NULL COMMENT 'A - ativo ou I - Inativo',
    PRIMARY KEY (CODCLI)
);