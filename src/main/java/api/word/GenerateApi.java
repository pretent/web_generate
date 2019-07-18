package api.word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class GenerateApi {

    private static String        tempPath     = "";
    // 文档路径
    private static String        docpath      = "";

    private static Configuration configuration;
    private static String        pageEncoding = "UTF-8";

    // 初始化
    static {
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

    public static void main(String[] args) {
        try {
            Template temp = configuration.getTemplate("api.xml", pageEncoding);
            temp.setEncoding(pageEncoding);
            docpath = "C:/Users/pretent/Desktop/api/api.doc";
            Map<String, Object> params = new HashMap<String, Object>();

            String sql = "select * from tb_module where project_id=1";
            BaseDao baseDao = new BaseDao();
            List<Module> modules = baseDao.find(sql, null, Module.class);
            for (Module module : modules) {
                sql = "select * from tb_page where module_id=?";
                List<Page> pages = baseDao.find(sql, new Object[] { module.getId() }, Page.class);
                module.setPageList(pages);
                for (Page page : pages) {
                    page.setModule(module);
                    sql = "select * from tb_action where id in(select action_id from tb_action_and_page where page_id=?)";
                    List<Action> actions = baseDao.find(sql, new Object[] { page.getId() }, Action.class);
                    page.setActionList(actions);
                    for (Action action : actions) {

                        sql = "select * from tb_parameter where id in(select parameter_id from tb_request_parameter_list_mapping where action_id=?)";
                        List<Parameter> requestParameterList = baseDao.find(sql, new Object[] { action.getId() }, Parameter.class);

                        action.setRequestParameterList(requestParameterList);
                        for (Parameter parameter : requestParameterList) {
                            parameter.setAction(action);
                            parameter.setParentName("root");
                            setParams(parameter);
                        }
                        sql = "select * from tb_parameter where id in(select parameter_id from tb_response_parameter_list_mapping where action_id=?)";
                        List<Parameter> responseParameterList = baseDao.find(sql, new Object[] { action.getId() }, Parameter.class);
                        action.setResponseParameterList(responseParameterList);
                        for (Parameter parameter : responseParameterList) {
                            parameter.setAction(action);
                            parameter.setParentName("root");
                            setParams(parameter);
                        }

                    }
                }
            }
            params.put("modules", modules);
            params.put("jsonUtil", new JsonUtil());
            saveFile(docpath, params, temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setParams(Parameter parameter) {
       
        BaseDao baseDao = new BaseDao();
        String sql = "select * from tb_parameter where id in(select parameter_id from tb_complex_parameter_list_mapping where complex_parameter_id=?)";
        List<Parameter> requestParameterList = baseDao.find(sql, new Object[] { parameter.getId() }, Parameter.class);
        for (Parameter item : requestParameterList) {
            item.setParentName(parameter.getIdentifier());
            setParams(item);
        }
        parameter.setParameterList(requestParameterList);
    }

    private static void saveFile(String path, Object content, Template template) {
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

}
