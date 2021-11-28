package com.example.board1.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private String coauthor;
    private String cocontent;
    private Long userId;
    private Long boardId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
