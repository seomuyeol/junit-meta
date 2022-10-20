package com.junitP.junit.web.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CMRespDto<T> { //commonResponseDto
	private Integer code; // 1 O,-1 X
	private String msg; // errorMessage 成功、失敗
	private T body; //generic
	
	@Builder //値入れるとき
	public CMRespDto(Integer code, String msg, T body) {
		super();
		this.code = code;
		this.msg = msg;
		this.body = body;
	}
}
