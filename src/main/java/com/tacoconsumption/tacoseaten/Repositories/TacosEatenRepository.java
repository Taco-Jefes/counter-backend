package com.tacoconsumption.tacoseaten.Repositories;

import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TacosEatenRepository extends JpaRepository<TacoEaten, Long> {
}
