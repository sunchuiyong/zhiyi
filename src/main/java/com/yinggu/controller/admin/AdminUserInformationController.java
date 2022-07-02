package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.UserMapper;
import com.yinggu.pojo.Information;
import com.yinggu.pojo.User;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminUserInformationController {
    @Autowired
    private UserMapper userMapper;
    @RequestMapping("/userInformationList")
    public String getuserInformationList(){
        //System.out.println();
        List<Information> userInformationList = userMapper.getUserInformationList();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(userInformationList.size() > 0){
            obj.setDt(userInformationList);
        }else {
            obj.setDt("管理员注册信息表为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/addUserInformation")
    public String addUserInformation(Information information){
        //System.out.println("addAdmin  "+admin);
        Information rs = userMapper.queryInformation(information);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(rs == null){
            int i = userMapper.addInformation(information);
            if(i == 0){
                obj.setDt("管理员基础信息表添加失败");
                obj.setResult(false);
            }
        }else {
            obj.setDt("该管理员基础信息已存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/queryUserInformation/{id}")
    public String queryUserInformation(@PathVariable("id") int id){
        Information information = new Information();
        information.setId(id);
        //System.out.println("queryAdmin  "+admin);
        Information rs = userMapper.query_UserInformation(information);
        ResultObject obj = ResultObject.obj(rs);
        //System.out.println(rs);
        if(rs == null){
            obj.setDt("用户不存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/editUserInformation")
    public String editUserInformation(Information information){
        //System.out.println("editAdmin  "+admin);
        int i = userMapper.editUserInformation(information);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("修改失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/searchUserInformation")
    public String searchUserInformation(String searchtext){
        //System.out.println(searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Information information = new Information();
            information.setUserName(searchtext);
            information.setName(searchtext);
            information.setSex(searchtext);
            information.setPlace(searchtext);
            information.setBirthday(searchtext);
            information.setPhone(searchtext);
            information.setWeChat(searchtext);
            List<Information> information1 = userMapper.searchInfo(information);
            //System.out.println(admins1.size());
            if(information1.size() != 0){
                obj = ResultObject.obj(information1);
            }else {
                if(information.getBirthday().length()>=4 && information.getBirthday().length()<=10){
                    List<Information> information2 = userMapper.searchBirthday(information);
                    if(information2.size() != 0){
                        obj = ResultObject.obj(information2);
                    }else {
                        List<Information> information3 = userMapper.searchWeChat(information);
                        if(information3.size() != 0){
                            obj = ResultObject.obj(information3);
                        }else {
                            obj = ResultObject.obj("未找到！");
                            obj.setResult(false);
                        }
                    }
                }else if(information.getPhone().length() == 11){
                    List<Information> information4 = userMapper.searchPhone(information);
                    if(information4.size() != 0){
                        obj = ResultObject.obj(information4);
                    }else {
                        List<Information> information5 = userMapper.searchWeChat(information);
                        if(information5.size() != 0){
                            obj = ResultObject.obj(information5);
                        }else {
                            obj = ResultObject.obj("未找到！");
                            obj.setResult(false);
                        }
                    }
                }
            }
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/deleteUserInformation/{id}")
    public String deleteUserInformation(@PathVariable("id") int id){
        Information information = new Information();
        information.setId(id);
        System.out.println("deleteUserInformation  "+information);
        int i = userMapper.deleteUserInformation(information);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("删除失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/truncateInformationTable")
    public String truncateInformationTable(){
        int i = userMapper.truncateInformationTable();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 1){
            obj.setDt("清空数据表失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
