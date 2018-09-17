drop table CAP_RESAUTH;
drop table cap_partyauth;
drop table CAP_ROLE;
drop table app_menu;
drop table app_funcresource;
drop table app_function;
drop table app_funcgroup;
drop table app_application;
drop table cap_rule;
drop table cap_user;
drop table org_emporg;
drop table org_empposition;
drop table org_employee;
drop table org_position;
drop table org_organization;
drop table org_duty;
create table CAP_RESAUTH (PARTY_ID varchar(64) not null, PARTY_TYPE varchar(64) not null, RES_ID varchar(255) not null, RES_TYPE varchar(64) not null, TENANT_ID varchar(64) null, RES_STATE varchar(512) not null, PARTY_SCOPE varchar(1) default '0' null, CREATEUSER varchar(64) null, CREATETIME datetime null, primary key (PARTY_ID, PARTY_TYPE, RES_ID, RES_TYPE));
create table CAP_ROLE (ROLE_ID varchar(64) not null, TENANT_ID varchar(64) not null, ROLE_CODE varchar(64) not null, ROLE_NAME varchar(64) null, ROLE_DESC varchar(255) null, CREATEUSER varchar(64) null, CREATETIME datetime null, primary key (ROLE_ID));
create table app_application (APPID numeric(10,0) not null, APPCODE varchar(32) null, APPNAME varchar(50) null, APPTYPE varchar(255) null, ISOPEN varchar(1) null, OPENDATE datetime null, URL varchar(256) null, APPDESC varchar(512) null, MAINTENANCE numeric(10,0) null, MANAROLE varchar(64) null, DEMO varchar(512) null, INIWP varchar(1) null, INTASKCENTER varchar(1) null, IPADDR varchar(50) null, IPPORT varchar(10) null, APP_ID varchar(64) null, TENANT_ID varchar(64) not null, protocol_type varchar(64) null, primary key (APPID));
create table app_funcgroup (FUNCGROUPID numeric(10,0) not null, FUNCGROUPNAME varchar(40) null, GROUPLEVEL int null, FUNCGROUPSEQ varchar(256) null, ISLEAF varchar(1) null, SUBCOUNT numeric(10,0) null, APP_ID varchar(64) null, TENANT_ID varchar(64) not null, PARENTGROUP numeric(10,0) null, APPID numeric(10,0) not null, primary key (FUNCGROUPID));
create table app_funcresource (RESID numeric(10,0) not null, RESTYPE varchar(255) null, RESPATH varchar(256) null, COMPACKNAME varchar(40) null, RESNAME varchar(40) null, APP_ID varchar(64) null, TENANT_ID varchar(64) not null, FUNCCODE varchar(255) null, primary key (RESID));
create table app_function (FUNCCODE varchar(255) not null, FUNCNAME varchar(128) not null, FUNCDESC varchar(512) null, FUNCACTION varchar(256) null, PARAINFO varchar(256) null, ISCHECK varchar(1) null, FUNCTYPE varchar(255) default '1' null, ISMENU varchar(1) null, APP_ID varchar(64) null, TENANT_ID varchar(64) not null, FUNCGROUPID numeric(10,0) null, primary key (FUNCCODE));
create table app_menu (MENUID varchar(40) not null, MENUNAME varchar(40) not null, MENULABEL varchar(40) not null, MENUCODE varchar(40) null, ISLEAF varchar(1) null, PARAMETER varchar(256) null, UIENTRY varchar(256) null, MENULEVEL smallint null, ROOTID varchar(40) null, DISPLAYORDER smallint null, IMAGEPATH varchar(100) null, EXPANDPATH varchar(100) null, MENUSEQ varchar(256) null, OPENMODE varchar(255) null, SUBCOUNT numeric(10,0) null, APPID numeric(10,0) null, FUNCCODE varchar(255) null, APP_ID varchar(64) null, TENANT_ID varchar(64) not null, PARENTSID varchar(40) null, primary key (MENUID));
create table cap_partyauth (ROLE_TYPE varchar(64) not null, PARTY_ID varchar(64) not null, PARTY_TYPE varchar(64) not null, ROLE_ID varchar(64) not null, TENANT_ID varchar(64) not null, CREATEUSER varchar(64) null, CREATETIME datetime not null, primary key (ROLE_TYPE, PARTY_ID, PARTY_TYPE, ROLE_ID));
create table cap_rule (RULE_ID varchar(64) not null, RULE_NAME varchar(64) , TENANT_ID varchar(64) , RULE_TYPE varchar(64) , NAMESPACE varchar(512) , RULE_EXPRESSION image null, CREATEUSER varchar(64) null, CREATETIME datetime null, primary key (RULE_ID));
create table cap_user (OPERATOR_ID numeric(18,0) not null, TENANT_ID varchar(64) not null, USER_ID varchar(64) not null, PASSWORD varchar(100) null, INVALDATE datetime null, USER_NAME varchar(64) null, AUTHMODE varchar(255) null, STATUS varchar(16) null, UNLOCKTIME datetime not null, MENUTYPE varchar(255) null, LASTLOGIN datetime not null, ERRCOUNT numeric(10,0) null, STARTDATE datetime null, ENDDATE datetime null, VALIDTIME varchar(255) null, MACCODE varchar(128) null, IPADDRESS varchar(128) null, EMAIL varchar(255) null, CREATEUSER varchar(64) null, CREATETIME datetime not null, primary key (OPERATOR_ID));
create table org_duty (DUTYID numeric(10,0) not null, DUTYCODE varchar(20) null, DUTYNAME varchar(30) null, PARENTDUTY numeric(10,0) null, DUTYLEVEL int null, DUTYSEQ varchar(256) null, DUTYTYPE varchar(255) null, ISLEAF varchar(10) null, SUBCOUNT numeric(10,0) null, REMARK varchar(256) null, TENANT_ID varchar(64) not null, APP_ID varchar(64) null, primary key (DUTYID));
create table org_employee (EMPID numeric(10,0) not null, EMPCODE varchar(30) null, OPERATORID numeric(18,0) null, USERID varchar(30) null, EMPNAME varchar(50) null, REALNAME varchar(50) null, GENDER varchar(255) null, BIRTHDATE datetime null, POSITION numeric(10,0) null, EMPSTATUS varchar(255) null, CARDTYPE varchar(255) null, CARDNO varchar(20) null, INDATE datetime null, OUTDATE datetime null, OTEL varchar(12) null, OADDRESS varchar(255) null, OZIPCODE varchar(10) null, OEMAIL varchar(128) null, FAXNO varchar(14) null, MOBILENO varchar(14) null, QQ varchar(16) null, HTEL varchar(12) null, HADDRESS varchar(128) null, HZIPCODE varchar(10) null, PEMAIL varchar(128) null, PARTY varchar(255) null, DEGREE varchar(255) null, MAJOR numeric(10,0) null, SPECIALTY varchar(1024) null, WORKEXP varchar(512) null, REGDATE datetime null, CREATETIME datetime not null, LASTMODYTIME datetime not null, ORGIDLIST varchar(128) null, ORGID numeric(10,0) null, REMARK varchar(512) null, TENANT_ID varchar(64) not null, APP_ID varchar(64) null, WEIBO varchar(255) null, primary key (EMPID));
create table org_emporg (ORGID numeric(10,0) not null, EMPID numeric(10,0) not null, ISMAIN varchar(1) null, TENANT_ID varchar(64) not null, APP_ID varchar(64) null, primary key (ORGID, EMPID));
create table org_empposition (EMPID numeric(10,0) not null, POSITIONID numeric(10,0) not null, ISMAIN varchar(1) null, TENANT_ID varchar(64) not null, APP_ID varchar(64) null, primary key (EMPID, POSITIONID));
create table org_organization (ORGID numeric(10,0) not null, ORGCODE varchar(32) not null, ORGNAME varchar(64) null, ORGLEVEL numeric(2,0) default 1 null, ORGDEGREE varchar(255) null, ORGSEQ varchar(512) null, ORGTYPE varchar(12) null, ORGADDR varchar(256) null, ZIPCODE varchar(10) null, MANAPOSITION numeric(10,0) null, MANAGERID numeric(10,0) null, ORGMANAGER varchar(128) null, LINKMAN varchar(30) null, LINKTEL varchar(20) null, EMAIL varchar(128) null, WEBURL varchar(512) null, STARTDATE datetime null, ENDDATE datetime null, STATUS varchar(255) null, AREA varchar(30) null, CREATETIME datetime not null, LASTUPDATE datetime not null, UPDATOR numeric(10,0) null, SORTNO int null, ISLEAF varchar(1) null, SUBCOUNT numeric(10,0) null, REMARK varchar(512) null, TENANT_ID varchar(64) not null, APP_ID varchar(64) null, PARENTORGID numeric(10,0) null, primary key (ORGID));
create table org_position (POSITIONID numeric(10,0) not null, POSICODE varchar(20) null, POSINAME varchar(128) not null, POSILEVEL numeric(2,0) null, POSITIONSEQ varchar(512) not null, POSITYPE varchar(255) null, CREATETIME datetime not null, LASTUPDATE datetime not null, UPDATOR numeric(10,0) null, STARTDATE datetime null, ENDDATE datetime null, STATUS varchar(255) null, ISLEAF varchar(1) null, SUBCOUNT numeric(10,0) null, TENANT_ID varchar(64) not null, APP_ID varchar(64) null, DUTYID numeric(10,0) null, ORGID numeric(10,0) null, MANAPOSI numeric(10,0) null, primary key (POSITIONID));
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
drop table CAP_RULEAUTH;
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
