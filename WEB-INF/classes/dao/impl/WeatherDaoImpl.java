package dao.impl;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;
import dao.WeatherDao;
import entity.Weather;
@Repository("weatherDao")
public class WeatherDaoImpl extends HibernateBaseDao<Weather> implements WeatherDao{

	@Override
	public Weather findByCityName(String city) {
		@SuppressWarnings("unchecked")
		List<Weather> list = getHT().find("from Weather where city=?",city);
		if(list != null && list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}



} 
 