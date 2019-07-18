package api.word;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

public class Parameter {

    private Integer         id;
    private String          mockData;
    private String          name;
    private String          identifier;
    private String          identifierChange;
    private String          remarkChange;
    private String          dataType;
    private String          remark;
    private String          validator     = "";
    private Action          action;
    private String          parentName;
    private List<Parameter> parameterList = new ArrayList<Parameter>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMockData() {
        return mockData;
    }

    public void setMockData(String mockData) {
        this.mockData = mockData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierChange() {
        return identifierChange;
    }

    public void setIdentifierChange(String identifierChange) {
        this.identifierChange = identifierChange;
    }

    public String getRemarkChange() {
        return remarkChange;
    }

    public void setRemarkChange(String remarkChange) {
        this.remarkChange = remarkChange;
    }

    public String getDataType() {
        return StringEscapeUtils.escapeHtml(dataType);
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public List<Parameter> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<Parameter> parameterList) {
        this.parameterList = parameterList;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

}
