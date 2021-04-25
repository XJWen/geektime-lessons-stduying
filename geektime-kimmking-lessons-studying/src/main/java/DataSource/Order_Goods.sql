create table ORDER_GOODS
(
    OR_GOODSID     VARCHAR2(32) not null,
    OR_MERCHANTSID  VARCHAR2(32) not null,
    OR_CREATETIME DATE not null,
    OR_UPDATETIME   DATE default SYSDATE ,
    OR_USERTYPE   int default 0,
);
