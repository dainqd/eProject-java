package com.example.eproject;

import com.example.eproject.entity.Course;
import com.example.eproject.entity.Manager;
import com.example.eproject.entity.News;
import com.example.eproject.entity.Role;
import com.example.eproject.service.CourseService;
import com.example.eproject.service.ManagerService;
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

    @Autowired
    ManagerService managerService;

    private void createNews(long id, String title, String summary, String desc, String img, String content, String contain, int views, Enums.NewsStatus status, String author) {
        News news = new News();
        news.setId(id);
        news.setCreatedAt(LocalDateTime.now());
        news.setTitle(title);
        news.setSummary(summary);
        news.setDescription(desc);
        news.setImg(img);
        news.setContent(content);
        news.setContain(contain);
        news.setViews(views);
        news.setStatus(status);
        news.setCategories(news.getCategories());
        news.setAuthor(author);
        newsService.save(news);
    }

    public void generateNews() {
        createNews(1, "Dự án nhà thi đấu gần 2.000 tỷ đồng bị bỏ hoang giữa trung tâm TP.HCM",
                "",
                "Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập," +
                        " thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.",
                "", "Dự án xây mới Trung tâm Thể dục Thể thao Phan Đình Phùng có chủ trương từ năm 2008, " +
                        "nhưng đến hiện tại vẫn \"bất động\". Hàng chục nghìn m2 \"đất vàng\" nằm ngay trung tâm TP.HCM bị bỏ hoang.",
                "",
                9, Enums.NewsStatus.DEACTIVE, "Tổng hợp");
        createNews(2, "Người bán bánh mì đâm chết tài xế xe ôm ở TP.HCM",
                "",
                "Mâu thuẫn trong lúc nói chuyện, nam thanh niên bán bánh mì trên đường Âu Cơ đã cầm dao đâm chết tài xế xe ôm.",
                "", "Ngày 22/7, Công an quận Tân Phú phối hợp các đơn vị nghiệp vụ Công an TP.HCM lấy lời khai Phạm Hùng Sang" +
                        " (30 tuổi, ngụ tỉnh Đồng Nai) để điều tra về hành vi giết người.",
                "",
                8, Enums.NewsStatus.DEACTIVE, "Tổng hợp");
        createNews(3, "Trúng độc đắc 205 tỷ đồng khi mua Vietlott theo sinh nhật vợ",
                "",
                "Chủ nhân giải thưởng Vietlott hơn 205 tỷ đồng cho biết dãy số trúng thưởng của tấm vé may mắn được ông mua dựa trên ngày sinh nhật của vợ.",
                "", "Sáng 22/7, Công ty Xổ số Điện toán Việt Nam (Vietlott) đã tổ chức lễ trao thưởng" +
                        " cho 2 tỷ phú Jackpot trúng giải trong 2 kỳ quay số liên tiếp là 748 và 749.",
                "",
                6, Enums.NewsStatus.DEACTIVE, "Tổng hợp");
        createNews(4, "Phá đường dây cho vay lãi nặng hơn 1.800 tỷ đồng: Các đối tượng \"khủng bố tinh thần\" con nợ thế nào?",
                "",
                "Cơ quan chức năng vừa triệt phá đường dây cho vay lãi nặng quy mô lớn qua ứng dụng điện thoại di động hoạt động xuyên biên giới vào Việt Nam. " +
                        "Đã có khoảng 159.000 khách hàng vay qua các ứng dụng với tổng số tiền vay là hơn 1.800 tỷ đồng.", "",
                "",
                "Content", 10, Enums.NewsStatus.DEACTIVE, "Tổng hợp");
        createNews(5, "Bình Thuận: Thần kỳ, tìm thấy thêm 5 thuyền viên sau 12 ngày lênh đênh trên biển",
                "",
                "Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập," +
                        " thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.", "", "Content",
                "",
                2, Enums.NewsStatus.DELETED, "Tổng hợp");
        createNews(6, "Hello", "", " Desc", "", "Content",
                "",
                1, Enums.NewsStatus.DELETED, "Tổng hợp");
        createNews(8, "Qualified and reputable teachers",
                "A team of qualified lecturers, experienced in teaching and training the best students in the industry, ensures you a solid and sure source of knowledge.",
                " <p>5SUPERHERO's human resources are well-trained at major training centers and prestigious research " +
                        "institutes in the country or abroad, so they have solid expertise to keep up with development trends in the region and countries around the world. " +
                        " Currently, specialized human resources are concentrated into focused research groups including 5 specialized resear" +
                        "ch groups in the field of educational science " +
                        "As of March 2021, 5SUPERHERO has 475 officers and employees. Of these, 320 teachers are directly involved in teaching, " +
                        "over 72% have doctorate degrees and about 20% are studying graduate degrees at home and abroad. " +
                        "More than 40 teachers have attained the titles of Professor and Associate Professor. " +
                        "The university is overseas, striving by 2025 to have over 90% of the lecturers having doctoral degrees, about 20% of the lecturers having been trained.</p>",
                "",
                " <p>In the context of the 4.0 revolution and the overhaul of general education, despite facing many challenges of the industry, " +
                        "the university staff and lecturers have overcome difficulties, been dedicated and responsible to the profession, " +
                        "and have constantly innovated and developed. The spirit of innovation and creativity is spread throughout each lecture," +
                        " where many active teaching methods are applied to inspire the love of teaching to students. The lecturers also spread their " +
                        "passion for the profession to the general teaching community in the Northern Midland and Mountainous provinces." +
                        " Where they orient and lead general teachers in integrated teaching, STEM education development and local program development." +
                        "A team of qualified, dedicated, responsible and highly skilled professionals is a condition to ensure the quality of " +
                        "educational and training activities of the University, and at the same time to ensure that 5SUPERHERO's " +
                        "learners meet the requirements of human resources. both high quality and global quality. " +
                        "With more than 1 year of establishment and development, going through many difficult and arduous periods," +
                        " the school's teachers have shown a sense of responsibility, dedication, " +
                        "and dedication to the profession through teaching activities, creating the most favorable conditions for generations of adult students.</p>",
                " 5SUPERHERO has nearly 100 pedagogical trainers and key educational administrators participating in national " +
                        "level teacher training and retraining activities, building and developing educational curricula, writing curricula" +
                        " and documents Other services for professional training, " +
                        "implementation of many topics, projects and tasks at ministerial and branch levels in various fields. " +
                        "of science in general, and especially in science education.",
                9600, Enums.NewsStatus.ACTIVE, "Hungnn");
        createNews(9, "The School of Advancement and Technology",
                "Our school always brings the latest innovations of the times, bringing cutting-edge science and technology to our curricula.",
                "<p>In an era of constant change and development, college campuses have the opportunity to expose students to " +
                        "advances in technology through interaction with new information tools, platforms, and websites. " +
                        "In addition, educators are now required to prepare students for success in fields that will rely on technology. " +
                        "Essentially, today teachers are preparing students for fields that don't even exist yet." +
                        "</p>" +
                        "<p>" +
                        "For the first time, educators are in the process of teaching digital native speakers, " +
                        "which creates fear and anxiety for many veteran teachers. " +
                        "These students, often as early as kindergarten, have been exposed to technology and social media from birth. " +
                        "Furthermore, more than any generation in the past, students have relied on technology for entertainment," +
                        "relationships, job search, and learning. This dependence and what seems to be an almost " +
                        "natural possibility with regard to technology presents learners with both promise and challenge." +
                        "</p>" +
                        "<p>To address the changes, this will cover a range of topics around technology in schools," +
                        "including at the college and university level, and online learning." +
                        " With this in mind, we took a closer look at how technology is affecting teaching, " +
                        "learning and career development in modern society.</p>",
                "",
                "<p>In this issue, we look at current trends in technology" +
                        "that are impacting students and the modern educational environment." +
                        "As 5SUPERHERO, we intend for readers to feel informed about the current" +
                        "advancement of technology in schools and to leave readers with questions about where technology will take us next.</p>" +
                        "<p>" +
                        "We hope our readers will feel encouraged by the change taking place" +
                        "and challenged to play an active role in these advancements." +
                        "Ultimately, our aim is to inspire students to proactively implement new strategies," +
                        " tools and ideas through the use of technology in schools and higher education institutions around the world." +
                        " all over the world." +
                        "</p>",
                "Our experiments have been applied to more than 1000 students and it gives a result that is absolutely worth the wait." +
                        " We assessed how educators can use technology to enhance students' problem-solving skills, " +
                        " and how digital technology and specifically mobile devices impact student learning. " +
                        " students and teaching strategies for teachers. " +
                        " The problem was solved and a discussion about the necessity of technology in learning led us to decide " +
                        " to apply advanced scientific technologies in learning.",
                6800, Enums.NewsStatus.ACTIVE, "Hieuhm");
        createNews(10, "Great experience while studying at school",
                "One of the good things about us is that we regularly hold contests every month, helping students gain confidence and expand their knowledge to the maximum.",
                " <p>  " +
                        "Depending on the type of work you are doing, you will learn a wide range of different skills that you can apply in many scenarios." +
                        " For example, if you work for a retail brand you like you can develop really great customer service/sales skills and consequently," +
                        "  your ability to communicate well and work with a diverse group of people will also improve. Similar skills are used in the workforce to manage projects and stakeholders. " +
                        "Similarly, becoming a private tutor for areas you are passionate about gives you great ownership and leadership skills" +
                        " that are easily transferrable into the workplace.</p>" +
                        "<p>" +
                        "Finding different types of work throughout university will help you stand out during the hiring process when applying for your first internship or graduate position. " +
                        "A lot of students graduate with a similar degree so experiences outside of university can differentiate candidates going for certain roles. " +
                        "This is where your work experience comes in. If you combine real professional experience, gained from the time you spent working for a company, with your degree qualification," +
                        " you'll be a more attractive prospect than other graduates who have a degree but no practical experience. " +
                        "Plus you'll have a lot more to talk about in the interview and on your CV!</p>" +
                        "<p>" +
                        "Students are generally not the most financially independent, which is why it's super handy to have a few extra bucks in the bank from a casual " +
                        "or part-time job. Whether you work 5 hours or 20 hours a week, it will help cover things like weekly shops, nights out or even some of" +
                        " those 'crucial' textbooks your lecturer recommended." +
                        "</p>",
                "",
                "<p>" +
                        "Believe it or not, working outside of university can be a great break from studying and actually be quite fun." +
                        " Despite having its ups and downs, work gives you the opportunity to meet new people in an exciting environment. " +
                        " When it comes to choosing the right job for you, take some time to research the different types of opportunities that also suit your needs." +
                        " The most common sectors that students go for are retail and hospitality due to the flexibility of the hours, " +
                        "as well as the potential staff discounts that come as a perk ;)</p>" +
                        "<p>" +
                        "However, other options can include administration type roles at larger companies or popular brands and freelancing, " +
                        "which allows you to flex your creative muscles and gain practical experience doing something you are good at. </p>",
                "You could also spend it on your own personal development such as picking up lessons or an online course to learn " +
                        "a new skill! Having some extra income will also allow you to make the most of university, " +
                        "enabling you to go on trips overseas or have other fun experiences with friends, which are so important for mental health and a study-life balance.",
                9000, Enums.NewsStatus.ACTIVE, "Anhbt");
        createNews(11, "Guaranteed low input quality, high output",
                "More than 80% of our entrance students are always at a low level, but over the years, they are 100% retrained, raising the output level to 95%, having a job right after studying at our school.",
                "String",
                "String",
                "String",
                "String",
                10000, Enums.NewsStatus.ACTIVE, "Dainq");
        createNews(12, "There are attractive, attractive contests",
                "One of the good things about us is that we regularly hold contests every month, helping students gain confidence and expand their knowledge to the maximum.",
                "String",
                "String",
                "String",
                "String",
                10000, Enums.NewsStatus.ACTIVE, "Kefaker");
    }

    private void createRole(long id, Enums.Role roleInput) {
        Role role = new Role();
        role.setId(id);
        role.setName(roleInput);
        roleService.save(role);
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
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date stDate = formatter.parse(startDate);
        Date enDate = formatter.parse(endDate);
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
        course.setOutline(outline);
        courseService.save(course);
    }

    public void generateCourse() throws ParseException {
        createCourse(1, "Learn Designing", "", "", "Multimedia art design has become a fertile land,\n" +
                        "\t\t\t\t\t\t\t\t\t thirsty for human resources with thousands of domestic and foreign advertising companies looking for candidates. \n" +
                        "\t\t\t\t\t\t\t\t\t Do you love beauty, are passionate about creativity and want to design your own life around you? \n" +
                        "\t\t\t\t\t\t\t\t\t Are you ready to enter the multimedia art design industry with exciting career opportunities and attractive salaries?\n" +
                        "\t\t\t\t\t\t\t\t\t Become a designer and confidently show yourself in the challenging creative industry today, why not?"
                , 368, 89, "Ngo Quang Dai", "456,99", 30, "10/02/2021", "10/10/2021", 25, "");
        createCourse(2, "Learn React js beginners", "", "", "React is the most popular javascript library for building user interfaces. \n" +
                        "\t\t\t\t\t\t\t\t\tIt's fast, flexible and it also has a strong online community to help you at all times. \n" +
                        "\t\t\t\t\t\t\t\t\tThe best part is that React is based on components, you break your complex code into individual parts, \n" +
                        "\t\t\t\t\t\t\t\t\tie components and that helps programmers organize their code in a better way. \n" +
                        "\t\t\t\t\t\t\t\t\tA lot of companies are moving to React and that's the reason most of the beginners \n" +
                        "\t\t\t\t\t\t\t\t\tlearn programming and programmers Experienced students start learning ReactJS."
                , 235, 68, "Hoang Minh Hieu", "625,99", 30, "10/02/2022", "10/10/2022", 25, "");
        createCourse(3, "Learn Photography", "", "", "The Photography program will equip students with all the necessary knowledge through subjects such as: \n" +
                        "\t\t\t\t\t\t\t\t\tDigital Photography, History of Vietnam and World Photography, Lenses, Flash, Photographs.\n" +
                        "\t\t\t\t\t\t\t\t\t Landscape, Architectural Photo, Macro Photo, Studio Portrait, Advertising Photo, Sports Photo... \n" +
                        "\t\t\t\t\t\t\t\t\t Skilled to handle basic techniques in photography with different genres such as advertising photos, architectural photos. \n" +
                        "\t\t\t\t\t\t\t\t\tarchitecture, sports photography… especially the creative language of photography."
                , 589, 88, "Ngo Quang Dai", "656,99", 36, "10/10/2022", "10/10/2023", 22, "");
        createCourse(4, "Learn Java - Spring Boot", "", "", "As the most powerful and popular object-oriented programming language today, \n" +
                        "\t\t\t\t\t\t\t\t\tJava is appreciated and praised by many experts for its extremely powerful support. \n" +
                        "\t\t\t\t\t\t\t\t\tThe strength of Java is that it can work on many technology platforms, \n" +
                        "\t\t\t\t\t\t\t\t\tincluding operating in many different operating systems. Or to put it simply, \n" +
                        "\t\t\t\t\t\t\t\t\tJava is a programming language that can \"write once, run everywhere\" (\"write one, run everywhere\") with the JVM. \n" +
                        "\t\t\t\t\t\t\t\t\tSpring Boot is a project developed by JAV (java language) in the Spring framework ecosystem. \n" +
                        "\t\t\t\t\t\t\t\t\tIt helps us programmers simplify the process of programming an application with Spring, \n" +
                        "\t\t\t\t\t\t\t\t\tfocusing only on developing the business for the application."
                , 486, 66, "Ngo Quang Dai", "858,99", 32, "05/04/2022", "05/04/2023", 25, "");
        createCourse(5, "Learn PHP - Laravel", "", "", "PHP stands for Personal Home Page which has now been converted to Hypertext Preprocessor.\n" +
                        "\t\t\t\t\t\t\t\t\t Simply put, PHP is a multi-purpose scripting language. \n" +
                        "\t\t\t\t\t\t\t\t\t PHP is commonly used for developing server-side web applications.\n" +
                        "\t\t\t\t\t\t\t\t\t  Thus, the PHP programming language can handle server-side functions to generate HTML code on the client \n" +
                        "\t\t\t\t\t\t\t\t\t  such as collecting form data, modifying the database, managing files on the server, or other operations. \n" +
                        "\t\t\t\t\t\t\t\t\t  Laravel is one of the most popular PHP Frameworks in the world used to build web applications from small \n" +
                        "\t\t\t\t\t\t\t\t\t  to large projects.Laravel is the choice of many professional PHP programmers for its performance, \n" +
                        "\t\t\t\t\t\t\t\t\t  features and its scalability."
                , 558, 85, "Ngo Quang Dai", "380,99", 36, "27/07/2022", "27/07/2023", 30, "");
        createCourse(6, "Learn Angular", "", "", "Angular is a JavaScript framework and is written in TypeScript. \n" +
                        "\t\t\t\t\t\t\t\t\tGoogle created this framework with the purpose of writing the web interface (Front-end) \n" +
                        "\t\t\t\t\t\t\t\t\tstandard \"less effort\". Not only does it offer the benefits of a framework, \n" +
                        "\t\t\t\t\t\t\t\t\tbut Angular keeps the same structure as a standard programming language. \n" +
                        "\t\t\t\t\t\t\t\t\tThat makes it easy for developers to scale the project as well as maintain it."
                , 569, 108, "Ngo Quang Dai", "489,99", 30, "27/12/2022", "27/12/2023", 25, "");
        createCourse(7, "Learn Marketing", "", "", "Digital Marketing in general and Online Marketing in particular is a strong industry trend \n" +
                        "\t\t\t\t\t\t\t\t\tand is always \"thirst\" for human resources. \n" +
                        "\t\t\t\t\t\t\t\t\tTherefore, it is not difficult to understand when the keyword \"Online Marketing Course\" \n" +
                        "\t\t\t\t\t\t\t\t\tis becoming very HOT in the search engines.\n" +
                        "\t\t\t\t\t\t\t\t\tOne of the leading prestigious training units in Vietnam must mention the Marketing Online course of 5SUPERHERO. \n" +
                        "\t\t\t\t\t\t\t\t\tSo, how in-depth this course is, what it offers and what the future of employment promises, let's explore it right here."
                , 862, 88, "Ngo Quang Dai", "386,99", 36, "12/12/2022", "12/12/2023", 30, "");
        createCourse(8, "Learn Surveying", "", "", "Data analysis is the science of analyzing raw data to draw conclusions about that information. \n" +
                        "\t\t\t\t\t\t\t\t\tData Analysts find trends and metrics in chunks of information that would otherwise \n" +
                        "\t\t\t\t\t\t\t\t\tbe missed without the use of techniques or analytical tools. The information obtained \n" +
                        "\t\t\t\t\t\t\t\t\tcan be used to optimize processes that increase the overall efficiency of a business or a system."
                , 766, 98, "Hoang Minh Hieu", "856,99", 30, "02/02/2022", "02/02/2023", 25, "");
    }

    public void createManager(long id, String fullName, String summary, String position, String introduce, String email, String phoneNumber) {
        Manager manager = new Manager();
        manager.setId(id);
        manager.setFullName(fullName);
        manager.setFeedbacks(10);
        manager.setSummary(summary);
        manager.setPosition(position);
        manager.setIntroduce(introduce);
        manager.setEmail(email);
        manager.setPhoneNumber(phoneNumber);
        manager.setStatus(Enums.ManagerStatus.ACTIVE);

        managerService.save(manager);
    }

    public void generateManager() {
        createManager(1, "Ngo Quang Dai", "Dr", "Principal",
                "As one of the best masters in the field of Information Technology, Dr. Ngo Quang Dai is the rector of 5SUPERHERO under the appointment of the Board of Directors of 5SUPERHERO University.",
                "dainq@gmail.com", "0388889899");
        createManager(2, "Hoang Minh Hieu", "Dr", "Direction",
                "As one of the first lecturers, since the school was established until now, Dr. Hoang Minh Hieu has witnessed the growth of hundreds of generations of 5SUPERHERO students",
                "hieuhm@gmail.com", "0989898888");
        createManager(3, "Bui Tuan Anh", "MSc", "Leader",
                "Is a department head possessing excellent communication skills and teaching skills that have made MSc. Bui Tuan Anh was selected as one of the most talented lecturers in 5SUPERHERO",
                "anhbt@gmail.com", "0868688868");
        createManager(4, "Nguyen Ngoc Hung", "ThS", "Deputy",
                "Not less than the head of the department in terms of skills and also one of the multi-talented instructors of 5SUPERHERO is MSc. Nguyen Ngoc Hung has been highly respected by students in the school",
                "hungnn@gmail.com", "0386886688");
        createManager(5, "Ngo Minh Hoang", "Mr", "",
                "Mr. Ngo Minh Hoang is a teacher who is always strict and strict with students, but he is also one of the lecturers who is always devoted to the students' academic career.",
                "hoangnm@gmail.com", "039449494");
        createManager(6, "Duong Cong Ke", "ThS", "Trainers",
                "Always enthusiastic about the profession with humor and wit are the reasons why MSc's training class. Duong Cong Ke is always full, on time and especially indispensable is the laughter.",
                "kedc@gmail.com", "0980999899");
        createManager(7, "Tran Nguyen Ngoc Linh", "ThS", "Manager",
                "MSc. Tran Nguyen Ngoc Linh is the school's excellent female lecturer when she entered the top 10 most proficient teachers in Vietnam",
                "linhtnn@gmail.com", "0868889899");
        createManager(8, "Hoang Bao Ngoc", "Mrs", "",
                "In parallel between the students' comments is the fastidiousness of Mrs. Hoang Bao Ngoc is her boundless love for her players",
                "ngochb@gmail.com", "098458985");
    }

    @Override
    public void run(String... arg0) throws Exception {
        generateNews();
        generateRole();
        generateCourse();
        generateManager();
    }
}
