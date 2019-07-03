package br.com.log.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.log.batch.model.LogLine;

public interface LogRepository extends JpaRepository<LogLine, Long> {

}
