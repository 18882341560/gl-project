drop table if exists sys_user;

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
  id          BIGINT(20) not null auto_increment comment 'ID',
  ad_account  NVARCHAR(191) comment 'AD域登录名',
  email       NVARCHAR(191) comment '邮箱',
  mobile      NVARCHAR(191) comment '手机号',
  password    NVARCHAR(191) comment '密码',
  status      INT(1)   default 1 comment '状态：1.正常；2.禁用',
  create_time DATETIME default CURRENT_TIMESTAMP comment '创建时间',
  update_time DATETIME default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
  user_id     NVARCHAR(191) comment '用户ID',
  username    NVARCHAR(191) comment '用户名',
  corp_id     BIGINT(20) comment '单位ID',
  headship    NVARCHAR(191) comment '职务',
  enabled     TINYINT(1) comment '是否启用：1.启用；0.停用',
  memo        NVARCHAR(255) comment '备注',
  is_admin    TINYINT(1) comment '是否是超级管理员:1.是；2.否',
  level       NVARCHAR(191) comment '级别（处级、副处级、科级、副科级、股级、一般员工）',
  work_time   NVARCHAR(191) comment '参公时间',
  sex         NVARCHAR(191) comment '性别',
  corp_name   NVARCHAR(191) comment '单位名称',
  primary key (id)
);

/*==============================================================*/
/* Index: ad_account_unique                                     */
/*==============================================================*/
create unique index ad_account_unique on sys_user
  (
   ad_account
    );

/*==============================================================*/
/* Index: email_unique                                          */
/*==============================================================*/
create unique index email_unique on sys_user
  (
   email
    );

/*==============================================================*/
/* Index: mobile_unique                                         */
/*==============================================================*/
create unique index mobile_unique on sys_user
  (
   mobile
    );

/*==============================================================*/
/* Index: status_index                                          */
/*==============================================================*/
create index status_index on sys_user
  (
   status
    );

/*==============================================================*/
/* Index: userid_unique                                         */
/*==============================================================*/
create unique index userid_unique on sys_user
  (
   user_id
    );

/*==============================================================*/
/* Index: corp_id_index                                         */
/*==============================================================*/
create index corp_id_index on sys_user
  (
   corp_id
    );

/*==============================================================*/
/* Index: username_index                                        */
/*==============================================================*/
create index username_index on sys_user
  (
   username
    );



drop table if exists sys_role;

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
  id          BIGINT(20) not null auto_increment comment 'ID',
  name        NVARCHAR(191) comment '角色名称',
  title       NVARCHAR(255) comment '角色标题',
  type        INT(1)   default 1 comment '角色类型：1.系统角色（无法删除）；2.用户自定义角色',
  status      INT(1)   default 1 comment '角色状态：1.正常；2.停用',
  create_time DATETIME default CURRENT_TIMESTAMP comment '创建时间',
  update_time DATETIME default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  primary key (id)
);

/*==============================================================*/
/* Index: role_name                                             */
/*==============================================================*/
create index role_name on sys_role
  (
   name
    );

/*==============================================================*/
/* Index: role_type                                             */
/*==============================================================*/
create index role_type on sys_role
  (
   type
    );

/*==============================================================*/
/* Index: role_status                                           */
/*==============================================================*/
create index role_status on sys_role
  (
   status
    );

/*==============================================================*/
/* Index: role_title                                            */
/*==============================================================*/
create index role_title on sys_role
  (
   title
    );

drop table if exists sys_user_role_mid;

/*==============================================================*/
/* Table: sys_user_role_mid                                     */
/*==============================================================*/
create table sys_user_role_mid
(
  user_id     BIGINT(20) not null comment '用户ID',
  role_id     BIGINT(20) not null comment '角色ID',
  create_time DATETIME default CURRENT_TIMESTAMP comment '创建时间'
);

/*==============================================================*/
/* Index: user_id_role_id_unique                                */
/*==============================================================*/
create unique index user_id_role_id_unique on sys_user_role_mid
  (
   user_id,
   role_id
    );



drop table if exists sys_resource;

/*==============================================================*/
/* Table: sys_resource                                          */
/*==============================================================*/
create table sys_resource
(
  id          BIGINT(20) not null auto_increment comment 'ID',
  pid         BIGINT(20)    default 0 comment '父级资源ID',
  p_title     NVARCHAR(191) default '首页' comment '父级资源标题',
  name        NVARCHAR(191) default '' comment '资源名称',
  description NVARCHAR(255) default '' comment '资源描述',
  url         NVARCHAR(191) default '' comment '资源访问路径',
  type        INT(1)        default 1 comment '资源类型：1.菜单；2.按钮',
  sys_type    INT(1)        default 2 comment '系统资源类型：1.系统资源（无法删除）；2.自定义资源',
  seq         INT(2)        default 1 comment '资源排序',
  status      INT(1)        default 1 comment '资源状态：1.正常；2.停用',
  create_time DATETIME      default CURRENT_TIMESTAMP comment '创建时间',
  update_time DATETIME      default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  permission  NVARCHAR(191) default '' comment '资源权限',
  icon        NVARCHAR(191) default 'el-icon-shiyongwendang' comment '资源图标',
  title       NVARCHAR(255) comment '资源标题',
  primary key (id)
);

/*==============================================================*/
/* Index: pid_index                                             */
/*==============================================================*/
create index pid_index on sys_resource
  (
   pid
    );

/*==============================================================*/
/* Index: url_index                                             */
/*==============================================================*/
create index url_index on sys_resource
  (
   url
    );

/*==============================================================*/
/* Index: type_index                                            */
/*==============================================================*/
create index type_index on sys_resource
  (
   type
    );

/*==============================================================*/
/* Index: seq_index                                             */
/*==============================================================*/
create index seq_index on sys_resource
  (
   seq
    );

/*==============================================================*/
/* Index: status_index                                          */
/*==============================================================*/
create index status_index on sys_resource
  (
   status
    );

/*==============================================================*/
/* Index: permission_index                                      */
/*==============================================================*/
create index permission_index on sys_resource
  (
   permission
    );


drop table if exists sys_role_resource_mid;

/*==============================================================*/
/* Table: sys_role_resource_mid                                 */
/*==============================================================*/
create table sys_role_resource_mid
(
  role_id     BIGINT(20) not null comment '角色ID',
  resource_id BIGINT(20) not null comment '资源ID',
  create_time DATETIME default CURRENT_TIMESTAMP comment '创建时间'
);

/*==============================================================*/
/* Index: role_id_resource_id_unique                            */
/*==============================================================*/
create unique index role_id_resource_id_unique on sys_role_resource_mid
  (
   role_id,
   resource_id
    );

drop table if exists sys_organization;

/*==============================================================*/
/* Table: sys_organization                                      */
/*==============================================================*/
create table sys_organization
(
  id             BIGINT(20) not null auto_increment comment '机构ID',
  corp_id        INT(11) comment '单位ID',
  corp_name      NVARCHAR(191) comment '机构名称',
  parent_corp_id INT(11) comment '父级单位ID',
  seq            INT(11) comment '排序',
  corp_manager   NVARCHAR(191) comment '单位负责人',
  create_time    DATETIME default CURRENT_TIMESTAMP comment '创建时间',
  update_time    DATETIME default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
  primary key (id)
);

/*==============================================================*/
/* Index: corp_id_unique                                        */
/*==============================================================*/
create unique index corp_id_unique on sys_organization
  (
   corp_id
    );

/*==============================================================*/
/* Index: parent_corp_id_index                                  */
/*==============================================================*/
create index parent_corp_id_index on sys_organization
  (
   parent_corp_id
    );

drop table if exists sys_user_organization_permission_mid;

/*==============================================================*/
/* Table: sys_user_organization_permission_mid                  */
/*==============================================================*/
create table sys_user_organization_permission_mid
(
  user_id          BIGINT(20) comment '系统用户表内自增id',
  organization_id BIGINT(20) comment '系统机构表内自增ID',
  create_time     DATETIME default CURRENT_TIMESTAMP comment '创建时间'
);

alter table sys_user_organization_permission_mid
  comment '系统用户与机构权限关联表,一个用户对应多个机构权限';

/*==============================================================*/
/* Index: userid_organization_id_unique                         */
/*==============================================================*/
create unique index userid_organization_id_unique on sys_user_organization_permission_mid
  (
   user_id,
   organization_id
    );
