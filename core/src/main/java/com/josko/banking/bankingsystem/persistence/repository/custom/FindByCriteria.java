package com.josko.banking.bankingsystem.persistence.repository.custom;

import java.util.List;


public interface FindByCriteria<C, T> {
    
    List<T> findByCriteria(C criteria);    
}
