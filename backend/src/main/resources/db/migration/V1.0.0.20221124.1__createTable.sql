create table `all_log`
	(
	`id` int(11) auto_increment not null comment '自增主键',
	`url` varchar(200)  not null comment '请求url',
	`method` varchar(30)  not null comment '请求类型',
	`ip` varchar(50)  not null comment '请求者ip',
	`description` varchar(50)   comment '请求描述',
	`parameter` text   comment '请求参数',
	`result` text   comment '请求返回参数',
	`spend_time` int(11)   comment '请求耗时(ms)',
	`create_account` varchar(50)  not null comment '请求用户',
	`start_time` datetime   comment '请求时间',
	`create_time` datetime   comment '创建日期',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '全日志表';