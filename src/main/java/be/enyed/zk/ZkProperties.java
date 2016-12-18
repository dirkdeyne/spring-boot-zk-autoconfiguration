package be.enyed.zk;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="zkoss")
public class ZkProperties {
	
	private String baseFolder = "/WEB-INF/pages/";
	private String extension = ".zul";
	
	public String getBaseFolder() {
		return baseFolder;
	}
	
	public void setBaseFolder(String baseFolder) {
		this.baseFolder = baseFolder;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public void setExtension(String extension) {
		this.extension = extension;
	}

	
}
