package src;

import src.dao.CriminalDAO;
import src.models.CriminalRecord;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PoliceRecordsSystem extends Frame {
    // Modern Color Palette
    // Brighter, more vivid color palette
    private final Color PRIMARY_BLUE = new Color(88, 86, 214); // indigo
    private final Color DARK_BLUE = new Color(28, 35, 65);
    private final Color LIGHT_BLUE = new Color(0, 150, 255);
    private final Color ACCENT_RED = new Color(236, 72, 153); // magenta-accent
    private final Color SUCCESS_GREEN = new Color(16, 185, 129);
    private final Color WARNING_ORANGE = new Color(255, 159, 67);
    private final Color PURPLE = new Color(106, 52, 255);
    private final Color TEAL = new Color(14, 165, 233);
    private final Color GOLD = new Color(250, 204, 21);

    private final Color BACKGROUND = new Color(245, 250, 255);
    private final Color CARD_WHITE = new Color(255, 255, 255);
    private final Color TEXT_DARK = new Color(33, 37, 41);
    private final Color TEXT_LIGHT = new Color(120, 130, 140);
    
    // Gradients
    private GradientPaint headerGradient;
    
    // Fonts
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 28);
    private Font subtitleFont = new Font("Segoe UI", Font.BOLD, 18);
    private Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
    private Font normalFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font boldFont = new Font("Segoe UI", Font.BOLD, 14);
    private Font smallFont = new Font("Segoe UI", Font.PLAIN, 12);
    
    // Components
    private TextField txtName, txtAge, txtCrimeType, txtAddress, txtPhone, txtSearch, txtDate;
    private TextArea txtDisplay;
    private Choice chStatus;
    private java.awt.List recordList;
    private CriminalDAO criminalDAO;
    private Button btnAdd, btnUpdate, btnDelete, btnSearch, btnShowAll, btnClear, btnRefresh;
    private Label lblTitle, lblSubtitle, lblStats;
    private Panel mainPanel, headerPanel, formPanel, buttonPanel, searchPanel, displayPanel, statsPanel;
    
    public PoliceRecordsSystem() {
        criminalDAO = new CriminalDAO();
        initializeColorfulGUI();
    }
    
    private void initializeColorfulGUI() {
        setTitle("🚨 Police Records Management System - Secure Database");
        setSize(1300, 850);
        setBackground(BACKGROUND);
        setLayout(new BorderLayout(0, 0));
        setLocationRelativeTo(null);
        
    // Initialize gradient (more vibrant)
    headerGradient = new GradientPaint(0, 0, PURPLE, getWidth(), 0, TEAL);
        
        createModernHeader();
        createMainPanel();
        createFooter();
        
        // Load initial data
        loadAllRecords();
        updateStats();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        
        setVisible(true);
    }
    
    private void createModernHeader() {
        headerPanel = new Panel() {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(headerGradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paint(g);
            }
        };
        headerPanel.setLayout(new BorderLayout());
        headerPanel.setPreferredSize(new Dimension(getWidth(), 120));
        
        // Main title
        Panel titlePanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        
        lblTitle = new Label("🚔 POLICE RECORDS MANAGEMENT SYSTEM");
        lblTitle.setFont(titleFont);
        lblTitle.setForeground(Color.WHITE);
        
        lblSubtitle = new Label("Secure Criminal Database & Analytics Platform");
        lblSubtitle.setFont(subtitleFont);
        lblSubtitle.setForeground(new Color(200, 200, 200));
        
        titlePanel.add(lblTitle);
        
        Panel subtitlePanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        subtitlePanel.setOpaque(false);
        subtitlePanel.add(lblSubtitle);
        
        Panel centerPanel = new Panel(new GridLayout(2, 1));
        centerPanel.setOpaque(false);
        centerPanel.add(titlePanel);
        centerPanel.add(subtitlePanel);
        
        // Stats with colorful badges
        lblStats = new Label("🔍 Loading system statistics...");
        lblStats.setFont(boldFont);
        lblStats.setForeground(Color.WHITE);
        lblStats.setAlignment(Label.RIGHT);
        
        headerPanel.add(centerPanel, BorderLayout.CENTER);
        
        Panel statsContainer = new Panel(new FlowLayout(FlowLayout.RIGHT));
        statsContainer.setOpaque(false);
        statsContainer.add(lblStats);
        headerPanel.add(statsContainer, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
    }
    
    private void createMainPanel() {
        mainPanel = new Panel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        createLeftPanel();
        createRightPanel();
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void createLeftPanel() {
        Panel leftPanel = new Panel(new BorderLayout(0, 20));
        leftPanel.setBackground(BACKGROUND);
        
        createFormPanel();
        createSearchPanel();
        
        leftPanel.add(formPanel, BorderLayout.NORTH);
        leftPanel.add(searchPanel, BorderLayout.CENTER);
        
        mainPanel.add(leftPanel);
    }
    
    private void createRightPanel() {
        Panel rightPanel = new Panel(new BorderLayout(0, 20));
        rightPanel.setBackground(BACKGROUND);
        
        createDisplayPanel();
        createStatsPanel();
        
        rightPanel.add(displayPanel, BorderLayout.CENTER);
        rightPanel.add(statsPanel, BorderLayout.SOUTH);
        
        mainPanel.add(rightPanel);
    }
    
    private void createFormPanel() {
        formPanel = createColorfulCard("📝 CRIMINAL RECORD FORM", TEAL, new GridLayout(8, 2, 15, 12));
        
        // Form components with colorful styling
        addColorfulFormField("👤 Full Name:", txtName = createColorfulTextField());
        addColorfulFormField("🎂 Age:", txtAge = createColorfulTextField());
        addColorfulFormField("⚖️ Crime Type:", txtCrimeType = createColorfulTextField());
        addColorfulFormField("📅 Crime Date (yyyy-mm-dd):", txtDate = createColorfulTextField());
        addColorfulFormField("🏠 Address:", txtAddress = createColorfulTextField());
        addColorfulFormField("📞 Phone:", txtPhone = createColorfulTextField());
        
        Panel statusPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statusPanel.setBackground(CARD_WHITE);
        
        Label statusLabel = new Label("🔄 Status:");
        statusLabel.setFont(boldFont);
        statusLabel.setForeground(TEXT_DARK);
        
        chStatus = new Choice();
        chStatus.setFont(normalFont);
        chStatus.setBackground(Color.WHITE);
        chStatus.setForeground(TEXT_DARK);
        chStatus.add("🚨 WANTED");
        chStatus.add("🔒 ARRESTED");
        chStatus.add("✅ RELEASED");
        chStatus.add("⏳ PENDING INVESTIGATION");
        
        statusPanel.add(statusLabel);
        statusPanel.add(Box.createHorizontalStrut(10));
        statusPanel.add(chStatus);
        
        formPanel.add(statusPanel);
        formPanel.add(new Label()); // Empty cell for alignment
        
        createColorfulButtons();
    }
    
    private void createColorfulButtons() {
        buttonPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(CARD_WHITE);
        btnAdd = createGradientButton("➕ ADD RECORD", SUCCESS_GREEN);
        btnUpdate = createGradientButton("✏️ UPDATE", LIGHT_BLUE);
        btnDelete = createGradientButton("🗑️ DELETE", ACCENT_RED);
        btnClear = createGradientButton("🧹 CLEAR", WARNING_ORANGE);
        btnRefresh = createGradientButton("🔄 REFRESH", PURPLE);
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        buttonPanel.add(btnRefresh);
        
        formPanel.add(new Label()); // Empty cell
        formPanel.add(buttonPanel);
        
        addEventListeners();
    }
    
    private void createSearchPanel() {
        searchPanel = createColorfulCard("🔍 SEARCH & RECORDS", PURPLE, new BorderLayout(10, 10));
        
        Panel searchInputPanel = new Panel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        searchInputPanel.setBackground(CARD_WHITE);
        
        txtSearch = createColorfulTextField();
        txtSearch.setPreferredSize(new Dimension(250, 35));
        
        btnSearch = createGradientButton("🔎 SEARCH", PRIMARY_BLUE);
        btnShowAll = createGradientButton("📋 SHOW ALL", TEAL);
        
        searchInputPanel.add(new Label("🔍 Search by Name:"));
        searchInputPanel.add(txtSearch);
        searchInputPanel.add(btnSearch);
        searchInputPanel.add(btnShowAll);
        
        searchPanel.add(searchInputPanel, BorderLayout.NORTH);
        
        // Record list with colorful styling
        Panel listPanel = new Panel(new BorderLayout());
        listPanel.setBackground(CARD_WHITE);
        
        Label listTitle = new Label("📋 CRIMINAL RECORDS LIST");
        listTitle.setFont(headerFont);
        listTitle.setForeground(TEXT_DARK);
        listTitle.setAlignment(Label.CENTER);
        listPanel.add(listTitle, BorderLayout.NORTH);
        
        recordList = new java.awt.List(15);
        recordList.setFont(normalFont);
        recordList.setBackground(new Color(248, 248, 252));
        recordList.setForeground(TEXT_DARK);
        recordList.setForeground(TEXT_DARK);
        
        // Add scroll pane effect
        Panel listContainer = new Panel(new BorderLayout());
        listContainer.setBackground(CARD_WHITE);
        listContainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 1));
        listContainer.add(recordList, BorderLayout.CENTER);
        
        listPanel.add(listContainer, BorderLayout.CENTER);
        
        searchPanel.add(listPanel, BorderLayout.CENTER);
    }
    
    private void createDisplayPanel() {
        displayPanel = createColorfulCard("📄 RECORD DETAILS", LIGHT_BLUE, new BorderLayout(10, 10));
        
        txtDisplay = new TextArea(18, 60);
        txtDisplay.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtDisplay.setBackground(new Color(248, 250, 252));
        txtDisplay.setForeground(TEXT_DARK);
        txtDisplay.setEditable(false);
        
        Panel displayContainer = new Panel(new BorderLayout());
        displayContainer.setBackground(CARD_WHITE);
        displayContainer.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 220), 1));
        displayContainer.add(txtDisplay, BorderLayout.CENTER);
        
        displayPanel.add(displayContainer, BorderLayout.CENTER);
    }
    
    private void createStatsPanel() {
        statsPanel = createColorfulCard("📊 LIVE STATISTICS", GOLD, new GridLayout(2, 4, 15, 15));
        statsPanel.setPreferredSize(new Dimension(getWidth(), 150));
    }
    
    private void createFooter() {
        Panel footerPanel = new Panel(new BorderLayout());
        footerPanel.setBackground(DARK_BLUE);
        footerPanel.setPreferredSize(new Dimension(getWidth(), 50));
        
        Label footerText = new Label("© 2024 Police Records Management System | Secure Database v2.0 | Developed for Law Enforcement Use");
        footerText.setFont(smallFont);
        footerText.setForeground(new Color(200, 200, 200));
        footerText.setAlignment(Label.CENTER);
        
        Label securityLabel = new Label("🔒 ENCRYPTED & SECURE");
        securityLabel.setFont(smallFont);
        securityLabel.setForeground(SUCCESS_GREEN);
        securityLabel.setAlignment(Label.RIGHT);
        
        footerPanel.add(footerText, BorderLayout.CENTER);
        
        Panel securityPanel = new Panel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        securityPanel.setBackground(DARK_BLUE);
        securityPanel.add(securityLabel);
        footerPanel.add(securityPanel, BorderLayout.EAST);
        
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    // Utility methods for colorful components
    private Panel createColorfulCard(String title, Color accentColor, LayoutManager layout) {
        Panel card = new Panel(new BorderLayout(10, 10));
        card.setBackground(BACKGROUND);
        
        // Title panel with accent color
        Panel titlePanel = new Panel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(accentColor);
        titlePanel.setPreferredSize(new Dimension(getWidth(), 40));
        
        Label titleLabel = new Label(" " + title);
        titleLabel.setFont(headerFont);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        
        Panel contentPanel = new Panel(layout);
        contentPanel.setBackground(CARD_WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 230), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        card.add(titlePanel, BorderLayout.NORTH);
        card.add(contentPanel, BorderLayout.CENTER);
        return card;
    }
    
    private TextField createColorfulTextField() {
        TextField field = new TextField(20);
        field.setFont(normalFont);
        field.setBackground(new Color(248, 248, 252));
        field.setForeground(TEXT_DARK);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }
    
    private Button createGradientButton(String text, Color baseColor) {
        Button button = new Button(text) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, baseColor, 0, getHeight(), baseColor.darker());
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getLabel())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2d.drawString(getLabel(), x, y);
            }
        };
        
        button.setFont(boldFont);
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(140, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.repaint();
            }
            public void mouseExited(MouseEvent e) {
                button.repaint();
            }
        });
        
        return button;
    }
    
    private void addColorfulFormField(String label, Component field) {
        Label lbl = new Label(label);
        lbl.setFont(boldFont);
        lbl.setForeground(TEXT_DARK);
        formPanel.add(lbl);
        formPanel.add(field);
    }
    
    // Event Listeners
    private void addEventListeners() {
        btnAdd.addActionListener(e -> addCriminalRecord());
        btnUpdate.addActionListener(e -> updateCriminalRecord());
        btnDelete.addActionListener(e -> deleteCriminalRecord());
        btnClear.addActionListener(e -> clearForm());
        btnRefresh.addActionListener(e -> refreshData());
        btnSearch.addActionListener(e -> searchRecords());
        btnShowAll.addActionListener(e -> loadAllRecords());
        recordList.addActionListener(e -> displaySelectedRecord());
        
        // Add Enter key listener for search
        txtSearch.addActionListener(e -> searchRecords());
    }
    
    // Business logic methods with colorful enhancements
    private void addCriminalRecord() {
        try {
            if (validateForm()) {
                CriminalRecord record = new CriminalRecord();
                record.setName(txtName.getText());
                record.setAge(Integer.parseInt(txtAge.getText()));
                record.setCrimeType(txtCrimeType.getText());
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                record.setCrimeDate(sdf.parse(txtDate.getText()));
                
                record.setAddress(txtAddress.getText());
                record.setPhone(txtPhone.getText());
                record.setStatus(chStatus.getSelectedItem().replaceAll("[^a-zA-Z\\s]", "").trim());
                
                if (criminalDAO.addCriminalRecord(record)) {
                    showColorfulMessage("✅ SUCCESS", "Record added successfully!", SUCCESS_GREEN);
                    clearForm();
                    refreshData();
                } else {
                    showColorfulMessage("❌ ERROR", "Failed to add record!", ACCENT_RED);
                }
            }
        } catch (Exception ex) {
            showColorfulMessage("⚠️ WARNING", "Error: " + ex.getMessage(), WARNING_ORANGE);
        }
    }
    
    private void updateCriminalRecord() {
        try {
            int selectedIndex = recordList.getSelectedIndex();
            if (selectedIndex == -1) {
                showColorfulMessage("⚠️ WARNING", "Please select a record to update!", WARNING_ORANGE);
                return;
            }
            
            if (validateForm()) {
                String selectedItem = recordList.getSelectedItem();
                int recordId = Integer.parseInt(selectedItem.split(":")[0]);
                
                CriminalRecord record = new CriminalRecord();
                record.setId(recordId);
                record.setName(txtName.getText());
                record.setAge(Integer.parseInt(txtAge.getText()));
                record.setCrimeType(txtCrimeType.getText());
                
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                record.setCrimeDate(sdf.parse(txtDate.getText()));
                
                record.setAddress(txtAddress.getText());
                record.setPhone(txtPhone.getText());
                record.setStatus(chStatus.getSelectedItem().replaceAll("[^a-zA-Z\\s]", "").trim());
                
                if (criminalDAO.updateRecord(record)) {
                    showColorfulMessage("✅ SUCCESS", "Record updated successfully!", SUCCESS_GREEN);
                    clearForm();
                    refreshData();
                } else {
                    showColorfulMessage("❌ ERROR", "Failed to update record!", ACCENT_RED);
                }
            }
        } catch (Exception ex) {
            showColorfulMessage("⚠️ WARNING", "Error: " + ex.getMessage(), WARNING_ORANGE);
        }
    }
    
    private void deleteCriminalRecord() {
        try {
            int selectedIndex = recordList.getSelectedIndex();
            if (selectedIndex == -1) {
                showColorfulMessage("⚠️ WARNING", "Please select a record to delete!", WARNING_ORANGE);
                return;
            }
            
            String selectedItem = recordList.getSelectedItem();
            int recordId = Integer.parseInt(selectedItem.split(":")[0]);
            
            if (criminalDAO.deleteRecord(recordId)) {
                showColorfulMessage("✅ SUCCESS", "Record deleted successfully!", SUCCESS_GREEN);
                clearForm();
                refreshData();
            } else {
                showColorfulMessage("❌ ERROR", "Failed to delete record!", ACCENT_RED);
            }
        } catch (Exception ex) {
            showColorfulMessage("⚠️ WARNING", "Error: " + ex.getMessage(), WARNING_ORANGE);
        }
    }
    
    private void refreshData() {
        loadAllRecords();
        updateStats();
        showColorfulMessage("🔄 REFRESH", "Data refreshed successfully!", LIGHT_BLUE);
    }
    
    private void searchRecords() {
        String searchName = txtSearch.getText().trim();
        if (searchName.isEmpty()) {
            showColorfulMessage("⚠️ WARNING", "Please enter a name to search!", WARNING_ORANGE);
            return;
        }
        
        List<CriminalRecord> records = criminalDAO.searchByName(searchName);
        displayRecords(records);
        updateStats();
        
        if (records.isEmpty()) {
            showColorfulMessage("🔍 SEARCH", "No records found for: " + searchName, WARNING_ORANGE);
        } else {
            showColorfulMessage("🔍 SEARCH", "Found " + records.size() + " record(s) for: " + searchName, SUCCESS_GREEN);
        }
    }
    
    private void loadAllRecords() {
        List<CriminalRecord> records = criminalDAO.getAllRecords();
        displayRecords(records);
        updateStats();
    }
    
    private void displayRecords(List<CriminalRecord> records) {
        recordList.removeAll();
        for (CriminalRecord record : records) {
            String statusIcon = getStatusIcon(record.getStatus());
            recordList.add(record.getId() + ": " + statusIcon + " " + record.getName() + " - " + record.getCrimeType());
        }
    }
    
    private void displaySelectedRecord() {
        try {
            int selectedIndex = recordList.getSelectedIndex();
            if (selectedIndex != -1) {
                String selectedItem = recordList.getSelectedItem();
                int recordId = Integer.parseInt(selectedItem.split(":")[0]);
                
                List<CriminalRecord> allRecords = criminalDAO.getAllRecords();
                for (CriminalRecord record : allRecords) {
                    if (record.getId() == recordId) {
                        displayRecordDetails(record);
                        fillFormWithRecord(record);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            showColorfulMessage("⚠️ WARNING", "Error displaying record: " + ex.getMessage(), WARNING_ORANGE);
        }
    }
    
    private void displayRecordDetails(CriminalRecord record) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder sb = new StringBuilder();
        sb.append("╔══════════════════════════════════════════════╗\n");
        sb.append("║           🔍 CRIMINAL RECORD DETAILS         ║\n");
        sb.append("╠══════════════════════════════════════════════╣\n");
        sb.append("║ 🆔  ID: ").append(String.format("%-35s", record.getId())).append("║\n");
        sb.append("║ 👤  Name: ").append(String.format("%-33s", record.getName())).append("║\n");
        sb.append("║ 🎂  Age: ").append(String.format("%-35s", record.getAge())).append("║\n");
        sb.append("║ ⚖️  Crime Type: ").append(String.format("%-28s", record.getCrimeType())).append("║\n");
        sb.append("║ 📅  Crime Date: ").append(String.format("%-27s", sdf.format(record.getCrimeDate()))).append("║\n");
        sb.append("║ 🏠  Address: ").append(String.format("%-30s", record.getAddress())).append("║\n");
        sb.append("║ 📞  Phone: ").append(String.format("%-33s", record.getPhone())).append("║\n");
        sb.append("║ 🔄  Status: ").append(String.format("%-32s", 
            getStatusIcon(record.getStatus()) + " " + record.getStatus())).append("║\n");
        sb.append("╚══════════════════════════════════════════════╝");
        
        txtDisplay.setText(sb.toString());
    }
    
    private void fillFormWithRecord(CriminalRecord record) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        txtName.setText(record.getName());
        txtAge.setText(String.valueOf(record.getAge()));
        txtCrimeType.setText(record.getCrimeType());
        txtDate.setText(sdf.format(record.getCrimeDate()));
        txtAddress.setText(record.getAddress());
        txtPhone.setText(record.getPhone());
        
        // Set status
        String statusWithEmoji = getStatusIcon(record.getStatus()) + " " + record.getStatus().toUpperCase();
        for (int i = 0; i < chStatus.getItemCount(); i++) {
            if (chStatus.getItem(i).contains(record.getStatus().toUpperCase())) {
                chStatus.select(i);
                break;
            }
        }
    }
    
    private void updateStats() {
        List<CriminalRecord> records = criminalDAO.getAllRecords();
        long wanted = records.stream().filter(r -> r.getStatus().equalsIgnoreCase("wanted")).count();
        long arrested = records.stream().filter(r -> r.getStatus().equalsIgnoreCase("arrested")).count();
        long released = records.stream().filter(r -> r.getStatus().equalsIgnoreCase("released")).count();
        long pending = records.stream().filter(r -> r.getStatus().equalsIgnoreCase("pending investigation")).count();
        
        lblStats.setText("📊 Total: " + records.size() + " | 🚨 Wanted: " + wanted + " | 🔒 Arrested: " + arrested + " | ✅ Released: " + released);
        
        // Update stats panel with colorful cards
        statsPanel.removeAll();
        
    // Reorder and use the vivid palette for stat cards
    Color[] statColors = {ACCENT_RED, PRIMARY_BLUE, SUCCESS_GREEN, WARNING_ORANGE, PURPLE, TEAL, GOLD, LIGHT_BLUE};
        String[] statIcons = {"🚨", "🔒", "✅", "⏳", "📊", "🆕", "📈", "📉"};
        String[] statLabels = {"WANTED", "ARRESTED", "RELEASED", "PENDING", "TOTAL", "ACTIVE", "URGENT", "CLOSED"};
        String[] statValues = {String.valueOf(wanted), String.valueOf(arrested), String.valueOf(released), 
                              String.valueOf(pending), String.valueOf(records.size()), 
                              String.valueOf(wanted + arrested), "0", String.valueOf(released)};
        
        for (int i = 0; i < 8; i++) {
            Panel statCard = createStatCard(statIcons[i], statLabels[i], statValues[i], statColors[i]);
            statsPanel.add(statCard);
        }
        
        statsPanel.validate();
        statsPanel.repaint();
    }
    
    private Panel createStatCard(String icon, String label, String value, Color color) {
        Panel card = new Panel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        Label iconLabel = new Label(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setAlignment(Label.CENTER);
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(titleFont);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignment(Label.CENTER);
        
        Label nameLabel = new Label(label);
        nameLabel.setFont(smallFont);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignment(Label.CENTER);
        
        Panel centerPanel = new Panel(new GridLayout(2, 1));
        centerPanel.setOpaque(false);
        centerPanel.add(valueLabel);
        centerPanel.add(nameLabel);
        
        card.add(iconLabel, BorderLayout.WEST);
        card.add(centerPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private String getStatusIcon(String status) {
        switch (status.toLowerCase()) {
            case "wanted": return "🚨";
            case "arrested": return "🔒";
            case "released": return "✅";
            case "pending":
            case "pending investigation": return "⏳";
            default: return "📝";
        }
    }
    
    private boolean validateForm() {
        if (txtName.getText().trim().isEmpty() ||
            txtAge.getText().trim().isEmpty() ||
            txtCrimeType.getText().trim().isEmpty() ||
            txtDate.getText().trim().isEmpty()) {
            
            showColorfulMessage("⚠️ VALIDATION", "Please fill all required fields!", WARNING_ORANGE);
            return false;
        }
        
        try {
            int age = Integer.parseInt(txtAge.getText());
            if (age < 1 || age > 150) {
                showColorfulMessage("⚠️ VALIDATION", "Age must be between 1 and 150!", WARNING_ORANGE);
                return false;
            }
        } catch (NumberFormatException e) {
            showColorfulMessage("⚠️ VALIDATION", "Age must be a valid number!", WARNING_ORANGE);
            return false;
        }
        
        if (!txtDate.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            showColorfulMessage("⚠️ VALIDATION", "Date must be in yyyy-mm-dd format!", WARNING_ORANGE);
            return false;
        }
        
        return true;
    }
    
    private void clearForm() {
        txtName.setText("");
        txtAge.setText("");
        txtCrimeType.setText("");
        txtDate.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        chStatus.select(0);
        txtDisplay.setText("");
        txtSearch.setText("");
    }
    
    private void showColorfulMessage(String title, String message, Color color) {
        Dialog dialog = new Dialog(this, title, true);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(450, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        // Header with gradient
        Panel headerPanel = new Panel(new FlowLayout(FlowLayout.LEFT)) {
            @Override
            public void paint(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, color, getWidth(), 0, color.darker());
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                super.paint(g);
            }
        };
        headerPanel.setPreferredSize(new Dimension(450, 50));
        
        Label titleLabel = new Label("   " + title);
        titleLabel.setFont(headerFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Message content
        Panel messagePanel = new Panel(new BorderLayout());
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        Label messageLabel = new Label(message);
        messageLabel.setFont(normalFont);
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setAlignment(Label.CENTER);
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        
        // Button
        Panel buttonPanel = new Panel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        
        Button okButton = createGradientButton("OK", color);
        okButton.addActionListener(e -> dialog.setVisible(false));
        buttonPanel.add(okButton);
        
        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(messagePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        // Set system look and feel for better appearance
        try {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        new PoliceRecordsSystem();
    }
}