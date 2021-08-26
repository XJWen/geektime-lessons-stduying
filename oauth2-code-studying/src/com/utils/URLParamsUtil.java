package com.utils;

import java.util.Map;
import java.util.Set;

public class URLParamsUtil {

    /**
     * 将参数附加在url后
     *
     * @param url  网络访问地址
     * @param params  body参数
     * */
    public static String appendParams(String url, Map<String,String> params){
        if (url==null){
            return "" ;
        }else if (params.isEmpty()){
            //去除空格
            return url.trim();
        }else {
            StringBuffer result = new StringBuffer();
            StringBuffer buffer = new StringBuffer("");
            Set<String> keys = params.keySet();

            for (String key : keys){
                buffer.append(key)
                        .append("=")
                        .append(params.get(key))
                        .append("&");
            }
            //去掉最后的&
            buffer.deleteCharAt(buffer.length()-1);

            url = url.trim();
            int length = url.length();
            //取?号位置
            int index = url.indexOf("?");
            if (index>-1){
                if ((length - 1)== index){
                    //url最后一个符号为？，如：http://wwww.baidu.com?
                    result.append(url);
                }else {
                    //情况为：http://wwww.baidu.com?aa=11
                    result.append(url).append("&");
                }
            }else {
                //url后面没有问号，如：http://wwww.baidu.com
                result.append(url).append("?");
            }
            result.append(buffer.toString());
            return  result.toString();
        }

    }

}
