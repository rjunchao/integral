drop table CAP_RESAUTH cascade constraints;
drop table cap_partyauth cascade constraints;
drop table CAP_ROLE cascade constraints;
drop table app_menu cascade constraints;
drop table app_funcresource cascade constraints;
drop table app_function cascade constraints;
drop table app_funcgroup cascade constraints;
drop table app_application cascade constraints;
drop table cap_rule cascade constraints;
drop table cap_user cascade constraints;
drop table org_empposition cascade constraints;
drop table org_emporg cascade constraints;
drop table org_employee cascade constraints;
drop table org_position cascade constraints;
drop table org_organization cascade constraints;
drop table org_duty cascade constraints;
create table CAP_RESAUTH (PARTY_ID varchar2(64) not null, PARTY_TYPE varchar2(64) not null, RES_ID varchar2(255) not null, RES_TYPE varchar2(64) not null, TENANT_ID varchar2(64), RES_STATE varchar2(512) not null, PARTY_SCOPE varchar2(1) default '0', CREATEUSER varchar2(64), CREATETIME timestamp, primary key (PARTY_ID, PARTY_TYPE, RES_ID, RES_TYPE));
create table CAP_ROLE (ROLE_ID varchar2(64) not null, TENANT_ID varchar2(64) not null, ROLE_CODE varchar2(64) not null, ROLE_NAME varchar2(64), ROLE_DESC varchar2(255), CREATEUSER varchar2(64), CREATETIME timestamp, primary key (ROLE_ID));
create table app_application (APPID number(10,0) not null, APPCODE varchar2(32), APPNAME varchar2(50), APPTYPE varchar2(255), ISOPEN varchar2(1), OPENDATE date, URL varchar2(256), APPDESC varchar2(512), MAINTENANCE number(10,0), MANAROLE varchar2(64), DEMO varchar2(512), INIWP varchar2(1), INTASKCENTER varchar2(1), IPADDR varchar2(50), IPPORT varchar2(10), APP_ID varchar2(64), TENANT_ID varchar2(64) not null, protocol_type varchar2(64), primary key (APPID));
create table app_funcgroup (FUNCGROUPID number(10,0) not null, FUNCGROUPNAME varchar2(40), GROUPLEVEL number(10,0), FUNCGROUPSEQ varchar2(256), ISLEAF varchar2(1), SUBCOUNT number(10,0), APP_ID varchar2(64), TENANT_ID varchar2(64) not null, PARENTGROUP number(10,0), APPID number(10,0) not null, primary key (FUNCGROUPID));
create table app_funcresource (RESID number(10,0) not null, RESTYPE varchar2(255), RESPATH varchar2(256), COMPACKNAME varchar2(40), RESNAME varchar2(40), APP_ID varchar2(64), TENANT_ID varchar2(64) not null, FUNCCODE varchar2(255), primary key (RESID));
create table app_function (FUNCCODE varchar2(255) not null, FUNCNAME varchar2(128) not null, FUNCDESC varchar2(512), FUNCACTION varchar2(256), PARAINFO varchar2(256), ISCHECK varchar2(1), FUNCTYPE varchar2(255) default '1', ISMENU varchar2(1), APP_ID varchar2(64), TENANT_ID varchar2(64) not null, FUNCGROUPID number(10,0), primary key (FUNCCODE));
create table app_menu (MENUID varchar2(40) not null, MENUNAME varchar2(40) not null, MENULABEL varchar2(40) not null, MENUCODE varchar2(40), ISLEAF varchar2(1), PARAMETER varchar2(256), UIENTRY varchar2(256), MENULEVEL number(5,0), ROOTID varchar2(40), DISPLAYORDER number(5,0), IMAGEPATH varchar2(100), EXPANDPATH varchar2(100), MENUSEQ varchar2(256), OPENMODE varchar2(255), SUBCOUNT number(10,0), APPID number(10,0), FUNCCODE varchar2(255), APP_ID varchar2(64), TENANT_ID varchar2(64) not null, PARENTSID varchar2(40), primary key (MENUID));
create table cap_partyauth (ROLE_TYPE varchar2(64) not null, PARTY_ID varchar2(64) not null, PARTY_TYPE varchar2(64) not null, ROLE_ID varchar2(64) not null, TENANT_ID varchar2(64) not null, CREATEUSER varchar2(64), CREATETIME timestamp not null, primary key (ROLE_TYPE, PARTY_ID, PARTY_TYPE, ROLE_ID));
create table cap_rule (RULE_ID varchar2(64) not null, RULE_NAME varchar2(64) , TENANT_ID varchar2(64) , RULE_TYPE varchar2(64) , NAMESPACE varchar2(512) , RULE_EXPRESSION blob, CREATEUSER varchar2(64), CREATETIME date, primary key (RULE_ID));
create table cap_user (OPERATOR_ID number(18,0) not null, TENANT_ID varchar2(64) not null, USER_ID varchar2(64) not null, PASSWORD varchar2(100), INVALDATE date, USER_NAME varchar2(64), AUTHMODE varchar2(255), STATUS varchar2(16), UNLOCKTIME timestamp not null, MENUTYPE varchar2(255), LASTLOGIN timestamp not null, ERRCOUNT number(10,0), STARTDATE date, ENDDATE date, VALIDTIME varchar2(255), MACCODE varchar2(128), IPADDRESS varchar2(128), EMAIL varchar2(255), CREATEUSER varchar2(64), CREATETIME timestamp not null, primary key (OPERATOR_ID));
create table org_duty (DUTYID number(10,0) not null, DUTYCODE varchar2(20), DUTYNAME varchar2(30), PARENTDUTY number(10,0), DUTYLEVEL number(10,0), DUTYSEQ varchar2(256), DUTYTYPE varchar2(255), ISLEAF varchar2(10), SUBCOUNT number(10,0), REMARK varchar2(256), TENANT_ID varchar2(64) not null, APP_ID varchar2(64), primary key (DUTYID));
create table org_employee (EMPID number(10,0) not null, EMPCODE varchar2(30), OPERATORID number(18,0), USERID varchar2(30), EMPNAME varchar2(50), REALNAME varchar2(50), GENDER varchar2(255), BIRTHDATE date, POSITION number(10,0), EMPSTATUS varchar2(255), CARDTYPE varchar2(255), CARDNO varchar2(20), INDATE date, OUTDATE date, OTEL varchar2(12), OADDRESS varchar2(255), OZIPCODE varchar2(10), OEMAIL varchar2(128), FAXNO varchar2(14), MOBILENO varchar2(14), QQ varchar2(16), HTEL varchar2(12), HADDRESS varchar2(128), HZIPCODE varchar2(10), PEMAIL varchar2(128), PARTY varchar2(255), DEGREE varchar2(255), MAJOR number(10,0), SPECIALTY varchar2(1024), WORKEXP varchar2(512), REGDATE date, CREATETIME timestamp not null, LASTMODYTIME timestamp not null, ORGIDLIST varchar2(128), ORGID number(10,0), REMARK varchar2(512), TENANT_ID varchar2(64) not null, APP_ID varchar2(64), WEIBO varchar2(255), primary key (EMPID));
create table org_emporg (ORGID number(10,0) not null, EMPID number(10,0) not null, ISMAIN varchar2(1), TENANT_ID varchar2(64) not null, APP_ID varchar2(64), primary key (ORGID, EMPID));
create table org_empposition (EMPID number(10,0) not null, POSITIONID number(10,0) not null, ISMAIN varchar2(1), TENANT_ID varchar2(64) not null, APP_ID varchar2(64), primary key (EMPID, POSITIONID));
create table org_organization (ORGID number(10,0) not null, ORGCODE varchar2(32) not null, ORGNAME varchar2(64), ORGLEVEL number(2,0) default 1, ORGDEGREE varchar2(255), ORGSEQ varchar2(512), ORGTYPE varchar2(12), ORGADDR varchar2(256), ZIPCODE varchar2(10), MANAPOSITION number(10,0), MANAGERID number(10,0), ORGMANAGER varchar2(128), LINKMAN varchar2(30), LINKTEL varchar2(20), EMAIL varchar2(128), WEBURL varchar2(512), STARTDATE date, ENDDATE date, STATUS varchar2(255), AREA varchar2(30), CREATETIME timestamp not null, LASTUPDATE timestamp not null, UPDATOR number(10,0), SORTNO number(10,0), ISLEAF varchar2(1), SUBCOUNT number(10,0), REMARK varchar2(512), TENANT_ID varchar2(64) not null, APP_ID varchar2(64), PARENTORGID number(10,0), primary key (ORGID));
create table org_position (POSITIONID number(10,0) not null, POSICODE varchar2(20), POSINAME varchar2(128) not null, POSILEVEL number(2,0), POSITIONSEQ varchar2(512) not null, POSITYPE varchar2(255), CREATETIME timestamp not null, LASTUPDATE timestamp not null, UPDATOR number(10,0), STARTDATE date, ENDDATE date, STATUS varchar2(255), ISLEAF varchar2(1), SUBCOUNT number(10,0), TENANT_ID varchar2(64) not null, APP_ID varchar2(64), DUTYID number(10,0), ORGID number(10,0), MANAPOSI number(10,0), primary key (POSITIONID));
alter table app_funcgroup add constraint FK_F_APP_FUNCTION foreign key (APPID) references app_application;
alter table app_funcgroup add constraint FK_F_FUNG_FUNG foreign key (PARENTGROUP) references app_funcgroup;
alter table app_funcresource add constraint FK_F_FUN_RES foreign key (FUNCCODE) references app_function;
alter table app_function add constraint FK_F_FUNGROUP_FUN foreign key (FUNCGROUPID) references app_funcgroup;
alter table app_menu add constraint FK_F_MENU_MENU foreign key (PARENTSID) references app_menu;
alter table cap_partyauth add constraint CapPartyauth_CapRole foreign key (ROLE_ID) references CAP_ROLE;
alter table org_emporg add constraint OrgEmporg_OrgEmployee foreign key (EMPID) references org_employee;
alter table org_empposition add constraint OrgEmpposition_OrgEmployee foreign key (EMPID) references org_employee;
alter table org_empposition add constraint OrgEmpposition_OrgPosition foreign key (POSITIONID) references org_position;
alter table org_organization add constraint OrgOrganization_Org foreign key (PARENTORGID) references org_organization;
alter table org_position add constraint OrgPosition_OrgOrganization foreign key (ORGID) references org_organization;
alter table org_position add constraint OrgPosition_OrgDuty foreign key (DUTYID) references org_duty;
alter table org_position add constraint OrgPosition_OrgPosition foreign key (MANAPOSI) references org_position;

/*==============================================================*/
/* Table: CAP_RULEAUTH                                          */
/*==============================================================*/
drop table CAP_RULEAUTH cascade constraints;
create table CAP_RULEAUTH  (
   RULEAUTH_ID          VARCHAR2(64)                    not null,
   TENANT_ID            VARCHAR2(64)                    not null,
   RULE_ID              VARCHAR2(64)                    not null,
   RES_ID               VARCHAR2(255)                   not null,
   RES_TYPE             VARCHAR2(64)                    not null,
   RES_STATE            VARCHAR2(512)                   not null,
   CREATEUSER           VARCHAR2(64),
   CREATETIME           TIMESTAMP,
   constraint PK_CAP_RULEAUTH primary key (RULEAUTH_ID)
);
create index IDX_CAP_RULEAUTH_RULE_ID on CAP_RULEAUTH(RULE_ID);
