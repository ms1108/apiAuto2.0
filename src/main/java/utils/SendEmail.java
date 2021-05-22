package utils;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

public class SendEmail {
    public static boolean sendEmail(String recipients, String subject, String content, String fileStr) {
        //使用SSL，企业邮箱必需！
        MailSSLSocketFactory mailSSLSocketFactory = null;
        //创建一个配置文件并保存
        Properties props = new Properties();

        props.setProperty("mail.host", "smtp.qq.com");

        props.setProperty("mail.transport.protocol", "smtp");

        props.setProperty("mail.smtp.auth", "true");


        try {
            mailSSLSocketFactory = new MailSSLSocketFactory();
            mailSSLSocketFactory.setTrustAllHosts(true);
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);

            Session session = Session.getDefaultInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("929262660@qq.com", "tgljydgkkmoybdcf");
                }
            });
            MimeMessage mimeMessage = new MimeMessage(session);

            //发件人
            mimeMessage.setFrom(new InternetAddress("929262660@qq.com"));

            //收件人
            Address[] toAddress;
            if (recipients.contains(";")) {
                String[] tos = recipients.split(";");
                int num = tos.length;
                toAddress = new Address[num];
                for (int i = 0; i < num; i++) {
                    toAddress[i] = new InternetAddress(tos[i]);
                }
            } else {
                toAddress = new Address[1];
                toAddress[0] = new InternetAddress(recipients);
            }
            mimeMessage.addRecipients(Message.RecipientType.TO, toAddress);
            //主题
            mimeMessage.setSubject(subject);
            //时间
            mimeMessage.setSentDate(new Date());
            //容器类，可以包含多个MimeBodyPart对象
            Multipart mp = new MimeMultipart();

            //MimeBodyPart可以包装文本，图片，附件
            MimeBodyPart body = new MimeBodyPart();
            //HTML正文
            body.setContent(content, "text/html; charset=UTF-8");
            mp.addBodyPart(body);

            //添加图片&附件
            if (StringUtil.isNotEmpty(fileStr)) {
                body = new MimeBodyPart();
                body.attachFile(fileStr);
                mp.addBodyPart(body);
            }

            //设置邮件内容
            mimeMessage.setContent(mp);
            //仅仅发送文本
            //mimeMessage.setText(content);
            mimeMessage.saveChanges();
            Transport.send(mimeMessage);
            // 发送成功
            return true;

        } catch (Exception e1) {
            return false;
        }

    }

    //public static void main(String[] args) {
    //    sendEmail("929262660@qq.com", "这是主题", "这是内容", "test-output/index.html");
    //}
}
