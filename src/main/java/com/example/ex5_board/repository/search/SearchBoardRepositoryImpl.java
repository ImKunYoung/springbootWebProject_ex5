package com.example.ex5_board.repository.search;

import com.example.ex5_board.entity.Board;
import com.example.ex5_board.entity.QBoard;
import com.example.ex5_board.entity.QMember;
import com.example.ex5_board.entity.QReply;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {

    /*description - QuerydslRepositorySupport 에 생성자가 존재함으로 클래스 내에서 super() 를 이용해 호출*/
    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    /*JPQL Query 객체*/
    @Override
    public Board search1() {

        log.info("search1............................");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        jpqlQuery.select(board, member.email, reply.count()).groupBy(board);

        log.info("_________________________________________________");
        log.info(jpqlQuery);
        log.info("_________________________________________________");


        List<Board> result = jpqlQuery.fetch();

        return null;

    }

}
