package com.apps.customerbackend.Facade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericCRUDRepository<T, ID> extends JpaRepository<T, ID> {
}
