package com.ef.parser.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ef.parser.model.AccessLog;

public interface AccessLogRepository extends JpaRepository<AccessLog, Long> {

	@Query(nativeQuery=true, value="SELECT IP FROM ACCESS_LOG WHERE ACCESS_DATE BETWEEN ?1 AND ?2 GROUP BY IP HAVING COUNT(IP) >= ?3")
	List<String> findIpsByThreshold(LocalDateTime startDate, LocalDateTime endDate, int Threshold);
}
