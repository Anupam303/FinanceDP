package com.example.financedp.service;

import com.example.financedp.exception.ResourceNotFoundException;
import com.example.financedp.model.EntryType;
import com.example.financedp.model.FinancialData;
import com.example.financedp.model.User;
import com.example.financedp.repository.FinanceRepository;
import com.example.financedp.repository.UserRepository;
import com.example.financedp.security.CustomUserDetails;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private FinanceRepository financeRepository;

    public DashboardService(FinanceRepository financeRepository) {
        this.financeRepository = financeRepository;
    }

    public Map<String, Object> getSummary(){

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
                                        .getAuthentication()
                                        .getPrincipal();
        User user = userDetails.getUser();
        String username = user.getUsername();
        String role = user.getRole().name();

        List<FinancialData> data;

        if(role.equals("VIEWER")){
            data = financeRepository.findByUserUsername(username);
        } else {
            data = financeRepository.findAll();
        }

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
