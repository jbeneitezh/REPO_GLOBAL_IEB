

DELIMITER $$
DROP PROCEDURE IF EXISTS IA_APP.NUEVO_ESCENARIO;
$$
DELIMITER;


DELIMITER $$
CREATE PROCEDURE IA_APP.NUEVO_ESCENARIO(IN  NOMBRE  VARCHAR (200), 
										OUT P_RES   INT)

BEGIN

	DECLARE vID INT DEFAULT 0;
	
	SET P_RES = 0;
	
	SELECT MAX(a.ID_ESCENARIO) 
	  INTO vID
	  FROM IA_APP.T_ESCENARIOS a;
	
	SET vID = IFNULL(vID, 0) + 1;
	/*Registrando escenario*/
	INSERT INTO T_ESCENARIOS VALUES (vID,
									 NOMBRE,
									 SYSDATE(),
									 SYSDATE(),
									 NULL);
	/*Insertando valores por defecto*/
	
	INSERT INTO IA_APP.T_CONFIG_ESCENARIOS VALUES(vID, 1, ' ', SYSDATE());
	
	INSERT INTO IA_APP.T_CONFIG_ESCENARIOS VALUES(vID, 2, ' ', SYSDATE());
	
	INSERT INTO IA_APP.T_CONFIG_ESCENARIOS VALUES(vID, 5, '1000', SYSDATE());
	
	INSERT INTO IA_APP.T_CONFIG_ESCENARIOS VALUES(vID, 99, '5', SYSDATE());
	
	INSERT INTO IA_APP.T_CONFIG_ESCENARIOS VALUES(vID, 100, '15', SYSDATE());
	
	INSERT INTO IA_APP.T_CONFIG_ESCENARIOS VALUES(vID, 102, '0.1', SYSDATE());	
	
	SET P_RES = 1;
	
END$$
DELIMITER;

DELIMITER $$
DROP PROCEDURE IF EXISTS IA_APP.MODIFICA_PARAM_ESCENARIO;
$$
DELIMITER;


DELIMITER $$
CREATE PROCEDURE IA_APP.MODIFICA_PARAM_ESCENARIO(IN  pID_ESCEN    INT,
												 IN  pID_PARAM    INT,
												 IN  pVALOR       VARCHAR (200), 
										         OUT P_RES        INT,
												 OUT P_TXT_RES    VARCHAR(200))

BEGIN

	DECLARE vExiste INT DEFAULT 0;
	
	SET P_RES = 0;
	SET P_TXT_RES = 'Se produjo un error.';
	
	SELECT COUNT(*)
	  INTO vExiste
	  FROM IA_APP.T_CONFIG_ESCENARIOS a;
	
	IF vExiste = 0 THEN
		/*SELECT CONCAT('El parametro ',CONVERT(pID_PARAM, CHAR), ' para el escenario ', CONVERT(pID_ESCEN, CHAR), ' no existia') INTO P_TXT_RES FROM DUAL;*/
		SET P_TXT_RES = 'El parametro no existia.';
	ELSE
		UPDATE IA_APP.T_CONFIG_ESCENARIOS
		   SET VALOR = pVALOR,
			   F_CONFIG = SYSDATE()
		 WHERE ID_ESCENARIO = pID_ESCEN
		   AND ID_PARAM     = pID_PARAM;
		
		UPDATE IA_APP.T_ESCENARIOS
		   SET F_MODIFICACION = SYSDATE()
		 WHERE ID_ESCENARIO = pID_ESCEN;
		 
		/*SELECT CONCAT('Actualizado el parametro ',CONVERT(pID_PARAM,CHAR), ' del Escenario ', CONVERT(pID_ESCEN, CHAR), ' a ', pVALOR) INTO P_TXT_RES FROM DUAL;*/
		SET P_TXT_RES = 'Parametro actualizado';
		
		SET P_RES = 1;
	END IF;
	

END$$
DELIMITER;










 IA_APP.T_CONFIG_ESCENARIOS(ID_ESCENARIO INT          NOT NULL,
										ID_PARAM     INT          NOT NULL,
										VALOR        VARCHAR(100) NOT NULL,
										F_CONFIG     DATETIME     NOT NULL);
								



	INSERT INTO IA_APP.T_CONFIG_ESC_PARAM VALUES(1, 'Num_Soluciones');
INSERT INTO IA_APP.T_CONFIG_ESC_PARAM VALUES(2, 'Nombre_Fichero');
INSERT INTO IA_APP.T_CONFIG_ESC_PARAM VALUES(99, 'Vueltas_por_Iteracion');



/*DROP TABLE IA_APP.T_CONFIG_ESCENARIOS;*/
CREATE TABLE IA_APP.T_CONFIG_ESCENARIOS(ID_ESCENARIO INT          NOT NULL,
										ID_PARAM     INT          NOT NULL,
										VALOR        VARCHAR(100) NOT NULL
										F_CONFIG     DATETIME     NOT NULL);













/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/

DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.TABLATRAZAS;
$$
DELIMITER;


DELIMITER $$
CREATE PROCEDURE IEB_PRO.TABLATRAZAS(
    IN  TEXTO                                VARCHAR                            (200))

BEGIN
	DECLARE vID INT DEFAULT 0;
	SELECT MAX(a.ID) 
	  INTO vID
	  FROM TABLA_TRAZAS a;
	
	SET vID = IFNULL(vID, 0) + 1;
	INSERT INTO TABLA_TRAZAS VALUES (vID,
									 TEXTO);
END$$
DELIMITER;



DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_UPDATE_HISTORIC_OPTIONS;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_UPDATE_HISTORIC_OPTIONS(
    IN  F_STR                                VARCHAR                            (14) ,
	IN  vID_ACTIVO                           INT 									,
    IN  P_PRECIO                             NUMERIC                            (15,5),
	OUT P_RES 							 INT)

BEGIN

	DECLARE vMaxHisto NUMERIC(15,5);
	DECLARE vTexto 	  VARCHAR(200);
	DECLARE vID    	  INT DEFAULT 0;
	DECLARE vContador INT DEFAULT 0;
	
	
	
	DECLARE F_H01     DATETIME;
	DECLARE F_D01     DATETIME;
	DECLARE F_M10     DATETIME;
	
	DECLARE vExisteH01 INT DEFAULT 0;
	DECLARE vExisteD01 INT DEFAULT 0;
	DECLARE vExisteM10 INT DEFAULT 0;
	DECLARE vMaxH01     NUMERIC(15,5);
	DECLARE vMinH01     NUMERIC(15,5);
	DECLARE vCierreH01     NUMERIC(15,5);
	
	DECLARE vMaxD01    NUMERIC(15,5);
	DECLARE vMinD01    NUMERIC(15,5);
	DECLARE vCierreD01     NUMERIC(15,5);
	
	DECLARE vMaxM10    NUMERIC(15,5);
	DECLARE vMinM10    NUMERIC(15,5);
	DECLARE vCierreM10     NUMERIC(15,5);

	
	SET P_RES = 0;
	
	SET vExisteD01 = 0;
	SET vExisteH01 = 0;
	SET vExisteM10 = 0;
	
	SET vCierreH01 = 0;
	SET vCierreD01 = 0;
	SET vCierreM10 = 0;
	
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2)), '%Y/%m/%d') INTO F_D01 FROM DUAL;
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2),' ',SUBSTRING(F_STR,9,2),':00:00'), '%Y/%m/%d %T') INTO F_H01 FROM DUAL;
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2),' ',SUBSTRING(F_STR,9,2),':',SUBSTRING(F_STR,11,1),'0:00'), '%Y/%m/%d %T') INTO F_M10 FROM DUAL;
	
	
	
	
	SELECT COUNT(*) INTO vExisteD01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																			 AND a.F_HISTO_SUBY_D01 = F_D01;
	SELECT COUNT(*) INTO vExisteH01 FROM IEB_PRO.T_SUBYACENTES_HISTO_H01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																			 AND a.F_HISTO_SUBY_H01 = F_H01;
	/*
	SELECT COUNT(*) INTO vExisteM10 FROM IEB_PRO.T_SUBYACENTES_HISTO_M10 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																			 AND a.F_HISTO_SUBY_M10 = F_M10;
	*/
	
	
	/*ID_SUBYACENTE                      INT                     			 NOT NULL,
											   F_HISTO_SUBY_H01                    DATE                               NOT NULL      ,
											   UNIQUE INDEX(ID_SUBYACENTE,F_HISTO_SUBY_H01),
											   P_CLOSE_SUBY_H01                    NUMERIC                            (15,5) NULL      ,
											   P_OPEN_SUBY_H01                     NUMERIC                            (15,5) NULL      ,
											   P_HIGH_SUBY_H01                     NUMERIC                            (15,5) NULL      ,
											   P_LOW_SUBY_H01                      NUMERIC                            (15,5) NULL      ,
											   P_HIGH_DAY_SUBY_H01 				  NUMERIC						     (15,5) NULL      ,
											   P_MIN_DAY_SUBY_H01 		*/
	
	/*D01*/
	
	IF (vExisteD01 = 0) THEN
		/*
		SELECT a.P_CLOSE_SUBY_D01 
		  INTO vCierreD01 
		  FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_SUBY_D01 = (SELECT MAX(b.F_HISTO_SUBY_D01) 
									   FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 b 
									  WHERE b.ID_SUBYACENTE = vID_ACTIVO);
									  
		/*IF (P_PRECIO <> vCierreD01) THEN*/
		
			INSERT INTO IEB_PRO.T_SUBYACENTES_HISTO_D01 VALUES (vID_ACTIVO, 
																F_D01, 
																P_PRECIO,
																P_PRECIO,
																P_PRECIO,
																P_PRECIO);
		
		/*END IF;*/
														
	ELSE
	
		SELECT a.P_HIGH_SUBY_D01, a.P_LOW_SUBY_D01 
		  INTO vMaxD01, vMinD01
		  FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_SUBY_D01 = F_D01;
		   
		IF (P_PRECIO > vMaxD01) THEN
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_D01 a
			   SET a.P_CLOSE_SUBY_D01 = P_PRECIO,
			       a.P_HIGH_SUBY_D01  = P_PRECIO
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_D01 = F_D01;
		  
		ELSEIF (P_PRECIO < vMinD01) THEN
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_D01 a
			   SET a.P_CLOSE_SUBY_D01 = P_PRECIO,
			       a.P_LOW_SUBY_D01  = P_PRECIO
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_D01 = F_D01;
			   
		ELSE
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_D01 a
			   SET a.P_CLOSE_SUBY_D01 = P_PRECIO
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_D01 = F_D01;
			
		END IF;
		
	END IF;
	
	/*--H01*/
	
	IF (vExisteH01 = 0) THEN
		/*
		SELECT a.P_CLOSE_SUBY_H01 
		  INTO vCierreH01 
		  FROM IEB_PRO.T_SUBYACENTES_HISTO_H01 a
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_SUBY_H01 = (SELECT MAX(b.F_HISTO_SUBY_H01) 
									   FROM IEB_PRO.T_SUBYACENTES_HISTO_H01 b 
									  WHERE b.ID_SUBYACENTE = vID_ACTIVO);
									  
		/*IF (P_PRECIO <> vCierreH01) THEN
		*/
			INSERT INTO IEB_PRO.T_SUBYACENTES_HISTO_H01 VALUES (vID_ACTIVO, 
																F_H01, 
																P_PRECIO,
																P_PRECIO,
																P_PRECIO,
																P_PRECIO,
																(SELECT a.P_HIGH_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO AND a.F_HISTO_SUBY_D01 = F_D01),
																(SELECT  a.P_LOW_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO AND a.F_HISTO_SUBY_D01 = F_D01));
		
		/*END IF;*/
															
	ELSE
	
		SELECT a.P_HIGH_SUBY_H01, a.P_LOW_SUBY_H01 
		  INTO vMaxH01, vMinH01
		  FROM IEB_PRO.T_SUBYACENTES_HISTO_H01 a
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_SUBY_H01 = F_H01;
		   
		IF (P_PRECIO > vMaxH01) THEN
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_H01 a
			   SET a.P_CLOSE_SUBY_H01 = P_PRECIO,
			       a.P_HIGH_SUBY_H01  = P_PRECIO,
				   a.P_HIGH_DAY_SUBY_H01 = (SELECT b.P_HIGH_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 b WHERE b.ID_SUBYACENTE = vID_ACTIVO AND b.F_HISTO_SUBY_D01 = F_D01)
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_H01 = F_H01;
		  
		ELSEIF (P_PRECIO < vMinH01) THEN
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_H01 a
			   SET a.P_CLOSE_SUBY_H01 = P_PRECIO,
			       a.P_LOW_SUBY_H01  = P_PRECIO,
				   a.P_MIN_DAY_SUBY_H01  = (SELECT b.P_HIGH_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 b WHERE b.ID_SUBYACENTE = vID_ACTIVO AND b.F_HISTO_SUBY_D01 = F_D01)
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_H01 = F_H01;
			   
		ELSE
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_H01 a
			   SET a.P_CLOSE_SUBY_H01 = P_PRECIO
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_H01 = F_H01;

		END IF;
		
	END IF;
	
	/*--M10*/
	/*
	IF (vExisteM10 = 0) THEN
		/*
		SELECT a.P_CLOSE_SUBY_M10 
		  INTO vCierreM10 
		  FROM IEB_PRO.T_SUBYACENTES_HISTO_M10 a
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_SUBY_M10 = (SELECT MAX(b.F_HISTO_SUBY_M10) 
									   FROM IEB_PRO.T_SUBYACENTES_HISTO_M10 b 
									  WHERE b.ID_SUBYACENTE = vID_ACTIVO);
									  
		IF (P_PRECIO <> vCierreM10) THEN
		*/
	/*		INSERT INTO IEB_PRO.T_SUBYACENTES_HISTO_M10 VALUES (vID_ACTIVO, 
																F_M10, 
																P_PRECIO,
																P_PRECIO,
																P_PRECIO,
																P_PRECIO,
																(SELECT a.P_HIGH_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO AND a.F_HISTO_SUBY_D01 = F_D01),
																(SELECT  a.P_LOW_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO AND a.F_HISTO_SUBY_D01 = F_D01));
		
		/*END IF;*/
	/*														
	ELSE
	
		SELECT a.P_HIGH_SUBY_M10, a.P_LOW_SUBY_M10 
		  INTO vMaxM10, vMinM10
		  FROM IEB_PRO.T_SUBYACENTES_HISTO_M10 a
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_SUBY_M10 = F_M10;
		   
		IF (P_PRECIO > vMaxM10) THEN
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_M10 a
			   SET a.P_CLOSE_SUBY_M10 = P_PRECIO,
			       a.P_HIGH_SUBY_M10  = P_PRECIO,
				   a.P_HIGH_DAY_SUBY_M10 = (SELECT b.P_HIGH_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 b WHERE b.ID_SUBYACENTE = vID_ACTIVO AND b.F_HISTO_SUBY_D01 = F_D01)
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_M10 = F_M10;
		  
		ELSEIF (P_PRECIO < vMinM10) THEN
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_M10 a
			   SET a.P_CLOSE_SUBY_M10 = P_PRECIO,
			       a.P_LOW_SUBY_M10  = P_PRECIO,
				   a.P_MIN_DAY_SUBY_M10  = (SELECT b.P_HIGH_SUBY_D01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 b WHERE b.ID_SUBYACENTE = vID_ACTIVO AND b.F_HISTO_SUBY_D01 = F_D01)
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_M10 = F_M10;
			   
		ELSE
		
			UPDATE IEB_PRO.T_SUBYACENTES_HISTO_M10 a
			   SET a.P_CLOSE_SUBY_M10 = P_PRECIO
			 WHERE a.ID_SUBYACENTE = vID_ACTIVO
			   AND a.F_HISTO_SUBY_M10 = F_M10;

		END IF;
		
	END IF;*/
	
	SET P_RES = 1;
	
END$$
DELIMITER;


/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/

DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_UPDATE_VOLAT_HISTO;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_UPDATE_VOLAT_HISTO(IN  vID_ACTIVO     INT,
											   IN  F_STR          VARCHAR(14),
											   IN  F_STR_VTO      VARCHAR(14),
											   IN  vATM			  NUMERIC(15,5),
											   IN  vVOL80		  NUMERIC(7,3),
											   IN  vVOL85		  NUMERIC(7,3),
											   IN  vVOL90		  NUMERIC(7,3),
											   IN  vVOL95		  NUMERIC(7,3),
											   IN  vVOL100		  NUMERIC(7,3),
											   IN  vVOL105		  NUMERIC(7,3),
											   IN  vVOL110		  NUMERIC(7,3),
											   IN  vVOL115		  NUMERIC(7,3),
											   IN  vVOL120		  NUMERIC(7,3),
											   IN  vTASA_R        NUMERIC(7,3),
											   OUT P_RES          INT)

BEGIN

	DECLARE F_H01     DATETIME;
	DECLARE F_D01     DATETIME;
	DECLARE F_M10     DATETIME;
	DECLARE F_VTO     DATETIME;
	
	DECLARE vExisteH01 INT DEFAULT 0;
	DECLARE vExisteD01 INT DEFAULT 0;
	DECLARE vExisteM10 INT DEFAULT 0;
	DECLARE vExisteVTO INT DEFAULT 0;
	
	DECLARE vBeta8090    NUMERIC(15,5) DEFAULT 0;
	DECLARE vBeta90100   NUMERIC(15,5) DEFAULT 0;
	DECLARE vBeta100110  NUMERIC(15,5) DEFAULT 0;
	DECLARE vBeta110120  NUMERIC(15,5) DEFAULT 0;
	
	SET P_RES = 0;
	
	SET vExisteD01 = 0;
	SET vExisteH01 = 0;
	SET vExisteM10 = 0;
	SET vExisteVTO = 0;
	
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2)), '%Y/%m/%d') INTO F_D01 FROM DUAL;
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2),' ',SUBSTRING(F_STR,9,2),':00:00'), '%Y/%m/%d %T') INTO F_H01 FROM DUAL;
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2),' ',SUBSTRING(F_STR,9,2),':',SUBSTRING(F_STR,11,1),'0:00'), '%Y/%m/%d %T') INTO F_M10 FROM DUAL;
	
	/*
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR_VTO,1,2),'/',SUBSTRING(F_STR_VTO,4,2),'/',SUBSTRING(F_STR_VTO,7,4),' ',
			(SELECT CONCAT(SUBSTRING(b.HORA_CIERRE,1,2),':',SUBSTRING(b.HORA_CIERRE,3,2)) 
			   FROM IEB_PRO.T_SUBYACENTES b 
			   WHERE b.ID_SUBYACENTE = vID_ACTIVO)), '%d/%m/%Y %T') INTO F_VTO FROM DUAL;
	*/
	/*Se modifica para que la hora de vencimiento sean las 00:00*/
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR_VTO,1,2),'/',SUBSTRING(F_STR_VTO,4,2),'/',SUBSTRING(F_STR_VTO,7,4),' 00:00'), '%d/%m/%Y %T') INTO F_VTO FROM DUAL;
	
	SET vBeta8090 = (vVOL90 - vVOL80) / 0.1;
	SET vBeta90100 = (vVOL100-vVOL90) / 0.1;
	SET vBeta100110 = (vVOL110 - vVOL100) / 0.1;
	SET vBeta110120 = (vVOL120 - vVOL110) / 0.1;
	
	SELECT COUNT(*)
	  INTO vExisteVTO
	  FROM IEB_PRO.T_VENCIMIENTOS a
	 WHERE a.ID_SUBYACENTE = vID_ACTIVO
	   AND a.VENCIMIENTO = F_VTO;
	
	IF (vExisteVTO = 0) THEN
		INSERT INTO IEB_PRO.T_VENCIMIENTOS (ID_SUBYACENTE,
											VENCIMIENTO)
				VALUES (vID_ACTIVO,
						F_VTO);
		
	END IF;
	
	
	/*
	IEB_PRO.T_VOLATILIDADES_M10 (ID_SUBYACENTE                           INT								 NOT NULL,
										  F_HISTO_VOL_M10                         DATE                              NOT NULL,
										  VENCIMIENTO                             DATE                              NOT NULL,
										  UNIQUE INDEX (ID_SUBYACENTE, F_HISTO_VOL_M10, VENCIMIENTO),
										  ATM                                  NUMERIC                            (15,5) NULL      ,
										  VOL80                                NUMERIC                            (7, 3) NULL      ,
										  VOL90                                NUMERIC                            (7, 3) NULL      ,
										  VOL100                               NUMERIC                            (7, 3) NULL      ,
										  VOL110                               NUMERIC                            (7, 3) NULL      ,
										  VOL120                               NUMERIC                            (7, 3) NULL      ,
										  TASA_R                               NUMERIC                            (7, 3) NULL	   );
	*/
	
	/*
	SELECT COUNT(*) INTO vExisteM10 FROM IEB_PRO.T_VOLATILIDADES_M10 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																		 AND a.F_HISTO_VOL_M10 = F_M10
																		 AND a.VENCIMIENTO = F_VTO;
	*/																 
	SELECT COUNT(*) INTO vExisteH01 FROM IEB_PRO.T_VOLATILIDADES_H01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																		 AND a.F_HISTO_VOL_H01 = F_H01
																		 AND a.VENCIMIENTO = F_VTO;

	SELECT COUNT(*) INTO vExisteD01 FROM IEB_PRO.T_VOLATILIDADES_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																		 AND a.F_HISTO_VOL_D01 = F_D01
																		 AND a.VENCIMIENTO = F_VTO;
	
	/*--D01*/
	
	IF (vExisteD01 = 0) THEN
		
		SELECT COUNT(*) INTO vExisteD01 FROM IEB_PRO.T_SUBYACENTES_HISTO_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																				 AND a.F_HISTO_SUBY_D01 = F_D01;
		
		IF (vExisteD01 = 1) THEN
			INSERT INTO IEB_PRO.T_VOLATILIDADES_D01 VALUES (vID_ACTIVO,
															F_D01,
															F_VTO,
															vATM,
															vVOL80,
															vVOL85,
															vVOL90,
															vVOL95,
															vVOL100,
															vVOL105,
															vVOL110,
															vVOL115,
															vVOL120,
															vTASA_R,
															vBeta8090,
															vBeta90100,
															vBeta100110,
															vBeta110120);
															
		END IF;
		
	ELSE
		
		UPDATE IEB_PRO.T_VOLATILIDADES_D01 a
		   SET a.ATM = vATM,
		       a.VOL80 = vVOL80,
			   a.VOL85 = vVOL85,
			   a.VOL90 = vVOL90,
			   a.VOL95 = vVOL95,
			   a.VOL100 = vVOL100,
			   a.VOL105 = vVOL105,
			   a.VOL110 = vVOL110,
			   a.VOL115 = vVOL115,
			   a.VOL120 = vVOL120,
			   a.BETA8090 = vBeta8090,
			   a.BETA90100 = vBeta90100,
			   a.BETA100110 = vBeta100110,
			   a.BETA110120 = vBeta110120
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_VOL_D01 = F_D01
		   AND a.VENCIMIENTO = F_VTO;
	  
	END IF;
	
	/*--H01*/
	
	IF (vExisteH01 = 0) THEN
	
		SELECT COUNT(*) INTO vExisteH01 FROM IEB_PRO.T_SUBYACENTES_HISTO_H01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																				 AND a.F_HISTO_SUBY_H01 = F_H01;
		
		IF (vExisteH01 = 1) THEN
			INSERT INTO IEB_PRO.T_VOLATILIDADES_H01 VALUES (vID_ACTIVO,
															F_H01,
															F_VTO,
															vATM,
															vVOL80,
															vVOL85,
															vVOL90,
															vVOL95,
															vVOL100,
															vVOL105,
															vVOL110,
															vVOL115,
															vVOL120,
															vTASA_R,
															vBeta8090,
															vBeta90100,
															vBeta100110,
															vBeta110120);
															
		END IF;
		
	ELSE
		
		UPDATE IEB_PRO.T_VOLATILIDADES_H01 a
		   SET a.ATM = vATM,
		       a.VOL80 = vVOL80,
			   a.VOL85 = vVOL85,
			   a.VOL90 = vVOL90,
			   a.VOL95 = vVOL95,
			   a.VOL100 = vVOL100,
			   a.VOL105 = vVOL105,
			   a.VOL110 = vVOL110,
			   a.VOL115 = vVOL115,
			   a.VOL120 = vVOL120,
			   a.BETA8090 = vBeta8090,
			   a.BETA90100 = vBeta90100,
			   a.BETA100110 = vBeta100110,
			   a.BETA110120 = vBeta110120
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_VOL_H01 = F_H01
		   AND a.VENCIMIENTO = F_VTO;
	  
	END IF;
	
	/*--M10*/
	/*
	IF (vExisteM10 = 0) THEN
	
		SELECT COUNT(*) INTO vExisteM10 FROM IEB_PRO.T_SUBYACENTES_HISTO_M10 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																				 AND a.F_HISTO_SUBY_M10 = F_M10;
		
		IF (vExisteM10 = 1) THEN
			INSERT INTO IEB_PRO.T_VOLATILIDADES_M10 VALUES (vID_ACTIVO,
															F_M10,
															F_VTO,
															vATM,
															vVOL80,
															vVOL85,
															vVOL90,
															vVOL95,
															vVOL100,
															vVOL105,
															vVOL110,
															vVOL115,
															vVOL120,
															vTASA_R,
															vBeta8090,
															vBeta90100,
															vBeta100110,
															vBeta110120);
															
		END IF;
		
	ELSE
		
		UPDATE IEB_PRO.T_VOLATILIDADES_M10 a
		   SET a.ATM = vATM,
		       a.VOL80 = vVOL80,
			   a.VOL85 = vVOL85,
			   a.VOL90 = vVOL90,
			   a.VOL95 = vVOL95,
			   a.VOL100 = vVOL100,
			   a.VOL105 = vVOL105,
			   a.VOL110 = vVOL110,
			   a.VOL115 = vVOL115,
			   a.VOL120 = vVOL120,
			   a.BETA8090 = vBeta8090,
			   a.BETA90100 = vBeta90100,
			   a.BETA100110 = vBeta100110,
			   a.BETA110120 = vBeta110120
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_VOL_M10 = F_M10
		   AND a.VENCIMIENTO = F_VTO;
	  
	END IF;
	*/
	
	SET P_RES = 1;
	
END$$
DELIMITER;

/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/

DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_INSERTA_BOLETA_OPCION;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_INSERTA_BOLETA_OPCION(  IN  vF_ORDEN        VARCHAR(16),
													IN  vNOMBRE        VARCHAR(100),
													IN  vTitulos       INT,
													IN  vPrecio		   NUMERIC(15,5),
													IN  vTipoCambio    NUMERIC(15,5),
													IN  vSubyacente    NUMERIC(15,5),
													IN  vCall_Put      VARCHAR(4),
													IN  vStrike		   NUMERIC(15,5),
													IN  vVencimiento   VARCHAR(10),
													IN  vVolatilidad   NUMERIC(7,3),
													IN  vTASA_R        NUMERIC(7,3),
													IN  vCompraVenta   VARCHAR(1),
													OUT P_RES          INT)

BEGIN

	DECLARE vID_ACTIVO INT DEFAULT 0;
	DECLARE vID_BENEFICIO INT DEFAULT 0;

	DECLARE F_ORDEN     DATETIME;
	DECLARE F_VTO       DATETIME;
	DECLARE vF_APERTURA DATETIME;
	
	DECLARE vExisteOpcion INT DEFAULT 0;
	DECLARE vIDNext       INT DEFAULT 0;
	DECLARE vIDMovimiento INT DEFAULT 0;
	DECLARE vTitulosNetos INT DEFAULT 0;
	
	DECLARE vID_Opcion    INT DEFAULT 0;
	DECLARE vExisteMov    INT DEFAULT 0;
	DECLARE vIDPosicion   INT DEFAULT 0;
	
	DECLARE vTitulosCartera INT DEFAULT 0;
	DECLARE vPrecioOrden   NUMERIC(15,5);
	DECLARE vCambioApertura NUMERIC(15,5);
	DECLARE vTipoPosicion  VARCHAR(1);
	
	DECLARE vTitulosRestan INT DEFAULT 0;
	DECLARE vTitulosQuitar INT DEFAULT 0;
	
	DECLARE vBeneficio NUMERIC(15,5);
	DECLARE vBeneficioEuros NUMERIC(15,5);
	DECLARE vMultiplicador NUMERIC(15,5);
	
	DECLARE vTEXTO VARCHAR(200);
	
	SET P_RES = 0;
	
	SELECT a.ID_SUBYACENTE 
	  INTO vID_ACTIVO
	  FROM T_SUBYACENTES a
	 WHERE a.NOMBRE = vNOMBRE
	   AND a.TIPO_SUBYACENTE = 'OPTION';
	 
	
	SELECT STR_TO_DATE(vF_ORDEN, '%d-%m-%Y %T') INTO F_ORDEN FROM DUAL;
	
	SELECT STR_TO_DATE(CONCAT(vVencimiento,' ',
			(SELECT CONCAT(SUBSTRING(b.HORA_CIERRE,1,2),':',SUBSTRING(b.HORA_CIERRE,3,2)) 
			   FROM IEB_PRO.T_SUBYACENTES b 
			   WHERE b.ID_SUBYACENTE = vID_ACTIVO)), '%d-%m-%Y %T') INTO F_VTO FROM DUAL;
	
	SELECT COUNT(*) 
	  INTO vExisteOpcion
	  FROM IEB_PRO.T_OPCIONES_CARTERA a
	 WHERE a.NOMBRE = vNOMBRE
	   AND a.TIPO = vCall_Put
	   AND a.STRIKE = vStrike
	   AND a.VENCIMIENTO = F_VTO;
	   
	CALL TABLATRAZAS(CONCAT('vID_Opcion = ',FORMAT(vID_Opcion,0)));
	
	IF (vExisteOpcion = 0) THEN
	
		
		
		SET vIDNext = 0;
		SELECT MAX(a.ID_OPCION)
		  INTO vIDNext
		  FROM IEB_PRO.T_OPCIONES_CARTERA a;
		
		SET vIDNext = IFNULL(vIDNext, 0) + 1;
		
		CALL TABLATRAZAS(CONCAT('La opcion no existe en T_OPCIONES_CARTERA. Se inserta con ID: ',FORMAT(vIDNext,0)));
		
		INSERT INTO IEB_PRO.T_OPCIONES_CARTERA VALUES (vIDNext,
											   vID_ACTIVO,
											   vNOMBRE,
											   vCall_Put,
											   vStrike,
											   F_VTO);
	
	END IF;
	
	SELECT a.ID_OPCION
	  INTO vID_Opcion
	  FROM T_OPCIONES_CARTERA a
	 WHERE a.NOMBRE = vNOMBRE
	   AND a.TIPO = vCall_Put
	   AND a.STRIKE = vStrike
	   AND a.VENCIMIENTO = F_VTO;
	
	SET vID_Opcion = IFNULL(vID_Opcion, 0);
	
	SELECT COUNT(*)
      INTO vExisteMov	
	  FROM T_ORDENES_OPCIONES a
	 WHERE a.ID_OPCION = vID_Opcion
	   AND a.FEC_ORDEN = F_ORDEN
	   AND a.TITULOS = vTitulos
	   AND a.PRECIO_ORDEN = vPrecio
	   AND a.TIPO_CAMBIO = vTipoCambio
	   AND a.VOLATILIDAD = vVolatilidad
	   AND a.TASA_R = vTASA_R;
	
	SET vExisteMov = IFNULL(vExisteMov, 0);
	
	CALL TABLATRAZAS(CONCAT('vID_Opcion = ',FORMAT(vID_Opcion,0)));
	CALL TABLATRAZAS(CONCAT('vExisteMov = ',FORMAT(vExisteMov,0)));
	
	IF (vID_Opcion > 0 AND vExisteMov = 0) THEN
		
		SET vIDMovimiento = 0;
		
		SELECT MAX(a.ID_MOVIMIENTO)
		  INTO vIDMovimiento
		  FROM T_ORDENES_OPCIONES a;
		
		SET vIDMovimiento = IFNULL(vIDMovimiento, 0) + 1;
		
		SELECT SUM(a.TITULOS)
		  INTO vTitulosNetos
		  FROM IEB_PRO.T_ORDENES_OPCIONES a
		 WHERE a.ID_OPCION = vID_Opcion;
		
		SET vTitulosNetos = IFNULL(vTitulosNetos, 0) + vTitulos;
		CALL TABLATRAZAS(CONCAT('Se registra movimiento en T_ORDENES_OPCIONES con ID: ',FORMAT(vIDMovimiento,0)));
		INSERT INTO T_ORDENES_OPCIONES VALUES(vIDMovimiento,
											  vID_Opcion,
											  F_ORDEN,
											  vTitulos,
											  vPrecio,
											  vTipoCambio,
											  vSubyacente,
											  vVolatilidad,
											  vTASA_R,
											  vTitulosNetos);
		
		
		SET vExisteMov = 0;
		
		SELECT COUNT(*)
		  INTO vExisteMov
		  FROM IEB_PRO.T_STOCK_OPCIONES a
		 WHERE a.ID_OPCION = vID_Opcion
		   AND a.COMPRA_VENTA <> vCompraVenta;
		
		SET vExisteMov = IFNULL(vExisteMov,0);
		
		IF (vExisteMov = 0) THEN
			
			CALL TABLATRAZAS(CONCAT('No existe contrapartida para el ID ',FORMAT(vID_Opcion,0),' ',vCompraVenta));
			
			SET vIDPosicion = 0;
			
			SELECT MAX(a.ID_POSICION) 
			  INTO vIDPosicion
			  FROM IEB_PRO.T_STOCK_OPCIONES a;
			 
			SET vIDPosicion = IFNULL(vIDPosicion, 0) + 1;
			
			INSERT INTO IEB_PRO.T_STOCK_OPCIONES VALUES(vIDPosicion,
														vID_Opcion,
														vTitulos,
														vPrecio,
														vTipoCambio,
														F_ORDEN,
														vCompraVenta);
		ELSE
			CALL TABLATRAZAS(CONCAT('EXISTE contrapartida para el ID ',FORMAT(vID_Opcion,0),' ',vCompraVenta,'. Se entrará en el bucle.'));
			SET vTitulosRestan = vTitulos;
			
			WHILE (vTitulosRestan <> 0 AND vExisteMov > 0) DO
			
				CALL TABLATRAZAS(CONCAT('Inicio bucle. Restan: ',FORMAT(vTitulosRestan,0),' titulos'));
				SET vExisteMov = 0;
				
				SELECT a.ID_POSICION, 
					   a.TITULOS,
					   a.PRECIO_ORDEN,
					   a.TIPO_CAMBIO,
					   a.FECHA_POSICION,
					   a.COMPRA_VENTA
				  INTO vExisteMov,
					   vTitulosCartera,
					   vPrecioOrden,
					   vCambioApertura,
					   vF_APERTURA,
					   vTipoPosicion
				  FROM IEB_PRO.T_STOCK_OPCIONES a
				 WHERE a.ID_OPCION = vID_Opcion
				   AND a.COMPRA_VENTA <> vCompraVenta
				   AND a.FECHA_POSICION = (SELECT MIN(b.FECHA_POSICION) 
											 FROM IEB_PRO.T_STOCK_OPCIONES b
											WHERE b.ID_OPCION = vID_Opcion);
				
				SET vExisteMov = IFNULL (vExisteMov, 0);
				
				IF (vExisteMov = 0) THEN
					CALL TABLATRAZAS(CONCAT('Ya NO existe el movimiento. Restan: ',FORMAT(vTitulosRestan,0),' titulos'));
					SET vIDPosicion = 0;
					
					SELECT MAX(a.ID_POSICION) 
					 INTO vIDPosicion
					 FROM IEB_PRO.T_STOCK_OPCIONES a;
					 
					SET vIDPosicion = IFNULL(vIDPosicion, 0) + 1;
					
					INSERT INTO IEB_PRO.T_STOCK_OPCIONES VALUES (vIDPosicion,
																 vID_Opcion,
																 vTitulosRestan,
																 vPrecio,
																 vTipoCambio,
																 F_ORDEN,
																 vCompraVenta);
				ELSE
					
					SET vID_BENEFICIO = 0;
					
					SELECT MAX(a.ID_BENEFICIO)
					  INTO vID_BENEFICIO
					  FROM T_BENEFICIO_OPCIONES a;
					
					SET vID_BENEFICIO = IFNULL(vID_BENEFICIO, 0) + 1;
					
					IF (vTitulosRestan > 0) THEN
					
						IF (ABS(vTitulosCartera) >= vTitulosRestan) THEN
							SET vTitulosQuitar = vTitulosRestan;
							SET vTitulosRestan = 0;
						ELSE
							SET vTitulosQuitar = ABS(vTitulosCartera);
							SET vTitulosRestan = vTitulosRestan - vTitulosQuitar;
						END IF;
						
						UPDATE IEB_PRO.T_STOCK_OPCIONES a
						   SET a.TITULOS = a.TITULOS + vTitulosQuitar
						 WHERE a.ID_POSICION = vExisteMov;
						 
						SELECT a.MULTIPLICADOR
						  INTO vMultiplicador
						  FROM IEB_PRO.T_SUBYACENTES a
						 WHERE a.ID_SUBYACENTE = vID_ACTIVO;
						
						SET vBeneficio = (vPrecioOrden-vPrecio) * vTitulosQuitar * vMultiplicador;
						SET vBeneficioEuros = (vPrecioOrden / vCambioApertura - vPrecio / vTipoCambio) * vTitulosQuitar * vMultiplicador;
						 
						INSERT INTO IEB_PRO.T_BENEFICIO_OPCIONES VALUES(vID_BENEFICIO,
																		vID_Opcion,
																		vF_APERTURA,
																		F_ORDEN,
																		vPrecio,
																		vTipoCambio,
																		vPrecioOrden,
																		vCambioApertura,
																		vTitulosQuitar,
																		vTipoPosicion,
																		vBeneficio,
																		vBeneficioEuros);
						
						
						
						CALL TABLATRAZAS(CONCAT('Se dan de baja: ',FORMAT(vTitulosQuitar,0),' titulos'));  
					ELSEIF (vTitulosRestan < 0) THEN
						
						IF (vTitulosCartera >= ABS(vTitulosRestan)) THEN
							SET vTitulosQuitar = ABS(vTitulosRestan);
							SET vTitulosRestan = 0;
						ELSE
							SET vTitulosQuitar = vTitulosCartera;
							SET vTitulosRestan = vTitulosRestan + vTitulosQuitar;
						END IF;
						
						UPDATE IEB_PRO.T_STOCK_OPCIONES a
						   SET a.TITULOS = a.TITULOS - vTitulosQuitar
						 WHERE a.ID_POSICION = vExisteMov;
						
						
						SELECT a.MULTIPLICADOR
						  INTO vMultiplicador
						  FROM IEB_PRO.T_SUBYACENTES a
						 WHERE a.ID_SUBYACENTE = vID_ACTIVO;
						
						SET vBeneficio = (vPrecio-vPrecioOrden) * vTitulosQuitar * vMultiplicador;
						SET vBeneficioEuros = (vPrecio / vTipoCambio - vPrecioOrden / vCambioApertura) * vTitulosQuitar * vMultiplicador;
						
						INSERT INTO IEB_PRO.T_BENEFICIO_OPCIONES VALUES(vID_BENEFICIO,
																		vID_Opcion,
																		vF_APERTURA,
																		F_ORDEN,
																		vPrecioOrden,
																		vCambioApertura,
																		vPrecio,
																		vTipoCambio,
																		vTitulosQuitar,
																		vTipoPosicion,
																		vBeneficio,
																		vBeneficioEuros);
																		
						CALL TABLATRAZAS(CONCAT('Se dan de baja: ',FORMAT(vTitulosQuitar,0),' titulos'));  
						
					END IF;
					
				END IF;
				
				DELETE FROM IEB_PRO.T_STOCK_OPCIONES
					 WHERE TITULOS = 0;
				

				CALL TABLATRAZAS(CONCAT('Fin bucle. Restan: ',FORMAT(vTitulosRestan,0),' titulos'));
				
			END WHILE;
			
			
		END IF;
		
		
									
		
		
	END IF;
	
	SET P_RES = 1;
END$$
DELIMITER;

/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/
