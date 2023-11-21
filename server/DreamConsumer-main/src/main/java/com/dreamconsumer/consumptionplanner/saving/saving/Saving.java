package com.dreamconsumer.consumptionplanner.saving.saving;

import com.dreamconsumer.consumptionplanner.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "saving")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE saving SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Saving extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="item_id", nullable = false)
    private Long itemId;

    @Column(name="member_id", nullable = false)
    private Long memberId;

    @Column(name="amount", nullable = false)
    private BigDecimal amount;
}
