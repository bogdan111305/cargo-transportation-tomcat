package com.example.cargo_transportation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Embeddable
public class GetServiceId implements Serializable {

    @Column(name = "journal_id")
    @ToString.Include
    private Long journalId;
    @Column(name = "service_id")
    @ToString.Include
    private Long serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GetServiceId that = (GetServiceId) o;
        return Objects.equals(journalId, that.journalId) && Objects.equals(serviceId, that.serviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(journalId, serviceId);
    }
}
