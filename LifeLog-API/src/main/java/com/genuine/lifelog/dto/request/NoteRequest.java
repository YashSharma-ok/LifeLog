package com.genuine.lifelog.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteRequest {
	

	private Long id;
	
	@Size(max = 255, message = "Title must be at most 255 characters")
	@NotBlank(message = "Title cannot be blank")
    private String title;

    @Size(max = 5000, message = "Content must be at most 5000 characters")
	@NotBlank(message = "Content cannot be blank")
    private String content;
    
	private List<String> tags;	
	
}
