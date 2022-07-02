package com.yinggu.mapper;

import com.yinggu.pojo.Complainant;
import com.yinggu.pojo.Page;
import com.yinggu.pojo.Product;
import com.yinggu.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplainantMapper {
    int addComplainant(Complainant complainant);//增加产品信息
    Complainant queryRespondentComplainant(Complainant complainant);//根据respondent查询投诉信息
    int getRespondentTotalCount(Complainant complainant);//得到产品信息总条数
    List<Complainant> getRespondentComplainantList(Page page);//得到投诉信息列表

    List<Complainant> getComplainantList();
    Complainant queryIdComplainant(Complainant complainant);
    int editComplainant(Complainant complainant);
    int deleteIdComplainant(Complainant complainant);
    List<Complainant> searchComplainantinfo(Complainant complainant);
    List<Complainant> searchDateComplainant(Complainant complainant);
    int truncateComplainantTable();
}
