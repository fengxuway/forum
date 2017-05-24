package pagination;

import java.util.*;

public class PageBean {

	private List list; // 要返回的某一页的记录列表

	private int allRow; // 总记录数
	private int totalPage; // 总页数
	private int currentPage; // 当前页
	private int pageSize; // 每页记录数

	private boolean firstPage; // 是否为第一页
	private boolean lastPage; // 是否为最后一页
	private boolean hasPreviousPage; // 是否有前一页
	private boolean hasNextPage; // 是否有下一页

	public String toString(){
		return "PageBean:\n" +
				"当前页: "+currentPage+
				"\n是否第一页: "+firstPage+
				"\n是否有下页: "+hasNextPage+
				"\n是否有上页: "+hasPreviousPage+
				"\n是否最后页: "+lastPage;
	}
	
	public Object getList(int index){
		return list.get(index);
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getAllRow() {
		return allRow;
	}

	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** */
	/**
	 * 初始化分页信息
	 */
	public void init() {
		this.firstPage = currentPage == 1;// 如是当前页是第1页
		this.lastPage = currentPage == totalPage;// 如果当前页是最后一页
		this.hasPreviousPage = currentPage != 1;// 只要当前页不是第1页
		this.hasNextPage = currentPage != totalPage;// 只要当前页不是最后1页
	}

	/** */
	/**
	 * 以下判断页的信息,只需getter方法(is方法)即可
	 * 
	 * @return
	 */

/*	public boolean isFirstPage() {
		return isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage; 
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}*/
	

	/** */
	/**
	 * 计算总页数,静态方法,供外部直接通过类名调用
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param allRow
	 *            总记录数
	 * @return 总页数
	 */
	public static int countTotalPage(final int pageSize, final int allRow) {
		int totalPage = allRow % pageSize == 0 ? allRow / pageSize : allRow
				/ pageSize + 1;
		return totalPage;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	/**
	 * 计算当前页开始记录
	 * 
	 * @param pageSize
	 *            每页记录数
	 * @param currentPage
	 *            当前第几页
	 * @return 当前页开始记录号
	 */
	public static int countOffset(final int pageSize, final int currentPage) {
		final int offset = pageSize * (currentPage - 1);
		return offset;
	}

	/** */
	/**
	 * 计算当前页,若为0或者请求的URL中没有"?page=",则用1代替
	 * 
	 * @param page
	 *            传入的参数(可能为空,即0,则返回1)
	 * @return 当前页
	 */
	public static int countCurrentPage(int page,int totalPage) {
		int curPage = (page == 0 ? 1 : page);
		curPage = (curPage>totalPage? totalPage : curPage);
		return curPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.firstPage = isFirstPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.lastPage = isLastPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
}
