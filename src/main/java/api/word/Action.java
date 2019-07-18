package api.word;

import java.util.ArrayList;
import java.util.List;

public class Action {

    private int             id;
    private int             disableCache;
    private String          name;
    private String          description;
    private String          requestType           = "1";
    private String          requestUrl;
    private List<Parameter> requestParameterList  = new ArrayList<Parameter>();
    private List<Parameter> responseParameterList = new ArrayList<Parameter>();
    private String          responseTemplate;
    private Page            page;
    private String          remarks;
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getDisableCache() {
        return disableCache;
    }
    
    public void setDisableCache(int disableCache) {
        this.disableCache = disableCache;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getRequestType() {
        return requestType;
    }
    
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    
    public String getRequestUrl() {
        return requestUrl;
    }
    
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
    
    public List<Parameter> getRequestParameterList() {
        return requestParameterList;
    }
    
    public void setRequestParameterList(List<Parameter> requestParameterList) {
        this.requestParameterList = requestParameterList;
    }
    
    public List<Parameter> getResponseParameterList() {
        return responseParameterList;
    }
    
    public void setResponseParameterList(List<Parameter> responseParameterList) {
        this.responseParameterList = responseParameterList;
    }
    
    public String getResponseTemplate() {
        return responseTemplate;
    }
    
    public void setResponseTemplate(String responseTemplate) {
        this.responseTemplate = responseTemplate;
    }
    
    public Page getPage() {
        return page;
    }
    
    public void setPage(Page page) {
        this.page = page;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
    

}
