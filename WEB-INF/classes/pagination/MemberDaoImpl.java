package pagination;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDaoImpl<T> extends HibernateDaoSupport implements MemberDao {
	
	/**
	 * 分页查询
	 * 
	 * @param currentPage
	 *            当前第几页
	 * @param pageSize
	 *            每页大小
	 * @return 封闭了分页信息(包括记录集list)的Bean
	 */
	@SuppressWarnings("unchecked")
	public PageBean findForPage(final String hql,final int pageSize, int pageNum) {
		int totalRow = getAllRowCount(hql); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, totalRow); // 总页数
		int currentPage = PageBean.countCurrentPage(pageNum,totalPage);//当前页码
		final int offset = PageBean.countOffset(pageSize, currentPage); // 当前页开始记录号
//		List list = findForPage(hql, offset, pageSize); 
		
		// "一页"的记录
		List list = getHibernateTemplate().executeFind(new HibernateCallback<List<T>>() {
			public List<T> doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql)
						.setFirstResult(offset)
						.setMaxResults(pageSize)
						.list();
			}
		});
		// 把分页信息保存到Bean中
		return initPageBean(pageSize, currentPage, totalRow, totalPage, list);
	}
	
	
	
	public PageBean findForPage(final String hql,final Object value, final int pageSize, int pageNum) {
		int totalRow = getAllRowCount(hql,value);
		int totalPage = PageBean.countTotalPage(pageSize, totalRow); // 总页数
		int currentPage = PageBean.countCurrentPage(pageNum,totalPage);//当前页码
		final int offset = PageBean.countOffset(pageSize, currentPage); // 当前页开始记录号
		
		
		// "一页"的记录
		List list = getHibernateTemplate().executeFind(new HibernateCallback() {

			@Override
			public Object doInHibernate(Session session) throws HibernateException,
					SQLException {
				List list = session.createQuery(hql)
						.setParameter(0, value)
						.setFirstResult(offset)
						.setMaxResults(pageSize)
						.list();
				return list;
			}
			
		});
		// 把分页信息保存到Bean中
		return initPageBean(pageSize, currentPage, totalRow, totalPage, list);
	}
	
	
	public PageBean findForPage(final String hql,final Object[] values, final int pageSize, int pageNum) {
		int totalRow = getAllRowCount(hql,values); // 总记录数
		int totalPage = PageBean.countTotalPage(pageSize, totalRow); // 总页数
		int currentPage = PageBean.countCurrentPage(pageNum,totalPage);//当前页码
		final int offset = PageBean.countOffset(pageSize, currentPage); // 当前页开始记录号
		
		// "一页"的记录
		List list =  getHT().executeFind(new HibernateCallback<List<T>>() {

			@Override
			public List<T> doInHibernate(Session session) throws HibernateException,
					SQLException {
				Query query = session.createQuery(hql);
				for(int i=0; i<values.length; i++){
					query.setParameter(i, values[i]);
				}
				return query.setFirstResult(offset).setMaxResults(pageSize).list();
			}
		});
		
		// 把分页信息保存到Bean中
		return initPageBean(pageSize, currentPage, totalRow, totalPage, list);
	}
	
	/*@SuppressWarnings("unchecked")
	/**
	 * 私有方法, 用于根据已知的分页数据创建和初始化并返回PageBean类对象, 
	 * @param pageSize 每页记录数
	 * @param currentPage 当前页码
	 * @param totalRow 总记录数
	 * @param totalPage 总页数
	 * @param list 当前页的数据
	 * @return new PageBean()对象
	 */
	private PageBean initPageBean(int pageSize, int currentPage, int totalRow, int totalPage, List list){
		PageBean pageBean = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(totalRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
		
	}
	/**
	 * 查询所有记录数
	 * 
	 * @return 总记录数
	 */
	public int getAllRowCount(String hql) {
		return getHT().find(hql).size();
	}
	public int getAllRowCount(String hql, Object value){
		return getHT().find(hql, value).size();
	}
	public int getAllRowCount(String hql, Object...values){
		return getHT().find(hql, values).size();
	}
	
	public HibernateTemplate getHT(){
		return getHibernateTemplate();
	}
	
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
}
