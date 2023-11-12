package com.example.demo.user.domain;

import com.example.demo.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
@Table(name = "users")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String userName;

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

    @PrePersist
    public void prePersist() {
        this.tier = this.tier == null ? Tier.BRONZE : this.tier;
        this.score = this.score == null ? 0 : this.score;
        this.userStatus = this.userStatus == null ? UserStatus.USER_ACTIVE : this.userStatus;
        this.deleted = this.deleted == null ? false : this.deleted;
    }
}
