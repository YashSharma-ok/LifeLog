package com.genuine.lifelog.dto.response;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PageInfo {
	int totalPages;
	int currentPage;
	long totalRecords;
	int currentRecords;
}

@Getter
public class PagedResponse {

	@JsonInclude(JsonInclude.Include.NON_NULL)
	protected PageInfo pageInfo;
	
	public PagedResponse() {
		pageInfo = new PageInfo();
	}
	
	public void setPagenation(Page<?> page) {
		if(pageInfo == null) pageInfo = new PageInfo();
		this.pageInfo.setCurrentPage(page.getNumber() + 1);
		this.pageInfo.setTotalPages(page.getTotalPages());
		this.pageInfo.setCurrentRecords(page.getNumberOfElements());
		this.pageInfo.setTotalRecords(page.getTotalElements());
	}
	
	public void noPagenation() {
		pageInfo = null;
	}
	
	
}
