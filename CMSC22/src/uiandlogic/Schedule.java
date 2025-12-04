package uiandlogic;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.*;

public class Schedule {
    
    private GridPane grid;
    private double scale;
    private User user;
    
    private static final String[] DAYS = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};
    private static final int ROWS_PER_HOUR = 2; 
    private static final int START_HOUR_INT = 7; 
    private static final int END_HOUR_INT = 19;  
    private static final int TOTAL_ROWS = (END_HOUR_INT - START_HOUR_INT) * ROWS_PER_HOUR; 
    
    private static final double CELL_HEIGHT = 20; 
    private static final double COL_WIDTH = 110; 
    private static final double TIME_COL_WIDTH = 85; 
    private static final double HEADER_HEIGHT = 30;

    private List<StackPane> activeBlocks;  
    
    private static final String[] COURSE_COLORS = {
        "#2F4156", "#5D7B93"
    };
    
    private Map<String, String> courseColors;
    private int colorIndex = 0;
    
    public Schedule(User user, double scale) {
        this.user = user;
        this.scale = scale;
        this.activeBlocks = new ArrayList<>();
        this.courseColors = new HashMap<>();
        buildGrid();
        loadUserSchedule();
    }
    
    public void setUser(User user) {
        this.user = user;
        refreshSchedule();
    }
    
    // refresh schedule
    public void refreshSchedule() {
        clearSchedule();
        if (user != null && user.getPlannedCourses() != null) {
            for (Section section : user.getPlannedCourses()) {
                addCourseBlock(section);
            }
        }
        grid.requestLayout(); 
    }
    
    // clears schedule
    public void clearSchedule() {
        for (StackPane block : activeBlocks) {
            grid.getChildren().remove(block);
        }
        activeBlocks.clear();
    }
    
    // create grid structure with headers and empty cells
    private void buildGrid() {
        grid = new GridPane();
        grid.setStyle("-fx-border-color: #DDDDDD; -fx-border-width: 1; -fx-background-color: white;");
        grid.setAlignment(Pos.TOP_LEFT);
        
        ColumnConstraints timeCol = new ColumnConstraints(TIME_COL_WIDTH * scale);
        timeCol.setHalignment(HPos.CENTER);
        grid.getColumnConstraints().add(timeCol);
        
        // day cols
        for (int i = 0; i < DAYS.length; i++) {
            ColumnConstraints dayCol = new ColumnConstraints(COL_WIDTH * scale);
            dayCol.setHgrow(Priority.ALWAYS);
            grid.getColumnConstraints().add(dayCol);
        }

        // header row constraint
        RowConstraints headerRow = new RowConstraints(HEADER_HEIGHT * scale);
        grid.getRowConstraints().add(headerRow);
        
        for (int i = 0; i < TOTAL_ROWS; i++) {
            RowConstraints row = new RowConstraints(CELL_HEIGHT * scale); 
            grid.getRowConstraints().add(row);
        }
        
        // create the header row
        for (int col = 0; col < DAYS.length; col++) {
            Label dayLabel = new Label(DAYS[col]);
            dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 10px; -fx-text-fill: #555555;");
            // put the label inside of the stackpane
            StackPane cell = new StackPane(dayLabel);
            cell.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #EEEEEE; -fx-border-width: 0 1 1 0;");
            grid.add(cell, col + 1, 0);
        }
        
        // for every row add label
        for (int row = 0; row < TOTAL_ROWS; row++) {
            int startMinTotal = (START_HOUR_INT * 60) + (row * 30);
            int endMinTotal = startMinTotal + 30;
            Label timeLabel = new Label(formatInterval(startMinTotal, endMinTotal));
            timeLabel.setStyle("-fx-font-size: 9px; -fx-text-fill: #888888;");
            timeLabel.setTextAlignment(TextAlignment.CENTER);
            // add label to every row
            grid.add(timeLabel, 0, row + 1);
            
            // create the grid cells
            for (int col = 0; col < DAYS.length; col++) {
                Region cell = new Region();
                // creates a zebra style to see better
                String borderStyle = (row % 2 == 0) 
                    ? "-fx-border-color: #E0E0E0; -fx-border-width: 0 1 1 0; -fx-border-style: solid;"
                    : "-fx-border-color: #E0E0E0; -fx-border-width: 0 1 1 0; -fx-border-style: dashed;";
                cell.setStyle(borderStyle);
                grid.add(cell, col + 1, row + 1);
            }
        }
    }
    
    private String formatInterval(int startMin, int endMin) {
        return formatTime(startMin) + " - " + formatTime(endMin);
    }

    private String formatTime(int totalMinutes) {
        int h = totalMinutes / 60;
        int m = totalMinutes % 60;
        if (h > 12) h -= 12;
        if (h == 0) h = 12; 
        return String.format("%d:%02d", h, m);
    }
    
    private void loadUserSchedule() {
        if (user == null || user.getPlannedCourses() == null) return;
        for (Section section : user.getPlannedCourses()) {
            addCourseBlock(section);
        }
    }
    
    // given a section, make a block to be put inside the schedule
    public void addCourseBlock(Section section) {
        List<Integer> dayIndices = parseDays(section.getDay());
        
        // get index given the day of section
        if (dayIndices.isEmpty()) return;
        
        // get up to where the block is gonna be
        TimeSlot slot = parseTimeRange(section.getTime());
        if (slot == null) return;

        for (int dayCol : dayIndices) {
            StackPane block = createBlockVisual(section);
            
            // col = dayCol + 1 since 0 is time label
            // row = slow.startRow + 1 since 0 is header
            
            grid.add(block, dayCol + 1, slot.startRow + 1, 1, slot.duration);
            // add to list for easy removal later
            activeBlocks.add(block); 
        }
    }
    
    // creats the actual block
    private StackPane createBlockVisual(Section section) {
        StackPane block = new StackPane();
        String color = getColorForCourse(section.getCourseCode());
        
        block.setStyle("-fx-background-color: " + color + "CC;" + 
                       "-fx-border-color: " + color + ";" +
                       "-fx-border-width: 0 0 0 3;" + 
                       "-fx-background-radius: 2;");
        
        VBox content = new VBox(0);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new javafx.geometry.Insets(2));
        
        Label code = new Label(section.getCourseCode());
        code.setStyle("-fx-font-weight: bold; -fx-font-size: 9px; -fx-text-fill: white;");
        code.setWrapText(true);
        code.setTextAlignment(TextAlignment.CENTER);
        
        Label room = new Label(section.getRoom()); 
        room.setStyle("-fx-font-size: 8px; -fx-text-fill: white;");
        
        content.getChildren().addAll(code, room);
        block.getChildren().add(content);
        return block;
    }

    // function to compute which indices of the grid would be blocked
    private TimeSlot parseTimeRange(String timeStr) {
        try {
        	 // remove  space from time and seperate from start to end 
            String[] parts = timeStr.replace(" ", "").split("-");
            if (parts.length != 2) return null;

            int startMinRaw = convertToMinutes(parts[0]);
            int endMinRaw = convertToMinutes(parts[1]);

            // checks if end time is smaller, when end is pm*
            if (endMinRaw < startMinRaw) endMinRaw += 12 * 60;
            
            // case where if started in pm  or 12, end cannot be am
            if (startMinRaw >= 12 * 60 && endMinRaw < startMinRaw) endMinRaw += 12 * 60;
            
            // case where the end is less than start like 4-7 since 7 will be seen as 420 mins, and 16:00 will be seen as 960
            // add minutes to endMinRaw
            if (endMinRaw < startMinRaw) endMinRaw += 12 * 60;

            // grid starts at 7 
            int gridStartMin = START_HOUR_INT * 60;
            
            // makes sure time is correct for calculating which indices
            if (startMinRaw < gridStartMin) startMinRaw += 12 * 60;
            if (endMinRaw < gridStartMin) endMinRaw += 12 * 60;

            int startRow = (startMinRaw - gridStartMin) / 30;
            int endRow = (endMinRaw - gridStartMin) / 30;
            int duration = endRow - startRow;
            
            if (startRow < 0) startRow = 0; 
            if (startRow >= TOTAL_ROWS) return null;
            if (duration <= 0) duration = 1;

            return new TimeSlot(startRow, duration);

        } catch (Exception e) {
            System.err.println("Time parse error: " + timeStr);
            return null;
        }
    }

    // converts time to military time from hour:minutes
    private int convertToMinutes(String time) {
        String[] parts = time.split(":");
        int h = Integer.parseInt(parts[0]);
        int m = (parts.length > 1) ? Integer.parseInt(parts[1]) : 0;
        if (h >= 1 && h <= 6) h += 12;
        return h * 60 + m;
    }

    private List<Integer> parseDays(String dayStr) {
        List<Integer> indices = new ArrayList<>();
        String clean = dayStr.toUpperCase().replace(" ", "");
        
        if (clean.contains("TTH")) {
            indices.add(1); indices.add(3); return indices;
        }
        if (clean.equals("WF")) {
            indices.add(2); indices.add(4); return indices;
        }

        if (clean.contains("M")) indices.add(0);
        if (clean.contains("T") && !clean.contains("TH")) indices.add(1); 
        if (clean.contains("W")) indices.add(2);
        if (clean.contains("TH")) indices.add(3);
        if (clean.contains("F")) indices.add(4);
        if (clean.contains("Sat")) indices.add(5);
        return indices;
    }

    // alternate between colors
    private String getColorForCourse(String courseCode) {
        if (!courseColors.containsKey(courseCode)) {
            courseColors.put(courseCode, COURSE_COLORS[colorIndex % COURSE_COLORS.length]);
            colorIndex++;
        }
        return courseColors.get(courseCode);
    }
    
    // getters
    public ScrollPane getScrollableGrid() {
        ScrollPane sp = new ScrollPane(grid);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return sp;
    }
    
    public User getUser() { return user; }

    // helper function
    private static class TimeSlot {
        int startRow;
        int duration;
        TimeSlot(int s, int d) { startRow = s; duration = d; }
    }
}


