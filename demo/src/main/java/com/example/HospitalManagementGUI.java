package com.example;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// ============= MAIN APPLICATION =============
public class HospitalManagementGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private ArrayList<User> users;
    private ArrayList<Dokter> dokters;
    private ArrayList<Appointment> appointments;
    private ArrayList<PaketAsuransi> paketAsuransiList;
    private User currentUser;
    
    // Modern Color Palette
    private static final Color DARK_BG = new Color(26, 32, 44);
    private static final Color SIDEBAR_BG = new Color(20, 25, 36);
    private static final Color CARD_BG = new Color(31, 41, 55);
    private static final Color PURPLE_GRADIENT_START = new Color(139, 92, 246);
    private static final Color PURPLE_GRADIENT_END = new Color(217, 70, 239);
    private static final Color PINK_ACCENT = new Color(236, 72, 153);
    private static final Color TEXT_PRIMARY = new Color(243, 244, 246);
    private static final Color TEXT_SECONDARY = new Color(156, 163, 175);
    
    public HospitalManagementGUI() {
        initializeData();
        setupFrame();
        createComponents();
    }
    
    private void initializeData() {
        users = new ArrayList<>();
        dokters = new ArrayList<>();
        appointments = new ArrayList<>();
        paketAsuransiList = new ArrayList<>();
        
        // Sample Dokter
        Dokter d1 = new Dokter("D001", "Ahmad Wijaya", "ahmad@hospital.com", "pass123", 
                               "081234567890", "SIP001", "Umum", 10, "08:00-16:00");
        Dokter d2 = new Dokter("D002", "Siti Nurhaliza", "siti@hospital.com", "pass123",
                               "081234567891", "SIP002", "Anak", 8, "09:00-17:00");
        Dokter d3 = new Dokter("D003", "Budi Santoso", "budi@hospital.com", "pass123",
                               "081234567892", "SIP003", "Jantung", 15, "07:00-15:00");
        dokters.add(d1);
        dokters.add(d2);
        dokters.add(d3);
        users.add(d1);
        users.add(d2);
        users.add(d3);
        
        // Sample Pasien
        Pasien p1 = new Pasien("P001", "Andi Pratama", "andi@email.com", "pass123",
                               "082345678901", "3201010101010001", "01-01-1990",
                               "Jl. Merdeka No. 10", "Laki-laki");
        users.add(p1);
        
        // Sample Admin
        Admin a1 = new Admin("A001", "Admin Utama", "admin@hospital.com", "admin123",
                             "081234567893", "Kepala Admin");
        users.add(a1);
        
        // Sample Paket Asuransi
        paketAsuransiList.add(new PaketAsuransi("PA001", "Paket Basic", 150000, 10000000,
            new String[]{"Rawat Jalan", "Obat Generik", "Konsultasi Dokter Umum"}));
        paketAsuransiList.add(new PaketAsuransi("PA002", "Paket Silver", 300000, 25000000,
            new String[]{"Rawat Jalan", "Rawat Inap Kelas 2", "Obat", "Konsultasi Spesialis"}));
        paketAsuransiList.add(new PaketAsuransi("PA003", "Paket Gold", 500000, 50000000,
            new String[]{"Rawat Jalan", "Rawat Inap Kelas 1", "Obat", "Operasi", "Lab"}));
    }
    
    private void setupFrame() {
        setTitle("Hospital Management System");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(DARK_BG);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(DARK_BG);
        add(mainPanel);
    }
    
    private void createComponents() {
        mainPanel.add(createLoginPanel(), "LOGIN");
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    // ============= LOGIN PANEL =============
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(DARK_BG);
        
        JPanel loginCard = new JPanel(new GridBagLayout());
        loginCard.setBackground(CARD_BG);
        loginCard.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        loginCard.setPreferredSize(new Dimension(450, 500));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        
        JLabel titleLabel = new JLabel("🏥 Hospital System");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(TEXT_PRIMARY);
        loginCard.add(titleLabel, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 20, 0);
        JLabel subtitleLabel = new JLabel("Sign in to your account");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(TEXT_SECONDARY);
        loginCard.add(subtitleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(TEXT_SECONDARY);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        loginCard.add(emailLabel, gbc);
        
        JTextField emailField = createModernTextField();
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 20, 0);
        loginCard.add(emailField, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(TEXT_SECONDARY);
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        loginCard.add(passwordLabel, gbc);
        
        JPasswordField passwordField = createModernPasswordField();
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 30, 0);
        loginCard.add(passwordField, gbc);
        
        JButton loginButton = createGradientButton("Sign In");
        gbc.gridy = 6;
        gbc.insets = new Insets(0, 0, 15, 0);
        loginCard.add(loginButton, gbc);
        
        JButton registerButton = createOutlineButton("Register New Patient");
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 0, 0);
        loginCard.add(registerButton, gbc);
        
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            
            User user = authenticateUser(email, password);
            if (user != null) {
                currentUser = user;
                showDashboard(user);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials!", 
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        registerButton.addActionListener(e -> showRegisterPanel());
        
        panel.add(loginCard);
        return panel;
    }
    
    // ============= REGISTER PANEL =============
    private void showRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        
        JPanel topBar = createTopBar("Register New Patient");
        
        JPanel scrollContent = new JPanel(new GridBagLayout());
        scrollContent.setBackground(DARK_BG);
        
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formCard.setPreferredSize(new Dimension(600, 700));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        
        JTextField namaField = createModernTextField();
        JTextField emailField = createModernTextField();
        JPasswordField passwordField = createModernPasswordField();
        JTextField telpField = createModernTextField();
        JTextField ktpField = createModernTextField();
        JTextField tglLahirField = createModernTextField();
        JTextField alamatField = createModernTextField();
        JComboBox<String> jenisKelaminBox = createModernComboBox(new String[]{"Laki-laki", "Perempuan"});
        
        addModernFormField(formCard, gbc, 0, "Full Name", namaField);
        addModernFormField(formCard, gbc, 1, "Email Address", emailField);
        addModernFormField(formCard, gbc, 2, "Password", passwordField);
        addModernFormField(formCard, gbc, 3, "Phone Number", telpField);
        addModernFormField(formCard, gbc, 4, "ID Card Number", ktpField);
        addModernFormField(formCard, gbc, 5, "Birth Date (DD-MM-YYYY)", tglLahirField);
        addModernFormField(formCard, gbc, 6, "Address", alamatField);
        addModernFormField(formCard, gbc, 7, "Gender", jenisKelaminBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(CARD_BG);
        JButton registerButton = createGradientButton("Register");
        JButton backButton = createOutlineButton("Back");
        
        registerButton.addActionListener(e -> {
            String userId = "P" + String.format("%03d", users.size() + 1);
            Pasien newPasien = new Pasien(userId, namaField.getText(), emailField.getText(),
                new String(passwordField.getPassword()), telpField.getText(), ktpField.getText(),
                tglLahirField.getText(), alamatField.getText(), (String)jenisKelaminBox.getSelectedItem());
            users.add(newPasien);
            JOptionPane.showMessageDialog(this, "Registration successful! Your ID: " + userId);
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridy = 8;
        gbc.insets = new Insets(20, 0, 0, 0);
        formCard.add(buttonPanel, gbc);
        
        scrollContent.add(formCard);
        
        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        mainPanel.add(panel, "REGISTER");
        cardLayout.show(mainPanel, "REGISTER");
    }
    
    // ============= DASHBOARD =============
    private void showDashboard(User user) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        
        JPanel sidebar = createModernSidebar(user);
        
        JPanel contentArea = new JPanel(new CardLayout());
        contentArea.setBackground(DARK_BG);
        
        if (user instanceof Pasien) {
            contentArea.add(createPasienDashboard((Pasien)user), "HOME");
            contentArea.add(createBookAppointmentPanel((Pasien)user), "BOOK");
            contentArea.add(createMyAppointmentsPanel((Pasien)user), "APPOINTMENTS");
            contentArea.add(createAsuransiPanel((Pasien)user), "ASURANSI");
        } else if (user instanceof Dokter) {
            contentArea.add(createDokterDashboard((Dokter)user), "HOME");
            contentArea.add(createDokterAppointmentsPanel((Dokter)user), "APPOINTMENTS");
        } else if (user instanceof Admin) {
            contentArea.add(createAdminDashboard(), "HOME");
            contentArea.add(createManageUsersPanel(), "USERS");
            contentArea.add(createManageDokterPanel(), "DOKTER");
        }
        
        panel.add(sidebar, BorderLayout.WEST);
        panel.add(contentArea, BorderLayout.CENTER);
        
        mainPanel.add(panel, "DASHBOARD");
        cardLayout.show(mainPanel, "DASHBOARD");
    }
    
    // ============= MODERN SIDEBAR =============
    private JPanel createModernSidebar(User user) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(280, 0));
        sidebar.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        // User Profile Section
        JPanel profileSection = new JPanel();
        profileSection.setLayout(new BoxLayout(profileSection, BoxLayout.Y_AXIS));
        profileSection.setBackground(SIDEBAR_BG);
        profileSection.setMaximumSize(new Dimension(280, 100));
        
        JLabel avatarLabel = new JLabel("👤");
        avatarLabel.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        avatarLabel.setForeground(TEXT_PRIMARY);
        avatarLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel nameLabel = new JLabel(user.getNama());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel roleLabel = new JLabel(user.getRole());
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        roleLabel.setForeground(TEXT_SECONDARY);
        roleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        profileSection.add(avatarLabel);
        profileSection.add(Box.createRigidArea(new Dimension(0, 10)));
        profileSection.add(nameLabel);
        profileSection.add(roleLabel);
        
        sidebar.add(profileSection);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));
        
        // Menu Items
        if (user instanceof Pasien) {
            sidebar.add(createModernMenuItem("🏠", "Dashboard", "HOME", true));
            sidebar.add(createModernMenuItem("📅", "Book Appointment", "BOOK", false));
            sidebar.add(createModernMenuItem("📋", "My Appointments", "APPOINTMENTS", false));
            sidebar.add(createModernMenuItem("🏥", "Insurance", "ASURANSI", false));
        } else if (user instanceof Dokter) {
            sidebar.add(createModernMenuItem("🏠", "Dashboard", "HOME", true));
            sidebar.add(createModernMenuItem("📅", "Patient Appointments", "APPOINTMENTS", false));
        } else if (user instanceof Admin) {
            sidebar.add(createModernMenuItem("🏠", "Dashboard", "HOME", true));
            sidebar.add(createModernMenuItem("👥", "Manage Users", "USERS", false));
            sidebar.add(createModernMenuItem("⚕️", "Manage Doctors", "DOKTER", false));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        // Logout Button
        JButton logoutBtn = createModernMenuItem("🚪", "Logout", "LOGOUT", false);
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        sidebar.add(logoutBtn);
        
        return sidebar;
    }
    
    private JButton createModernMenuItem(String icon, String text, String action, boolean selected) {
        JButton btn = new JButton(icon + "  " + text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(280, 45));
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(selected ? TEXT_PRIMARY : TEXT_SECONDARY);
        btn.setBackground(selected ? CARD_BG : SIDEBAR_BG);
        btn.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!selected) {
                    btn.setBackground(CARD_BG);
                    btn.setForeground(TEXT_PRIMARY);
                }
            }
            public void mouseExited(MouseEvent e) {
                if (!selected) {
                    btn.setBackground(SIDEBAR_BG);
                    btn.setForeground(TEXT_SECONDARY);
                }
            }
        });
        
        if (!action.equals("LOGOUT")) {
            btn.addActionListener(e -> {
                Component parent = btn.getParent().getParent();
                if (parent instanceof JPanel) {
                    Component contentArea = ((JPanel)parent).getComponent(1);
                    if (contentArea instanceof JPanel) {
                        CardLayout cl = (CardLayout)((JPanel)contentArea).getLayout();
                        cl.show((JPanel)contentArea, action);
                    }
                }
            });
        }
        
        return btn;
    }
    
    // ============= PASIEN DASHBOARD =============
    private JPanel createPasienDashboard(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Dashboard");
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(DARK_BG);
        
        // Stats Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(DARK_BG);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        long appointmentCount = appointments.stream().filter(a -> a.getPasien().equals(pasien)).count();
        statsPanel.add(createGradientStatCard("Total Appointments", String.valueOf(appointmentCount), "📅", 85));
        statsPanel.add(createGradientStatCard("Available Doctors", String.valueOf(dokters.size()), "⚕️", 100));
        
        String insuranceText = pasien.getPaketAsuransi() != null ? 
            pasien.getPaketAsuransi().getNamaPaket() : "None";
        statsPanel.add(createGradientStatCard("Insurance Plan", insuranceText, "🏥", 78));
        
        content.add(statsPanel);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= GRADIENT STAT CARD =============
    private JPanel createGradientStatCard(String title, String value, String icon, int percentage) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                
                GradientPaint gp = new GradientPaint(0, 0, PURPLE_GRADIENT_START, w, h, PURPLE_GRADIENT_END);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w, h, 20, 20);
            }
        };
        card.setLayout(new BorderLayout());
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(new Color(255, 255, 255, 200));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        leftPanel.add(valueLabel);
        
        // Circular Progress
        JPanel progressPanel = new CircularProgressPanel(percentage);
        
        card.add(leftPanel, BorderLayout.WEST);
        card.add(progressPanel, BorderLayout.EAST);
        
        return card;
    }
    
    // ============= CIRCULAR PROGRESS PANEL =============
    class CircularProgressPanel extends JPanel {
        private int percentage;
        
        public CircularProgressPanel(int percentage) {
            this.percentage = percentage;
            setOpaque(false);
            setPreferredSize(new Dimension(80, 80));
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            int size = 70;
            int x = (getWidth() - size) / 2;
            int y = (getHeight() - size) / 2;
            
            // Background circle
            g2d.setColor(new Color(255, 255, 255, 50));
            g2d.setStroke(new BasicStroke(8));
            g2d.drawOval(x, y, size, size);
            
            // Progress arc
            g2d.setColor(Color.WHITE);
            int angle = (int) (360 * (percentage / 100.0));
            g2d.drawArc(x, y, size, size, 90, -angle);
            
            // Percentage text
            g2d.setFont(new Font("Segoe UI", Font.BOLD, 16));
            String text = percentage + "%";
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (size - fm.stringWidth(text)) / 2;
            int textY = y + ((size - fm.getHeight()) / 2) + fm.getAscent();
            g2d.drawString(text, textX, textY);
        }
    }
    
    // ============= BOOK APPOINTMENT PANEL =============
    private JPanel createBookAppointmentPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Book New Appointment");
        
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(CARD_BG);
        formCard.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        formCard.setPreferredSize(new Dimension(700, 600));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);
        
        JComboBox<String> dokterBox = createModernComboBox(new String[]{});
        for (Dokter d : dokters) {
            dokterBox.addItem(d.getNama() + " - " + d.getSpesialisasi());
        }
        
        JTextField tanggalField = createModernTextField();
        JTextField waktuField = createModernTextField();
        JTextArea keluhanArea = new JTextArea(5, 20);
        keluhanArea.setBackground(new Color(45, 55, 72));
        keluhanArea.setForeground(TEXT_PRIMARY);
        keluhanArea.setCaretColor(TEXT_PRIMARY);
        keluhanArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 85, 99)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JScrollPane keluhanScroll = new JScrollPane(keluhanArea);
        keluhanScroll.setBorder(BorderFactory.createLineBorder(new Color(75, 85, 99)));
        
        addModernFormField(formCard, gbc, 0, "Select Doctor", dokterBox);
        addModernFormField(formCard, gbc, 1, "Date (DD-MM-YYYY)", tanggalField);
        addModernFormField(formCard, gbc, 2, "Time (HH:MM)", waktuField);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel keluhanLabel = new JLabel("Complaint");
        keluhanLabel.setForeground(TEXT_SECONDARY);
        keluhanLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        formCard.add(keluhanLabel, gbc);
        
        gbc.gridy = 7;
        gbc.insets = new Insets(0, 0, 20, 0);
        formCard.add(keluhanScroll, gbc);
        
        JButton submitButton = createGradientButton("Book Appointment");
        gbc.gridy = 8;
        gbc.insets = new Insets(10, 0, 0, 0);
        formCard.add(submitButton, gbc);
        
        submitButton.addActionListener(e -> {
            String appointmentId = "APT" + String.format("%03d", appointments.size() + 1);
            Dokter selectedDokter = dokters.get(dokterBox.getSelectedIndex());
            Appointment apt = new Appointment(appointmentId, pasien, selectedDokter,
                tanggalField.getText(), waktuField.getText(), keluhanArea.getText());
            appointments.add(apt);
            JOptionPane.showMessageDialog(this, "Appointment created successfully!\nID: " + appointmentId);
            tanggalField.setText("");
            waktuField.setText("");
            keluhanArea.setText("");
        });
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(DARK_BG);
        centerPanel.add(formCard);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= MY APPOINTMENTS PANEL =============
    private JPanel createMyAppointmentsPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("My Appointments");
        
        String[] columns = {"ID", "Doctor", "Date", "Time", "Status", "Complaint"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        for (Appointment apt : appointments) {
            if (apt.getPasien().equals(pasien)) {
                model.addRow(new Object[]{
                    apt.getIdAppointment(),
                    "Dr. " + apt.getDokter().getNama(),
                    apt.getTanggal(),
                    apt.getWaktu(),
                    apt.getStatus(),
                    apt.getKeluhan()
                });
            }
        }
        
        JTable table = new JTable(model);
        styleModernTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK_BG);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= ASURANSI PANEL =============
    private JPanel createAsuransiPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Insurance Packages");
        
        JPanel paketsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        paketsPanel.setBackground(DARK_BG);
        
        for (PaketAsuransi paket : paketAsuransiList) {
            paketsPanel.add(createModernPaketCard(paket, pasien));
        }
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(paketsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= DOKTER DASHBOARD =============
    private JPanel createDokterDashboard(Dokter dokter) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Doctor Dashboard");
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(DARK_BG);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(DARK_BG);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        long appointmentCount = appointments.stream().filter(a -> a.getDokter().equals(dokter)).count();
        statsPanel.add(createGradientStatCard("Total Patients", String.valueOf(appointmentCount), "📅", 92));
        statsPanel.add(createGradientStatCard("Rating", String.format("%.1f/5.0", dokter.getRating()), "⭐", 85));
        statsPanel.add(createGradientStatCard("Working Hours", dokter.getJamPraktik(), "⏰", 100));
        
        content.add(statsPanel);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= DOKTER APPOINTMENTS PANEL =============
    private JPanel createDokterAppointmentsPanel(Dokter dokter) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Patient Appointments");
        
        String[] columns = {"ID", "Patient", "Date", "Time", "Complaint", "Status", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return column == 6; }
        };
        
        for (Appointment apt : appointments) {
            if (apt.getDokter().equals(dokter)) {
                model.addRow(new Object[]{
                    apt.getIdAppointment(),
                    apt.getPasien().getNama(),
                    apt.getTanggal(),
                    apt.getWaktu(),
                    apt.getKeluhan(),
                    apt.getStatus(),
                    "View"
                });
            }
        }
        
        JTable table = new JTable(model);
        styleModernTable(table);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 6 && row >= 0) {
                    String aptId = (String) table.getValueAt(row, 0);
                    for (Appointment apt : appointments) {
                        if (apt.getIdAppointment().equals(aptId)) {
                            showAppointmentDetail(apt);
                            break;
                        }
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK_BG);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= ADMIN DASHBOARD =============
    private JPanel createAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Admin Dashboard");
        
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(DARK_BG);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(DARK_BG);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 200));
        
        long pasienCount = users.stream().filter(u -> u instanceof Pasien).count();
        long dokterCount = users.stream().filter(u -> u instanceof Dokter).count();
        
        statsPanel.add(createGradientStatCard("Total Patients", String.valueOf(pasienCount), "👥", 88));
        statsPanel.add(createGradientStatCard("Total Doctors", String.valueOf(dokterCount), "⚕️", 95));
        statsPanel.add(createGradientStatCard("Appointments", String.valueOf(appointments.size()), "📅", 78));
        
        content.add(statsPanel);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(content, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= MANAGE USERS PANEL =============
    private JPanel createManageUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Manage Users");
        
        String[] columns = {"ID", "Name", "Email", "Role", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        for (User user : users) {
            model.addRow(new Object[]{
                user.getUserId(),
                user.getNama(),
                user.getEmail(),
                user.getRole(),
                user.getNomorTelepon()
            });
        }
        
        JTable table = new JTable(model);
        styleModernTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK_BG);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= MANAGE DOKTER PANEL =============
    private JPanel createManageDokterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JPanel topBar = createTopBar("Manage Doctors");
        
        String[] columns = {"ID", "Name", "Specialization", "Experience", "Rating", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        for (Dokter dokter : dokters) {
            model.addRow(new Object[]{
                dokter.getUserId(),
                "Dr. " + dokter.getNama(),
                dokter.getSpesialisasi(),
                dokter.getPengalaman() + " years",
                String.format("%.1f", dokter.getRating()),
                dokter.isTersedia() ? "Available" : "Unavailable"
            });
        }
        
        JTable table = new JTable(model);
        styleModernTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(DARK_BG);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK_BG);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(topBar, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= HELPER METHODS =============
    private JPanel createTopBar(String title) {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(DARK_BG);
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(TEXT_PRIMARY);
        
        topBar.add(titleLabel, BorderLayout.WEST);
        
        return topBar;
    }
    
    private JTextField createModernTextField() {
        JTextField field = new JTextField(20);
        field.setPreferredSize(new Dimension(300, 40));
        field.setBackground(new Color(45, 55, 72));
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 85, 99)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }
    
    private JPasswordField createModernPasswordField() {
        JPasswordField field = new JPasswordField(20);
        field.setPreferredSize(new Dimension(300, 40));
        field.setBackground(new Color(45, 55, 72));
        field.setForeground(TEXT_PRIMARY);
        field.setCaretColor(TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 85, 99)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }
    
    private JComboBox<String> createModernComboBox(String[] items) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setPreferredSize(new Dimension(300, 40));
        box.setBackground(new Color(45, 55, 72));
        box.setForeground(TEXT_PRIMARY);
        box.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        box.setBorder(BorderFactory.createLineBorder(new Color(75, 85, 99)));
        return box;
    }
    
    private JButton createGradientButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                
                GradientPaint gp = new GradientPaint(0, 0, PURPLE_GRADIENT_START, w, h, PURPLE_GRADIENT_END);
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, w, h, 10, 10);
                
                FontMetrics fm = g2d.getFontMetrics();
                Rectangle2D r = fm.getStringBounds(getText(), g2d);
                int x = (w - (int) r.getWidth()) / 2;
                int y = (h - (int) r.getHeight()) / 2 + fm.getAscent();
                
                g2d.setColor(Color.WHITE);
                g2d.drawString(getText(), x, y);
            }
        };
        button.setPreferredSize(new Dimension(300, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private JButton createOutlineButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 45));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(TEXT_PRIMARY);
        button.setBackground(CARD_BG);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 85, 99), 2),
            BorderFactory.createEmptyBorder(10, 20, 10, 20)));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(45, 55, 72));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(CARD_BG);
            }
        });
        
        return button;
    }
    
    private JPanel createModernPaketCard(PaketAsuransi paket, Pasien pasien) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(75, 85, 99)),
            BorderFactory.createEmptyBorder(30, 25, 30, 25)));
        
        JLabel nameLabel = new JLabel(paket.getNamaPaket());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameLabel.setForeground(TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(String.format("Rp %,.0f/month", paket.getIuranBulanan()));
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(PURPLE_GRADIENT_START);
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel limitLabel = new JLabel(String.format("Limit: Rp %,.0f/year", paket.getLimitPertahun()));
        limitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        limitLabel.setForeground(TEXT_SECONDARY);
        limitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel fasilitasPanel = new JPanel();
        fasilitasPanel.setLayout(new BoxLayout(fasilitasPanel, BoxLayout.Y_AXIS));
        fasilitasPanel.setBackground(CARD_BG);
        fasilitasPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        for (String fasilitas : paket.getFasilitasTersedia()) {
            JLabel fasLabel = new JLabel("✓ " + fasilitas);
            fasLabel.setForeground(TEXT_SECONDARY);
            fasLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            fasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            fasilitasPanel.add(fasLabel);
            fasilitasPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        }
        
        JButton pilihButton = createGradientButton("Choose Plan");
        pilihButton.setPreferredSize(new Dimension(200, 40));
        pilihButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pilihButton.addActionListener(e -> {
            pasien.setPaketAsuransi(paket);
            JOptionPane.showMessageDialog(this, "Package " + paket.getNamaPaket() + " selected successfully!");
        });
        
        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(priceLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(limitLabel);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(fasilitasPanel);
        card.add(Box.createRigidArea(new Dimension(0, 25)));
        card.add(pilihButton);
        
        return card;
    }
    
    private void addModernFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridy = row * 2;
        gbc.insets = new Insets(10, 0, 5, 0);
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        jLabel.setForeground(TEXT_SECONDARY);
        panel.add(jLabel, gbc);
        
        gbc.gridy = row * 2 + 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(field, gbc);
    }
    
    private void styleModernTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(45);
        table.setBackground(CARD_BG);
        table.setForeground(TEXT_PRIMARY);
        table.setGridColor(new Color(55, 65, 81));
        table.setSelectionBackground(new Color(75, 85, 99));
        table.setSelectionForeground(TEXT_PRIMARY);
        table.setShowVerticalLines(true);
        table.setShowHorizontalLines(true);
        
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(45, 55, 72));
        table.getTableHeader().setForeground(TEXT_PRIMARY);
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(75, 85, 99)));
        table.getTableHeader().setPreferredSize(new Dimension(0, 45));
    }
    
    private User authenticateUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && (password.equals("pass123") || password.equals("admin123"))) {
                return user;
            }
        }
        return null;
    }
    
    private void showAppointmentDetail(Appointment appointment) {
        JDialog dialog = new JDialog(this, "Appointment Details", true);
        dialog.setSize(550, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(DARK_BG);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailArea.setBackground(CARD_BG);
        detailArea.setForeground(TEXT_PRIMARY);
        detailArea.setText(String.format(
            "ID Appointment: %s\n\n" +
            "Patient: %s\n" +
            "Date: %s %s\n" +
            "Complaint: %s\n" +
            "Status: %s\n\n" +
            "Diagnosis: %s\n" +
            "Prescription: %s",
            appointment.getIdAppointment(),
            appointment.getPasien().getNama(),
            appointment.getTanggal(),
            appointment.getWaktu(),
            appointment.getKeluhan(),
            appointment.getStatus(),
            appointment.getDiagnosa() != null ? appointment.getDiagnosa() : "-",
            appointment.getResepObat() != null ? appointment.getResepObat() : "-"
        ));
        
        JScrollPane scrollPane = new JScrollPane(detailArea);
        scrollPane.setBorder(null);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        JButton confirmButton = createGradientButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(120, 40));
        JButton completeButton = createGradientButton("Complete");
        completeButton.setPreferredSize(new Dimension(120, 40));
        JButton closeButton = createOutlineButton("Close");
        closeButton.setPreferredSize(new Dimension(120, 40));
        
        confirmButton.addActionListener(e -> {
            appointment.setStatus("CONFIRMED");
            JOptionPane.showMessageDialog(dialog, "Appointment confirmed!");
            dialog.dispose();
        });
        
        completeButton.addActionListener(e -> {
            String diagnosa = JOptionPane.showInputDialog(dialog, "Enter diagnosis:");
            String resep = JOptionPane.showInputDialog(dialog, "Enter prescription:");
            if (diagnosa != null && resep != null) {
                appointment.setDiagnosa(diagnosa);
                appointment.setResepObat(resep);
                appointment.setStatus("COMPLETED");
                JOptionPane.showMessageDialog(dialog, "Appointment completed!");
                dialog.dispose();
            }
        });
        
        closeButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(confirmButton);
        buttonPanel.add(completeButton);
        buttonPanel.add(closeButton);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    // ============= MAIN METHOD =============
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new HospitalManagementGUI().setVisible(true);
        });
    }
}

// ============= MODEL CLASSES =============

abstract class User {
    private String userId;
    private String nama;
    private String email;
    private String password;
    private String nomorTelepon;
    
    public User(String userId, String nama, String email, String password, String nomorTelepon) {
        this.userId = userId;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.nomorTelepon = nomorTelepon;
    }
    
    public String getUserId() { return userId; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNomorTelepon() { return nomorTelepon; }
    public void setNomorTelepon(String nomorTelepon) { this.nomorTelepon = nomorTelepon; }
    
    public abstract void displayInfo();
    public abstract String getRole();
}

class Pasien extends User {
    private String nomorKTP;
    private String tanggalLahir;
    private String alamat;
    private String jenisKelamin;
    private PaketAsuransi paketAsuransi;
    private RiwayatMedis riwayatMedis;
    
    public Pasien(String userId, String nama, String email, String password, 
                  String nomorTelepon, String nomorKTP, String tanggalLahir, 
                  String alamat, String jenisKelamin) {
        super(userId, nama, email, password, nomorTelepon);
        this.nomorKTP = nomorKTP;
        this.tanggalLahir = tanggalLahir;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
        this.riwayatMedis = new RiwayatMedis(userId);
    }
    
    public String getNomorKTP() { return nomorKTP; }
    public String getTanggalLahir() { return tanggalLahir; }
    public String getAlamat() { return alamat; }
    public String getJenisKelamin() { return jenisKelamin; }
    public PaketAsuransi getPaketAsuransi() { return paketAsuransi; }
    public void setPaketAsuransi(PaketAsuransi paket) { this.paketAsuransi = paket; }
    public RiwayatMedis getRiwayatMedis() { return riwayatMedis; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== PATIENT INFO ===");
    }
    
    @Override
    public String getRole() {
        return "PATIENT";
    }
}

class Dokter extends User {
    private String nomorSIP;
    private String spesialisasi;
    private int pengalaman;
    private double rating;
    private String jamPraktik;
    private boolean tersedia;
    
    public Dokter(String userId, String nama, String email, String password,
                  String nomorTelepon, String nomorSIP, String spesialisasi,
                  int pengalaman, String jamPraktik) {
        super(userId, nama, email, password, nomorTelepon);
        this.nomorSIP = nomorSIP;
        this.spesialisasi = spesialisasi;
        this.pengalaman = pengalaman;
        this.jamPraktik = jamPraktik;
        this.rating = 5.0;
        this.tersedia = true;
    }
    
    public String getNomorSIP() { return nomorSIP; }
    public String getSpesialisasi() { return spesialisasi; }
    public int getPengalaman() { return pengalaman; }
    public double getRating() { return rating; }
    public void setRating(double rating) { this.rating = rating; }
    public String getJamPraktik() { return jamPraktik; }
    public boolean isTersedia() { return tersedia; }
    public void setTersedia(boolean tersedia) { this.tersedia = tersedia; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== DOCTOR INFO ===");
    }
    
    @Override
    public String getRole() {
        return "DOCTOR";
    }
}

class Admin extends User {
    private String jabatan;
    
    public Admin(String userId, String nama, String email, String password,
                 String nomorTelepon, String jabatan) {
        super(userId, nama, email, password, nomorTelepon);
        this.jabatan = jabatan;
    }
    
    public String getJabatan() { return jabatan; }
    
    @Override
    public void displayInfo() {
        System.out.println("=== ADMIN INFO ===");
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
}

class Appointment {
    private String idAppointment;
    private Pasien pasien;
    private Dokter dokter;
    private String tanggal;
    private String waktu;
    private String keluhan;
    private String status;
    private String diagnosa;
    private String resepObat;
    
    public Appointment(String idAppointment, Pasien pasien, Dokter dokter,
                      String tanggal, String waktu, String keluhan) {
        this.idAppointment = idAppointment;
        this.pasien = pasien;
        this.dokter = dokter;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.keluhan = keluhan;
        this.status = "PENDING";
    }
    
    public String getIdAppointment() { return idAppointment; }
    public Pasien getPasien() { return pasien; }
    public Dokter getDokter() { return dokter; }
    public String getTanggal() { return tanggal; }
    public String getWaktu() { return waktu; }
    public String getKeluhan() { return keluhan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDiagnosa() { return diagnosa; }
    public void setDiagnosa(String diagnosa) { this.diagnosa = diagnosa; }
    public String getResepObat() { return resepObat; }
    public void setResepObat(String resepObat) { this.resepObat = resepObat; }
}

class PaketAsuransi {
    private String idPaket;
    private String namaPaket;
    private double iuranBulanan;
    private double limitPertahun;
    private String[] fasilitasTersedia;
    
    public PaketAsuransi(String idPaket, String namaPaket, double iuranBulanan,
                        double limitPertahun, String[] fasilitasTersedia) {
        this.idPaket = idPaket;
        this.namaPaket = namaPaket;
        this.iuranBulanan = iuranBulanan;
        this.limitPertahun = limitPertahun;
        this.fasilitasTersedia = fasilitasTersedia;
    }
    
    public String getIdPaket() { return idPaket; }
    public String getNamaPaket() { return namaPaket; }
    public double getIuranBulanan() { return iuranBulanan; }
    public double getLimitPertahun() { return limitPertahun; }
    public String[] getFasilitasTersedia() { return fasilitasTersedia; }
    
    public void displayPaketInfo() {
        System.out.println("=== INSURANCE PACKAGE INFO ===");
        System.out.println("Package: " + namaPaket);
        System.out.println("Monthly Premium: Rp " + iuranBulanan);
        System.out.println("Annual Limit: Rp " + limitPertahun);
        System.out.println("Available Facilities:");
        for (String fasilitas : fasilitasTersedia) {
            System.out.println("- " + fasilitas);
        }
    }
}

class RiwayatMedis {
    private String idRiwayat;
    private ArrayList<String> riwayatPenyakit;
    private ArrayList<String> riwayatObat;
    private ArrayList<String> alergi;
    
    public RiwayatMedis(String idRiwayat) {
        this.idRiwayat = idRiwayat;
        this.riwayatPenyakit = new ArrayList<>();
        this.riwayatObat = new ArrayList<>();
        this.alergi = new ArrayList<>();
    }
    
    public String getIdRiwayat() { return idRiwayat; }
    public ArrayList<String> getRiwayatPenyakit() { return riwayatPenyakit; }
    public ArrayList<String> getRiwayatObat() { return riwayatObat; }
    public ArrayList<String> getAlergi() { return alergi; }
    
    public void tambahRiwayatPenyakit(String penyakit) {
        riwayatPenyakit.add(penyakit);
    }
    
    public void tambahRiwayatObat(String obat) {
        riwayatObat.add(obat);
    }
    
    public void tambahAlergi(String alergi) {
        this.alergi.add(alergi);
    }
    
    public void displayRiwayat() {
        System.out.println("=== MEDICAL HISTORY ===");
        System.out.println("Disease History: " + riwayatPenyakit);
        System.out.println("Medication History: " + riwayatObat);
        System.out.println("Allergies: " + alergi);
    }
}