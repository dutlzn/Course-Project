# ------ 大章 ---------
drop table if EXISTS `chapter`;
create table `chapter` (
	`id` char(8) not null COMMENT 'ID',
	`course_id` char(8) comment '课程id',
	`name` varchar(50) comment '名称',
	primary key (`id`)
) ENGINE=INNODB DEFAULT charset=utf8mb4 comment='大章';

insert into `chapter` (id, course_id, name) values ('00000000', '0000000', '测试大章1');
insert into `chapter` (id, course_id, name) values ('00000001', '00000000', '测试大章2');
insert into `chapter` (id, course_id, name) values ('00000002', '00000002', '测试大章3');
insert into `chapter` (id, course_id, name) values ('00000003', '00000003', '测试大章4');
insert into `chapter` (id, course_id, name) values ('00000004', '00000004', '测试大章5');
insert into `chapter` (id, course_id, name) values ('00000005', '00000005', '测试大章6');
insert into `chapter` (id, course_id, name) values ('00000006', '00000006', '测试大章7');
insert into `chapter` (id, course_id, name) values ('00000007', '00000007', '测试大章8');
insert into `chapter` (id, course_id, name) values ('00000008', '00000008', '测试大章9');
insert into `chapter` (id, course_id, name) values ('00000009', '00000009', '测试大章10');
insert into `chapter` (id, course_id, name) values ('00000010', '00000010', '测试大章11');
insert into `chapter` (id, course_id, name) values ('00000011', '00000011', '测试大章12');
insert into `chapter` (id, course_id, name) values ('00000012', '00000012', '测试大章13');
insert into `chapter` (id, course_id, name) values ('00000013', '00000013', '测试大章14');
insert into `chapter` (id, course_id, name) values ('00000014', '00000014', '测试大章15');
insert into `chapter` (id, course_id, name) values ('00000015', '00000015', '测试大章16');
insert into `chapter` (id, course_id, name) values ('00000016', '00000016', '测试大章17');
insert into `chapter` (id, course_id, name) values ('00000017', '00000017', '测试大章18');
insert into `chapter` (id, course_id, name) values ('00000018', '00000018', '测试大章19');
insert into `chapter` (id, course_id, name) values ('00000019', '00000019', '测试大章20');
insert into `chapter` (id, course_id, name) values ('00000020', '00000020', '测试大章21');
insert into `chapter` (id, course_id, name) values ('00000021', '00000021', '测试大章22');


# ------- 小节--------
-- 小节
drop table if exists `section`;
create table `section` (
  `id` char(8) not null default '' comment 'id',
  `title` varchar(50) not null comment '标题',
  `course_id` char(8) comment '课程|course.id',
  `chapter_id` char(8) comment '大章|chapter.id',
  `video` varchar(200) comment '视频',
  `time` int comment '时长|单位秒',
  `charge` char(1) comment '收费|C 收费；F 免费',
  `sort` int comment '顺序',
  `created_at` datetime(3) comment '创建时间',
  `updated_at` datetime(3) comment '修改时间',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='小节';


insert into `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
values ('00000001', '测试小节01', '00000001', '00000000', '', 500, 'f', 1, now(), now());


# ----------------测试
drop table if exists `test`;
create table `test` (
    `id` char(8) not null default '' comment 'id',
    `name` varchar(255) comment '名称',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 ;

insert into `test` (id, name) values (1, '测试');
insert into `test` (id, name) values (3, '测试2');
