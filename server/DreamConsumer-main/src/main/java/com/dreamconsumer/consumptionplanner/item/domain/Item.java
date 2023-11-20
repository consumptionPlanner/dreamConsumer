package com.dreamconsumer.consumptionplanner.item.domain;


import com.dreamconsumer.consumptionplanner.base.BaseEntity;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE item SET deleted = true and deletedAt = CURRENT_TIMESTAMP WHERE id = ? and deleted = false")
@Where(clause = "deleted = false")
public class Item extends BaseEntity{
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

    @OneToMany(mappedBy = "item", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItem> memberItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        System.out.println("*****prePersist********");
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


