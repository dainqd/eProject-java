package com.example.eproject;

import com.example.eproject.entity.News;
import com.example.eproject.entity.Role;
import com.example.eproject.service.NewsService;
import com.example.eproject.service.RoleService;
import com.example.eproject.util.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;


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
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    NewsService newsService;

    @Autowired
    RoleService roleService;

    @Override
    public void run(String... arg0) throws Exception {
        News news = new News();
        news.setId(1);
        news.setCreatedAt(news.getCreatedAt());
        news.setTitle("Dự án nhà thi đấu gần 2.000 tỷ đồng bị bỏ hoang giữa trung tâm TP.HCM");
        news.setDescription("Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập," +
                " thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.");
        news.setImg("");
        news.setContent("Dự án xây mới Trung tâm Thể dục Thể thao Phan Đình Phùng có chủ trương từ năm 2008, " +
                "nhưng đến hiện tại vẫn \"bất động\". Hàng chục nghìn m2 \"đất vàng\" nằm ngay trung tâm TP.HCM bị bỏ hoang.");
        news.setViews(9);
        news.setStatus(Enums.NewsStatus.ACTIVE);
        news.setCategories(news.getCategories());
        news.setAuthor("Tổng hợp");

        News news1 = new News();
        news1.setId(2);
        news1.setCreatedAt(news.getCreatedAt());
        news1.setTitle("Người bán bánh mì đâm chết tài xế xe ôm ở TP.HCM");
        news1.setDescription("Mâu thuẫn trong lúc nói chuyện, nam thanh niên bán bánh mì trên đường Âu Cơ đã cầm dao đâm chết tài xế xe ôm.");
        news1.setImg("");
        news1.setContent("Ngày 22/7, Công an quận Tân Phú phối hợp các đơn vị nghiệp vụ Công an TP.HCM lấy lời khai Phạm Hùng Sang" +
                " (30 tuổi, ngụ tỉnh Đồng Nai) để điều tra về hành vi giết người.");
        news1.setViews(6);
        news1.setStatus(Enums.NewsStatus.ACTIVE);
        news1.setCategories(news1.getCategories());
        news1.setAuthor("Tổng hợp");

        News news2 = new News();
        news2.setId(3);
        news2.setCreatedAt(news.getCreatedAt());
        news2.setTitle("Trúng độc đắc 205 tỷ đồng khi mua Vietlott theo sinh nhật vợ");
        news2.setDescription("Chủ nhân giải thưởng Vietlott hơn 205 tỷ đồng cho biết dãy số trúng thưởng của tấm vé may mắn được ông mua dựa trên ngày sinh nhật của vợ.");
        news2.setImg("");
        news2.setContent("Sáng 22/7, Công ty Xổ số Điện toán Việt Nam (Vietlott) đã tổ chức lễ trao thưởng cho 2 tỷ phú Jackpot trúng giải trong 2 kỳ quay số liên tiếp là 748 và 749.");
        news2.setViews(8);
        news2.setStatus(Enums.NewsStatus.ACTIVE);
        news2.setCategories(news2.getCategories());
        news2.setAuthor("Tổng hợp");

        News news3 = new News();
        news3.setId(4);
        news3.setCreatedAt(news.getCreatedAt());
        news3.setTitle("Phá đường dây cho vay lãi nặng hơn 1.800 tỷ đồng: Các đối tượng \"khủng bố tinh thần\" con nợ thế nào?");
        news3.setDescription("Cơ quan chức năng vừa triệt phá đường dây cho vay lãi nặng quy mô lớn qua ứng dụng điện thoại di động hoạt động xuyên biên giới vào Việt Nam. " +
                "Đã có khoảng 159.000 khách hàng vay qua các ứng dụng với tổng số tiền vay là hơn 1.800 tỷ đồng.");
        news3.setImg("");
        news3.setContent("Dự án xây mới Trung tâm Thể dục Thể thao Phan Đình Phùng có chủ trương từ năm 2008, " +
                "nhưng đến hiện tại vẫn \"bất động\". Hàng chục nghìn m2 \"đất vàng\" nằm ngay trung tâm TP.HCM bị bỏ hoang.");
        news3.setViews(9);
        news3.setStatus(Enums.NewsStatus.ACTIVE);
        news3.setCategories(news3.getCategories());
        news3.setAuthor("Tổng hợp");

        News news4 = new News();
        news4.setId(5);
        news4.setCreatedAt(news.getCreatedAt());
        news4.setTitle("Dự án nhà thi đấu gần 2.000 tỷ đồng bị bỏ hoang giữa trung tâm TP.HCM");
        news4.setDescription("Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập," +
                " thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.");
        news4.setImg("");
        news4.setContent("Tại căn nhà trên đường đường Hoàng Văn Thụ, phường Cốc Lếu, cơ quan chức năng bắt giữ nghi phạm cầm đầu là Phạm Thị Huyền (32 tuổi, người địa phương) " +
                "cùng 17 nhân viên cấp dưới đang có hành vi đòi nợ, nhắc nợ, thu hồi nợ.");
        news4.setViews(16);
        news4.setStatus(Enums.NewsStatus.DEACTIVE);
        news4.setCategories(news4.getCategories());
        news4.setAuthor("Tổng hợp");

        News news5 = new News();
        news5.setId(6);
        news5.setCreatedAt(news.getCreatedAt());
        news5.setTitle("Bình Thuận: Thần kỳ, tìm thấy thêm 5 thuyền viên sau 12 ngày lênh đênh trên biển");
        news5.setDescription("Ngày 22/7, tin từ Trung tâm Phối hợp tìm kiếm cứu nạn hàng hải khu vực 3, 11h trưa cùng ngày, " +
                "thêm 5 thuyền viên gặp nạn trên biển được một tàu Ai Cập cứu sống.");
        news5.setImg("");
        news5.setContent("Khoảng 11h ngày 22/7 tàu phát hiện được 5 thuyền viên của tàu BTh 97478 TS trên chiếc thúng chai trôi dạt tại vị trí 11 độ 34’N – 112 độ 49’E, " +
                "cách Tp.Nha Trang, tỉnh Khánh Hòa khoảng 214 lý về phía Đông, cách Vũng Tàu khoảng 344 lý về phía Đông Bắc.");
        news5.setViews(18);
        news5.setStatus(Enums.NewsStatus.ACTIVE);
        news5.setCategories(news5.getCategories());
        news5.setAuthor("Tổng hợp");
        newsService.save(news);
        newsService.save(news1);
        newsService.save(news2);
        newsService.save(news3);
        newsService.save(news4);
        newsService.save(news5);

        Role role1 = new Role();
        role1.setId(1);
        role1.setName(Enums.Role.USER);

        Role role2 = new Role();
        role2.setId(2);
        role2.setName(Enums.Role.MODERATOR);

        Role role3 = new Role();
        role3.setId(3);
        role3.setName(Enums.Role.ADMIN);

        roleService.save(role1);
        roleService.save(role2);
        roleService.save(role3);
    }
}
