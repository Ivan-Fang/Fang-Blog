create database `fang_times`;

use `fang_times`;

-- user info
create table `user` (
	`id` int unsigned not null auto_increment primary key,
    `username` varchar(20) not null unique key comment "username is unique and cannot be changed",
    `password` varchar(32) not null,
    `nickname` varchar(20) default "" comment "nickname can be changed",
    `email` varchar(128) default "",
    `user_pic` varchar(128) default "",
    `create_time` datetime not null comment "create time of the user",
    `update_time` datetime not null comment "latest update time of the user info"
);

-- article category
create table `category` (
	`id` int unsigned not null auto_increment primary key,
    `category_name` varchar(32) not null,
    `category_alias` varchar(32) not null,
    `create_user` int unsigned not null comment "user id that create the article category",
    `create_time` datetime not null,
    `update_time` datetime not null,
    constraint `fk_category_user` foreign key (create_user) references user(id)
);

-- article info
create table `article` (
	`id` int unsigned not null auto_increment primary key,
    `title` varchar(30) not null,
    `content` varchar(10000) not null,
    `cover_img` varchar(128) not null,
    `state` varchar(10) default "drafted" comment "only [drafted] or [published]",
    `category_id` int unsigned not null comment "category id of the article",
    `create_user` int unsigned not null comment "create user id of the article",
    `create_time` datetime not null,
    `update_time` datetime not null,
    constraint `fk_article_category` foreign key (`category_id`) references `category`(`id`),
    constraint `fk_article_user` foreign key (`create_user`) references `user`(`id`)
);










