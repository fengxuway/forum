package entity;

import java.io.Serializable;
import java.util.Date;

public class Weather  implements Serializable {
	private static final long serialVersionUID = 4751972450736457155L;
	
	private int id;
	private String city = "太原";
	private String degree;// 温度
	private String climate;// 气候情况，即阴晴雨雪等
	private String image;// 当前天气对应的图片
	private String wind;// 风力
	private String humidity; // 湿度%
	private Date time;// 获取天气的时间
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * 创建Weather天气对象
	 * @param degree 温度
	 * @param climate 气候情况，即阴晴雨雪等
	 * @param image 当前天气对应的图片
	 * @param wind 风力
	 * @param humidity 湿度%
	 * @param time 获取天气的时间
	 */
	public Weather(String degree, String climate, String image, String wind,
			String humidity,Date time) {
		this.degree = degree;
		this.climate = climate;
		this.image = image;
		this.wind = wind;
		this.humidity = humidity;
		this.time = time;
	}
	public Weather() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getClimate() {
		return climate;
	}
	public void setClimate(String climate) {
		this.climate = climate;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getHumidity() {
		return humidity;
	}
	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
	

}
