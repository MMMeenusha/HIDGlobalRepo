package Pages;

public class venues {

	private int id;
	private int lat;
	private int lon;
	private String category;
	private String name;
	private int created_on;
	private String geolocation_degrees;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCreated_on() {
		return created_on;
	}
	public void setCreated_on(int created_on) {
		this.created_on = created_on;
	}
	public String getGeolocation_degrees() {
		return geolocation_degrees;
	}
	public void setGeolocation_degrees(String geolocation_degrees) {
		this.geolocation_degrees = geolocation_degrees;
	}
	

}
