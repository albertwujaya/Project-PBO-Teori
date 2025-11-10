
package com.example.healthapp.gui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.example.healthapp.Appointment;
import com.example.healthapp.Claim;
import com.example.healthapp.Dokter;
import com.example.healthapp.InMemoryRepository;
import com.example.healthapp.Insurance;
import com.example.healthapp.InsuranceService;
import com.example.healthapp.MockNewsService;
import com.example.healthapp.NewsArticle;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Simple JavaFX GUI for the Integrated Health App.
 * Uses existing model classes (Patient, Provider, Policy, Claim, Appointment)
 * and the MockNewsService for news.
 *
 * This file replaces a corrupted GUI file and provides a minimal, working demo.
 */
public class IntegratedHealthAppGUI extends Application {

    private InMemoryRepository<Patient> patientRepo = new InMemoryRepository<>();
    private InMemoryRepository<Provider> providerRepo = new InMemoryRepository<>();
    private InMemoryRepository<Poli> poliRepo = new InMemoryRepository<>();
    private InMemoryRepository<Dokter> dokterRepo = new InMemoryRepository<>();
    private InMemoryRepository<Appointment> appointmentRepo = new InMemoryRepository<>();
    private InsuranceService insuranceService;

    private NewsService newsService = new MockNewsService();

    private ListView<String> patientListView = new ListView<>();
    private ListView<String> claimsListView = new ListView<>();
    private ListView<String> appointmentsListView = new ListView<>();
    private ListView<String> queueListView = new ListView<>();
    private ListView<String> newsListView = new ListView<>();
    private ListView<String> poliListView = new ListView<>();
    private ListView<String> dokterListView = new ListView<>();
    // appointment tab selectors (fields so other tabs can refresh them)
    private ComboBox<String> cbPatientBox;
    private ComboBox<String> cbPoliBox;
    private ComboBox<String> cbDokterBox;

    @Override
    public void init() {
        // initialize repositories and service
        insuranceService = new InsuranceService(patientRepo, providerRepo);
        
        // ===== Sample Providers (2) =====
        Provider prov1 = new Provider("HSP01", "Rumah Sakit Sehat", "Jakarta");
        Provider prov2 = new Provider("HSP02", "Klinik Maju", "Bandung");
        providerRepo.save(prov1.getId(), prov1);
        providerRepo.save(prov2.getId(), prov2);

        // ===== Sample Poli (5) =====
        Poli poliAnak = new Poli("POLI-A", "Poli Anak", "Spesialis penyakit anak-anak");
        Poli poliUmum = new Poli("POLI-U", "Poli Umum", "Konsultasi kesehatan umum");
        Poli poliGigi = new Poli("POLI-G", "Poli Gigi", "Perawatan dan pemeriksaan gigi");
        Poli poliMata = new Poli("POLI-M", "Poli Mata", "Pemeriksaan kesehatan mata");
        Poli poliHamil = new Poli("POLI-H", "Poli Kandungan", "Perawatan ibu hamil dan melahirkan");
        poliRepo.save(poliAnak.getId(), poliAnak);
        poliRepo.save(poliUmum.getId(), poliUmum);
        poliRepo.save(poliGigi.getId(), poliGigi);
        poliRepo.save(poliMata.getId(), poliMata);
        poliRepo.save(poliHamil.getId(), poliHamil);

        // ===== Sample Doctors (5+) =====
        Dokter d1 = new Dokter("D001", "Dr. Suryo", poliAnak.getId(), prov1.getId());
        Dokter d2 = new Dokter("D002", "Dr. Dewi Lestari", poliUmum.getId(), prov1.getId());
        Dokter d3 = new Dokter("D003", "Dr. Adi Prasetyo", poliGigi.getId(), prov2.getId());
        Dokter d4 = new Dokter("D004", "Dr. Siti Nurhaliza", poliMata.getId(), prov1.getId());
        Dokter d5 = new Dokter("D005", "Dr. Budi Kartono", poliHamil.getId(), prov2.getId());
        Dokter d6 = new Dokter("D006", "Dr. Rina Wijaya", poliAnak.getId(), prov2.getId());
        dokterRepo.save(d1.getId(), d1);
        dokterRepo.save(d2.getId(), d2);
        dokterRepo.save(d3.getId(), d3);
        dokterRepo.save(d4.getId(), d4);
        dokterRepo.save(d5.getId(), d5);
        dokterRepo.save(d6.getId(), d6);
        
        poliAnak.addDoctorId(d1.getId());
        poliAnak.addDoctorId(d6.getId());
        poliUmum.addDoctorId(d2.getId());
        poliGigi.addDoctorId(d3.getId());
        poliMata.addDoctorId(d4.getId());
        poliHamil.addDoctorId(d5.getId());
        poliRepo.save(poliAnak.getId(), poliAnak);
        poliRepo.save(poliUmum.getId(), poliUmum);
        poliRepo.save(poliGigi.getId(), poliGigi);
        poliRepo.save(poliMata.getId(), poliMata);
        poliRepo.save(poliHamil.getId(), poliHamil);

        // ===== Sample Patients (5) =====
        Patient p1 = new Patient("P001", "Budi Santoso", "budi@example.com", LocalDate.of(1990, 5, 20));
        Patient p2 = new Patient("P002", "Siti Aminah", "siti@example.com", LocalDate.of(1985, 8, 12));
        Patient p3 = new Patient("P003", "Ahmad Wijaya", "ahmad@example.com", LocalDate.of(1988, 3, 15));
        Patient p4 = new Patient("P004", "Nur Azizah", "nur@example.com", LocalDate.of(1992, 11, 25));
        Patient p5 = new Patient("P005", "Rudi Hermawan", "rudi@example.com", LocalDate.of(1987, 7, 8));
        patientRepo.save(p1.getId(), p1);
        patientRepo.save(p2.getId(), p2);
        patientRepo.save(p3.getId(), p3);
        patientRepo.save(p4.getId(), p4);
        patientRepo.save(p5.getId(), p5);

        // ===== Enroll patients in insurance with tiers =====
        insuranceService.enroll(p1.getId(), Insurance.Tier.BASIC);
        insuranceService.enroll(p2.getId(), Insurance.Tier.STANDARD);
        insuranceService.enroll(p3.getId(), Insurance.Tier.PREMIUM);
        insuranceService.enroll(p4.getId(), Insurance.Tier.BASIC);
        insuranceService.enroll(p5.getId(), Insurance.Tier.STANDARD);

        // ===== Sample Consultations/Appointments (5) =====
        LocalDateTime now = LocalDateTime.now();
        Appointment appt1 = new Appointment(p1.getId(), d2.getId(), now.plusDays(1).withHour(9).withMinute(0), "Pemeriksaan rutin");
        Appointment appt2 = new Appointment(p2.getId(), d3.getId(), now.plusDays(2).withHour(10).withMinute(30), "Scaling dan pembersihan gigi");
        Appointment appt3 = new Appointment(p3.getId(), d1.getId(), now.plusDays(1).withHour(14).withMinute(0), "Konsultasi demam dan batuk");
        Appointment appt4 = new Appointment(p4.getId(), d4.getId(), now.plusDays(3).withHour(13).withMinute(0), "Pemeriksaan mata berkala");
        Appointment appt5 = new Appointment(p5.getId(), d5.getId(), now.plusDays(5).withHour(11).withMinute(0), "Konsultasi kesehatan umum");
        appointmentRepo.save(appt1.getId(), appt1);
        appointmentRepo.save(appt2.getId(), appt2);
        appointmentRepo.save(appt3.getId(), appt3);
        appointmentRepo.save(appt4.getId(), appt4);
        appointmentRepo.save(appt5.getId(), appt5);
        
        p1.addAppointment(appt1);
        p2.addAppointment(appt2);
        p3.addAppointment(appt3);
        p4.addAppointment(appt4);
        p5.addAppointment(appt5);
        patientRepo.save(p1.getId(), p1);
        patientRepo.save(p2.getId(), p2);
        patientRepo.save(p3.getId(), p3);
        patientRepo.save(p4.getId(), p4);
        patientRepo.save(p5.getId(), p5);

        // ===== Sample Claims/Insurance Data (5) =====
        // Submit sample claims for each enrolled patient
        int claimCount = 0;
        for (Patient pat : patientRepo.findAll()) {
            if (claimCount >= 5) break;
            if (!pat.isEnrolled()) continue;
            try {
                insuranceService.submitClaim(
                    pat.getId(),
                    100000 + (claimCount * 50000),
                    LocalDate.now().minusDays(claimCount),
                    prov1.getId(),
                    "Klaim konsultasi " + claimCount
                );
                claimCount++;
            } catch (Exception e) {
                // skip if error
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Integrated Health Management System v2.0");
        primaryStage.setWidth(1600);
        primaryStage.setHeight(900);
        primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/icon.png") != null ? 
            getClass().getResourceAsStream("/icon.png") : getClass().getResourceAsStream("/styles.css")));

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(
            createDashboardTab(),
            createBerandaTab(), 
            createInformasiTab(), 
            createArtikelInformasiTab(), 
            createDaftarAntarianTab(),
            createPatientsTab(), 
            createPoliDokterTab(), 
            createAppointmentsTab(), 
            createHealthInsuranceTab(), 
            createNewsTab()
        );

        BorderPane root = new BorderPane();
        
        // Professional header
        VBox header = createProfessionalHeader();
        root.setTop(header);
        root.setCenter(tabPane);

        Scene scene = new Scene(root, 900, 600);
        try {
            String css = getClass().getResource("/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            System.out.println("Loaded stylesheet: " + css);
        } catch (Exception ex) {
            System.err.println("Warning: styles.css not found");
        }
        
        applyProfessionalStyling(scene);
        
        primaryStage.setScene(scene);
        primaryStage.setX(100);
        primaryStage.setY(100);
        primaryStage.show();

        refreshPatients();
        refreshClaims();
        refreshNews();
        refreshAppointments();
        refreshPoliDokter();
    }
    
    private VBox createProfessionalHeader() {
        VBox header = new VBox();
        header.setStyle(
            "-fx-background-color: linear-gradient(to right, #1a5f4a, #009B88 50%, #007B6E); " +
            "-fx-padding: 12 20 12 20;"
        );
        header.setPrefHeight(60);
        
        HBox titleBox = new HBox(15);
        titleBox.setAlignment(Pos.CENTER_LEFT);
        
        Label logo = new Label("⚕️");
        logo.setFont(Font.font("System", 28));
        
        VBox titleSection = new VBox(0);
        Label mainTitle = new Label("Integrated Health Management System");
        mainTitle.setFont(Font.font("System", 16));
        mainTitle.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Label subtitle = new Label("Professional Healthcare Administration Platform");
        subtitle.setFont(Font.font("System", 10));
        subtitle.setStyle("-fx-text-fill: #e0f2f1;");
        
        titleSection.getChildren().addAll(mainTitle, subtitle);
        
        // Spacer
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        javafx.scene.layout.HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        
        // System status
        HBox statusBox = new HBox(15);
        statusBox.setAlignment(Pos.CENTER_RIGHT);
        
        Label status = new Label("✓ System Status: Online");
        status.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold; -fx-font-size: 11;");
        
        Label time = new Label(java.time.LocalDateTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")));
        time.setStyle("-fx-text-fill: #e0f2f1; -fx-font-size: 11;");
        
        statusBox.getChildren().addAll(status, time);
        
        titleBox.getChildren().addAll(logo, titleSection, spacer, statusBox);
        header.getChildren().add(titleBox);
        
        return header;
    }
    
    private Tab createDashboardTab() {
        Tab tab = new Tab("📊 Dashboard");
        tab.setClosable(false);
        
        VBox vbox = new VBox(15);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f5f5f5;");
        
        // KPI Cards
        HBox kpiRow1 = new HBox(20);
        kpiRow1.getChildren().addAll(
            createKPICard("👥 Pasien Terdaftar", String.valueOf(patientRepo.findAll().size()), "#009B88"),
            createKPICard("📋 Janji Temu Aktif", String.valueOf(appointmentRepo.findAll().size()), "#00796B"),
            createKPICard("💳 Klaim Diproses", 
                String.valueOf(patientRepo.findAll().stream()
                    .filter(Patient::isEnrolled)
                    .mapToInt(p -> p.getInsurance().getClaims().size()).sum()), "#00695C"),
            createKPICard("🏥 Provider Jaringan", String.valueOf(providerRepo.findAll().size()), "#004D40")
        );
        
        // Recent activities section
        VBox activitiesSection = new VBox(10);
        activitiesSection.setPadding(new Insets(20));
        activitiesSection.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 5, 0, 0, 2);"
        );
        
        Label activitiesTitle = new Label("📈 Aktivitas Terkini");
        activitiesTitle.setFont(Font.font("System", 14));
        activitiesTitle.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold;");
        
        ListView<String> activitiesList = new ListView<>();
        activitiesList.setPrefHeight(250);
        activitiesList.getStyleClass().add("list-view");
        
        // Sample recent activities
        activitiesList.getItems().addAll(
            "✓ Pasien Budi Santoso mendaftar asuransi tier PREMIUM - 11 Nov 2025 10:30",
            "✓ Klaim dari Siti Aminah disetujui - Total: Rp 100,000 - 11 Nov 2025 09:15",
            "✓ Dr. Dewi Lestari menambahkan jadwal konsultasi baru - 11 Nov 2025 08:45",
            "✓ 3 pasien sudah check-in untuk antrian hari ini - 11 Nov 2025 08:20",
            "✓ Laporan bulai November telah digenerate - 10 Nov 2025 17:30"
        );
        
        activitiesSection.getChildren().addAll(activitiesTitle, activitiesList);
        
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        
        vbox.getChildren().addAll(kpiRow1, activitiesSection);
        
        tab.setContent(vbox);
        return tab;
    }
    
    private VBox createKPICard(String title, String value, String color) {
        VBox card = new VBox(10);
        card.setPrefWidth(250);
        card.setPrefHeight(120);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setStyle(String.format(
            "-fx-background: linear-gradient(135deg, %s, %s); " +
            "-fx-border-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2); " +
            "-fx-text-fill: white;",
            color, adjustBrightness(color, -20)
        ));
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setStyle("-fx-text-fill: rgba(255,255,255,0.9);");
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", 32));
        valueLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        card.getChildren().addAll(titleLabel, valueLabel);
        
        card.setOnMouseEntered(e -> card.setStyle(String.format(
            "-fx-background: linear-gradient(135deg, %s, %s); " +
            "-fx-border-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 3); " +
            "-fx-text-fill: white; " +
            "-fx-cursor: hand;",
            adjustBrightness(color, 10), adjustBrightness(color, -10)
        )));
        
        card.setOnMouseExited(e -> card.setStyle(String.format(
            "-fx-background: linear-gradient(135deg, %s, %s); " +
            "-fx-border-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 6, 0, 0, 2); " +
            "-fx-text-fill: white;",
            color, adjustBrightness(color, -20)
        )));
        
        return card;
    }
    
    private String adjustBrightness(String hex, int adjustment) {
        // Simple brightness adjustment for colors
        return hex; // Simplified for now
    }
    
    private void applyProfessionalStyling(Scene scene) {
        String professionalCSS = 
            // Root and main backgrounds
            ".root { " +
            "-fx-base: #f5f5f5; " +
            "-fx-background: #f5f5f5; " +
            "-fx-font-family: 'Segoe UI', 'System'; " +
            "}" +
            
            // Tab styling - Healthcare themed
            ".tab-pane .tab-header-background { " +
            "-fx-background-color: linear-gradient(to right, #1a5f4a, #009B88); " +
            "}" +
            ".tab-pane .tab { " +
            "-fx-background-color: #e8e8e8; " +
            "-fx-text-fill: #333; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 11; " +
            "-fx-padding: 8 16 8 16; " +
            "}" +
            ".tab-pane .tab:selected { " +
            "-fx-background-color: linear-gradient(to right, #009B88, #007B6E); " +
            "-fx-text-fill: white; " +
            "}" +
            
            // Professional buttons
            ".button { " +
            "-fx-background-color: linear-gradient(to bottom, #009B88, #007B6E); " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 11; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 16 8 16; " +
            "-fx-border-radius: 4; " +
            "-fx-background-radius: 4; " +
            "-fx-cursor: hand; " +
            "}" +
            ".button:hover { " +
            "-fx-background-color: linear-gradient(to bottom, #007B6E, #005B52); " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 4, 0, 0, 1); " +
            "}" +
            ".button:pressed { -fx-background-color: #005B52; }" +
            
            // Input fields with professional styling
            ".text-field { " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-width: 1; " +
            "-fx-padding: 8 10 8 10; " +
            "-fx-font-size: 11; " +
            "-fx-background-color: white; " +
            "-fx-focus-color: #009B88; " +
            "-fx-faint-focus-color: #009B8844; " +
            "}" +
            ".text-field:focused { " +
            "-fx-border-color: #009B88; " +
            "-fx-border-width: 2; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,155,136,0.15), 3, 0, 0, 0); " +
            "}" +
            ".combo-box { " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-width: 1; " +
            "-fx-font-size: 11; " +
            "-fx-padding: 8 10 8 10; " +
            "}" +
            ".combo-box:focused { " +
            "-fx-border-color: #009B88; " +
            "-fx-border-width: 2; " +
            "}" +
            ".date-picker { " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-width: 1; " +
            "-fx-padding: 8 10 8 10; " +
            "}" +
            
            // Professional list views
            ".list-view { " +
            "-fx-control-inner-background: white; " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-width: 1; " +
            "-fx-font-size: 11; " +
            "-fx-padding: 0; " +
            "}" +
            ".list-view:focused { " +
            "-fx-border-color: #009B88; " +
            "-fx-border-width: 2; " +
            "}" +
            ".list-view .list-cell { " +
            "-fx-padding: 8 10 8 10; " +
            "-fx-font-size: 11; " +
            "-fx-text-fill: #333; " +
            "}" +
            ".list-view .list-cell:filled:selected { " +
            "-fx-background-color: linear-gradient(to right, #009B88, #007B6E); " +
            "-fx-text-fill: white; " +
            "}" +
            ".list-view .list-cell:odd { " +
            "-fx-background-color: #f9f9f9; " +
            "}" +
            
            // Labels and typography
            ".label { -fx-text-fill: #333; -fx-font-size: 11; }" +
            ".title { " +
            "-fx-text-fill: #009B88; " +
            "-fx-font-weight: bold; " +
            "-fx-font-size: 18; " +
            "}" +
            
            // Panels with professional styling
            ".panel { " +
            "-fx-background-color: white; " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-radius: 4; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 1); " +
            "}" +
            
            // Scroll pane
            ".scroll-pane { -fx-background-color: #f5f5f5; }" +
            ".scroll-bar { -fx-block-increment: 10; }" +
            
            // Alert styling
            ".alert { -fx-font-size: 11; }";
        
        scene.getStylesheets().add("data:text/css," + professionalCSS);
    }
    
    // ===== NEW TABS FOR MOCKUP DESIGN =====
    
    private Tab createBerandaTab() {
        Tab tab = new Tab("🏠 Beranda");
        tab.setClosable(false);
        
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f5f5f5;");
        
        // Professional Welcome Section
        VBox welcomeSection = new VBox(15);
        welcomeSection.setPadding(new Insets(25));
        welcomeSection.setStyle(
            "-fx-background: linear-gradient(135deg, #1a5f4a, #009B88); " +
            "-fx-border-radius: 8; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );
        
        Label greeting = new Label("Selamat Datang ke Portal Kesehatan Terintegrasi");
        greeting.setFont(Font.font("System", 22));
        greeting.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Label description = new Label(
            "Platform manajemen kesehatan modern untuk administrasi klinik dan rumah sakit. " +
            "Kelola pasien, janji temu, klaim asuransi, dan informasi kesehatan dengan mudah."
        );
        description.setFont(Font.font("System", 12));
        description.setWrapText(true);
        description.setStyle("-fx-text-fill: #e0f2f1; -fx-line-spacing: 1.3;");
        
        welcomeSection.getChildren().addAll(greeting, description);
        
        // Quick Actions Section
        VBox quickActionsSection = new VBox(15);
        quickActionsSection.setPadding(new Insets(20));
        quickActionsSection.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4, 0, 0, 1);"
        );
        
        Label quickTitle = new Label("⚡ Akses Cepat");
        quickTitle.setFont(Font.font("System", 14));
        quickTitle.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold;");
        
        HBox quickActionsGrid = new HBox(15);
        quickActionsGrid.setAlignment(Pos.TOP_LEFT);
        quickActionsGrid.setStyle("-fx-wrap-text: true;");
        
        String[] quickActions = {
            "📋 Buat Janji Temu",
            "👤 Daftar Pasien Baru",
            "💳 Proses Klaim Asuransi",
            "📊 Lihat Laporan",
            "🩺 Lihat Riwayat Medis",
            "⚙️ Pengaturan Sistem"
        };
        
        for (String action : quickActions) {
            Button quickBtn = new Button(action);
            quickBtn.setPrefWidth(150);
            quickBtn.setStyle(
                "-fx-background-color: white; " +
                "-fx-text-fill: #009B88; " +
                "-fx-font-weight: bold; " +
                "-fx-border-color: #009B88; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 8 16 8 16;"
            );
            
            // Add functionality to quick action buttons
            quickBtn.setOnAction(e -> handleQuickAction(action));
            
            quickBtn.setOnMouseEntered(e -> quickBtn.setStyle(
                "-fx-background-color: #f0fdfb; " +
                "-fx-text-fill: #009B88; " +
                "-fx-font-weight: bold; " +
                "-fx-border-color: #009B88; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 8 16 8 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,155,136,0.2), 4, 0, 0, 1);"
            ));
            quickBtn.setOnMouseExited(e -> quickBtn.setStyle(
                "-fx-background-color: white; " +
                "-fx-text-fill: #009B88; " +
                "-fx-font-weight: bold; " +
                "-fx-border-color: #009B88; " +
                "-fx-border-width: 2; " +
                "-fx-border-radius: 4; " +
                "-fx-padding: 8 16 8 16;"
            ));
            quickActionsGrid.getChildren().add(quickBtn);
        }
        
        ScrollPane quickScrollPane = new ScrollPane(quickActionsGrid);
        quickScrollPane.setFitToWidth(true);
        quickScrollPane.setStyle("-fx-background-color: white;");
        
        quickActionsSection.getChildren().addAll(quickTitle, quickScrollPane);
        
        // System Information
        HBox sysInfoRow = new HBox(15);
        sysInfoRow.getChildren().addAll(
            createInfoCard("� Total Pasien", 
                String.valueOf(patientRepo.findAll().size()) + " pasien terdaftar"),
            createInfoCard("✓ Asuransi Aktif", 
                String.valueOf(patientRepo.findAll().stream().filter(Patient::isEnrolled).count()) + " pasien"),
            createInfoCard("� Janji Hari Ini", 
                String.valueOf(appointmentRepo.findAll().stream()
                    .filter(a -> a.getDateTime().toLocalDate().equals(LocalDate.now()))
                    .count()) + " janji")
        );
        
        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        
        vbox.getChildren().addAll(welcomeSection, quickActionsSection, sysInfoRow);
        
        tab.setContent(vbox);
        return tab;
    }
    
    private VBox createInfoCard(String title, String content) {
        VBox card = new VBox(8);
        card.setPrefWidth(200);
        card.setPrefHeight(100);
        card.setPadding(new Insets(15));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 6; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 3, 0, 0, 1);"
        );
        card.setAlignment(Pos.TOP_LEFT);
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold;");
        
        Label contentLabel = new Label(content);
        contentLabel.setFont(Font.font("System", 11));
        contentLabel.setWrapText(true);
        contentLabel.setStyle("-fx-text-fill: #666;");
        
        card.getChildren().addAll(titleLabel, contentLabel);
        return card;
    }
    
    private VBox createStatCard(String emoji, String title, String value) {
        VBox card = new VBox(8);
        card.setPrefWidth(120);
        card.setAlignment(Pos.CENTER);
        
        Label emojiLabel = new Label(emoji);
        emojiLabel.setFont(Font.font(24));
        
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", 12));
        titleLabel.setStyle("-fx-text-fill: #666;");
        
        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("System", 20));
        valueLabel.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold;");
        
        card.getChildren().addAll(emojiLabel, titleLabel, valueLabel);
        return card;
    }
    
    private VBox createMenuCard(String title) {
        VBox card = new VBox(10);
        card.setPrefWidth(160);
        card.setPrefHeight(140);
        card.setPadding(new Insets(15));
        card.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 4, 0.5, 0, 1);"
        );
        card.setAlignment(Pos.CENTER);
        
        // Add hover effect with CSS
        card.setOnMouseEntered(e -> card.setStyle(
            "-fx-background-color: #f0fdfb; " +
            "-fx-border-color: #009B88; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 2; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,155,136,0.2), 6, 0.5, 0, 2);"
        ));
        
        card.setOnMouseExited(e -> card.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 4, 0.5, 0, 1);"
        ));
        
        Label label = new Label(title);
        label.setFont(Font.font("System", 13));
        label.setWrapText(true);
        label.setStyle("-fx-text-fill: #333; -fx-font-weight: bold;");
        label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        
        Button btn = new Button("→");
        btn.setFont(Font.font("System", 18));
        btn.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #009B88, #007B6E); " +
            "-fx-text-fill: white; " +
            "-fx-padding: 6 12 6 12; " +
            "-fx-font-size: 14;"
        );
        btn.setCursor(javafx.scene.Cursor.HAND);
        
        card.getChildren().addAll(label, btn);
        return card;
    }
    
    private Tab createInformasiTab() {
        Tab tab = new Tab("📚 Informasi");
        tab.setClosable(false);
        
        VBox vbox = new VBox(0);
        vbox.setPadding(new Insets(0));
        vbox.setStyle("-fx-background-color: #f5f5f5;");
        
        // Professional header
        VBox headerSection = new VBox(15);
        headerSection.setPadding(new Insets(25, 20, 20, 20));
        headerSection.setStyle(
            "-fx-background-color: linear-gradient(135deg, #1a5f4a, #009B88); " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-border-width: 0 0 2 0;"
        );
        
        Label title = new Label("Informasi Kesehatan dan Berita Industri");
        title.setFont(Font.font("System", 18));
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        
        Label subtitle = new Label("Dapatkan update terbaru tentang perkembangan kesehatan dan edukasi medis");
        subtitle.setFont(Font.font("System", 11));
        subtitle.setStyle("-fx-text-fill: #e0f2f1;");
        
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Cari artikel atau topik kesehatan...");
        searchField.setPrefWidth(450);
        searchField.setStyle(
            "-fx-padding: 8 12 8 12; " +
            "-fx-font-size: 11; " +
            "-fx-border-radius: 4;"
        );
        
        Button searchBtn = new Button("Cari");
        searchBtn.setStyle(
            "-fx-background-color: white; " +
            "-fx-text-fill: #009B88; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 20 8 20;"
        );
        searchBtn.setOnAction(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                showAlert("Pencarian", "Silakan masukkan kata kunci untuk mencari");
            } else {
                showAlert("Hasil Pencarian", "Menampilkan hasil untuk: '" + query + "'");
            }
        });
        searchBox.getChildren().addAll(searchField, searchBtn);
        
        headerSection.getChildren().addAll(title, subtitle, searchBox);
        
        // Content area with categories
        VBox contentSection = new VBox(15);
        contentSection.setPadding(new Insets(20));
        
        // Category tabs
        HBox categoryBox = new HBox(10);
        categoryBox.setAlignment(Pos.CENTER_LEFT);
        
        String[] categories = {"Semua", "Kesehatan Umum", "Nutrisi", "Olahraga", "Mental Health"};
        for (String cat : categories) {
            Button catBtn = new Button(cat);
            catBtn.setStyle(
                "-fx-background-color: white; " +
                "-fx-border-color: #d0d0d0; " +
                "-fx-border-width: 1; " +
                "-fx-padding: 6 12 6 12; " +
                "-fx-font-size: 10; " +
                "-fx-border-radius: 4;"
            );
            catBtn.setOnAction(e -> showAlert("Kategori: " + cat, 
                "Menampilkan informasi kesehatan untuk kategori: " + cat));
            catBtn.setOnMouseEntered(e -> catBtn.setStyle(
                "-fx-background-color: #f0fdfb; " +
                "-fx-border-color: #009B88; " +
                "-fx-border-width: 2; " +
                "-fx-padding: 6 12 6 12; " +
                "-fx-font-size: 10; " +
                "-fx-border-radius: 4;"
            ));
            catBtn.setOnMouseExited(e -> catBtn.setStyle(
                "-fx-background-color: white; " +
                "-fx-border-color: #d0d0d0; " +
                "-fx-border-width: 1; " +
                "-fx-padding: 6 12 6 12; " +
                "-fx-font-size: 10; " +
                "-fx-border-radius: 4;"
            ));
            categoryBox.getChildren().add(catBtn);
        }
        
        // Information list
        ListView<String> infoList = new ListView<>();
        infoList.setPrefHeight(500);
        infoList.getStyleClass().add("list-view");
        infoList.setStyle(
            "-fx-control-inner-background: white; " +
            "-fx-border-color: #d0d0d0; " +
            "-fx-font-size: 11; " +
            "-fx-padding: 0;"
        );
        
        // Professional info items with dates and sources
        String[] infoItems = {
            "[TERBARU] 🩺 Panduan Kesehatan Umum - Kesehatan yang baik adalah investasi terbaik | 10 Nov 2025 | Sumber: Kemenkes",
            "[FEATURED] 💊 Penggunaan Obat Yang Aman - Pelajari cara menggunakan obat dengan benar | 08 Nov 2025 | Sumber: WHO",
            "🥗 Nutrisi Seimbang - Tips makanan sehat untuk gaya hidup lebih baik | 05 Nov 2025 | Sumber: Ahli Gizi",
            "🏃 Olahraga Teratur - Manfaat olahraga untuk kesehatan jangka panjang | 03 Nov 2025 | Sumber: IDI",
            "😴 Tidur Berkualitas - Pentingnya istirahat yang cukup untuk kesehatan | 01 Nov 2025 | Sumber: Sleep Center",
            "🧘 Manajemen Stres - Teknik relaksasi dan meditasi untuk kesehatan mental | 30 Oct 2025 | Sumber: Psikolog"
        };
        
        for (String item : infoItems) {
            infoList.getItems().add(item);
        }
        
        contentSection.getChildren().addAll(categoryBox, infoList);
        VBox.setVgrow(infoList, javafx.scene.layout.Priority.ALWAYS);
        
        vbox.getChildren().addAll(headerSection, contentSection);
        tab.setContent(vbox);
        return tab;
    }
    
    private Tab createArtikelInformasiTab() {
        Tab tab = new Tab("Artikel Informasi");
        tab.setClosable(false);
        
        VBox vbox = new VBox(0);
        vbox.setPadding(new Insets(0));
        vbox.setStyle("-fx-background-color: #f8f9fa;");
        
        // Article header
        VBox articleHeader = new VBox(12);
        articleHeader.setPadding(new Insets(30, 25, 20, 25));
        articleHeader.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        
        Label title = new Label("Lorem ipsum dolor sit amet, consectetur adipiscing elit.");
        title.setFont(Font.font("System", 18));
        title.setWrapText(true);
        title.setStyle("-fx-text-fill: #333; -fx-font-weight: bold;");
        
        HBox metaBox = new HBox(30);
        metaBox.setStyle("-fx-alignment: center-left;");
        
        Label date = new Label("📅 30 Mei 2024");
        date.setStyle("-fx-text-fill: #666; -fx-font-size: 11;");
        
        Label author = new Label("✍️ Penulis: Dr. Kesehatan");
        author.setStyle("-fx-text-fill: #666; -fx-font-size: 11;");
        
        Label category = new Label("🏷️ Kategori: Kesehatan Umum");
        category.setStyle("-fx-text-fill: #666; -fx-font-size: 11;");
        
        metaBox.getChildren().addAll(date, author, category);
        articleHeader.getChildren().addAll(title, metaBox);
        
        // Article content
        VBox contentBox = new VBox(15);
        contentBox.setPadding(new Insets(30, 25, 30, 25));
        
        VBox articleContent = new VBox(12);
        articleContent.setPadding(new Insets(20));
        articleContent.setStyle(
            "-fx-background-color: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-radius: 8; " +
            "-fx-border-width: 1; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 3, 0.5, 0, 1);"
        );
        
        Label content = new Label(
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor " +
            "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
            "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n\n" +
            "Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n\n" +
            "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, " +
            "totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo."
        );
        content.setWrapText(true);
        content.setFont(Font.font("System", 12));
        content.setStyle("-fx-text-fill: #333; -fx-line-spacing: 1.5;");
        
        articleContent.getChildren().add(content);
        
        ScrollPane scrollPane = new ScrollPane(articleContent);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");
        
        contentBox.getChildren().add(scrollPane);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
        
        // Related articles section
        HBox relatedBox = new HBox(15);
        relatedBox.setPadding(new Insets(20, 25, 30, 25));
        relatedBox.setStyle("-fx-alignment: top-left;");
        
        Label relatedTitle = new Label("📖 Artikel Terkait:");
        relatedTitle.setFont(Font.font("System", 12));
        relatedTitle.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold;");
        
        VBox relatedList = new VBox(10);
        String[] related = {"Kesehatan Jantung", "Nutrisi Seimbang", "Olahraga Teratur"};
        for (String r : related) {
            Label relLink = new Label("• " + r);
            relLink.setStyle("-fx-text-fill: #009B88; -fx-font-size: 11; -fx-cursor: hand;");
            relatedList.getChildren().add(relLink);
        }
        
        // Note: We could add actual navigation here
        
        vbox.getChildren().addAll(articleHeader, contentBox);
        tab.setContent(vbox);
        return tab;
    }
    
    private Tab createDaftarAntarianTab() {
        Tab tab = new Tab("Daftar Antrian");
        tab.setClosable(false);
        
        VBox vbox = new VBox(0);
        vbox.setPadding(new Insets(0));
        vbox.setStyle("-fx-background-color: #f8f9fa;");
        
        // Header section
        VBox headerSection = new VBox(15);
        headerSection.setPadding(new Insets(25, 20, 20, 20));
        headerSection.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 0 1 0;");
        
        Label title = new Label("📋 Daftar Antrian dan Konsultasi Saya");
        title.setFont(Font.font("System", 18));
        title.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold;");
        
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        
        TextField searchField = new TextField();
        searchField.setPromptText("🔍 Cari kode antrian atau pasien...");
        searchField.setPrefWidth(400);
        searchField.setStyle(
            "-fx-padding: 8 12 8 12; " +
            "-fx-font-size: 11; " +
            "-fx-border-radius: 20; " +
            "-fx-background-radius: 20;"
        );
        
        Button searchBtn = new Button("Cari");
        searchBtn.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #009B88, #007B6E); " +
            "-fx-text-fill: white; " +
            "-fx-padding: 8 20 8 20; " +
            "-fx-border-radius: 20; " +
            "-fx-background-radius: 20;"
        );
        searchBtn.setOnAction(e -> {
            String query = searchField.getText().trim();
            if (query.isEmpty()) {
                showAlert("Pencarian", "Silakan masukkan nomor antrian atau nama pasien");
            } else {
                showAlert("Hasil Pencarian Antrian", "Mencari data untuk: '" + query + "'");
            }
        });
        searchBox.getChildren().addAll(searchField, searchBtn);
        
        headerSection.getChildren().addAll(title, searchBox);
        
        // Queue list section
        VBox contentSection = new VBox(15);
        contentSection.setPadding(new Insets(20));
        
        // Info bar about current queue
        HBox infoBar = new HBox(20);
        infoBar.setPadding(new Insets(12));
        infoBar.setStyle(
            "-fx-background-color: linear-gradient(to right, #f0fdfb, #e8f8f5); " +
            "-fx-border-color: #009B88; " +
            "-fx-border-radius: 6; " +
            "-fx-border-width: 1;"
        );
        
        Label infoText = new Label("⏰ Total Antrian: 5 | Waktu Tunggu Rata-rata: 15 menit");
        infoText.setStyle("-fx-text-fill: #009B88; -fx-font-weight: bold; -fx-font-size: 11;");
        infoBar.getChildren().add(infoText);
        
        // Antrian list
        ListView<String> queueListForAntrian = new ListView<>();
        queueListForAntrian.setPrefHeight(400);
        queueListForAntrian.getStyleClass().add("list-view");
        queueListForAntrian.setStyle(
            "-fx-control-inner-background: white; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-font-size: 11; " +
            "-fx-padding: 0;"
        );
        
        // Populate from existing queue
        refreshQueueDisplay(queueListForAntrian);
        
        // Button bar
        HBox buttonBar = new HBox(10);
        buttonBar.setPadding(new Insets(15, 0, 0, 0));
        buttonBar.setAlignment(Pos.CENTER_LEFT);
        
        Button btnRefresh = new Button("🔄 Refresh Antrian");
        btnRefresh.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #009B88, #007B6E); " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 16 8 16;"
        );
        btnRefresh.setOnAction(e -> refreshQueueDisplay(queueListForAntrian));
        
        Button btnCheckIn = new Button("✓ Check In Sekarang");
        btnCheckIn.setStyle(
            "-fx-background-color: linear-gradient(to bottom, #FFD700, #FFC700); " +
            "-fx-text-fill: #333; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 8 16 8 16;"
        );
        btnCheckIn.setOnAction(e -> {
            String sel = queueListForAntrian.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("⚠️ Perhatian", "Pilih antrian terlebih dahulu."); return; }
            // Extract appointment ID from the formatted string
            String[] parts = sel.split(" \\| ");
            if (parts.length > 0) {
                String apptId = parts[1].trim();
                Appointment appt = appointmentRepo.findById(apptId).orElse(null);
                if (appt == null) { showAlert("❌ Error", "Antrian tidak ditemukan."); return; }
                appt.setCheckedIn(true);
                appointmentRepo.save(appt.getId(), appt);
                showAlert("✅ Berhasil", "Anda berhasil check in!");
                refreshQueueDisplay(queueListForAntrian);
            }
        });
        
        buttonBar.getChildren().addAll(btnCheckIn, btnRefresh);
        
        contentSection.getChildren().addAll(infoBar, queueListForAntrian, buttonBar);
        VBox.setVgrow(queueListForAntrian, javafx.scene.layout.Priority.ALWAYS);
        
        vbox.getChildren().addAll(headerSection, contentSection);
        tab.setContent(vbox);
        return tab;
    }
    
    private void refreshQueueDisplay(ListView<String> listView) {
        listView.getItems().clear();
        
        java.util.List<Appointment> appts = new java.util.ArrayList<>(appointmentRepo.findAll());
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
            
            String time = a.getDateTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
            String line = String.format("%s | %s | Dr. %s (%s) | %s | %s", 
                time, a.getId(), dname, poliName, pname, status);
            listView.getItems().add(line);
        }
    }

    private Tab createPoliDokterTab() {
        Tab tab = new Tab("Poli & Dokter");
        tab.setClosable(false);

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));
    vbox.getStyleClass().add("panel");

    Label title = new Label("Manage Poli and Dokter");
    title.setFont(Font.font(18));
    title.getStyleClass().add("title");

        // Add Poli
        HBox hPoli = new HBox(8);
        TextField tfPoliId = new TextField(); tfPoliId.setPromptText("POLI id e.g. POLI-A");
        TextField tfPoliName = new TextField(); tfPoliName.setPromptText("Poli name");
        TextField tfPoliDesc = new TextField(); tfPoliDesc.setPromptText("Description");
        Button btnAddPoli = new Button("Add Poli");
        btnAddPoli.setOnAction(e -> {
            String id = tfPoliId.getText().trim();
            String name = tfPoliName.getText().trim();
            String desc = tfPoliDesc.getText().trim();
            if (id.isEmpty() || name.isEmpty()) { showAlert("Validation", "Poli id and name required."); return; }
            if (poliRepo.exists(id)) { showAlert("Exists", "Poli id already exists."); return; }
            Poli pl = new Poli(id, name, desc);
            poliRepo.save(id, pl);
            tfPoliId.clear(); tfPoliName.clear(); tfPoliDesc.clear();
            refreshPoliDokter();
        });
        hPoli.getChildren().addAll(tfPoliId, tfPoliName, tfPoliDesc, btnAddPoli);

        // Add Dokter
        HBox hDokter = new HBox(8);
        TextField tfDokId = new TextField(); tfDokId.setPromptText("D id e.g. D003");
        TextField tfDokName = new TextField(); tfDokName.setPromptText("Dokter name");
        ComboBox<String> cbPoliForDok = new ComboBox<>(); cbPoliForDok.setPromptText("Poli");
        ComboBox<String> cbProvForDok = new ComboBox<>(); cbProvForDok.setPromptText("Provider (optional)");
        Button btnAddDok = new Button("Add Dokter");
        btnAddDok.setOnAction(e -> {
            String id = tfDokId.getText().trim();
            String name = tfDokName.getText().trim();
            String poliSel = cbPoliForDok.getValue();
            String provSel = cbProvForDok.getValue();
            if (id.isEmpty() || name.isEmpty() || poliSel == null) { showAlert("Validation", "Dokter id, name and poli are required."); return; }
            if (dokterRepo.exists(id)) { showAlert("Exists", "Dokter id already exists."); return; }
            String poliId = poliSel.split(" - ")[0];
            String provId = provSel == null ? null : provSel.split(" - ")[0];
            Dokter dk = new Dokter(id, name, poliId, provId);
            dokterRepo.save(id, dk);
            // attach to poli
            Poli pl = poliRepo.findById(poliId).orElse(null);
            if (pl != null) { pl.addDoctorId(id); poliRepo.save(pl.getId(), pl); }
            tfDokId.clear(); tfDokName.clear(); cbPoliForDok.setValue(null); cbProvForDok.setValue(null);
            refreshPoliDokter();
        });
        hDokter.getChildren().addAll(tfDokId, tfDokName, cbPoliForDok, cbProvForDok, btnAddDok);

    poliListView.setPrefHeight(150);
    poliListView.getStyleClass().add("list-view");
    dokterListView.setPrefHeight(150);
    dokterListView.getStyleClass().add("list-view");

        vbox.getChildren().addAll(title, new Label("Add Poli:"), hPoli, new Label("Add Dokter:"), hDokter, new Label("Poli List"), poliListView, new Label("Dokter List"), dokterListView);
        tab.setContent(vbox);

        // populate combo boxes
        cbPoliForDok.getItems().clear();
        for (Poli pl : poliRepo.findAll()) cbPoliForDok.getItems().add(String.format("%s - %s", pl.getId(), pl.getName()));
        cbProvForDok.getItems().clear();
        for (Provider pr : providerRepo.findAll()) cbProvForDok.getItems().add(String.format("%s - %s", pr.getId(), pr.getName()));

        return tab;
    }

    private void refreshPoliDokter() {
        poliListView.getItems().clear();
        for (Poli pl : poliRepo.findAll()) {
            poliListView.getItems().add(String.format("%s - %s - %s", pl.getId(), pl.getName(), pl.getDescription()));
        }
        dokterListView.getItems().clear();
        for (Dokter dk : dokterRepo.findAll()) {
            dokterListView.getItems().add(String.format("%s - %s - poli:%s - prov:%s", dk.getId(), dk.getName(), dk.getPoliId(), dk.getProviderId()));
        }
        // update appointment selectors when poli/dokter change
        refreshAppointmentSelectors();
    }

    private Tab createAppointmentsTab() {
        Tab tab = new Tab("Appointments");
        tab.setClosable(false);

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));
    vbox.getStyleClass().add("panel");

    Label title = new Label("Make an Appointment");
    title.setFont(Font.font(18));
    title.getStyleClass().add("title");

        HBox hInputs = new HBox(8);
    // patient selector
    cbPatientBox = new ComboBox<>();
    cbPatientBox.setPromptText("Patient (ID - Name)");
    // poli selector
    cbPoliBox = new ComboBox<>();
    cbPoliBox.setPromptText("Poli (ID - Name)");
    // dokter selector (filtered by poli)
    cbDokterBox = new ComboBox<>();
    cbDokterBox.setPromptText("Dokter (ID - Name)");

        DatePicker dpDate = new DatePicker();
        TextField tfTime = new TextField();
        tfTime.setPromptText("HH:mm (e.g. 09:30)");
        TextField tfReason = new TextField();
        tfReason.setPromptText("Reason for visit");

        Button btnMake = new Button("Make Appointment");
        btnMake.setOnAction(e -> {
            String patSel = cbPatientBox.getValue();
            String poliSel = cbPoliBox.getValue();
            String dokterSel = cbDokterBox.getValue();
            if (patSel == null || poliSel == null || dokterSel == null || dpDate.getValue() == null) {
                showAlert("Validation", "Patient, poli, dokter and date are required.");
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
                showAlert("Validation", "Time must be in HH:mm format.");
                return;
            }
            LocalDate d = dpDate.getValue();
            LocalDateTime dateTime = LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), time.getHour(), time.getMinute());
            String reason = tfReason.getText().trim();

            try {
                    // create and attach appointment to patient and save globally
                    Appointment appt = new Appointment(patientId, dokterId, dateTime, reason);
                    Patient p = patientRepo.findById(patientId).orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
                    p.addAppointment(appt);
                    patientRepo.save(p.getId(), p);
                    appointmentRepo.save(appt.getId(), appt);
                    showAlert("Created", "Appointment created: " + appt.getId());
                    // refresh UI
                    refreshAppointments();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        // when poli changes, update dokter list
        cbPoliBox.setOnAction(ev -> {
            String sel = cbPoliBox.getValue();
            cbDokterBox.getItems().clear();
            if (sel != null) {
                String poliId = sel.split(" - ")[0];
                for (Dokter dk : dokterRepo.findAll()) {
                    if (poliId.equals(dk.getPoliId())) cbDokterBox.getItems().add(String.format("%s - %s", dk.getId(), dk.getName()));
                }
            }
        });

    hInputs.getChildren().addAll(cbPatientBox, cbPoliBox, cbDokterBox, dpDate, tfTime, tfReason, btnMake);

    appointmentsListView.setPrefHeight(400);
    appointmentsListView.getStyleClass().add("list-view");

        vbox.getChildren().addAll(title, hInputs, appointmentsListView);
        tab.setContent(vbox);

        // populate selectors
    // populate selectors
    refreshAppointmentSelectors();

    // add simple CRUD controls for appointments (delete/edit)
    HBox hApptButtons = new HBox(8);
    Button btnDelete = new Button("Delete Selected");
    btnDelete.setOnAction(ev -> {
        String sel = appointmentsListView.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Select", "Choose an appointment first."); return; }
        String apptId = sel.split(" - ")[0];
        // remove from global repo
        appointmentRepo.delete(apptId);
        // remove from patient record
        for (Patient pp : patientRepo.findAll()) {
            pp.getAppointments().removeIf(a -> a.getId().equals(apptId));
            patientRepo.save(pp.getId(), pp);
        }
        refreshAppointments();
    });

    Button btnEdit = new Button("Edit Selected");
    btnEdit.setOnAction(ev -> {
        String sel = appointmentsListView.getSelectionModel().getSelectedItem();
        if (sel == null) { showAlert("Select", "Choose an appointment first."); return; }
        String apptId = sel.split(" - ")[0];
        Appointment appt = appointmentRepo.findById(apptId).orElse(null);
        if (appt == null) { showAlert("Not found", "Appointment not found."); return; }
        // prompt for new date (yyyy-MM-dd)
        TextField tfNewDate = new TextField(); tfNewDate.setPromptText("yyyy-MM-dd");
        TextField tfNewTime = new TextField(); tfNewTime.setPromptText("HH:mm");
        TextField tfNewReason = new TextField(); tfNewReason.setPromptText("New reason (leave blank to keep)");
        HBox hb = new HBox(8, new Label("Date:"), tfNewDate, new Label("Time:"), tfNewTime, new Label("Reason:"), tfNewReason);
        Stage stage = new Stage();
        VBox vb = new VBox(10, hb, new Button("Save"));
        vb.setPadding(new Insets(10));
        Scene sc = new Scene(vb);
        Button save = (Button) vb.getChildren().get(1);
        save.setOnAction(ev2 -> {
            try {
                String nd = tfNewDate.getText().trim();
                String nt = tfNewTime.getText().trim();
                if (!nd.isEmpty() && !nt.isEmpty()) {
                    LocalDate d = LocalDate.parse(nd);
                    LocalTime t = LocalTime.parse(nt, DateTimeFormatter.ofPattern("H:mm"));
                    appt.setDateTime(LocalDateTime.of(d.getYear(), d.getMonth(), d.getDayOfMonth(), t.getHour(), t.getMinute()));
                }
                String nr = tfNewReason.getText().trim();
                if (!nr.isEmpty()) appt.setReason(nr);
                appointmentRepo.save(appt.getId(), appt);
                // patient list contains same instance, so it's updated already
                refreshAppointments();
                stage.close();
            } catch (Exception ex) {
                showAlert("Error", "Invalid date/time format or other error: " + ex.getMessage());
            }
        });
        stage.setScene(sc);
        stage.setTitle("Edit Appointment");
        stage.show();
    });

    hApptButtons.getChildren().addAll(btnEdit, btnDelete);
    vbox.getChildren().add(hApptButtons);

        return tab;
    }

    private void refreshAppointments() {
        appointmentsListView.getItems().clear();
        for (Appointment a : appointmentRepo.findAll()) {
            Patient p = patientRepo.findById(a.getPatientId()).orElse(null);
            Dokter d = dokterRepo.findById(a.getDoctorId()).orElse(null);
            String pname = p == null ? a.getPatientId() : p.getName();
            String dname = d == null ? a.getDoctorId() : d.getName();
            String status = a.isCheckedIn() ? "ARRIVED" : "SCHEDULED";
            String line = String.format("%s - patient:%s - dokter:%s - %s - %s - %s", a.getId(), pname, dname, a.getDateTime(), a.getReason(), status);
            appointmentsListView.getItems().add(line);
        }
    }

    private Tab createPatientQueueTab() {
        Tab tab = new Tab("Queue");
        tab.setClosable(false);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getStyleClass().add("panel");

        Label title = new Label("Seluruh Antrian Hari Ini");
        title.setFont(Font.font(18));
        title.getStyleClass().add("title");

        queueListView.setPrefHeight(400);
        queueListView.getStyleClass().add("list-view");

        Button btnCheckIn = new Button("Check In");
        btnCheckIn.setOnAction(ev -> {
            String sel = queueListView.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Select", "Choose an appointment first."); return; }
            String apptId = sel.split(" \\| ")[0].trim();
            Appointment appt = appointmentRepo.findById(apptId).orElse(null);
            if (appt == null) { showAlert("Not found", "Appointment not found."); return; }
            appt.setCheckedIn(true);
            appointmentRepo.save(appt.getId(), appt);
            refreshQueueForPatient();
            refreshAppointments();
        });

        Button btnRefreshQueue = new Button("Refresh");
        btnRefreshQueue.setOnAction(e -> refreshQueueForPatient());

        HBox hbButtons = new HBox(10, btnCheckIn, btnRefreshQueue);

        vbox.getChildren().addAll(title, queueListView, hbButtons);
        tab.setContent(vbox);

        // initial load
        refreshQueueForPatient();

        return tab;
    }

    private void refreshQueueForPatient() {
        // Show ALL appointments (global queue view), sorted by datetime
        queueListView.getItems().clear();
        
        java.util.List<Appointment> appts = new java.util.ArrayList<>(appointmentRepo.findAll());
        appts.sort((a1, a2) -> a1.getDateTime().compareTo(a2.getDateTime()));

        if (appts.isEmpty()) {
            queueListView.getItems().add("Tidak ada antrian");
            return;
        }

        for (Appointment a : appts) {
            Patient p = patientRepo.findById(a.getPatientId()).orElse(null);
            Dokter d = dokterRepo.findById(a.getDoctorId()).orElse(null);
            Poli poli = d == null ? null : poliRepo.findById(d.getPoliId()).orElse(null);
            
            String pname = p == null ? a.getPatientId() : p.getName();
            String dname = d == null ? a.getDoctorId() : d.getName();
            String poliName = poli == null ? "Unknown" : poli.getName();
            String status = a.isCheckedIn() ? "✓ ARRIVED" : "⏳ SCHEDULED";
            
            String time = a.getDateTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
            String line = String.format("%s | %s | Dr.%s (%s) | %s | %s | %s", 
                time, a.getId().substring(0, 8), dname, poliName, pname, a.getReason(), status);
            queueListView.getItems().add(line);
        }
    }

    private void refreshAppointmentSelectors() {
        if (cbPatientBox == null || cbPoliBox == null || cbDokterBox == null) return;
        cbPatientBox.getItems().clear();
        for (Patient p : patientRepo.findAll()) cbPatientBox.getItems().add(String.format("%s - %s", p.getId(), p.getName()));
        cbPoliBox.getItems().clear();
        for (Poli pl : poliRepo.findAll()) cbPoliBox.getItems().add(String.format("%s - %s", pl.getId(), pl.getName()));
        cbDokterBox.getItems().clear();
        for (Dokter dk : dokterRepo.findAll()) cbDokterBox.getItems().add(String.format("%s - %s", dk.getId(), dk.getName()));
    }

    private Tab createPatientsTab() {
        Tab tab = new Tab("Patients");
        tab.setClosable(false);

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));
    vbox.getStyleClass().add("panel");

    Label title = new Label("Registered Patients");
    title.setFont(Font.font(18));
    title.getStyleClass().add("title");

        HBox hAdd = new HBox(8);
        TextField tfId = new TextField();
        tfId.setPromptText("ID (e.g. P003)");
        TextField tfName = new TextField();
        tfName.setPromptText("Full name");
        TextField tfEmail = new TextField();
        tfEmail.setPromptText("Email");
        DatePicker dpBirth = new DatePicker();

        Button btnAdd = new Button("Add Patient");
        btnAdd.setOnAction(e -> {
            String id = tfId.getText().trim();
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            LocalDate bd = dpBirth.getValue();
            if (id.isEmpty() || name.isEmpty()) {
                showAlert("Validation", "ID and name are required.");
                return;
            }
            Patient existing = patientRepo.findById(id).orElse(null);
            if (existing != null) {
                showAlert("Exists", "Patient with ID already exists.");
                return;
            }
            Patient p = new Patient(id, name, email, bd == null ? LocalDate.now() : bd);
            patientRepo.save(id, p);
            refreshPatients();
            tfId.clear(); tfName.clear(); tfEmail.clear(); dpBirth.setValue(null);
        });

        hAdd.getChildren().addAll(tfId, tfName, tfEmail, dpBirth, btnAdd);
        hAdd.setAlignment(Pos.CENTER_LEFT);

    patientListView.setPrefHeight(400);
    patientListView.getStyleClass().add("list-view");
        Button btnEnroll = new Button("Enroll Selected in Insurance");
        btnEnroll.setOnAction(e -> {
            String sel = patientListView.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Select", "Choose a patient first."); return; }
            String id = sel.split(" - ")[0];
            try {
                insuranceService.enroll(id, Insurance.Tier.BASIC);
                showAlert("Enrolled", "Patient " + id + " enrolled in insurance (BASIC tier).");
                refreshClaims();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        vbox.getChildren().addAll(title, hAdd, patientListView, btnEnroll);
        tab.setContent(vbox);
        return tab;
    }

    private Tab createHealthInsuranceTab() {
        Tab tab = new Tab("Health Insurance");
        tab.setClosable(false);

        // Create TabPane inside to separate Insurance Registration vs Claims
        TabPane insuranceTabPane = new TabPane();
        insuranceTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        // ===== TAB 1: INSURANCE REGISTRATION =====
        Tab tabRegistration = new Tab("Pendaftaran");
        tabRegistration.setClosable(false);
        VBox vboxReg = new VBox(10);
        vboxReg.setPadding(new Insets(10));
        vboxReg.getStyleClass().add("panel");

        Label titleReg = new Label("Pendaftaran Asuransi Kesehatan");
        titleReg.setFont(Font.font(18));
        titleReg.getStyleClass().add("title");

        // Insurance tiers info
        VBox vbTiers = new VBox(8);
        vbTiers.setStyle("-fx-border-color: #e0e0e0; -fx-border-radius: 4; -fx-padding: 10;");
        
        Label tierTitle = new Label("Paket Asuransi Tersedia:");
        tierTitle.setFont(Font.font("System", 12));
        
        Label tier1 = new Label("🔷 BASIC: Rp 500.000/bulan - Konsultasi umum, obat-obatan dasar");
        Label tier2 = new Label("🔵 STANDARD: Rp 1.000.000/bulan - Semua benefit BASIC + Khusus gigi + Lab");
        Label tier3 = new Label("🔶 PREMIUM: Rp 2.000.000/bulan - Semua benefit + Spesialis + Rawat inap");
        
        tier1.setStyle("-fx-font-size: 11;");
        tier2.setStyle("-fx-font-size: 11;");
        tier3.setStyle("-fx-font-size: 11;");
        
        vbTiers.getChildren().addAll(tierTitle, tier1, tier2, tier3);

        // Patient selection
        ComboBox<String> cbPatientReg = new ComboBox<>();
        cbPatientReg.setPromptText("Pilih Pasien");
        for (Patient p : patientRepo.findAll()) {
            cbPatientReg.getItems().add(String.format("%s - %s", p.getId(), p.getName()));
        }

        // Insurance tier selection
        ComboBox<String> cbInsuranceTier = new ComboBox<>();
        cbInsuranceTier.setPromptText("Pilih Paket Asuransi");
        cbInsuranceTier.getItems().addAll("BASIC - Rp 500.000/bulan", "STANDARD - Rp 1.000.000/bulan", "PREMIUM - Rp 2.000.000/bulan");

        Button btnRegisterInsurance = new Button("Daftar Asuransi");
        btnRegisterInsurance.setStyle("-fx-font-size: 12; -fx-padding: 8 20 8 20;");
        btnRegisterInsurance.setOnAction(e -> {
            String patSel = cbPatientReg.getValue();
            String tierSel = cbInsuranceTier.getValue();
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
                cbPatientReg.setValue(null);
                cbInsuranceTier.setValue(null);
                refreshClaims();
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        HBox hbRegistration = new HBox(10, cbPatientReg, cbInsuranceTier, btnRegisterInsurance);
        hbRegistration.setAlignment(Pos.CENTER_LEFT);

        // List of enrolled patients
        Label lblEnrolled = new Label("Pasien Terdaftar Asuransi:");
        lblEnrolled.setFont(Font.font("System", 12));
        
        ListView<String> enrolledListView = new ListView<>();
        enrolledListView.setPrefHeight(250);
        enrolledListView.getStyleClass().add("list-view");
        refreshEnrolledPatients(enrolledListView);

        Button btnRefreshEnrolled = new Button("Refresh");
        btnRefreshEnrolled.setOnAction(e -> refreshEnrolledPatients(enrolledListView));

        vboxReg.getChildren().addAll(titleReg, vbTiers, new Label(""), lblEnrolled, hbRegistration, enrolledListView, btnRefreshEnrolled);
        tabRegistration.setContent(vboxReg);

        // ===== TAB 2: CLAIMS MANAGEMENT =====
        Tab tabClaims = new Tab("Klaim");
        tabClaims.setClosable(false);
        VBox vboxClaims = new VBox(10);
        vboxClaims.setPadding(new Insets(10));
        vboxClaims.getStyleClass().add("panel");

        Label titleClaims = new Label("Manajemen Klaim Asuransi");
        titleClaims.setFont(Font.font(18));
        titleClaims.getStyleClass().add("title");

        HBox hInputs = new HBox(8);
        TextField tfPatientId = new TextField();
        tfPatientId.setPromptText("Patient ID (e.g. P001)");
        TextField tfAmount = new TextField();
        tfAmount.setPromptText("Amount (numeric)");
        TextField tfProvider = new TextField();
        tfProvider.setPromptText("Provider ID (e.g. HSP01)");
        TextField tfDesc = new TextField();
        tfDesc.setPromptText("Description");

        Button btnSubmit = new Button("Submit Claim");
        btnSubmit.setOnAction(e -> {
            String pid = tfPatientId.getText().trim();
            String amtS = tfAmount.getText().trim();
            String prov = tfProvider.getText().trim();
            String desc = tfDesc.getText().trim();
            if (pid.isEmpty() || amtS.isEmpty() || prov.isEmpty()) {
                showAlert("Validation", "Patient ID, amount and provider are required.");
                return;
            }
            try {
                double amt = Double.parseDouble(amtS);
                Claim claim = insuranceService.submitClaim(pid, amt, LocalDate.now(), prov, desc);
                showAlert("Submitted", "Claim submitted: " + claim.getId());
                refreshClaims();
            } catch (NumberFormatException nfe) {
                showAlert("Validation", "Amount must be numeric.");
            } catch (Exception ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        hInputs.getChildren().addAll(tfPatientId, tfAmount, tfProvider, tfDesc, btnSubmit);

        claimsListView.setPrefHeight(250);
        claimsListView.getStyleClass().add("list-view");

        Button btnApprove = new Button("Approve Selected");
        btnApprove.setOnAction(e -> {
            String sel = claimsListView.getSelectionModel().getSelectedItem();
            if (sel == null) { showAlert("Select", "Choose a claim first."); return; }
            String claimId = sel.split(" - ")[0];
            // find claim in patient insurance
            boolean found = false;
            for (Patient patient : patientRepo.findAll()) {
                if (!patient.isEnrolled()) continue;
                Insurance insurance = patient.getInsurance();
                for (Claim c : insurance.getClaims()) {
                    if (c.getId().equals(claimId)) {
                        insuranceService.reviewClaim(patient.getId(), claimId, true);
                        showAlert("Approved", "Claim approved.");
                        refreshClaims();
                        found = true;
                        break;
                    }
                }
                if (found) break;
            }
            if (!found) showAlert("Not found", "Claim not found in insurance.");
        });

        vboxClaims.getChildren().addAll(titleClaims, hInputs, claimsListView, btnApprove);
        tabClaims.setContent(vboxClaims);

        // Add both sub-tabs to the inner TabPane
        insuranceTabPane.getTabs().addAll(tabRegistration, tabClaims);

        tab.setContent(insuranceTabPane);
        return tab;
    }

    private void refreshEnrolledPatients(ListView<String> enrolledListView) {
        enrolledListView.getItems().clear();
        for (Patient patient : patientRepo.findAll()) {
            if (!patient.isEnrolled()) continue;
            Insurance insurance = patient.getInsurance();
            String line = String.format("%s - Asuransi:%s - Tier:%s - Claims:%d", 
                patient.getName(), insurance.getId(), insurance.getTier(), insurance.getClaims().size());
            enrolledListView.getItems().add(line);
        }
    }

    private Tab createNewsTab() {
        Tab tab = new Tab("News");
        tab.setClosable(false);

    VBox vbox = new VBox(10);
    vbox.setPadding(new Insets(10));
    vbox.getStyleClass().add("panel");
    Label title = new Label("Health Industry News");
    title.setFont(Font.font(18));
    title.getStyleClass().add("title");

    newsListView.setPrefHeight(450);
    newsListView.getStyleClass().add("list-view");

        Button btnRefresh = new Button("Refresh");
        btnRefresh.setOnAction(e -> refreshNews());

        vbox.getChildren().addAll(title, newsListView, btnRefresh);
        tab.setContent(vbox);
        return tab;
    }

    private void refreshPatients() {
        patientListView.getItems().clear();
        for (Patient p : patientRepo.findAll()) {
            String line = String.format("%s - %s - %s", p.getId(), p.getName(), p.getEmail());
            patientListView.getItems().add(line);
        }
        // update appointment selectors when patients change
        refreshAppointmentSelectors();
    }

    private void refreshClaims() {
        claimsListView.getItems().clear();
        for (Patient patient : patientRepo.findAll()) {
            if (!patient.isEnrolled()) continue;
            Insurance insurance = patient.getInsurance();
            for (Claim c : insurance.getClaims()) {
                String line = String.format("%s - Tier:%s - insurance:%s - %s - %.2f - %s", 
                    c.getId(), insurance.getTier(), c.getPolicyNumber(), c.getProviderId(), c.getAmount(), c.getState());
                claimsListView.getItems().add(line);
            }
        }
    }

    private void refreshNews() {
        newsListView.getItems().clear();
        List<NewsArticle> news = newsService.fetchLatest(10);
        for (NewsArticle n : news) {
            String line = String.format("%s | %s | %s", n.getPublishedAt(), n.getTitle(), n.getSource());
            newsListView.getItems().add(line);
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    private void handleQuickAction(String action) {
        switch(action) {
            case "📋 Buat Janji Temu":
                showAlert("Buat Janji Temu", "Fitur membuat janji temu baru.\n" + 
                    "Silakan pergi ke tab 'Appointments' untuk menambah janji temu baru.");
                break;
            case "👤 Daftar Pasien Baru":
                showAlert("Daftar Pasien Baru", "Fitur pendaftaran pasien baru.\n" + 
                    "Silakan pergi ke tab 'Patients' untuk menambah pasien baru dengan tombol 'Add Patient'.");
                break;
            case "💳 Proses Klaim Asuransi":
                showAlert("Proses Klaim Asuransi", "Fitur pemrosesan klaim asuransi.\n" + 
                    "Silakan pergi ke tab 'Health Insurance' untuk mengelola klaim.");
                break;
            case "📊 Lihat Laporan":
                showAlert("Lihat Laporan", "Dashboard tersedia di tab pertama dengan:\n" +
                    "• Jumlah pasien terdaftar\n" +
                    "• Janji temu aktif\n" +
                    "• Klaim yang diproses\n" +
                    "• Provider jaringan\n" +
                    "• Log aktivitas real-time");
                break;
            case "🩺 Lihat Riwayat Medis":
                showAlert("Lihat Riwayat Medis", "Informasi riwayat medis tersedia di tab 'Patients'.\n" +
                    "Pilih pasien dari daftar untuk melihat detail dan riwayat mereka.");
                break;
            case "⚙️ Pengaturan Sistem":
                showAlert("Pengaturan Sistem", "Sistem sedang berjalan dengan normal.\n" +
                    "• Database: In-Memory\n" +
                    "• Status: Online\n" +
                    "• Version: 2.0 Professional Edition");
                break;
            default:
                showAlert("Action", "Tombol: " + action);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
