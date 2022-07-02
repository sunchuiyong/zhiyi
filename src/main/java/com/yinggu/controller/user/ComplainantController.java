package com.yinggu.controller.user;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.ComplainantMapper;
import com.yinggu.pojo.*;
import com.yinggu.pojo.Complainant;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.UUID;

@RestController
public class ComplainantController {
    @Autowired
    private ComplainantMapper complainantMapper;
    @RequestMapping(value = "/addComplainantImg",method = RequestMethod.POST)
    public String addComplainant(@RequestParam("imgfile1") MultipartFile imgfile1, @RequestParam("imgfile2") MultipartFile imgfile2, @RequestParam("imgfile3") MultipartFile imgfile3){
        String path = "D:\\Javabao\\idea2020.1\\ideaIUworkspace\\zhiyi\\target\\complainant";
        String fileName1 = imgfile1.getOriginalFilename();//获取文件名称
        String fileName2 = imgfile2.getOriginalFilename();//获取文件名称
        String fileName3 = imgfile3.getOriginalFilename();//获取文件名称
        System.out.println(fileName1);
        System.out.println(fileName2);
        System.out.println(fileName3);
        String savefileName1 = saveImg(imgfile1, path);
        if(savefileName1 == "false"){
            System.out.println("图1存储失败");
        }
        String savefileName2 = saveImg(imgfile2, path);
        if(savefileName2 == "false"){
            System.out.println("图2存储失败");
        }
        String savefileName3 = saveImg(imgfile3, path);
        if(savefileName3 == "false"){
            System.out.println("图3存储失败");
        }

        Complainant complainant = new Complainant();
        complainant.setEvidence1(savefileName1);
        complainant.setEvidence2(savefileName2);
        complainant.setEvidence3(savefileName3);
        complainant.setSavePath(path);
        ResultObject obj = ResultObject.obj(complainant);
        if(complainant == null){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    public String saveImg(MultipartFile imgfile, String path){

        String fileName = imgfile.getOriginalFilename();
        String suffixName=fileName.substring(fileName.lastIndexOf("."));//获取文件后缀
        String savefileName= UUID.randomUUID().toString().substring(0,4)+"_"+fileName;//重新生成文件名
        System.out.println(fileName);
        System.out.println("type::" + suffixName);
        System.out.println("savefilename::" + savefileName);
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
            return savefileName;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("执行失败");
            return "false";
        }
    }

    @RequestMapping("/addComplainant")
    public String addComplainant(Complainant complainant){
        //System.out.println(complainant);
        int i = complainantMapper.addComplainant(complainant);
        ResultObject obj = ResultObject.obj(null);
        if(i == 0){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }

    @RequestMapping("/respondentTotalCount")
    public String getRespondentTotalCount(Complainant complainant){
        //System.out.println(complainant);
        int i = complainantMapper.getRespondentTotalCount(complainant);
        ResultObject obj = ResultObject.obj(i);
        if(i == 0){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/respondentComplainantlist/{currentPage}")
    public String respondentComplainantlist(@PathVariable("currentPage") int currentPage, Complainant complainant){
        //System.out.println(complainant);
        int totalCount = complainantMapper.getRespondentTotalCount(complainant);
        //System.out.println(totalCount);
        if(currentPage>0 && totalCount>0){
            Page page = new Page(6,currentPage,totalCount);
            page.setRespondent(complainant.getRespondent());
            List<Complainant> respondentComplainantList = complainantMapper.getRespondentComplainantList(page);
            //System.out.println("ab"+demandList.size());
            if (respondentComplainantList.size()<6){
                int size = 6-respondentComplainantList.size();
                for (int i=0;i<size;i++){
                    Complainant complainant1 = new Complainant();
                    complainant1.setComplainant("");
                    complainant1.setRespondent("");
                    complainant1.setContent("");
                    complainant1.setData("");
                    respondentComplainantList.add(complainant1);
                }
            }
            ResultObject obj = ResultObject.obj(respondentComplainantList);
            if (respondentComplainantList == null){
                obj.setResult(false);
            }
            return JSON.toJSONString(obj);
        }else if (totalCount <= 0){
            return JSON.toJSONString("需求列表数据为空！");
        } else {
            return JSON.toJSONString("列表获取失败，请重新加载！");
        }
    }
    @RequestMapping("/download1/{id}")
    @ResponseBody
    public String download1(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        //System.out.println(id);
        Complainant complainant = new Complainant();
        complainant.setId(id);
        Complainant queryIdComplainant = complainantMapper.queryIdComplainant(complainant);
        ResultObject obj = ResultObject.obj("下载成功");
        if(queryIdComplainant != null){
            String path = queryIdComplainant.getSavePath();
            String evidence1 = queryIdComplainant.getEvidence1();

            boolean downloadFile1 = downloadFile(request, response, evidence1, path);

            if(downloadFile1 == true){
                obj.setDt("材料1下载成功");
            }else {
                obj.setDt("材料1下载失败");
                obj.setResult(false);
            }

        }else {
            obj.setDt("投诉信息为空");
            obj.setResult(false);
        }

        return JSON.toJSONString(obj);
    }

    @RequestMapping("/download2/{id}")
    public String download2(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        //System.out.println(id);
        Complainant complainant = new Complainant();
        complainant.setId(id);
        Complainant queryIdComplainant = complainantMapper.queryIdComplainant(complainant);
        ResultObject obj = ResultObject.obj("下载成功");
        if(queryIdComplainant != null){
            String path = queryIdComplainant.getSavePath();
            String evidence2 = queryIdComplainant.getEvidence2();

            boolean downloadFile2 = downloadFile(request, response, evidence2, path);

            if(downloadFile2 == true){
                obj.setDt("材料2下载成功");
            }else {
                obj.setDt("材料2下载失败");
                obj.setResult(false);
            }

        }else {
            obj.setDt("投诉信息为空");
            obj.setResult(false);
        }

        return JSON.toJSONString(obj);
    }
    @RequestMapping("/download3/{id}")
    public String download3(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") int id) {
        //System.out.println(id);
        Complainant complainant = new Complainant();
        complainant.setId(id);
        Complainant queryIdComplainant = complainantMapper.queryIdComplainant(complainant);
        ResultObject obj = ResultObject.obj("下载成功");
        if(queryIdComplainant != null){
            String path = queryIdComplainant.getSavePath();
            String evidence3 = queryIdComplainant.getEvidence3();

            boolean downloadFile3 = downloadFile(request, response, evidence3, path);

            if(downloadFile3 == true){
                obj.setDt("材料3下载成功");
            }else {
                obj.setDt("材料3下载失败");
                obj.setResult(false);
            }

        }else {
            obj.setDt("投诉信息为空");
            obj.setResult(false);
        }

        return JSON.toJSONString(obj);
    }

    public boolean downloadFile(HttpServletRequest request, HttpServletResponse response,String fileName,String path) {
        if (fileName != null || path != null) {
            String evidencePath = path +"\\"+ fileName;
            //设置文件路径
            File file = new File(evidencePath);
            //File file = new File(realPath , fileName);
            if (file.exists()) {
                //response.setContentType("application/force-download");// 设置强制下载不打开
                try {
                    response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));// 设置文件名
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("材料下载成功");
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }
    @RequestMapping("/respondentTotalPage")
    public String respondentTotalPage(Complainant complainant){
        //System.out.println(user);
        if(complainant != null ){
            int totalCount = complainantMapper.getRespondentTotalCount(complainant);
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
}
