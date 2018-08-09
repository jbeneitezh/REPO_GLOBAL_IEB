
/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/


DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_UPDATE_HISTORIC_FUTURES;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_UPDATE_HISTORIC_FUTURES(IN  F_STR                                VARCHAR                            (14) ,
													IN  vID_ACTIVO                           INT 									,
													IN  P_PRECIO                             NUMERIC                            (15,5),
													OUT P_RES 							 INT)

BEGIN

	DECLARE vContador INT DEFAULT 0;
	
	
	
	DECLARE F_H01     DATETIME;
	DECLARE F_D01     DATETIME;
	DECLARE F_M10     DATETIME;
	
	DECLARE vExisteH01 INT DEFAULT 0;
	DECLARE vExisteD01 INT DEFAULT 0;
	DECLARE vExisteM10 INT DEFAULT 0;

	SET P_RES = 0;
	
	SET vExisteD01 = 0;
	SET vExisteH01 = 0;
	SET vExisteM10 = 0;
	
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2)), '%Y/%m/%d') INTO F_D01 FROM DUAL;
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2),' ',SUBSTRING(F_STR,9,2),':00:00'), '%Y/%m/%d %T') INTO F_H01 FROM DUAL;
	SELECT STR_TO_DATE(CONCAT(SUBSTRING(F_STR,1,4),'/',SUBSTRING(F_STR,5,2),'/',SUBSTRING(F_STR,7,2),' ',SUBSTRING(F_STR,9,2),':',SUBSTRING(F_STR,11,1),'0:00'), '%Y/%m/%d %T') INTO F_M10 FROM DUAL;
	

	
	/*D01*/
	
	SELECT COUNT(*) INTO vExisteD01 FROM IEB_PRO.T_FUTUROS_D01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																   AND a.F_HISTO_FUT_D01 = F_D01;
																   
	IF (vExisteD01 = 0) THEN
		
		INSERT INTO IEB_PRO.T_FUTUROS_D01 VALUES (vID_ACTIVO, 
												  F_D01, 
												  P_PRECIO);
														
	ELSE
	
		UPDATE IEB_PRO.T_FUTUROS_D01 a
		   SET a.P_HISTO_FUT_D01 = P_PRECIO
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_FUT_D01 = F_D01;

	END IF;
	
	/*H01*/
	
	SELECT COUNT(*) INTO vExisteH01 FROM IEB_PRO.T_FUTUROS_H01 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																   AND a.F_HISTO_FUT_H01 = F_H01;
																   
	IF (vExisteH01 = 0) THEN
		
		INSERT INTO IEB_PRO.T_FUTUROS_H01 VALUES (vID_ACTIVO, 
												  F_H01, 
												  P_PRECIO);
														
	ELSE
	
		UPDATE IEB_PRO.T_FUTUROS_H01 a
		   SET a.P_HISTO_FUT_H01 = P_PRECIO
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_FUT_H01 = F_H01;

	END IF;
	
	/*M10*/
	
	SELECT COUNT(*) INTO vExisteM10 FROM IEB_PRO.T_FUTUROS_M10 a WHERE a.ID_SUBYACENTE = vID_ACTIVO
																   AND a.F_HISTO_FUT_M10 = F_M10;
																   
	IF (vExisteM10 = 0) THEN
		
		INSERT INTO IEB_PRO.T_FUTUROS_M10 VALUES (vID_ACTIVO, 
												  F_M10, 
												  P_PRECIO);
														
	ELSE
	
		UPDATE IEB_PRO.T_FUTUROS_M10 a
		   SET a.P_HISTO_FUT_M10 = P_PRECIO
		 WHERE a.ID_SUBYACENTE = vID_ACTIVO
		   AND a.F_HISTO_FUT_M10 = F_M10;

	END IF;
	
	
	SET P_RES = 1;
	
END$$
DELIMITER;



/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/

DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_INSERTA_BOLETA_FUTURO;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_INSERTA_BOLETA_FUTURO(  IN  vF_ORDEN        VARCHAR(16),
													IN  vNOMBRE        VARCHAR(100),
													IN  vTitulos       INT,
													IN  vCompraVenta   VARCHAR(1),
													IN  vPrecio		   NUMERIC(15,5),
													IN  vTipoCambio    NUMERIC(15,5),
													IN  vVencimiento   VARCHAR(10),													
													OUT P_RES          INT)

BEGIN

	DECLARE vID_ACTIVO INT DEFAULT 0;
	DECLARE vID_BENEFICIO INT DEFAULT 0;

	DECLARE F_ORDEN     DATETIME;
	DECLARE F_VTO       DATETIME;
	DECLARE vF_APERTURA DATETIME;
	
	DECLARE vExisteFuturo INT DEFAULT 0;
	DECLARE vIDNext       INT DEFAULT 0;
	DECLARE vIDMovimiento INT DEFAULT 0;
	DECLARE vTitulosNetos INT DEFAULT 0;
	
	DECLARE vID_Futuro    INT DEFAULT 0;
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
	
	DECLARE vTEXTO VARCHAR(200);
	
	SET P_RES = 0;
	
	SELECT a.ID_SUBYACENTE 
	  INTO vID_ACTIVO
	  FROM T_SUBYACENTES a
	 WHERE a.NOMBRE = vNOMBRE
	   AND a.TIPO_SUBYACENTE = 'FUTURE';
	 
	
	SELECT STR_TO_DATE(vF_ORDEN, '%d-%m-%Y %T') INTO F_ORDEN FROM DUAL;
	
	SELECT STR_TO_DATE(vVencimiento, '%d-%m-%Y %T') INTO F_VTO FROM DUAL;
	
	SELECT COUNT(*) 
	  INTO vExisteFuturo
	  FROM IEB_PRO.T_FUTUROS_CARTERA a
	 WHERE a.ID_SUBYACENTE = vID_ACTIVO
	   AND a.VENCIMIENTO = F_VTO;
	   
	CALL TABLATRAZAS(CONCAT('vID_Futuro = ',FORMAT(vID_Futuro,0)));
	
	IF (vExisteFuturo = 0) THEN
	
		
		
		SET vIDNext = 0;
		SELECT MAX(a.ID_FUTURO)
		  INTO vIDNext
		  FROM IEB_PRO.T_FUTUROS_CARTERA a;
		
		SET vIDNext = IFNULL(vIDNext, 0) + 1;
		
		CALL TABLATRAZAS(CONCAT('La opcion no existe en T_FUTUROS_CARTERA. Se inserta con ID: ',FORMAT(vIDNext,0)));
		
		INSERT INTO IEB_PRO.T_FUTUROS_CARTERA VALUES ( vIDNext,
													   vID_ACTIVO,
													   vNOMBRE,
													   F_VTO);
	
	END IF;
	
	SELECT a.ID_FUTURO
	  INTO vID_Futuro
	  FROM T_FUTUROS_CARTERA a
	 WHERE a.ID_SUBYACENTE = vID_ACTIVO
	   AND a.VENCIMIENTO = F_VTO;
	
	SET vID_Futuro = IFNULL(vID_Futuro, 0);
	
	SELECT COUNT(*)
      INTO vExisteMov	
	  FROM T_ORDENES_FUTUROS a
	 WHERE a.ID_FUTURO = vID_Futuro
	   AND a.FEC_ORDEN = F_ORDEN
	   AND a.TITULOS = vTitulos
	   AND a.PRECIO_ORDEN = vPrecio;
	
	SET vExisteMov = IFNULL(vExisteMov, 0);
	
	CALL TABLATRAZAS(CONCAT('vID_Futuro = ',FORMAT(vID_Futuro,0)));
	CALL TABLATRAZAS(CONCAT('vExisteMov = ',FORMAT(vExisteMov,0)));
	
	IF (vID_Futuro > 0 AND vExisteMov = 0) THEN
		
		SET vIDMovimiento = 0;
		
		SELECT MAX(a.ID_MOVIMIENTO)
		  INTO vIDMovimiento
		  FROM T_ORDENES_FUTUROS a;
		
		SET vIDMovimiento = IFNULL(vIDMovimiento, 0) + 1;
		
		SELECT SUM(a.TITULOS)
		  INTO vTitulosNetos
		  FROM IEB_PRO.T_ORDENES_FUTUROS a
		 WHERE a.ID_FUTURO = vID_Futuro;
		
		SET vTitulosNetos = IFNULL(vTitulosNetos, 0) + vTitulos;
		CALL TABLATRAZAS(CONCAT('Se registra movimiento en T_ORDENES_FUTUROS con ID: ',FORMAT(vIDMovimiento,0)));
		INSERT INTO T_ORDENES_FUTUROS VALUES(vIDMovimiento,
											 vID_Futuro,
											 F_ORDEN,
											 vTitulos,
											 vPrecio,
											 vTipoCambio,
											 vTitulosNetos);
		
		SET vExisteMov = 0;
		
		SELECT COUNT(*)
		  INTO vExisteMov
		  FROM IEB_PRO.T_STOCK_FUTUROS a
		 WHERE a.ID_FUTURO = vID_Futuro
		   AND a.COMPRA_VENTA <> vCompraVenta;
		
		SET vExisteMov = IFNULL(vExisteMov,0);
		
		IF (vExisteMov = 0) THEN
			
			CALL TABLATRAZAS(CONCAT('No existe contrapartida para el ID ',FORMAT(vID_Futuro,0),' ',vCompraVenta));
			
			SET vIDPosicion = 0;
			
			SELECT MAX(a.ID_POSICION) 
			  INTO vIDPosicion
			  FROM IEB_PRO.T_STOCK_FUTUROS a;
			 
			SET vIDPosicion = IFNULL(vIDPosicion, 0) + 1;
			
			INSERT INTO IEB_PRO.T_STOCK_FUTUROS VALUES(vIDPosicion,
														vID_Futuro,
														vTitulos,
														vPrecio,
														vTipoCambio,
														F_ORDEN,
														vCompraVenta);
		ELSE
			CALL TABLATRAZAS(CONCAT('EXISTE contrapartida para el ID ',FORMAT(vID_Futuro,0),' ',vCompraVenta,'. Se entrará en el bucle.'));
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
				  FROM IEB_PRO.T_STOCK_FUTUROS a
				 WHERE a.ID_FUTURO = vID_Futuro
				   AND a.COMPRA_VENTA <> vCompraVenta
				   AND a.FECHA_POSICION = (SELECT MIN(b.FECHA_POSICION) 
											 FROM IEB_PRO.T_STOCK_FUTUROS b
											WHERE b.ID_FUTURO = vID_Futuro);
				
				SET vExisteMov = IFNULL (vExisteMov, 0);
				
				IF (vExisteMov = 0) THEN
					CALL TABLATRAZAS(CONCAT('Ya NO existe el movimiento. Restan: ',FORMAT(vTitulosRestan,0),' titulos'));
					SET vIDPosicion = 0;
					
					SELECT MAX(a.ID_POSICION) 
					 INTO vIDPosicion
					 FROM IEB_PRO.T_STOCK_FUTUROS a;
					 
					SET vIDPosicion = IFNULL(vIDPosicion, 0) + 1;
					
					INSERT INTO IEB_PRO.T_STOCK_FUTUROS  VALUES (vIDPosicion,
																 vID_Futuro,
																 vTitulosRestan,
																 vPrecio,
																 vTipoCambio,
																 F_ORDEN,
																 vCompraVenta);
				ELSE
					
					SET vID_BENEFICIO = 0;
					
					SELECT MAX(a.ID_BENEFICIO)
					  INTO vID_BENEFICIO
					  FROM T_BENEFICIO_FUTUROS a;
					
					SET vID_BENEFICIO = IFNULL(vID_BENEFICIO, 0) + 1;
					
					IF (vTitulosRestan > 0) THEN
					
						IF (ABS(vTitulosCartera) >= vTitulosRestan) THEN
							SET vTitulosQuitar = vTitulosRestan;
							SET vTitulosRestan = 0;
						ELSE
							SET vTitulosQuitar = ABS(vTitulosCartera);
							SET vTitulosRestan = vTitulosRestan - vTitulosQuitar;
						END IF;
						
						UPDATE IEB_PRO.T_STOCK_FUTUROS a
						   SET a.TITULOS = a.TITULOS + vTitulosQuitar
						 WHERE a.ID_POSICION = vExisteMov;
						 
						INSERT INTO IEB_PRO.T_BENEFICIO_FUTUROS  VALUES(vID_BENEFICIO,
																		vID_Futuro,
																		vF_APERTURA,
																		F_ORDEN,
																		vPrecio,
																		vTipoCambio,
																		vPrecioOrden,
																		vCambioApertura,
																		vTitulosQuitar,
																		vTipoPosicion);
																		
						CALL TABLATRAZAS(CONCAT('Se dan de baja: ',FORMAT(vTitulosQuitar,0),' titulos'));  
					ELSEIF (vTitulosRestan < 0) THEN
						
						IF (vTitulosCartera >= ABS(vTitulosRestan)) THEN
							SET vTitulosQuitar = ABS(vTitulosRestan);
							SET vTitulosRestan = 0;
						ELSE
							SET vTitulosQuitar = vTitulosCartera;
							SET vTitulosRestan = vTitulosRestan + vTitulosQuitar;
						END IF;
						
						UPDATE IEB_PRO.T_STOCK_FUTUROS a
						   SET a.TITULOS = a.TITULOS - vTitulosQuitar
						 WHERE a.ID_POSICION = vExisteMov;
						
						INSERT INTO IEB_PRO.T_BENEFICIO_FUTUROS  VALUES(vID_BENEFICIO,
																		vID_Futuro,
																		vF_APERTURA,
																		F_ORDEN,
																		vPrecioOrden,
																		vCambioApertura,
																		vPrecio,
																		vTipoCambio,
																		vTitulosQuitar,
																		vTipoPosicion);
						CALL TABLATRAZAS(CONCAT('Se dan de baja: ',FORMAT(vTitulosQuitar,0),' titulos'));  
						
					END IF;
					
				END IF;
				
				DELETE FROM IEB_PRO.T_STOCK_FUTUROS
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

DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_HISTORICAL_ATM_VOL;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_HISTORICAL_ATM_VOL(IN pNombre       VARCHAR(100),
											   IN pFec          VARCHAR(10) ,
											   IN pVola1        NUMERIC(7,3),
											   IN pVola2        NUMERIC(7,3),
											   IN pVola3        NUMERIC(7,3),
											   IN pVola4        NUMERIC(7,3),
											   IN pVola5        NUMERIC(7,3),
											   IN pVola6        NUMERIC(7,3),
											   IN pVto1         VARCHAR(10) ,
											   IN pVto2         VARCHAR(10) ,
											   IN pVto3         VARCHAR(10) ,
											   IN pVto4         VARCHAR(10) ,
											   IN pVto5         VARCHAR(10) ,
											   IN pVto6         VARCHAR(10) ,
											   OUT P_RES 		INT,
											   OUT P_TXT_OUT    VARCHAR(200))

BEGIN

	DECLARE vContador INT DEFAULT 0;
	
	DECLARE vFECHA  DATE;
	DECLARE vF_1    DATE;
	DECLARE vF_2    DATE;
	DECLARE vF_3    DATE;
	DECLARE vF_4    DATE;
	DECLARE vF_5    DATE;
	DECLARE vF_6    DATE;

	DECLARE vExisteRegistro INT DEFAULT 0;
	DECLARE vID_Subyacente  INT DEFAULT 0;

	SET P_RES = 0;
	
	SELECT STR_TO_DATE(pFec,  '%d-%m-%Y') INTO vFECHA FROM DUAL;
	SELECT STR_TO_DATE(pVto1, '%d-%m-%Y') INTO vF_1 FROM DUAL;
	SELECT STR_TO_DATE(pVto2, '%d-%m-%Y') INTO vF_2 FROM DUAL;
	SELECT STR_TO_DATE(pVto3, '%d-%m-%Y') INTO vF_3 FROM DUAL;
	SELECT STR_TO_DATE(pVto4, '%d-%m-%Y') INTO vF_4 FROM DUAL;
	SELECT STR_TO_DATE(pVto5, '%d-%m-%Y') INTO vF_5 FROM DUAL;
	SELECT STR_TO_DATE(pVto6, '%d-%m-%Y') INTO vF_6 FROM DUAL;
	
	SELECT a.ID_SUBYACENTE 
	  INTO vID_Subyacente 
	  FROM IEB_PRO.T_SUBYACENTES a
	 WHERE a.NOMBRE = pNombre
	   AND a.TIPO_SUBYACENTE = 'OPTION';
	   
	SET vID_Subyacente = IFNULL(vID_Subyacente, 0);
	
	SELECT COUNT(*) 
	  INTO vExisteRegistro 
	  FROM IEB_PRO.T_HISTORICOS_VOLATILIDADES a 
	 WHERE a.ID_SUBYACENTE = vID_Subyacente 
	   AND a.FECHA = vFECHA;
	
	
	IF (vExisteRegistro > 0) THEN
		SET P_RES = -1;
		SET P_TXT_OUT = CONCAT('La fecha ', pFec,' para el subyacente ', pNombre, ' ya se encontraba.');
	ELSEIF (vID_Subyacente = 0) THEN
		SET P_RES = -1;
		SET P_TXT_OUT = 'Subyacente no encontrado - '||pNombre;
	ELSE
		INSERT INTO IEB_PRO.T_HISTORICOS_VOLATILIDADES VALUES(vID_Subyacente,
															  vFECHA,
															  pVola1,
															  pVola2,
															  pVola3,
															  pVola4,
															  pVola5,
															  pVola6,
															  vF_1,
															  vF_2,
															  vF_3,
															  vF_4,
															  vF_5,
															  vF_6);
															  
		SET P_TXT_OUT = CONCAT('Insertada ',pFec,' - ', pNombre,'.');
		SET P_RES = 1;
	END IF;
	
END$$
DELIMITER;



/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/

DELIMITER $$
DROP PROCEDURE IF EXISTS IEB_PRO.PR_HISTORICAL_SUBYACENTE;
$$
DELIMITER;


DELIMITER $$


CREATE PROCEDURE IEB_PRO.PR_HISTORICAL_SUBYACENTE(IN pNombre       VARCHAR(100),
											      IN pFec          VARCHAR(10) ,
											      IN pPrecio       NUMERIC(15,5),
												  OUT P_RES        INT,
											      OUT P_TXT_OUT    VARCHAR(200))

BEGIN

	DECLARE vContador INT DEFAULT 0;
	
	DECLARE vFECHA  DATE;

	DECLARE vExisteRegistro INT DEFAULT 0;
	DECLARE vID_Subyacente  INT DEFAULT 0;

	SET P_RES = 0;
	
	SELECT STR_TO_DATE(pFec,  '%d-%m-%Y') INTO vFECHA FROM DUAL;
	
	SELECT a.ID_SUBYACENTE 
	  INTO vID_Subyacente 
	  FROM IEB_PRO.T_SUBYACENTES a
	 WHERE a.NOMBRE = pNombre
	   AND a.TIPO_SUBYACENTE = 'FUTURE';
	   
	SET vID_Subyacente = IFNULL(vID_Subyacente, 0);
	
	SELECT COUNT(*) 
	  INTO vExisteRegistro 
	  FROM IEB_PRO.T_HISTORICOS_SUBYACENTES a 
	 WHERE a.ID_SUBYACENTE = vID_Subyacente 
	   AND a.FECHA = vFECHA;
	
	
	IF (vExisteRegistro > 0) THEN
		SET P_RES = -1;
		SET P_TXT_OUT = CONCAT('La fecha ', pFec,' para el subyacente ', pNombre, ' ya se encontraba.');
	ELSEIF (vID_Subyacente = 0) THEN
		SET P_RES = -1;
		SET P_TXT_OUT = 'Subyacente no encontrado - '||pNombre;
	ELSE
		INSERT INTO IEB_PRO.T_HISTORICOS_SUBYACENTES VALUES(vID_Subyacente,
															  vFECHA,
															  pPrecio);
															  
		SET P_TXT_OUT = CONCAT('Insertada ',pFec,' - ', pNombre,'.');
		SET P_RES = 1;
	END IF;
	
END$$
DELIMITER;



/*--------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------------------------*/