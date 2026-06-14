package com.genuine.lifelog.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteResponse {
	
	private Long id;
	private String title;	
	private String content;
	private List<String> tags;
	
}
