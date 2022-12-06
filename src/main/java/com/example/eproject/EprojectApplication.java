package com.example.eproject;

import com.example.eproject.entity.Course;
import com.example.eproject.entity.News;
import com.example.eproject.entity.Role;
import com.example.eproject.service.CourseService;
import com.example.eproject.service.NewsService;
import com.example.eproject.service.RoleService;
import com.example.eproject.util.Enums;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
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

    @Autowired
    CourseService courseService;

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

    private void createCourse(long id, String title, String intent, String condition, String content,
                              long comments, long reviews, String trainer, String price, long seat,
                              String startDate, String endDate, long free, String outlineStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date stDate = formatter.parse(startDate);
        System.out.println(stDate);
        Date enDate = formatter.parse(endDate);
        System.out.println(enDate);
// Tạo mới khóa học
        Course course = new Course();
        course.setId(id);
        course.setTitle(title);
        course.setIntent(intent);
        course.setCondition(condition);
        course.setContent(content);
        course.setComments(comments);
        course.setReviews(reviews);
        course.setTrainer(trainer);
        course.setPrice(price);
        course.setSeat(seat);
        course.setStartDate(stDate);
        course.setEndDate(enDate);
        course.setFree(free);
        course.setStatus(Enums.CourseStatus.ACTIVE);
        List<String> outline = new ArrayList<String>(Arrays.asList(outlineStr.split(",")));
        System.out.println(outline);
        course.setOutline(outline);
//        courseService.save(course);
    }

    public void generateCourse() throws ParseException {
        createCourse(1, "Learn Designing", "", "", "Multimedia art design has become a fertile land,\n" +
                        "\t\t\t\t\t\t\t\t\t thirsty for human resources with thousands of domestic and foreign advertising companies looking for candidates. \n" +
                        "\t\t\t\t\t\t\t\t\t Do you love beauty, are passionate about creativity and want to design your own life around you? \n" +
                        "\t\t\t\t\t\t\t\t\t Are you ready to enter the multimedia art design industry with exciting career opportunities and attractive salaries?\n" +
                        "\t\t\t\t\t\t\t\t\t Become a designer and confidently show yourself in the challenging creative industry today, why not?"
                , 368, 89, "Ngo Quang Dai", "456,99", 30, "2022-02-10", "2022-10-10", 25, "");
        createCourse(2, "Learn React js beginners", "", "", "React is the most popular javascript library for building user interfaces. \n" +
                        "\t\t\t\t\t\t\t\t\tIt's fast, flexible and it also has a strong online community to help you at all times. \n" +
                        "\t\t\t\t\t\t\t\t\tThe best part is that React is based on components, you break your complex code into individual parts, \n" +
                        "\t\t\t\t\t\t\t\t\tie components and that helps programmers organize their code in a better way. \n" +
                        "\t\t\t\t\t\t\t\t\tA lot of companies are moving to React and that's the reason most of the beginners \n" +
                        "\t\t\t\t\t\t\t\t\tlearn programming and programmers Experienced students start learning ReactJS."
                , 235, 68, "Hoang Minh Hieu", "625,99", 30, "2022-02-10", "2022-10-10", 25, "");
        createCourse(3, "Learn Photography", "", "", "The Photography program will equip students with all the necessary knowledge through subjects such as: \n" +
                        "\t\t\t\t\t\t\t\t\tDigital Photography, History of Vietnam and World Photography, Lenses, Flash, Photographs.\n" +
                        "\t\t\t\t\t\t\t\t\t Landscape, Architectural Photo, Macro Photo, Studio Portrait, Advertising Photo, Sports Photo... \n" +
                        "\t\t\t\t\t\t\t\t\t Skilled to handle basic techniques in photography with different genres such as advertising photos, architectural photos. \n" +
                        "\t\t\t\t\t\t\t\t\tarchitecture, sports photography… especially the creative language of photography."
                , 589, 88, "Ngo Quang Dai", "656,99", 36, "2021-02-10", "2022-10-10", 22, "");
        createCourse(4, "Learn Java - Spring Boot", "", "", "As the most powerful and popular object-oriented programming language today, \n" +
                        "\t\t\t\t\t\t\t\t\tJava is appreciated and praised by many experts for its extremely powerful support. \n" +
                        "\t\t\t\t\t\t\t\t\tThe strength of Java is that it can work on many technology platforms, \n" +
                        "\t\t\t\t\t\t\t\t\tincluding operating in many different operating systems. Or to put it simply, \n" +
                        "\t\t\t\t\t\t\t\t\tJava is a programming language that can \"write once, run everywhere\" (\"write one, run everywhere\") with the JVM. \n" +
                        "\t\t\t\t\t\t\t\t\tSpring Boot is a project developed by JAV (java language) in the Spring framework ecosystem. \n" +
                        "\t\t\t\t\t\t\t\t\tIt helps us programmers simplify the process of programming an application with Spring, \n" +
                        "\t\t\t\t\t\t\t\t\tfocusing only on developing the business for the application."
                , 486, 66, "Ngo Quang Dai", "858,99", 32, "2022-04-05", "2023-04-05", 25, "");
        createCourse(5, "Learn PHP - Laravel", "", "", "PHP stands for Personal Home Page which has now been converted to Hypertext Preprocessor.\n" +
                        "\t\t\t\t\t\t\t\t\t Simply put, PHP is a multi-purpose scripting language. \n" +
                        "\t\t\t\t\t\t\t\t\t PHP is commonly used for developing server-side web applications.\n" +
                        "\t\t\t\t\t\t\t\t\t  Thus, the PHP programming language can handle server-side functions to generate HTML code on the client \n" +
                        "\t\t\t\t\t\t\t\t\t  such as collecting form data, modifying the database, managing files on the server, or other operations. \n" +
                        "\t\t\t\t\t\t\t\t\t  Laravel is one of the most popular PHP Frameworks in the world used to build web applications from small \n" +
                        "\t\t\t\t\t\t\t\t\t  to large projects.Laravel is the choice of many professional PHP programmers for its performance, \n" +
                        "\t\t\t\t\t\t\t\t\t  features and its scalability."
                , 558, 85, "Ngo Quang Dai", "380,99", 36, "2022-01-01", "2022-10-10", 30, "");
        createCourse(6, "Learn Angular", "", "", "Angular is a JavaScript framework and is written in TypeScript. \n" +
                        "\t\t\t\t\t\t\t\t\tGoogle created this framework with the purpose of writing the web interface (Front-end) \n" +
                        "\t\t\t\t\t\t\t\t\tstandard \"less effort\". Not only does it offer the benefits of a framework, \n" +
                        "\t\t\t\t\t\t\t\t\tbut Angular keeps the same structure as a standard programming language. \n" +
                        "\t\t\t\t\t\t\t\t\tThat makes it easy for developers to scale the project as well as maintain it."
                , 569, 108, "Ngo Quang Dai", "489,99", 30, "2022-10-10", "2023-10-10", 25, "");
        createCourse(7, "Learn Marketing", "", "", "Digital Marketing in general and Online Marketing in particular is a strong industry trend \n" +
                        "\t\t\t\t\t\t\t\t\tand is always \"thirst\" for human resources. \n" +
                        "\t\t\t\t\t\t\t\t\tTherefore, it is not difficult to understand when the keyword \"Online Marketing Course\" \n" +
                        "\t\t\t\t\t\t\t\t\tis becoming very HOT in the search engines.\n" +
                        "\t\t\t\t\t\t\t\t\tOne of the leading prestigious training units in Vietnam must mention the Marketing Online course of 5SUPERHERO. \n" +
                        "\t\t\t\t\t\t\t\t\tSo, how in-depth this course is, what it offers and what the future of employment promises, let's explore it right here."
                , 862, 88, "Ngo Quang Dai", "386,99", 36, "2022-02-10", "2022-06-10", 30, "");
        createCourse(8, "Learn Surveying", "", "", "Data analysis is the science of analyzing raw data to draw conclusions about that information. \n" +
                        "\t\t\t\t\t\t\t\t\tData Analysts find trends and metrics in chunks of information that would otherwise \n" +
                        "\t\t\t\t\t\t\t\t\tbe missed without the use of techniques or analytical tools. The information obtained \n" +
                        "\t\t\t\t\t\t\t\t\tcan be used to optimize processes that increase the overall efficiency of a business or a system."
                , 766, 98, "Hoang Minh Hieu", "856,99", 30, "2023-02-02", "2023-10-10", 25, "");
    }


    @Override
    public void run(String... arg0) throws Exception {
        generateNews();
        generateRole();
        generateCourse();
    }
}
