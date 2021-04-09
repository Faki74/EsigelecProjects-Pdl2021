--MANDATORY INSTRUCTIONS, always uncomment
----------------------------------------------------------------
--****************Prevent from too large values*****************
--alter system set max_string_size='EXTENDED';
----------------------------------------------------------------
--***************Generate alphanum_id for a seq******************
--/*

CREATE OR REPLACE FUNCTION idgenerate(idbase VARCHAR2,tablename IN VARCHAR2,genval NUMBER) 
    return VARCHAR2 DETERMINISTIC
    is newid VARCHAR2(32);idcount NUMBER(2);
    table_acronym VARCHAR2(10); table_sql VARCHAR2(2000);
    next_val VARCHAR2(200); seq_sql VARCHAR2(2000);
    begin
        newid:=initcap(idbase);--idcount:=0;
        table_acronym:=substr(tablename,instr(tablename,'_',-1)+1);--get the table's nick sequence name
        table_sql:='SELECT count(*) FROM '||tablename||' where initcap('||table_acronym||'_id) Like :id';
        --select count(*) from polluant_plt where lower(plt_nom) like 'ozone';
        EXECUTE IMMEDIATE table_sql into idcount using newid||'%';
        if idcount!=0 then
            --seq_sql := 'SELECT '||table_acronym||'_seq.nextval FROM DUAL';
            --EXECUTE IMMEDIATE seq_sql INTO NEXT_VAL;
            --id:=idbase||next_val;
            newid:=newid||(idcount+1);
        end if;
        if genval !=0 then
            newid:=lower(newid||'@pdlesig');
        end if;
        return (newid);
    end idgenerate;
/
--A ne pas supprimer si d'autres instructions suivent
commit;
----------------------------------------------------------------
--********************Test de la fonction***********************
--
----------------------------------------------------------------
--/*
----------------------------------------------------------------
--***************Suppression des anciennes tables***************
DROP TABLE userprofil_usrpfl; DROP TABLE profil_pfl;
DROP TABLE cardLeo_card;DROP TABLE user_usr;
DROP TABLE acces_acs; DROP TABLE type_acces_acstype; DROP TABLE place_plc;
DROP TABLE horaire_hr; 
--drop function idgenerate;
--*/
--/*
----------------------------------------------------------------
--*************Suppression des anciennes sequences**************
drop sequence usrpfl_seq; drop sequence pfl_seq;
drop sequence card_seq; drop sequence usr_seq;
drop SEQUENCE acs_seq; drop sequence acstype_seq; drop sequence plc_seq;
drop sequence hr_seq;
commit;
--*/
----------------------------------------------------------------
--*************-----------LET'S DO IT-----------****************
----------------------------------------------------------------
--/*
--*************Table contenant les utilisateurs*****************
CREATE SEQUENCE usr_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE user_usr(
    usr_id VARCHAR2(50),
    usr_nom VARCHAR(15) CONSTRAINT NN_usr_nom NOT NULL,
    usr_prenom VARCHAR(15) CONSTRAINT NN_usr_prenom NOT NULL,
    usr_birthday VARCHAR2(11) CONSTRAINT NN_usr_birthday NOT NULL,
    usr_fonction VARCHAR(16) CONSTRAINT NN_usr_fonction NOT NULL,
    CONSTRAINT PK_user_usr PRIMARY KEY(usr_id)
);
----------------------------------------------------------------
--**************Table contenant les Horaires******************
CREATE SEQUENCE hr_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE horaire_hr(
    hr_id VARCHAR2(32),
    hr_open VARCHAR2(9) CONSTRAINT NN_hr_open NOT NULL,
    hr_close VARCHAR2(9) CONSTRAINT NN_hr_close NOT NULL,
    CONSTRAINT PK_hr_id PRIMARY KEY(hr_id),
    CONSTRAINT Unique_hr Unique(hr_open,hr_close)
);
--------------------------------------------------------------
--*************Table contenant les types des acces**************
CREATE SEQUENCE acstype_seq START WITH 1 INCREMENT BY 1 NOCYCLE NOCACHE;
CREATE TABLE type_acces_acstype(
    acstype_id NUMBER(38),
    acstype_description VARCHAR2(32) default 'Aucune info',
    CONSTRAINT PK_acstype_id PRIMARY KEY(acstype_id)
);
----------------------------------------------------------------
--*************Table contenant les cartes Leo*******************
CREATE SEQUENCE card_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE cardLeo_card(
    card_id VARCHAR2(32),
    card_usr_id VARCHAR2(50),
    card_status NUMBER(3) CONSTRAINT NN_card_status NOT NULL,--0=Desactivated 1=Activated
    CONSTRAINT PK_card_id PRIMARY KEY(card_id),
    CONSTRAINT FK_card_usr_id FOREIGN KEY (card_usr_id) REFERENCES user_usr(usr_id) ON DELETE CASCADE
);
----------------------------------------------------------------
--*******************Table contenant les lieux******************
CREATE SEQUENCE plc_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE place_plc(
    plc_id VARCHAR2(32),
    plc_location VARCHAR2(32) default 'Aucune info',
    plc_nbacces NUMBER(3) CHECK(plc_nbacces>0),
    plc_hr_id VARCHAR(32) default on null 'Horaire accessible',
    plc_status NUMBER(3) CONSTRAINT NN_plc_status NOT NULL,--0=Desactivated 1=Activated
    CONSTRAINT Pk_plc_id PRIMARY KEY(plc_id),
    CONSTRAINT Fk_plc_hr_id FOREIGN KEY(plc_hr_id) REFERENCES horaire_hr(hr_id) ON DELETE SET NULL
);
----------------------------------------------------------------
--***Table contenant les differents acces associes aux lieux***
CREATE SEQUENCE acs_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE acces_acs(
    acs_id VARCHAR2(32),
    acs_description VARCHAR2(32) default 'Aucune info',
    acs_plc_id VARCHAR2(32),
    acs_acstype_id NUMBER(38),
    CONSTRAINT NN_acs_id PRIMARY KEY (acs_id),
    CONSTRAINT FK_acs_plc_id FOREIGN KEY (acs_plc_id) REFERENCES place_plc(plc_id) ON DELETE CASCADE,
    CONSTRAINT FK_acs_acstype_id FOREIGN KEY (acs_acstype_id) REFERENCES type_acces_acstype(acstype_id) ON DELETE CASCADE
);
--------------------------------------------------------------
--**************Table contenant les Profils*******************
CREATE SEQUENCE pfl_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE profil_pfl(
    pfl_id VARCHAR2(32),
    pfl_description VARCHAR2(32) default 'Aucune info',
    pfl_plc_id VARCHAR2(32),
    pfl_hr_id VARCHAR2(32),
    CONSTRAINT PK_pfl_id PRIMARY KEY(pfl_id),
    CONSTRAINT FK_pfl_plc_id FOREIGN KEY(pfl_plc_id) REFERENCES place_plc(plc_id) ON DELETE CASCADE,
    CONSTRAINT FK_pfl_hr_id FOREIGN KEY(pfl_hr_id) REFERENCES horaire_hr(hr_id) ON DELETE SET NULL
);
--------------------------------------------------------------
--*****Table contenant associant les Usagers aux Profils******
CREATE SEQUENCE usrpfl_seq START WITH 1 INCREMENT BY 1 NOCACHE NOCYCLE;
CREATE TABLE userprofil_usrpfl(
    usrpfl_id NUMBER(38),
    usrpfl_pfl_id VARCHAR2(32),
    usrpfl_usr_id VARCHAR2(50),
    CONSTRAINT PK_usrpfl_id PRIMARY KEY(usrpfl_id),
    CONSTRAINT FK_usrpfl_pfl_id FOREIGN KEY(usrpfl_pfl_id) REFERENCES profil_pfl(pfl_id) ON DELETE CASCADE,
    CONSTRAINT FK_usrpfl_usr_id FOREIGN KEY(usrpfl_usr_id) REFERENCES user_usr(usr_id) ON DELETE CASCADE
);
commit;
--*/