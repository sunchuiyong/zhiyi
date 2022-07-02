package com.yinggu;

import com.yinggu.mapper.*;
import com.yinggu.pojo.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ZhiyiApplicationTests {
    @Autowired
    private DemandMapper demandMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ComplainantMapper complainantMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Test
    void contextLoads() {

        Information information = new Information();
        information.setUserName("韵");
        information.setName("韵");
        information.setSex("韵");
        information.setPlace("韵");
        information.setBirthday("");
        information.setPhone("");
        information.setWeChat("");
        information.setEmail("");
        List<Information> informationList = userMapper.searchInfo(information);
        System.out.println(informationList);

//        User user = new User();
//        user.setUserName("小");
//        List<User> users = userMapper.searchUserName(user);
//        System.out.println(user);

//        Admin admin = new Admin();
//        admin.setId(1);
//        Admin admin1 = adminMapper.queryAdmin(admin);
//        System.out.println(admin1);

//        Admin admin = new Admin();
//        admin.setAdminName("ss");
//        admin.setPwd("123456");
//        admin.setRoleId("1");
//        admin.setDate("2022-10-01");
//        int i = adminMapper.addAdmin(admin);
//        System.out.println(i);

//        List<Admin> adminList = adminMapper.getAdminList();
//        System.out.println(adminList);

//        Admin admin = new Admin();
//        admin.setAdminName("admin");
//        admin.setPwd("123456");
//        Admin login = adminMapper.login(admin);
//        System.out.println(login);

//        Complainant complainant = new Complainant();
//        complainant.setRespondent("风逸");
//        int respondentTotalCount = complainantMapper.getRespondentTotalCount(complainant);
//        System.out.println(respondentTotalCount);

//        User user = new User();
//        user.setPwd("123456");
//        User login = userMapper.login(user);
//        System.out.println(login);

//        Product product = new Product();
//        product.setId(7);
//        User user = new User();
//        user.setUserName("aa");
//        int totalCount = productMapper.getProductTotalCount(user);
//        Page page = new Page(6,1,totalCount);
//        page.setUserName("aa");
//        int deleteIdProduct = productMapper.deleteIdProduct(product);
//        Demand demand = new Demand();
//        demand.setUserName("null");
//        demand.setTitle("番茄");
//        demand.setTime("null");
//        demand.setPlace("null");
//        List<Demand> demands1 = demandMapper.searchUserNameDemand(demand);
//        List<Demand> demands2 = demandMapper.searchTitleDemand(demand);
//        List<Demand> demands3 = demandMapper.searchPlaceDemand(demand);
//        List<Demand> demands4 = demandMapper.searchTimeDemand(demand);
//        System.out.println(demands1);
//        System.out.println(demands1.size());
//        System.out.println(demands2);
//        System.out.println(demands2.size());
//        System.out.println(demands3);
//        System.out.println(demands3.size());
//        System.out.println(demands4);
//        System.out.println(demands4.size());

    }

}
