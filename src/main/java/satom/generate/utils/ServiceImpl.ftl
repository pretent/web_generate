package ${serviceImplPackageName};

import org.springframework.stereotype.Service;
import ${servicePackageName}.${serviceClassName};
import ${daoPackageName}.${daoClassName};
import javax.annotation.Resource;

@Service("${serviceClassName?uncap_first}")
public class ${serviceImplClassName}  implements ${serviceClassName} {
	
	private ${daoClassName} ${daoClassName?uncap_first};
	
    public ${daoClassName} get${daoClassName}() {
        return ${daoClassName?uncap_first};
    }

    @Resource(name = "${daoClassName?uncap_first}")
    public void set${daoClassName}(${daoClassName} ${daoClassName?uncap_first}) {
        this.${daoClassName?uncap_first} = ${daoClassName?uncap_first};
    }
	
}