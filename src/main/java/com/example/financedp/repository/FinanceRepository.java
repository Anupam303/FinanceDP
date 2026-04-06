package com.example.financedp.repository;

import com.example.financedp.model.EntryType;
import com.example.financedp.model.FinancialData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FinanceRepository extends JpaRepository<FinancialData, Long> {

    List<FinancialData> findByUserId(Long userId);
//    List<FinancialData> findByUserIdAndType(Long userId, EntryType type);
//    List<FinancialData> findByCategory(String category);
//    List<FinancialData> findByDate(LocalDate date);
//    List<FinancialData> findByType(EntryType type);
    List<FinancialData> findByUserUsername(String username);
}
