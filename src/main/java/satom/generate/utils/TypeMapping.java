package satom.generate.utils;

import java.util.HashMap;
import java.util.Map;

public class TypeMapping {

    private static Map<String, String> typeMap = new HashMap<String, String>();

    public static void registerType(String key, String value) {
        typeMap.put(key, value);
    }

    public static Map<String, String> getMap() {
        return typeMap;
    }

    public static String getType(String key) {
        return typeMap.get(key);
    }

    /**
     * 默认4种映射 Creates a new instance of TypeMapping.
     */
    static {
        registerType("VARCHAR2", "String");
        registerType("NUMBER", "Integer");
        registerType("DATE", "Date");
        registerType("TIMESTAMP", "Date");
        registerType("CHAR", "String");
        registerType("NCHAR", "String");
        registerType("NVARCHAR2", "String");
        registerType("LONG", "Long");
        registerType("FLOAT", "Float");
        registerType("CLOB", "String");
        
    }
}
