package com.xy124.r2dbc.service;

import com.xy124.r2dbc.model.Product;
import com.xy124.r2dbc.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;

    public Flux<Product> getAllProducts(){
        return this.productRepository.findAll();
    }

    public Mono<Product> getProductById(int productId){
        return this.productRepository.findById(productId);
    }

    public Mono<Product> createProduct(final Product product){
        return this.productRepository.save(product);
    }

    public Mono<Product> updateProduct(int productId, final Mono<Product> productMono){
        return this.productRepository.findById(productId)
                .flatMap(p -> productMono.map(u -> {
                    p.setDescription(u.getDescription());
                    p.setPrice(u.getPrice());
                    return p;
                }))
                .flatMap(p -> this.productRepository.save(p));
    }

    public Mono<Void> deleteProduct(final int id){
        return this.productRepository.deleteById(id);
    }
}
