package com.example.cargo_transportation.modal.report;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface JournalReport {
    String getWaybill();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime getIncomingDateTime();
    String getGosNum();
    String getService();
}
