package com.example.financedp.service;

import com.example.financedp.model.EntryType;
import com.example.financedp.model.FinancialData;
import com.example.financedp.model.User;
import com.example.financedp.repository.FinanceRepository;
import com.example.financedp.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final FinanceRepository financeRepository;

    public DashboardService(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    public Map<String, Object> getSummary(){

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        User user = userDetails.getUser();
        String role = user.getRole().name();

        if(role.equals("VIEWER")){
            List<FinancialData> data = financeRepository.findByUser(user);
            return calculateSummary(data);
        } else {
            List<FinancialData> data = financeRepository.findAll();

            Map<String, List<FinancialData>> groupedData = data.stream()
                    .collect(Collectors.groupingBy(fd -> fd.getUser().getUsername()));

            Map<String, Object> allUsersSummary = new HashMap<>();
            for(Map.Entry<String, List<FinancialData>> entry : groupedData.entrySet()) {
                allUsersSummary.put(entry.getKey(), calculateSummary(entry.getValue()));
            }

            return allUsersSummary;
        }
    }

    private Map<String, Object> calculateSummary(List<FinancialData> data) {
        double income = 0;
        double expense = 0;

        Map<String, Double> summary = new HashMap<>();
        for(FinancialData fd : data){
            if(fd.getType() == EntryType.INCOME){
                income += fd.getAmount();
            } else {
                expense += fd.getAmount();
            }

            summary.put(
                    fd.getCategory(),
                    summary.getOrDefault(fd.getCategory(), 0.0) + fd.getAmount()
            );
        }
        double balance = income - expense;

        Map<String, Object> dashSummary = new HashMap<>();

        dashSummary.put("TotalIncome", income);
        dashSummary.put("TotalExpense", expense);
        dashSummary.put("Balance", balance);
        dashSummary.put("ExpenditureSummary", summary);

        return dashSummary;
    }
}
