package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.UserMapper;
import com.yinggu.pojo.Admin;
import com.yinggu.pojo.User;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminUserController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/userList")
    public String getuserList(){
        //System.out.println(user);
        List<User> userList1 = userMapper.getUserList();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(userList1.size() > 0){
            obj.setDt(userList1);
        }else {
            obj.setDt("管理员注册信息表为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/addUser")
    public String addUser(User user){
        //System.out.println("addAdmin  "+admin);
        user.setRoleId("0");
        User rs = userMapper.queryUser(user);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(rs == null){
            int i = userMapper.register(user);
            if(i == 0){
                obj.setDt("用户注册信息表添加失败");
                obj.setResult(false);
            }
        }else {
            obj.setDt("用户已存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/queryUser/{id}")
    public String queryUser(@PathVariable("id") int id){
        User user = new User();
        user.setId(id);
        //System.out.println("queryAdmin  "+admin);
        User rs = userMapper.query_User(user);
        ResultObject obj = ResultObject.obj(rs);
        //System.out.println(rs);
        if(rs == null){
            obj.setDt("用户不存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/editUser")
    public String editUser(User user){
        //System.out.println("editAdmin  "+admin);
        int i = userMapper.editUser(user);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("修改失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/searchUser")
    public String searchUser(String searchtext){
        //System.out.println(searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            User user = new User();
            user.setUserName(searchtext);
            user.setRoleId(searchtext);
            user.setDate(searchtext);
            List<User> users1 = userMapper.searchUserName(user);
            //System.out.println(admins1.size());
            if(users1.size() != 0){
                obj = ResultObject.obj(users1);
            }else {
                List<User> users2 = userMapper.searchRoleId(user);
                //System.out.println(admins2.size());
                if (users2.size() != 0){
                    obj = ResultObject.obj(users2);
                }else {
                    //System.out.println("datelength: "+admin.getDate().length());
                    if(user.getDate().length() >= 4){
                        List<User> users3 = userMapper.searchDate(user);
                        System.out.println(users3.size());
                        if (users3.size() != 0){
                            obj = ResultObject.obj(users3);
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
    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id){
        User user = new User();
        user.setId(id);
        System.out.println("deleteAdmin  "+user);
        int i = userMapper.deleteUser(user);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("删除失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/truncateUserTable")
    public String truncateUserTable(){
        int i = userMapper.truncateUserTable();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 1){
            obj.setDt("清空数据表失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
