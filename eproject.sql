-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th2 02, 2023 lúc 03:54 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `eproject`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `admissions`
--

CREATE TABLE `admissions` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `link_face_book` varchar(255) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `attendancereport`
--

CREATE TABLE `attendancereport` (
  `id` bigint(20) NOT NULL,
  `condition` varchar(255) DEFAULT NULL,
  `lesson` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `studentid` bigint(20) NOT NULL,
  `teacherid` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `categories`
--

CREATE TABLE `categories` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classroom`
--

CREATE TABLE `classroom` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `mod_id` bigint(20) NOT NULL,
  `note` varchar(255) DEFAULT NULL,
  `qty_student` int(11) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classroom_faculties`
--

CREATE TABLE `classroom_faculties` (
  `classroom_id` bigint(20) NOT NULL,
  `faculty_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `classroom_users`
--

CREATE TABLE `classroom_users` (
  `classroom_id` bigint(20) NOT NULL,
  `users_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course`
--

CREATE TABLE `course` (
  `id` bigint(20) NOT NULL,
  `comments` bigint(20) NOT NULL,
  `condition` longtext DEFAULT NULL,
  `content` longtext DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `free` bigint(20) NOT NULL,
  `intent` longtext DEFAULT NULL,
  `outline` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  `reviews` bigint(20) NOT NULL,
  `seat` bigint(20) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `trainer` varchar(255) DEFAULT NULL,
  `thumbnail` longtext DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `course`
--

INSERT INTO `course` (`id`, `comments`, `condition`, `content`, `end_date`, `free`, `intent`, `outline`, `price`, `reviews`, `seat`, `start_date`, `status`, `title`, `trainer`, `thumbnail`) VALUES
(1, 368, '', 'Multimedia art design has become a fertile land,\n									 thirsty for human resources with thousands of domestic and foreign advertising companies looking for candidates. \n									 Do you love beauty, are passionate about creativity and want to design your own life around you? \n									 Are you ready to enter the multimedia art design industry with exciting career opportunities and attractive salaries?\n									 Become a designer and confidently show yourself in the challenging creative industry today, why not?', '2021-10-10 00:00:00', 25, '', '', '456,99', 89, 30, '2021-02-10 00:00:00', 'ACTIVE', 'Learn Designing', 'Ngo Quang Dai', 'https://icdn.dantri.com.vn/thumb_w/660/2021/09/08/316784x441-1631079051594.jpg'),
(2, 235, '', 'React is the most popular javascript library for building user interfaces. \n									It\'s fast, flexible and it also has a strong online community to help you at all times. \n									The best part is that React is based on components, you break your complex code into individual parts, \n									ie components and that helps programmers organize their code in a better way. \n									A lot of companies are moving to React and that\'s the reason most of the beginners \n									learn programming and programmers Experienced students start learning ReactJS.', '2022-10-10 00:00:00', 25, '', '', '625,99', 68, 30, '2022-02-10 00:00:00', 'ACTIVE', 'Learn React js beginners', 'Hoang Minh Hieu', 'https://daohieu.com/wp-content/uploads/2020/05/meo-con.jpg'),
(3, 589, '', 'The Photography program will equip students with all the necessary knowledge through subjects such as: \n									Digital Photography, History of Vietnam and World Photography, Lenses, Flash, Photographs.\n									 Landscape, Architectural Photo, Macro Photo, Studio Portrait, Advertising Photo, Sports Photo... \n									 Skilled to handle basic techniques in photography with different genres such as advertising photos, architectural photos. \n									architecture, sports photography… especially the creative language of photography.', '2023-10-10 00:00:00', 22, '', '', '656,99', 88, 36, '2022-10-10 00:00:00', 'ACTIVE', 'Learn Photography', 'Ngo Quang Dai', 'https://thegdian.com/wp-content/uploads/2021/01/nam-mo-thay-meo-con-danh-con-gi-thumb.jpg'),
(4, 486, '', 'As the most powerful and popular object-oriented programming language today, \n									Java is appreciated and praised by many experts for its extremely powerful support. \n									The strength of Java is that it can work on many technology platforms, \n									including operating in many different operating systems. Or to put it simply, \n									Java is a programming language that can \"write once, run everywhere\" (\"write one, run everywhere\") with the JVM. \n									Spring Boot is a project developed by JAV (java language) in the Spring framework ecosystem. \n									It helps us programmers simplify the process of programming an application with Spring, \n									focusing only on developing the business for the application.', '2023-04-05 00:00:00', 25, '', '', '858,99', 66, 32, '2022-04-05 00:00:00', 'ACTIVE', 'Learn Java - Spring Boot', 'Ngo Quang Dai', 'https://pic-bstarstatic.akamaized.net/ugc/2620577bdb127a891ddb11a985b9c8dc5d825c41.jpg'),
(5, 558, '', 'PHP stands for Personal Home Page which has now been converted to Hypertext Preprocessor.\n									 Simply put, PHP is a multi-purpose scripting language. \n									 PHP is commonly used for developing server-side web applications.\n									  Thus, the PHP programming language can handle server-side functions to generate HTML code on the client \n									  such as collecting form data, modifying the database, managing files on the server, or other operations. \n									  Laravel is one of the most popular PHP Frameworks in the world used to build web applications from small \n									  to large projects.Laravel is the choice of many professional PHP programmers for its performance, \n									  features and its scalability.', '2023-07-27 00:00:00', 30, '', '', '380,99', 85, 36, '2022-07-27 00:00:00', 'ACTIVE', 'Learn PHP - Laravel', 'Ngo Quang Dai', 'http://mcvideomd1fr.keeng.net/playnow/images/20180707/1527809.jpg'),
(6, 569, '', 'Angular is a JavaScript framework and is written in TypeScript. \n									Google created this framework with the purpose of writing the web interface (Front-end) \n									standard \"less effort\". Not only does it offer the benefits of a framework, \n									but Angular keeps the same structure as a standard programming language. \n									That makes it easy for developers to scale the project as well as maintain it.', '2023-12-27 00:00:00', 25, '', '', '489,99', 108, 30, '2022-12-27 00:00:00', 'ACTIVE', 'Learn Angular', 'Ngo Quang Dai', 'https://pic-bstarstatic.akamaized.net/ugc/2620577bdb127a891ddb11a985b9c8dc5d825c41.jpg@1200w_630h_1e_1c_1f.webp'),
(7, 862, '', 'Digital Marketing in general and Online Marketing in particular is a strong industry trend \n									and is always \"thirst\" for human resources. \n									Therefore, it is not difficult to understand when the keyword \"Online Marketing Course\" \n									is becoming very HOT in the search engines.\n									One of the leading prestigious training units in Vietnam must mention the Marketing Online course of 5SUPERHERO. \n									So, how in-depth this course is, what it offers and what the future of employment promises, let\'s explore it right here.', '2023-12-12 00:00:00', 30, '', '', '386,99', 88, 36, '2022-12-12 00:00:00', 'ACTIVE', 'Learn Marketing', 'Ngo Quang Dai', 'https://thuthuatnhanh.com/wp-content/uploads/2022/06/Hinh-meo-con-cute.jpg'),
(8, 766, '', 'Data analysis is the science of analyzing raw data to draw conclusions about that information. \n									Data Analysts find trends and metrics in chunks of information that would otherwise \n									be missed without the use of techniques or analytical tools. The information obtained \n									can be used to optimize processes that increase the overall efficiency of a business or a system.', '2023-02-02 00:00:00', 25, '', '', '856,99', 98, 30, '2022-02-02 00:00:00', 'ACTIVE', 'Learn Surveying', 'Hoang Minh Hieu', 'https://img.meta.com.vn/Data/image/2021/09/21/anh-meo-con-2.jpg');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `course_register`
--

CREATE TABLE `course_register` (
  `id` bigint(20) NOT NULL,
  `course_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `course_register`
--

INSERT INTO `course_register` (`id`, `course_name`, `email`, `name`, `phone_number`, `status`) VALUES
(1, NULL, 'ngodaix5tp@gmail.com', 'sgas', '034534636', 'PENDING'),
(2, NULL, 'ngodaix5tp@gmail.com', 'dzs', '233466325', 'PENDING'),
(3, NULL, 'ngodaix5tp@gmail.com', 'sdg', NULL, 'PENDING'),
(4, 'Learn Marketing', 'ngodaix5tp@gmail.com', 'SƯGáDGw', '009640664', 'PENDING'),
(5, 'Learn Marketing', 'ngodaxi5tp@gmail.com', 'fasgf', '93045305', 'PENDING'),
(6, 'Learn Angular', 'ngodaix5tp@gmail.com', 'sgsg', '09059353', 'PENDING');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `events`
--

CREATE TABLE `events` (
  `id` bigint(20) NOT NULL,
  `content` longtext DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `organiser` varchar(255) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `ticket_number` int(11) NOT NULL,
  `ticket_price` float NOT NULL,
  `title` varchar(255) DEFAULT NULL,
  `location_id` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `events`
--

INSERT INTO `events` (`id`, `content`, `description`, `end_date`, `organiser`, `start_date`, `status`, `thumbnail`, `ticket_number`, `ticket_price`, `title`, `location_id`) VALUES
(1, 'jjh', 'ghghgh', '2023-01-27 08:46:13', 'hhh', '2023-01-01 08:46:13', 'ACTIVE', 'https://cdn.pixabay.com/photo/2016/03/28/12/35/cat-1285634_960_720.png', 1, 11, 'hhh', NULL),
(2, 'gf', 'tr', '2023-01-19 08:46:50', 't', '2023-01-01 08:46:50', 'ACTIVE', 'https://cdn.pixabay.com/photo/2015/11/16/14/43/cat-1045782_960_720.jpg', 2, 2, 'sahgd', NULL),
(3, 'gf', 'tr', '2023-01-19 08:46:50', 't', '2023-01-01 08:46:50', 'ACTIVE', 'https://cdn.pixabay.com/photo/2014/04/13/20/49/cat-323262_960_720.jpg', 2, 2, 'sahgd', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `faculty`
--

CREATE TABLE `faculty` (
  `id` bigint(20) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `feedbacks`
--

CREATE TABLE `feedbacks` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `message` longtext DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `userid` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `location`
--

CREATE TABLE `location` (
  `id` bigint(20) NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `ward` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `managers`
--

CREATE TABLE `managers` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `feedbacks` bigint(20) DEFAULT NULL,
  `full_name` varchar(255) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `position` varchar(255) DEFAULT NULL,
  `avatar` text DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `summary` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `managers`
--

INSERT INTO `managers` (`id`, `email`, `feedbacks`, `full_name`, `introduce`, `phone_number`, `position`, `avatar`, `status`, `summary`) VALUES
(1, 'dainq@gmail.com', 10, 'Ngo Quang Dai', 'As one of the best masters in the field of Information Technology, Dr. Ngo Quang Dai is the rector of 5SUPERHERO under the appointment of the Board of Directors of 5SUPERHERO University.', '0388889899', 'Principal', 'https://kimipet.vn/wp-content/uploads/2022/06/cach-tri-nam-cho-meo-tai-nha.jpg', 'ACTIVE', 'Dr'),
(2, 'hieuhm@gmail.com', 10, 'Hoang Minh Hieu', 'As one of the first lecturers, since the school was established until now, Dr. Hoang Minh Hieu has witnessed the growth of hundreds of generations of 5SUPERHERO students', '0989898888', 'Direction', 'https://img.meta.com.vn/Data/image/2021/09/22/anh-meo-cute-de-thuong-dang-yeu-42.jpg', 'ACTIVE', 'Dr'),
(3, 'anhbt@gmail.com', 10, 'Bui Tuan Anh', 'Is a department head possessing excellent communication skills and teaching skills that have made MSc. Bui Tuan Anh was selected as one of the most talented lecturers in 5SUPERHERO', '0868688868', 'Leader', 'https://bdkhtravinh.vn/hinh-anh-meo-con-dang-yeu/imager_37298.jpg', 'ACTIVE', 'MSc'),
(4, 'hungnn@gmail.com', 10, 'Nguyen Ngoc Hung', 'Not less than the head of the department in terms of skills and also one of the multi-talented instructors of 5SUPERHERO is MSc. Nguyen Ngoc Hung has been highly respected by students in the school', '0386886688', 'Deputy', 'https://anhdep123.com/45-hinh-anh-meo-con-cute-de-thuong-nhat/hinh-anh-con-meo-ngo-nghinh/', 'ACTIVE', 'ThS'),
(5, 'hoangnm@gmail.com', 10, 'Ngo Minh Hoang', 'Mr. Ngo Minh Hoang is a teacher who is always strict and strict with students, but he is also one of the lecturers who is always devoted to the students\' academic career.', '039449494', '', 'https://allimages.sgp1.digitaloceanspaces.com/tipeduvn/2022/06/50-Hinh-anh-meo-con-dep-anh-meo-con-xinh.jpg', 'ACTIVE', 'Mr'),
(6, 'kedc@gmail.com', 10, 'Duong Cong Ke', 'Always enthusiastic about the profession with humor and wit are the reasons why MSc\'s training class. Duong Cong Ke is always full, on time and especially indispensable is the laughter.', '0980999899', 'Trainers', 'https://kynguyenlamdep.com/wp-content/uploads/2022/01/hinh-anh-meo-con-sieu-cute-scaled.jpg', 'ACTIVE', 'ThS'),
(7, 'linhtnn@gmail.com', 10, 'Tran Nguyen Ngoc Linh', 'MSc. Tran Nguyen Ngoc Linh is the school\'s excellent female lecturer when she entered the top 10 most proficient teachers in Vietnam', '0868889899', 'Manager', 'https://i0.wp.com/thatnhucuocsong.com.vn/wp-content/uploads/2022/01/hinh-anh-meo-con-de-thuong-1.jpg?ssl=1', 'ACTIVE', 'ThS'),
(8, 'ngochb@gmail.com', 10, 'Hoang Bao Ngoc', 'In parallel between the students\' comments is the fastidiousness of Mrs. Hoang Bao Ngoc is her boundless love for her players', '098458985', '', 'http://4.bp.blogspot.com/-IzEbtVMSMLo/VM2jMc5iyII/AAAAAAAAWbE/wGWx2PXhjAk/s1600/hinh-anh-meo-con-de-thuong-nhat-1.jpg', 'ACTIVE', 'Mrs');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `markreport`
--

CREATE TABLE `markreport` (
  `id` bigint(20) NOT NULL,
  `aspect` varchar(255) DEFAULT NULL,
  `mark` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `student_id` bigint(20) NOT NULL,
  `subject` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `news`
--

CREATE TABLE `news` (
  `id` bigint(20) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `comments` int(11) NOT NULL,
  `contain` longtext DEFAULT NULL,
  `content` longtext DEFAULT NULL,
  `description` longtext DEFAULT NULL,
  `img` longtext DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `summary` longtext DEFAULT NULL,
  `tag` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `views` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `news`
--

INSERT INTO `news` (`id`, `author`, `comments`, `contain`, `content`, `description`, `img`, `status`, `summary`, `tag`, `title`, `views`) VALUES
(1, 'Tổng hợp', 1, '', 'Dự án xây mới Trung tâm Thể dục Thể thao Phan Đình Phùng có chủ trương từ năm 2008, nhưng đến hiện tại vẫn \"bất động\". Hàng chục nghìn m2 \"đất vàng\" nằm ngay trung tâm TP.HCM bị bỏ hoang.', 'Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập, thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.', NULL, 'DEACTIVE', '', '', 'Dự án nhà thi đấu gần 2.000 tỷ đồng bị bỏ hoang giữa trung tâm TP.HCM', 9),
(2, 'Tổng hợp', 1, '', 'Ngày 22/7, Công an quận Tân Phú phối hợp các đơn vị nghiệp vụ Công an TP.HCM lấy lời khai Phạm Hùng Sang (30 tuổi, ngụ tỉnh Đồng Nai) để điều tra về hành vi giết người.', 'Mâu thuẫn trong lúc nói chuyện, nam thanh niên bán bánh mì trên đường Âu Cơ đã cầm dao đâm chết tài xế xe ôm.', '', 'DEACTIVE', '', '', 'Người bán bánh mì đâm chết tài xế xe ôm ở TP.HCM', 8),
(3, 'Tổng hợp', 1, '', 'Sáng 22/7, Công ty Xổ số Điện toán Việt Nam (Vietlott) đã tổ chức lễ trao thưởng cho 2 tỷ phú Jackpot trúng giải trong 2 kỳ quay số liên tiếp là 748 và 749.', 'Chủ nhân giải thưởng Vietlott hơn 205 tỷ đồng cho biết dãy số trúng thưởng của tấm vé may mắn được ông mua dựa trên ngày sinh nhật của vợ.', '', 'DEACTIVE', '', '', 'Trúng độc đắc 205 tỷ đồng khi mua Vietlott theo sinh nhật vợ', 6),
(4, 'Tổng hợp', 1, 'Content', '', 'Cơ quan chức năng vừa triệt phá đường dây cho vay lãi nặng quy mô lớn qua ứng dụng điện thoại di động hoạt động xuyên biên giới vào Việt Nam. Đã có khoảng 159.000 khách hàng vay qua các ứng dụng với tổng số tiền vay là hơn 1.800 tỷ đồng.', '', 'DEACTIVE', '', '', 'Phá đường dây cho vay lãi nặng hơn 1.800 tỷ đồng: Các đối tượng \"khủng bố tinh thần\" con nợ thế nào?', 10),
(5, 'Tổng hợp', 1, '', 'Content', 'Nhà thi đấu Phan Đình Phùng (quận 3, TP.HCM) từng là nơi thu hút người dân và vận động viên đến luyện tập, thi đấu, hiện chỉ còn là bãi đất trống, cỏ mọc um tùm.', '', 'DELETED', '', '', 'Bình Thuận: Thần kỳ, tìm thấy thêm 5 thuyền viên sau 12 ngày lênh đênh trên biển', 2),
(6, 'Tổng hợp', 1, '', 'Content', ' Desc', '', 'DELETED', '', '', 'Hello', 1),
(7, 'Hungnn', 1, ' 5SUPERHERO has nearly 100 pedagogical trainers and key educational administrators participating in national level teacher training and retraining activities, building and developing educational curricula, writing curricula and documents Other services for professional training, implementation of many topics, projects and tasks at ministerial and branch levels in various fields. of science in general, and especially in science education.', ' <p>In the context of the 4.0 revolution and the overhaul of general education, despite facing many challenges of the industry, the university staff and lecturers have overcome difficulties, been dedicated and responsible to the profession, and have constantly innovated and developed. The spirit of innovation and creativity is spread throughout each lecture, where many active teaching methods are applied to inspire the love of teaching to students. The lecturers also spread their passion for the profession to the general teaching community in the Northern Midland and Mountainous provinces. Where they orient and lead general teachers in integrated teaching, STEM education development and local program development.A team of qualified, dedicated, responsible and highly skilled professionals is a condition to ensure the quality of educational and training activities of the University, and at the same time to ensure that 5SUPERHERO\'s learners meet the requirements of human resources. both high quality and global quality. With more than 1 year of establishment and development, going through many difficult and arduous periods, the school\'s teachers have shown a sense of responsibility, dedication, and dedication to the profession through teaching activities, creating the most favorable conditions for generations of adult students.</p>', ' <p>5SUPERHERO\'s human resources are well-trained at major training centers and prestigious research institutes in the country or abroad, so they have solid expertise to keep up with development trends in the region and countries around the world.  Currently, specialized human resources are concentrated into focused research groups including 5 specialized research groups in the field of educational science As of March 2021, 5SUPERHERO has 475 officers and employees. Of these, 320 teachers are directly involved in teaching, over 72% have doctorate degrees and about 20% are studying graduate degrees at home and abroad. More than 40 teachers have attained the titles of Professor and Associate Professor. The university is overseas, striving by 2025 to have over 90% of the lecturers having doctoral degrees, about 20% of the lecturers having been trained.</p>', 'https://upload.wikimedia.org/wikipedia/commons/thumb/b/bd/Golden_tabby_and_white_kitten_n01.jpg/300px-Golden_tabby_and_white_kitten_n01.jpg', 'ACTIVE', 'A team of qualified lecturers, experienced in teaching and training the best students in the industry, ensures you a solid and sure source of knowledge.', '', 'Qualified and reputable teachers', 9600),
(8, 'Hungnn', 1, ' 5SUPERHERO has nearly 100 pedagogical trainers and key educational administrators participating in national level teacher training and retraining activities, building and developing educational curricula, writing curricula and documents Other services for professional training, implementation of many topics, projects and tasks at ministerial and branch levels in various fields. of science in general, and especially in science education.', ' <p>In the context of the 4.0 revolution and the overhaul of general education, despite facing many challenges of the industry, the university staff and lecturers have overcome difficulties, been dedicated and responsible to the profession, and have constantly innovated and developed. The spirit of innovation and creativity is spread throughout each lecture, where many active teaching methods are applied to inspire the love of teaching to students. The lecturers also spread their passion for the profession to the general teaching community in the Northern Midland and Mountainous provinces. Where they orient and lead general teachers in integrated teaching, STEM education development and local program development.A team of qualified, dedicated, responsible and highly skilled professionals is a condition to ensure the quality of educational and training activities of the University, and at the same time to ensure that 5SUPERHERO\'s learners meet the requirements of human resources. both high quality and global quality. With more than 1 year of establishment and development, going through many difficult and arduous periods, the school\'s teachers have shown a sense of responsibility, dedication, and dedication to the profession through teaching activities, creating the most favorable conditions for generations of adult students.</p>', ' <p>5SUPERHERO\'s human resources are well-trained at major training centers and prestigious research institutes in the country or abroad, so they have solid expertise to keep up with development trends in the region and countries around the world.  Currently, specialized human resources are concentrated into focused research groups including 5 specialized research groups in the field of educational science As of March 2021, 5SUPERHERO has 475 officers and employees. Of these, 320 teachers are directly involved in teaching, over 72% have doctorate degrees and about 20% are studying graduate degrees at home and abroad. More than 40 teachers have attained the titles of Professor and Associate Professor. The university is overseas, striving by 2025 to have over 90% of the lecturers having doctoral degrees, about 20% of the lecturers having been trained.</p>', 'https://cdn.pixabay.com/photo/2021/01/16/10/41/cat-5921697__340.jpg', 'ACTIVE', 'A team of qualified lecturers, experienced in teaching and training the best students in the industry, ensures you a solid and sure source of knowledge.', '', 'Qualified and reputable teachers', 9600),
(9, 'Hieuhm', 1, 'Our experiments have been applied to more than 1000 students and it gives a result that is absolutely worth the wait. We assessed how educators can use technology to enhance students\' problem-solving skills,  and how digital technology and specifically mobile devices impact student learning.  students and teaching strategies for teachers.  The problem was solved and a discussion about the necessity of technology in learning led us to decide  to apply advanced scientific technologies in learning.', '<p>In this issue, we look at current trends in technologythat are impacting students and the modern educational environment.As 5SUPERHERO, we intend for readers to feel informed about the currentadvancement of technology in schools and to leave readers with questions about where technology will take us next.</p><p>We hope our readers will feel encouraged by the change taking placeand challenged to play an active role in these advancements.Ultimately, our aim is to inspire students to proactively implement new strategies, tools and ideas through the use of technology in schools and higher education institutions around the world. all over the world.</p>', '<p>In an era of constant change and development, college campuses have the opportunity to expose students to advances in technology through interaction with new information tools, platforms, and websites. In addition, educators are now required to prepare students for success in fields that will rely on technology. Essentially, today teachers are preparing students for fields that don\'t even exist yet.</p><p>For the first time, educators are in the process of teaching digital native speakers, which creates fear and anxiety for many veteran teachers. These students, often as early as kindergarten, have been exposed to technology and social media from birth. Furthermore, more than any generation in the past, students have relied on technology for entertainment,relationships, job search, and learning. This dependence and what seems to be an almost natural possibility with regard to technology presents learners with both promise and challenge.</p><p>To address the changes, this will cover a range of topics around technology in schools,including at the college and university level, and online learning. With this in mind, we took a closer look at how technology is affecting teaching, learning and career development in modern society.</p>', 'https://baokhanhhoa.vn/dataimages/202301/original/images5536305_van_dung_01_jpg.jpg', 'ACTIVE', 'Our school always brings the latest innovations of the times, bringing cutting-edge science and technology to our curricula.', '', 'The School of Advancement and Technology', 6800),
(10, 'Anhbt', 1, 'You could also spend it on your own personal development such as picking up lessons or an online course to learn a new skill! Having some extra income will also allow you to make the most of university, enabling you to go on trips overseas or have other fun experiences with friends, which are so important for mental health and a study-life balance.', '<p>Believe it or not, working outside of university can be a great break from studying and actually be quite fun. Despite having its ups and downs, work gives you the opportunity to meet new people in an exciting environment.  When it comes to choosing the right job for you, take some time to research the different types of opportunities that also suit your needs. The most common sectors that students go for are retail and hospitality due to the flexibility of the hours, as well as the potential staff discounts that come as a perk ;)</p><p>However, other options can include administration type roles at larger companies or popular brands and freelancing, which allows you to flex your creative muscles and gain practical experience doing something you are good at. </p>', ' <p>  Depending on the type of work you are doing, you will learn a wide range of different skills that you can apply in many scenarios. For example, if you work for a retail brand you like you can develop really great customer service/sales skills and consequently,  your ability to communicate well and work with a diverse group of people will also improve. Similar skills are used in the workforce to manage projects and stakeholders. Similarly, becoming a private tutor for areas you are passionate about gives you great ownership and leadership skills that are easily transferrable into the workplace.</p><p>Finding different types of work throughout university will help you stand out during the hiring process when applying for your first internship or graduate position. A lot of students graduate with a similar degree so experiences outside of university can differentiate candidates going for certain roles. This is where your work experience comes in. If you combine real professional experience, gained from the time you spent working for a company, with your degree qualification, you\'ll be a more attractive prospect than other graduates who have a degree but no practical experience. Plus you\'ll have a lot more to talk about in the interview and on your CV!</p><p>Students are generally not the most financially independent, which is why it\'s super handy to have a few extra bucks in the bank from a casual or part-time job. Whether you work 5 hours or 20 hours a week, it will help cover things like weekly shops, nights out or even some of those \'crucial\' textbooks your lecturer recommended.</p>', 'https://vcdn1-vnexpress.vnecdn.net/2023/01/16/img-9201-1673835106.jpg?w=900&h=540&q=100&dpr=1&fit=crop&s=dNBiG654pyTFvovq3Y4CbQ', 'ACTIVE', 'One of the good things about us is that we regularly hold contests every month, helping students gain confidence and expand their knowledge to the maximum.', '', 'Great experience while studying at school', 9000),
(11, 'Dainq', 1, 'String', 'String', 'String', 'https://i.bloganchoi.com/bloganchoi.com/wp-content/uploads/2019/10/meo-anh-long-ngan-canh-1-696x522.jpg?fit=700%2C20000&quality=95&ssl=1', 'ACTIVE', 'More than 80% of our entrance students are always at a low level, but over the years, they are 100% retrained, raising the output level to 95%, having a job right after studying at our school.', '', 'Guaranteed low input quality, high output', 10000),
(12, 'Kefaker', 1, 'String', 'String', 'String', 'https://daohieu.com/wp-content/uploads/2020/05/meo-con.jpg', 'ACTIVE', 'One of the good things about us is that we regularly hold contests every month, helping students gain confidence and expand their knowledge to the maximum.', '', 'There are attractive, attractive contests', 10000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `news_categories`
--

CREATE TABLE `news_categories` (
  `news_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `roles`
--

CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `name` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'USER'),
(2, 'MODERATOR'),
(3, 'ADMIN'),
(4, 'TEACHER'),
(5, 'STUDENT');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `avt` longtext DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `referral_code` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `verified` bit(1) NOT NULL,
  `verify_code` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `address`, `avt`, `birthday`, `email`, `first_name`, `gender`, `last_name`, `password`, `phone_number`, `referral_code`, `status`, `username`, `verified`, `verify_code`) VALUES
(1, 'Ha Noi', 'https://i.pinimg.com/564x/b0/54/4d/b0544da5a86e80ad3b9edca069f580cb.jpg', NULL, 'dainq@gmail.com', 'Dai', 'Male', 'Ngo', '$2a$10$Ts40J40snkYGxaVSQ5d6lOIFr9UdGyNJOZwZkZK8NuasFipU/yKhu', '0986886886', '19fb1461', 'ACTIVE', 'dainq', b'1', NULL),
(2, 'Hai Phong', 'https://img.meta.com.vn/Data/image/2021/09/21/anh-meo-con-2.jpg', NULL, 'user@gmail.com', 'User', 'Female', 'New', '$2a$10$Ts40J40snkYGxaVSQ5d6lOIFr9UdGyNJOZwZkZK8NuasFipU/yKhu', '0838338888', NULL, 'ACTIVE', 'user', b'1', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 3),
(2, 1);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `admissions`
--
ALTER TABLE `admissions`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `attendancereport`
--
ALTER TABLE `attendancereport`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `classroom`
--
ALTER TABLE `classroom`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `classroom_faculties`
--
ALTER TABLE `classroom_faculties`
  ADD PRIMARY KEY (`classroom_id`,`faculty_id`),
  ADD KEY `FK332vmjigg2sk74gtq4rrmktgl` (`faculty_id`);

--
-- Chỉ mục cho bảng `classroom_users`
--
ALTER TABLE `classroom_users`
  ADD PRIMARY KEY (`classroom_id`,`users_id`),
  ADD KEY `FK599e4wm0n3crl4ox6cr98obd7` (`users_id`);

--
-- Chỉ mục cho bảng `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `course_register`
--
ALTER TABLE `course_register`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `events`
--
ALTER TABLE `events`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKe2kgh11mh6ew5s4yg9akekfql` (`location_id`);

--
-- Chỉ mục cho bảng `faculty`
--
ALTER TABLE `faculty`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `feedbacks`
--
ALTER TABLE `feedbacks`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `location`
--
ALTER TABLE `location`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `managers`
--
ALTER TABLE `managers`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `markreport`
--
ALTER TABLE `markreport`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `news`
--
ALTER TABLE `news`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `news_categories`
--
ALTER TABLE `news_categories`
  ADD PRIMARY KEY (`news_id`,`category_id`),
  ADD KEY `FKd06p8hg58jfn2gvt9pw7eb8hy` (`category_id`);

--
-- Chỉ mục cho bảng `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKej3jidxlte0r8flpavhiso3g6` (`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `admissions`
--
ALTER TABLE `admissions`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `attendancereport`
--
ALTER TABLE `attendancereport`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `categories`
--
ALTER TABLE `categories`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `classroom`
--
ALTER TABLE `classroom`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `course`
--
ALTER TABLE `course`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `course_register`
--
ALTER TABLE `course_register`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT cho bảng `events`
--
ALTER TABLE `events`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `faculty`
--
ALTER TABLE `faculty`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `feedbacks`
--
ALTER TABLE `feedbacks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `managers`
--
ALTER TABLE `managers`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `markreport`
--
ALTER TABLE `markreport`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `news`
--
ALTER TABLE `news`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT cho bảng `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
