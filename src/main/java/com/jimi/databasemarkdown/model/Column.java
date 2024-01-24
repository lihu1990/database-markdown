package com.jimi.databasemarkdown.model;

/**
 * @author lihu@eversec.cn
 * @date 2018-09-23
 */
public class Column {
    /**
     * 数据库字段名称
     */
    private String field;

    /**
     * 数据库字段类型
     */
    private String type;
    /**
     * 是否为空
     */
    private String isNull;
    /**
     * 默认值
     */
    private String defaultStr;
    /**
     * key说明 主外键
     */
    private String key;
    /**
     * extra autoins..
     */
    private String extra;
    /**
     * 数据库字段注释
     */
    private String comment;

    public Column() {
    }

    public Column(String field, String type, String isNull, String defaultStr, String key, String extra, String comment) {
        this.field = field;
        this.type = type;
        this.isNull = isNull;
        this.defaultStr = defaultStr;
        this.key = key;
        this.extra = extra;
        this.comment = comment;
    }

    public String getDefaultStr() {
        return defaultStr;
    }

    public void setDefaultStr(String defaultStr) {
        this.defaultStr = defaultStr;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getIsNull() {
        return isNull;
    }

    public void setIsNull(String isNull) {
        this.isNull = isNull;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
