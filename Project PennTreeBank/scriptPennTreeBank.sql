/*==============================================================*/
/* DBMS name:      Microsoft SQL Server 2012                    */
/* Created on:     05/06/2018 19:32:40                          */
/*==============================================================*/

DROP DATABASE CorpusPennTreeBank
GO
CREATE DATABASE CorpusPennTreeBank
GO

USE CorpusPennTreeBank
GO

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('FRASE_TERMINO') and o.name = 'FK_FRASE_TE_RELATIONS_TERMINO')
alter table FRASE_TERMINO
   drop constraint FK_FRASE_TE_RELATIONS_TERMINO
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('FRASE_TERMINO') and o.name = 'FK_FRASE_TE_RELATIONS_FRASE')
alter table FRASE_TERMINO
   drop constraint FK_FRASE_TE_RELATIONS_FRASE
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TERMINO_ETIQUETA') and o.name = 'FK_TERMINO__TIENE_ETIQUETA')
alter table TERMINO_ETIQUETA
   drop constraint FK_TERMINO__TIENE_ETIQUETA
go

if exists (select 1
   from sys.sysreferences r join sys.sysobjects o on (o.id = r.constid and o.type = 'F')
   where r.fkeyid = object_id('TERMINO_ETIQUETA') and o.name = 'FK_TERMINO__TIENE2_TERMINO')
alter table TERMINO_ETIQUETA
   drop constraint FK_TERMINO__TIENE2_TERMINO
go

if exists (select 1
            from  sysobjects
           where  id = object_id('ETIQUETA')
            and   type = 'U')
   drop table ETIQUETA
go

if exists (select 1
            from  sysobjects
           where  id = object_id('FRASE')
            and   type = 'U')
   drop table FRASE
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('FRASE_TERMINO')
            and   name  = 'RELATIONSHIP_1_FK'
            and   indid > 0
            and   indid < 255)
   drop index FRASE_TERMINO.RELATIONSHIP_1_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('FRASE_TERMINO')
            and   name  = 'RELATIONSHIP_2_FK'
            and   indid > 0
            and   indid < 255)
   drop index FRASE_TERMINO.RELATIONSHIP_2_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('FRASE_TERMINO')
            and   type = 'U')
   drop table FRASE_TERMINO
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TERMINO')
            and   type = 'U')
   drop table TERMINO
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TERMINO_ETIQUETA')
            and   name  = 'TIENE_FK'
            and   indid > 0
            and   indid < 255)
   drop index TERMINO_ETIQUETA.TIENE_FK
go

if exists (select 1
            from  sysindexes
           where  id    = object_id('TERMINO_ETIQUETA')
            and   name  = 'TIENE2_FK'
            and   indid > 0
            and   indid < 255)
   drop index TERMINO_ETIQUETA.TIENE2_FK
go

if exists (select 1
            from  sysobjects
           where  id = object_id('TERMINO_ETIQUETA')
            and   type = 'U')
   drop table TERMINO_ETIQUETA
go

/*==============================================================*/
/* Table: ETIQUETA                                              */
/*==============================================================*/
create table ETIQUETA (
   ID_ETIQUETA          numeric(2)           not null,
   NOMBRE_ETIQUETA      varchar(5)           null,
   constraint PK_ETIQUETA primary key nonclustered (ID_ETIQUETA)
)
go

/*==============================================================*/
/* Table: FRASE                                                 */
/*==============================================================*/
create table FRASE (
   ID_FRASE             numeric(4)           not null,
   FOLDER_FRASE         numeric(3)           null,
   VALOR_FRASE          varchar(max)       null,
   VALOR_FRASSEETIX48   varchar(max)       null,
   VALOR_FRASSEETIX12   varchar(max)       null,
   constraint PK_FRASE primary key nonclustered (ID_FRASE)
)
go

/*==============================================================*/
/* Table: FRASE_TERMINO                                         */
/*==============================================================*/
create table FRASE_TERMINO (
   ID_TERMINO           numeric(5)           not null,
   ID_FRASE             numeric(4)           not null,
   ID_ORDEN             numeric(4)           not null,
   TERMINO              varchar(25)          not null,
   ETIQUETA             varchar(10)          not null,
   ETIQUETA_PREDECESORA_ID numeric(2)           not null,
   ETIQUETA_ID          numeric(2)           not null,
   ETIQUETA_SUCESORA_ID numeric(2)           not null,
   FOLDER               numeric(3)           not null,
   constraint PK_FRASE_TERMINO primary key (ID_TERMINO, ID_FRASE, ID_ORDEN),
   constraint AK_IDENTIFIER_1_FRASE_TE unique (ID_TERMINO, ID_FRASE, ID_ORDEN),
   constraint AK_PK_TERMINO_FRASE_O_FRASE_TE unique (ID_TERMINO, ID_FRASE, ID_ORDEN)
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_2_FK on FRASE_TERMINO (
ID_FRASE ASC
)
go

/*==============================================================*/
/* Index: RELATIONSHIP_1_FK                                     */
/*==============================================================*/
create index RELATIONSHIP_1_FK on FRASE_TERMINO (
ID_TERMINO ASC
)
go

/*==============================================================*/
/* Table: TERMINO                                               */
/*==============================================================*/
create table TERMINO (
   ID_TERMINO           numeric(5)           not null,
   VALOR_TERMINO        varchar(25)          null,
   constraint PK_TERMINO primary key nonclustered (ID_TERMINO)
)
go

/*==============================================================*/
/* Table: TERMINO_ETIQUETA                                      */
/*==============================================================*/
create table TERMINO_ETIQUETA (
   ID_TERMINO           numeric(5)           not null,
   ID_ETIQUETA          numeric(2)           not null,
   constraint AK_IDENTIFIER_1_TERMINO_ unique (ID_ETIQUETA, ID_TERMINO),
   constraint AK_PK_TERMINO_ETIQUET_TERMINO_ unique (ID_ETIQUETA, ID_TERMINO)
)
go

/*==============================================================*/
/* Index: TIENE2_FK                                             */
/*==============================================================*/
create index TIENE2_FK on TERMINO_ETIQUETA (
ID_TERMINO ASC
)
go

/*==============================================================*/
/* Index: TIENE_FK                                              */
/*==============================================================*/
create index TIENE_FK on TERMINO_ETIQUETA (
ID_ETIQUETA ASC
)
go

alter table FRASE_TERMINO
   add constraint FK_FRASE_TE_RELATIONS_TERMINO foreign key (ID_TERMINO)
      references TERMINO (ID_TERMINO)
go

alter table FRASE_TERMINO
   add constraint FK_FRASE_TE_RELATIONS_FRASE foreign key (ID_FRASE)
      references FRASE (ID_FRASE)
go

alter table TERMINO_ETIQUETA
   add constraint FK_TERMINO__TIENE_ETIQUETA foreign key (ID_ETIQUETA)
      references ETIQUETA (ID_ETIQUETA)
go

alter table TERMINO_ETIQUETA
   add constraint FK_TERMINO__TIENE2_TERMINO foreign key (ID_TERMINO)
      references TERMINO (ID_TERMINO)
go

