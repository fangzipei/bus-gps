drop table bus_info ;
drop table bus_position ;
drop table bus_stop ;
drop table bus_tour ;
drop table stop_info ;
-- -------------------------------------------------
create table `bus_tour`
	(
	`id` int(11) auto_increment not null comment '主键',
	`driver_id` varchar(20)   comment '工号',
	`bus_no` varchar(20)   comment '公交车号码',
	`bus_schedule` int(3)   comment '公交班次',
	`tour_date` datetime   comment '日期',
	`heading_type` char(1)   comment '驶向(1-上行,2-下行)',
	`now_stop` varchar(30)   comment '现在的站台',
	`next_stop` varchar(30)   comment '下一个站台',
	`is_end` char(1)   comment '是否结束了(1-未结束,2-已结束)',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '公交车运行情况';
-- --------------------------------------------------------------------------------
create table `bus_position`
	(
	`id` int(11) auto_increment not null comment '主键',
	`tour_id` int(11)   comment '运行编号',
	`longitude` varchar(30)   comment '经度',
	`longitude_type` varchar(2)   comment '西经还是东经',
	`latitude` varchar(30)   comment '纬度',
	`latitude_type` varchar(2)   comment '南纬还是北纬',
	`height` varchar(10)   comment '海拔',
	`time` int(20)   comment '时间',
	`velocity` int(5)   comment '速度(km/h)',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '公交车位置';
-- --------------------------------------------------------------------------------
create table `bus_info`
	(
	`id` int(11) auto_increment not null comment '主键',
	`bus_no` varchar(20)   comment '公交车号码',
	`up_start` varchar(30)   comment '上行开始站台',
	`up_end` varchar(10)   comment '上行结束站台',
	`down_start` varchar(30)   comment '下行开始站台',
	`down_end` varchar(10)   comment '下行结束站台',
	`run_time` varchar(100)   comment '运行时间',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '公交车信息';
-- --------------------------------------------------------------------------------
create table `stop_info`
	(
	`id` int(11) auto_increment not null comment '主键',
	`stop_name` varchar(30)   comment '站台名',
	`longitude` varchar(30)   comment '经度',
	`longitude_type` varchar(2)   comment '西经还是东经',
	`latitude` varchar(30)   comment '纬度',
	`latitude_type` varchar(2)   comment '南纬还是北纬',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '站台信息';
-- --------------------------------------------------------------------------------
create table `bus_stop`
	(
	`id` int(11) auto_increment not null comment '主键',
	`bus_no` varchar(20)   comment '公交车号码',
	`sequence` int(3)   comment '站台顺序',
	`stop_name` varchar(30)   comment '站台名',
	`heading_type` char(1)   comment '驶向(1-上行,2-下行)',
	primary key (id)
	) engine=innodb collate='utf8mb4_0900_ai_ci' comment '公交站台信息';
-- --------------------------------------------------------------------------------
