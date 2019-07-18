package api.word;

import java.util.ArrayList;
import java.util.List;

public class Module {

    private Integer id;
    private Integer projectId;
    private String  name;
    private String  introduction;
    private List<Page> pageList = new ArrayList<Page>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    
    

    
    public List<Page> getPageList() {
        return pageList;
    }

    
    public void setPageList(List<Page> pageList) {
        this.pageList = pageList;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Module) {
            Module module = (Module) obj;
            if (module.getId() == this.id) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

}
