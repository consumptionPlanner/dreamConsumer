package com.example.demo.item.domain;


import com.example.demo.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE item SET deleted = true and deletedAt = CURRENT_TIMESTAMP WHERE id = ? and deleted = false")
@Where(clause = "deleted = false")
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "total_money", nullable = false)
    private BigDecimal totalMoney;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "item_url")
    private String itemUrl;

    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @Column(name = "group_purchase", nullable = false)
    private Boolean groupPurchase = false;

    @Column(name = "auto_update", nullable = false)
    private Boolean autoUpdate;

    @PrePersist
    public void prePersist() {
        this.completed = this.completed == null ? false : this.completed;
        this.deleted = this.deleted == null ? false : this.deleted;
        this.totalMoney = this.totalMoney == null ? BigDecimal.valueOf(0) : this.totalMoney;
    }

//    public static ItemResponseDto EntityToItemResponse(Item item) {
//        return ItemResponseDto.builder().
//                id(item.getId())
//                .userId(item.getUserId())
//                .itemName(item.getItemName())
//                .price(item.getPrice())
//                .currentMoney(item.getCurrentMoney())
//                .imagePath(item.getImagePath())
//                .groupPurchase(item.getGroupPurchase())
//                .moneyAutoUpdate(item.getMoneyAutoUpdate())
//                .autoUpdateMoneyAmount(item.getAutoUpdateMoneyAmount())
//                .tag(item.getTag().element)
//                .itemUri(item.getItemUri()).build();
//    }


}


