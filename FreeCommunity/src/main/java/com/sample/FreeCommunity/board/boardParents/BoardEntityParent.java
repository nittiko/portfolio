package com.sample.FreeCommunity.board.boardParents;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

//게시판 공통 속성용 부모 엔티티


public class BoardEntityParent {

    String boardTitle;

    @Id
    private String id;

    @Column(nullable = false)  // 제목은 필수
    private String title;

    @Lob  // 큰 텍스트 데이터
    private String content;

    @CreatedDate
    @Column(updatable = false)  // 생성일은 수정되지 않도록
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)  // 수정일은 필수
    private LocalDateTime updatedAt;

    @Column(nullable = false)  // 작성자는 필수
    private String author;

    @Column(nullable = false)
    private int visitCount = 0;

    @Column(nullable = false)
    private int thumbUp = 0;
}
