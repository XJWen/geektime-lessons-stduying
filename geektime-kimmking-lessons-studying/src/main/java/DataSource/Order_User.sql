create table ORDER_USERDATA
(
    OR_USERID     VARCHAR2(32) not null,
    OR_PHONE      VARCHAR2(32) not null,
    OR_IMAGE      VARCHAR2(512) ,
    OR_CREATETIME DATE not null,
    OR_UPDATETIME   DATE default SYSDATE ,
    OR_USERTYPE   int default 0
)

alter table ORDER_USERDATA modify column OR_USERID varchar(32) not null comment '用户id';
alter table ORDER_USERDATA modify column OR_PHONE varchar(32) not null comment '用户手机号码';
alter table ORDER_USERDATA modify column OR_IMAGE varchar(512) comment '用户头像url';
alter table ORDER_USERDATA modify column OR_CREATETIME DATE not null comment '创建时间';
alter table ORDER_USERDATA modify column OR_UPDATETIME DATE default SYSDATE comment '修改时间';
alter table ORDER_USERDATA modify column OR_USERTYPE int default 0  comment '用户类型';