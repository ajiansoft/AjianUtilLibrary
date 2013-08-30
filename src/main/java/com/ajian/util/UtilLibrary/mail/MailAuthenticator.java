package com.ajian.util.UtilLibrary.mail;

import javax.mail.PasswordAuthentication;

/**
 * @邮件用户验证类
 * @author zhangxin
 * @since 2012.12.27
 */
public class MailAuthenticator extends javax.mail.Authenticator {
    //邮箱名
	String username;
	//邮箱密码
	String password;

	public MailAuthenticator() {
	}

	public MailAuthenticator(String username, String password) {
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
}
