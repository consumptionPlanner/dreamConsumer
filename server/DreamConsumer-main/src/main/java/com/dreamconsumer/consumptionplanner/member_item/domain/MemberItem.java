package com.dreamconsumer.consumptionplanner.member_item.domain;

import com.dreamconsumer.consumptionplanner.base.BaseEntity;
import com.dreamconsumer.consumptionplanner.item.domain.Item;
import com.dreamconsumer.consumptionplanner.member.domain.Member;
import com.dreamconsumer.consumptionplanner.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member_item SET deleted = true and deletedAt = CURRENT_TIMESTAMP  WHERE id = ?")
@Where(clause = "deleted = false")
public class MemberItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name="item_id", referencedColumnName = "id", nullable = false)
    private Item item;

    @Column(name="total_money", nullable = false)
    private BigDecimal totalMoney;

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

    @OneToMany(mappedBy = "memberItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tag> tags = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.bookmark = this.bookmark == null ? false : this.bookmark;
    }

}
