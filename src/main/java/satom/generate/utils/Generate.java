package satom.generate.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class Generate {

    private Connection    connection            = null;
    private ResultSet     rs                    = null;
    private File          entityFile            = null;
    private File          daoFile               = null;
    private File          daoImplFile           = null;
    private File          serviceFile           = null;
    private File          serviceImplFile       = null;

    /**
     * 数据库配置
     */
    private String        url                   = "";
    private String        name                  = "";
    private String        pass                  = "";
    private String        driver                = "";
    // 作者
    private String        author                = "";
    // 过滤数据库表名称
    private String        prefix                = "";

    // 实体包名
    private String        entityPackageName     = "";
    // Dao包名
    private String        daoPackageName        = "";

    // Service包名
    private String        servicePackageName    = "";
    // 模板路径
    private String        tempPath              = "";
    // JAVA文件路径
    private String        basepath              = "";
    private String        javaPath              = "";

    private Configuration configuration;
    private String        pageEncoding          = "UTF-8";

    private boolean       isDao                 = true;
    private boolean       isService             = true;

    // 初始化
    {
        try {
            tempPath = new Object() {

                public String getPath() {
                    return this.getClass().getResource("").getPath();
                }
            }.getPath();
            configuration = new Configuration();
            configuration.setDirectoryForTemplateLoading(new File(tempPath));
            configuration.setEncoding(Locale.getDefault(), pageEncoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成代码范例
     * 
     * @param args
     * @author pretent
     * @date 2017年5月5日 下午5:46:25
     */
    public static void main(String[] args) {
        Generate g = new Generate();
        g.setBasepath("C:\\Users\\pretent\\Desktop\\code");
        g.setAuthor("pretent");
        g.setPrefix("T_MAIN_");

        g.setUrl("jdbc:oracle:thin:@172.16.10.28:1521:orcl");
        g.setDriver("oracle.jdbc.driver.OracleDriver");
        g.setName("phpatient");
        g.setPass("phpatient");

        g.setEntityPackageName("com.senyintxa.phpatient.main.entity");
        g.setDaoPackageName("com.senyintxa.phpatient.main.dao");
        g.setServicePackageName("com.senyintxa.phpatient.main.service");

        g.setDao(true);
        g.setService(true);
        g.generate("T_MAIN_AUTH_BING");

    }

    public void generate(String... tables) {
        if (tables.length <= 0) {
            throw new NullPointerException("请输入要生成表的名称");
        }
        // 创建文件存储路径
        initConfig();
        for (String table : tables) {
            this.generateEntity(table);
        }

    }

    /**
     * 生成JAVA文件
     * 
     * @param entity
     * @author pretent
     * @date 2017年5月5日 下午5:46:31
     */
    public void generateJava(Entity entity) {
        try {
            String entityName = TempUtil.firstUpperCase(TempUtil.camelName(entity.getName().replaceFirst(prefix, "")));
            Template temp = configuration.getTemplate("Entity.ftl", pageEncoding);
            temp.setEncoding(pageEncoding);
            String primaryKeyName = "";
            String primaryKeyType = "";
            String isExistIfdel = "false";
            for (Column column : entity.getColumns()) {
                if (column.getIsPrimary().equals("true")) {
                    primaryKeyName = column.getName();
                    primaryKeyType = column.getType();
                }
                if (("IF_DEL").equals(column.getName())) {
                    isExistIfdel = "true";
                }
            }
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("typeMapping", TypeMapping.getMap());
            params.put("tempUtil", new TempUtil());
            params.put("blankString", "");
            params.put("author", author);
            params.put("date", SatomDateUtil.format(new Date()));
            params.put("primaryKeyName", primaryKeyName);
            params.put("primaryKeyType", primaryKeyType);
            params.put("isExistIfdel", isExistIfdel);
            params.put("isExistIfdel", isExistIfdel);


            // entity数据
            params.put("entityPackageName", entityPackageName);
            params.put("entityCommont", entity.getCommont());
            params.put("tableName", entity.getName().toLowerCase());
            params.put("entityName", entityName);
            params.put("entity", entity);
            // dao数据
            String daoClassName =  entityName + "Dao";
            params.put("daoPackageName", daoPackageName);
            params.put("daoClassName", daoClassName);
            // daoImpl数据
            String daoImplClassName = entityName + "DaoImpl";
            params.put("daoImplPackageName", daoPackageName + ".impl");
            params.put("daoImplClassName", daoImplClassName);
            //service数据
            String serviceClassName = entityName + "Service";
            params.put("servicePackageName", servicePackageName);
            params.put("serviceClassName", serviceClassName);
          //serviceImpl数据
            String serviceImplClassName = entityName + "ServiceImpl";
            params.put("serviceImplPackageName", servicePackageName + ".impl");
            params.put("serviceImplClassName", serviceImplClassName);
            

            // 生成Entity
            String outputPath = entityFile.getPath() + "/" + entityName + ".java";
            saveFile(outputPath, params, temp);
            if (isDao) {
                // 生成Business
                temp = configuration.getTemplate("Dao.ftl", pageEncoding);
                temp.setEncoding(pageEncoding);
                outputPath = daoFile.getPath() + "/" + daoClassName + ".java";
                saveFile(outputPath, params, temp);
                // 生成BusinessImpl
                temp = configuration.getTemplate("DaoImpl.ftl", pageEncoding);
                temp.setEncoding(pageEncoding);
                outputPath = daoFile.getPath() + "/impl/" + daoImplClassName + ".java";
                saveFile(outputPath, params, temp);
            }
            if (isService) {
                // 生成Business
                temp = configuration.getTemplate("Service.ftl", pageEncoding);
                temp.setEncoding(pageEncoding);
                outputPath = serviceFile.getPath() + "/" + serviceClassName + ".java";
                saveFile(outputPath, params, temp);
                // 生成BusinessImpl
                temp = configuration.getTemplate("ServiceImpl.ftl", pageEncoding);
                temp.setEncoding(pageEncoding);
                outputPath = serviceFile.getPath() + "/impl/" + serviceImplClassName + ".java";
                saveFile(outputPath, params, temp);
            }
            
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询数据库
     * 
     * @param tableName
     * @param isthisDao
     * @author pretent
     * @date 2017年5月5日 下午5:46:45
     */
    public void generateEntity(String... tableNames) {
        for (String tableName : tableNames) {
            try {
                Class.forName(driver);
                Properties props = new Properties();
                props.setProperty("user", name);
                props.setProperty("password", pass);
                props.setProperty("remarks", "true");
                props.setProperty("useInformationSchema", "true");
                connection = DriverManager.getConnection(url, props);
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                String user = databaseMetaData.getUserName();
                rs = databaseMetaData.getTables(null, user, tableName, new String[] { "TABLE" });
                while (rs.next()) {
                    String table = rs.getString("TABLE_NAME");
                    String tableCommont = rs.getString("REMARKS");
                    Entity entity = new Entity();
                    entity.setName(table);
                    entity.setCommont(tableCommont == null ? "" : tableCommont.trim());
                    entity.setAuthor(author);

                    if (tableName.equals(table)) {
                        List<Column> columns = new ArrayList<Column>();
                        ResultSet columnRs = databaseMetaData.getColumns(null, user, tableName.toUpperCase(), null);
                        while (columnRs.next()) {
                            String columnCommont = columnRs.getString("REMARKS");
                            Column column = new Column();
                            column.setName(columnRs.getString("COLUMN_NAME"));
                            column.setCommont(columnCommont == null ? "" : columnCommont.trim());
                            column.setIsPrimary("false");
                            String typeName = columnRs.getString("TYPE_NAME");
                            System.out.println(typeName);
                            if (typeName.indexOf("(") > 0 && typeName.indexOf(")") > 0) {
                                typeName = typeName.substring(0, typeName.indexOf("("));
                            }
                            column.setType(typeName);
                            columns.add(column);
                        }
                        entity.setColumns(columns);
                    }

                    ResultSet plRs = databaseMetaData.getPrimaryKeys(null, user, tableName);
                    int rowNumber = 0;
                    while (plRs.next()) {
                        rowNumber = plRs.getRow();
                        String pkColumnName = plRs.getString("COLUMN_NAME");
                        for (Column itemColumn : entity.getColumns()) {
                            if (itemColumn.getName().equals(pkColumnName)) {
                                itemColumn.setIsPrimary("true");
                            }
                        }
                    }
                    // 当主键为多个是 我们使用逻辑主键，规则为实体名_id或实体名首字母_id
                    if (rowNumber > 1) {
                        String pName = "";
                        String tName = entity.getName().replaceFirst(prefix, "");
                        if (tName.indexOf("_") > 0) {
                            String[] names = tName.split("_");
                            for (String name : names) {
                                pName += name.substring(0, 1);
                            }
                            pName += "_ID";
                        } else {
                            pName = tName + "_ID";
                        }
                        for (Column itemColumn : entity.getColumns()) {
                            itemColumn.setIsPrimary("false");
                            if (itemColumn.getName().equals(pName)) {
                                itemColumn.setIsPrimary("true");
                            }
                        }
                    }
                    generateJava(entity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成几个文件夹
     * 
     * @author pretent
     * @date 2017年5月4日 下午2:13:30
     */
    private void initConfig() {

        if (StringUtils.isBlank(url)) {
            throw new NullPointerException("请调用setUrl设置数据库url.");
        }
        if (StringUtils.isBlank(driver)) {
            throw new NullPointerException("请调用setDriver设置数据库驱动名称.");
        }
        if (StringUtils.isBlank(name)) {
            throw new NullPointerException("请调用setName设置数据库用户名.");
        }
        if (StringUtils.isBlank(pass)) {
            throw new NullPointerException("请调用setPass设置数据库密码.");
        }
        if (StringUtils.isBlank(author)) {
            throw new NullPointerException("请调用setEntityPackageName设置实体类具体包名.");
        }
        if (StringUtils.isBlank(prefix)) {
            throw new NullPointerException("请调用setEntityPackageName设置实体类具体包名.");
        }
        if (StringUtils.isBlank(author)) {
            throw new NullPointerException("请调用setAuthor设置注释作者.");
        }
        if (StringUtils.isBlank(prefix)) {
            throw new NullPointerException("请调用setPrefix设置实体名称过滤内容.");
        }
        if (StringUtils.isBlank(entityPackageName)) {
            throw new NullPointerException("请调用setEntityPackageName设置实体类具体包名.");
        }
        if (StringUtils.isBlank(daoPackageName)) {
            throw new NullPointerException("请调用setDaoPackageName设置具体Dao包名.");
        }
        if (StringUtils.isBlank(servicePackageName)) {
            throw new NullPointerException("请调用setServicePackageName设置具体Service包名.");
        }

        if (StringUtils.isBlank(basepath)) {
            throw new NullPointerException("请调用setBasepath设置JAVA文件路径");
        }
        String entityFilePath = entityPackageName.replace(".", "/");
        String daoFilePath = daoPackageName.replace(".", "/");
        String serviceFilePath = servicePackageName.replace(".", "/");

        entityFile = new File(javaPath + entityFilePath);
        daoFile = new File(javaPath + daoFilePath);
        daoImplFile = new File(javaPath + daoFilePath + "/impl");
        serviceFile = new File(javaPath + serviceFilePath);
        serviceImplFile = new File(javaPath + serviceFilePath + "/impl");
        if (!entityFile.exists()) {
            entityFile.mkdirs();
        }
        if (!daoFile.exists()) {
            daoFile.mkdirs();
        }

        if (!daoImplFile.exists()) {
            daoImplFile.mkdirs();
        }

        if (!serviceFile.exists()) {
            serviceFile.mkdirs();
        }

        if (!serviceImplFile.exists()) {
            serviceImplFile.mkdirs();
        }

    }

    private void saveFile(String path, Object content, Template template) {
        try {
            File target = new File(path);
            Writer out = new OutputStreamWriter(new FileOutputStream(target), pageEncoding);
            template.process(content, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置基础路径
     * 
     * @param basepath
     * @author pretent
     * @date 2017年6月1日 下午7:20:07
     */
    public void setBasepath(String basepath) {
        this.basepath = basepath;
        this.javaPath = basepath + "/java/";

    }

    /**
     * 设置实体包名
     * 
     * @param entityPackageName
     * @author pretent
     * @date 2017年6月1日 下午7:48:04
     */
    public void setEntityPackageName(String entityPackageName) {
        this.entityPackageName = entityPackageName;
    }

    /**
     * 设置Business包名
     * 
     * @param daoPackageName
     * @author pretent
     * @date 2017年6月1日 下午7:48:24
     */
    public void setDaoPackageName(String daoPackageName) {
        this.daoPackageName = daoPackageName;
    }

    public void setServicePackageName(String servicePackageName) {
        this.servicePackageName = servicePackageName;
    }


    /**
     * 设置数据库连接url
     * 
     * @param url
     * @author pretent
     * @date 2017年6月1日 下午7:55:47
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置数据库连接用户名
     * 
     * @param url
     * @author pretent
     * @date 2017年6月1日 下午7:55:47
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置数据库连接密码
     * 
     * @param url
     * @author pretent
     * @date 2017年6月1日 下午7:55:47
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * 设置数据库连接驱动
     * 
     * @param url
     * @author pretent
     * @date 2017年6月1日 下午7:55:47
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * 设置注释作者
     * 
     * @param author
     * @author pretent
     * @date 2017年6月1日 下午7:58:53
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 设置实体类名称过滤内容
     * 
     * @param author
     * @author pretent
     * @date 2017年6月1日 下午7:58:53
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDao(boolean isDao) {
        this.isDao = isDao;
    }

    public void setService(boolean isService) {
        this.isService = isService;
    }

}
