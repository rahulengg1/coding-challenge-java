package com.gfg.product.repository;

import com.gfg.product.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    List<Seller> findByUuid(@Param("uuid") String uuid);
    
    @Query(value = "select seller.* from seller inner join " + 
    		"(select fk_seller from product group by fk_seller order by count(*) desc limit 10) product_data " + 
    		"on seller.id_seller=product_data.fk_seller",
    		nativeQuery = true)
    List<Seller> findTopTen();
    
}
