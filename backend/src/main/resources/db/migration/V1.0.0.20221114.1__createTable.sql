-- --------------------------------------------------------------------------------
create table `dictionary_catalog`
	(
	`id` int(11) auto_increment not null comment '自增主键',
	`dict_no` varchar(50)  not null comment '字典编号',
	`dict_name` varchar(100)  not null comment '字典名称',
	`dict_english_name` varchar(100)  not null comment '字典英文名称',
	`description` varchar(100)   comment '备注',
	`create_time` datetime  not null comment '创建日期',
	`update_time` datetime  not null comment '更新日期',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '字典目录表';
-- --------------------------------------------------------------------------------
create table `dictionary_item`
	(
	`id` int(11) auto_increment not null comment '自增主键',
	`dict_no` varchar(50)  not null comment '字典编号',
	`item_value` varchar(100)  not null comment '字典项值',
	`item_description` varchar(100)  not null comment '字典项描述',
	`item_english_name` varchar(100)   comment '字典项英文名',
	`remark` varchar(500)   comment '备注',
	`create_time` datetime  not null comment '创建日期',
	`update_time` datetime  not null comment '更新日期',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '字典项表';
-- --------------------------------------------------------------------------------