package flab.schoolreunion.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDeleteResponse {
    private boolean success;
    private String message;
    private Long memberId;
}
