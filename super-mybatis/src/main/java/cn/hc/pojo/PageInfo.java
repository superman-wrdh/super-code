package cn.hc.pojo;

public class PageInfo {
	public static final String ORDER_ASC = "asc";
	public static final String ORDER_DESC = "desc";
	
	private int pageSize;
	private long totalRecords;
	private int currentPage;
	private String orderColumn;
	private String orderDirection = ORDER_ASC;
	
	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public PageInfo() {
	}
	
	public PageInfo(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
		if(pageSize>0) {
			int pages = (int)(totalRecords % pageSize > 0? totalRecords / pageSize + 1: totalRecords / pageSize);
			if(currentPage>=pages)
				currentPage = pages-1;
		}
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage<0?0:currentPage;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}
}
