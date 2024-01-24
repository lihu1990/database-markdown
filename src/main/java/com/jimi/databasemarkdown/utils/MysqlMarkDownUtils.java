package com.jimi.databasemarkdown.utils;

import com.jimi.databasemarkdown.model.Column;
import com.jimi.databasemarkdown.model.Doc;
import com.jimi.databasemarkdown.model.Table;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihu@eversec.cn
 * @date 2018-09-23
 */
public class MysqlMarkDownUtils {
    //该设详设需要
    public static List<Map<String, String>> runMarkDownSJTxt(Map<?, ?> map) throws Exception {
        try {
            List<Map<String, String>> titles = new ArrayList<>();
            List<Doc> docs = new ArrayList<>();
            List<Doc> docs2 = new ArrayList<>();
            Connection conn = getMySQLConnection(map);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select a.id,a.name,a.parent_id,a.seq from sys_security_menu a where app_id=(select id from sys_security_application where app_key= '" + map.get("appKey") + "') and type=1 and parent_id is null order by id asc");
            while (rs.next()) {
                Doc doc = new Doc();
                doc.setId(rs.getInt(1));
                doc.setName(rs.getString(2));
                doc.setParentId(rs.getInt(3));
                docs.add(doc);
            }
            rs.close();
            stmt.close();
            conn.close();
            conn = getMySQLConnection(map);
            stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery("select a.id,a.name,a.parent_id,a.seq from sys_security_menu a where app_id=(select id from sys_security_application where app_key= '" + map.get("appKey") + "') and type=1 and parent_id is not null order by id asc");
            while (rs2.next()) {
                Doc doc = new Doc();
                doc.setId(rs2.getInt(1));
                doc.setName(rs2.getString(2));
                doc.setParentId(rs2.getInt(3));
                docs2.add(doc);
            }
            rs2.close();
            stmt.close();
            conn.close();
            int start = 1;
            for (Doc item : docs) {
                Map<String, String> s = new HashMap<>();
                s.put("id", start + ".0.0");
                s.put("name", item.getName());
                titles.add(s);
                int start2 = 1;
                for (Doc item1 : docs2) {
                    if (item1.getParentId().equals(item.getId())) {
                        Map<String, String> s2 = new HashMap<>();
                        s2.put("id", start + "." + start2 + ".0");
                        s2.put("name", item1.getName());
                        titles.add(s2);
                        start2++;
                    }
                }
                start++;
            }
            return titles;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    //mysql导出需要
    public static Map<String, Object> runMarkDownTxt(Map<?, ?> map) throws Exception {
        try {
            // 获取数据库下的所有表名称
            List<Table> tables = getAllTableName(map);
            // 获得表的建表语句
            buildTableComment(tables, map);
            // 获得表中所有字段信息
            buildColumns(tables, map);
            // 写文件
            return write(tables, map);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * 写文件
     */
    private static Map<String, Object> write(List<Table> tables, Map<?, ?> map) {
        StringBuffer content = new StringBuffer();

        content.append("#  "+ map.get("projectName") + " V" + map.get("version") + "-"+map.get("dbName")+"-数据库设计说明书\n" +
                "{{>toc}}\n" +
                "> **密级：内部公开**\n" +
                "> **文档编号： **\n" +
                ">**版本号：" + map.get("version") + "**\n" +
                "\n" +
                "" + map.get("companyName") + "对本文件资料享受著作权及其它专属权利，未经书面许可，不得将该文件资料（其全部或任何部分）披露予任何第三方，或进行修改后使用。\n" +
                "\n" +
                "**文件更改摘要：**\n" +
                "\n" +
                "|日期|版本号|修订说明|修订人|审核人|批准人\n" +
                "|---|---|---|---|---|---|\n" +
                "\n" +
                "# 1 数据库环境\n" +
                "【数据库】MySQL5.6？？\n" +
                "# 2 数据库命名规则\n" +
                "无\n\n" +
                "# 3 物理设计\n");
        int j = 1;
        for (Table table : tables) {
            StringBuilder buffer = new StringBuilder();
            buffer.append("## 3." + j + " 表：" + table.getTableName() + "(" + table.getComment() + ")\n");
            buffer.append("------------\n");
            buffer.append("|序号|参数|类型|是否允许为空|Key|默认值|Extra|说明|\n");
            buffer.append("|:-------|:-------|:-------|:-------|:-------|:-------|:-------|:-------|\n");
            List<Column> columns = table.getColumns();
            int i = 1;
            for (Column column : columns) {
                String field = column.getField();
                String type = column.getType();
                String comment = column.getComment();
                buffer.append("|" + i + "|" + field + "|" + type + "|" + column.getIsNull() + "|" + column.getKey() + "|" + column.getDefaultStr() + "|" + column.getExtra() + "|" + ("".equals(comment) ? "无" : comment) + "|\n");
                i++;
            }
            content.append(buffer + "\n");
            j++;
        }
        content.append("# 4 主要数据库表ER图\n" +
                "![img](？？) \n" +
                "\n" +
                "# 5 安全性设计\n" +
                "权限：\n" +
                "| 角色|允许访问的表与列|操作权限\n" +
                "|----|---|---|\n" +
                "\n" +
                "# 6 优化\n" +
                "无\n" +
                "# 7 数据库管理与维护说明\n" +
                "无");
        String fileName = System.currentTimeMillis() + ".txt";
        String path = System.getProperty("user.dir") + "/sqlMarkDown/" + fileName;
        Map<String, Object> mapr = new HashMap<>();
        mapr.put("path", path);
        mapr.put("fileName", fileName);
        try {
            FileUtils.writeStringToFile(new File(path), content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mapr;
    }

    /**
     * 连接数据库
     */
    private static Connection getMySQLConnection(Map<?, ?> map) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://" + map.get("ip").toString() + ":" + map.get("port").toString() + "/" + map.get("dbName").toString(), map.get("userName").toString(), map.get("pwd").toString());
        return conn;
    }

    /**
     * 获取当前数据库下的所有表名称
     */
    private static List<Table> getAllTableName(Map<?, ?> map) throws Exception {
        List<Table> tables = new ArrayList<>();
        Connection conn = getMySQLConnection(map);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES");
        while (rs.next()) {
            String tableName = rs.getString(1);
            String objectName = camelCase(tableName);
            Table table = new Table(tableName, objectName);
            tables.add(table);
        }
        rs.close();
        stmt.close();
        conn.close();
        return tables;
    }

    /**
     * 获得某表的建表语句
     */
    private static void buildTableComment(List<Table> tables, Map<?, ?> map) throws Exception {
        Connection conn = getMySQLConnection(map);
        Statement stmt = conn.createStatement();
        for (Table table : tables) {
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("SHOW CREATE TABLE " + table.getTableName());
                if (rs != null && rs.next()) {
                    String createDDL = rs.getString(2);
                    String comment = parse(createDDL);
                    table.setComment(comment);
                }
            } catch (Exception ex) {
                System.out.printf("表" + table.getTableName() + "-------show create table 失败！");
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
        stmt.close();
        conn.close();
    }

    /**
     * 获得某表中所有字段信息
     */
    private static void buildColumns(List<Table> tables, Map<?, ?> map) throws Exception {
        Connection conn = getMySQLConnection(map);
        Statement stmt = conn.createStatement();
        for (Table table : tables) {
            List<Column> columns = new ArrayList<>();
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery("show full columns from " + table.getTableName());
                while (rs.next()) {
                    String field = rs.getString("Field");
                    String type = rs.getString("Type");
                    String nulls = rs.getString("Null");
                    String key = rs.getString("Key");
                    String defaults = rs.getString("Default");
                    String extra = rs.getString("Extra");
                    String comment = rs.getString("Comment").replaceAll("\r\n", "");
                    Column column = new Column(field, type, nulls, defaults, key, extra, comment);
                    columns.add(column);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }
            }

            table.setColumns(columns);
        }
        stmt.close();
        conn.close();
    }

    /**
     * 返回注释信息
     */
    private static String parse(String all) {
        String comment;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }

    /**
     * 例如：employ_user_id变成employUserId
     */
    private static String camelCase(String str) {
        String[] str1 = str.split("_");
        int size = str1.length;
        String str2;
        StringBuilder str4 = null;
        String str3;
        for (int i = 0; i < size; i++) {
            if (i == 0) {
                str2 = str1[i];
                str4 = new StringBuilder(str2);
            } else {
                str3 = initcap(str1[i]);
                str4.append(str3);
            }
        }
        return str4.toString();
    }

    /**
     * 把输入字符串的首字母改成大写
     */
    private static String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
