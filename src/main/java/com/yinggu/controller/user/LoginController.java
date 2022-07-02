package com.yinggu.controller.user;

import com.alibaba.fastjson.JSON;
import com.yinggu.config.JWTUtil;
import com.yinggu.mapper.DemandMapper;
import com.yinggu.mapper.UserMapper;
import com.yinggu.pojo.Demand;
import com.yinggu.pojo.User;
import com.yinggu.pojo.Information;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class LoginController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DemandMapper demandMapper;
    @RequestMapping("/login")
    public String login(User user){
        //System.out.println(user);
        User rs = userMapper.login(user);

        ResultObject obj = ResultObject.obj(rs);
        if (rs == null){
            obj.setResult(false);
        }else {
            rs.setToken(JWTUtil.jwtCreate(rs.getUserName()));
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/register")
    public String register(User user){
        user.setRoleId("0");
        User login = userMapper.login(user);
        //System.out.println(user);
        ResultObject rs = ResultObject.obj(null);
        if(login != null){
            return JSON.toJSONString("用户已存在！");
        }else {
            int i = userMapper.register(user);
            if (i == 0){
                rs.setResult(false);
            }
            return JSON.toJSONString(rs);
        }
    }
    @RequestMapping("/informationModify")
    public String userInformationModify(Information information){
        //System.out.println(information);
        Information queryInformation = userMapper.queryInformation(information);
        //System.out.println(user);
        ResultObject rs = ResultObject.obj(null);
        if(queryInformation != null){
            //System.out.println(1);
            int i = userMapper.editInformation(information);
            if (i == 0){
                rs.setResult(false);
            }
            return JSON.toJSONString(rs);
        }else {
            //System.out.println(2);
            int i = userMapper.addInformation(information);
            if (i == 0){
                rs.setResult(false);
            }
            return JSON.toJSONString(rs);
        }
    }
    @RequestMapping("/informationQuery")
    public String userInformationQuery(Information information){
        //System.out.println(information);
        Information queryInformation = userMapper.queryInformation(information);
        ResultObject rs = ResultObject.obj(queryInformation);
        if(queryInformation == null){
            rs.setResult(false);
        }
        return JSON.toJSONString(rs);
    }
    @RequestMapping("/informationNameQuery")
    public String userInformationNameQuery(Information information){
        System.out.println(information);
        Information queryInformation = userMapper.queryNameInformation(information);
        ResultObject rs = ResultObject.obj(queryInformation);
        if(queryInformation == null){
            rs.setDt("用户名查询失败");
            rs.setResult(false);
        }
        return JSON.toJSONString(rs);
    }
    @RequestMapping("/logout")
    public String logout(User user){
        //System.out.println(user);
        Information information = new Information();
        Demand demand = new Demand();
        information.setUserName(user.getUserName());
        demand.setUserName(user.getUserName());
        ResultObject rs = ResultObject.obj(null);
        String erro1 = "";
        String erro2 = "";
        String erro3 = "";
        User queryUser = userMapper.queryUser(user);
        if(queryUser != null){
            int userDelete = userMapper.userDelete(user);
            if(userDelete == 0){
                erro1 = "用户删除失败";
                System.out.println(erro1);
                rs.setResult(false);
            }
        }
        Information queryInformation = userMapper.queryInformation(information);
        if(queryInformation != null){
            int informationDelete = userMapper.informationDelete(information);
            if(informationDelete == 0){
                erro2 = "用户基本信息删除失败";
                System.out.println(erro2);
                rs.setResult(false);
            }
        }
        List<Demand> demands = demandMapper.queryDemand(demand);
        if(demands.size() != 0) {
            int deleteUserNameDemand = demandMapper.deleteUserNameDemand(demand);
            if (deleteUserNameDemand == 0) {
                erro3 = "需求信息删除失败";
                System.out.println(erro3);
                rs.setResult(false);
            }
        }
        if(queryUser == null && queryInformation == null && demands.size() == 0){
            rs.setDt("用户不存在");
            rs.setResult(false);
            return JSON.toJSONString(rs);
        }else {
            rs.setDt(erro1+' '+erro2+' '+erro3);
            return JSON.toJSONString(rs);
        }

    }
    @RequestMapping("/queryUser")
    public String queryUser(User user){
        //System.out.println(user);
        User rs = userMapper.queryUser(user);

        ResultObject obj = ResultObject.obj(rs);
        if (rs == null){
            obj.setResult(false);
        }else {
            //rs.setToken(JWTUtil.jwtCreate(rs.getUserName()));
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello";
    }
}
