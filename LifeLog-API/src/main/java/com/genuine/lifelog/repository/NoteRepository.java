package com.genuine.lifelog.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.genuine.lifelog.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	@Query("SELECT n FROM Note n JOIN FETCH n.tags")
	Page<Note> findAllWithTags(Pageable pageable);
	Page<Note> findByIsTrashedNot(Boolean isTrash, Pageable pageable);
	Page<Note> findByIsTrashed(Boolean isTrash, Pageable pageable);
	
	@Query("SELECT DISTINCT n FROM Note n " +
	           "LEFT JOIN n.tags t " +
	           "WHERE (LOWER(n.title) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	           "OR LOWER(n.content) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	           "OR LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword, '%')))" +
	           "AND n.isTrashed = false")
	Page<Note> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
