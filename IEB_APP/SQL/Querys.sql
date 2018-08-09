CTCRVLX684HQUUDK

https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=ITX.MC&outputsize=full&apikey=CTCRVLX684HQUUDK&datatype=csv


https://www.alphavantage.co/query?function=SECTOR&apikey=CTCRVLX684HQUUDK
	

SELECT * FROM T_SUBYACENTES;

SELECT b.NOMBRE, a.* 
  FROM T_SUBYACENTES_HISTO_D01 a,
	   T_SUBYACENTES b
 WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE;
 
SELECT b.NOMBRE, a.* 
  FROM T_SUBYACENTES_HISTO_H01 a,
	   T_SUBYACENTES b
 WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE;
 
SELECT b.NOMBRE, a.* 
  FROM T_SUBYACENTES_HISTO_M10 a,
	   T_SUBYACENTES b
 WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE;
 
SELECT b.NOMBRE, a.* FROM T_VENCIMIENTOS a, T_SUBYACENTES b WHERE a.ID_SUBYACENTE = B.ID_SUBYACENTE;

SELECT c.NOMBRE, b.P_CLOSE_SUBY_H01, a.* FROM T_VOLATILIDADES_H01 a, T_SUBYACENTES_HISTO_H01 b, T_SUBYACENTES c WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE AND a.ID_SUBYACENTE = c.ID_SUBYACENTE 
AND a.F_HISTO_VOL_H01 = b.F_HISTO_SUBY_H01
AND a.F_HISTO_VOL_H01 = (SELECT MAX(d.F_HISTO_VOL_H01) FROM T_VOLATILIDADES_H01 d) AND UPPER(c.NOMBRE) LIKE '%S&P%';

SELECT * FROM T_VOLATILIDADES_M10 a WHERE a.F_HISTO_VOL_M10 = (SELECT MAX(d.F_HISTO_VOL_M10) FROM T_VOLATILIDADES_M10 d);

SELECT * FROM T_VOLATILIDADES_D01;

select b.nombre, a.* from t_volatilidades_h01 a, t_subyacentes b
where a.id_subyacente = 30009
  and a.id_subyacente = b.id_subyacente;

SELECT * FROM T_HOUR_RATE;

SELECT b.ID_SUBYACENTE, MAX(b.F_HISTO_VOL_M10) FROM T_VOLATILIDADES_M10 b GROUP BY ID_SUBYACENTE;
 WHERE b.ID_SUBYACENTE IN (
SELECT DISTINCT(a.ID_SUBYACENTE) FROM T_VOLATILIDADES_M10 a);

SELECT a.ID_SUBYACENTE, 
       (SELECT MAX(b.ID_SUBYACENTE) FROM T_VOLATILIDADES_M10 b WHERE b.ID_SUBYACENTE = a.ID_SUBYACENTE) AS FECHA, 
	   a.VENCIMIENTO
	   
  FROM T_VOLATILIDADES_M10 a
GROUP BY a.ID_SUBYACENTE, a.VENCIMIENTO;

select * from t_opciones_Cartera;

select a.*, b.* from T_OPCIONES_CARTERA a, T_ORDENES_OPCIONES b WHERE a.ID_OPCION = b.ID_OPCION;

select a.*, b.* from T_OPCIONES_CARTERA a, T_STOCK_OPCIONES b WHERE a.ID_OPCION = b.ID_OPCION;

select * from T_BENEFICIO_OPCIONES;

select a.NOMBRE, 
	   a.TIPO, 
	   a.STRIKE, 
	   a.VENCIMIENTO,
	   b.P_COMPRA,
	   b.TIPO_CAMBIO_COMPRA AS TIPO_C, 
	   b.P_VENTA, 
	   b.TIPO_CAMBIO_VENTA AS TIPO_V, 
	   b.TITULOS, 
	   b.COMPRA_VENTA AS C_V,
	   b.BENEFICIO_DIVISA AS BEN_DIV,
	   b.BENEFICIO_EUROS AS BEN_EUR
  from T_OPCIONES_CARTERA a, 
	   T_BENEFICIO_OPCIONES b 
 WHERE a.ID_OPCION = b.ID_OPCION;

select * from TABLA_TRAZAS;

select a.*, b.* from t_futuros_d01 b, T_subyacentes a where a.id_subyacente = b.id_subyacente;

/*-------------------*/

select * from T_FUTUROS_CARTERA;

select a.*, b.* from T_FUTUROS_CARTERA a, T_ORDENES_FUTUROS b WHERE a.ID_FUTURO = b.ID_FUTURO;

select a.*, b.* from T_FUTUROS_CARTERA a, T_STOCK_FUTUROS b WHERE a.ID_FUTURO = b.ID_FUTURO;

select * from T_BENEFICIO_FUTUROS;

select b.nombre, a.f_histo_vol_h01, a.vencimiento, a.vol90, a.vol100, a.vol110, a.tasa_r, beta90100, beta100110
from t_volatilidades_h01 a, t_subyacentes b
where a.id_subyacente = b.id_subyacente
and a.f_histo_vol_h01 = (select max(c.f_histo_vol_h01) from t_volatilidades_h01 c
						 where c.id_subyacente = a.id_subyacente)
order by a.vol100 desc;


/* Consulta matriz de historicos de volatilidades. MÁS RECIENTES!!*/
select b.nombre, a.f_histo_vol_h01, a.vencimiento,a.vol80, a.vol85, a.vol90, a.vol95, a.vol100, a.vol105, a.vol110, a.vol115, a.vol120, a.tasa_r, beta90100, beta100110
from t_volatilidades_h01 a, t_subyacentes b
where a.id_subyacente = b.id_subyacente
and a.f_histo_vol_h01 = (select max(c.f_histo_vol_h01) from t_volatilidades_h01 c
						 where c.id_subyacente = a.id_subyacente)
order by b.nombre, a.vencimiento asc;

select b.nombre, a.f_histo_vol_h01, a.vencimiento,a.vol80, a.vol85, a.vol90, a.vol95, a.vol100, a.vol105, a.vol110, a.vol115, a.vol120, a.tasa_r, beta90100, beta100110
from t_volatilidades_h01 a, t_subyacentes b
where a.id_subyacente = b.id_subyacente
and a.f_histo_vol_h01 = (select max(c.f_histo_vol_h01) from t_volatilidades_h01 c)
order by b.nombre, a.vencimiento asc;


/* Consulta matriz de historicos de volatilidades para el vencimiento más cercano de un subyacente.*/
select b.nombre, 
	   a.f_histo_vol_h01, 
	   a.vencimiento, 
	   a.ATM,
	   a.vol80, 
	   a.vol85, 
	   a.vol90, 
	   a.vol95, 
	   a.vol100, 
	   a.vol105, 
	   a.vol110, 
	   a.vol115, 
	   a.vol120, 
	   a.tasa_r, 
	   a.beta90100, 
	   a.beta100110
from t_volatilidades_h01 a, t_subyacentes b, T_VENCIMIENTOS c
where a.id_subyacente = '30009'
  and a.id_subyacente = b.id_subyacente
  and a.vencimiento = (SELECT MIN(d.vencimiento) FROM T_VENCIMIENTOS d WHERE d.id_subyacente = b.id_subyacente)
order by b.nombre, a.f_histo_vol_h01;



select * from t_volatilidades_h01 a
 where a.f_histo_vol_h01 = STR_TO_DATE('2018-07-16 18:00:00', '%Y-%m-%d %T')
   and a.id_subyacente = '30001'
   and a.vencimiento in (SELECT MIN(d.vencimiento) FROM T_VENCIMIENTOS d WHERE d.id_subyacente = a.id_subyacente);
   
select b.nombre, a.f_histo_vol_h01, a.vencimiento, a.ATM, a.vol80, a.vol85, a.vol90, a.vol95, a.vol100, a.vol105, a.vol110, a.vol115, a.vol120, a.tasa_r, a.beta90100,  a.beta100110
from t_volatilidades_h01 a, t_subyacentes b
where a.id_subyacente = '30059'
  /*and a.f_histo_vol_h01 = STR_TO_DATE('2018-07-16 18:00:00', '%Y-%m-%d %T')*/
  and a.id_subyacente = b.id_subyacente
  and a.vencimiento = (SELECT MIN(d.vencimiento) 
						 FROM T_VENCIMIENTOS d 
						WHERE d.id_subyacente = a.id_subyacente
						  AND d.vencimiento > NOW())
order by b.nombre, a.f_histo_vol_h01;



select * from t_volatilidades_d01 a 
where a.f_histo_vol_d01 < STR_TO_DATE('2018-07-17 20:01:00', '%Y-%m-%d %T')
	and a.vencimiento = (SELECT MIN(d.vencimiento) 
							 FROM T_VENCIMIENTOS d 
							WHERE d.id_subyacente = a.id_subyacente
							  AND d.vencimiento > NOW())
  and a.id_subyacente = '30059';

/*------------------------HISTORICOS VOLATILIDADES------------------------------*/

SELECT B.NOMBRE, A.* FROM T_HISTORICOS_VOLATILIDADES a, T_SUBYACENTES b WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE;

select count(*) from t_historicos_subyacentes;

select count(*) from t_historicos_volatilidades;

select  b.nombre, a.ID_SUBYACENTE, MAX(a.FECHA) as FECHAMAX, MIN(a.FECHA) as FECHAMIN, COUNT(a.ID_SUBYACENTE) AS CUENTA
  from t_historicos_volatilidades a, t_subyacentes b
 where a.ID_SUBYACENTE = b.ID_SUBYACENTE
 group by a.id_subyacente
 order by FECHAMIN;
 
select  b.nombre, a.ID_SUBYACENTE, MAX(a.FECHA) as FECHAMAX, MIN(a.FECHA) as FECHAMIN
  from t_historicos_subyacentes a, t_subyacentes b
 where a.ID_SUBYACENTE = b.ID_SUBYACENTE
 group by a.id_subyacente
 order by FECHAMIN;
 
/*------------------------HISTORICOS VOLATILIDADES (sólo activos en uso)-------------------------*/
select  b.nombre, 
		c.ID_SUBYACENTE, 
		(SELECT MAX(d.FECHA) FROM T_HISTORICOS_VOLATILIDADES d WHERE d.ID_SUBYACENTE = e.ID_OPCION) as FECMAX_VOL, 
		(SELECT MIN(d.FECHA) FROM T_HISTORICOS_VOLATILIDADES d WHERE d.ID_SUBYACENTE = e.ID_OPCION)  as FECMIN_VOL, 
		(SELECT COUNT(*) FROM T_HISTORICOS_VOLATILIDADES d WHERE d.ID_SUBYACENTE = e.ID_OPCION) AS CUENTA_VOL,
		(SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = c.ID_SUBYACENTE) AS CUENTA_HIST,
		(SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA f, 
							  T_HISTORICOS_VOLATILIDADES g
			            WHERE f.ID_SUBYACENTE = c.ID_SUBYACENTE
						  AND g.ID_SUBYACENTE = e.ID_OPCION
						  AND f.FECHA = g.FECHA) AS CUENTA_JOIN
  from t_activos_uso c, t_subyacentes b, t_historicos_volatilidades a, T_RELA_OPT_FUT e
 where c.ID_SUBYACENTE = b.ID_SUBYACENTE
   and c.ID_SUBYACENTE = e.ID_FUTURO
   and e.ID_OPCION = a.ID_SUBYACENTE
 group by c.id_subyacente
 order by CUENTA_JOIN;


select t1.fecha, t2.fecha 
  from t_historicos_subyacentes t1
 where t1.ID_SUBYACENTE = 30057
inner join t_historicos_subyacentes t2 ON t1.FECHA = t2.fecha; 
	   t_historicos_subyacentes t2
  INNER JOIN 
 WHERE t1.fecha = t2.fecha 
   AND t1.ID_SUBYACENTE = 30057
   AND t2.ID_SUBYACENTE = 30059;

SELECT a.fecha
  from t_historicos_volatilidades a
 where a.id_subyacente = 30011
   and a.fecha in (SELECT b.fecha
  from t_historicos_volatilidades b
 where b.id_subyacente = 30034);
 
 
 /*ACTUALIZACIÓN DE HORAS DE APERTURA Y CIERRE*/
 UPDATE T_SUBYACENTES a
    SET a.HORA_APERTURA = '0000',
        a.HORA_CIERRE   = '0000'
  WHERE a.TIPO_SUBYACENTE = 'OPTION';		
 
/*ACTUALIZACIÓN DE LAS HORAS DE LOS VENCIMIENTOS*/
select STR_TO_DATE(concat(year(a.vencimiento),'/',month(a.vencimiento),'/',day(a.vencimiento), ' 00:00'), '%Y/%m/%d %T') from T_VOLATILIDADES_H01 a;

select * from t_volatilidades_h01 a

SELECT a.ID_SUBYACENTE, a.F_HISTO_VOL_H01, STR_TO_DATE(concat(year(a.vencimiento),'/',month(a.vencimiento),'/',day(a.vencimiento), ' 00:00'), '%Y/%m/%d %T') AS FECHA, count(*) AS CUENTA from t_volatilidades_h01 a 
WHERE HOUR(a.VENCIMIENTO) <> 0
GROUP BY a.ID_SUBYACENTE, a.F_HISTO_VOL_H01, FECHA
HAVING CUENTA > 1;

select * from t_volatilidades_h01 a
 where a.f_histo_vol_h01 = STR_TO_DATE('2018-07-19 20:00:00', '%Y-%m-%d %T')
   and hour(a.vencimiento) <> 0;

DELETE FROM T_VOLATILIDADES_H01
where f_histo_vol_h01 = STR_TO_DATE('2018-07-19 20:00:00', '%Y-%m-%d %T')
   and hour(vencimiento) <> 0;
   
UPDATE T_VOLATILIDADES_H01 a
   SET a.VENCIMIENTO = STR_TO_DATE(concat(year(a.VENCIMIENTO),'/',month(a.VENCIMIENTO),'/',day(a.VENCIMIENTO), ' 00:00'), '%Y/%m/%d %T')
 WHERE HOUR(a.VENCIMIENTO) <> 0;

DELETE FROM T_VENCIMIENTOS WHERE HOUR(VENCIMIENTO)<>0;
   
SELECT a.ID_SUBYACENTE, COUNT(a.ID_SUBYACENTE) AS CUENTA FROM IEB_PRO.T_HISTORICOS_SUBYACENTES a GROUP BY a.ID_SUBYACENTE;
  


SELECT a.ID_SUBYACENTE, 
	   a.NOMBRE,
	   b.TICKER,
	   (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) as REGISTROS,
	   (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE and d.P_ADJ_CLOSE = 0) as CUENTA_MAL,
	   (SELECT MAX(P_ADJ_CLOSE) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) as MAXIMO,
	   (SELECT MIN(P_ADJ_CLOSE) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) as MINIMO,
	   (SELECT P_ADJ_CLOSE FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND d.FECHA = 
				(SELECT MAX(e.FECHA) FROM T_HISTORICOS_COMPLETA e WHERE e.ID_SUBYACENTE = a.ID_SUBYACENTE)) as ACTUAL,
	   (SELECT P_CLOSE FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND d.FECHA = 
				(SELECT MAX(e.FECHA) FROM T_HISTORICOS_COMPLETA e WHERE e.ID_SUBYACENTE = a.ID_SUBYACENTE)) as ACTUAL_2,
	   (SELECT P_HISTO_FUT_D01 FROM T_FUTUROS_D01 d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND d.F_HISTO_FUT_D01 = 
				(SELECT MAX(e.F_HISTO_FUT_D01) FROM T_FUTUROS_D01 e WHERE e.ID_SUBYACENTE = a.ID_SUBYACENTE)) AS ACTUAL_IEB,
	   (SELECT MIN(d.FECHA) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) AS FEC_FIRST,
	   (SELECT MAX(d.FECHA) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) AS FEC_LAST
	   
 FROM T_SUBYACENTES a,
	  T_ALPHAVANTAGE b
WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE
  /*AND (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE and d.P_ADJ_CLOSE = 0) > 0*/
GROUP BY ID_SUBYACENTE
ORDER BY REGISTROS;


SELECT a.ID_SUBYACENTE, 
	   a.NOMBRE,
	   b.TICKER,
	   (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND YEAR(d.FECHA) = 2018) as REG_2018,
	   (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) as REGISTROS_TOTAL,
	   (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE and d.P_ADJ_CLOSE = 0) as CUENTA_MAL,
	   (SELECT MAX(P_ADJ_CLOSE) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) as MAXIMO,
	   (SELECT MIN(P_ADJ_CLOSE) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) as MINIMO,
	   (SELECT P_ADJ_CLOSE FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND d.FECHA = 
				(SELECT MAX(e.FECHA) FROM T_HISTORICOS_COMPLETA e WHERE e.ID_SUBYACENTE = a.ID_SUBYACENTE)) as ACTUAL,
	   (SELECT P_CLOSE FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND d.FECHA = 
				(SELECT MAX(e.FECHA) FROM T_HISTORICOS_COMPLETA e WHERE e.ID_SUBYACENTE = a.ID_SUBYACENTE)) as ACTUAL_2,
	   (SELECT P_HISTO_FUT_D01 FROM T_FUTUROS_D01 d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE AND d.F_HISTO_FUT_D01 = 
				(SELECT MAX(e.F_HISTO_FUT_D01) FROM T_FUTUROS_D01 e WHERE e.ID_SUBYACENTE = a.ID_SUBYACENTE)) AS ACTUAL_IEB,
	   (SELECT MIN(d.FECHA) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) AS FEC_FIRST,
	   (SELECT MAX(d.FECHA) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE) AS FEC_LAST
	   
 FROM T_SUBYACENTES a,
	  T_ALPHAVANTAGE b
WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE
  /*AND (SELECT COUNT(*) FROM T_HISTORICOS_COMPLETA d WHERE d.ID_SUBYACENTE = a.ID_SUBYACENTE and d.P_ADJ_CLOSE = 0) > 0*/
GROUP BY ID_SUBYACENTE
ORDER BY REG_2018;

SELECT * FROM T_HISTORICOS_COMPLETA a WHERE a.ID_SUBYACENTE = 20005 AND YEAR(a.FECHA) = 2018;


DELETE FROM T_HISTORICOS_COMPLETA WHERE FECHA < STR_TO_DATE ('25/09/2007', '%d/%m/%Y');

/* ******************RELACIONES ENTRE FUTUROS Y OPCIONES***********************************/

SELECT DISTINCT(a.NOMBRE),
	   (SELECT b.ID_SUBYACENTE 
	      FROM T_SUBYACENTES b
		 WHERE b.NOMBRE = a.NOMBRE
		   AND b.TIPO_SUBYACENTE = 'FUTURE') AS ID_FUTURO,
		(SELECT b.ID_SUBYACENTE 
	      FROM T_SUBYACENTES b
		 WHERE b.NOMBRE = a.NOMBRE
		   AND b.TIPO_SUBYACENTE = 'OPTION') AS ID_OPCION
  FROM T_SUBYACENTES a;

  
SELECT DISTINCT(a.NOMBRE),
	   (SELECT b.ID_SUBYACENTE 
	      FROM T_SUBYACENTES b
		 WHERE b.NOMBRE = a.NOMBRE
		   AND b.TIPO_SUBYACENTE = 'FUTURE') AS ID_FUTURO,
		(SELECT b.ID_SUBYACENTE 
	      FROM T_SUBYACENTES b
		 WHERE b.NOMBRE = a.NOMBRE
		   AND b.TIPO_SUBYACENTE = 'OPTION') AS ID_OPCION
  FROM T_SUBYACENTES a
 WHERE a.NOMBRE NOT IN (
						SELECT b.NOMBRE
						   FROM T_SUBYACENTES b,
						        T_RELA_OPT_FUT c
						  WHERE b.ID_SUBYACENTE = c.ID_FUTURO
						UNION 
						SELECT b.NOMBRE
						   FROM T_SUBYACENTES b,
						        T_RELA_OPT_FUT c
						  WHERE b.ID_SUBYACENTE = c.ID_OPCION);


SELECT a.ID_FUTURO, 
	   a.ID_OPCION,
	   (SELECT b.NOMBRE FROM T_SUBYACENTES b WHERE b.ID_SUBYACENTE = a.ID_FUTURO) AS FUTURO,
	   (SELECT b.NOMBRE FROM T_SUBYACENTES b WHERE b.ID_SUBYACENTE = a.ID_OPCION) AS OPCION
  FROM T_RELA_OPT_FUT a;

select * from t_rela_opt_fut;




DROP VIEW V_DIFERENCIAS_PERIODOS;
CREATE VIEW V_DIFERENCIAS_PERIODOS AS

SELECT b.NOMBRE, 
	   a.ID_SUBYACENTE AS ID,
	   DATE_FORMAT(a.F_HISTO_VOL_D01, '%Y-%m-%d') AS FECHA_VALOR,
	   (SELECT k.VOL95 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) AS V95_CP,
	   (SELECT k.VOL100 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) AS V100_CP,
	   (SELECT k.VOL105 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) AS V105_CP,
	   (SELECT h.VOL95 
	     FROM T_VOLATILIDADES_D01 h
	    WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
		  AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		  AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')) AS V95_LP,
	   (SELECT h.VOL100 
	     FROM T_VOLATILIDADES_D01 h
	    WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
		  AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		  AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')) AS V100_LP,
	   (SELECT h.VOL105 
	     FROM T_VOLATILIDADES_D01 h
	    WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
		  AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		  AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')) AS V105_LP,
	    (SELECT k.BETA90100 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) AS B90100_CP,
	   (SELECT k.BETA100110 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) AS B100110_CP,
	   (SELECT h.BETA90100 
	     FROM T_VOLATILIDADES_D01 h
	    WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
		  AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		  AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')) AS B90100_LP,
	   (SELECT h.BETA100110 
	     FROM T_VOLATILIDADES_D01 h
	    WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
		  AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		  AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')) AS B100110_LP

  FROM T_VOLATILIDADES_D01 a,
       T_SUBYACENTES b
 WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE
   AND a.VENCIMIENTO IN (STR_TO_DATE('2018-12-21', '%Y-%m-%d'), STR_TO_DATE('2019-03-15', '%Y-%m-%d'))
   /*AND ((SELECT k.VOL95 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) < (SELECT h.VOL95 
																		   FROM T_VOLATILIDADES_D01 h
																		  WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
																		    AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
																			AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d'))
		OR
		 (SELECT k.VOL100 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) < (SELECT h.VOL100 
																		   FROM T_VOLATILIDADES_D01 h
																		  WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
																		    AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
																			AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d'))
		OR
		 (SELECT k.VOL105 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) < (SELECT h.VOL105 
																		   FROM T_VOLATILIDADES_D01 h
																		  WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
																		    AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
																			AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')))*/
 GROUP BY a.ID_SUBYACENTE, FECHA_VALOR		
 ORDER BY a.ID_SUBYACENTE, FECHA_VALOR, VENCIMIENTO;

SELECT v.*,
	   (v.V95_CP-v.V95_LP) AS DIF95,
	   (v.V100_CP-v.V100_LP) AS DIF100,
	   (v.V105_CP-v.V105_LP) AS DIF105
  FROM V_DIFERENCIAS_PERIODOS v
  WHERE v.FECHA_VALOR = (SELECT MAX(a.F_HISTO_VOL_D01) FROM T_VOLATILIDADES_D01 a)
 ORDER BY DIF100;

 
SELECT v.*,
   (v.V95_CP-v.V95_LP) AS DIF95,
   (v.V100_CP-v.V100_LP) AS DIF100,
   (v.V105_CP-v.V105_LP) AS DIF105
  FROM V_DIFERENCIAS_PERIODOS v
 WHERE v.ID = 30059
 ORDER BY v.FECHA_VALOR;












SELECT b.NOMBRE, 
	   a.ID_SUBYACENTE AS ID,
	   DATE_FORMAT(a.F_HISTO_VOL_D01, '%Y-%m-%d') AS FECHA_VALOR,
	   DATE_FORMAT(a.VENCIMIENTO, '%Y-%m-%d') AS VENCIMIENTO,
	   a.ATM,
	   a.VOL95,
	   a.VOL100,
	   a.VOL105,
	   a.TASA_R,
	   a.BETA90100 AS B90100,
	   a.BETA100110 AS B100110
  FROM T_VOLATILIDADES_D01 a,
       T_SUBYACENTES b
 WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE
   AND a.VENCIMIENTO IN (STR_TO_DATE('2018-12-21', '%Y-%m-%d'), STR_TO_DATE('2019-03-15', '%Y-%m-%d'))
   AND ((SELECT k.VOL95 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) < (SELECT h.VOL95 
																		   FROM T_VOLATILIDADES_D01 h
																		  WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
																		    AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
																			AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d'))
		OR
		 (SELECT k.VOL100 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) < (SELECT h.VOL100 
																		   FROM T_VOLATILIDADES_D01 h
																		  WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
																		    AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
																			AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d'))
		OR
		 (SELECT k.VOL105 
		  FROM T_VOLATILIDADES_D01 k 
		 WHERE k.ID_SUBYACENTE = a.ID_SUBYACENTE 
		   AND k.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
		   AND k.VENCIMIENTO = STR_TO_DATE('2018-12-21', '%Y-%m-%d')) < (SELECT h.VOL105 
																		   FROM T_VOLATILIDADES_D01 h
																		  WHERE h.ID_SUBYACENTE = a.ID_SUBYACENTE 
																		    AND h.F_HISTO_VOL_D01 = a.F_HISTO_VOL_D01
																			AND h.VENCIMIENTO = STR_TO_DATE('2019-03-15', '%Y-%m-%d')))
		
 ORDER BY a.ID_SUBYACENTE, FECHA_VALOR, VENCIMIENTO;


SELECT b.NOMBRE, 
	   a.*,
	   DATE_FORMAT(a.F_HISTO_VOL_D01, '%Y-%m-%d') AS FECHA_VALOR
  FROM T_VOLATILIDADES_D01 a,
       T_SUBYACENTES b
 WHERE a.ID_SUBYACENTE = b.ID_SUBYACENTE
   AND a.VENCIMIENTO IN (STR_TO_DATE('2018-12-21', '%Y-%m-%d'), STR_TO_DATE('2018-12-21', '%Y-%m-%d'))
   AND a.F_HISTO_VOL_D01 = (SELECT MAX(z.F_HISTO_VOL_D01) FROM T_VOLATILIDADES_D01 z)





SELECT * FROM T_VENCIMIENTOS v
 WHERE v.VENCIMIENTO IN (STR_TO_DATE('2018-12-21', '%Y-%m-%d'), STR_TO_DATE('2018-12-21', '%Y-%m-%d'));



