package com.dreamconsumer.consumptionplanner.member_item.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberItemRepository extends JpaRepository<MemberItem, Long> {

}
