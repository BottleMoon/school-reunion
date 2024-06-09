package flab.schoolreunion.board.repository;

import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardRepositoryCustom {

    @Override
    Page<Board> search(BoardSearchCondition condition, Pageable pageable);

    @Modifying
    @Query("delete " +
            " from Board b" +
            " where b.memberId = :memberId"
    )
    void deleteAllByMemberId(Long memberId);
}