package com.jimi.databasemarkdown.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class MysqlMarkDownDto {

    @ApiModelProperty("mysql数据库IP")
    private String ip;
    @ApiModelProperty("mysql数据库服务的端口")
    private int port;
    @ApiModelProperty("数据库名")
    private String dbName;
    @ApiModelProperty("登录mysql帐号")
    private String userName;
    @ApiModelProperty("mysql帐号密码")
    private String pwd;
    @ApiModelProperty("项目名字")
    private String projectName;
    @ApiModelProperty("版本号")
    private String version;
    @ApiModelProperty("公司名称")
    private String companyName;

//    @ApiModelProperty("APPkey(导出该设、详设需要)")
//    private String appKey;
//
//    @ApiModelProperty("需求说明书？详细说明书")
//    private boolean isxuqiu;
//
//    public boolean isIsxuqiu() {
//        return isxuqiu;
//    }
//
//    public void setIsxuqiu(boolean isxuqiu) {
//        this.isxuqiu = isxuqiu;
//    }
//
//    public String getAppKey() {
//        return appKey;
//    }
//
//    public void setAppKey(String appKey) {
//        this.appKey = appKey;
//    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
