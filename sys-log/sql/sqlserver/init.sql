if exists(select 1
          from sysobjects
          where id = object_id('action_log')
            and type = 'U')
  drop table action_log
go

/*==============================================================*/
/* Table: action_log                                            */
/*==============================================================*/
create table action_log
(
  id          bigint identity,
  type        NVARCHAR(255) null default 'null',
  identify    NVARCHAR(255) null default 'null',
  description NVARCHAR(255) null default '',
  uri         NVARCHAR(255) null default '',
  param       TEXT          null,
  duration    bigint        null default 0,
  ip          NVARCHAR(255) null default '',
  visit_time  datetime      null default getdate(),
  create_time datetime      null default getdate(),
  constraint PK_ACTION_LOG primary key nonclustered (id)
)
go

if exists(select 1
          from sys.extended_properties
          where major_id = object_id('action_log')
            and minor_id = 0)
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '操作日志表',
        'user', @CurrentUser, 'table', 'action_log'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'type')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'type'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '接口类型',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'type'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'identify')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'identify'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '身份(如用户名,手机号,用户 id 等)',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'identify'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'description')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'description'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '接口描述',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'description'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'uri')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'uri'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '接口地址',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'uri'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'param')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'param'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求参数JSON字符串',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'param'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'duration')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'duration'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求时长',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'duration'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ip')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'ip'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求IP',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'ip'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'visit_time')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'visit_time'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求时间',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'visit_time'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('action_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'action_log', 'column', 'create_time'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'action_log', 'column', 'create_time'
go

/*==============================================================*/
/* Index: type_index                                            */
/*==============================================================*/
create index type_index on action_log (
                                       type ASC
  )
go

/*==============================================================*/
/* Index: identify_index                                        */
/*==============================================================*/
create index identify_index on action_log (
                                           identify ASC
  )
go

/*==============================================================*/
/* Index: uri_index                                             */
/*==============================================================*/
create index uri_index on action_log (
                                      uri ASC
  )
go

/*==============================================================*/
/* Index: ip_index                                              */
/*==============================================================*/
create index ip_index on action_log (
                                     ip ASC
  )
go

if exists(select 1
          from sysobjects
          where id = object_id('exception_log')
            and type = 'U')
  drop table exception_log
go

/*==============================================================*/
/* Table: exception_log                                         */
/*==============================================================*/
create table exception_log
(
  id          bigint identity,
  identify    VARCHAR(191) null default 'null',
  ip          VARCHAR(255) null default '',
  uri         VARCHAR(191) null default '',
  param       TEXT         null,
  class_name  VARCHAR(255) null default '',
  message     VARCHAR(255) null default '',
  stack_trace TEXT         null,
  create_time datetime     null default getdate(),
  constraint PK_EXCEPTION_LOG primary key nonclustered (id)
)
go

if exists(select 1
          from sys.extended_properties
          where major_id = object_id('exception_log')
            and minor_id = 0)
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '异常日志表',
        'user', @CurrentUser, 'table', 'exception_log'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'id'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'id',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'id'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'identify')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'identify'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '身份（如用户名，手机号，用户id等）',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'identify'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'ip')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'ip'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求者IP',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'ip'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'uri')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'uri'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '接口地址',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'uri'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'param')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'param'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求参数JSON字符串',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'param'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'class_name')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'class_name'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '异常类名',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'class_name'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and
              p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'message')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'message'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '异常信息',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'message'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'stack_trace')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'stack_trace'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '异常堆栈信息',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'stack_trace'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('exception_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'exception_log', 'column', 'create_time'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'exception_log', 'column', 'create_time'
go

/*==============================================================*/
/* Index: uri_index                                             */
/*==============================================================*/
create index uri_index on exception_log (
                                         uri ASC
  )
go

/*==============================================================*/
/* Index: ip_index                                              */
/*==============================================================*/
create index ip_index on exception_log (
                                        ip ASC
  )
go

if exists(select 1
          from sysobjects
          where id = object_id('api_log')
            and type = 'U')
  drop table api_log
go

/*==============================================================*/
/* Table: api_log                                               */
/*==============================================================*/
create table api_log
(
  id          bigint identity,
  url         VARCHAR(191) null,
  method      VARCHAR(191) null,
  param       TEXT         null,
  create_time datetime     null default getdate(),
  response    TEXT         null,
  description VARCHAR(191) null,
  constraint PK_API_LOG primary key nonclustered (id)
)
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'id')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'id'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'ID',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'id'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'url')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'url'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'API地址',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'url'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and
              p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'method')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'method'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求方式',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'method'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and p.minor_id = (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'param')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'param'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '请求参数',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'param'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'create_time')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'create_time'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '创建时间',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'create_time'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'response')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'response'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        '响应内容',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'response'
go

if exists(select 1
          from sys.extended_properties p
          where p.major_id = object_id('api_log')
            and p.minor_id =
                (select c.column_id from sys.columns c where c.object_id = p.major_id and c.name = 'description')
  )
  begin
    declare @CurrentUser sysname
    select @CurrentUser = user_name()
    execute sp_dropextendedproperty 'MS_Description',
            'user', @CurrentUser, 'table', 'api_log', 'column', 'description'

  end


select @CurrentUser = user_name()
execute sp_addextendedproperty 'MS_Description',
        'API描述',
        'user', @CurrentUser, 'table', 'api_log', 'column', 'description'
go

/*==============================================================*/
/* Index: url_index                                             */
/*==============================================================*/
create index url_index on api_log (
                                   url ASC
  )
go
