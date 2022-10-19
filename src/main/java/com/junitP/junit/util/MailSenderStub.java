package com.junitP.junit.util;

import org.springframework.stereotype.Component;

@Component //IoCコンテナー
public class MailSenderStub implements MailSender {
	
	@Override
	public boolean send() {
		return true;
	}

}
