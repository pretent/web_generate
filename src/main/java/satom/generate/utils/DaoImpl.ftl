package ${daoImplPackageName};

import org.springframework.stereotype.Service;
import com.senyintxa.phpatient.base.dao.impl.BaseDaoImpl;
import com.senyintxa.phpatient.base.dao.JdbcTemplateDao;
import ${daoPackageName}.${daoClassName};
import javax.annotation.Resource;

@Service("${daoClassName?uncap_first}")
public class ${daoImplClassName}  extends BaseDaoImpl implements ${daoClassName} {
	
	private JdbcTemplateDao jdbcTemplateDao;

    public JdbcTemplateDao getJdbcTemplateDao() {
        return jdbcTemplateDao;
    }

    @Resource(name = "jdbcTemplateDao")
    public void setJdbcTemplateDao(JdbcTemplateDao jdbcTemplateDao) {
        this.jdbcTemplateDao = jdbcTemplateDao;
    }
    
    
	
	
}