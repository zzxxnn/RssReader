package model;

public class Channel {
	private String name;
	private String filePath;
	private String url;
	public Channel(){
		this.name="";
		this.filePath = "";
		this.url = "";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return this.name;
	}
	
}
