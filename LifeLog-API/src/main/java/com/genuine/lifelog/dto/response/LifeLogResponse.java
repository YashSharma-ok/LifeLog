package com.genuine.lifelog.dto.response;

import java.util.List;

import org.springframework.data.domain.Page;

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

	public void success(String message) {
		this.message = message;
		this.data = null;
		this.pageInfo = null;
	}
	
	public void success(String message, Object data) {
		this.message = message;
		this.data = data;
		this.pageInfo = null;
	}
	
	public void success(String message, Page<?> page) {
		this.message = message;
		this.data = page.getContent();
		this.setPagenation(page);
	}
}
