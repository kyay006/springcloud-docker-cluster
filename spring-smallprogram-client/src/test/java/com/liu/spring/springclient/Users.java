package com.liu.spring.springclient;

import com.liu.util.date.DateUtils;
import com.liu.util.string.StringUtils;

import java.util.Date;
import java.util.Random;

public class Users {

    public static void main(String[] args) {
        //生成用户数据
        Random random = new Random();
        StringBuilder sf = new StringBuilder(1000);
//        int i = 10;
        int i = 24;
        String sex = "";
        String nickName = "";
        String loveSound = "";//情音
        String age = "1990-06-04";//年龄
        String height = "";//身高
        String weihei = "";//体重kg
        String occ = "";//职业
        String aihao = "";
        String jingji = "";//经济状况


        i++;
        sex = "1";
        nickName = "骑着祥云";
        loveSound = "好好修行，好好做事，好好挣钱。";//情音
        age = "1989-01-01";//年龄
        height = "172";//身高
        weihei = "70";//体重kg
        occ = "创业";//职业
        aihao="创业";//爱好
        jingji = "尚可";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);




        i++;
        sex = "2";
        nickName = "耶";
        loveSound = "我是个比较成熟的人，像赌气不吃东西这种事都是吃饱后才做的";//情音
        age = "1995-01-01";//年龄
        height = "160";//身高
        weihei = "45";//体重kg
        occ = "兼职化妆师";//职业
        jingji = "无车无房无存款";//经济状况
        aihao="爱好美食，旅行";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        //(random.nextInt(20) + 150 )
        //(random.nextInt(12) + 45 )
        i++;
        sex = "2";
        nickName = "dengdeng";
        loveSound = "喜欢小动物，小孩，花花草草，大自然。对爱笑的，笑得开心的大男孩没有抵抗力。";//情音
        age = "1988-01-01";//年龄
        height = "" + (random.nextInt(20) + 150 );//身高
        weihei = "" + (random.nextInt(12) + 45 );//体重kg
        occ = "设计";//职业
        jingji = "";//经济状况
        aihao="喜欢服饰，自己会做衣服";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);




        i++;
        sex = "1";
        nickName = "腹黑的抖S";
        loveSound = "";//情音
        age = "1989-01-01";//年龄
        height = "175";//身高
        weihei = "80";//体重kg
        occ = "翻译（英语、日语、土耳其语）";//职业
        jingji = "尚可";//经济状况
        aihao="喜欢旅行；喜欢一切美好的事物";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "隔壁家的";
        loveSound = "不约";//情音
        age = "1997-01-01";//年龄
        height = "162";//身高
        weihei = "48";//体重kg
        occ = "公司职员";//职业
        jingji = "在成都有稳定的工作";//经济状况
        aihao="会和朋友一起玩玩游戏、健身、看电影、旅行（感觉自己喜欢的超多）";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "豆瓣酱";
        loveSound = "不约";//情音
        age = "1992-01-01";//年龄
        height = "155";//身高
        weihei = "48";//体重kg
        occ = "民企财务";//职业
        jingji = "有房";//经济状况
        aihao="朝夕相处";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "1";
        nickName = "zt.";
        loveSound = "偷看了就要关注！";//情音
        age = "1988-01-01";//年龄
        height = "171";//身高
        weihei = "60";//体重kg
        occ = "公司职员";//职业
        aihao="打球";//爱好
        jingji = "稳定";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "1";
        nickName = "班尼迪克s";
        loveSound = "I know how you feel inside.I've been there before";//情音
        age = "1990-01-01";//年龄
        height = "173";//身高
        weihei = "75";//体重kg
        occ = "创业";//职业
        aihao="电子产品发烧友，喜欢小动物、小狗，喜欢小孩子";//爱好
        jingji = "有车和房，一辆S500L的代步车";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "居咦";
        loveSound = "";//情音
        age = "1993-01-01";//年龄
        height = "162";//身高
        weihei = "48";//体重kg
        occ = "以前有个铁饭碗，现在辞职自己开了家店";//职业
        jingji = "有个小房子，车技太烂了还是滴滴更方便，经济独立，一直维持着自己小小资的生活";//经济状况
        aihao="喜欢吃吃喝喝玩玩，也很喜欢宅，爱听jazz，看书看剧打游戏。喜欢新东西";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "满天都是小鑫鑫";
        loveSound = "";//情音
        age = "1994-01-01";//年龄
        height = "160";//身高
        weihei = "48";//体重kg
        occ = "教育培训行业";//职业
        jingji = "生活工作能力强，性格独立";//经济状况
        aihao="无聊了和朋友约个饭逛逛街或者打个游戏，去健身房锻炼锻炼，日子过得自由潇洒";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "1";
        nickName = "时光改变了他";
        loveSound = "音乐人一枚";//情音
        age = "1990-01-01";//年龄
        height = "171";//身高
        weihei = "62";//体重kg
        occ = "国企有稳定的工作";//职业
        aihao="平时生活中喜欢旅行、电影以及走街串巷的吃好吃的";//爱好
        jingji = "卸下工作的同时又是一名独立音乐人，有个小小的音乐工作室";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "你说什么";
        loveSound = "";//情音
        age = "1994-01-01";//年龄
        height = "157";//身高
        weihei = "46";//体重kg
        occ = "正经职业，";//职业
        jingji = "月到手收入10k+。存款6位数。无房车";//经济状况
        aihao="健身、看书、看电影、看美剧（我口语还可以喔），今年夏天打算去学游泳，我可以做一些简单的菜";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "winding";
        loveSound = "不要套路我，我套路不过你";//情音
        age = "1991-01-01";//年龄
        height = "158";//身高
        weihei = "45";//体重kg
        occ = "程序员开发，";//职业
        jingji = "月入1万+";//经济状况
        aihao="爱好嘛，旅游，喜欢蓝天白云大海草原，深山野林类不喜欢，拍照，会习惯用拍照记录感觉很好的时刻，可是拍不好，k歌，特别喜欢周杰伦的歌，还有乱七八糟忘记名字的一大堆，打农药(目前星耀)，看看书，挣钱";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "_Mint";
        loveSound = "";//情音
        age = "1990-01-01";//年龄
        height = "158";//身高
        weihei = "45";//体重kg
        occ = "程序员开发，";//职业
        jingji = "月入1万+";//经济状况
        aihao="爱好嘛，旅游，喜欢蓝天白云大海草原，深山野林类不喜欢，拍照，会习惯用拍照记录感觉很好的时刻，可是拍不好，k歌，特别喜欢周杰伦的歌，还有乱七八糟忘记名字的一大堆，打农药(目前星耀)，看看书，挣钱";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);




        i++;
        sex = "2";
        nickName = "-";
        loveSound = "？";//情音
        age = "1993-01-01";//年龄
        height = "158";//身高
        weihei = "44";//体重kg
        occ = "自己对外有点小投资，";//职业
        jingji = "在成都有房有车 收入大概就是税后15+的样子";//经济状况
        aihao="常年健身 爱好是睡瞌睡喝咖啡和工作";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "1";
        nickName = "差不多先生";
        loveSound = "沙雕网友";//情音
        age = "1992-01-01";//年龄
        height = "170";//身高
        weihei = "68";//体重kg
        occ = "互联网公司上班";//职业
        aihao="学习、看电影、逛街";//爱好
        jingji = "在高新新北已购房，个人偿还贷款，没有车";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);




        i++;
        sex = "2";
        nickName = "琪琪";
        loveSound = "有朵云";//情音
        age = "1992-01-01";//年龄
        height = "156";//身高
        weihei = "48";//体重kg
        occ = "公司职员";//职业
        jingji = "在成都有稳定的工作";//经济状况
        aihao="初闻不知曲中意，再听已是曲中人";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);




        i++;
        sex = "2";
        nickName = "芒果";
        loveSound = "一个很二的宅女。时而活泼时而文静。阴晴难定。";//情音
        age = "1991-01-01";//年龄
        height = "157";//身高
        weihei = "42";//体重kg
        occ = "公司职员";//职业
        jingji = "在成都有稳定的工作";//经济状况
        aihao="世界很大，愿与对的人一起携手看遍祖国的大好河山";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "刺猬";
        loveSound = "不知原谅什麽 诚觉世事尽可原谅.";//情音
        age = "1994-01-01";//年龄
        height = "155";//身高
        weihei = "45";//体重kg
        occ = "在城北建设路一家做宠物医疗器械的公司做销售助理";//职业
        jingji = "家庭普通";//经济状况
        aihao="没有想遇上高富帅的情结，比起长相更在意对方的性格";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);




        i++;
        sex = "2";
        nickName = "嬿";
        loveSound = "";//情音
        age = "1992-01-01";//年龄
        height = "156";//身高
        weihei = "45";//体重kg
        occ = "事业单位";//职业
        jingji = "10+w";//经济状况
        aihao="性格开朗，比较笨，情商不高，喜欢美食，会做简单的饭菜，工作节奏不快。希望遇到能携手同行的那个你。";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "1";
        nickName = "Fall";
        loveSound = "素履以往";//情音
        age = "1990-01-01";//年龄
        height = "168";//身高
        weihei = "62";//体重kg
        occ = "IT类行业";//职业
        aihao="我重来不觉得高尔夫，室内泳池，名人派对或者我无法想象的那些生活方式可以提升多少所谓的精神境界，或者我不赞同这里有一个标准——富人定义的标准。所以我还是觉得我们在以后的生活里应该有个书房，多读书，能多去看看世界也可以，但是千万别强求";//爱好
        jingji = "刚毕业一年接近二十的样子，家庭物质普通不拖后腿，没什么负担";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "米砾";
        loveSound = "喜欢下午的阳光。";//情音
        age = "1992-01-01";//年龄
        height = "165";//身高
        weihei = "50";//体重kg
        occ = "工作和居住都在西门，行政岗工作";//职业
        jingji = "普通";//经济状况
        aihao="性格开朗，好相处，外形也还好";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "2";
        nickName = "自由的小白羊";
        loveSound = "94年的小姐姐 长相可爱～在意外貌和学识！骗p的勿扰！";//情音
        age = "1994-01-01";//年龄
        height = "159";//身高
        weihei = "43";//体重kg
        occ = "行政";//职业
        jingji = "普通";//经济状况
        aihao="听民谣，看书、综艺、日剧，喜欢游泳。一年外出旅游1-2次，看看风景，体验当地美食。";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "1";
        nickName = "夜月下";
        loveSound = "唯美主义者。书虫。";//情音
        age = "1990-01-01";//年龄
        height = "170";//身高
        weihei = "65";//体重kg
        occ = "软件行业";//职业
        aihao="喜欢沉浸在别样的世界里，漫画小说游戏都很喜欢，曾经为了能够无障碍的看漫画，玩游戏自学日语，可以啃原文小说的水平吧";//爱好
        jingji = "在成都购得一房还贷中";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "Kiko";
        loveSound = "";//情音
        age = "1993-01-01";//年龄
        height = "162";//身高
        weihei = "48";//体重kg
        occ = "财务";//职业
        jingji = "正处于一人吃饱全家不饿的状态";//经济状况
        aihao="不闹腾不内向偶尔冷漠偶尔撒娇像小孩的双子座";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



        i++;
        sex = "1";
        nickName = "LAY";
        loveSound = "";//情音
        age = "1990-01-01";//年龄
        height = "172";//身高
        weihei = "70";//体重kg
        occ = "私企工作";//职业
        aihao="喜欢做饭 看电影 看书 健身之类的";//爱好
        jingji = "稳定";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "悄悄咪咪喜欢你";
        loveSound = "人都矛盾，渴望被理解又怕被看穿。";//情音
        age = "1991-01-01";//年龄
        height = "153";//身高
        weihei = "42";//体重kg
        occ = "互联网公司工作";//职业
        jingji = "不富裕也不拖后腿";//经济状况
        aihao="比较喜欢吃，为了吃好吃的可以去很远地方";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "桔梗";
        loveSound = "";//情音
        age = "1996-01-01";//年龄
        height = "158";//身高
        weihei = "45";//体重kg
        occ = "互联网公司工作";//职业
        jingji = "目前无车无房，计划今年下半年给自己买一辆小代步车";//经济状况
        aihao="喜欢看有关自己工作相关的书，也喜欢看历史，哲学类的书籍，还有一个爱好就是喜欢上B站看搞笑视频";//爱好
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "1";
        nickName = "LittlePanda";
        loveSound = "可能我撞了南墙才会回头吧";//情音
        age = "1993-01-01";//年龄
        height = "170";//身高
        weihei = "65";//体重kg
        occ = "体制内工作，建设系统";//职业
        aihao="喜欢运动，尤其爱游泳";//爱好
        jingji = "家里有房，自己也在一直摇号买房中";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "KK";
        loveSound = "";//情音
        age = "1994-01-01";//年龄
        height = "160";//身高
        weihei = "45";//体重kg
        occ = "互联网公司";//职业
        aihao="猫控 美剧 电影 游泳 旅行 NBA 世界杯";//爱好
        jingji = "稳定";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "1";
        nickName = "我不是王林";
        loveSound = "天生低调，却无奈太多艺术细菌";//情音
        age = "1992-01-01";//年龄
        height = "172";//身高
        weihei = "62";//体重kg
        occ = "互联网设计打杂";//职业
        aihao="乒乓/网球/跑步/唱歌/影视/农耀/bbox等";//爱好
        jingji = "尚可";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "盛夏的暖阳";
        loveSound = "总要允许有人错过你，才会赶上最好的相遇";//情音
        age = "1992-01-01";//年龄
        height = "155";//身高
        weihei = "46";//体重kg
        occ = "公司职员";//职业
        aihao="喜欢旅行，徒步爬山";//爱好
        jingji = "在成都有稳定的工作";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "2";
        nickName = "Xxx";
        loveSound = "";//情音
        age = "1993-01-01";//年龄
        height = "165";//身高
        weihei = "49";//体重kg
        occ = "创业";//职业
        aihao="去了国外30多个城市，蹦极 ，跳伞 ，看极光 喜欢一切新鲜的事情。";//爱好
        jingji = "有自己独立住房，南门，三居，期房。";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        i++;
        sex = "1";
        nickName = "星星点灯";
        loveSound = "";//情音
        age = "1990-01-01";//年龄
        height = "173";//身高
        weihei = "62";//体重kg
        occ = "高新区从事技术类工作";//职业
        aihao="春暖花开的季节，希望对的时间遇见对的人。";//爱好
        jingji = "而立之年，生活工作各方面都稳定下来";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);


        System.out.println(sf.toString());


    }



/*

女

        i++;
        sex = "2";
        nickName = "隔壁家的";
        loveSound = "不约";//情音
        age = "1997-01-01";//年龄
        height = "162";//身高
        weihei = "48";//体重kg
        occ = "公司职员";//职业
        aihao="会和朋友一起玩玩游戏、健身、看电影、旅行（感觉自己喜欢的超多）";//爱好
        jingji = "在成都有稳定的工作";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);



男
        i++;
        sex = "1";
        nickName = "骑着祥云";
        loveSound = "好好修行";//情音
        age = "1990-01-01";//年龄
        height = "170";//身高
        weihei = "70";//体重kg
        occ = "公司职员";//职业
        aihao="创业";//爱好
        jingji = "尚可";//经济状况
        getSql(i, sex, nickName, loveSound, age, height, weihei, occ, jingji,aihao, sf, random);






 */


    private static void getSql(int i,
                               String sex,
                               String nickName,
                               String loveSound,
                               String age,//年龄
                               String height,
                               String weihei,
                               String occ,
                               String jingji,
                               String aihao,
                               StringBuilder sf,
                               Random random){
        if(loveSound.indexOf("'") > -1){
            loveSound = " \"" + loveSound + "\" ";
        }else{
            loveSound = " '" + loveSound + "' ";
        }
        int year = DateUtils.yearsBetween(DateUtils.parserDate(age, "yyyy-MM-dd"), new Date());
        String temp = "INSERT INTO `wx_user` VALUES (" + i + ", '" + StringUtils.getCharAndNumrLowerCase(28) + "', '" + nickName + "', 'https://wx.qlogo.cn/mmopen/vi_32/ayw7UwwaL37ku6HXsWKmOzWwhg8K1SlEXHeiaL3v37CHM2XYSJ94TLG6LVTGCEYBRCRHD8Xvpoe7GL1LY3fUXyg/132', " +
                "NULL, NULL, " + sex + ", 'France', 'Paris', '', 'zh_CN', 'ACTIVE','" + nickName + "', 'https://iqingyin.com/resource/11/HeadImage/20190228/" + i + ".jpg'," +
                " " + loveSound + ", '" + jingji + "', " +
                "'" + age + "', " + year + ", " + height + ", " + weihei + ", 2, 0, '=" + (random.nextInt(3) + 1 ) + "=,=" + (random.nextInt(3) + 4 ) + "=,=" + (random.nextInt(3) + 7 ) + "=', '" + occ + "', " +
                "'=" + (random.nextInt(3) + 1 ) + "=,=" + (random.nextInt(3) + 4 ) + "=,=" + (random.nextInt(3) + 7 ) + "=', '" + aihao + "',NULL, '2019-01-30 14:42:42', NULL);";
        sf.append(temp).append("\r\n");
    }





}
