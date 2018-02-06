package com.memorynotfound;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {

	@Value("${application.DBURL}")
    private String DBURL;
    @Value("${application.driver}")
    private String driver;
    
    @Value("${application.username}")
    private String username;
    
    @Value("${application.password}")
    private String password;
    public String getDBURL() {
		return DBURL;
	}

	public String getDriver() {
		return driver;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}

	

    @Override
    public String toString() {
        return "Config{" +
                "DBURL='" + DBURL + '\'' +
                ", Driver='" + driver + '\'' +
                 ", Username='" + username + '\'' +
                  ", Password='" + password + '\'' +
                '}';
    }
}
