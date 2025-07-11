package com.wsd.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@ToString(callSuper = true)
@Getter
@Setter
@EqualsAndHashCode
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Audit<U> {
    @CreatedDate
    @JsonIgnore
    @Column(name = "created_at")
    protected Instant createdAt;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "modified_at")
    protected Instant modifiedAt;

    @CreatedBy
    @Column(name = "created_by")
    protected U createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    protected U modifiedBy;

    public Long getCreatedAtTimestamp() {
        return createdAt != null ? createdAt.getEpochSecond() : 0L;
    }

    public Long getModifiedAtTimestamp() {
        return modifiedAt != null ? modifiedAt.getEpochSecond() : 0L;
    }
}