package com.dreamconsumer.consumptionplanner.member.domain;

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


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "member")
@Table(name = "member")
@SQLDelete(sql = "UPDATE member SET deleted = true and deletedAt = CURRENT_TIMESTAMP WHERE id = ?")
@Where(clause = "deleted = false")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long memberId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name ="tier", nullable = false)
    private Tier tier;

    public enum Tier {
        BRONZE, SILVER, GOLD, DIAMOND, EMERALD, RUBY
    }

    @Column(name="score", nullable = false)
    private Integer score;

    @Column(name = "age")
    private Integer age;

    @Column(name = "job")
    private String job;

    @Column(name = "email_acceptance", nullable = false)
    private Boolean emailAcceptance;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "user_status", nullable = false)
    private UserStatus userStatus;

    public enum UserStatus {
        USER_ACTIVE("활동 상태"),
        USER_SLEEP("휴면 상태"),
        USER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        UserStatus(String status) {
            this.status = status;
        }
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "roles", nullable = false)
    private List<String> roles = new ArrayList<>();

    public enum UserRole {
        ROLE_USER,
        ROLE_ADMIN;
    }

    @Column(name = "deleted", nullable = false)
    private Boolean deleted;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberItem> memberItems = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.tier = this.tier == null ? Tier.BRONZE : this.tier;
        this.score = this.score == null ? 0 : this.score;
        this.userStatus = this.userStatus == null ? UserStatus.USER_ACTIVE : this.userStatus;
        this.deleted = this.deleted == null ? false : this.deleted;
    }
}
