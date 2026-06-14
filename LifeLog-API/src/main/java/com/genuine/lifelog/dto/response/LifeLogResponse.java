package com.genuine.lifelog.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LifeLogResponse {

	private String status;
	private String message;
	private List<String> errorList;
	private Object data;
	
}
