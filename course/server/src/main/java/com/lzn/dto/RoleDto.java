package com.lzn.dto;


import java.util.List;

public class RoleDto {

    /**
     * id
     */
    private String id;

    /**
     * 角色
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    @Override
    public String toString() {
        return "RoleDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", userIds=" + userIds +
                ", resourceIds=" + resourceIds +
                '}';
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    private List<String> userIds;

    /**
     * 资源id
     * @return
     */
    private List<String> resourceIds;

    public List<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(List<String> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


}