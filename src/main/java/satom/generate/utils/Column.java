package satom.generate.utils;

import java.util.UUID;

public class Column {

    private String name;

    private String type;

    private String isPrimary;

    private String commont;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(String isPrimary) {
        this.isPrimary = isPrimary;
    }

    public String getCommont() {
        return commont;
    }

    public void setCommont(String commont) {
        this.commont = commont;
    }
    
    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString());
    }

}
