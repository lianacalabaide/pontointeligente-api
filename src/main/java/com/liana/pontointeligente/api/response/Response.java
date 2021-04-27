package com.liana.pontointeligente.api.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Response<T> {
	private T data;
	private List<String> errors;
	
	public Response() {
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public List<String> getErrors() {
		if (Objects.isNull(this.errors))
			this.errors = new ArrayList<String>();
		return this.errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
