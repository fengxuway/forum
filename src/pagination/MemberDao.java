package pagination;
import java.util.*;
@SuppressWarnings("rawtypes")
public interface MemberDao{

	/**
	 * 分页查询
	 * @param hql 查询的条件
	 * @param pageSize 每页记录数
	 * @param pageNum 页码，从1开始
	 * @return
	 */
	public PageBean findForPage(final String hql,final int pageSize,final int pageNum);
	
	/**
	 * 分页查询
	 * @param hql 查询的条件
	 * @param value hql中条件值
	 * @param pageSize 每页记录数
	 * @param pageNum 页码，从1开始
	 * @return
	 */
	public PageBean findForPage(final String hql,Object value,final int pageSize,final int pageNum);
	
	/**
	 * 分页查询
	 * @param hql 查询的条件
	 * @param value hql中条件值
	 * @param pageSize 每页记录数
	 * @param pageNum 页码，从1开始
	 * @return
	 */
	public PageBean findForPage(final String hql,Object[] values,final int pageSize,final int pageNum);
	
	/**
	 * 查询所有记录数
	 * @param hql 查询的条件
	 * @return 总记录数
	 */
	public int getAllRowCount(String hql);
	public int getAllRowCount(String hql, Object value);
	public int getAllRowCount(String hql, Object...value);
}
