package org.helpers.enums;

public enum Routes {
    // LOGIN ENDPOINTS
    GetLoginEndpoint("/v1/Login/login"),
    // PRODUCTIVE HOUR BY USER
    GetProductiveHoursEndpoint("/v1/Dashboard/GetTotalProductiveHoursByUser"),
    // User Activities
    GetUserActivityEndpoint("/v1/ActivityTracker/GetUserActivities"),
    GetUserTimeTrackerEndPoint("/v1/Calculate/DownloadTimeTrackerReport"),
    GetScreenActivity("/v1/CaptureWindows/GetScreenActivity"),
    GetSystemActivityEndPoint("/v1/CaptureWindows/GetScreenActivity"),
    // Productivity vs IDle end points
    GetTotalProductiveHoursByUserEndPoint("/v1/TotalProductiveHours/GetTotalProductiveHoursByUser"),
    // Web and Apps end points
    GetWebAndAppsEndPoints("/v1/WebAndAppReport/WebUsageForUser"),
    // Attendance end points
    GetAttendanceEndPoint("/v1/AttendanceReport/GetAttendaceReportByReportManager"),
    GetAttendaceByUserEndPoint("/v1/AttendanceReport/GetAttendaceByUser"),
    GetClaimTimeForUser("/v1/TimeClaim/GetClaimTimeForUser"),
    GetUserTimeToClaim("/v1/TimeClaim/GetUserTimeToClaim"),
    GetNewTimeTrackerEndPoint("/v1/Calculate/GetUsersBifurcationTotalTime"),
    GetTotalTimeInSecondsTilesData("/v1/Calculate/TotalTimeInSeconds"),
    GetYearlyHolidaysByOrgId("/v1/HolidayCalendar/GetYearlyHolidaysByOrgId"),
    GetScreenshotsEndPoint("/v1/CloudStorageScreenshots/GetScreenshots");

    private String resource;

    Routes(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}

