package com.mkyong.web.model;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.annotation.JsonView;
import com.mkyong.web.jsonview.Views;

public class AjaxResponseBody {

	@JsonView(Views.Public.class)
	String msg;

	@JsonView(Views.Public.class)
	String code;
	@JsonView(Views.Public.class)
	List<User> result;
	@JsonView(Views.Public.class)
	List<ResponseEntity> responseentity;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<User> getResult() {
		return result;
	}

	public void setResult(List<User> result) {
		this.result = result;
	}

	public List<ResponseEntity> getResponseentity() {
		return responseentity;
	}

	public void setResponseentity(List<ResponseEntity> responseentity) {
		this.responseentity = responseentity;
	}

	@Override
	public String toString() {
		return "AjaxResponseBody [msg=" + msg + ", code=" + code + ", result=" + result + ", responseentity="
				+ responseentity + "]";
	}

}
