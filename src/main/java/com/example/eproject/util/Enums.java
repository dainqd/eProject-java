package com.example.eproject.util;

public class Enums {
    public static enum Role {
        USER, MODERATOR, ADMIN, TEACHER, STUDENT
    }

    public static enum AccountStatus {
        ACTIVE, DELETED, DEACTIVE, BLOCKED
    }

    public static enum NewsStatus {
        ACTIVE, DELETED, DEACTIVE
    }

    public static enum CategoryType {
        HOMEPAGE, POLITICAL, SOCIAL, ECONOMY, HEALTH, EDUCATION, LAW, SPORT, WORLD, OTHER
    }

    public static enum CategoryStatus {
        ACTIVE, DELETED
    }

    public static enum FeedbackStatus {
        ACTIVE, DELETED, DEACTIVE
    }

    public static enum AdmissionsStatus {
        PENDING, APPROVED, DELETED
    }

    public static enum CourseStatus {
        ACTIVE, DEACTIVE, PREACTIVE, FULLY, DELETED
    }

    public static enum CourseRegisterStatus {
        APPROVED, PENDING, WAITCONFIRM, REFUSE, COMPLETE, DELETED
    }

    public static enum EventsStatus {
        ACTIVE, DELETED, DEACTIVE
    }

    public static enum FacultyStatus {
        ACTIVE, DELETED, DEACTIVE
    }

    public static enum ClassroomStatus {
        ACTIVE, DELETED, DEACTIVE, COMPLETED
    }

    public static enum PointStatus {
        PASS, FAIL, NONE, NOTSTART
    }

    public static enum MarkReportStatus {
        ACTIVE, DELETED, BLOCK, COMPLETED
    }

    public static enum Condition {
        PRESENT, ABSENT, FUTURE
    }

    public static enum AttendanceStatus {
        ACTIVE, DELETED, BLOCK, COMPLETED
    }

    public static enum ManagerStatus {
        ACTIVE, DELETED, BLOCK
    }
}
