package com.example.jpablog.mail.repository;

import com.example.jpablog.mail.entity.MailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailTemplateRepository extends JpaRepository<MailTemplate, Long>{

    Optional<MailTemplate> findByTemplateId(String templateId);
}
