package org.bokhoeva.services;

import org.bokhoeva.dao.AppDao;
import org.bokhoeva.model.Category;
import org.bokhoeva.model.Product;
import org.bokhoeva.model.Unit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class AppService {

    private AppDao appDao;

    @Autowired
    public AppService(AppDao appDao) {
        this.appDao = appDao;
    }

    public List<Category> getCategories(boolean forProduct, boolean forCategory, int parentId) {
        return appDao.getCategories(forProduct,forCategory,parentId);
    }

    public List<Product> getProductsByCategory(int categoryId) {
        return appDao.getProductsByCategory(categoryId);
    }

    public Product getProduct(int productId) {
        return appDao.getProduct(productId);
    }

    public void saveProduct(Product product) {
         appDao.saveProduct(product);
    }

    public void updateProduct(Product product) {
        appDao.updateProduct(product);
    }

    public void deleteProduct(int product) {
        appDao.deleteProduct(product);
    }

    public Category getCategory(int id) {
        return appDao.getCategory(id);
    }

    public void saveCategory(Category category) {
        appDao.saveCategory(category);
    }


    public void updateCategory(Category category) {
        appDao.updateCategory(category);
    }


    public void deleteCategory(int id) {
        appDao.deleteCategory(id);
    }

    public List<Unit> getUnits() {
        return appDao.getUnits();
    }


}
