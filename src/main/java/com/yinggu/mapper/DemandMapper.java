package com.yinggu.mapper;

import com.yinggu.pojo.Demand;
import com.yinggu.pojo.Page;
import com.yinggu.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandMapper {
    List<Demand> getDemandList(Page page);//得到总需求信息列表
    List<Demand> getUserDemandList(Page page);//得到某用户需求信息列表
    List<Demand> queryDemand(Demand demand);//查询userName的所有需求信息
    Demand queryIdDemand(Demand demand);//根据id查询需求信息
    int getTotalCount();//得到信息总条数
    int getUserTotalCount(User user);//得到userName的信息总条数
    int addDemand(Demand demand);//增加需求信息
    int deleteIdDemand(Demand demand);//根据id删除需求信息
    int deleteUserNameDemand(Demand demand);//根据userName删除需求信息
    List<Demand> searchUserNameDemand(Demand demand);//模糊搜索需求信息
    List<Demand> searchTitleDemand(Demand demand);//模糊搜索需求信息
    List<Demand> searchTimeDemand(Demand demand);//模糊搜索需求信息
    List<Demand> searchPlaceDemand(Demand demand);//模糊搜索需求信息

    List<Demand> getDemandInfoList();
    int editDemand(Demand demand);

    int truncateDemandTable();
}
