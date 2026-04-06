package com.example.financedp.service;

import com.example.financedp.exception.ResourceNotFoundException;
import com.example.financedp.model.FinancialData;
import com.example.financedp.model.Role;
import com.example.financedp.model.User;
import com.example.financedp.repository.FinanceRepository;
import com.example.financedp.repository.UserRepository;
import com.example.financedp.security.CustomUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceService {

    private final UserRepository userRepository;
    private final FinanceRepository financeRepository;

    public FinanceService(FinanceRepository financeRepository, UserRepository userRepository) {
        this.financeRepository = financeRepository;
        this.userRepository = userRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FinancialData create(Long id, FinancialData financialData){
        User user = userRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );
        financialData.setUser(user);
        return financeRepository.save(financialData);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public FinancialData update(Long id, FinancialData financialData){
        FinancialData dataToUpdate = financeRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Data not found")
                );
        dataToUpdate.setAmount(financialData.getAmount());
        dataToUpdate.setCategory(financialData.getCategory());
        dataToUpdate.setType(financialData.getType());
        dataToUpdate.setDate(financialData.getDate());
        dataToUpdate.setDescription(financialData.getDescription());

        return financeRepository.save(dataToUpdate);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id){
        if(!financeRepository.existsById(id)){
            throw new ResourceNotFoundException("Data not found");
        }

        financeRepository.deleteById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'ANALYST', 'VIEWER')")
    public List<FinancialData> getAll(){

        CustomUserDetails userDetails =(CustomUserDetails) SecurityContextHolder.getContext()
                                        .getAuthentication()
                                        .getPrincipal();
        User user = userDetails.getUser();

        if(user.getRole() == Role.VIEWER){
            return financeRepository.findByUserId(user.getId());
        }

        return financeRepository.findAll();
    }
}
