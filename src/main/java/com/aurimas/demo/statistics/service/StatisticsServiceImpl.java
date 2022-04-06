package com.aurimas.demo.statistics.service;

import com.aurimas.demo.statistics.dao.ArchiveLogDao;
import com.aurimas.demo.statistics.entity.ArchiveUsageLog;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final ArchiveLogDao logDao;

    public StatisticsServiceImpl(ArchiveLogDao logDao) {
        this.logDao = logDao;
    }

    @Override
    public void logArchiveUsage(String ip) {
        logDao.findByIp(ip).ifPresentOrElse(
                log -> {
                    log.setUsageCount(log.getUsageCount() + 1);
                    logDao.save(log);
                }, () -> logDao.save(new ArchiveUsageLog(ip, 1)));
    }
}
