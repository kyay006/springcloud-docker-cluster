
DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user` (
`id`  bigint primary key auto_increment,
`openid`  char(28) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '小程序用户openid' ,
`nickname`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称,微信的' ,
`avatarurl`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像,微信的' ,
`mobile`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '手机类型,微信的' ,
`telnum`  char(13) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号码,微信的',
`gender`  tinyint(1) NULL DEFAULT 0 COMMENT '性别 0：未知、1：男、2：女' ,
`country`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在国家' ,
`province`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份' ,
`city`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '城市' ,
`language`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`status` varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
`userNickname`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户昵称' ,
`userAvatarurl`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户头像,自己上传的',
`loveWords`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '当前情话' ,
`email`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
`ageFormat`  date   NULL COMMENT '年龄,年月日',
`userAge`  int   NULL COMMENT '年龄,数量',
`userHeight`  smallint   NULL COMMENT '身高,170cm',
`userWeight`  smallint   NULL COMMENT '体重,50kg',
`userEducation`  tinyint   NULL COMMENT '学历,0表示高中,1表示专科,2表示本科,3表示硕士,4表示博士',
`userMarriage`  tinyint   NULL COMMENT '婚否,0表示未婚,1表示离异',
`userIndustry`  varchar(128)  CHARACTER SET utf8 COLLATE utf8_general_ci   NULL COMMENT '行业id,可选择多个行业,如果有多个就用,号拼接',
`userOccupation`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL COMMENT '职业,自行输入',
`userCharacter`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL COMMENT '性格id,可选择多个性格,如果有多个就用,号拼接',
`userHobby`  varchar(256)   NULL COMMENT '爱好,自行输入',
`createDate`  datetime NULL DEFAULT NULL COMMENT '创建时间' ,
`updateDate` datetime DEFAULT NULL COMMENT '修改时间'
) COMMENT '微信用户表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


INSERT INTO `wx_user` VALUES (2, 'oI7o-5RIsF6x-qbdVRFnozOcc2W1', '桃子1', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子1', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', 'qing\yi', NULL, NULL, 22, 155, 55, 1, 0, '=2=', '产品', '=3=', '爱好a1', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (3, 'oI7o-5RIsF6x-qbdVRFnozOcc2W2', '桃子2', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子2', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', 'qing\yi111', NULL, NULL, 21, 163, 49, 1, 0, '=3=', '产品', '=2=', '爱好a2', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (4, 'oI7o-5RIsF6x-qbdVRFnozOcc2W3', '桃子3', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子3', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 22, 162, 48, 2, 0, '=2=,=4=', '销售', '=2=', '爱好a3', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (5, 'oI7o-5RIsF6x-qbdVRFnozOcc2W4', '桃子4', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子4', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 23, 161, 44, 2, 0, '=1=', '销售', '=1=', '爱好a4', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (6, 'oI7o-5RIsF6x-qbdVRFnozOcc2W5', '桃子5', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子5', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 22, 160, 55, 2, 0, '=2=', '金融', '=8=', '爱好a5', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (7, 'oI7o-5RIsF6x-qbdVRFnozOcc2W6', '桃子6', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子6', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 28, 166, 55, 2, 0, '=8=', '编辑', '=8=', '爱好a6', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (8, 'oI7o-5RIsF6x-qbdVRFnozOcc2W7', '桃子7', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子7', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 27, 155, 55, 2, 0, '=8=', '编辑', '=2=', '爱好a7', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (9, 'oI7o-5RIsF6x-qbdVRFnozOcc2W8', '桃子8', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子8', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 26, 166, 42, 1, 1, '=1=', '编辑', '=8=,=1=', '爱好a8', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (10, 'oI7o-5RIsF6x-qbdVRFnozOcc2W9', '桃子9', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 2, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','桃子9', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, NULL, 25, 166, 55, 4, 0, '=2=', '策划', '=8=', '爱好a9', '2019-01-30 14:42:42', NULL);
INSERT INTO `wx_user` VALUES (11, 'oI7o-5RIsF6x-qbdVRFnozOcc2Wo', '窗台的雨', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', NULL, NULL, 1, 'France', 'Paris', '', 'zh_CN', 'ACTIVE','窗台2的雨', 'http://127.0.0.1:8005/resource/11/HeadImage/20190203/0d21dc8bd1fb4cf8848347cc17a3730c.jpg', 'sdfg', NULL, '1990-06-04', 28, 164, 48, 2, 0, '=1=,=5=,=8=', 'hjjhj', '=2=,=4=,=8=', 'yyuj', '2019-02-01 23:49:37', '2019-02-08 20:48:59');



/*用户举报表*/
create table lovesound_report
(
  id bigint primary key auto_increment,
  reportReason varchar(260) not null COMMENT '举报原因',
  functionId int not null COMMENT '举报功能id',
  dataId int not null COMMENT '举报功能的具体数据id',
  reportStatus char(1) not null DEFAULT 0 COMMENT '默认为0,举报是否有效，0为未处理，1为无效，2为有效',
  status varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
  reportUserId bigint not null COMMENT '被举报人',
  createTime datetime not null COMMENT '举报时间',
  createUserId bigint not null COMMENT '举报人',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id，不出意外的话就是审核人'
) COMMENT '用户举报表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*小黑屋表 用来放违规的用户*/
create table lovesound_dark_room
(
  id int(10) primary key auto_increment,
  darkUserId bigint not null COMMENT '关联的用户表id',
  darkCause varchar(256) COMMENT '关进小黑屋的原因',
  userStatus char(1) not null DEFAULT 0 COMMENT '0为正在被关，1为已释放。多个原因被关进小黑屋有多条记录',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id'
) COMMENT '小黑屋表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*小黑屋表*/


/*行业表*/
create table lovesound_industry
(
  id int(10) primary key auto_increment,
  industryName varchar(64) not null COMMENT '行业名称',
  remark varchar(100) COMMENT '备注',
  orderNumber smallint not null DEFAULT 1 COMMENT '排序号',
  status varchar(10) not null DEFAULT 'DISABLED' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id'
) COMMENT '行业表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `lovesound_industry` VALUES ('1', '互联网', null, '1', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('2', '行政', null, '2', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('3', '教育', null, '3', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('4', '金融', null, '4', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('5', '医疗', null, '5', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('6', '建筑', null, '6', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('7', '销售', null, '7', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('8', '传媒', null, '8', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_industry` VALUES ('9', '服务', null, '9', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');


/*职业表*/
create table lovesound_occupation
(
  id int(10) primary key auto_increment,
  occupationName varchar(50) not null COMMENT '职业名称',
  industryId int(10) not null COMMENT '关联的行业id，职业属于行业',
  remark varchar(100) COMMENT '备注',
  orderNumber smallint not null DEFAULT 1 COMMENT '排序号',
  status varchar(10) not null DEFAULT 'DISABLED' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id',
  foreign key(industryId) references lovesound_industry(id)
) COMMENT '职业表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*性格表*/
create table lovesound_character
(
  id int(10) primary key auto_increment,
  characterName varchar(64) not null COMMENT '性格名称',
  remark varchar(100) COMMENT '备注',
  orderNumber smallint not null DEFAULT 1 COMMENT '排序号',
  status varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id'
) COMMENT '性格表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `lovesound_character` VALUES ('1', '外向型', null, '1', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('2', '内向型', null, '2', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('3', '和平型', null, '3', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('4', '独立型', null, '4', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('5', '顺从型', null, '5', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('6', '反抗型', null, '6', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('7', '活泼型', null, '7', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('8', '完美型', null, '8', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');
INSERT INTO `lovesound_character` VALUES ('9', '力量型', null, '9', 'ACTIVE', '2019-01-31 12:51:56', '1', '2019-01-31 12:51:41', '1');


/*图片管理表(包括用户头像,文章图片等)*/
create table lovesound_img_manage
(
id bigint primary key auto_increment,
absolutePath varchar(225) not null COMMENT '绝对路径',
urlPath varchar(225) not null COMMENT 'url路径',
imgFileName varchar(128)  COMMENT '文件原始的名称，用户上传时候的名称',
imgType char(1) not null DEFAULT 1 COMMENT '图片是头像还是相册什么的，枚举类里定义有',
status varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
createTime datetime not null COMMENT '创建时间',
createUserId bigint COMMENT '创建人id',
updateTime datetime not null COMMENT '修改时间',
updateUserId bigint COMMENT '修改人id'
) COMMENT '图片管理表(包括用户头像,文章图片等)'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*图片管理表(包括用户头像,文章图片等)end*/



/*用户动态信息表*/
DROP TABLE IF EXISTS `lovesound_user_dynamic`;
create table lovesound_user_dynamic
(
  id bigint primary key auto_increment,
  dynamicContent varchar(256) not null COMMENT '动态内容',
  dynamicImgUrls varchar(1024) not null COMMENT '图片url拼接',
  dynamicImgIds varchar(256) not null COMMENT '图片表的id,多个用逗号拼接',
  remark varchar(100) COMMENT '备注',
  status varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id'
) COMMENT '用户动态信息表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*用户关注其他用户表*/
DROP TABLE IF EXISTS `lovesound_user_subscribe`;
create table lovesound_user_subscribe
(
id bigint primary key auto_increment,
passiveUserId bigint not null COMMENT '被关注的用户id',
subscribeStatus char(1) DEFAULT 1 COMMENT '关注状态，0表示取消关注，1表示关注',
createTime datetime not null COMMENT '创建时间',
createUserId bigint COMMENT '创建人id',
updateTime datetime not null COMMENT '修改时间',
updateUserId bigint COMMENT '修改人id'
) COMMENT '用户关注其他用户表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*用户关注其他用户表end*/



-- ----------------------------
-- Table structure for `lovesound_chat_list`
-- ----------------------------
DROP TABLE IF EXISTS `lovesound_chat_list`;
CREATE TABLE `lovesound_chat_list` (
  `id`  bigint primary key AUTO_INCREMENT,
  `sendUserId` bigint DEFAULT NULL COMMENT '信息发送人id',
  `receiverUserId` bigint DEFAULT NULL COMMENT '信息接收人id',
  `sendUnReadCount` int(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '消息未读数量-发送人用',
  `receiverUnReadCount` int(11) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '消息未读数量-接收人用',
  `lastContent` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '最后一条发送的内容,如果是图片的话,这里就保存图片这两个字',
  `lastSendDate` datetime DEFAULT NULL COMMENT '最后一条发送的时间',
  `status` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态,ACTIVE为正常活动状态,LOCKING为锁定状态,DELETE为删除状态',
  `createTime` datetime not null COMMENT '创建时间',
  `createUserId` bigint COMMENT '创建人id',
  `updateTime` datetime not null COMMENT '修改时间',
  `updateUserId` bigint COMMENT '修改人id'
) COMMENT '聊天列表表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------------
-- Table structure for `lovesound_chat_msg`
-- ----------------------------
DROP TABLE IF EXISTS `lovesound_chat_msg`;
CREATE TABLE `lovesound_chat_msg` (
  `id`  bigint primary key AUTO_INCREMENT,
  `sendUserId` bigint DEFAULT NULL COMMENT '信息发送人id',
  `receiverUserId` bigint DEFAULT NULL COMMENT '信息接收人id',
  `content` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '消息内容',
  `imageId` bigint  DEFAULT NULL COMMENT '消息图片id',
  `image` varchar(1024) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '消息图片uri',
  `sendDate` datetime DEFAULT NULL COMMENT '发送时间',
  `status` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '状态,ACTIVE为正常活动状态,DELETE为删除状态',
  `haveRead` tinyint(1) DEFAULT '0' COMMENT '是否已读,0表示未读,1表示已读',
  `msgType` tinyint(4) DEFAULT '0' COMMENT '信息类型,0表示文字,1表示图片',
  `createTime` datetime not null COMMENT '创建时间',
  `createUserId` bigint COMMENT '创建人id',
  `updateTime` datetime not null COMMENT '修改时间',
  `updateUserId` bigint COMMENT '修改人id'
) COMMENT '聊天消息表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



-- ----------------------------
-- Table structure for `lovesound_log_login`
-- ----------------------------
DROP TABLE IF EXISTS `lovesound_log_login`;
CREATE TABLE `lovesound_log_login` (
  `id`  bigint primary key AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL COMMENT '登录人id',
  `openId` char(28) DEFAULT NULL COMMENT '登录人openid',
  `loginType` tinyint(4) DEFAULT 0 COMMENT '登录类型,0表示授权,1表示登录',
  `createTime` datetime not null COMMENT '创建时间'
) COMMENT '登录日志表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `lovesound_log_search`
-- ----------------------------
DROP TABLE IF EXISTS `lovesound_log_search`;
CREATE TABLE `lovesound_log_search` (
  `id`  bigint primary key AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL COMMENT '登录人id',
  `userAgeStart` int(11) DEFAULT NULL COMMENT '年龄start',
  `userAgeEnd` int(11) DEFAULT NULL COMMENT '年龄end',
  `userHeightStart` int(11) DEFAULT NULL COMMENT '身高start',
  `userHeightEnd` int(11) DEFAULT NULL COMMENT '身高end',
  `userIndustry` varchar(128) DEFAULT NULL COMMENT '行业',
  `userEducation` int(11) DEFAULT NULL COMMENT '最低学历',
  `userCharacter` varchar(128) DEFAULT NULL COMMENT '兴趣',
  `userWeightStart` int(11) DEFAULT NULL COMMENT '体重start',
  `userWeightEnd` int(11) DEFAULT NULL COMMENT '体重end',
  `userMarriage` int(11) DEFAULT NULL COMMENT '婚否',
  `gender` int(11) DEFAULT NULL COMMENT '性别',
  `createTime` datetime not null COMMENT '创建时间'
) COMMENT '搜索用户日志表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `lovesound_log_userinfo`
-- ----------------------------
DROP TABLE IF EXISTS `lovesound_log_userinfo`;
CREATE TABLE `lovesound_log_userinfo` (
  `id`  bigint primary key AUTO_INCREMENT,
  `userId` bigint DEFAULT NULL COMMENT '登录人id',
  `userNickname`  varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户昵称' ,
  `userAvatarurl`  varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户头像,自己上传的',
  `loveWords`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '当前情话' ,
  `email`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `ageFormat`  date   NULL COMMENT '年龄,年月日',
  `userAge`  int   NULL COMMENT '年龄,数量',
  `userHeight`  smallint   NULL COMMENT '身高,170cm',
  `userWeight`  smallint   NULL COMMENT '体重,50kg',
  `userEducation`  tinyint   NULL COMMENT '学历,0表示高中,1表示专科,2表示本科,3表示硕士,4表示博士',
  `userMarriage`  tinyint   NULL COMMENT '婚否,0表示未婚,1表示离异',
  `userIndustry`  varchar(128)  CHARACTER SET utf8 COLLATE utf8_general_ci   NULL COMMENT '行业id,可选择多个行业,如果有多个就用,号拼接',
  `userOccupation`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL COMMENT '职业,自行输入',
  `userCharacter`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci    NULL COMMENT '性格id,可选择多个性格,如果有多个就用,号拼接',
  `userHobby`  varchar(256)   NULL COMMENT '爱好,自行输入',
  `createTime` datetime NOT NULL COMMENT '创建时间'
) COMMENT '用户基本信息历史日志表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*用户阅读（查看）其他用户日志表*/
DROP TABLE IF EXISTS `lovesound_log_readuser`;
create table lovesound_log_readuser
(
  id bigint primary key auto_increment,
  readUserId bigint not null COMMENT '被查看的用户id',
  createTime datetime not null COMMENT '创建时间(即阅读时间)',
  createUserId bigint COMMENT '创建人id',
  createUserIp varchar(64) COMMENT '访问用户ip地址,因为用户可以不登录就观看,所以这里存个地址'
) COMMENT '用户阅读（查看）其他用户日志表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*用户阅读（查看）其他用户日志表end*/



/*微信订阅号上墙表*/
DROP TABLE IF EXISTS `lovesound_wechat_upwall`;
create table lovesound_wechat_upwall
(
  id bigint primary key auto_increment,
  name varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '姓名,网名',
  sex tinyint not null COMMENT '性别,1表示男,0表示女',
  wechat varchar(128) not null COMMENT '微信号',
  baseInfo varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '年龄 身高 星座 职业 混迹区',
  interest varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '兴趣爱好',
  selfIntroduction varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '自我介绍',
  demand varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '交友要求',
  img1 bigint not null COMMENT '第一张图片,img表的id',
  img2 bigint not null COMMENT '第二张图片,img表的id',
  img3 bigint not null COMMENT '第三张图片,img表的id',
  img4 bigint COMMENT '第四张图片,可为空,img表的id',
  img5 bigint COMMENT '第五张图片,可为空,img表的id',
  imgUrls varchar(1024) COMMENT '所有图片的url,多个用逗号拼接',
  examineStatus tinyint DEFAULT 0 not null COMMENT '审核状态,0表示未审核,1表示审核失败,2表示审核成功但未发布,3表示已发布',
  examineTime datetime COMMENT '审核时间',
  publishTime datetime COMMENT '发布时间',
  `status` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL DEFAULT 'ACTIVE' COMMENT '状态,ACTIVE为正常活动状态,DELETE为删除状态',
  createTime datetime not null COMMENT '创建时间',
  createUserIp varchar(64) COMMENT '访问用户ip地址,因为用户可以不登录,所以这里存个地址'
) COMMENT '微信订阅号上墙表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*微信订阅号上墙表end*/







/***************************************2019-03-04 模板消息id串,已在本地，服务器执行*******************************************************/
ALTER TABLE `lovesound`.`wx_user`
ADD COLUMN `formIds` varchar(512) NULL COMMENT '模板消息用的id串,多个用逗号拼接' AFTER `userHobby`;
/***************************************2019-03-04 模板消息id串,已在本地，服务器执行end*******************************************************/




/***************************************2019-03-06 文章,已在本地执行，服务器已执行*******************************************************/

/*交友文章表*/
DROP TABLE IF EXISTS `lovesound_article`;
create table lovesound_article
(
  id bigint primary key auto_increment,
  title varchar(128) not null COMMENT '标题',
  wechat varchar(128) null COMMENT '微信号',
  baseInfo varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '年龄 身高 (前两个其实可以不要)星座 职业 混迹区',
  selfIntroduction varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '自我介绍',
  interest varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '价值观、爱情观',
  demand varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '交友要求',
  img1 bigint not null COMMENT '第一张图片,img表的id',
  img2 bigint not null COMMENT '第二张图片,img表的id',
  img3 bigint not null COMMENT '第三张图片,img表的id',
  img4 bigint COMMENT '第四张图片,可为空,img表的id',
  img5 bigint COMMENT '第五张图片,可为空,img表的id',
  imgUrls varchar(1024) COMMENT '所有图片的url,多个用逗号拼接',
  hasTop char(1) not null DEFAULT 0 COMMENT '默认不置顶,是否置顶，0表示不置顶,1表示置顶',
  topTime datetime COMMENT '置顶时间',
  `province`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份' ,
  `city`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '城市' ,
  `status` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL DEFAULT 'ACTIVE' COMMENT '状态,ACTIVE为正常活动状态,DELETE为删除状态',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id'
) COMMENT '交友文章表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*交友文章表end*/


/*用户文章点赞表*/
create table water_article_zan
(
  id bigint primary key auto_increment,
  articleId bigint not null COMMENT '关联的文章表id',
  zanStatus char(1) DEFAULT 1 COMMENT '点赞状态，0为倒赞，1为点赞.(一个用户对于一篇文章只能倒赞或只能点赞)',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id'
) COMMENT '用户文章点赞表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*用户文章点赞表end*/

/*用户文章阅读表*/
create table water_article_read
(
  id bigint primary key auto_increment,
  articleId bigint not null COMMENT '关联的文章表id',
  articlecreateUserId bigint not null COMMENT '关联文章创建者id',
  createTime datetime not null COMMENT '创建时间(即阅读时间)',
  createUserId bigint COMMENT '创建人id',
  createUserIp varchar(64) COMMENT '访问用户ip地址,因为用户可以不登录就观看,所以这里存个地址'
) COMMENT '用户文章阅读表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*用户文章阅读表end*/

/*用户文章评论表*/
create table water_message_board
(
  id bigint primary key auto_increment,
  articleId bigint not null COMMENT '关联的文章表id',
  messageUserId bigint not null COMMENT '给这个用户留的言,也就是文章的创建者',
  content varchar(1024) not null COMMENT '留言的内容',
  floor int(10) not null  DEFAULT 1 COMMENT '留言所在的楼层，如果是回复的话楼层数和该留言在同一楼层',
  replyMessageId bigint COMMENT '如果是回复的话就有值，对应回复的那条留言',
  readStatus char(1) not null DEFAULT 0 COMMENT '该留言是否被用户已读，0表示未读，1表示已读',
  status varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED,REPORT(举报状态,只有这个用户能举报留言的人)',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id,这个人留的言'
) COMMENT '用户文章评论表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*用户文章评论表end*/


/***************************************2019-03-06 文章,已在本地执行，服务器已执行end*******************************************************/


/***************************************2019-03-12 约见,已在本地执行，服务器已执行*******************************************************/

/*约见表*/
DROP TABLE IF EXISTS `lovesound_appointment`;
create table lovesound_appointment
(
  id bigint primary key auto_increment,
  title varchar(128) not null COMMENT '标题',
  wechat varchar(128) null COMMENT '微信号',
  selfIntroduction varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '自我介绍',
  formality varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '约见形式描述,如散步,吃饭等',
  `condition` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '约见条件,对方条件',
  address varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '约见地址',
  appointmentTime datetime COMMENT '约见时间',
  img1 bigint not null COMMENT '第一张图片,img表的id',
  img2 bigint COMMENT '第二张图片,可为空,img表的id',
  img3 bigint COMMENT '第三张图片,可为空,img表的id',
  img4 bigint COMMENT '第四张图片,可为空,img表的id',
  img5 bigint COMMENT '第五张图片,可为空,img表的id',
  imgUrls varchar(1024) COMMENT '所有图片的url,多个用逗号拼接',
  hasTop char(1) not null DEFAULT 0 COMMENT '默认不置顶,是否置顶，0表示不置顶,1表示置顶',
  topTime datetime COMMENT '置顶时间',
  `province`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份' ,
  `city`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '城市' ,
  `status` varchar(16) COLLATE utf8_unicode_ci DEFAULT NULL DEFAULT 'ACTIVE' COMMENT '状态,ACTIVE为正常活动状态,DISABLED为禁用或者关闭状态,DELETE为删除状态',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id',
  updateTime datetime not null COMMENT '修改时间',
  updateUserId bigint COMMENT '修改人id'
) COMMENT '约见表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*约见表end*/

/*约见报名表*/
DROP TABLE IF EXISTS `lovesound_appointment_sign`;
create table lovesound_appointment_sign
(
  id bigint primary key auto_increment,
  messageUserId bigint not null COMMENT '给这个用户报的名,也就是约见的创建者',
  appointmentId bigint not null COMMENT '关联的约见表id',
  content varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '报名的描述',
  readStatus char(1) not null DEFAULT 0 COMMENT '该留言是否被用户已读，0表示未读，1表示已读',
  status varchar(10) not null DEFAULT 'ACTIVE' COMMENT '数据状态,ACTIVE,DELETE,DISABLED,REPORT(举报状态,只有这个用户能举报留言的人)',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id,这个人报的名'
) COMMENT '约见报名表'  ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*约见报名表end*/


/***************************************2019-03-12 约见,已在本地执行，服务器已执行end*******************************************************/


/*推送模板消息表*/
DROP TABLE IF EXISTS `lovesound_template_msg`;
create table lovesound_template_msg
(
  id bigint primary key auto_increment,
  userId bigint not null COMMENT '推送给了谁 id',
  content varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci not null COMMENT '内容',
  createTime datetime not null COMMENT '创建时间',
  createUserId bigint COMMENT '创建人id,谁推送的'
) COMMENT '推送模板消息表'  ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*推送模板消息表end*/






