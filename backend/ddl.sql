create database secondhand;
-------------------------------
create table `user_info`
	(
	`id` int(11) auto_increment not null comment '主键',
	`account` varchar(25)   comment '账号',
	`password` varchar(60)   comment '密码',
	`sex` char(1)   comment '性格',
	`address` varchar(255)   comment '地址',
	`tel` varchar(11)   comment '电话',
	`status` char(1)   comment '状态',
	`role` char(1)   comment '角色',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '用户信息';
-- --------------------------------------------------------------------------------
create table `user_log`
	(
	`id` int(11) auto_increment not null comment '主键',
	`url` varchar(200)   comment '请求url',
	`method` varchar(30)   comment '请求类型',
	`ip` varchar(50)   comment '请求者ip',
	`description` varchar(50)   comment '请求描述',
	`parameter` text   comment '请求参数',
	`result` text   comment '请求返回参数',
	`spend_time` int(11)   comment '请求耗时(ms)',
	`create_account` varchar(50)   comment '请求用户',
	`start_time` datetime   comment '请求时间',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '用户日志表';
-- --------------------------------------------------------------------------------
create table `dictionary`
	(
	`id` int(11) auto_increment not null comment '主键',
	`dict_id` int(5)   comment '字典编号',
	`dict_name` varchar(25)   comment '字典名称',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '字典表';
-- --------------------------------------------------------------------------------
create table `dictionary_item`
	(
	`id` int(11) auto_increment not null comment '主键',
	`dict_id` int(5)   comment '字典编号',
	`item_id` int(5)   comment '字典值编号',
	`item_name` varchar(25)   comment '字典值名称',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '字典值表';
-- --------------------------------------------------------------------------------
create table `house_info`
	(
	`id` int(11) auto_increment not null comment '主键',
	`estate_name` varchar(255)   comment '小区名',
	`address` varchar(255)   comment '房子地址',
	`description` varchar(255)   comment '描述',
	`area` int(11)   comment '面积',
	`unit_price` int(11)   comment '单价',
	`total_price` int(22)   comment '总价',
	`picture1` mediumblob   comment '照片1',
	`picture2` mediumblob   comment '照片2',
	`picture3` mediumblob   comment '照片3',
	`score` int(3)   comment '评分',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '二手房信息';
-- --------------------------------------------------------------------------------
