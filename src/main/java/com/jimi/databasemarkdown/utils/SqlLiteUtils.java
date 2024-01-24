package com.jimi.databasemarkdown.utils;

import com.jimi.databasemarkdown.model.APP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SqlLiteUtils {

    public static Connection getConnect() {
        Connection c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:sqlite.db");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
        return c;
    }

    public static List<APP> appList() {
        Connection c = getConnect();
        List<APP> list = new ArrayList<>();
        try {
//            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = " select * from APP";

            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    APP app1 = new APP();
                    app1.setName(resultSet.getString("name"));
                    app1.setId(resultSet.getString("id"));
                    app1.setDeveloper(resultSet.getString("developer"));
                    list.add(app1);
                }
            }
            stmt.close();
            c.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean addApp(APP app) {
        Connection c = getConnect();
        try {
//            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            String sql = " select * from APP where name='" + app.getName() + "'";

            ResultSet resultSet = stmt.executeQuery(sql);
            APP app1 = null;
            if (resultSet != null && resultSet.next()) {
                app1 = new APP();
                app1.setName(resultSet.getString("name"));
                app1.setId(resultSet.getString("id"));
                app1.setDeveloper(resultSet.getString("developer"));
            }
            if (app1 != null) {
                return false;
            }
            sql = "INSERT INTO APP (ID,NAME,DEVELOPER) " +
                    "VALUES ('" + UUID.randomUUID().toString().replaceAll("-", "") + "','" + app.getName() + "','" + app.getDeveloper() + "');";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void init() {
        Connection c = getConnect();
        try {
            Statement stmt = c.createStatement();

            String sql = "CREATE TABLE APP " +
                    "(ID TEXT     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " DEVELOPER         TEXT     NOT NULL)";
            stmt.executeUpdate(sql);
//            stmt.close();
            sql = "CREATE TABLE XUQIU " +
                    "(ID TEXT  NOT NULL," +
                    " APPID    INT    NOT NULL, " +
                    " XQMC      TEXT   NOT NULL," +  //需求名称
                    " YWCJ      TEXT     NOT NULL," + //业务场景
                    "YWLC       TEXT     NOT NULL," +  //业务流程
                    "SJLCT      TEXT      NOT NULL," + //数据流程图
                    "SRSM       TEXT     NOT NULL," +  //输入说明
                    "SCSM       TEXT     NOT NULL," +  //输出说明
                    "CXCLSM     TEXT     NOT NULL," +  //程序处理说明
                    "QTSM       TEXT     NOT NULL)";   //其他说明
            stmt.executeUpdate(sql);
//            stmt.close();
            sql = "CREATE TABLE XIANGXISHEJI " +
                    "(ID TEXT NOT NULL," +
                    " XQID   TEXT    NOT NULL, " +     //需求id
                    " YWMS   TEXT     NOT NULL," +     //业务描述
                    " CXLJLCT TEXT    NOT NULL," +    //程序逻辑流转图
                    " SJLZLCT TEXT    NOT NULL," +    //数据流转流程图
                    " GNSXYL  TEXT    NOT NULL," +    //功能实现原理
                    " CXXNAQMS TEXT   NOT NULL," +    //程序性能和安全描述
                    " SRSCMS   TEXT   NOT NULL," +    //输入、输出说明
                    " SJJGSM   TEXT   NOT NULL," +    //数据结构说明
                    " JKDY     TEXT   NOT NULL," +    //接口定义
                    " SF       TEXT   ," +            //算法
                    " CSYD     TEXT   )";             //测试要点
            stmt.executeUpdate(sql);
            stmt.close();

            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        getConnect();
        init();
    }
}
