package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SampleDTO {
    private long id;
    private String first;
    private String last;
    private LocalDateTime regDateTime;
}
