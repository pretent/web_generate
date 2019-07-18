package ${entityPackageName};

import java.util.Date;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
* @${entityCommont}
* 
* @author ${author}
* @date ${date}
*/
@Entity
@Table(name="${tableName}")
public class ${entityName} implements Serializable{

	private static final long serialVersionUID = -1;
	
<#list entity.columns as column>
	<#if column.commont??&&column.commont!="">
	/** ${column.commont} **/
	</#if>
	private ${typeMapping[column.type]} ${tempUtil.camelName(column.name)};
</#list> 


<#list entity.columns as column>
	
	<#if column.isPrimary=="true">
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	</#if>
	@Column(name = "${column.name?lower_case}")
	public ${typeMapping[column.type]} get${tempUtil.camelName(column.name)?cap_first}(){
		return ${tempUtil.camelName(column.name)};
	}
	
	public void set${tempUtil.camelName(column.name)?cap_first}(${typeMapping[column.type]} ${tempUtil.camelName(column.name)}){
		this.${tempUtil.camelName(column.name)}=${tempUtil.camelName(column.name)};
	}
	
</#list> 
}