/****** Script para el comando SelectTopNRows de SSMS  ******/
SELECT TOP 1000 [ID_TERMINO]
      ,[ID_FRASE]
      ,[ID_ORDEN]
      ,[TERMINO]
      ,[ETIQUETA]
      ,[ETIQUETA_PREDECESORA_ID]
      ,[ETIQUETA_ID]
      ,[ETIQUETA_SUCESORA_ID]
      ,[FOLDER]
  FROM [CorpusPennTreeBank].[dbo].[FRASE_TERMINO]
  order by ID_FRASE,ID_ORDEN