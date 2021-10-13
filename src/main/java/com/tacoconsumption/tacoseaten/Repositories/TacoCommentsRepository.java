package com.tacoconsumption.tacoseaten.Repositories;

import com.tacoconsumption.tacoseaten.Entities.TacoComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface TacoCommentsRepository extends JpaRepository<TacoComment, Long> {
    List<TacoComment> findFirst20ByOrderByCommentTimeStampDesc();
}
