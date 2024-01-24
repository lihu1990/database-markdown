//package com.jimi.databasemarkdown.controller;
//
//import com.jimi.databasemarkdown.model.APP;
//import com.jimi.databasemarkdown.utils.SqlLiteUtils;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//import static org.springframework.web.bind.annotation.RequestMethod.GET;
//import static org.springframework.web.bind.annotation.RequestMethod.PUT;
//
//@Api(tags = "进行markdown编写")
//@RestController
//@RequestMapping("sqlite")
//public class SqlLiteController {
//    @ApiOperation("增加app")
//    @RequestMapping(value = "addApp", method = PUT)
//    public boolean addApp(@ModelAttribute APP app) throws Exception {
//        return SqlLiteUtils.addApp(app);
//    }
//
//    @ApiOperation("查询app")
//    @RequestMapping(value = "applist", method = GET)
//    public List<APP> applist() throws Exception {
//        return SqlLiteUtils.appList();
//    }
//
//}
