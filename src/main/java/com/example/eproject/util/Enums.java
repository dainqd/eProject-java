package com.example.eproject.util;

public class Enums {
	public static enum Role {
		USER, MODERATOR, ADMIN
	}

	public static enum AccountStatus{
		ACTIVE, DELETED, DEACTIVE, BLOCKED
	}

	public static enum NewsStatus {
		ACTIVE, DELETED, DEACTIVE
	}
	public static enum CategoryType {
		HOMEPAGE, POLITICAL, SOCIAL, ECONOMY, HEALTH, EDUCATION, LAW, SPORT, WORLD
	}
	public static enum CategoryStatus {
		ACTIVE, DELETED
	}
	public static enum FeedbackStatus{
		ACTIVE, DELETED, DEACTIVE
	}

	public static enum AdmissionsStatus{
		PENDING, APPROVED, DELETED
	}

	public static enum CourseStatus{
		ACTIVE, DEACTIVE, PREACTIVE, FULLY,  DELETED
	}
}
