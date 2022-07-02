package com.yinggu.mapper;

import com.yinggu.pojo.Demand;
import com.yinggu.pojo.Page;
import com.yinggu.pojo.Product;
import com.yinggu.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductMapper {
    int addProduct(Product product);//增加产品信息
    int getProductTotalCount(User user);//得到产品信息总条数
    List<Product> getUserProductList(Page page);//得到userName产品信息列表
    Product queryUserProduct(Product product);//根据user查询产品信息
    Product queryIdProduct(Product product);//根据id查询产品信息
    int deleteIdProduct(Product product);//根据id删除产品信息
    int editProduct(Product product);

    List<Product> getProductList();
    List<Product> searchProductInfo(Product product);
    List<Product> searchProductDate(Product product);

    int truncateProductTable();
}
