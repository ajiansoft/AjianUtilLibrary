package com.ajian.util.UtilLibrary.mail;

public class mailTest {

	public static void main(String[] args) {

		MailSenderInfo msi = new MailSenderInfo();
		msi.setMailServerHost("smtp.qq.com");
		msi.setMailServerPort("25");
		msi.setValidate(true);
		msi.setUserName("511098425@qq.com");
		msi.setPassword("zhangxin");// 您的邮箱密码
		msi.setFromAddress("511098425@qq.com");
		msi.setToAddress("759447583@qq.com");
		msi.setSubject("设置邮箱标题");
		msi.setContent("请点击以下链接修改密码http://192.168.1.113:8080/HelpSystem/pages/changePsw.jsp");
		msi.setAttachFileNames("H://news_服务器.sql");
		// 这个类主要来发送邮件
		mainSendMail sms = new mainSendMail();
		// sms.sendTextMail(msi);//发送文体格式
		sms.sendMail(msi);// 发送html格式
	}

}
