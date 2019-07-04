package com.ef.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ef.parser.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

}
