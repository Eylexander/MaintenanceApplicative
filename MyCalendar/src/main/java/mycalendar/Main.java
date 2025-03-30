package mycalendar;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import mycalendar.calendar.Calendar;
import mycalendar.calendar.CalendarManager;
import mycalendar.calendar.CalendarTitle;
import mycalendar.events.*;
import mycalendar.person.Person;
import mycalendar.service.AuthService;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static CalendarManager calendarManager;
    private static Person currentUser = null;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        initializeCalendarManager();
        runAuthLoop();
        scanner.close();
        System.out.println("Exiting MyCalendar. Goodbye!");
    }

    private static void initializeCalendarManager() {
        Calendar loadedCalendar = null;
        try {
            Person placeholderOwner = new Person("system", "system");
            CalendarManager tempManager = new CalendarManager(
                    new Calendar(new CalendarTitle("Default Temporary"), placeholderOwner));
            tempManager.loadCalendars();
            loadedCalendar = tempManager.getActiveCalendar();
        } catch (Exception e) {
            System.err.println("Error loading calendar: " + e.getMessage() + ". Creating a new default calendar.");
        }

        if (loadedCalendar == null) {
            Person placeholderOwner = new Person("system", "system");
            loadedCalendar = new Calendar(new CalendarTitle("My Default Calendar"), placeholderOwner);
        }
        calendarManager = new CalendarManager(loadedCalendar);
        calendarManager.saveCalendars();
    }

    private static void runAuthLoop() {
        while (currentUser == null) {
            printAuthMenu();
            String choice = scanner.nextLine();
            handleAuthChoice(choice);
        }
    }

    private static void printAuthMenu() {
        System.out.println("\n--- Authentication ---");
        System.out.println("1. Login");
        System.out.println("2. Signup");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    private static void handleAuthChoice(String choice) {
        switch (choice) {
            case "1":
                handleLogin();
                break;
            case "2":
                handleSignup();
                break;
            case "3":
                System.out.println("Exiting...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        if (currentUser != null) {
            runMainMenuLoop();
        }
    }

    private static void handleLogin() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Optional<Person> userOpt = authService.login(username, password);
        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println("Login successful. Welcome " + currentUser.getName() + "!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }

    private static void handleSignup() {
        System.out.print("Choose a username: ");
        String username = scanner.nextLine();
        System.out.print("Choose a password: ");
        String password = scanner.nextLine();

        Optional<Person> userOpt = authService.signup(username, password);
        if (userOpt.isPresent()) {
            currentUser = userOpt.get();
            System.out.println("Signup successful. Welcome " + currentUser.getName() + "!");

            calendarManager.addCalendar(new Calendar(new CalendarTitle(username + "'s Calendar"), currentUser));
            calendarManager.saveCalendars();
        } else {
            System.out.println("Signup failed. Username might be taken.");
        }
    }

    private static void runMainMenuLoop() {
        String choice;
        do {
            printMainMenu();
            choice = scanner.nextLine();
            handleMainMenuChoice(choice);
        } while (!choice.equals("6"));
    }

    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("Welcome, " + currentUser.getName() + "!");
        System.out.println("1. View All My Events");
        System.out.println("2. View My Events by Month");
        System.out.println("3. View My Events by Week");
        System.out.println("4. View My Events by Day");
        System.out.println("5. Create New Event");
        System.out.println("6. Logout");
        System.out.print("Choose an option: ");
    }

    private static void handleMainMenuChoice(String choice) {
        switch (choice) {
            case "1":
                viewAllEvents();
                break;
            case "2":
                viewEventsByMonth();
                break;
            case "3":
                viewEventsByWeek();
                break;
            case "4":
                viewEventsByDay();
                break;
            case "5":
                createEvent();
                break;
            case "6":
                handleLogout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void viewAllEvents() {
        System.out.println("\n--- All Your Events ---");
        List<Event> userEvents = calendarManager.getActiveCalendarEvents().stream()
                .filter(event -> event.getProprietaire() != null
                        && event.getProprietaire().getName().equals(currentUser.getName()))
                .collect(Collectors.toList());

        if (userEvents.isEmpty()) {
            System.out.println("You have no events.");
        } else {
            userEvents.forEach(event -> System.out.println(event.description()));
        }
    }

    private static void viewEventsByPeriod(EventDate start, EventDate end, String periodDescription) {
        System.out.println("\n--- Your Events for " + periodDescription + " ---");
        List<Event> periodEvents = calendarManager.eventsDansPeriode(start, end).stream()
                .filter(event -> event.getProprietaire() != null
                        && event.getProprietaire().getName().equals(currentUser.getName()))
                .collect(Collectors.toList());

        if (periodEvents.isEmpty()) {
            System.out.println("No events found for this period.");
        } else {
            periodEvents.forEach(event -> System.out.println(event.description()));
        }
    }

    private static void viewEventsByMonth() {
        System.out.print("Enter year (YYYY): ");
        int year = readIntInput();
        System.out.print("Enter month (1-12): ");
        int month = readIntInput();

        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            LocalDateTime startOfMonth = yearMonth.atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = yearMonth.atEndOfMonth().atTime(23, 59, 59);
            viewEventsByPeriod(new EventDate(startOfMonth), new EventDate(endOfMonth),
                    yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        } catch (DateTimeException e) {
            System.out.println("Invalid year or month.");
        }
    }

    private static void viewEventsByWeek() {
        System.out.print("Enter year (YYYY): ");
        int year = readIntInput();
        System.out.print("Enter week number (1-53): ");
        int week = readIntInput();

        try {
            WeekFields weekFields = WeekFields.ISO;
            LocalDateTime firstDayOfYear = LocalDateTime.of(year, Month.JANUARY, 1, 0, 0);
            LocalDateTime startOfWeek = firstDayOfYear
                    .with(weekFields.weekOfYear(), week)
                    .with(weekFields.dayOfWeek(), 1);

            if (startOfWeek.getYear() < year) {
                startOfWeek = firstDayOfYear.with(weekFields.dayOfWeek(), 1);
            } else if (startOfWeek.getYear() > year && week == 1) {
                startOfWeek = firstDayOfYear.with(weekFields.dayOfWeek(), 1);
            }

            LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59);

            String weekDesc = String.format("Week %d of %d (starting %s)", week, year,
                    startOfWeek.format(DATE_FORMATTER));
            viewEventsByPeriod(new EventDate(startOfWeek), new EventDate(endOfWeek), weekDesc);

        } catch (IllegalArgumentException | DateTimeException e) {
            System.out.println("Invalid year or week number. " + e.getMessage());
        }
    }

    private static void viewEventsByDay() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateStr = scanner.nextLine();
        try {
            LocalDateTime startOfDay = LocalDateTime.parse(dateStr + " 00:00", DATE_TIME_FORMATTER);
            LocalDateTime endOfDay = LocalDateTime.parse(dateStr + " 23:59", DATE_TIME_FORMATTER);
            viewEventsByPeriod(new EventDate(startOfDay), new EventDate(endOfDay), dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        }
    }

    private static void createEvent() {
        System.out.println("\n--- Create New Event ---");
        System.out.println("Choose event type:");
        System.out.println("1. Birthday");
        System.out.println("2. Periodic");
        System.out.println("3. Personal Meeting");
        System.out.println("4. Reunion");
        int eventType = readIntInput();
        System.out.print("Enter event title: ");
        String titleStr = scanner.nextLine();
        System.out.print("Enter start date and time (YYYY-MM-DD HH:mm): ");
        String dateTimeStr = scanner.nextLine();
        int durationMinutes = 0;
        if (eventType != 1) {
            System.out.print("Enter event duration in minutes: ");
            durationMinutes = readIntInput();
        }
        EventDuration duration = new EventDuration(durationMinutes);

        try {
            EventTitle title = new EventTitle(titleStr);
            LocalDateTime startDateTime = LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
            EventDate startDate = new EventDate(startDateTime);
            Event newEvent;

            switch (eventType) {
                case 1:
                    newEvent = new Birthday(title, currentUser, startDate);
                    break;
                case 2:
                    System.out.print("Enter event frequency in days: ");
                    String frequencyStr = scanner.nextLine();
                    int frequency = Integer.parseInt(frequencyStr);
                    EventFrequency eventFrequency = new EventFrequency(frequency);
                    newEvent = new PeriodicEvent(title, currentUser, startDate, duration,
                            eventFrequency);
                    break;
                case 3:
                    newEvent = new PersonalMeeting(title, currentUser, startDate, duration);
                    break;
                case 4:
                    System.out.print("Enter location: ");
                    String locationStr = scanner.nextLine();
                    EventLocation location = new EventLocation(locationStr);
                    System.out.print("Enter participants (comma-separated): ");
                    String participantsStr = scanner.nextLine();
EventParticipants participants = new EventParticipants(List.of(participantsStr.split(",")));
newEvent = new Reunion(title, currentUser, startDate, duration, location, participants);
                    break;
                default:
                    System.out.println("Invalid event type.");
                    return;
            }

            calendarManager.ajouterEvent(newEvent);
            calendarManager.saveCalendars();
            System.out.println("Event '" + title.getTitle() + "' created successfully.");

        } catch (DateTimeParseException e) {
            System.out.println("Invalid date/time format. Please use YYYY-MM-DD HH:mm.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }

    private static void handleLogout() {
        System.out.println("Logging out " + currentUser.getName() + "...");
        currentUser = null;
    }

    private static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter an integer: ");
            }
        }
    }
}
