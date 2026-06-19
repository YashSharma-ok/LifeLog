package com.genuine.lifelog.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LifeLogResponse extends PagedResponse {

	private String status;
	private String message;
	private List<String> errorList;
	private Object data;
	
}
