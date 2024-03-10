package com.product.repository;

import com.product.modal.ProductModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductModal, Long>{

	List<ProductModal> findByIsDeletedOrderByProductIdDesc(int isDeleted);

	ProductModal findByProductIdAndIsDeletedOrderByProductIdDesc(UUID productId, int isDeleted);

	@Modifying
	@Transactional
	@Query(value = "update product set is_deleted=1 where product_id = :productId",nativeQuery = true)
	int deleteProduct(@Param("productId") UUID productId);

	@Modifying
	@Transactional
	@Query(value = "update product set inventory=(inventory - :inventory) where product_id = :productId",nativeQuery = true)
	int updateProductInventory(@Param("productId") UUID productId,@Param("inventory") Double inventory);

}
