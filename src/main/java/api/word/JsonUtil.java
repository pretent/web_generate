package api.word;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {

    public static String toJson(Object obj) {
        System.out.println(obj);
        return JSON.toJSONString(obj,SerializerFeature.WRITE_MAP_NULL_FEATURES);
    }
}
