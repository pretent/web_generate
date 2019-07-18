package ${controllerPackageName};

import ${entityPackageName}.${entityName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import satom.base.code.annotation.RequestPackagePath;
import satom.base.code.annotation.Resource;
import satom.base.code.annotation.Resources;
import satom.base.code.annotation.Operation;
import satom.base.code.annotation.Operations;
import satom.base.code.mode.Page;
import satom.base.code.annotation.ListDomain;
import satom.base.bridge.model.ResultObject;
import satom.base.code.annotation.ToKen;
import satom.base.view.controller.impl.BaseController;
import ${daoPackageName}.${daoClassName};
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
* @${entity.commont}控制器
* 
* @author ${author}
* @date ${date}
*/
@Controller
@RequestMapping("${requestMappingPrefix}${resourcePath}")
@RequestPackagePath("${requestPackagePath}${resourcePath}")
@Resources(value = { @Resource(name = "${entity.getCommont()}列表", code = "${controllerClassName}.list", invokeMethod = "list", desc = "${entity.getCommont()} 列表")})
@Operations(value = { @Operation(name = "新增${entity.getCommont()}", code = "${controllerClassName}.add", invokeMethod = "add", desc = "新增${entity.getCommont()}"),
                      @Operation(name = "编辑${entity.getCommont()}", code = "${controllerClassName}.edit", invokeMethod = "edit", desc = "编辑${entity.getCommont()}"),
                      @Operation(name = "删除${entity.getCommont()}", code = "${controllerClassName}.delete${entityName}", invokeMethod = "delete${entityName}", desc = "删除${entity.getCommont()}")})
public class ${controllerClassName}  extends BaseController{
	@Autowired
	private  ${daoClassName} ${daoClassName?substring(1)?uncap_first };
	
	/**
	* 跳往${entity.commont}列表页面
	* 
	* @author ${author}
	* @date ${date}
	*/
	@RequestMapping("/list")
	public String list(){
		return "list";
	}
	
	/**
	* 获取${entity.commont}分页数据
	* 
	* @author ${author}
	* @date ${date}
	*/
	@RequestMapping("/get${entityName}Page")
	@ListDomain(${entityName}.class)
	@ResponseBody
	public Page<${entityName}> get${entityName}Page(Map<String,Object> params){
		<#if isExistIfdel=='true'>
			params.put("_ifDel", 0);
		</#if>
		return this.outPageBean(params);
	}
	
	/**
	* 跳往新增${entity.commont}页面
	* 
	* @author ${author}
	* @date ${date}
	*/
	@RequestMapping("/add")
	@ToKen
	public String add(Model model){
		model.addAttribute("view", "add");
		return "add";
	}
	
	/**
	* 跳往编辑${entity.commont}页面
	* 
	* @author ${author}
	* @date ${date}
	*/
	@RequestMapping("/edit")
	@ToKen
	public String edit(Model model,${typeMapping[primaryKeyType]} ${tempUtil.camelName(primaryKeyName)}){
		model.addAttribute("view", "edit");
		model.addAttribute(${daoClassName?substring(1)?uncap_first }.get${entityName}(${tempUtil.camelName(primaryKeyName)}));
		return "add";
	}
	
	/**
	* 新增或保存${entity.commont}数据
	* 
	* @author ${author}
	* @date ${date}
	*/
	@RequestMapping("/save${entityName}")
	@ResponseBody
	public ResultObject save${entityName}(${entityName} ${entityName?uncap_first}){
		${daoClassName?substring(1)?uncap_first }.save${entityName}(${entityName?uncap_first});
		return new ResultObject(true);
	}
	
	/**
	* 删除${entity.commont}
	* 
	* @author ${author}
	* @date ${date}
	*/
	@RequestMapping("/delete${entityName}")
	@ResponseBody
	public ResultObject delete${entityName}(${typeMapping[primaryKeyType]} ${tempUtil.camelName(primaryKeyName)}){
		<#if isExistIfdel=='true'>
		${daoClassName?substring(1)?uncap_first }.update${entityName}IfDel(${tempUtil.camelName(primaryKeyName)});
		<#else>
		${daoClassName?substring(1)?uncap_first }.delete${entityName}(${tempUtil.camelName(primaryKeyName)});
		</#if>
		return new ResultObject(true);
	}
	
	@InitBinder  
	public void InitBinder(WebDataBinder dataBinder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    dateFormat.setLenient(false);  
	    dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}  
	
}