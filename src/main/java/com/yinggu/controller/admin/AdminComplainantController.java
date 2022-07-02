package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.ComplainantMapper;
import com.yinggu.mapper.DemandMapper;
import com.yinggu.pojo.Complainant;
import com.yinggu.pojo.Demand;
import com.yinggu.pojo.Product;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.List;

@RestController
public class AdminComplainantController {
    @Autowired
    private ComplainantMapper complainantMapper;
    @RequestMapping("/complainantList")
    public String getComplainantList(){
        //System.out.println();
        List<Complainant> complainantList = complainantMapper.getComplainantList();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(complainantList.size() > 0){
            obj.setDt(complainantList);
        }else {
            obj.setDt("投诉信息表为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/queryComplainant")
    public String queryComplainant(Complainant complainant){
        //System.out.println("queryAdmin  "+admin);
        Complainant rs = complainantMapper.queryIdComplainant(complainant);
        ResultObject obj = ResultObject.obj(rs);
        //System.out.println(rs);
        if(rs == null){
            obj.setDt("需求信息不存在");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/editComplainant")
    public String editComplainant(Complainant complainant){
        //System.out.println("editAdmin  "+admin);

        Complainant idComplainant = complainantMapper.queryIdComplainant(complainant);
        ResultObject obj = ResultObject.obj(null);
        if(idComplainant != null){
            String savePath = idComplainant.getSavePath();
            String saveName1 = idComplainant.getEvidence1();
            String saveName2 = idComplainant.getEvidence2();
            String saveName3 = idComplainant.getEvidence3();

            deleteComplainantImg(savePath,saveName1);
            deleteComplainantImg(savePath,saveName2);
            deleteComplainantImg(savePath,saveName3);

            int i = complainantMapper.editComplainant(complainant);

            //System.out.println(rs);
            if(i == 0){
                obj.setDt("修改失败");
                obj.setResult(false);
            }
        }else {
            obj.setDt("投诉信息为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    public void deleteComplainantImg(String savePath,String saveImgName){
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
    @RequestMapping("/searchComplainantinfo")
    public String searchDemandinfo(String searchtext){
        System.out.println(searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Complainant complainant = new Complainant();
            complainant.setComplainant(searchtext);
            complainant.setRespondent(searchtext);
            complainant.setData(searchtext);
            List<Complainant> complainants1 = complainantMapper.searchComplainantinfo(complainant);
            //System.out.println(demands1.size());
            if(complainants1.size() != 0){
                obj = ResultObject.obj(complainants1);
            }else {
                if(complainant.getData().length() >= 4){
                    List<Complainant> complainants2 = complainantMapper.searchDateComplainant(complainant);
                    //System.out.println(demands4.size());
                    if (complainants2.size() != 0){
                        obj = ResultObject.obj(complainants2);
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
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/deleteComplainant/{id}")
    public String deleteComplainant(@PathVariable("id") int id){
        Complainant complainant = new Complainant();
        complainant.setId(id);
        Complainant queryIdComplainant = complainantMapper.queryIdComplainant(complainant);
        //System.out.println(queryIdProduct);

        ResultObject obj = ResultObject.obj(null);
        if(queryIdComplainant != null){

            String savePath = queryIdComplainant.getSavePath();
            String saveImgName1 = queryIdComplainant.getEvidence1();
            String saveImgName2 = queryIdComplainant.getEvidence2();
            String saveImgName3 = queryIdComplainant.getEvidence3();

            deleteComplainantImg(savePath,saveImgName1);
            deleteComplainantImg(savePath,saveImgName2);
            deleteComplainantImg(savePath,saveImgName3);

            int i = complainantMapper.deleteIdComplainant(complainant);
            if(i == 0){
                obj.setDt("删除失败");
                obj.setResult(false);
            }
        }else {
            obj.setDt("上传需求信息为空");
            obj.setResult(false);
        }

        return JSON.toJSONString(obj);
    }
    @RequestMapping("/truncateComplainantTable")
    public String truncateComplainantTable(){
        int i = complainantMapper.truncateComplainantTable();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 1){
            obj.setDt("清空数据表失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
