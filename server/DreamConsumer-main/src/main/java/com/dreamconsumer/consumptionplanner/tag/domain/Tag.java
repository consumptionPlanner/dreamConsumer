package com.dreamconsumer.consumptionplanner.tag.domain;

import com.dreamconsumer.consumptionplanner.base.BaseEntity;
import com.dreamconsumer.consumptionplanner.member_item.domain.MemberItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE tag SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long id;

    @Column(name="contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_item_id")
    private MemberItem memberItem;
}
