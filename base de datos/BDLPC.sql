/*==============================================================*/
/* DBMS name:      PostgreSQL 8                                 */
/* Created on:     07/08/2016 13:15:11                          */
/*==============================================================*/


drop index CARGO_PK;

drop table CARGO;

drop index RELATIONSHIP_15_FK;

drop index ESPECIALIDAD_PK;

drop table ESPECIALIDAD;

drop index ESTADOCIVIL_PK;

drop table ESTADOCIVIL;

drop index RELATIONSHIP_12_FK;

drop index RELATIONSHIP_11_FK;

drop index RELATIONSHIP_10_FK;

drop index FUNCIONARIO_PK;

drop table FUNCIONARIO;

drop index GENERO_PK;

drop table GENERO;

drop index GRUPOSANGUINEO_PK;

drop table GRUPOSANGUINEO;

drop index HORARIO_PK;

drop table HORARIO;

drop index RELATIONSHIP_13_FK;

drop index MEDICO_PK;

drop table MEDICO;

drop index OCUPACION_PK;

drop table OCUPACION;

drop index RELATIONSHIP_5_FK;

drop index RELATIONSHIP_4_FK;

drop index RELATIONSHIP_3_FK;

drop index RELATIONSHIP_2_FK;

drop index PERSONA_PK;

drop table PERSONA;

drop index RELATIONSHIP_16_FK;

drop index RELATIONSHIP_14_FK;

drop index TURNO_PK;

drop table TURNO;

/*==============================================================*/
/* Table: CARGO                                                 */
/*==============================================================*/
create table CARGO (
   IDCARGO              INT4                 not null,
   NOMBRECARGO          VARCHAR(200)         not null,
   constraint PK_CARGO primary key (IDCARGO)
);

/*==============================================================*/
/* Index: CARGO_PK                                              */
/*==============================================================*/
create unique index CARGO_PK on CARGO (
IDCARGO
);

/*==============================================================*/
/* Table: ESPECIALIDAD                                          */
/*==============================================================*/
create table ESPECIALIDAD (
   ID_ESPECIALIDAD      SERIAL               not null,
   IDMEDICO             INT4                 null,
   DESCRIPCION          CHAR(20)             null,
   constraint PK_ESPECIALIDAD primary key (ID_ESPECIALIDAD)
);

/*==============================================================*/
/* Index: ESPECIALIDAD_PK                                       */
/*==============================================================*/
create unique index ESPECIALIDAD_PK on ESPECIALIDAD (
ID_ESPECIALIDAD
);

/*==============================================================*/
/* Index: RELATIONSHIP_15_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_15_FK on ESPECIALIDAD (
IDMEDICO
);

/*==============================================================*/
/* Table: ESTADOCIVIL                                           */
/*==============================================================*/
create table ESTADOCIVIL (
   IDESTADOCIVIL        INT4                 not null,
   NOMBREESTADOCIVIL    VARCHAR(60)          not null,
   constraint PK_ESTADOCIVIL primary key (IDESTADOCIVIL)
);

/*==============================================================*/
/* Index: ESTADOCIVIL_PK                                        */
/*==============================================================*/
create unique index ESTADOCIVIL_PK on ESTADOCIVIL (
IDESTADOCIVIL
);

/*==============================================================*/
/* Table: FUNCIONARIO                                           */
/*==============================================================*/
create table FUNCIONARIO (
   IDFUNCIONARIO        INT4                 not null,
   IDHORARIO            INT4                 null,
   IDPERSONA            INT4                 null,
   IDCARGO              INT4                 null,
   AREATRABAJO          VARCHAR(200)         not null,
   ESTADO               BOOL                 not null,
   constraint PK_FUNCIONARIO primary key (IDFUNCIONARIO)
);

/*==============================================================*/
/* Index: FUNCIONARIO_PK                                        */
/*==============================================================*/
create unique index FUNCIONARIO_PK on FUNCIONARIO (
IDFUNCIONARIO
);

/*==============================================================*/
/* Index: RELATIONSHIP_10_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_10_FK on FUNCIONARIO (
IDPERSONA
);

/*==============================================================*/
/* Index: RELATIONSHIP_11_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_11_FK on FUNCIONARIO (
IDCARGO
);

/*==============================================================*/
/* Index: RELATIONSHIP_12_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_12_FK on FUNCIONARIO (
IDHORARIO
);

/*==============================================================*/
/* Table: GENERO                                                */
/*==============================================================*/
create table GENERO (
   IDGENERO             INT4                 not null,
   NOMBREGENERO         VARCHAR(100)         not null,
   constraint PK_GENERO primary key (IDGENERO)
);

/*==============================================================*/
/* Index: GENERO_PK                                             */
/*==============================================================*/
create unique index GENERO_PK on GENERO (
IDGENERO
);

/*==============================================================*/
/* Table: GRUPOSANGUINEO                                        */
/*==============================================================*/
create table GRUPOSANGUINEO (
   IDGRSANGUINEO        INT4                 not null,
   NOMBREGRSANGUINEO    VARCHAR(60)          not null,
   constraint PK_GRUPOSANGUINEO primary key (IDGRSANGUINEO)
);

/*==============================================================*/
/* Index: GRUPOSANGUINEO_PK                                     */
/*==============================================================*/
create unique index GRUPOSANGUINEO_PK on GRUPOSANGUINEO (
IDGRSANGUINEO
);

/*==============================================================*/
/* Table: HORARIO                                               */
/*==============================================================*/
create table HORARIO (
   IDHORARIO            INT4                 not null,
   DIA                  VARCHAR(20)          not null,
   HORAINICIO           TIME                 not null,
   HORAFIN              TIME                 not null,
   constraint PK_HORARIO primary key (IDHORARIO)
);

/*==============================================================*/
/* Index: HORARIO_PK                                            */
/*==============================================================*/
create unique index HORARIO_PK on HORARIO (
IDHORARIO
);

/*==============================================================*/
/* Table: MEDICO                                                */
/*==============================================================*/
create table MEDICO (
   IDMEDICO             INT4                 not null,
   IDFUNCIONARIO        INT4                 null,
   ESPECIALIDAD         VARCHAR(50)          not null,
   constraint PK_MEDICO primary key (IDMEDICO)
);

/*==============================================================*/
/* Index: MEDICO_PK                                             */
/*==============================================================*/
create unique index MEDICO_PK on MEDICO (
IDMEDICO
);

/*==============================================================*/
/* Index: RELATIONSHIP_13_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_13_FK on MEDICO (
IDFUNCIONARIO
);

/*==============================================================*/
/* Table: OCUPACION                                             */
/*==============================================================*/
create table OCUPACION (
   IDOCUPACION          INT4                 not null,
   NOMBREOCUPACION      VARCHAR(60)          not null,
   constraint PK_OCUPACION primary key (IDOCUPACION)
);

/*==============================================================*/
/* Index: OCUPACION_PK                                          */
/*==============================================================*/
create unique index OCUPACION_PK on OCUPACION (
IDOCUPACION
);

/*==============================================================*/
/* Table: PERSONA                                               */
/*==============================================================*/
create table PERSONA (
   IDPERSONA            INT4                 not null,
   IDESTADOCIVIL        INT4                 null,
   IDGENERO             INT4                 null,
   IDOCUPACION          INT4                 null,
   IDGRSANGUINEO        INT4                 null,
   NOMBRES              VARCHAR(150)         not null,
   PRIMERAPELLIDO       VARCHAR(150)         not null,
   SEGUNDOAPELLIDO      VARCHAR(150)         not null,
   FECHANACIMIENTO      DATE                 not null,
   CEDULA               VARCHAR(10)          not null,
   EMAIL                VARCHAR(200)         not null,
   TELEFONOCASA         VARCHAR(20)          not null,
   TELEFONOCELULAR      VARCHAR(20)          not null,
   TELEFONOOFICINA      VARCHAR(20)          not null,
   DIRECCION            VARCHAR(500)         not null,
   constraint PK_PERSONA primary key (IDPERSONA)
);

/*==============================================================*/
/* Index: PERSONA_PK                                            */
/*==============================================================*/
create unique index PERSONA_PK on PERSONA (
IDPERSONA
);

/*==============================================================*/
/* Index: RELATIONSHIP_2_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_2_FK on PERSONA (
IDESTADOCIVIL
);

/*==============================================================*/
/* Index: RELATIONSHIP_3_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_3_FK on PERSONA (
IDGENERO
);

/*==============================================================*/
/* Index: RELATIONSHIP_4_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_4_FK on PERSONA (
IDGRSANGUINEO
);

/*==============================================================*/
/* Index: RELATIONSHIP_5_FK                                     */
/*==============================================================*/
create  index RELATIONSHIP_5_FK on PERSONA (
IDOCUPACION
);

/*==============================================================*/
/* Table: TURNO                                                 */
/*==============================================================*/
create table TURNO (
   ID_TURNO             SERIAL               not null,
   ID_ESPECIALIDAD      INT4                 null,
   IDPERSONA            INT4                 null,
   FECHA_CITA           DATE                 null,
   HORA                 TIME                 null,
   DISPONIBLE           INT4                 null,
   constraint PK_TURNO primary key (ID_TURNO)
);

/*==============================================================*/
/* Index: TURNO_PK                                              */
/*==============================================================*/
create unique index TURNO_PK on TURNO (
ID_TURNO
);

/*==============================================================*/
/* Index: RELATIONSHIP_14_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_14_FK on TURNO (
IDPERSONA
);

/*==============================================================*/
/* Index: RELATIONSHIP_16_FK                                    */
/*==============================================================*/
create  index RELATIONSHIP_16_FK on TURNO (
ID_ESPECIALIDAD
);

alter table ESPECIALIDAD
   add constraint FK_ESPECIAL_RELATIONS_MEDICO foreign key (IDMEDICO)
      references MEDICO (IDMEDICO)
      on delete restrict on update restrict;

alter table FUNCIONARIO
   add constraint FK_FUNCIONA_RELATIONS_PERSONA foreign key (IDPERSONA)
      references PERSONA (IDPERSONA)
      on delete restrict on update restrict;

alter table FUNCIONARIO
   add constraint FK_FUNCIONA_RELATIONS_CARGO foreign key (IDCARGO)
      references CARGO (IDCARGO)
      on delete restrict on update restrict;

alter table FUNCIONARIO
   add constraint FK_FUNCIONA_RELATIONS_HORARIO foreign key (IDHORARIO)
      references HORARIO (IDHORARIO)
      on delete restrict on update restrict;

alter table MEDICO
   add constraint FK_MEDICO_RELATIONS_FUNCIONA foreign key (IDFUNCIONARIO)
      references FUNCIONARIO (IDFUNCIONARIO)
      on delete restrict on update restrict;

alter table PERSONA
   add constraint FK_PERSONA_RELATIONS_ESTADOCI foreign key (IDESTADOCIVIL)
      references ESTADOCIVIL (IDESTADOCIVIL)
      on delete restrict on update restrict;

alter table PERSONA
   add constraint FK_PERSONA_RELATIONS_GENERO foreign key (IDGENERO)
      references GENERO (IDGENERO)
      on delete restrict on update restrict;

alter table PERSONA
   add constraint FK_PERSONA_RELATIONS_GRUPOSAN foreign key (IDGRSANGUINEO)
      references GRUPOSANGUINEO (IDGRSANGUINEO)
      on delete restrict on update restrict;

alter table PERSONA
   add constraint FK_PERSONA_RELATIONS_OCUPACIO foreign key (IDOCUPACION)
      references OCUPACION (IDOCUPACION)
      on delete restrict on update restrict;

alter table TURNO
   add constraint FK_TURNO_RELATIONS_PERSONA foreign key (IDPERSONA)
      references PERSONA (IDPERSONA)
      on delete restrict on update restrict;

alter table TURNO
   add constraint FK_TURNO_RELATIONS_ESPECIAL foreign key (ID_ESPECIALIDAD)
      references ESPECIALIDAD (ID_ESPECIALIDAD)
      on delete restrict on update restrict;

