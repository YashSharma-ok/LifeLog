package com.genuine.lifelog.entity;


import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Note {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 255)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;	
	
	private Boolean isTrashed;
	
	private Long userId;
	
	
	public Note(String title, String content, List<Tag> tags) {
		this.title = title;
		this.content = content;
		this.tags = tags;
	}

	@Column(name = "created_dt")
	@CreationTimestamp
	private LocalDateTime createdDate;
	
	@Column(name = "updated_dt")
	@UpdateTimestamp
	private LocalDateTime updatedDate;
	
	@ManyToMany
	@JoinTable(
			name = "note_tag",
			joinColumns = @JoinColumn(name = "note_id"),
			inverseJoinColumns = @JoinColumn(name = "tag_id")
	)
	
	private List<Tag> tags;	
}
