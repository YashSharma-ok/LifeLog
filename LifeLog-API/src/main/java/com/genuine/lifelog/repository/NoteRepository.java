package com.genuine.lifelog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.genuine.lifelog.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
	@Query("SELECT n FROM Note n JOIN FETCH n.tags")
	List<Note> findAllWithTags();
	List<Note> findByIsTrashedNot(Boolean isTrash);
	List<Note> findByIsTrashed(Boolean string);
}
