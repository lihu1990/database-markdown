package com.jimi.databasemarkdown.controller;

import com.jimi.databasemarkdown.dto.MysqlMarkDownDto;
import com.jimi.databasemarkdown.utils.MysqlMarkDownUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author lihu@eversec.cn
 * @date 2018-09-23
 */
@Api(tags = "根据Mysql导出DataBase产出对应的markdown.md文件")
@RestController
@RequestMapping
public class MysqlMarkDownController {
    @ApiOperation("导出mysql-markdown文档结构")
    @RequestMapping(value = "exportMysqlMarkdown", method = GET)
    public void exportMysqlMarkdown(@ModelAttribute MysqlMarkDownDto mysqlMarkDownDto, HttpServletResponse response) throws Exception {
        try {
            Map<?, ?> maps = new org.apache.commons.beanutils.BeanMap(mysqlMarkDownDto);
            Map<String, Object> map = MysqlMarkDownUtils.runMarkDownTxt(maps);
            response.setCharacterEncoding("UTF-8");
            InputStream is = new FileInputStream(new File(map.get("path").toString()));
            if (is == null) {
                return;
            }
            response.reset();
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(System.currentTimeMillis()+"-mysql-markdown.md", "UTF-8"));
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/octet-stream");
            ServletOutputStream outStream = response.getOutputStream();
            byte[] buffer = new byte[10 * 1024];
            int nbytes = 0;
            while ((nbytes = is.read(buffer)) != -1) {
                try {
                    outStream.write(buffer, 0, nbytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outStream != null) {
                outStream.flush();
                outStream.close();
            }
            if (is != null) {
                is.close();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    private String[] xuqiu = new String[]{"业务场景", "业务流程", "数据流程图", "输入说明", "输出说明", "程序处理说明", "其他说明", "特殊需求"};

    private String[] xiangxi = new String[]{"业务描述", "程序逻辑流程图", "数据流转流程图", "功能实现原理", "程序性能和安全描述", "输入、输出描述", "数据结构说明", "接口定义", "算法（有则写，无则不写）",
            "测试要点"};

//    @ApiOperation("根据菜单导出该设详设文档结构")
//    @RequestMapping(value = "exportDocMarkDown", method = GET)
//    public void exportDocMarkDown(@ModelAttribute MysqlMarkDownDto mysqlMarkDownDto, HttpServletResponse response) throws Exception {
//        try {
//            Map<?, ?> maps = new org.apache.commons.beanutils.BeanMap(mysqlMarkDownDto);
//            List<Map<String, String>> map = MysqlMarkDownUtils.runMarkDownSJTxt(maps);
//            StringBuffer content = new StringBuffer();
//            for (Map<String, String> ite : map) {
//                int i = 1;
//                content.append("### " + ite.get("id") + " " + ite.get("name") + "\n");
//                if (mysqlMarkDownDto.isIsxuqiu()) {
//                    for (String ite2 : xuqiu) {
//                        content.append("#### " + ite.get("id") + "." + i + " " + ite2 + "\n");
//                    }
//                } else {
//                    for (String ite2 : xiangxi) {
//                        content.append("#### " + ite.get("id") + "." + i + " " + ite2 + "\n");
//                    }
//                }
//            }
//            response.setCharacterEncoding("UTF-8");
//            InputStream is = new FileInputStream(new File("/home/docMarkDown.txt"));
//            if (is == null) {
//                return;
//            }
//            response.reset();
//            response.setContentType("application/x-download;charset=utf-8");
//            String filename = URLEncoder.encode("docMarkDown", "UTF-8");
//            filename = filename.replaceAll("\\+", "%20");
//            response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
//            response.addHeader("Cache-Control", "no-cache");
//            ServletOutputStream outStream = response.getOutputStream();
//            byte[] buffer = new byte[10 * 1024];
//            int nbytes = 0;
//            while ((nbytes = is.read(buffer)) != -1) {
//                try {
//                    outStream.write(buffer, 0, nbytes);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if (outStream != null) {
//                outStream.flush();
//                outStream.close();
//            }
//            if (is != null) {
//                is.close();
//            }
//        } catch (Exception ex) {
//            throw ex;
//        }
//    }
}
