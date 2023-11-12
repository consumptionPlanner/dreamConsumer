package com.example.demo.user_item.domain;

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
@Table(name = "user_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user_item SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class UserItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="item_id", nullable = false)
    private Long itemId;

    @Column(name="user_id", nullable = false)
    private Long userId;

    @Column(name="saving_money", nullable = false)
    private BigDecimal savingMoney;

    @Column(name="unit_amount", nullable = false)
    private BigDecimal unitAmount;

    Boolean autoUpdate;
    @Enumerated(EnumType.STRING)
    @Column(name = "cycle", nullable = false)
    private Cycle cycle;

    private Boolean bookmark;

    public enum Cycle {
        DAILY, WEEKLY, MONTHLY
    }

    @PrePersist
    public void prePersist() {
        this.bookmark = this.bookmark == null ? false : this.bookmark;
    }

}
