package com.yinggu.controller.user;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.ProductMapper;
import com.yinggu.pojo.*;
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

@RestController
public class ProductController {
    @Autowired
    private ProductMapper productMapper;
    @RequestMapping(value = "/addProductImg",method = RequestMethod.POST)
    public String addProduct(@RequestParam("imgfile1") MultipartFile imgfile1,@RequestParam("imgfile2") MultipartFile imgfile2,@RequestParam("imgfile3") MultipartFile imgfile3){
        String path = "D:\\Javabao\\idea2020.1\\ideaIUworkspace\\zhiyi\\target\\img_product";
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

        Product product = new Product();
        product.setSaveImgName1(savefileName1);
        product.setSaveImgName2(savefileName2);
        product.setSaveImgName3(savefileName3);
        product.setSavePath(path);
        ResultObject obj = ResultObject.obj(product);
        if(product == null){
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
    @RequestMapping(value = "/getImgProduct/{saveImgName}",produces = MediaType.IMAGE_JPEG_VALUE)
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
                    InputStream is = new FileInputStream("D:\\Javabao\\idea2020.1\\ideaIUworkspace\\zhiyi\\target\\img_product\\"+saveImgName)){
                return ImageIO.read(is);
            }
        }
    }
    @RequestMapping("/addProductText")
    public String addProduct(Product product){
        product.setDate("时间："+product.getDate());
        product.setPlace("地址："+product.getPlace());
        System.out.println(product);
        int i = productMapper.addProduct(product);
        ResultObject obj = ResultObject.obj(null);
        if(i == 0){
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/userProductlist/{currentPage}")
    public String userDemandList(@PathVariable("currentPage") int currentPage, User user){
        System.out.println(user);
        int totalCount = productMapper.getProductTotalCount(user);
        System.out.println(totalCount);
        if(currentPage>0 && totalCount>0){
            Page page = new Page(6,currentPage,totalCount);
            page.setUserName(user.getUserName());
            List<Product> userProductList = productMapper.getUserProductList(page);
            //System.out.println("ab"+demandList.size());
            if (userProductList.size()<6){
                int size = 6-userProductList.size();
                for (int i=0;i<size;i++){
                    Product product = new Product();
                    product.setProductName("");
                    product.setProductPrice("");
                    product.setProductDetails("");
                    product.setDate("");
                    product.setPlace("");
                    userProductList.add(product);
                }
            }
            ResultObject obj = ResultObject.obj(userProductList);
            if (userProductList == null){
                obj.setResult(false);
            }
            return JSON.toJSONString(obj);
        }else if (totalCount <= 0){
            return JSON.toJSONString("需求列表数据为空！");
        } else {
            return JSON.toJSONString("列表获取失败，请重新加载！");
        }
    }
    @RequestMapping("/productTotalPage")
    public String productTotalPage(User user){
        //System.out.println(user);
        if(user != null ){
            int totalCount = productMapper.getProductTotalCount(user);
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
    @RequestMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable("id") int id){
        Product product = new Product();
        product.setId(id);
        Product queryIdProduct = productMapper.queryIdProduct(product);
        //System.out.println(queryIdProduct);

        ResultObject obj = ResultObject.obj(null);
        if(queryIdProduct != null){

            String savePath = queryIdProduct.getSavePath();
            String saveImgName1 = queryIdProduct.getSaveImgName1();
            String saveImgName2 = queryIdProduct.getSaveImgName2();
            String saveImgName3 = queryIdProduct.getSaveImgName3();

            deleteProductImg(savePath,saveImgName1);
            deleteProductImg(savePath,saveImgName2);
            deleteProductImg(savePath,saveImgName3);

            int i = productMapper.deleteIdProduct(product);
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
    public void deleteProductImg(String savePath,String saveImgName){
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
    @RequestMapping("/queryProduct")
    public String queryProduct(Product product){
        //System.out.println(information);
        Product queryUserProduct = productMapper.queryIdProduct(product);
        ResultObject rs = ResultObject.obj(queryUserProduct);
        if(queryUserProduct == null){
            rs.setResult(false);
        }
        return JSON.toJSONString(rs);
    }
    @RequestMapping("/editProduct")
    public String editProduct(Product product){
        //System.out.println("editAdmin  "+admin);
        Product idProduct = productMapper.queryIdProduct(product);
        ResultObject obj = ResultObject.obj(null);
        if(idProduct != null){
            String savePath = idProduct.getSavePath();
            String saveImgName1 = idProduct.getSaveImgName1();
            String saveImgName2 = idProduct.getSaveImgName2();
            String saveImgName3 = idProduct.getSaveImgName3();

            deleteProductImg(savePath,saveImgName1);
            deleteProductImg(savePath,saveImgName2);
            deleteProductImg(savePath,saveImgName3);

            int i = productMapper.editProduct(product);

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
}
