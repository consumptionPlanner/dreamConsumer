package com.dreamconsumer.consumptionplanner.item.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    Page<Item> findAll(Pageable pageable);

//    @Query(value = "select * from Item where deleted = true and (:nowTime - deleted_at) >= 4L * 60 * 1000", nativeQuery = true)
    @Query(value = "SELECT * FROM item WHERE deleted = true AND TIMESTAMPDIFF(MINUTE, deleted_at, CURRENT_TIMESTAMP()) >= 4", nativeQuery = true)
    List<Item> findItemByDeletedIsTrue(@Param("nowTime") Long nowTime);
}