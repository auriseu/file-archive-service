package com.aurimas.demo.statistics.dao;

import java.util.Optional;

import com.aurimas.demo.statistics.entity.ArchiveUsageLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchiveLogDao extends JpaRepository<ArchiveUsageLog, Long> {

    Optional<ArchiveUsageLog> findByIp(String ip);

}
