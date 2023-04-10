package com.example.cargo_transportation.modal.report;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface StatisticsReport {
    Long getClientId();
    Long getCount();
    Long getCost();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime getIncomingDateMin();
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime getIncomingDateMax();
}
