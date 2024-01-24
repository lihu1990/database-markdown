package com.jimi.databasemarkdown.model;

public class Doc implements Comparable<Doc> {
    private Integer id;
    private String name;
    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }


    @Override
    public int compareTo(Doc o) {
        return this.id.compareTo(o.id);
    }
}
