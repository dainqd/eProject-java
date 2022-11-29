package com.example.eproject.service;

import com.example.eproject.entity.User;
import com.example.eproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    SpringTemplateEngine templateEngine;
    //  @Autowired NoticeRegisterRepository noticeRegisterRepository;
    @Autowired
    UserRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${config.mail.host}")
    private String host;
    @Value("${config.mail.port}")
    private String port;
    @Value("${config.mail.username}")
    private String email;
    @Value("${config.mail.password}")
    private String password;

    public void sendMessageUsingThymeleafTemplate(String to, String subject, String template,
                                                  Map<String, Object> templateModel) {

        Context thymeleafContext = new Context();
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/email/");
        templateResolver.setSuffix(".html");

        System.out.println("url: " + templateResolver.getPrefix());

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
        thymeleafContext.setVariables(templateModel);
        String htmlBody = templateEngine.process(template, thymeleafContext);
        sendEmail(to, subject, htmlBody);
    }

    public void sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
//        MimeMessage message = emailSender.createMimeMessage();
        Message message = new MimeMessage(session);
//        MimeMessageHelper helper;
//        try {
//            helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setFrom("HelloWorld <support@me.com>");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(body);
//            message.setContent(body, "text/html; charset=UTF-8");
//            emailSender.send(message);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
        try {
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(to)});

            message.setFrom(new InternetAddress(email));
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=UTF-8");
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void forgot(String email, String link) {
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("email", email);
        templateModel.put("link", link);
        String subject = "Xác thực làm mới mật khẩu!";
        sendMessageUsingThymeleafTemplate(email, subject, "userForgotPassEmail", templateModel);
    }

    public void completedRegisterMail(User account, Map<String, Object> templateModel) {
        String subject = "Tài khoản được kích hoạt thành công!";
        sendMessageUsingThymeleafTemplate(account.getEmail(), subject, "userActivateEmail", templateModel);
    }

    public void userRegisterMail(String email, String code) {
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("email", email);
        System.out.println(email);
        templateModel.put("code", code);
        System.out.println(code);
        String subject = "Đăng ký tài khoản!";
        sendMessageUsingThymeleafTemplate(email, subject, "userRegisterEmail", templateModel);
    }

    public void userCreateByAdminMail(String email, String password) {
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("email", email);
        templateModel.put("password", password);
        String subject = "Tạo mới tài khoản!";
        sendMessageUsingThymeleafTemplate(email, subject, "userCreateByAdminEmail", templateModel);
    }

    public void userChangeMail(String email, String code) {
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("email", email);
        templateModel.put("code", code);
        String subject = "Thay đổi email!";
        sendMessageUsingThymeleafTemplate(email, subject, "userChangeEmail", templateModel);
    }

    public void userShowMail(String email, String code) {
        Map<String, Object> templateModel = new HashMap<String, Object>();
        templateModel.put("email", email);
        templateModel.put("code", code);
        String subject = "Xác nhận địa chỉ email";
        sendMessageUsingThymeleafTemplate(email, subject, "userShowEmail", templateModel);
    }
}
