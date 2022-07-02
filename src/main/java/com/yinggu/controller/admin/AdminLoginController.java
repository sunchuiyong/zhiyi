package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.config.JWTUtil;
import com.yinggu.mapper.AdminMapper;
import com.yinggu.pojo.Admin;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class AdminLoginController {
    @Autowired
    private AdminMapper adminMapper;
    @RequestMapping("/adminlogin")
    public String login(Admin admin){
        //System.out.println(user);
        Admin rs = adminMapper.login(admin);

        ResultObject obj = ResultObject.obj(rs);
        if (rs == null){
            obj.setResult(false);
        }else {
            rs.setToken(JWTUtil.jwtCreate(rs.getAdminName()));
        }
        return JSON.toJSONString(obj);
    }

}
