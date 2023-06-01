package com.rest_api.fs14backend.order;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@Data
@NoArgsConstructor
public class OrderDTO {
    private Long userId;
}
