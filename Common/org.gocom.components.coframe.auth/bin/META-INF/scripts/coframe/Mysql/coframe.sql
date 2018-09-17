drop table if exists CAP_RESAUTH;
drop table if exists cap_partyauth;
drop table if exists CAP_ROLE;
drop table if exists app_menu;
drop table if exists app_funcresource;
drop table if exists app_function;
drop table if exists app_funcgroup;
drop table if exists app_application;
drop table if exists cap_rule;
drop table if exists cap_user;
drop table if exists org_empposition;
drop table if exists org_emporg;
drop table if exists org_employee;
drop table if exists org_position;
drop table if exists org_organization;
drop table if exists org_duty;
create table CAP_RESAUTH (PARTY_ID varchar(64) not null, PARTY_TYPE varchar(64) not null, RES_ID varchar(255) not null, RES_TYPE varchar(64) not null, TENANT_ID varchar(64), RES_STATE varchar(512) not null, PARTY_SCOPE varchar(1) default '0', CREATEUSER varchar(64), CREATETIME datetime, primary key (PARTY_ID, PARTY_TYPE, RES_ID, RES_TYPE));
create table CAP_ROLE (ROLE_ID varchar(64) not null, TENANT_ID varchar(64) not null, ROLE_CODE varchar(64) not null, ROLE_NAME varchar(64), ROLE_DESC varchar(255), CREATEUSER varchar(64), CREATETIME datetime, primary key (ROLE_ID));
create table app_application (APPID numeric(10,0) not null, APPCODE varchar(32), APPNAME varchar(50), APPTYPE varchar(255), ISOPEN varchar(1), OPENDATE date, URL varchar(256), APPDESC varchar(512), MAINTENANCE numeric(10,0), MANAROLE varchar(64), DEMO varchar(512), INIWP varchar(1), INTASKCENTER varchar(1), IPADDR varchar(50), IPPORT varchar(10), APP_ID varchar(64), TENANT_ID varchar(64) not null, protocol_type varchar(64), primary key (APPID));
create table app_funcgroup (FUNCGROUPID numeric(10,0) not null, FUNCGROUPNAME varchar(40), GROUPLEVEL integer, FUNCGROUPSEQ varchar(256), ISLEAF varchar(1), SUBCOUNT numeric(10,0), APP_ID varchar(64), TENANT_ID varchar(64) not null, PARENTGROUP numeric(10,0), APPID numeric(10,0) not null, primary key (FUNCGROUPID));
create table app_funcresource (RESID numeric(10,0) not null, RESTYPE varchar(255), RESPATH varchar(256), COMPACKNAME varchar(40), RESNAME varchar(40), APP_ID varchar(64), TENANT_ID varchar(64) not null, FUNCCODE varchar(255), primary key (RESID));
create table app_function (FUNCCODE varchar(255) not null, FUNCNAME varchar(128) not null, FUNCDESC varchar(512), FUNCACTION varchar(256), PARAINFO varchar(256), ISCHECK varchar(1), FUNCTYPE varchar(255) default '1', ISMENU varchar(1), APP_ID varchar(64), TENANT_ID varchar(64) not null, FUNCGROUPID numeric(10,0), primary key (FUNCCODE));
create table app_menu (MENUID varchar(40) not null, MENUNAME varchar(40) not null, MENULABEL varchar(40) not null, MENUCODE varchar(40), ISLEAF varchar(1), PARAMETER varchar(256), UIENTRY varchar(256), MENULEVEL smallint, ROOTID varchar(40), DISPLAYORDER smallint, IMAGEPATH varchar(100), EXPANDPATH varchar(100), MENUSEQ varchar(256), OPENMODE varchar(255), SUBCOUNT numeric(10,0), APPID numeric(10,0), FUNCCODE varchar(255), APP_ID varchar(64), TENANT_ID varchar(64) not null, PARENTSID varchar(40), primary key (MENUID));
create table cap_partyauth (ROLE_TYPE varchar(64) not null, PARTY_ID varchar(64) not null, PARTY_TYPE varchar(64) not null, ROLE_ID varchar(64) not null, TENANT_ID varchar(64) not null, CREATEUSER varchar(64), CREATETIME datetime not null, primary key (ROLE_TYPE, PARTY_ID, PARTY_TYPE, ROLE_ID));
create table cap_rule (RULE_ID varchar(64) not null, RULE_NAME varchar(64) , TENANT_ID varchar(64) , RULE_TYPE varchar(64) , NAMESPACE varchar(512) , RULE_EXPRESSION blob, CREATEUSER varchar(64), CREATETIME date, primary key (RULE_ID));
create table cap_user (OPERATOR_ID numeric(18,0) not null, TENANT_ID varchar(64) not null, USER_ID varchar(64) not null, PASSWORD varchar(100), INVALDATE date, USER_NAME varchar(64), AUTHMODE varchar(255), STATUS varchar(16), UNLOCKTIME datetime not null, MENUTYPE varchar(255), LASTLOGIN datetime not null, ERRCOUNT numeric(10,0), STARTDATE date, ENDDATE date, VALIDTIME varchar(255), MACCODE varchar(128), IPADDRESS varchar(128), EMAIL varchar(255), CREATEUSER varchar(64), CREATETIME datetime not null, primary key (OPERATOR_ID));
create table org_duty (DUTYID numeric(10,0) not null, DUTYCODE varchar(20), DUTYNAME varchar(30), PARENTDUTY numeric(10,0), DUTYLEVEL integer, DUTYSEQ varchar(256), DUTYTYPE varchar(255), ISLEAF varchar(10), SUBCOUNT numeric(10,0), REMARK varchar(256), TENANT_ID varchar(64) not null, APP_ID varchar(64), primary key (DUTYID));
create table org_employee (EMPID numeric(10,0) not null, EMPCODE varchar(30), OPERATORID numeric(18,0), USERID varchar(30), EMPNAME varchar(50), REALNAME varchar(50), GENDER varchar(255), BIRTHDATE date, POSITION numeric(10,0), EMPSTATUS varchar(255), CARDTYPE varchar(255), CARDNO varchar(20), INDATE date, OUTDATE date, OTEL varchar(12), OADDRESS varchar(255), OZIPCODE varchar(10), OEMAIL varchar(128), FAXNO varchar(14), MOBILENO varchar(14), QQ varchar(16), HTEL varchar(12), HADDRESS varchar(128), HZIPCODE varchar(10), PEMAIL varchar(128), PARTY varchar(255), DEGREE varchar(255), MAJOR numeric(10,0), SPECIALTY varchar(1024), WORKEXP varchar(512), REGDATE date, CREATETIME datetime not null, LASTMODYTIME datetime not null, ORGIDLIST varchar(128), ORGID numeric(10,0), REMARK varchar(512), TENANT_ID varchar(64) not null, APP_ID varchar(64), WEIBO varchar(255), primary key (EMPID));
create table org_emporg (ORGID numeric(10,0) not null, EMPID numeric(10,0) not null, ISMAIN varchar(1), TENANT_ID varchar(64) not null, APP_ID varchar(64), primary key (ORGID, EMPID));
create table org_empposition (EMPID numeric(10,0) not null, POSITIONID numeric(10,0) not null, ISMAIN varchar(1), TENANT_ID varchar(64) not null, APP_ID varchar(64), primary key (EMPID, POSITIONID));
create table org_organization (ORGID numeric(10,0) not null, ORGCODE varchar(32) not null, ORGNAME varchar(64), ORGLEVEL numeric(2,0) default 1, ORGDEGREE varchar(255), ORGSEQ varchar(512), ORGTYPE varchar(12), ORGADDR varchar(256), ZIPCODE varchar(10), MANAPOSITION numeric(10,0), MANAGERID numeric(10,0), ORGMANAGER varchar(128), LINKMAN varchar(30), LINKTEL varchar(20), EMAIL varchar(128), WEBURL varchar(512), STARTDATE date, ENDDATE date, STATUS varchar(255), AREA varchar(30), CREATETIME datetime not null, LASTUPDATE datetime not null, UPDATOR numeric(10,0), SORTNO integer, ISLEAF varchar(1), SUBCOUNT numeric(10,0), REMARK varchar(512), TENANT_ID varchar(64) not null, APP_ID varchar(64), PARENTORGID numeric(10,0), primary key (ORGID));
create table org_position (POSITIONID numeric(10,0) not null, POSICODE varchar(20), POSINAME varchar(128) not null, POSILEVEL numeric(2,0), POSITIONSEQ varchar(512) not null, POSITYPE varchar(255), CREATETIME datetime not null, LASTUPDATE datetime not null, UPDATOR numeric(10,0), STARTDATE date, ENDDATE date, STATUS varchar(255), ISLEAF varchar(1), SUBCOUNT numeric(10,0), TENANT_ID varchar(64) not null, APP_ID varchar(64), DUTYID numeric(10,0), ORGID numeric(10,0), MANAPOSI numeric(10,0), primary key (POSITIONID));
alter table app_funcgroup add index FK_F_APP_FUNCTION (APPID), add constraint FK_F_APP_FUNCTION foreign key (APPID) references app_application (APPID);
alter table app_funcgroup add index FK_F_FUNG_FUNG (PARENTGROUP), add constraint FK_F_FUNG_FUNG foreign key (PARENTGROUP) references app_funcgroup (FUNCGROUPID);
alter table app_funcresource add index FK_F_FUN_RES (FUNCCODE), add constraint FK_F_FUN_RES foreign key (FUNCCODE) references app_function (FUNCCODE);
alter table app_function add index FK_F_FUNGROUP_FUN (FUNCGROUPID), add constraint FK_F_FUNGROUP_FUN foreign key (FUNCGROUPID) references app_funcgroup (FUNCGROUPID);
alter table app_menu add index FK_F_MENU_MENU (PARENTSID), add constraint FK_F_MENU_MENU foreign key (PARENTSID) references app_menu (MENUID);
alter table cap_partyauth add index CapPartyauth_CapRole (ROLE_ID), add constraint CapPartyauth_CapRole foreign key (ROLE_ID) references CAP_ROLE (ROLE_ID);
alter table org_emporg add index OrgEmporg_OrgEmployee (EMPID), add constraint OrgEmporg_OrgEmployee foreign key (EMPID) references org_employee (EMPID);
alter table org_empposition add index OrgEmpposition_OrgEmployee (EMPID), add constraint OrgEmpposition_OrgEmployee foreign key (EMPID) references org_employee (EMPID);
alter table org_empposition add index OrgEmpposition_OrgPosition (POSITIONID), add constraint OrgEmpposition_OrgPosition foreign key (POSITIONID) references org_position (POSITIONID);
alter table org_organization add index OrgOrganization_OrgOrganization (PARENTORGID), add constraint OrgOrganization_OrgOrganization foreign key (PARENTORGID) references org_organization (ORGID);
alter table org_position add index OrgPosition_OrgOrganization (ORGID), add constraint OrgPosition_OrgOrganization foreign key (ORGID) references org_organization (ORGID);
alter table org_position add index OrgPosition_OrgDuty (DUTYID), add constraint OrgPosition_OrgDuty foreign key (DUTYID) references org_duty (DUTYID);
alter table org_position add index OrgPosition_OrgPosition (MANAPOSI), add constraint OrgPosition_OrgPosition foreign key (MANAPOSI) references org_position (POSITIONID);


/*==============================================================*/
/* Table: CAP_RULEAUTH                                          */
/*==============================================================*/
drop table if exists CAP_RULEAUTH;
create table CAP_RULEAUTH  (
   RULEAUTH_ID          VARCHAR(64)                    not null,
   TENANT_ID            VARCHAR(64)                    not null,
   RULE_ID              VARCHAR(64)                    not null,
   RES_ID               VARCHAR(255)                   not null,
   RES_TYPE             VARCHAR(64)                    not null,
   RES_STATE            VARCHAR(512)                   not null,
   CREATEUSER           VARCHAR(64),
   CREATETIME           DATETIME,
   constraint PK_CAP_RULEAUTH primary key (RULEAUTH_ID)
);
create index IDX_CAP_RULEAUTH_RULE_ID on CAP_RULEAUTH(RULE_ID);
