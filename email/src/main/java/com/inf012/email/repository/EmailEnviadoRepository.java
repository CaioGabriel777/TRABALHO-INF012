package com.inf012.email.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inf012.email.model.EmailEnviado;

@Repository
public interface EmailEnviadoRepository extends JpaRepository<EmailEnviado, Long> {
}
