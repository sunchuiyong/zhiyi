package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.AdmininfoMapper;
import com.yinggu.pojo.Admin;
import com.yinggu.pojo.Admininfo;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdmininfoController {
    @Autowired
    private AdmininfoMapper admininfoMapper;
    @RequestMapping("/admininfoList")
    public String login(){
        ResultObject obj = ResultObject.obj(null);
        List<Admininfo> admininfoList = admininfoMapper.getAdmininfoList();
        if(admininfoList.size() > 0){
            obj.setDt(admininfoList);
        }else {
            obj.setDt("管理员基础信息表为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/addAdmininfo")
    public String addAdmininfo(Admininfo admininfo){
        //System.out.println("addAdmin  "+admin);
        Admininfo rs = admininfoMapper.query(admininfo);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(rs == null){
            int i = admininfoMapper.addAdmininfo(admininfo);
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
    @RequestMapping("/queryAdmininfo/{id}")
    public String queryAdmin(@PathVariable("id") int id){
        Admininfo admininfo = new Admininfo();
        admininfo.setId(id);
        //System.out.println("queryAdmin  "+admin);
        Admininfo rs = admininfoMapper.queryAdmininfo(admininfo);
        ResultObject obj = ResultObject.obj(rs);
        //System.out.println(rs);
        if(rs == null){
            obj.setDt("该管理员基础信息不存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/editAdmininfo")
    public String editAdmininfo(Admininfo admininfo){
        //System.out.println("editAdmin  "+admin);
        int i = admininfoMapper.editAdmininfo(admininfo);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("修改失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/searchAdmininfo")
    public String searchAdmininfo(String searchtext){
        System.out.println("searchtext: "+searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Admininfo admininfo = new Admininfo();
            admininfo.setAdminName(searchtext);
            admininfo.setName(searchtext);
            admininfo.setSex(searchtext);
            admininfo.setBirthday(searchtext);
            admininfo.setPhone(searchtext);
            admininfo.setPlace(searchtext);
            List<Admininfo> admininfos1 = admininfoMapper.searchAdminName(admininfo);
            //System.out.println(admins1.size());
            if(admininfos1.size() != 0){
                obj = ResultObject.obj(admininfos1);
            }else {
                List<Admininfo> admininfos2 = admininfoMapper.searchName(admininfo);
                //System.out.println(admins2.size());
                if (admininfos2.size() != 0){
                    obj = ResultObject.obj(admininfos2);
                }else {
                    List<Admininfo> admininfos3 = admininfoMapper.searchPlace(admininfo);
                    //System.out.println(admins3.size());
                    if (admininfos3.size() != 0){
                        obj = ResultObject.obj(admininfos3);
                    }else {
                        if(admininfo.getBirthday().length()>=4&&admininfo.getBirthday().length()<=10){
                            List<Admininfo> admininfos4 = admininfoMapper.searchBirthday(admininfo);
                            if(admininfos4.size() != 0){
                                obj = ResultObject.obj(admininfos4);
                            }else {
                                obj = ResultObject.obj("未找到！");
                                obj.setResult(false);
                            }
                        }else if(admininfo.getPhone().length() == 11){
                            List<Admininfo> admininfos5 = admininfoMapper.searchPhone(admininfo);
                            if(admininfos5.size() != 0){
                                obj = ResultObject.obj(admininfos5);
                            }else {
                                obj = ResultObject.obj("未找到！");
                                obj.setResult(false);
                            }
                        }else {
                            List<Admininfo> admininfos6 = admininfoMapper.searchSex(admininfo);
                            if (admininfos6.size() != 0){
                                obj = ResultObject.obj(admininfos6);
                            }else {
                                obj = ResultObject.obj("未找到！");
                                obj.setResult(false);
                            }
                        }
                    }
                }
            }
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/deleteAdmininfo/{id}")
    public String deleteAdmin(@PathVariable("id") int id){
        Admininfo admininfo = new Admininfo();
        admininfo.setId(id);
        System.out.println("deleteAdmininfo  "+admininfo);
        int i = admininfoMapper.deleteAdmininfo(admininfo);
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 0){
            obj.setDt("删除失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/truncateAdmininfoTable")
    public String truncateAdmininfoTable(){
        int i = admininfoMapper.truncateAdmininfoTable();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 1){
            obj.setDt("清空数据表失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
