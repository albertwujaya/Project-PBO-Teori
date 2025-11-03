package com.example;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
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
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        add(mainPanel);
    }
    
    private void createComponents() {
        mainPanel.add(createLoginPanel(), "LOGIN");
        cardLayout.show(mainPanel, "LOGIN");
    }
    
    // ============= LOGIN PANEL =============
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(25, 118, 210));
        headerPanel.setPreferredSize(new Dimension(0, 100));
        JLabel headerLabel = new JLabel("🏥 Hospital Management System");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);
        
        // Login Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel titleLabel = new JLabel("Login ke Sistem");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Email:"), gbc);
        
        JTextField emailField = new JTextField(20);
        emailField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        formPanel.add(emailField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Password:"), gbc);
        
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);
        
        JButton loginButton = createStyledButton("Login", new Color(25, 118, 210));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);
        
        JButton registerButton = createStyledButton("Register Pasien Baru", new Color(76, 175, 80));
        gbc.gridy = 4;
        formPanel.add(registerButton, gbc);
        
        loginButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            
            User user = authenticateUser(email, password);
            if (user != null) {
                currentUser = user;
                showDashboard(user);
            } else {
                JOptionPane.showMessageDialog(this, "Email atau password salah!", 
                    "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        registerButton.addActionListener(e -> showRegisterPanel());
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(formPanel);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= REGISTER PANEL =============
    private void showRegisterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        
        JPanel headerPanel = createHeaderPanel("Registrasi Pasien Baru");
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField namaField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField telpField = new JTextField(20);
        JTextField ktpField = new JTextField(20);
        JTextField tglLahirField = new JTextField(20);
        JTextField alamatField = new JTextField(20);
        JComboBox<String> jenisKelaminBox = new JComboBox<>(new String[]{"Laki-laki", "Perempuan"});
        
        addFormField(formPanel, gbc, 0, "Nama Lengkap:", namaField);
        addFormField(formPanel, gbc, 1, "Email:", emailField);
        addFormField(formPanel, gbc, 2, "Password:", passwordField);
        addFormField(formPanel, gbc, 3, "No. Telepon:", telpField);
        addFormField(formPanel, gbc, 4, "No. KTP:", ktpField);
        addFormField(formPanel, gbc, 5, "Tanggal Lahir:", tglLahirField);
        addFormField(formPanel, gbc, 6, "Alamat:", alamatField);
        addFormField(formPanel, gbc, 7, "Jenis Kelamin:", jenisKelaminBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);
        JButton registerButton = createStyledButton("Daftar", new Color(76, 175, 80));
        JButton backButton = createStyledButton("Kembali", new Color(158, 158, 158));
        
        registerButton.addActionListener(e -> {
            String userId = "P" + String.format("%03d", users.size() + 1);
            Pasien newPasien = new Pasien(userId, namaField.getText(), emailField.getText(),
                new String(passwordField.getPassword()), telpField.getText(), ktpField.getText(),
                tglLahirField.getText(), alamatField.getText(), (String)jenisKelaminBox.getSelectedItem());
            users.add(newPasien);
            JOptionPane.showMessageDialog(this, "Registrasi berhasil! ID Anda: " + userId);
            cardLayout.show(mainPanel, "LOGIN");
        });
        
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));
        
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);
        
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(240, 248, 255));
        centerPanel.add(formPanel);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        
        mainPanel.add(panel, "REGISTER");
        cardLayout.show(mainPanel, "REGISTER");
    }
    
    // ============= DASHBOARD =============
    private void showDashboard(User user) {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Sidebar
        JPanel sidebar = createSidebar(user);
        
        // Content Area
        JPanel contentArea = new JPanel(new CardLayout());
        contentArea.setBackground(new Color(240, 248, 255));
        
        if (user instanceof Pasien) {
            contentArea.add(createPasienDashboard((Pasien)user), "HOME");
            contentArea.add(createBookAppointmentPanel((Pasien)user), "BOOK");
            contentArea.add(createMyAppointmentsPanel((Pasien)user), "APPOINTMENTS");
            contentArea.add(createAsuransiPanel((Pasien)user), "ASURANSI");
            contentArea.add(createRiwayatMedisPanel((Pasien)user), "RIWAYAT");
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
    
    // ============= SIDEBAR =============
    private JPanel createSidebar(User user) {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(33, 33, 33));
        sidebar.setPreferredSize(new Dimension(250, 0));
        
        JLabel userLabel = new JLabel("  👤 " + user.getNama());
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        sidebar.add(userLabel);
        
        JLabel roleLabel = new JLabel("  " + user.getRole());
        roleLabel.setForeground(new Color(200, 200, 200));
        roleLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
        sidebar.add(roleLabel);
        
        sidebar.add(Box.createRigidArea(new Dimension(0, 20)));
        
        if (user instanceof Pasien) {
            sidebar.add(createMenuButton("🏠 Dashboard", "HOME"));
            sidebar.add(createMenuButton("📅 Buat Appointment", "BOOK"));
            sidebar.add(createMenuButton("📋 Appointment Saya", "APPOINTMENTS"));
            sidebar.add(createMenuButton("🏥 Paket Asuransi", "ASURANSI"));
            sidebar.add(createMenuButton("📊 Riwayat Medis", "RIWAYAT"));
        } else if (user instanceof Dokter) {
            sidebar.add(createMenuButton("🏠 Dashboard", "HOME"));
            sidebar.add(createMenuButton("📅 Appointment Pasien", "APPOINTMENTS"));
        } else if (user instanceof Admin) {
            sidebar.add(createMenuButton("🏠 Dashboard", "HOME"));
            sidebar.add(createMenuButton("👥 Kelola Users", "USERS"));
            sidebar.add(createMenuButton("⚕️ Kelola Dokter", "DOKTER"));
        }
        
        sidebar.add(Box.createVerticalGlue());
        
        JButton logoutButton = createMenuButton("🚪 Logout", "LOGOUT");
        logoutButton.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "LOGIN");
        });
        sidebar.add(logoutButton);
        
        return sidebar;
    }
    
    private JButton createMenuButton(String text, String action) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 45));
        button.setBackground(new Color(33, 33, 33));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(66, 66, 66));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(33, 33, 33));
            }
        });
        
        if (!action.equals("LOGOUT")) {
            button.addActionListener(e -> {
                CardLayout cl = (CardLayout) ((JPanel)button.getParent().getParent().getComponent(1)).getLayout();
                cl.show((JPanel)button.getParent().getParent().getComponent(1), action);
            });
        }
        
        return button;
    }
    
    // ============= PASIEN PANELS =============
    private JPanel createPasienDashboard(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Dashboard Pasien");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        long appointmentCount = appointments.stream().filter(a -> a.getPasien().equals(pasien)).count();
        statsPanel.add(createStatCard("📅 Total Appointment", String.valueOf(appointmentCount), new Color(33, 150, 243)));
        statsPanel.add(createStatCard("⚕️ Dokter Tersedia", String.valueOf(dokters.size()), new Color(76, 175, 80)));
        statsPanel.add(createStatCard("🏥 Paket Asuransi", 
            pasien.getPaketAsuransi() != null ? pasien.getPaketAsuransi().getNamaPaket() : "Belum Ada",
            new Color(255, 152, 0)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBookAppointmentPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Buat Appointment Baru");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 0, 0, 0),
            BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JComboBox<String> dokterBox = new JComboBox<>();
        for (Dokter d : dokters) {
            dokterBox.addItem(d.getNama() + " - " + d.getSpesialisasi());
        }
        
        JTextField tanggalField = new JTextField(15);
        JTextField waktuField = new JTextField(15);
        JTextArea keluhanArea = new JTextArea(5, 15);
        keluhanArea.setLineWrap(true);
        JScrollPane keluhanScroll = new JScrollPane(keluhanArea);
        
        addFormField(formPanel, gbc, 0, "Pilih Dokter:", dokterBox);
        addFormField(formPanel, gbc, 1, "Tanggal (DD-MM-YYYY):", tanggalField);
        addFormField(formPanel, gbc, 2, "Waktu (HH:MM):", waktuField);
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Keluhan:"), gbc);
        gbc.gridx = 1;
        formPanel.add(keluhanScroll, gbc);
        
        JButton submitButton = createStyledButton("Buat Appointment", new Color(33, 150, 243));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(submitButton, gbc);
        
        submitButton.addActionListener(e -> {
            String appointmentId = "APT" + String.format("%03d", appointments.size() + 1);
            Dokter selectedDokter = dokters.get(dokterBox.getSelectedIndex());
            Appointment apt = new Appointment(appointmentId, pasien, selectedDokter,
                tanggalField.getText(), waktuField.getText(), keluhanArea.getText());
            appointments.add(apt);
            JOptionPane.showMessageDialog(this, "Appointment berhasil dibuat!\nID: " + appointmentId);
            tanggalField.setText("");
            waktuField.setText("");
            keluhanArea.setText("");
        });
        
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createMyAppointmentsPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Appointment Saya");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Dokter", "Tanggal", "Waktu", "Status", "Keluhan"};
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
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createAsuransiPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Paket Asuransi");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel paketsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        paketsPanel.setBackground(new Color(240, 248, 255));
        paketsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        for (PaketAsuransi paket : paketAsuransiList) {
            paketsPanel.add(createPaketCard(paket, pasien));
        }
        
        panel.add(paketsPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createRiwayatMedisPanel(Pasien pasien) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Riwayat Medis");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        JTextArea riwayatArea = new JTextArea();
        riwayatArea.setEditable(false);
        riwayatArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        riwayatArea.setText("Riwayat medis untuk pasien: " + pasien.getNama() + "\n\n");
        riwayatArea.append("Riwayat kunjungan akan ditampilkan di sini setelah appointment selesai.\n");
        
        JScrollPane scrollPane = new JScrollPane(riwayatArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(20, 0, 0, 0),
            BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }
    
    // ============= DOKTER PANELS =============
    private JPanel createDokterDashboard(Dokter dokter) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Dashboard Dokter");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        long appointmentCount = appointments.stream().filter(a -> a.getDokter().equals(dokter)).count();
        statsPanel.add(createStatCard("📅 Total Pasien", String.valueOf(appointmentCount), new Color(33, 150, 243)));
        statsPanel.add(createStatCard("⭐ Rating", String.format("%.1f/5.0", dokter.getRating()), new Color(255, 193, 7)));
        statsPanel.add(createStatCard("⏰ Jam Praktik", dokter.getJamPraktik(), new Color(76, 175, 80)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createDokterAppointmentsPanel(Dokter dokter) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Appointment Pasien");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Pasien", "Tanggal", "Waktu", "Keluhan", "Status", "Aksi"};
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
                    "Detail"
                });
            }
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (col == 6 && row >= 0) {
                    showAppointmentDetail(appointments.get(row));
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= ADMIN PANELS =============
    private JPanel createAdminDashboard() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Dashboard Admin");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(new Color(240, 248, 255));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        long pasienCount = users.stream().filter(u -> u instanceof Pasien).count();
        long dokterCount = users.stream().filter(u -> u instanceof Dokter).count();
        
        statsPanel.add(createStatCard("👥 Total Pasien", String.valueOf(pasienCount), new Color(33, 150, 243)));
        statsPanel.add(createStatCard("⚕️ Total Dokter", String.valueOf(dokterCount), new Color(76, 175, 80)));
        statsPanel.add(createStatCard("📅 Total Appointment", String.valueOf(appointments.size()), new Color(255, 152, 0)));
        
        panel.add(statsPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createManageUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Kelola Pengguna");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Nama", "Email", "Role", "Telepon"};
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
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createManageDokterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel title = new JLabel("Kelola Dokter");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panel.add(title, BorderLayout.NORTH);
        
        String[] columns = {"ID", "Nama", "Spesialisasi", "Pengalaman", "Rating", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        for (Dokter dokter : dokters) {
            model.addRow(new Object[]{
                dokter.getUserId(),
                "Dr. " + dokter.getNama(),
                dokter.getSpesialisasi(),
                dokter.getPengalaman() + " tahun",
                String.format("%.1f", dokter.getRating()),
                dokter.isTersedia() ? "Tersedia" : "Tidak Tersedia"
            });
        }
        
        JTable table = new JTable(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    // ============= HELPER METHODS =============
    private JPanel createHeaderPanel(String text) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(25, 118, 210));
        panel.setPreferredSize(new Dimension(0, 80));
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 28));
        label.setForeground(Color.WHITE);
        panel.add(label);
        return panel;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleLabel.setForeground(Color.GRAY);
        
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(color);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createPaketCard(PaketAsuransi paket, Pasien pasien) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        
        JLabel nameLabel = new JLabel(paket.getNamaPaket());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel priceLabel = new JLabel(String.format("Rp %,.0f/bulan", paket.getIuranBulanan()));
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        priceLabel.setForeground(new Color(76, 175, 80));
        priceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel limitLabel = new JLabel(String.format("Limit: Rp %,.0f/tahun", paket.getLimitPertahun()));
        limitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        limitLabel.setForeground(Color.GRAY);
        limitLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextArea fasilitasArea = new JTextArea();
        fasilitasArea.setEditable(false);
        fasilitasArea.setBackground(Color.WHITE);
        fasilitasArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        for (String fasilitas : paket.getFasilitasTersedia()) {
            fasilitasArea.append("✓ " + fasilitas + "\n");
        }
        
        JButton pilihButton = createStyledButton("Pilih Paket", new Color(33, 150, 243));
        pilihButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        pilihButton.addActionListener(e -> {
            pasien.setPaketAsuransi(paket);
            JOptionPane.showMessageDialog(this, "Paket " + paket.getNamaPaket() + " berhasil dipilih!");
        });
        
        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(priceLabel);
        card.add(limitLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(fasilitasArea);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(pilihButton);
        
        return card;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(jLabel, gbc);
        
        gbc.gridx = 1;
        field.setPreferredSize(new Dimension(250, 35));
        panel.add(field, gbc);
    }
    
    private void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(30);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        table.getTableHeader().setBackground(new Color(33, 150, 243));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(200, 230, 255));
        table.setGridColor(new Color(230, 230, 230));
    }
    
    private User authenticateUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) && password.equals("pass123") || 
                password.equals("admin123")) {
                return user;
            }
        }
        return null;
    }
    
    private void showAppointmentDetail(Appointment appointment) {
        JDialog dialog = new JDialog(this, "Detail Appointment", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        detailArea.setText(String.format(
            "ID Appointment: %s\n" +
            "Pasien: %s\n" +
            "Tanggal: %s %s\n" +
            "Keluhan: %s\n" +
            "Status: %s\n\n" +
            "Diagnosa: %s\n" +
            "Resep: %s",
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
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        JButton confirmButton = createStyledButton("Konfirmasi", new Color(76, 175, 80));
        JButton completeButton = createStyledButton("Selesai", new Color(33, 150, 243));
        JButton closeButton = createStyledButton("Tutup", new Color(158, 158, 158));
        
        confirmButton.addActionListener(e -> {
            appointment.setStatus("CONFIRMED");
            JOptionPane.showMessageDialog(dialog, "Appointment dikonfirmasi!");
            dialog.dispose();
        });
        
        completeButton.addActionListener(e -> {
            String diagnosa = JOptionPane.showInputDialog(dialog, "Masukkan diagnosa:");
            String resep = JOptionPane.showInputDialog(dialog, "Masukkan resep obat:");
            if (diagnosa != null && resep != null) {
                appointment.setDiagnosa(diagnosa);
                appointment.setResepObat(resep);
                appointment.setStatus("COMPLETED");
                JOptionPane.showMessageDialog(dialog, "Appointment diselesaikan!");
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

// ============= MODEL CLASSES (from original code) =============

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
        System.out.println("=== INFO PASIEN ===");
    }
    
    @Override
    public String getRole() {
        return "PASIEN";
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
        System.out.println("=== INFO DOKTER ===");
    }
    
    @Override
    public String getRole() {
        return "DOKTER";
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
        System.out.println("=== INFO ADMIN ===");
    }
    
    @Override
    public String getRole() {
        return "ADMIN";
    }
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

class RiwayatMedis {
    private String idPasien;
    private ArrayList<String> riwayatPenyakit;
    private ArrayList<String> alergi;
    private ArrayList<Appointment> riwayatKunjungan;
    
    public RiwayatMedis(String idPasien) {
        this.idPasien = idPasien;
        this.riwayatPenyakit = new ArrayList<>();
        this.alergi = new ArrayList<>();
        this.riwayatKunjungan = new ArrayList<>();
    }
    
    public void tambahRiwayatPenyakit(String penyakit) {
        riwayatPenyakit.add(penyakit);
    }
    
    public void tambahAlergi(String alergiItem) {
        alergi.add(alergiItem);
    }
    
    public void tambahKunjungan(Appointment appointment) {
        riwayatKunjungan.add(appointment);
    }
}