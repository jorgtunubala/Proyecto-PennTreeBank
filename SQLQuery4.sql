/****** Script para el comando SelectTopNRows de SSMS  ******/
SELECT TOP 1000 
      [ID_FRASE],[ID_TERMINO]
      ,[ID_ORDEN]
      ,[ETIQUETA_PREDECESORA]
      ,[ETIQUETA_SUCESORA]
  FROM [CorpusPennTreeBank].[dbo].[FRASE_TERMINO]
  order by ID_FRASE,ID_ORDEN