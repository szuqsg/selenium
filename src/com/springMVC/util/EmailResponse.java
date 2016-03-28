package com.springMVC.util;

import java.io.Serializable;
import java.util.Date;

public class EmailResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String lastServerResponse; // last SMTP response

	private Integer lastReturnCode; // last SMTP return code
	
	private boolean reportSuccess;	
	
	private Date serverRespTimestamp;

	public String getLastServerResponse() {
		return lastServerResponse;
	}

	public void setLastServerResponse(String lastServerResponse) {
		this.lastServerResponse = lastServerResponse;
	}

	public Integer getLastReturnCode() {
		return lastReturnCode;
	}

	public void setLastReturnCode(Integer lastReturnCode) {
		this.lastReturnCode = lastReturnCode;
	}

	public boolean isReportSuccess() {
		return reportSuccess;
	}

	public void setReportSuccess(boolean reportSuccess) {
		this.reportSuccess = reportSuccess;
	}

	public Date getServerRespTimestamp() {
		return serverRespTimestamp;
	}

	public void setServerRespTimestamp(Date serverRespTimestamp) {
		this.serverRespTimestamp = serverRespTimestamp;
	}
	
	
}
