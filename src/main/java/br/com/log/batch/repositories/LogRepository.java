package br.com.log.batch.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.log.batch.entities.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
