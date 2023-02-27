package org.bokhoeva.dao;

import org.bokhoeva.model.Category;
import org.bokhoeva.model.Product;
import org.bokhoeva.model.Unit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class AppDao {

    public SessionFactory sessionFactory;

    @Autowired
    public AppDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Category> getCategories(boolean forProduct, boolean forCategory, int parentId) {
        Session session = sessionFactory.getCurrentSession();

        List<Category> categories = null;

        if (forProduct) {
            categories = session.createQuery("From Category where id>1 and parent>1").getResultList();
        }
        if (forCategory) {
            categories = session.createQuery("From Category").getResultList();
        }
        if (!forCategory && !forProduct) {
            categories = session.createQuery("From Category  where id>1 and parent=" + parentId).getResultList();
        }

        return categories;
    }

    public List<Product> getProductsByCategory(int categoryId) {
        Session session = sessionFactory.getCurrentSession();

        List<Product> products = null;
        products = session.createQuery("FROM Product where category=" +categoryId).getResultList();
        return products;
    }

    public Product getProduct(int productId) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Product.class,productId);
    }

    public void saveProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(product);
    }

    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        Product updatedProduct = session.get(Product.class,product.getId());
        updatedProduct.setName(product.getName());
        updatedProduct.setCategory(product.getCategory());
        updatedProduct.setUnit(product.getUnit());
        updatedProduct.setAmount(product.getAmount());

    }

    public void deleteProduct(int product) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Product.class,product));
    }

    public Category getCategory(int id) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Category.class,id);
    }

    public void saveCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(category);
    }


    public void updateCategory(Category category) {
        Session session = sessionFactory.getCurrentSession();
        Category updatedCategory = session.get(Category.class,category.getId());
        updatedCategory.setName(category.getName());
        updatedCategory.setParent(category.getParent());

    }


    public void deleteCategory(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Category.class,id));
    }

    public List<Unit> getUnits() {
        Session session = sessionFactory.getCurrentSession();
        List<Unit> units = session.createQuery("FROM Unit").getResultList();
        return units;

    }

    public Unit getUnit(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Unit.class,id);
    }

    public void updateUnit(Unit unit){
        Session session = sessionFactory.getCurrentSession();

        Unit updatedUnit = session.get(Unit.class,unit.getId());
        updatedUnit.setNomination(unit.getNomination());

    }

    public void deleteUnit(int id){
        Session session = sessionFactory.getCurrentSession();
        session.delete(session.get(Unit.class,id));
    }


}
