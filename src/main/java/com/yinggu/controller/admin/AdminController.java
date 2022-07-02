package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.config.JWTUtil;
import com.yinggu.mapper.AdminMapper;
import com.yinggu.pojo.Admin;
import com.yinggu.pojo.Admin;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private AdminMapper adminMapper;
    @RequestMapping("/adminList")
    public String login(Admin admin){
        //System.out.println(admin);
        Admin rs = adminMapper.login(admin);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(rs != null){
            List<Admin> adminList = adminMapper.getAdminList();
            if(adminList.size() > 0){
                obj.setDt(adminList);
                rs.setToken(JWTUtil.jwtCreate(rs.getAdminName()));
            }else {
                obj.setDt("管理员注册信息表为空");
                obj.setResult(false);
            }
        }else {
            obj.setDt("无管理员权限");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/addAdmin")
    public String addAdmin(Admin admin){
        //System.out.println("addAdmin  "+admin);
        admin.setRoleId("1");
        Admin rs = adminMapper.login(admin);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(rs == null){
            int i = adminMapper.addAdmin(admin);
            if(i == 0){
                obj.setDt("管理员注册信息表添加失败");
                obj.setResult(false);
            }
        }else {
            obj.setDt("管理员已存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/queryAdmin/{id}")
    public String queryAdmin(@PathVariable("id") int id){
        Admin admin = new Admin();
        admin.setId(id);
        //System.out.println("queryAdmin  "+admin);
        Admin rs = adminMapper.queryAdmin(admin);
        ResultObject obj = ResultObject.obj(rs);
        //System.out.println(rs);
        if(rs == null){
            obj.setDt("管理员不存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/editAdmin")
    public String editAdmin(Admin admin){
        //System.out.println("editAdmin  "+admin);
        int i = adminMapper.editAdmin(admin);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("修改失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/searchAdmin")
    public String searchAdmin(String searchtext){
        //System.out.println(searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Admin admin = new Admin();
            admin.setAdminName(searchtext);
            admin.setRoleId(searchtext);
            admin.setDate(searchtext);
            List<Admin> admins1 = adminMapper.searchAdminName(admin);
            //System.out.println(admins1.size());
            if(admins1.size() != 0){
                obj = ResultObject.obj(admins1);
            }else {
                List<Admin> admins2 = adminMapper.searchRoleId(admin);
                //System.out.println(admins2.size());
                if (admins2.size() != 0){
                    obj = ResultObject.obj(admins2);
                }else {
                    //System.out.println("datelength: "+admin.getDate().length());
                    if(admin.getDate().length() >= 4){
                        List<Admin> admins3 = adminMapper.searchDate(admin);
                        System.out.println(admins3.size());
                        if (admins3.size() != 0){
                            obj = ResultObject.obj(admins3);
                        }else {
                            obj = ResultObject.obj("未找到！");
                            obj.setResult(false);
                        }
                    }else {
                        obj = ResultObject.obj("未找到！");
                        obj.setResult(false);
                    }
                }
            }
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/deleteAdmin/{id}")
    public String deleteAdmin(@PathVariable("id") int id){
        Admin admin = new Admin();
        admin.setId(id);
        System.out.println("deleteAdmin  "+admin);
        int i = adminMapper.deleteAdmin(admin);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("删除失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
