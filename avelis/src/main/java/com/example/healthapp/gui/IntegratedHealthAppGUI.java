package com.example.healthapp.gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.healthapp.Appointment;
import com.example.healthapp.Claim;
import com.example.healthapp.Dokter;
import com.example.healthapp.InMemoryRepository;
import com.example.healthapp.Insurance;
import com.example.healthapp.InsuranceService;
import com.example.healthapp.MockNewsService;
import com.example.healthapp.NewsService;
import com.example.healthapp.Patient;
import com.example.healthapp.Poli;
import com.example.healthapp.Provider;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Integrated Health Management System GUI
 * Beranda sebagai tab utama dengan font hitam
 */
public class IntegratedHealthAppGUI extends Application {

    // Repositories and Services
    private InMemoryRepository<Patient> patientRepo = new InMemoryRepository<>();
    private InMemoryRepository<Provider> providerRepo = new InMemoryRepository<>();
    private InMemoryRepository<Poli> poliRepo = new InMemoryRepository<>();
    private InMemoryRepository<Dokter> dokterRepo = new InMemoryRepository<>();
    private InMemoryRepository<Appointment> appointmentRepo = new InMemoryRepository<>();
    private InsuranceService insuranceService;
    private NewsService newsService = new MockNewsService();

    // UI Components
    private TabPane tabPane;
    private Map<String, Tab> tabMap = new HashMap<>();
    
    // ListViews
    private ListView<String> patientListView = new ListView<>();
    private ListView<String> claimsListView = new ListView<>();
    private ListView<String> appointmentsListView = new ListView<>();
    private ListView<String> queueListView = new ListView<>();
    private ListView<String> newsListView = new ListView<>();
    private ListView<String> poliListView = new ListView<>();
    private ListView<String> dokterListView = new ListView<>();
    
    // Common UI controls
    private ComboBox<String> cbPatientBox, cbPoliBox, cbDokterBox;

    // Constants
    private static final String PRIMARY_COLOR = "#009B88";
    private static final String SECONDARY_COLOR = "#007B6E";
    private static final String BACKGROUND_COLOR = "#f5f5f5";
    private static final String TEXT_BLACK = "#000000";
    private static final Font TITLE_FONT = Font.font("Segoe UI", 18);
    private static final Font SUBTITLE_FONT = Font.font("Segoe UI", 14);
    private static final Font NORMAL_FONT = Font.font("Segoe UI", 12);

    @Override
    public void init() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        insuranceService = new InsuranceService(patientRepo, providerRepo);
        
        // Sample Providers
        createProvider("HSP01", "Rumah Sakit Sehat", "Jakarta");
        createProvider("HSP02", "Klinik Maju", "Bandung");

        // Sample Poli
        Poli[] polis = {
            new Poli("POLI-A", "Poli Anak", "Spesialis penyakit anak-anak"),
            new Poli("POLI-U", "Poli Umum", "Konsultasi kesehatan umum"),
            new Poli("POLI-G", "Poli Gigi", "Perawatan dan pemeriksaan gigi"),
            new Poli("POLI-M", "Poli Mata", "Pemeriksaan kesehatan mata"),
            new Poli("POLI-H", "Poli Kandungan", "Perawatan ibu hamil dan melahirkan")
        };
        
        for (Poli poli : polis) {
            poliRepo.save(poli.getId(), poli);
        }

        // Sample Doctors
        Dokter[] doctors = {
            new Dokter("D001", "Dr. Suryo", "POLI-A", "HSP01"),
            new Dokter("D002", "Dr. Dewi Lestari", "POLI-U", "HSP01"),
            new Dokter("D003", "Dr. Adi Prasetyo", "POLI-G", "HSP02"),
            new Dokter("D004", "Dr. Siti Nurhaliza", "POLI-M", "HSP01"),
            new Dokter("D005", "Dr. Budi Kartono", "POLI-H", "HSP02"),
            new Dokter("D006", "Dr. Rina Wijaya", "POLI-A", "HSP02")
        };
        
        for (Dokter doctor : doctors) {
            dokterRepo.save(doctor.getId(), doctor);
            Poli poli = poliRepo.findById(doctor.getPoliId()).orElse(null);
            if (poli != null) {
                poli.addDoctorId(doctor.getId());
                poliRepo.save(poli.getId(), poli);
            }
        }

        // Sample Patients
        Patient[] patients = {
            new Patient("P001", "Budi Santoso", "budi@example.com", LocalDate.of(1990, 5, 20)),
            new Patient("P002", "Siti Aminah", "siti@example.com", LocalDate.of(1985, 8, 12)),
            new Patient("P003", "Ahmad Wijaya", "ahmad@example.com", LocalDate.of(1988, 3, 15)),
            new Patient("P004", "Nur Azizah", "nur@example.com", LocalDate.of(1992, 11, 25)),
            new Patient("P005", "Rudi Hermawan", "rudi@example.com", LocalDate.of(1987, 7, 8))
        };
        
        for (Patient patient : patients) {
            patientRepo.save(patient.getId(), patient);
        }

        // Enroll patients in insurance
        Insurance.Tier[] tiers = {Insurance.Tier.BASIC, Insurance.Tier.STANDARD, Insurance.Tier.PREMIUM, Insurance.Tier.BASIC, Insurance.Tier.STANDARD};
        for (int i = 0; i < patients.length; i++) {
            insuranceService.enroll(patients[i].getId(), tiers[i]);
        }

        // Sample Appointments
        LocalDateTime now = LocalDateTime.now();
        Appointment[] appointments = {
            new Appointment("P001", "D002", now.plusDays(1).withHour(9).withMinute(0), "Pemeriksaan rutin"),
            new Appointment("P002", "D003", now.plusDays(2).withHour(10).withMinute(30), "Scaling dan pembersihan gigi"),
            new Appointment("P003", "D001", now.plusDays(1).withHour(14).withMinute(0), "Konsultasi demam dan batuk"),
            new Appointment("P004", "D004", now.plusDays(3).withHour(13).withMinute(0), "Pemeriksaan mata berkala"),
            new Appointment("P005", "D005", now.plusDays(5).withHour(11).withMinute(0), "Konsultasi kesehatan umum")
        };
        
        for (Appointment appointment : appointments) {
            appointmentRepo.save(appointment.getId(), appointment);
            Patient patient = patientRepo.findById(appointment.getPatientId()).orElse(null);
            if (patient != null) {
                patient.addAppointment(appointment);
                patientRepo.save(patient.getId(), patient);
            }
        }

        // Sample Claims
        int claimCount = 0;
        for (Patient patient : patientRepo.findAll()) {
            if (claimCount >= 5 || !patient.isEnrolled()) continue;
            try {
                insuranceService.submitClaim(
                    patient.getId(),
                    100000 + (claimCount * 50000),
                    LocalDate.now().minusDays(claimCount),
                    "HSP01",
                    "Klaim konsultasi " + claimCount
                );
                claimCount++;
            } catch (Exception e) {
                // Skip if error
            }
        }
    }

    private void createProvider(String id, String name, String location) {
        providerRepo.save(id, new Provider(id, name, location));
    }

    @Override
    public void start(Stage primaryStage) {
        setupPrimaryStage(primaryStage);
        setupTabSystem();
        setupMainScene(primaryStage);
        refreshAllData();
    }

    private void setupPrimaryStage(Stage stage) {
        stage.setTitle("Integrated Health Management System v2.0");
        stage.setWidth(1600);
        stage.setHeight(900);
    }

    private void setupTabSystem() {
        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Create all tabs - BERANDA sebagai tab utama
        Tab[] tabs = {
            createBerandaTab(),           // Tab utama menggantikan Dashboard
            createInformasiTab(),
            createDaftarAntarianTab(),
            createPatientsTab(),
            createPoliDokterTab(),
            createAppointmentsTab(),
            createHealthInsuranceTab(),
            createNewsTab()
        };

        tabPane.getTabs().addAll(tabs);
        setupTabMapping();
    }

    private void setupTabMapping() {
        // Map action names to corresponding tabs
        String[][] tabMappings = {
            {"📋 Buat Janji Temu", "Appointments"},
            {"👤 Daftar Pasien Baru", "Patients"},
            {"💳 Proses Klaim Asuransi", "Health Insurance"},
            {"📊 Lihat Laporan", "🏠 Beranda"},
            {"🩺 Lihat Riwayat Medis", "Patients"},
            {"⚙️ Pengaturan Sistem", "🏠 Beranda"},
            {"Kelola Pasien", "Patients"},
            {"Janji Temu", "Appointments"},
            {"Asuransi", "Health Insurance"},
            {"Poli & Dokter", "Poli & Dokter"},
            {"Antrian", "Daftar Antrian"},
            {"Berita", "News"}
        };

        for (String[] mapping : tabMappings) {
            tabMap.put(mapping[0], findTabByTitle(mapping[1]));
        }
    }

    private void setupMainScene(Stage stage) {
        BorderPane root = new BorderPane();
        root.setTop(createProfessionalHeader());
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 900, 600);
        applyProfessionalStyling(scene);
        
        stage.setScene(scene);
        stage.setX(100);
        stage.setY(100);
        stage.show();
    }

    private void refreshAllData() {
        refreshPatients();
        refreshClaims();
        refreshNews();
        refreshAppointments();
        refreshPoliDokter();
    }

    // ===== HEADER & STYLING =====
    private VBox createProfessionalHeader() {
        VBox header = new VBox();
        header.setStyle(
            "-fx-background-color: linear-gradient(to right, #1a5f4a, " + PRIMARY_COLOR + " 50%, " + SECONDARY_COLOR + "); " +
            "-fx-padding: 12 20 12 20;"
        );
        header.setPrefHeight(60);
        
        HBox titleBox = new HBox(15);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        
        Label logo = new Label("⚕️");
        logo.setFont(Font.font("Segoe UI", 28));
        
        VBox titleSection = new VBox(0);
        Label mainTitle = new Label("Integrated Health Management System");
        mainTitle.setFont(Font.font("Segoe UI", 16));
        mainTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Label subtitle = new Label("Professional Healthcare Administration Platform");
        subtitle.setFont(Font.font("Segoe UI", 10));
        subtitle.setStyle("-fx-text-fill: #e0f2f1;");
        
        titleSection.getChildren().addAll(mainTitle, subtitle);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox statusBox = new HBox(15);
        statusBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label status = new Label("✓ System Status: Online");
        status.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold; -fx-font-size: 11;");
        
        Label time = new Label(LocalDateTime.now().format(
            DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")));
        time.setStyle("-fx-text-fill: #e0f2f1; -fx-font-size: 11;");
        
        statusBox.getChildren().addAll(status, time);
        titleBox.getChildren().addAll(logo, titleSection, spacer, statusBox);
        header.getChildren().add(titleBox);
        
        return header;
    }

    private void applyProfessionalStyling(Scene scene) {
        String professionalCSS = 
            ".root { -fx-base: " + BACKGROUND_COLOR + "; -fx-background: " + BACKGROUND_COLOR + "; -fx-font-family: 'Segoe UI', 'System'; }" +
            ".tab-pane .tab-header-background { -fx-background-color: linear-gradient(to right, #1a5f4a, " + PRIMARY_COLOR + "); }" +
            ".tab-pane .tab { -fx-background-color: #e8e8e8; -fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold; -fx-font-size: 11; -fx-padding: 8 16 8 16; }" +
            ".tab-pane .tab:selected { -fx-background-color: linear-gradient(to right, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + "); -fx-text-fill: white; }" +
            ".button { -fx-background-color: linear-gradient(to bottom, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + "); -fx-text-fill: white; -fx-font-size: 11; -fx-font-weight: bold; -fx-padding: 8 16 8 16; -fx-border-radius: 4; -fx-background-radius: 4; -fx-cursor: hand; }" +
            ".button:hover { -fx-background-color: linear-gradient(to bottom, " + SECONDARY_COLOR + ", #005B52); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 1); }" +
            ".text-field, .combo-box, .date-picker { -fx-border-color: #d0d0d0; -fx-border-width: 1; -fx-padding: 8 10 8 10; -fx-font-size: 11; -fx-text-fill: " + TEXT_BLACK + "; }" +
            ".text-field:focused, .combo-box:focused { -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-width: 2; }" +
            ".list-view { -fx-control-inner-background: white; -fx-border-color: #d0d0d0; -fx-border-width: 1; -fx-font-size: 11; }" +
            ".list-view:focused { -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-width: 2; }" +
            ".list-view .list-cell { -fx-padding: 8 10 8 10; -fx-font-size: 11; -fx-text-fill: " + TEXT_BLACK + "; }" +
            ".list-view .list-cell:filled:selected { -fx-background-color: linear-gradient(to right, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + "); -fx-text-fill: white; }" +
            ".label { -fx-text-fill: " + TEXT_BLACK + "; -fx-font-size: 11; }" +
            ".title { -fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold; -fx-font-size: 18; }" +
            ".panel { -fx-background-color: white; -fx-border-color: #d0d0d0; -fx-border-radius: 4; -fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 1); }";

        scene.getStylesheets().add("data:text/css," + professionalCSS);
    }

    // ===== REUSABLE UI COMPONENTS =====
    private VBox createInfoCard(String title, String content) {
        VBox card = new VBox(8);
        card.setPrefSize(200, 100);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-radius: 6; -fx-border-width: 1; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 3, 0, 0, 1);");
        card.setAlignment(Pos.TOP_LEFT);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", 12));
        titleLabel.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold;");
        
        Label contentLabel = new Label(content);
        contentLabel.setFont(NORMAL_FONT);
        contentLabel.setWrapText(true);
        contentLabel.setStyle("-fx-text-fill: " + TEXT_BLACK + ";");
        
        card.getChildren().addAll(titleLabel, contentLabel);
        return card;
    }

    private Button createActionButton(String text, String targetTab) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setStyle("-fx-background-color: white; -fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold; -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-width: 2; -fx-border-radius: 4; -fx-padding: 8 16 8 16;");
        
        button.setOnAction(e -> handleQuickAction(text));
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f0fdfb; -fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold; -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-width: 2; -fx-border-radius: 4; -fx-padding: 8 16 8 16; -fx-effect: dropshadow(gaussian, rgba(0,155,136,0.2), 4, 0, 0, 1);"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; -fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold; -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-width: 2; -fx-border-radius: 4; -fx-padding: 8 16 8 16;"));
        
        return button;
    }

    // ===== TAB CREATION METHODS =====
    private Tab createBerandaTab() {
        Tab tab = new Tab("🏠 Beranda");
        tab.setClosable(false);
        
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        
        // Welcome Section
        VBox welcomeSection = createGradientPanel("Selamat Datang di Sistem Manajemen Kesehatan", 
            "Platform terintegrasi untuk mengelola pasien, janji temu, asuransi, dan informasi kesehatan dengan mudah dan efisien.");
        
        // Quick Actions - TANPA RINGKASAN SISTEM
        VBox quickActionsSection = new VBox(15);
        quickActionsSection.setPadding(new Insets(20));
        quickActionsSection.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 1);"
        );
        
        Label quickTitle = new Label("⚡ Akses Cepat");
        quickTitle.setFont(SUBTITLE_FONT);
        quickTitle.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold;");
        
        HBox quickActionsGrid = new HBox(15);
        
        String[] quickActions = {
            "📋 Buat Janji Temu", "👤 Daftar Pasien Baru", "💳 Proses Klaim Asuransi",
            "📊 Lihat Laporan", "🩺 Lihat Riwayat Medis", "⚙️ Pengaturan Sistem"
        };
        
        for (String action : quickActions) {
            quickActionsGrid.getChildren().add(createActionButton(action, action));
        }
        
        ScrollPane quickScroll = new ScrollPane(quickActionsGrid);
        quickScroll.setFitToWidth(true);
        quickScroll.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        
        quickActionsSection.getChildren().addAll(quickTitle, quickScroll);
        
        // System Info
        HBox sysInfoRow = new HBox(15);
        sysInfoRow.getChildren().addAll(
            createInfoCard("👥 Pasien Terdaftar", patientRepo.findAll().size() + " pasien terdaftar"),
            createInfoCard("✓ Asuransi Aktif", getEnrolledPatientCount() + " pasien terdaftar asuransi"),
            createInfoCard("📅 Janji Hari Ini", getTodayAppointmentsCount() + " janji temu"),
            createInfoCard("🩺 Dokter Tersedia", dokterRepo.findAll().size() + " dokter aktif")
        );
        
        // Recent Activities
        VBox activitiesSection = new VBox(15);
        activitiesSection.setPadding(new Insets(20));
        activitiesSection.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);"
        );
        
        Label activitiesTitle = new Label("📈 Aktivitas Terkini");
        activitiesTitle.setFont(SUBTITLE_FONT);
        activitiesTitle.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold;");
        
        ListView<String> activitiesList = new ListView<>();
        activitiesList.setPrefHeight(150);
        activitiesList.setStyle("-fx-control-inner-background: white; -fx-border-color: #e0e0e0; -fx-text-fill: " + TEXT_BLACK + ";");
        activitiesList.getItems().addAll(
            "✓ Pasien Budi Santoso mendaftar asuransi tier PREMIUM - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " 10:30",
            "✓ Klaim dari Siti Aminah disetujui - Total: Rp 100,000 - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " 09:15",
            "✓ Dr. Dewi Lestari menambahkan jadwal konsultasi baru - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " 08:45",
            "✓ 3 pasien sudah check-in untuk antrian hari ini - " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + " 08:20"
        );
        
        activitiesSection.getChildren().addAll(activitiesTitle, activitiesList);
        
        vbox.getChildren().addAll(welcomeSection, quickActionsSection, sysInfoRow, activitiesSection);
        
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        
        tab.setContent(scrollPane);
        return tab;
    }

    private Tab createInformasiTab() {
        Tab tab = new Tab("📚 Informasi");
        tab.setClosable(false);
        
        VBox vbox = new VBox(0);
        vbox.setPadding(new Insets(0));
        vbox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        
        // Header
        VBox headerSection = createGradientPanel("Informasi Kesehatan dan Berita Industri", 
            "Dapatkan update terbaru tentang perkembangan kesehatan dan edukasi medis");
        
        // Search and Categories
        VBox contentSection = new VBox(15);
        contentSection.setPadding(new Insets(20));
        
        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Cari artikel atau topik kesehatan...");
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-text-fill: " + TEXT_BLACK + ";");
        Button searchBtn = new Button("Cari");
        searchBtn.setOnAction(e -> performSearch(searchField.getText()));
        searchBox.getChildren().addAll(searchField, searchBtn);
        
        // Information list
        ListView<String> infoList = createStyledListView(400);
        infoList.getItems().addAll(
            "[TERBARU] 🩺 Panduan Kesehatan Umum - Kesehatan yang baik adalah investasi terbaik | " + LocalDate.now().minusDays(2) + " | Sumber: Kemenkes",
            "[FEATURED] 💊 Penggunaan Obat Yang Aman - Pelajari cara menggunakan obat dengan benar | " + LocalDate.now().minusDays(4) + " | Sumber: WHO",
            "🥗 Nutrisi Seimbang - Tips makanan sehat untuk gaya hidup lebih baik | " + LocalDate.now().minusDays(6) + " | Sumber: Ahli Gizi"
        );
        
        contentSection.getChildren().addAll(searchBox, infoList);
        vbox.getChildren().addAll(headerSection, contentSection);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createDaftarAntarianTab() {
        Tab tab = new Tab("Daftar Antrian");
        tab.setClosable(false);
        
        VBox vbox = new VBox(0);
        vbox.setPadding(new Insets(0));
        vbox.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");
        
        // Header
        VBox headerSection = new VBox(15);
        headerSection.setPadding(new Insets(25, 20, 20, 20));
        headerSection.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        
        Label headerTitle = new Label("📋 Daftar Antrian dan Konsultasi Saya");
        headerTitle.setFont(TITLE_FONT);
        headerTitle.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold;");
        
        HBox searchBox = createSearchBox("🔍 Cari kode antrian atau pasien...");
        
        headerSection.getChildren().addAll(headerTitle, searchBox);
        
        // Content
        VBox contentSection = new VBox(15);
        contentSection.setPadding(new Insets(20));
        
        // Info bar
        HBox infoBar = createInfoBar("⏰ Total Antrian: " + appointmentRepo.findAll().size() + " | Waktu Tunggu Rata-rata: 15 menit");
        
        // Queue list
        ListView<String> queueList = createStyledListView(400);
        refreshQueueDisplay(queueList);
        
        // Action buttons
        HBox buttonBar = new HBox(10);
        Button btnCheckIn = createPrimaryButton("✓ Check In Sekarang");
        Button btnRefresh = createPrimaryButton("🔄 Refresh Antrian");
        
        btnCheckIn.setOnAction(e -> handleCheckIn(queueList));
        btnRefresh.setOnAction(e -> refreshQueueDisplay(queueList));
        
        buttonBar.getChildren().addAll(btnCheckIn, btnRefresh);
        
        contentSection.getChildren().addAll(infoBar, queueList, buttonBar);
        vbox.getChildren().addAll(headerSection, contentSection);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createPatientsTab() {
        Tab tab = new Tab("Patients");
        tab.setClosable(false);
        
        VBox vbox = createStyledVBox();
        Label title = createTitleLabel("Manajemen Pasien");
        
        // Add Patient Form
        HBox addForm = createAddPatientForm();
        
        // Patient List
        setupListView(patientListView, 400);
        refreshPatients();
        
        // Enroll Button
        Button btnEnroll = createPrimaryButton("Daftarkan Asuransi");
        btnEnroll.setOnAction(e -> enrollSelectedPatient());
        
        vbox.getChildren().addAll(title, addForm, patientListView, btnEnroll);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createPoliDokterTab() {
        Tab tab = new Tab("Poli & Dokter");
        tab.setClosable(false);

        VBox vbox = createStyledVBox();
        Label title = createTitleLabel("Manajemen Poli dan Dokter");

        // Add Poli Form
        HBox poliForm = createAddPoliForm();
        
        // Add Dokter Form
        HBox dokterForm = createAddDokterForm();
        
        // Lists
        setupListView(poliListView, 150);
        setupListView(dokterListView, 150);
        refreshPoliDokter();

        vbox.getChildren().addAll(title, new Label("Tambah Poli:"), poliForm, new Label("Tambah Dokter:"), dokterForm, 
                                new Label("Daftar Poli"), poliListView, new Label("Daftar Dokter"), dokterListView);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createAppointmentsTab() {
        Tab tab = new Tab("Appointments");
        tab.setClosable(false);

        VBox vbox = createStyledVBox();
        Label title = createTitleLabel("Manajemen Janji Temu");

        // Appointment Form
        HBox appointmentForm = createAppointmentForm();
        
        // Appointments List
        setupListView(appointmentsListView, 300);
        refreshAppointments();
        
        // Action Buttons
        HBox actionButtons = createAppointmentActionButtons();

        vbox.getChildren().addAll(title, appointmentForm, appointmentsListView, actionButtons);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createHealthInsuranceTab() {
        Tab tab = new Tab("Health Insurance");
        tab.setClosable(false);

        TabPane insuranceTabPane = new TabPane();
        insuranceTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // Registration Tab
        Tab tabRegistration = createInsuranceRegistrationTab();
        // Claims Tab
        Tab tabClaims = createInsuranceClaimsTab();

        insuranceTabPane.getTabs().addAll(tabRegistration, tabClaims);
        tab.setContent(insuranceTabPane);
        return tab;
    }

    private Tab createInsuranceRegistrationTab() {
        Tab tab = new Tab("Pendaftaran");
        VBox vbox = createStyledVBox();
        
        Label title = createTitleLabel("Pendaftaran Asuransi Kesehatan");
        VBox tiersInfo = createTiersInfoPanel();
        
        // Registration Form
        HBox registrationForm = createInsuranceRegistrationForm();
        
        // Enrolled Patients List
        Label lblEnrolled = new Label("Pasien Terdaftar Asuransi:");
        lblEnrolled.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold;");
        ListView<String> enrolledList = createStyledListView(200);
        refreshEnrolledPatients(enrolledList);
        
        Button btnRefresh = createPrimaryButton("Refresh");
        btnRefresh.setOnAction(e -> refreshEnrolledPatients(enrolledList));
        
        vbox.getChildren().addAll(title, tiersInfo, new Label(""), lblEnrolled, registrationForm, enrolledList, btnRefresh);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createInsuranceClaimsTab() {
        Tab tab = new Tab("Klaim");
        VBox vbox = createStyledVBox();
        
        Label title = createTitleLabel("Manajemen Klaim Asuransi");
        
        // Claims Form
        HBox claimsForm = createClaimsForm();
        
        // Claims List
        setupListView(claimsListView, 250);
        refreshClaims();
        
        Button btnApprove = createPrimaryButton("Setujui Klaim");
        btnApprove.setOnAction(e -> approveSelectedClaim());
        
        vbox.getChildren().addAll(title, claimsForm, claimsListView, btnApprove);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createNewsTab() {
        Tab tab = new Tab("News");
        tab.setClosable(false);

        VBox vbox = createStyledVBox();
        Label title = createTitleLabel("Berita Kesehatan");

        setupListView(newsListView, 450);
        refreshNews();
        
        Button btnRefresh = createPrimaryButton("Refresh");
        btnRefresh.setOnAction(e -> refreshNews());

        vbox.getChildren().addAll(title, newsListView, btnRefresh);
        tab.setContent(vbox);
        return tab;
    }

    // ===== FORM CREATION METHODS =====
    private HBox createAddPatientForm() {
        HBox form = new HBox(8);
        TextField tfId = createTextField("ID (e.g. P003)");
        TextField tfName = createTextField("Nama lengkap");
        TextField tfEmail = createTextField("Email");
        DatePicker dpBirth = new DatePicker();
        
        Button btnAdd = createPrimaryButton("Tambah Pasien");
        btnAdd.setOnAction(e -> addPatient(tfId, tfName, tfEmail, dpBirth));
        
        form.getChildren().addAll(tfId, tfName, tfEmail, dpBirth, btnAdd);
        return form;
    }

    private HBox createAddPoliForm() {
        HBox form = new HBox(8);
        TextField tfId = createTextField("ID Poli (e.g. POLI-A)");
        TextField tfName = createTextField("Nama Poli");
        TextField tfDesc = createTextField("Deskripsi");
        
        Button btnAdd = createPrimaryButton("Tambah Poli");
        btnAdd.setOnAction(e -> addPoli(tfId, tfName, tfDesc));
        
        form.getChildren().addAll(tfId, tfName, tfDesc, btnAdd);
        return form;
    }

    private HBox createAddDokterForm() {
        HBox form = new HBox(8);
        TextField tfId = createTextField("ID Dokter (e.g. D003)");
        TextField tfName = createTextField("Nama Dokter");
        ComboBox<String> cbPoli = createComboBox("Poli");
        ComboBox<String> cbProv = createComboBox("Provider (opsional)");
        
        Button btnAdd = createPrimaryButton("Tambah Dokter");
        btnAdd.setOnAction(e -> addDokter(tfId, tfName, cbPoli, cbProv));
        
        refreshComboBoxes(cbPoli, cbProv);
        
        form.getChildren().addAll(tfId, tfName, cbPoli, cbProv, btnAdd);
        return form;
    }

    private HBox createAppointmentForm() {
        HBox form = new HBox(8);
        cbPatientBox = createComboBox("Pilih Pasien");
        cbPoliBox = createComboBox("Pilih Poli");
        cbDokterBox = createComboBox("Pilih Dokter");
        DatePicker dpDate = new DatePicker();
        TextField tfTime = createTextField("HH:mm (e.g. 09:30)");
        TextField tfReason = createTextField("Alasan kunjungan");
        
        Button btnMake = createPrimaryButton("Buat Janji");
        btnMake.setOnAction(e -> makeAppointment(dpDate, tfTime, tfReason));
        
        setupAppointmentComboBoxes();
        
        form.getChildren().addAll(cbPatientBox, cbPoliBox, cbDokterBox, dpDate, tfTime, tfReason, btnMake);
        return form;
    }

    private HBox createInsuranceRegistrationForm() {
        HBox form = new HBox(10);
        ComboBox<String> cbPatient = createComboBox("Pilih Pasien");
        ComboBox<String> cbTier = createComboBox("Pilih Paket Asuransi");
        
        for (Patient p : patientRepo.findAll()) {
            cbPatient.getItems().add(p.getId() + " - " + p.getName());
        }
        cbTier.getItems().addAll("BASIC - Rp 500.000/bulan", "STANDARD - Rp 1.000.000/bulan", "PREMIUM - Rp 2.000.000/bulan");
        
        Button btnRegister = createPrimaryButton("Daftar Asuransi");
        btnRegister.setOnAction(e -> registerInsurance(cbPatient, cbTier));
        
        form.getChildren().addAll(cbPatient, cbTier, btnRegister);
        return form;
    }

    private HBox createClaimsForm() {
        HBox form = new HBox(8);
        TextField tfPatientId = createTextField("ID Pasien (e.g. P001)");
        TextField tfAmount = createTextField("Jumlah (numeric)");
        TextField tfProvider = createTextField("ID Provider (e.g. HSP01)");
        TextField tfDesc = createTextField("Deskripsi");
        
        Button btnSubmit = createPrimaryButton("Ajukan Klaim");
        btnSubmit.setOnAction(e -> submitClaim(tfPatientId, tfAmount, tfProvider, tfDesc));
        
        form.getChildren().addAll(tfPatientId, tfAmount, tfProvider, tfDesc, btnSubmit);
        return form;
    }

    // ===== ACTION HANDLERS =====
    private void handleQuickAction(String action) {
        switch(action) {
            case "📋 Buat Janji Temu":
                navigateToTab(action);
                showAlert("Buat Janji Temu", "Silakan isi form untuk membuat janji temu baru.");
                break;
            case "👤 Daftar Pasien Baru":
                navigateToTab(action);
                showAlert("Daftar Pasien Baru", "Silakan isi form untuk mendaftarkan pasien baru.");
                break;
            case "💳 Proses Klaim Asuransi":
                navigateToTab(action);
                selectInsuranceClaimsTab();
                break;
            case "📊 Lihat Laporan":
                showAlert("Lihat Laporan", "Menampilkan dashboard dengan berbagai metrik dan laporan.");
                break;
            case "🩺 Lihat Riwayat Medis":
                navigateToTab(action);
                showAlert("Lihat Riwayat Medis", "Pilih pasien dari daftar untuk melihat riwayat medis lengkap.");
                break;
            case "⚙️ Pengaturan Sistem":
                showAlert("Pengaturan Sistem", "Sistem sedang berjalan dengan normal.\n• Database: In-Memory\n• Status: Online\n• Version: 2.0 Professional Edition");
                break;
            default:
                navigateToTab(action);
        }
    }

    private void addPatient(TextField tfId, TextField tfName, TextField tfEmail, DatePicker dpBirth) {
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();
        String email = tfEmail.getText().trim();
        LocalDate bd = dpBirth.getValue();
        
        if (id.isEmpty() || name.isEmpty()) {
            showAlert("Validasi", "ID dan nama wajib diisi.");
            return;
        }
        
        if (patientRepo.exists(id)) {
            showAlert("Data sudah ada", "Pasien dengan ID tersebut sudah terdaftar.");
            return;
        }
        
        Patient p = new Patient(id, name, email, bd == null ? LocalDate.now() : bd);
        patientRepo.save(id, p);
        refreshPatients();
        clearFields(tfId, tfName, tfEmail);
        dpBirth.setValue(null);
        showAlert("Berhasil", "Pasien " + name + " berhasil ditambahkan.");
    }

    private void addPoli(TextField tfId, TextField tfName, TextField tfDesc) {
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();
        String desc = tfDesc.getText().trim();
        
        if (id.isEmpty() || name.isEmpty()) {
            showAlert("Validasi", "ID dan nama poli wajib diisi.");
            return;
        }
        
        if (poliRepo.exists(id)) {
            showAlert("Data sudah ada", "Poli dengan ID tersebut sudah terdaftar.");
            return;
        }
        
        Poli pl = new Poli(id, name, desc);
        poliRepo.save(id, pl);
        refreshPoliDokter();
        clearFields(tfId, tfName, tfDesc);
        showAlert("Berhasil", "Poli " + name + " berhasil ditambahkan.");
    }

    private void addDokter(TextField tfId, TextField tfName, ComboBox<String> cbPoli, ComboBox<String> cbProv) {
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();
        String poliSel = cbPoli.getValue();
        String provSel = cbProv.getValue();
        
        if (id.isEmpty() || name.isEmpty() || poliSel == null) {
            showAlert("Validasi", "ID, nama dokter, dan poli wajib diisi.");
            return;
        }
        
        if (dokterRepo.exists(id)) {
            showAlert("Data sudah ada", "Dokter dengan ID tersebut sudah terdaftar.");
            return;
        }
        
        String poliId = poliSel.split(" - ")[0];
        String provId = provSel == null ? null : provSel.split(" - ")[0];
        Dokter dk = new Dokter(id, name, poliId, provId);
        dokterRepo.save(id, dk);
        
        // Attach to poli
        Poli pl = poliRepo.findById(poliId).orElse(null);
        if (pl != null) {
            pl.addDoctorId(id);
            poliRepo.save(pl.getId(), pl);
        }
        
        refreshPoliDokter();
        clearFields(tfId, tfName);
        cbPoli.setValue(null);
        cbProv.setValue(null);
        showAlert("Berhasil", "Dokter " + name + " berhasil ditambahkan.");
    }

    private void makeAppointment(DatePicker dpDate, TextField tfTime, TextField tfReason) {
        String patSel = cbPatientBox.getValue();
        String poliSel = cbPoliBox.getValue();
        String dokterSel = cbDokterBox.getValue();
        
        if (patSel == null || poliSel == null || dokterSel == null || dpDate.getValue() == null) {
            showAlert("Validasi", "Pasien, poli, dokter, dan tanggal wajib diisi.");
            return;
        }
        
        String patientId = patSel.split(" - ")[0];
        String dokterId = dokterSel.split(" - ")[0];
        String timeStr = tfTime.getText().trim();
        LocalTime time = LocalTime.of(9, 0);
        
        try {
            if (!timeStr.isEmpty()) {
                time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("H:mm"));
            }
        } catch (DateTimeParseException dtpe) {
            showAlert("Validasi", "Format waktu harus HH:mm.");
            return;
        }
        
        LocalDate d = dpDate.getValue();
        LocalDateTime dateTime = LocalDateTime.of(d, time);
        String reason = tfReason.getText().trim();

        try {
            Appointment appt = new Appointment(patientId, dokterId, dateTime, reason);
            Patient p = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Pasien tidak ditemukan: " + patientId));
            p.addAppointment(appt);
            patientRepo.save(p.getId(), p);
            appointmentRepo.save(appt.getId(), appt);
            showAlert("Berhasil", "Janji temu berhasil dibuat: " + appt.getId());
            refreshAppointments();
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    private void registerInsurance(ComboBox<String> cbPatient, ComboBox<String> cbTier) {
        String patSel = cbPatient.getValue();
        String tierSel = cbTier.getValue();
        
        if (patSel == null || tierSel == null) {
            showAlert("Validasi", "Pilih pasien dan paket asuransi terlebih dahulu.");
            return;
        }
        
        String patientId = patSel.split(" - ")[0];
        String tierStr = tierSel.split(" - ")[0];
        
        try {
            Insurance.Tier tier = Insurance.Tier.valueOf(tierStr);
            insuranceService.enroll(patientId, tier);
            showAlert("Berhasil", "Pasien " + patSel + " berhasil didaftarkan dalam paket " + tierStr);
            cbPatient.setValue(null);
            cbTier.setValue(null);
            refreshClaims();
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    private void submitClaim(TextField tfPatientId, TextField tfAmount, TextField tfProvider, TextField tfDesc) {
        String pid = tfPatientId.getText().trim();
        String amtS = tfAmount.getText().trim();
        String prov = tfProvider.getText().trim();
        String desc = tfDesc.getText().trim();
        
        if (pid.isEmpty() || amtS.isEmpty() || prov.isEmpty()) {
            showAlert("Validasi", "ID Pasien, jumlah, dan provider wajib diisi.");
            return;
        }
        
        try {
            double amt = Double.parseDouble(amtS);
            Claim claim = insuranceService.submitClaim(pid, amt, LocalDate.now(), prov, desc);
            showAlert("Berhasil", "Klaim berhasil diajukan: " + claim.getId());
            refreshClaims();
            clearFields(tfPatientId, tfAmount, tfProvider, tfDesc);
        } catch (NumberFormatException nfe) {
            showAlert("Validasi", "Jumlah harus berupa angka.");
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    // ===== NAVIGATION & UTILITY METHODS =====
    private void navigateToTab(String tabName) {
        Tab targetTab = tabMap.get(tabName);
        if (targetTab != null) {
            tabPane.getSelectionModel().select(targetTab);
        } else {
            showAlert("Navigasi", "Fitur '" + tabName + "' sedang dalam pengembangan.");
        }
    }

    private void selectInsuranceClaimsTab() {
        Tab insuranceTab = findTabByTitle("Health Insurance");
        if (insuranceTab != null && insuranceTab.getContent() instanceof TabPane) {
            TabPane innerTabPane = (TabPane) insuranceTab.getContent();
            innerTabPane.getSelectionModel().select(1);
        }
    }

    private Tab findTabByTitle(String title) {
        return tabPane.getTabs().stream()
                .filter(tab -> tab.getText().equals(title))
                .findFirst()
                .orElse(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // ===== REFRESH METHODS =====
    private void refreshPatients() {
        refreshListView(patientListView, patientRepo.findAll(), 
            p -> String.format("%s - %s - %s", p.getId(), p.getName(), p.getEmail()));
        refreshAppointmentSelectors();
    }

    private void refreshPoliDokter() {
        refreshListView(poliListView, poliRepo.findAll(),
            p -> String.format("%s - %s - %s", p.getId(), p.getName(), p.getDescription()));
        refreshListView(dokterListView, dokterRepo.findAll(),
            d -> String.format("%s - %s - poli:%s - prov:%s", d.getId(), d.getName(), d.getPoliId(), d.getProviderId()));
        refreshAppointmentSelectors();
    }

    private void refreshAppointments() {
        refreshListView(appointmentsListView, appointmentRepo.findAll(), a -> {
            Patient p = patientRepo.findById(a.getPatientId()).orElse(null);
            Dokter d = dokterRepo.findById(a.getDoctorId()).orElse(null);
            String pname = p == null ? a.getPatientId() : p.getName();
            String dname = d == null ? a.getDoctorId() : d.getName();
            String status = a.isCheckedIn() ? "HADIR" : "TERJADWAL";
            return String.format("%s - %s - %s - %s - %s - %s", 
                a.getId(), pname, dname, a.getDateTime(), a.getReason(), status);
        });
    }

    private void refreshClaims() {
        claimsListView.getItems().clear();
        for (Patient patient : patientRepo.findAll()) {
            if (!patient.isEnrolled()) continue;
            Insurance insurance = patient.getInsurance();
            for (Claim c : insurance.getClaims()) {
                String line = String.format("%s - Tier:%s - %s - %s - %.2f - %s", 
                    c.getId(), insurance.getTier(), c.getPolicyNumber(), c.getProviderId(), c.getAmount(), c.getState());
                claimsListView.getItems().add(line);
            }
        }
    }

    private void refreshNews() {
        refreshListView(newsListView, newsService.fetchLatest(10),
            n -> String.format("%s | %s | %s", n.getPublishedAt(), n.getTitle(), n.getSource()));
    }

    private void refreshEnrolledPatients(ListView<String> listView) {
        refreshListView(listView, 
            patientRepo.findAll().stream().filter(Patient::isEnrolled).toList(),
            p -> String.format("%s - %s - Tier:%s - Klaim:%d", 
                p.getName(), p.getInsurance().getId(), p.getInsurance().getTier(), p.getInsurance().getClaims().size()));
    }

    private void refreshQueueDisplay(ListView<String> listView) {
        listView.getItems().clear();
        List<Appointment> appts = new ArrayList<>(appointmentRepo.findAll());
        appts.sort((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()));

        if (appts.isEmpty()) {
            listView.getItems().add("📭 Tidak ada antrian");
            return;
        }

        for (Appointment a : appts) {
            Patient p = patientRepo.findById(a.getPatientId()).orElse(null);
            Dokter d = dokterRepo.findById(a.getDoctorId()).orElse(null);
            Poli poli = d == null ? null : poliRepo.findById(d.getPoliId()).orElse(null);
            
            String pname = p == null ? a.getPatientId() : p.getName();
            String dname = d == null ? a.getDoctorId() : d.getName();
            String poliName = poli == null ? "Unknown" : poli.getName();
            String status = a.isCheckedIn() ? "✅ HADIR" : "⏳ MENUNGGU";
            
            String time = a.getDateTime().format(DateTimeFormatter.ofPattern("HH:mm"));
            String line = String.format("%s | %s | Dr. %s (%s) | %s | %s", 
                time, a.getId(), dname, poliName, pname, status);
            listView.getItems().add(line);
        }
    }

    private <T> void refreshListView(ListView<String> listView, Collection<T> items, java.util.function.Function<T, String> mapper) {
        listView.getItems().clear();
        items.stream().map(mapper).forEach(listView.getItems()::add);
    }

    private void refreshAppointmentSelectors() {
        if (cbPatientBox == null || cbPoliBox == null || cbDokterBox == null) return;
        
        refreshComboBox(cbPatientBox, patientRepo.findAll(), p -> p.getId() + " - " + p.getName());
        refreshComboBox(cbPoliBox, poliRepo.findAll(), p -> p.getId() + " - " + p.getName());
        refreshComboBox(cbDokterBox, dokterRepo.findAll(), d -> d.getId() + " - " + d.getName());
    }

    private <T> void refreshComboBox(ComboBox<String> comboBox, Collection<T> items, java.util.function.Function<T, String> mapper) {
        comboBox.getItems().clear();
        items.stream().map(mapper).forEach(comboBox.getItems()::add);
    }

    private void refreshComboBoxes(ComboBox<String> cbPoli, ComboBox<String> cbProv) {
        refreshComboBox(cbPoli, poliRepo.findAll(), p -> p.getId() + " - " + p.getName());
        refreshComboBox(cbProv, providerRepo.findAll(), p -> p.getId() + " - " + p.getName());
    }

    private void setupAppointmentComboBoxes() {
        cbPoliBox.setOnAction(ev -> {
            String sel = cbPoliBox.getValue();
            cbDokterBox.getItems().clear();
            if (sel != null) {
                String poliId = sel.split(" - ")[0];
                refreshComboBox(cbDokterBox, 
                    dokterRepo.findAll().stream().filter(d -> poliId.equals(d.getPoliId())).toList(),
                    d -> d.getId() + " - " + d.getName());
            }
        });
    }

    // ===== UI HELPER METHODS =====
    private VBox createStyledVBox() {
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("panel");
        return vbox;
    }

    private Label createTitleLabel(String text) {
        Label label = new Label(text);
        label.setFont(TITLE_FONT);
        label.getStyleClass().add("title");
        return label;
    }

    private TextField createTextField(String prompt) {
        TextField tf = new TextField();
        tf.setPromptText(prompt);
        tf.setStyle("-fx-text-fill: " + TEXT_BLACK + ";");
        return tf;
    }

    private ComboBox<String> createComboBox(String prompt) {
        ComboBox<String> cb = new ComboBox<>();
        cb.setPromptText(prompt);
        return cb;
    }

    private Button createPrimaryButton(String text) {
        Button btn = new Button(text);
        btn.setStyle("-fx-background-color: linear-gradient(to bottom, " + PRIMARY_COLOR + ", " + SECONDARY_COLOR + "); -fx-text-fill: white; -fx-font-weight: bold;");
        return btn;
    }

    private ListView<String> createStyledListView(double height) {
        ListView<String> listView = new ListView<>();
        setupListView(listView, height);
        return listView;
    }

    private void setupListView(ListView<String> listView, double height) {
        listView.setPrefHeight(height);
        listView.getStyleClass().add("list-view");
    }

    private VBox createGradientPanel(String title, String subtitle) {
        VBox panel = new VBox(15);
        panel.setPadding(new Insets(25));
        panel.setStyle("-fx-background: linear-gradient(135deg, #1a5f4a, " + PRIMARY_COLOR + "); -fx-border-radius: 8; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", 22));
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Label subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(Font.font("Segoe UI", 12));
        subtitleLabel.setWrapText(true);
        subtitleLabel.setStyle("-fx-text-fill: #e0f2f1; -fx-line-spacing: 1.3;");
        
        panel.getChildren().addAll(titleLabel, subtitleLabel);
        return panel;
    }

    private HBox createInfoBar(String text) {
        HBox infoBar = new HBox();
        infoBar.setPadding(new Insets(12));
        infoBar.setStyle("-fx-background-color: linear-gradient(to right, #f0fdfb, #e8f8f5); -fx-border-color: " + PRIMARY_COLOR + "; -fx-border-radius: 6; -fx-border-width: 1;");
        
        Label infoText = new Label(text);
        infoText.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold; -fx-font-size: 11;");
        infoBar.getChildren().add(infoText);
        return infoBar;
    }

    private HBox createSearchBox(String prompt) {
        HBox searchBox = new HBox(10);
        TextField searchField = new TextField();
        searchField.setPromptText(prompt);
        searchField.setPrefWidth(400);
        searchField.setStyle("-fx-text-fill: " + TEXT_BLACK + ";");
        Button searchBtn = new Button("Cari");
        searchBtn.setOnAction(e -> performSearch(searchField.getText()));
        searchBox.getChildren().addAll(searchField, searchBtn);
        return searchBox;
    }

    private HBox createAppointmentActionButtons() {
        HBox buttons = new HBox(8);
        Button btnEdit = createPrimaryButton("Edit Terpilih");
        Button btnDelete = createPrimaryButton("Hapus Terpilih");
        
        btnEdit.setOnAction(e -> editSelectedAppointment());
        btnDelete.setOnAction(e -> deleteSelectedAppointment());
        
        buttons.getChildren().addAll(btnEdit, btnDelete);
        return buttons;
    }

    private VBox createTiersInfoPanel() {
        VBox panel = new VBox(8);
        panel.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4; -fx-padding: 10;");
        
        Label title = new Label("Paket Asuransi Tersedia:");
        title.setFont(Font.font("Segoe UI", 12));
        title.setStyle("-fx-text-fill: " + TEXT_BLACK + "; -fx-font-weight: bold;");
        
        String[] tiers = {
            "🔷 BASIC: Rp 500.000/bulan - Konsultasi umum, obat-obatan dasar",
            "🔵 STANDARD: Rp 1.000.000/bulan - Semua benefit BASIC + Khusus gigi + Lab", 
            "🔶 PREMIUM: Rp 2.000.000/bulan - Semua benefit + Spesialis + Rawat inap"
        };
        
        panel.getChildren().add(title);
        for (String tier : tiers) {
            Label label = new Label(tier);
            label.setStyle("-fx-font-size: 11; -fx-text-fill: " + TEXT_BLACK + ";");
            panel.getChildren().add(label);
        }
        return panel;
    }

    // ===== BUSINESS LOGIC HELPERS =====
    private String getTotalClaimsCount() {
        return String.valueOf(patientRepo.findAll().stream()
                .filter(Patient::isEnrolled)
                .mapToInt(p -> p.getInsurance().getClaims().size())
                .sum());
    }

    private String getEnrolledPatientCount() {
        return String.valueOf(patientRepo.findAll().stream().filter(Patient::isEnrolled).count());
    }

    private String getTodayAppointmentsCount() {
        return String.valueOf(appointmentRepo.findAll().stream()
                .filter(a -> a.getDateTime().toLocalDate().equals(LocalDate.now()))
                .count());
    }

    private void performSearch(String query) {
        if (query.isEmpty()) {
            showAlert("Pencarian", "Silakan masukkan kata kunci untuk mencari");
        } else {
            showAlert("Hasil Pencarian", "Menampilkan hasil untuk: '" + query + "'");
        }
    }

    private void handleCheckIn(ListView<String> queueList) {
        String sel = queueList.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("⚠️ Perhatian", "Pilih antrian terlebih dahulu.");
            return;
        }
        
        String[] parts = sel.split(" \\| ");
        if (parts.length > 0) {
            String apptId = parts[1].trim();
            Appointment appt = appointmentRepo.findById(apptId).orElse(null);
            if (appt == null) {
                showAlert("❌ Error", "Antrian tidak ditemukan.");
                return;
            }
            appt.setCheckedIn(true);
            appointmentRepo.save(appt.getId(), appt);
            showAlert("✅ Berhasil", "Anda berhasil check in!");
            refreshQueueDisplay(queueList);
        }
    }

    private void enrollSelectedPatient() {
        String sel = patientListView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Peringatan", "Pilih pasien terlebih dahulu.");
            return;
        }
        String id = sel.split(" - ")[0];
        try {
            insuranceService.enroll(id, Insurance.Tier.BASIC);
            showAlert("Berhasil", "Pasien " + id + " berhasil didaftarkan asuransi (BASIC tier).");
            refreshClaims();
        } catch (Exception ex) {
            showAlert("Error", ex.getMessage());
        }
    }

    private void approveSelectedClaim() {
        String sel = claimsListView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Peringatan", "Pilih klaim terlebih dahulu.");
            return;
        }
        
        String claimId = sel.split(" - ")[0];
        boolean found = false;
        
        for (Patient patient : patientRepo.findAll()) {
            if (!patient.isEnrolled()) continue;
            Insurance insurance = patient.getInsurance();
            for (Claim c : insurance.getClaims()) {
                if (c.getId().equals(claimId)) {
                    insuranceService.reviewClaim(patient.getId(), claimId, true);
                    showAlert("Berhasil", "Klaim disetujui.");
                    refreshClaims();
                    found = true;
                    break;
                }
            }
            if (found) break;
        }
        
        if (!found) {
            showAlert("Tidak ditemukan", "Klaim tidak ditemukan dalam asuransi.");
        }
    }

    private void editSelectedAppointment() {
        String sel = appointmentsListView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Peringatan", "Pilih janji temu terlebih dahulu.");
            return;
        }
        
        String apptId = sel.split(" - ")[0];
        Appointment appt = appointmentRepo.findById(apptId).orElse(null);
        if (appt == null) {
            showAlert("Tidak ditemukan", "Janji temu tidak ditemukan.");
            return;
        }
        
        showAlert("Edit Janji Temu", "Fungsi edit untuk: " + apptId + "\nDalam implementasi nyata, ini akan membuka dialog edit.");
    }

    private void deleteSelectedAppointment() {
        String sel = appointmentsListView.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert("Peringatan", "Pilih janji temu terlebih dahulu.");
            return;
        }
        
        String apptId = sel.split(" - ")[0];
        appointmentRepo.delete(apptId);
        
        for (Patient pp : patientRepo.findAll()) {
            pp.getAppointments().removeIf(a -> a.getId().equals(apptId));
            patientRepo.save(pp.getId(), pp);
        }
        
        refreshAppointments();
        showAlert("Berhasil", "Janji temu dihapus: " + apptId);
    }

    private String adjustBrightness(String hex, int adjustment) {
        return hex;
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}