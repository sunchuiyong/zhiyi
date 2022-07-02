package com.yinggu.mapper;

import com.yinggu.pojo.Admin;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminMapper {
    Admin login(Admin admin);
    List<Admin> getAdminList();
    int addAdmin(Admin admin);
    Admin queryAdmin(Admin admin);
    int editAdmin(Admin admin);
    List<Admin> searchAdminName(Admin admin);
    List<Admin> searchRoleId(Admin admin);
    List<Admin> searchDate(Admin admin);
    int deleteAdmin(Admin admin);
}
