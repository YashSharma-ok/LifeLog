package com.genuine.lifelog.dto.request;

import java.util.List;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchNoteRequest {
	
	@Size(max = 5000, message = "Searched text can be at most 5000 characters")
	private String text; 
	
	@Size(max = 255, message = "Title can be at most 255 characters")
    private String title;
	
	@Size(max = 5000, message = "Content can be at most 5000 characters")
    private String content;
    
	private List<String> tags;	
	
}
