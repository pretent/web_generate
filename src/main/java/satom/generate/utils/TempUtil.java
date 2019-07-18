package satom.generate.utils;

public class TempUtil {

    /**
     * 转驼峰
     * 
     * @param name
     * @return
     * @author pretent
     * @date 2017年5月3日 下午4:52:32
     */
    public static String camelName(String name) {
        name = name.toLowerCase();
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母小写
            return name.substring(0, 1).toLowerCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String camels[] = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.toLowerCase());
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }

    /**
     * 功能：将输入字符串的首字母改成大写
     * 
     * @param str
     * @return
     * @author pretent
     * @date 2017年5月4日 上午11:36:41
     */
    public static String firstUpperCase(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 功能：将输入字符串的首字母改成小写
     * 
     * @param str
     * @return
     * @author pretent
     * @date 2017年5月4日 上午11:36:41
     */
    public static String firstLowerCase(String str) {

        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char) (ch[0] + 32);
        }
        return new String(ch);
    }
    
    
    public static String isDate(Column column){
        if(("Date").equals(TypeMapping.getType(column.getType()))){
            return "true";
        }else{
            return "false";
        }
    }

}
