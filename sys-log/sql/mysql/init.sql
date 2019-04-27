drop table if exists action_log;

/*==============================================================*/
/* Table: action_log                                            */
/*==============================================================*/
create table action_log
(
  id          BIGINT(20) not null auto_increment comment 'id',
  type        NVARCHAR(255) default 'null' comment '接口类型',
  identify    NVARCHAR(255) default 'null' comment '身份(如用户名,手机号,用户 id 等)',
  description NVARCHAR(255) default '' comment '接口描述',
  uri         NVARCHAR(255) default '' comment '接口地址',
  param       TEXT comment '请求参数JSON字符串',
  duration    BIGINT(20)    default 0 comment '请求时长',
  ip          NVARCHAR(255) default '' comment '请求IP',
  visit_time  DATETIME      default CURRENT_TIMESTAMP comment '请求时间',
  create_time DATETIME      default CURRENT_TIMESTAMP comment '创建时间',
  primary key (id)
) ENGINE = InnoDB
  CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci;

alter table action_log
  comment '操作日志表';

/*==============================================================*/
/* Index: type_index                                            */
/*==============================================================*/
create index type_index on action_log
  (
   type
    );

/*==============================================================*/
/* Index: identify_index                                        */
/*==============================================================*/
create index identify_index on action_log
  (
   identify
    );

/*==============================================================*/
/* Index: uri_index                                             */
/*==============================================================*/
create index uri_index on action_log
  (
   uri
    );

/*==============================================================*/
/* Index: ip_index                                              */
/*==============================================================*/
create index ip_index on action_log
  (
   ip
    );

/*==============================================================*/
/* Table: exception_log                                         */
/*==============================================================*/
create table exception_log
(
  id          BIGINT(20) not null auto_increment comment 'id',
  identify    NVARCHAR(255) default 'null' comment '身份（如用户名，手机号，用户id等）',
  ip          NVARCHAR(255) default '' comment '请求者IP',
  uri         NVARCHAR(191) default '' comment '接口地址',
  param       TEXT comment '请求参数JSON字符串',
  class_name  NVARCHAR(255) default '' comment '异常类名',
  message     NVARCHAR(255) default '' comment '异常信息',
  stack_trace TEXT comment '异常堆栈信息',
  create_time DATETIME      default CURRENT_TIMESTAMP comment '创建时间',
  primary key (id)
) ENGINE = InnoDB
  CHARSET = utf8mb4
  COLLATE utf8mb4_general_ci;

alter table exception_log
  comment '异常日志表';

/*==============================================================*/
/* Index: uri_index                                             */
/*==============================================================*/
create index uri_index on exception_log
  (
   uri
    );

/*==============================================================*/
/* Index: ip_index                                              */
/*==============================================================*/
create index ip_index on exception_log
  (
   ip
    );

drop table if exists api_log;

/*==============================================================*/
/* Table: api_log                                               */
/*==============================================================*/
create table api_log
(
  id          BIGINT(20) not null auto_increment comment 'ID',
  url         NVARCHAR(255) comment 'API地址',
  method      NVARCHAR(255) comment '请求方式',
  param       TEXT comment '请求参数',
  create_time DATETIME default CURRENT_TIMESTAMP comment '创建时间',
  response    TEXT comment '响应内容',
  description NVARCHAR(255) comment 'API描述',
  primary key (id)
);

/*==============================================================*/
/* Index: url_index                                             */
/*==============================================================*/
create index url_index on api_log
  (
   url
    );
