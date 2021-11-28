package com.example.board1.domain;

import com.example.board1.dto.BoardDto;
import com.example.board1.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
public class Comment extends Timestamped{


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(nullable = false)
    private String coauthor;


    @Column(nullable = false)
    private String cocontent;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long boardId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public Comment(CommentDto commentDto, Long userId, Long boardId) {

        this.userId = userId;
        this.boardId = boardId;
        this.coauthor = commentDto.getCoauthor();
        this.cocontent = commentDto.getCocontent();

    }


    public void update(CommentDto commentDto) {

        this.cocontent = commentDto.getCocontent();

        this.coauthor = commentDto.getCoauthor();
    }



}
