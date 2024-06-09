package flab.schoolreunion.board.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.entity.Board;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static flab.schoolreunion.board.entity.QBoard.board;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Page<Board> search(BoardSearchCondition condition, Pageable pageable) {
        List<Board> content = queryFactory.
                select(board)
                .from(board)
                .where(getWhereQuery(condition))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(board.count())
                .from(board);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private Predicate getWhereQuery(BoardSearchCondition condition) {
        BooleanExpression query = board.reunionId.eq(condition.getReunionId());
        try {
            switch (condition.getTarget()) {
                case TITLE -> query = query.and(board.title.contains(condition.getKeyword()));
                case CONTENT -> query = query.and(board.content.contains(condition.getKeyword()));
                case USERNAME -> query = query.and(board.memberName.contains(condition.getKeyword()));
            }
        } catch (NullPointerException e) {
            return query;
        }

        return query;
    }
}