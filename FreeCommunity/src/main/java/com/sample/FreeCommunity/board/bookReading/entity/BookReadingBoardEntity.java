package com.sample.FreeCommunity.board.bookReading.entity;

import com.sample.FreeCommunity.board.boardParents.BoardEntityParent;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookReadingBoardEntity extends BoardEntityParent {

    String boardTitle = "bookReading";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
