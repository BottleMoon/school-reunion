package flab.schoolreunion.board.service;

import com.rabbitmq.client.Channel;
import flab.schoolreunion.board.dto.board.BoardRequest;
import flab.schoolreunion.board.dto.board.BoardResponse;
import flab.schoolreunion.board.dto.board.BoardSearchCondition;
import flab.schoolreunion.board.dto.board.BoardUpdateRequest;
import flab.schoolreunion.board.entity.Board;
import flab.schoolreunion.board.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;


    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Page<BoardResponse> search(BoardSearchCondition condition, Pageable pageable) {
        return boardRepository.search(condition, pageable).map(this::boardToBoardResponse);
    }

    public BoardResponse getOne(Long id) {
        return boardToBoardResponse(boardRepository.findById(id).orElseThrow());
    }

    @Transactional
    public BoardResponse post(BoardRequest boardRequest) {
        Board board = boardRequestToBoard(boardRequest);
        board = boardRepository.save(board);
        return boardToBoardResponse(board);
    }

    @Transactional
    public BoardResponse update(BoardUpdateRequest boardUpdateRequest, Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.update(boardUpdateRequest.getTitle(), boardUpdateRequest.getContent());
        return boardToBoardResponse(board);
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    private Board boardRequestToBoard(BoardRequest dto) {
        return Board.builder()
                .reunionId(dto.getReunionId())
                .memberId(dto.getWriterId())
                .memberName(dto.getWriterName())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    private BoardResponse boardToBoardResponse(Board board) {
        return new BoardResponse(
                board.getMemberName(),
                board.getMemberId(),
                board.getReunionId(),
                board.getId(),
                board.getTitle(),
                board.getContent());
    }

    @Transactional
    @RabbitListener(queues = "board.delete")
    public String deleteBoardsByMemberId(Long memberId, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            boardRepository.deleteAllByMemberId(memberId);
            log.info("Delete boards by member id: {}", memberId);
            return "OK";
        } catch (Exception e) {
            log.error("Error deleting boards by member id: {}, message: {}", memberId, e.getMessage(), e);
            return "fail";
        }
    }
}