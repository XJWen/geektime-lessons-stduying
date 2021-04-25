create table ORDER_MALL
(
    OR_MALLID     VARCHAR2(32) not null,
    OR_CREATETIME DATE not null,
    OR_UPDATETIME   DATE default SYSDATE ,
    OR_USERTYPE   int default 0
);

