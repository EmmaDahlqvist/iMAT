package imat;

import se.chalmers.cse.dat216.project.ProductCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCategory {
    private ProductCategory product;
    private ProductCategory[] productCategorys = product.values();
    List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
    public MainCategory() {

    }
    public String getName() {
        return "name";
    }
    public List<ProductCategory> getChildren() {
        productCategoryList.addAll(Arrays.asList(productCategorys));
        return productCategoryList;
    }
}
