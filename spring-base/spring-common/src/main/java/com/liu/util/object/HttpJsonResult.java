package com.liu.util.object;


/**
 * Created by lang.liu on 2018/03/23.
 * 请求返回格式对象
 */
public class HttpJsonResult
{
    String resultCode;
    String resultInfo;
    Object resultData;
    public final static String SUCCESS = "200";
    public final static String ERROR   = "501";

    public HttpJsonResult(String resultCode, String resultInfo, Object resultData)
    {
        this.resultCode = resultCode;
        this.resultInfo = resultInfo;
        this.resultData = resultData;
    }

    public static HttpJsonResult SUCCESS()
    {
        return new HttpJsonResult(SUCCESS,null,null);
    }

    public static HttpJsonResult SUCCESS(String resultInfo)
    {
        return new HttpJsonResult(SUCCESS,resultInfo,null);
    }

    public static HttpJsonResult SUCCESS(Object resultData)
    {
        return new HttpJsonResult(SUCCESS,"",resultData);
    }

    public static HttpJsonResult SUCCESS(String resultInfo,Object resultData)
    {
        return new HttpJsonResult(SUCCESS,resultInfo,resultData);
    }

    public static HttpJsonResult FAIL()
    {
        return new HttpJsonResult(ERROR,null,null);
    }

    public static HttpJsonResult FAIL(String resultInfo)
    {
        return new HttpJsonResult(ERROR,resultInfo,null);
    }

    public static HttpJsonResult FAIL(Object resultData)
    {
        return new HttpJsonResult(ERROR,"",resultData);
    }

    public static HttpJsonResult FAIL(String resultInfo,Object resultData)
    {
        return new HttpJsonResult(ERROR,resultInfo,resultData);
    }






    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    public Object getResultData() {
        return resultData;
    }

    public void setResultData(Object resultData) {
        this.resultData = resultData;
    }
}
