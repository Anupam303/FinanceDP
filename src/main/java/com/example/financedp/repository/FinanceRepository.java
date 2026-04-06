package com.example.financedp.repository;

import com.example.financedp.model.FinancialData;
import com.example.financedp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FinanceRepository extends JpaRepository<FinancialData, Long> {

    List<FinancialData> findByUserId(Long userId);
    List<FinancialData> findByUser(User user);
}
