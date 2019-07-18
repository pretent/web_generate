package api.word;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 生成API文档
 *
 * @author pretent
 * @date 2017年10月2日 下午5:46:05
 * @since jdk 1.8
 */
public class BaseDao {

    // MySQL连接参数
    private static final String URL          = "jdbc:mysql://172.16.30.246:3306/rap_db?serverTimezone=UTC";
    private static final String USER         = "root";
    private static final String PASSWORD     = "root";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";

    // 注册驱动
    static {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到连接对象
     * 
     * @return
     */
    public Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭连接对象
     */
    public void closeAll(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
            if (stmt != null) {
                stmt.close();
                stmt = null;
            }
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    /**
     * 关闭连接对象
     */
    public void closeAll(Connection conn, Statement stmt) {
        this.closeAll(conn, stmt, null);
    }

    /**
     * 通用的增删改的方法
     * 
     * @return 返回影响的行数
     */
    public int executeUpdate(String sql, Object[] params) {
        // 得到连接对象
        Connection conn = null;
        PreparedStatement stmt = null;
        // 影响的行数
        int rows = 0;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            // 得到参数个数
            ParameterMetaData pmd = stmt.getParameterMetaData();
            // 设置参数
            for (int i = 0; i < pmd.getParameterCount(); i++) {
                stmt.setObject(i + 1, params[i]);
            }
            rows = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, stmt);
        }
        return rows;
    }

    /**
     * 通用的查询方法，封装成List<T> 使用了泛型方法 使用这个方法的前提：数据库表的字段名称和JavaBean的属性名称保持一致 在这个方法调用了 BeanUtils.setProperty(obj, columnName,
     * value) 来实现，不然就要使用反射来完成类似的操作了
     */
    public <T> List<T> find(String sql, Object[] params, Class<T> clazz) {
        // 得到连接对象
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        // 创建集合
        List<T> list = new ArrayList<T>();
        try {
            conn = getConnection();
            stmt = conn.prepareStatement(sql);
            // 得到参数的个数
            ParameterMetaData pmd = stmt.getParameterMetaData();
            for (int i = 0; i < pmd.getParameterCount(); i++) {
                // 给参数赋值
                stmt.setObject(i + 1, params[i]);
            }
            // 运行得到结果集
            rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            // 遍历结果集
            while (rs.next()) {
                T obj = clazz.newInstance(); // 实例化对象
                // 得到结果集的列数
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    // 得到列名
                    String colName = rsmd.getColumnName(i + 1);
                    // 列号从1开始，得到每1列的值
                    Object value = rs.getObject(colName);
                    // 把名字和值赋值到对象中

                    colName = camelName(colName);
                    BeanUtils.setProperty(obj, colName, value);
                }
                // 添加到列表中
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(conn, stmt, rs);
        }
        return list;
    }

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

    public String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

}
