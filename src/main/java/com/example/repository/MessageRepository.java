package com.example.repository;
import java.util.List;

import com.example.entity.Message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
    @Query(value = "SELECT * FROM message WHERE posted_by = ?1", nativeQuery = true)
    List<Message> findMessagesByPostedBy(int id);
    

    @Modifying
    @Transactional
    @Query("UPDATE Message SET message_text = :message WHERE message_id = :message_id")
    int updateText(@Param("message") String message, @Param("message_id") int message_id);
}
