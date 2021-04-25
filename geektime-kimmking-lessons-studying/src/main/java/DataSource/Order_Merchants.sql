create table ORDER_MERCHANTS
(
    OR_MERCHANTSID     VARCHAR2(32) not null,
    OR_MALLID      VARCHAR2(32) ,
    OR_CREATETIME DATE not null,
    OR_UPDATETIME   DATE default SYSDATE ,
    OR_MERCHANTSTYPE   int default 0 ,
    OR_GOODSNUM        int default 1
);

alter table ORDER_MERCHANTS modify column OR_MERCHANTSID varchar(32) comment '商户ID';