package com.example.board1.dto;


import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private Long userId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
