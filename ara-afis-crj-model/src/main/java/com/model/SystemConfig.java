package com.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository("config")
public class SystemConfig {

	@Value("${tac.dbType}")
	private String dbType;

    @Value("${tac.busCodes}")
    private String busCodes;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getBusCodes() {
        return busCodes;
    }

    public void setBusCodes(String busCodes) {
        this.busCodes = busCodes;
    }
}
