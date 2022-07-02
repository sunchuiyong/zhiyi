package com.yinggu.mapper;

import com.yinggu.pojo.Admin;
import com.yinggu.pojo.Admininfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmininfoMapper {
    Admininfo query(Admininfo admininfo);
    List<Admininfo> getAdmininfoList();
    int addAdmininfo(Admininfo admininfo);
    Admininfo queryAdmininfo(Admininfo admininfo);
    int editAdmininfo(Admininfo admininfo);
    List<Admininfo> searchAdminName(Admininfo admininfo);
    List<Admininfo> searchName(Admininfo admininfo);
    List<Admininfo> searchSex(Admininfo admininfo);
    List<Admininfo> searchBirthday(Admininfo admininfo);
    List<Admininfo> searchPhone(Admininfo admininfo);
    List<Admininfo> searchPlace(Admininfo admininfo);
    int deleteAdmininfo(Admininfo admininfo);

    int truncateAdmininfoTable();
}
