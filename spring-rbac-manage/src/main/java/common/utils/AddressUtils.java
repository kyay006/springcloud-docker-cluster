package common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取地址类
 * 
 * @author wave
 */
public class AddressUtils
{
    private static final Logger log = LoggerFactory.getLogger(AddressUtils.class);

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip)
    {
        String address = "";
        try
        {
//            address = HttpUtils.sendPost(IP_URL, "ip=" + ip);
//            JSONObject json = JSONObject.parseObject(address);
//            JSONObject object = json.getObject("data", JSONObject.class);
//            String region = object.getString("region");
//            String city = object.getString("city");
//            address = region + " " + city;
            address = ip;
        }
        catch (Exception e)
        {
            log.error("根据IP获取所在位置----------错误消息：" + e.getMessage());
        }
        return address;
    }
}
