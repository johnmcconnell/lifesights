package com.lifesights.webapp;

import java.util.List;
import java.util.ArrayList;

public class TransactionResponse {
	private Boolean success;
	private List<?> errors;
	private List<DataObject> result;

	public TransactionResponse(List<?> errors, boolean dummy) {
		this.success = false;
		this.errors = errors;
	}
	
	public TransactionResponse(String error) {
		this.success = false;
		List<String> errors = new ArrayList<String>(1);
		errors.add(error);
		this.errors = errors;
	}
	
	public TransactionResponse(DataObject o) {
		List<DataObject> os = new ArrayList<DataObject>(1);
		os.add(o);
		this.result = os;
		this.success = true;
		this.errors = null;
	}
	public TransactionResponse(List<DataObject> os) {
		this.result = os;
		this.success = true;
		this.errors = null;
	}


	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public List<?> getErrors() {
		return errors;
	}

	public void setErrors(List<?> errors) {
		this.errors = errors;
	}

	public List<DataObject> getResult() {
		return result;
	}

	public void setResult(List<DataObject> result) {
		this.result = result;
	}

}
