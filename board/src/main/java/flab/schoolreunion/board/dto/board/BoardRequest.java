package flab.schoolreunion.board.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequest {
    private Long writerId;
    private String writerName;
    private Long reunionId;
    private String title;
    private String content;
}
