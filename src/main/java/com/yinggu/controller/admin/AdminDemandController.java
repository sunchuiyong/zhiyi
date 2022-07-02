package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.DemandMapper;
import com.yinggu.mapper.ProductMapper;
import com.yinggu.pojo.Demand;
import com.yinggu.pojo.Product;
import com.yinggu.pojo.User;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class AdminDemandController {
    @Autowired
    private DemandMapper demandMapper;
    @RequestMapping("/demandList")
    public String getdemandList(){
        //System.out.println();
        List<Demand> demandInfoList = demandMapper.getDemandInfoList();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(demandInfoList.size() > 0){
            obj.setDt(demandInfoList);
        }else {
            obj.setDt("需求信息表为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/queryDemand")
    public String queryDemand(Demand demand){
        //System.out.println("queryAdmin  "+admin);
        Demand rs = demandMapper.queryIdDemand(demand);
        ResultObject obj = ResultObject.obj(rs);
        //System.out.println(rs);
        if(rs == null){
            obj.setDt("需求信息不存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/editDemand")
    public String editDemand(Demand demand){
        //System.out.println("editAdmin  "+admin);

        Demand idDemand = demandMapper.queryIdDemand(demand);
        ResultObject obj = ResultObject.obj(null);
        if(idDemand != null){
            String savePath = idDemand.getSavePath();
            String saveImgName = idDemand.getSaveImgName();
            deleteDemandImg(savePath,saveImgName);

            int i = demandMapper.editDemand(demand);

            //System.out.println(rs);
            if(i == 0){
                obj.setDt("修改失败");
                obj.setResult(false);
            }
        }else {
            obj.setDt("产品信息为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    public void deleteDemandImg(String savePath,String saveImgName){
        String filePath = savePath+"\\"+saveImgName;
        //System.out.println(filePath);
        File file = new File(filePath);
        try {
            boolean flag = file.delete(); // 删除照片
            if (flag) {
                System.out.println("图片删除成功");
            } else {
                System.out.println("图片删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("图片不存在或其他异常！");
        }
    }
    @RequestMapping("/searchDemandinfo")
    public String searchDemandinfo(String searchtext){
        System.out.println(searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Demand demand = new Demand();
            demand.setUserName(searchtext);
            demand.setTitle(searchtext);
            demand.setTime(searchtext);
            demand.setPlace(searchtext);
            List<Demand> demands1 = demandMapper.searchUserNameDemand(demand);
            //System.out.println(demands1.size());
            if(demands1.size() != 0){
                obj = ResultObject.obj(demands1);
            }else {
                List<Demand> demands2 = demandMapper.searchTitleDemand(demand);
                //System.out.println(demands2.size());
                if (demands2.size() != 0){
                    obj = ResultObject.obj(demands2);
                }else {
                    List<Demand> demands3 = demandMapper.searchPlaceDemand(demand);
                    //System.out.println(demands3.size());
                    if (demands3.size() != 0){
                        obj = ResultObject.obj(demands3);
                    }else {
                        if(demand.getTime().length() >= 4){
                            List<Demand> demands4 = demandMapper.searchTimeDemand(demand);
                            //System.out.println(demands4.size());
                            if (demands4.size() != 0){
                                obj = ResultObject.obj(demands4);
                            }else {
                                obj.setDt("未找到");
                                obj.setResult(false);
                            }
                        }else {
                            obj.setDt("未找到");
                            obj.setResult(false);
                        }
                    }
                }
            }
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/truncateDemandTable")
    public String truncateDemandTable(){
        int i = demandMapper.truncateDemandTable();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 1){
            obj.setDt("清空数据表失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
