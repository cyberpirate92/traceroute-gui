class MapCoordinates
{
	private String latitude,longitude;
	
	public MapCoordinates(String latitude,String longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public MapCoordinates(MapCoordinates mcObj)
	{
		this.latitude  = mcObj.getLatitude();
		this.longitude = mcObj.getLongitude();
	}
	public String getLatitude()
	{
		return this.latitude;
	}
	public String getLongitude()
	{
		return this.longitude;
	}
}