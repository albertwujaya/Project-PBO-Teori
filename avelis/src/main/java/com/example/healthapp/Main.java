package com.example.healthapp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // repositories
        InMemoryRepository<Patient> patientRepo = new InMemoryRepository<>();
        InMemoryRepository<Provider> providerRepo = new InMemoryRepository<>();

        // create services
        InsuranceService insuranceService = new InsuranceService(patientRepo, providerRepo);
        NewsService newsService = new MockNewsService(); // swap with HttpNewsService(apiKey) to fetch live data

        // create sample data
        Patient p = new Patient("P001", "Budi Santoso", "budi@example.com", LocalDate.of(1990, 5, 20));
        patientRepo.save(p.getId(), p);
        Provider prov = new Provider("HSP01", "Rumah Sakit Sehat", "Jakarta");
        providerRepo.save(prov.getId(), prov);

        System.out.println("== Enrolling patient ==");
        insuranceService.enroll(p.getId(), Insurance.Tier.BASIC);
        Insurance ins = p.getInsurance();
        System.out.println("Enrolled: " + ins);

        System.out.println("\n== Booking appointment ==");
        Appointment appt = new Appointment(p.getId(), prov.getId(), LocalDateTime.now().plusDays(3), "General checkup");
        p.addAppointment(appt);
        patientRepo.save(p.getId(), p);
        System.out.println("Appointment added: " + appt);

        System.out.println("\n== Submitting claim ==");
        Claim claim = insuranceService.submitClaim(p.getId(), 2500000.0, LocalDate.now(), prov.getId(), "Surgery X");
        System.out.println("Claim submitted: " + claim);

        System.out.println("\n== Reviewing claim (approving) ==");
        insuranceService.reviewClaim(p.getId(), claim.getId(), true);
        System.out.println("Claim approved");

        System.out.println("\n== Fetching news ==");
        List<NewsArticle> news = newsService.fetchLatest(5);
        news.forEach(n -> System.out.println(n));

        System.out.println("\n== Demo finished ==");
    }
}
