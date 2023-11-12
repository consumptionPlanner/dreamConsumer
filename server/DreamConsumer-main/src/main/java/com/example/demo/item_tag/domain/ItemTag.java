package com.example.demo.item_tag.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "item_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE item_tag SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class ItemTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="item_id", nullable = false)
    private Long itemId;

    @Column(name="tag_id", nullable = false)
    private Long tagId;
}
