drop database if exists changgou_poster;
create database changgou_poster;
use changgou_poster;

-- 广告分类表
drop table if exists tb_content_category;
create table tb_content_category
(
    id bigint(20) auto_increment primary key comment '类目ID',
    name varchar(50) not null comment '分类名称'
);

insert into tb_content_category(name) values('轮播广告');
insert into tb_content_category(name) values('活动广告');

-- 广告表
drop table if exists tb_content;
create table tb_content
(
    id int auto_increment primary key comment '广告编号',
    category_id bigint(20) not null comment '内容类别编号',
    title varchar(500) comment '内容标题',
    url varchar(1000) comment '链接',
    pic varchar(500) comment '图片路径',
    status varchar(1) comment '状态:0无效，1有效',
    sort_order int(11) comment '排序'
);

insert into tb_content(category_id, title, url, pic, status, sort_order) values(1, '肉松饼', 'http://www.baidu.com', 'http://192.168.137.118:8080/group1/M00/00/00/wKiJdl8rYp-Ab9ydAAA-Nvu01EY467.png', '1', 1);
insert into tb_content(category_id, title, url, pic, status, sort_order) values(1, '华为P30', 'http://www.baidu.com', 'http://192.168.137.118:8080/group1/M00/00/00/wKiJdl8rYp-Ab9ydAAA-Nvu01EY467.png', '1', 1);

select url,pic from tb_content where status = '1' and category_id = 1 order by sort_order;
