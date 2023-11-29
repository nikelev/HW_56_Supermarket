package ait.supermarket.dao;

import ait.supermarket.model.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class SupermarketStreamImpl implements Supermarket {
    private Collection<Product> products = new ArrayList<>();

    @Override
    public Iterator<Product> iterator() {
        return products.iterator();
    }

    @Override
    public boolean addProduct(Product product) {
        if (product == null || products.contains(product)) {
            return false;
        }
        return products.add(product);
    }

    @Override
    public Product removeProduct(long barCode) {
        Product removed = findByBarCode(barCode);
        products.remove(removed);
        return removed;
    }

    @Override
    public Product findByBarCode(long barCode) {
//        for (Product product : products) {
//            if (barCode == product.getBarCode()) {
//                return product;
//            }
//        }
//        return null;

        return products.stream()
                .filter(b->barCode==b.getBarCode())
                .findFirst()
                .orElse(null);
    }

    @Override
    public Iterable<Product> findByCategory(String category) {
        return findByPredicate(p -> category.equalsIgnoreCase(p.getCategory()));
    }

    @Override
    public Iterable<Product> findByBrand(String brand) {
        return findByPredicate(p -> brand.equalsIgnoreCase(p.getBrand()));
    }

    @Override
    public Iterable<Product> findProductWithExpDate() {
        LocalDate current = LocalDate.now();
        return findByPredicate(p -> current.isAfter(p.getExpDate()));
    }

    public Iterable<Product> findByPredicate(Predicate<Product> predicate) {
//        List<Product> res = new ArrayList<>();
//        for (Product product : products) {
//            if (predicate.test(product)) {
//                res.add(product);
//            }
//        }
        return products.stream()
                .filter(predicate)
                .collect(Collectors.toList());


    }

    @Override
    public int skuQuantity() {
        return products.size();
    }
}