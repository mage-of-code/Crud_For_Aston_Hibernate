package org.bokhoeva.controller;

import jakarta.validation.Valid;
import org.bokhoeva.model.Category;
import org.bokhoeva.model.Product;
import org.bokhoeva.services.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {

    private final AppService appService;

    @Autowired
    public AppController(AppService appService) {
        this.appService = appService;
    }

    @GetMapping("/parent-categories")
    public String getParentCategories(Model model) {
        model.addAttribute("categories", appService.getCategories(false, false, 1));
        return "products/parentCategories";
    }

    @GetMapping("/{id}/inner-categories")
    public String getInnerCategories(Model model, @PathVariable("id") int parentId) {
        List<Category> categories = appService.getCategories(false, false, parentId);
        if (!categories.isEmpty()) {
            model.addAttribute("categories", categories);
            return "products/innerCategories";
        }
        return "redirect:/inner-categories/" + parentId;
    }

    @GetMapping("/inner-categories/{id}")
    public String getProductsByInnerCategories(@PathVariable("id") int categoryId, Model model) {
        model.addAttribute("products", appService.getProductsByCategory(categoryId));
        return "products/productsByCategory";
    }

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable("id") int productId, Model model) {
        model.addAttribute("product", appService.getProduct(productId))
                .addAttribute("categories", appService.getCategories(true, false, 0))
                .addAttribute("units", appService.getUnits());

        return "products/showProduct";
    }

    @GetMapping("/new-product")
    public String newProduct(Model model) {
        Product product = new Product();
        model.addAttribute("categories", appService.getCategories(true, false, 0))
                .addAttribute("units", appService.getUnits())
                .addAttribute(product);
        return "products/newProduct";
    }

    @PostMapping("/add-product")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "products/newProduct";
        } else {
            appService.saveProduct(product);
            model.addAttribute("categoryId", product.getCategory().getId());
            return "products/addedProduct";
        }

    }


    @PatchMapping("/update-product/{id}")
    public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult,
                                @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "products/showProduct";
        } else {
            appService.updateProduct(product);
            return "redirect:/inner-categories/" + product.getCategory().getId();
        }

    }

    @GetMapping("/new-category")
    public String newCategory(Model model) {
        Category category = new Category();
        model.addAttribute("categories", appService.getCategories(false, true, 0))
                .addAttribute(category);

        return "products/newCategory";
    }

    @PostMapping("/add-category")
    public String addCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/newCategory";
        } else {
            appService.saveCategory(category);
            return "products/addedCategory";
        }
    }

    @GetMapping("/edit-category/{id}")
    public String editCategory(Model model, @PathVariable("id") int id) {
        model.addAttribute("categories", appService.getCategories(false, true, 0))
                .addAttribute("category", appService.getCategory(id));
        return "products/editCategory";
    }

    @PatchMapping("/update-category/{id}")
    public String updateCategory(@ModelAttribute("category") @Valid Category category, BindingResult bindingResult,
                                 @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "products/editCategory";
        } else {
            appService.updateCategory(category);
            return "products/updatedCategory";
        }
    }


    @DeleteMapping("/delete-product/{categoryId}/{productId}")
    public String deleteProduct(@PathVariable("categoryId") int category, @PathVariable("productId") int product, Model model) {
        appService.deleteProduct(product);
        model.addAttribute("categoryId", category);
        return "products/productDeleted";

    }

    @DeleteMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        appService.deleteCategory(id);
        return "products/categoryDeleted";
    }


}
