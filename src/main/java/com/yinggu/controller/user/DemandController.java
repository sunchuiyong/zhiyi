package com.yinggu.controller.user;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.DemandMapper;
import com.yinggu.pojo.Demand;
import com.yinggu.pojo.Page;
import com.yinggu.pojo.User;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/demand")
public class DemandController {
    @Autowired
    private DemandMapper demandMapper;
    @RequestMapping("/demandlist/{currentPage}")
    public String demandList(@PathVariable("currentPage") int currentPage){
        int totalCount = demandMapper.getTotalCount();
        if(currentPage>0 && totalCount>0){
            Page page = new Page(6,currentPage,totalCount);
            List<Demand> demandList = demandMapper.getDemandList(page);
            //System.out.println("ab"+demandList.size());
            if (demandList.size()<6){
                int size = 6-demandList.size();
                for (int i=0;i<size;i++){
                    Demand demand = new Demand();
                    demand.setTitle("");
                    demand.setContent("");
                    demand.setPlace("");
                    demand.setTime("");
                    demandList.add(demand);
                }
            }
            ResultObject obj = ResultObject.obj(demandList);
            if (demandList == null){
                obj.setResult(false);
            }
            return JSON.toJSONString(obj);
        }else if (totalCount <= 0){
            return JSON.toJSONString("需求列表数据为空！");
        } else {
            return JSON.toJSONString("列表获取失败，请重新加载！");
        }
    }
    @RequestMapping("/userDemandlist/{currentPage}")
    public String userDemandList(@PathVariable("currentPage") int currentPage, User user){
        //System.out.println(user);
        int totalCount = demandMapper.getUserTotalCount(user);
        //System.out.println(totalCount);
        if(currentPage>0 && totalCount>0){
            Page page = new Page(6,currentPage,totalCount);
            page.setUserName(user.getUserName());
            List<Demand> demandList = demandMapper.getUserDemandList(page);
            //System.out.println("ab"+demandList.size());
            if (demandList.size()<6){
                int size = 6-demandList.size();
                for (int i=0;i<size;i++){
                    Demand demand = new Demand();
                    demand.setTitle("");
                    demand.setContent("");
                    demand.setPlace("");
                    demand.setTime("");
                    demandList.add(demand);
                }
            }
            ResultObject obj = ResultObject.obj(demandList);
            if (demandList == null){
                obj.setResult(false);
            }
            return JSON.toJSONString(obj);
        }else if (totalCount <= 0){
            return JSON.toJSONString("需求列表数据为空！");
        } else {
            return JSON.toJSONString("列表获取失败，请重新加载！");
        }
    }
    @RequestMapping("/totalPage")
    public String totalPage(){
        int totalCount = demandMapper.getTotalCount();
        Page page = new Page(6,totalCount);
        ResultObject obj = ResultObject.obj(page.getTotalPage());
        if (totalCount < 0){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/userTotalPage")
    public String userTotalPage(User user){
        //System.out.println(user);
        if(user != null ){
            int totalCount = demandMapper.getUserTotalCount(user);
            //System.out.println(totalCount);
            //System.out.println(totalCount % 6);
            //System.out.println(totalCount % 6 == 0 ? 1 : totalCount/6 + 1);
            Page page = new Page(6,totalCount);
            //System.out.println(page.getTotalPage());
            ResultObject obj = ResultObject.obj(page.getTotalPage());
            if (totalCount < 0){
                obj.setResult(false);
            }
            return JSON.toJSONString(obj);
        }else {
            System.out.println("用户为空");
            ResultObject obj = ResultObject.obj(null);
            obj.setResult(false);
            return JSON.toJSONString("用户为空");
        }
    }

    @RequestMapping("/addDemandText")
    public String addDemand(Demand demand){
        demand.setTime("时间："+demand.getTime());
        demand.setPlace("地址："+demand.getPlace());
        //System.out.println(demand);
        int i = demandMapper.addDemand(demand);
        ResultObject obj = ResultObject.obj(null);
        if(i == 0){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping(value = "/get/{saveImgName}",produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public BufferedImage getImage(@PathVariable("saveImgName") String saveImgName) throws IOException {
        //System.out.println(saveImgName);
        if(saveImgName.equals("undefined")){
            try (
                    InputStream is = new FileInputStream("D:\\Javabao\\idea2020.1\\ideaIUworkspace\\zhiyi\\target\\img\\0000.JPG")){
                return ImageIO.read(is);
            }
        }else {
            try (
                    InputStream is = new FileInputStream("D:\\Javabao\\idea2020.1\\ideaIUworkspace\\zhiyi\\target\\img\\"+saveImgName)){
                return ImageIO.read(is);
            }
        }

    }
    @RequestMapping(value = "/addDemandImg",method = RequestMethod.POST)
    public String addDemand(@RequestParam("imgfile") MultipartFile imgfile){
        String path = "D:\\Javabao\\idea2020.1\\ideaIUworkspace\\zhiyi\\target\\img";
        String fileName = imgfile.getOriginalFilename();//获取文件名称
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
        String savefileName= UUID.randomUUID().toString().substring(0,4)+"_"+fileName;//重新生成文件名
        //System.out.println(fileName);
        //System.out.println("type::" + suffixName);
        //System.out.println("savefilename::" + savefileName);
        File targetFile = new File(path);
        if (!targetFile.exists()) {
            // 判断文件夹是否未空，空则创建
            targetFile.mkdirs();
        }
        File saveFile = new File(targetFile, savefileName);
        try {
            //指定本地存入路径
            imgfile.transferTo(saveFile);
            System.out.println("执行成功");
            String path1 = path + fileName;
            System.out.println(path1);
            //     return "success";
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败");
            return "failed";
        }

        Demand demand = new Demand();
        demand.setSaveImgName(savefileName);
        demand.setSavePath(path);
        ResultObject obj = ResultObject.obj(demand);
        if(demand == null){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/deleteDemand/{id}")
    public String deleteDemand(@PathVariable("id") int id){
        Demand demand = new Demand();
        demand.setId(id);
        Demand queryIdDemand = demandMapper.queryIdDemand(demand);
        //System.out.println(queryIdDemand);
        ResultObject obj = ResultObject.obj(null);
        if(demand != null){
            String filePath = queryIdDemand.getSavePath()+"\\"+queryIdDemand.getSaveImgName();
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

            int i = demandMapper.deleteIdDemand(demand);
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
    @RequestMapping("/searchDemand")
    public String searchDemand(String searchtext){
        System.out.println(searchtext);
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Demand demand = new Demand();
            demand.setUserName(searchtext);
            demand.setTitle(searchtext);
            demand.setTime(searchtext);
            demand.setPlace(searchtext);
            List<Demand> demands1 = demandMapper.searchUserNameDemand(demand);
            System.out.println(demands1.size());
            if(demands1.size() != 0){
                listsize(demands1);
                obj = ResultObject.obj(demands1);
            }else {
                List<Demand> demands2 = demandMapper.searchTitleDemand(demand);
                System.out.println(demands2.size());
                if (demands2.size() != 0){
                    listsize(demands2);
                    obj = ResultObject.obj(demands2);
                }else {
                    List<Demand> demands3 = demandMapper.searchPlaceDemand(demand);
                    System.out.println(demands3.size());
                    if (demands3.size() != 0){
                        listsize(demands3);
                        obj = ResultObject.obj(demands3);
                    }else {
                        List<Demand> demands4 = demandMapper.searchTimeDemand(demand);
                        System.out.println(demands4.size());
                        if (demands4.size() != 0){
                            listsize(demands4);
                            obj = ResultObject.obj(demands4);
                        }else {
                            obj = ResultObject.obj(null);
                        }
                    }
                }
            }
        }
        return JSON.toJSONString(obj);
    }
    public void listsize(List<Demand> demandList){
        if (demandList.size()<6){
            int size = 6-demandList.size();
            for (int i=0;i<size;i++){
                Demand demand = new Demand();
                demand.setTitle("");
                demand.setContent("");
                demand.setPlace("");
                demand.setTime("");
                demandList.add(demand);
            }
        }
    }
}
