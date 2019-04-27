if exists (select 1
           from  sysobjects
           where  id = object_id('sys_user')
             and   type = 'U')
    drop table sys_user
go

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user (
                          id                   bigint               identity,
                          ad_account           VARCHAR(191)         null,
                          email                VARCHAR(191)         null,
                          mobile               VARCHAR(191)         null,
                          password             VARCHAR(191)         null,
                          status               INT                  null default 1,
                          create_time          datetime             null default getdate(),
                          update_time          datetime             null default getdate(),
                          user_id              VARCHAR(191)         null,
                          username             VARCHAR(191)         null,
                          corp_id              bigint               null,
                          headship             VARCHAR(191)         null,
                          enabled              TINYINT              null,
                          memo                 VARCHAR(255)         null,
                          is_admin             TINYINT              null,
                          level                VARCHAR(191)         null,
                          work_time            VARCHAR(191)         null,
                          sex                  VARCHAR(191)         null,
                          corp_name            VARCHAR(191)         null,
                          constraint PK_SYS_USER primary key nonclustered (id)
)
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'ID',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ad_account')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'ad_account'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'AD域登录名',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'ad_account'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'email')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'email'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '邮箱',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'email'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'mobile')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'mobile'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '手机号',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'mobile'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'password')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'password'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '密码',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'password'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'status')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'status'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '状态：1.正常；2.禁用',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'status'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'create_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'update_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'update_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '修改时间',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'update_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'user_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'user_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '用户ID',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'user_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'username')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'username'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '用户名',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'username'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'corp_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'corp_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '单位ID',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'corp_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'headship')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'headship'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '职务',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'headship'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'enabled')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'enabled'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '是否启用：1.启用；0.停用',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'enabled'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'memo')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'memo'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '备注',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'memo'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'is_admin')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'is_admin'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '是否是超级管理员:1.是；2.否',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'is_admin'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'level')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'level'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '级别（处级、副处级、科级、副科级、股级、一般员工）',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'level'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'work_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'work_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '参公时间',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'work_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'sex')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'sex'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '性别',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'sex'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'corp_name')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user', 'column', 'corp_name'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '单位名称',
        'user', @CurrentUser, 'table', 'sys_user', 'column', 'corp_name'
go

/*==============================================================*/
/* Index: ad_account_unique                                     */
/*==============================================================*/
create unique index ad_account_unique on sys_user (
                                                   ad_account ASC
    )
go

/*==============================================================*/
/* Index: email_unique                                          */
/*==============================================================*/
create unique index email_unique on sys_user (
                                              email ASC
    )
go

/*==============================================================*/
/* Index: mobile_unique                                         */
/*==============================================================*/
create unique index mobile_unique on sys_user (
                                               mobile ASC
    )
go

/*==============================================================*/
/* Index: status_index                                          */
/*==============================================================*/
create index status_index on sys_user (
                                       status ASC
    )
go

/*==============================================================*/
/* Index: userid_unique                                         */
/*==============================================================*/
create unique index userid_unique on sys_user (
                                               user_id ASC
    )
go

/*==============================================================*/
/* Index: corp_id_index                                         */
/*==============================================================*/
create index corp_id_index on sys_user (
                                        corp_id ASC
    )
go

/*==============================================================*/
/* Index: username_index                                        */
/*==============================================================*/
create index username_index on sys_user (
                                         username ASC
    )
go


if exists (select 1
           from  sysobjects
           where  id = object_id('sys_user_role_mid')
             and   type = 'U')
    drop table sys_user_role_mid
go

/*==============================================================*/
/* Table: sys_user_role_mid                                     */
/*==============================================================*/
create table sys_user_role_mid (
                                   user_id              bigint               not null,
                                   role_id              bigint               not null,
                                   create_time          datetime             null default getdate(),
                                   constraint PK_SYS_USER_ROLE_MID primary key (user_id, role_id)
)
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user_role_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'user_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user_role_mid', 'column', 'user_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '用户表内自增ID',
        'user', @CurrentUser, 'table', 'sys_user_role_mid', 'column', 'user_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user_role_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'role_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user_role_mid', 'column', 'role_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色ID',
        'user', @CurrentUser, 'table', 'sys_user_role_mid', 'column', 'role_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_user_role_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_user_role_mid', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_user_role_mid', 'column', 'create_time'
go



if exists (select 1
           from  sysobjects
           where  id = object_id('sys_role')
             and   type = 'U')
    drop table sys_role
go

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role (
                          id                   bigint               identity,
                          name                 VARCHAR(191)         null,
                          title                VARCHAR(255)         null,
                          type                 INT                  null,
                          data_permission_type int                  null,
                          status               INT                  null,
                          create_time          datetime             null,
                          update_time          datetime             null,
                          constraint PK_SYS_ROLE primary key nonclustered (id)
)
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'ID',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'name')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'name'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色名称',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'name'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'title')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'title'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色标题',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'title'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'type')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'type'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色类型：1.系统角色（无法删除）；2.用户自定义角色',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'type'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'data_permission_type')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'data_permission_type'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '数据权限类型：1.全部；2.自己；3.自己或下属；4.指定关联列表',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'data_permission_type'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'status')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'status'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色状态：1.正常；2.停用',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'status'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'create_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'update_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role', 'column', 'update_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '更新时间',
        'user', @CurrentUser, 'table', 'sys_role', 'column', 'update_time'
go

/*==============================================================*/
/* Index: role_name                                             */
/*==============================================================*/
create index role_name on sys_role (
                                    name ASC
    )
go

/*==============================================================*/
/* Index: role_type                                             */
/*==============================================================*/
create index role_type on sys_role (
                                    type ASC
    )
go

/*==============================================================*/
/* Index: role_status                                           */
/*==============================================================*/
create index role_status on sys_role (
                                      status ASC
    )
go

/*==============================================================*/
/* Index: role_title                                            */
/*==============================================================*/
create index role_title on sys_role (
                                     title ASC
    )
go

if exists (select 1
           from  sysobjects
           where  id = object_id('sys_role_organization_data_permission_mid')
             and   type = 'U')
    drop table sys_role_organization_data_permission_mid
go

/*==============================================================*/
/* Table: sys_role_organization_data_permission_mid             */
/*==============================================================*/
create table sys_role_organization_data_permission_mid (
                                                           role_id              bigint               not null,
                                                           corp_id              bigint               not null,
                                                           create_time          datetime             null default getdate(),
                                                           constraint PK_SYS_ROLE_ORGANIZATION_DATA_ primary key (role_id, corp_id)
)
go

if exists (select 1 from  sys.extended_properties
           where major_id = object_id('sys_role_organization_data_permission_mid') and minor_id = 0)
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '若此角色的数据权限类型为“自己及下属”或“指定关联列表”，则需要用到此关联表获取权限范围',
        'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role_organization_data_permission_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'role_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid', 'column', 'role_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色ID',
        'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid', 'column', 'role_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role_organization_data_permission_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'corp_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid', 'column', 'corp_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '机构ID',
        'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid', 'column', 'corp_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role_organization_data_permission_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_role_organization_data_permission_mid', 'column', 'create_time'
go



if exists (select 1
           from  sysobjects
           where  id = object_id('sys_role_resource_mid')
             and   type = 'U')
    drop table sys_role_resource_mid
go

/*==============================================================*/
/* Table: sys_role_resource_mid                                 */
/*==============================================================*/
create table sys_role_resource_mid (
                                       role_id              bigint               not null,
                                       resource_id          bigint               not null,
                                       create_time          datetime             null,
                                       constraint PK_SYS_ROLE_RESOURCE_MID primary key (role_id, resource_id)
)
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role_resource_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'role_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_resource_mid', 'column', 'role_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '角色ID',
        'user', @CurrentUser, 'table', 'sys_role_resource_mid', 'column', 'role_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role_resource_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'resource_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_resource_mid', 'column', 'resource_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源ID',
        'user', @CurrentUser, 'table', 'sys_role_resource_mid', 'column', 'resource_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_role_resource_mid')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_role_resource_mid', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_role_resource_mid', 'column', 'create_time'
go

if exists (select 1
           from  sysobjects
           where  id = object_id('sys_resource')
             and   type = 'U')
    drop table sys_resource
go

/*==============================================================*/
/* Table: sys_resource                                          */
/*==============================================================*/
create table sys_resource (
                              id                   bigint               identity,
                              pid                  bigint               null default 0,
                              p_title              VARCHAR(191)         null default '首页',
                              name                 VARCHAR(191)         null default '',
                              description          VARCHAR(255)         null default '',
                              url                  VARCHAR(191)         null default '',
                              type                 INT                  null default 1,
                              sys_type             INT                  null default 2,
                              seq                  INT                  null default 1,
                              status               INT                  null default 1,
                              create_time          datetime             null default getdate(),
                              update_time          datetime             null default getdate(),
                              permission           VARCHAR(191)         null default '',
                              icon                 VARCHAR(191)         null default 'el-icon-shiyongwendang',
                              title                VARCHAR(255)         null,
                              constraint PK_SYS_RESOURCE primary key nonclustered (id)
)
go


if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'ID',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'pid')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'pid'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '父级资源ID',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'pid'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'p_title')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'p_title'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '父级资源标题',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'p_title'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'name')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'name'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源名称',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'name'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'description')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'description'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源描述',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'description'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'url')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'url'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源访问路径',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'url'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'type')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'type'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源类型：1.菜单；2.按钮',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'type'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'sys_type')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'sys_type'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '系统资源类型：1.系统资源（无法删除）；2.自定义资源',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'sys_type'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'seq')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'seq'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源排序',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'seq'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'status')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'status'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源状态：1.正常；2.停用',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'status'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'create_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'update_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'update_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '更新时间',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'update_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'permission')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'permission'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源权限',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'permission'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'icon')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'icon'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源图标',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'icon'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_resource')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'title')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_resource', 'column', 'title'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '资源标题',
        'user', @CurrentUser, 'table', 'sys_resource', 'column', 'title'
go

/*==============================================================*/
/* Index: pid_index                                             */
/*==============================================================*/
create index pid_index on sys_resource (
                                        pid ASC
    )
go

/*==============================================================*/
/* Index: url_index                                             */
/*==============================================================*/
create index url_index on sys_resource (
                                        url ASC
    )
go

/*==============================================================*/
/* Index: type_index                                            */
/*==============================================================*/
create index type_index on sys_resource (
                                         type ASC
    )
go

/*==============================================================*/
/* Index: seq_index                                             */
/*==============================================================*/
create index seq_index on sys_resource (
                                        seq ASC
    )
go

/*==============================================================*/
/* Index: status_index                                          */
/*==============================================================*/
create index status_index on sys_resource (
                                           status ASC
    )
go

/*==============================================================*/
/* Index: permission_index                                      */
/*==============================================================*/
create index permission_index on sys_resource (
                                               permission ASC
    )
go


if exists (select 1
           from  sysobjects
           where  id = object_id('sys_organization')
             and   type = 'U')
    drop table sys_organization
go

/*==============================================================*/
/* Table: sys_organization                                      */
/*==============================================================*/
create table sys_organization (
                                  id                   bigint               identity,
                                  corp_id              INT                  null,
                                  corp_name            VARCHAR(191)         null,
                                  parent_corp_id       INT                  null,
                                  seq                  INT                  null,
                                  corp_manager         VARCHAR(191)         null,
                                  create_time          datetime             null default getdate(),
                                  update_time          datetime             null default getdate(),
                                  constraint PK_SYS_ORGANIZATION primary key nonclustered (id)
)
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '机构ID',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'corp_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'corp_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '单位ID',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'corp_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'corp_name')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'corp_name'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '机构名称',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'corp_name'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'parent_corp_id')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'parent_corp_id'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '父级单位ID',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'parent_corp_id'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'seq')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'seq'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '排序',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'seq'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'corp_manager')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'corp_manager'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '单位负责人',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'corp_manager'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'create_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'create_time'
go

if exists(select 1 from sys.extended_properties p where
        p.major_id = object_id('sys_organization')
                                                    and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'update_time')
    )
    begin
        declare @CurrentUser sysname
        select @CurrentUser = user_name()
        execute sp_dropextendedproperty 'MS_Description',
                'user', @CurrentUser, 'table', 'sys_organization', 'column', 'update_time'

    end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '更新时间',
        'user', @CurrentUser, 'table', 'sys_organization', 'column', 'update_time'
go

/*==============================================================*/
/* Index: corp_id_unique                                        */
/*==============================================================*/
create unique index corp_id_unique on sys_organization (
                                                        corp_id ASC
    )
go

/*==============================================================*/
/* Index: parent_corp_id_index                                  */
/*==============================================================*/
create index parent_corp_id_index on sys_organization (
                                                       parent_corp_id ASC
    )
go
