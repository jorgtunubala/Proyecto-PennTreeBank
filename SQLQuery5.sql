/****** Script para el comando SelectTopNRows de SSMS  ******/
SELECT TOP 1000 [ID_TERMINO]
      ,[ID_ETIQUETA]
  FROM [CorpusPennTreeBank].[dbo].[TERMINO_ETIQUETA]
  order by ID_TERMINO,ID_ETIQUETA

  select id_TERMINO, valor_termino from TERMINO

  select id_etiqueta, nombre_etiqueta from ETIQUETA