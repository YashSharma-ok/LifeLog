package com.genuine.lifelog.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genuine.lifelog.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findByNameIn(List<String> tagNames);
}
