Feature: Web User Test Cases

  @UserTest_01
  Scenario Outline: Verify user should not be able to login with invalid credentials
    Given User should be on login screen.
    When  User fill the invalid "<email>"email details on email field.
    When  User fill the invalid "<password>"password details on password field.
    And   User click on submit button.
    Then  The error validation messages on email field display successfully.
    And  User clear the text from email id and password field.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify User should be able to login with valid credentials
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    Then Verify User should be able to open Dashboard page after login.
    Then Verify user should be seen all options on left side.
    Then Verify all 5 tiles are showing on the Dashboard page.
    Then Verify user should able to see current date on the screen.
    Then Verify user should able to see all data's of current day on all 5 tiles.
    Then Verify Hours for current day on Time section.
    Then Verify Productive vs Idle section present on dashboard.
    Then Verify user should be able to see the past attendance from Attendance calendar.
    Then Verify user should be able to see the web and app time for current week for Website, Application.

    Examples: | email | password |
    | email | password |

  Scenario Outline:Verify after clicking on the Time Tracker from dropdown of report, Time tracker page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on time tracker option.
    Then Verify user can see the components in the time tracker screen.
    And User can see the todays date on tracker screen.
    Then Verify user can see the data in time for a particular day.
    Then Verify user is able to see the data for a date range.
    Then Verify range of data should be verify with api's.
    Then Verify user's Active time, Idle Time and Total Time Data on time tracker page should be correct.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on System Activity report tab from report dropdown,System Activity page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on the system activity tab.
    And User get the date from system activity screen.
    Then Verify the user can see component in the system activity screen.
    Then Verify user should able to see current date all used url & App when loading the page first time.
    Then Verify that first activity of the user should have start time as day start time.
    Then Verify that Time spent on any activity should be the difference between next Activity start time and that particular Activity start time.
    Then Verify user can able to change the date to see activities of that day.
    Then Verify activities of the past day with api's.
    Then Verify user able to change the next and previous button successfully
    Then Verify user should be able to select Row per pages 25.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on Activity summery tab from report dropdown,Activity summery page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on the activity summery tab.
    Then Verify the user can see component in the activity summery.
    Then Verify the user activity summery data mapping with api's.
    Then Verify the user table activity summery mapping with api's.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on Productivity vs Idle tab from report dropdown,Productivity vs Idle page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on the productivity vs idle tab.
    Then Verify the user can see component in the productivity vs idle screen.
    Then Verify the user productivity vs idle data mapping with api's.
    Then Verify Idle away and total Time on this page is same as Idle Time on Time tracker Page !
    And User click on the productive green graph.
    Then Verify the popup with productive time column open.
    Then Verify the productive column entries.
    Then User click on cross icon from popup screen.
    And User click on the unproductive red graph.
    Then Verify the popup with unproductive time column open.
    Then Verify the unproductive column entries.
    Then User click on cross icon from popup screen.
    And User can select past date from date picker.
    Then Verify the user can check the productivity vs idle report for past date.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on web and apps tab from report dropdown,web and apps page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on the web and apps tab.
    Then Verify all the components on the web and apps screen.
    Then verify websites data from api's integration.
    And User click on application tab.
    Then Verify all the components on the application screen.
    Then Verify application data from api's integration.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on attendance tab from report dropdown,attendance page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on the attendance tab.
    Then Verify all the components on the attendance screen.
    Then Verify the user details on attendance screen from api's.
    Then Verify current date in out total time from api's.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on timeline tab from report dropdown,timeline page should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    Then User click on submit button.
    And User click on the report tab.
    And User click on the timeline tab.
    Then Verify all the components on the timeline screen.
    Then Verify the user details on timeline screen from api's.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on screenshot tab from screenshot should get open with time stamp.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    And User click on submit button.
    And User click on Screenshot tab.
    And User select the particular day and month using calender.
    Then Verify all the components on the screenshot screen.
    Then Verify the screen shots and screenshot time with api's.
    Then Verify user able to maximize the screenshot image.
    Then Verify user should be able to minimize the screenshot images.
    Then Verify user can start and stop the slideshow of the screenshot.
    Then Verify user should able to change the date.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on time claim tab the claim screen should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    And User click on submit button.
    And User click on time claim tab.
    And User click on claim option sub tab.
    Then Verify all the components on the time claim screen.
    And User select the particular day and month using calender on the time claim screen.
    Then Verify the time claim activity times with api's.
    Then Verify the time claims record with api's.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on time claim status tab the Status screen should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    And User click on submit button.
    And User click on time claim tab.
    And User click on status option sub tab.
    Then Verify all the components on the time claim status screen.
    And User select the particular day and month using calender on claim status.
    Then Verify the time claims status record and status with api's.
    Examples: | email | password |
    | email | password |

  Scenario Outline: Verify when clicking on holiday tab the holiday screen should get open.
    Given User should be on login screen.
    When User fill the valid "<email>"email details on email field.
    When User fill the valid "<password>"password details on password field.
    And User click on submit button.
    And User click on holiday tab.
    Then Verify all UI components on the holiday screen.
    Then Verify the holiday data with holidays api's.
    Examples: | email | password |
    | email | password |