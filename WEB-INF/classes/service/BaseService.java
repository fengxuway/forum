package service;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
	T get(Serializable id);
	List<T> getAll();
	Serializable save(T o);
	void delete(T o);
	void update(T o);
	void saveOrUpdate(T o);
}
 