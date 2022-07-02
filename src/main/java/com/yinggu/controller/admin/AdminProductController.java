package com.yinggu.controller.admin;

import com.alibaba.fastjson.JSON;
import com.yinggu.mapper.ProductMapper;
import com.yinggu.mapper.UserMapper;
import com.yinggu.pojo.Information;
import com.yinggu.pojo.Product;
import com.yinggu.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminProductController {
    @Autowired
    private ProductMapper productMapper;
    @RequestMapping("/productList")
    public String getproductList(){
        //System.out.println();
        List<Product> productList = productMapper.getProductList();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(productList.size() > 0){
            obj.setDt(productList);
        }else {
            obj.setDt("产品信息表为空");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/searchProduct")
    public String searchProduct(String searchtext){
        //System.out.println(searchtext.length());
        ResultObject obj = ResultObject.obj(null);
        if(searchtext != ""){
            Product product = new Product();
            product.setUserName(searchtext);
            product.setProductName(searchtext);
            product.setPlace(searchtext);
            product.setDate(searchtext);
            List<Product> product1 = productMapper.searchProductInfo(product);
            //System.out.println(admins1.size());
            if(product1.size() != 0){
                obj = ResultObject.obj(product1);
            }else {
                if(product.getDate().length()>=4 && product.getDate().length()<=10){
                    List<Product> product2 = productMapper.searchProductDate(product);
                    if(product2.size() != 0){
                        obj = ResultObject.obj(product2);
                    }else {
                        obj = ResultObject.obj("未找到！");
                        obj.setResult(false);
                    }
                }else{
                    obj = ResultObject.obj("未找到！");
                    obj.setResult(false);
                }
            }
        }
        return JSON.toJSONString(obj);
    }
    @RequestMapping("/truncateProductTable")
    public String truncateProductTable(){
        int i = productMapper.truncateProductTable();
        ResultObject obj = ResultObject.obj(null);
        //System.out.println(rs);
        if(i == 1){
            obj.setDt("清空数据表失败");
            obj.setResult(false);
        }
        return JSON.toJSONString(obj);
    }
}
