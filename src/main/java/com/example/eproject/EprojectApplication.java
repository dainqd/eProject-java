package com.example.eproject;

import com.example.eproject.entity.News;
import com.example.eproject.entity.Role;
import com.example.eproject.entity.User;
import com.example.eproject.service.NewsService;
import com.example.eproject.service.RoleService;
import com.example.eproject.util.Enums;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Set;

@EnableScheduling
@SpringBootApplication
public class EprojectApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(EprojectApplication.class, args);
    }

    @Value("${i18n.localechange.interceptor.default}")
    String localeChangeInterceptorParaName;

    @Value("${i18n.resourcebundle.message.source.default}")
    String resourceBundleMessageSourceBase;

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(localeChangeInterceptorParaName);
        return lci;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(resourceBundleMessageSourceBase);
        return messageSource;
    }

    @Bean
    public SpringTemplateEngine templateEngine(ApplicationContext ctx) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(ctx);
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(new LayoutDialect());
        templateEngine.addDialect(new Java8TimeDialect());
        return templateEngine;
    }

    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    NewsService newsService;

    @Autowired
    RoleService roleService;

    private void createNews(long id, String title, String desc, String img, String content, int views, Enums.NewsStatus status, String author) {
        News news = new News();
        news.setId(id);
        news.setCreatedAt(LocalDateTime.now());
        news.setTitle(title);
        news.setDescription(desc);
        news.setImg(img);
        news.setContent(content);
        news.setViews(views);
        news.setStatus(status);
        news.setCategories(news.getCategories());
        news.setAuthor(author);
//        newsService.save(news);
    }

    public void generateNews() {
        createNews(1, "Dự án nhà thi đấu gần 2.000 tỷ đồng bị bỏ hoang giữa trung tâm TP.HCM",
                "Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập," +
                        " thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.",
                "", "Dự án xây mới Trung tâm Thể dục Thể thao Phan Đình Phùng có chủ trương từ năm 2008, " +
                        "nhưng đến hiện tại vẫn \"bất động\". Hàng chục nghìn m2 \"đất vàng\" nằm ngay trung tâm TP.HCM bị bỏ hoang.",
                9, Enums.NewsStatus.ACTIVE, "Tổng hợp");
        createNews(2, "Người bán bánh mì đâm chết tài xế xe ôm ở TP.HCM",
                "Mâu thuẫn trong lúc nói chuyện, nam thanh niên bán bánh mì trên đường Âu Cơ đã cầm dao đâm chết tài xế xe ôm.",
                "", "Ngày 22/7, Công an quận Tân Phú phối hợp các đơn vị nghiệp vụ Công an TP.HCM lấy lời khai Phạm Hùng Sang" +
                        " (30 tuổi, ngụ tỉnh Đồng Nai) để điều tra về hành vi giết người.",
                8, Enums.NewsStatus.ACTIVE, "Tổng hợp");
        createNews(3, "Trúng độc đắc 205 tỷ đồng khi mua Vietlott theo sinh nhật vợ",
                "Chủ nhân giải thưởng Vietlott hơn 205 tỷ đồng cho biết dãy số trúng thưởng của tấm vé may mắn được ông mua dựa trên ngày sinh nhật của vợ.",
                "", "Sáng 22/7, Công ty Xổ số Điện toán Việt Nam (Vietlott) đã tổ chức lễ trao thưởng" +
                        " cho 2 tỷ phú Jackpot trúng giải trong 2 kỳ quay số liên tiếp là 748 và 749.",
                6, Enums.NewsStatus.ACTIVE, "Tổng hợp");
        createNews(4, "Phá đường dây cho vay lãi nặng hơn 1.800 tỷ đồng: Các đối tượng \"khủng bố tinh thần\" con nợ thế nào?",
                "Cơ quan chức năng vừa triệt phá đường dây cho vay lãi nặng quy mô lớn qua ứng dụng điện thoại di động hoạt động xuyên biên giới vào Việt Nam. " +
                        "Đã có khoảng 159.000 khách hàng vay qua các ứng dụng với tổng số tiền vay là hơn 1.800 tỷ đồng.", "",
                "Content", 10, Enums.NewsStatus.DEACTIVE, "Tổng hợp");
        createNews(5, "Bình Thuận: Thần kỳ, tìm thấy thêm 5 thuyền viên sau 12 ngày lênh đênh trên biển",
                "Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập," +
                        " thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.", "", "Content",
                2, Enums.NewsStatus.DELETED, "Tổng hợp");
        createNews(1, "Hello", " Desc", "", "Content", 1, Enums.NewsStatus.DELETED, "Tổng hợp");
    }

    private void createRole(long id, Enums.Role roleInput) {
        Role role = new Role();
        role.setId(id);
        role.setName(roleInput);
//        roleService.save(role);
    }

    public void generateRole() {
        createRole(1, Enums.Role.USER);
        createRole(2, Enums.Role.MODERATOR);
        createRole(3, Enums.Role.ADMIN);
        createRole(4, Enums.Role.TEACHER);
        createRole(5, Enums.Role.STUDENT);
    }

    @Override
    public void run(String... arg0) throws Exception {
        generateNews();
        generateRole();
    }
}
