package com.liu.spring.domain.commoninfo;

/**
 * Created by lang.liu on 2017/1/5.
 */
public enum  Status
{
    /*状态*/
    ACTIVE("启用","ACTIVE"),DELETE("删除","DELETE"),DISABLED("禁用","DISABLED"),REPORT("举报","REPORT"),

    /*userStatus 小黑屋用户状态*/
    Cage("被关在小黑屋","0"),Release("已释放","1"),

    /*存redis里的sessionid openid userid 拼接的字符串*/
    AND("拼接使用", "&&&"),


    /*imgType key图片管理*/
    HeadImage("用户头像图片","0"),PhotoImage("用户相册图片","1"),DynamicImage("用户动态图片","2"),
    ChatMsgImage("用户聊天图片","3"),UpWallImage("订阅号上墙图片","4"),ArticleImage("用户文章图片","5"),AppointmentImage("约见图片","6"),

    //关注 粉丝
    取消关注("取消关注", "0"),关注("关注", "1"),

    //聊天消息的类型
    文字("文字", "0"),图片("图片", "1"),


    //登录日志
    授权("0表示授权", "0"),登录("1表示登录", "1"),


    //微信订阅号上墙审核状态
    未审核("0表示未审核", "0"),审核失败("1表示审核失败", "1"),审核成功但未发布("2表示审核成功但未发布", "2"),
    已发布("3表示已发布", "3"),


    /*readStatus 文章评论读状态*/
    UnRead("未读","0"),HaveRead("已读","1"),

    /*zanStatus 文章的点赞状态*/
    FallZan("倒赞","0"),Zan("点赞","1"),


    /*举报使用的 功能列表,这个是因为没有建表，所以这里定义，是存到举报表里的*/
    ArticleReport("文章","0"),
    /*举报的各个功能，每个用户当前能举报的次数*/
    ArticleReportCount("文章功能当天举报次数","10")

    ;

    private String name;
    private String value;

    private Status(String name,String value) {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }
    public String getValue()
    {
        return value;
    }

}