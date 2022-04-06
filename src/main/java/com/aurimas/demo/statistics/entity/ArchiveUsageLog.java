package com.aurimas.demo.statistics.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "archive_usage_log")
public class ArchiveUsageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "ip")
    private String ip;

    @Column(name = "usage_count")
    private int usageCount;

    public ArchiveUsageLog() {
    }

    public ArchiveUsageLog(String ip, int usageCount) {
        this.ip = ip;
        this.usageCount = usageCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(int usageCount) {
        this.usageCount = usageCount;
    }
}
