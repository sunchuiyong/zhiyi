package com.yinggu.mapper;

import com.yinggu.pojo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    User login(User u);//登录，查询用户
    int register(User u);//注册，添加用户
    Information queryInformation(Information information);//查询用户基础信息
    Information queryNameInformation(Information information);//根据name查询用户基础信息
    User queryUser(User user);//查询用户
    int editInformation(Information information);//修改用户基础信息
    int addInformation(Information information);//添加用户基础信息
    int userDelete(User user);//删除用户
    int informationDelete(Information information);//删除用户基础信息

    List<User> getUserList();
    User query_User(User user);
    int editUser(User user);
    List<User> searchUserName(User user);
    List<User> searchRoleId(User user);
    List<User> searchDate(User user);
    int deleteUser(User user);

    List<Information> getUserInformationList();
    Information query_UserInformation(Information information);
    int editUserInformation(Information information);
    List<Information> searchInfo(Information information);
    List<Information> searchBirthday(Information information);
    List<Information> searchPhone(Information information);
    List<Information> searchWeChat(Information information);
    int deleteUserInformation(Information information);

    int truncateUserTable();
    int truncateInformationTable();
}
