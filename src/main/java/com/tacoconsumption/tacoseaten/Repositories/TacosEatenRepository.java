package com.tacoconsumption.tacoseaten.Repositories;

import com.tacoconsumption.tacoseaten.Entities.TacoEaten;
import com.tacoconsumption.tacoseaten.TacosEatenList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TacosEatenRepository extends JpaRepository<TacoEaten, Long> {
    List<TacoEaten> findAllByOrderByTeamAssociation();
}
