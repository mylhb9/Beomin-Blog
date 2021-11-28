package com.example.board1.domain;

import com.example.board1.dto.BoardDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@ToString
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class) // 뭔지 모르겠음
public class Board extends Timestamped{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;

    @Column(columnDefinition = "integer default 0")
    private int views;

    @Column
    private int likeCnt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Board (BoardDto boardDto, Long userId) {
        this.userId = userId;
        this.author = boardDto.getAuthor();
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
        this.likeCnt = 0;
    }

    public void addViewCount(Board board) {
        this.views = board.getViews() + 1;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }




    public void update(BoardDto boardDto) {

        this.author = boardDto.getAuthor();
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }



}
