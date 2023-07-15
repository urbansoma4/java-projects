package com.codecool.buyourstuff.dao;

import com.codecool.buyourstuff.model.*;
import com.codecool.buyourstuff.model.exception.DataNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LineItemDaoTest {
    private static final LineItemDao LINE_ITEM_DAO = DataManager.getLineItemDao();
    private static final SupplierDao SUPPLIER_DAO = DataManager.getSupplierDao();
    private static final ProductCategoryDao PRODUCT_CATEGORY_DAO = DataManager.getProductCategoryDao();
    private static final ProductDao PRODUCT_DAO = DataManager.getProductDao();
    private static final CartDao CART_DAO = DataManager.getCartDao();

    private static Supplier testSupplier = new Supplier("test", "test");
    private static ProductCategory testProductCategory = new ProductCategory("test", "test", "test");
    private static Product testProduct = new Product("Test", new BigDecimal(12),
            "USD", "test", testProductCategory, testSupplier);
    private static Cart testCart = new Cart();

    @BeforeEach
    void setup() {
        SUPPLIER_DAO.add(testSupplier);
        PRODUCT_CATEGORY_DAO.add(testProductCategory);
        PRODUCT_DAO.add(testProduct);
        CART_DAO.add(testCart);

    }

    @AfterEach
    void breakdown() {
        SUPPLIER_DAO.remove(testSupplier.getId());
        PRODUCT_CATEGORY_DAO.remove(testProductCategory.getId());
        PRODUCT_DAO.remove(testProduct.getId());
        CART_DAO.remove(testCart.getId());
    }

    @Test
    void testAdd() {
        LineItem lineItem = new LineItem(testProduct, testCart.getId(), 1);
        LINE_ITEM_DAO.add(lineItem);
        assertNotEquals(0, lineItem.getId());
        LINE_ITEM_DAO.remove(lineItem);
    }

    @Test
    void testFind_validId() {
        LineItem lineItem = new LineItem(testProduct, testCart.getId(), 1);
        LINE_ITEM_DAO.add(lineItem);

        LineItem result = LINE_ITEM_DAO.find(lineItem.getId());
        assertEquals(lineItem.getId(), result.getId());
        LINE_ITEM_DAO.remove(lineItem);
    }

    @Test
    void testFind_invalidId() {
        assertThrows(DataNotFoundException.class, () -> LINE_ITEM_DAO.find(-1));
    }

    @Test
    void testRemove() {
        LineItem lineItem = new LineItem(testProduct, testCart.getId(), 1);
        LINE_ITEM_DAO.add(lineItem);
        assertNotNull(LINE_ITEM_DAO.find(lineItem.getId()));

        LINE_ITEM_DAO.remove(lineItem);
        assertThrows(DataNotFoundException.class, () -> LINE_ITEM_DAO.find(lineItem.getId()));
    }

    @Test
    void getBy_validCart_returnsValid() {
        LineItem lineItem = new LineItem(testProduct, testCart.getId(), 1);
        LINE_ITEM_DAO.add(lineItem);

        List<LineItem> result = LINE_ITEM_DAO.getBy(testCart);
        assertEquals(lineItem.getId(), result.get(0).getId());
        LINE_ITEM_DAO.remove(lineItem);
    }

}
