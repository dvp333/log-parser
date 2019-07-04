package br.com.log.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.log.batch.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}
