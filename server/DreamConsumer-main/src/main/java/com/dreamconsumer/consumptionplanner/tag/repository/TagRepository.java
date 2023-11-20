package com.dreamconsumer.consumptionplanner.tag.repository;

import com.dreamconsumer.consumptionplanner.tag.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
