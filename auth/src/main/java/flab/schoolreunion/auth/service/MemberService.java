package flab.schoolreunion.auth.service;

import flab.schoolreunion.auth.dto.MemberDeleteResponse;
import flab.schoolreunion.auth.entity.Member;
import flab.schoolreunion.auth.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final RabbitTemplate rabbitTemplate;

    public MemberService(MemberRepository memberRepository, RabbitTemplate rabbitTemplate) {
        this.memberRepository = memberRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public MemberDeleteResponse deleteMember(Long memberId) {

        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.delete();

        return sendMessageWithRetry(member.getId());
    }

    private MemberDeleteResponse sendMessageWithRetry(Long memberId) {
        int retryCount = 0;
        String response = "";

        // 3번 재시도
        while (retryCount < 3) {
            log.info("Sending message to RabbitMQ, attempt {}", retryCount + 1);

            response = (String) rabbitTemplate
                    .convertSendAndReceive("board.exchange", "member-id." + memberId, memberId);

            if ("OK".equals(response)) {
                log.info("Successfully received response for member id: {}", memberId);
                break;
            }

            log.warn("Timeout waiting for response, retrying... attempt {}", retryCount + 1);
            // 전달 실패한 message 삭제
            deleteMessage(memberId);
            retryCount++;
        }

        if (retryCount == 3) {
            compensateMember(memberId);
            return new MemberDeleteResponse(false, "fail delete member", memberId);
        }

        return new MemberDeleteResponse(true, "success delete member", memberId);
    }

    private void deleteMessage(Long memberId) {
        rabbitTemplate.setRoutingKey("member-id." + memberId);
        rabbitTemplate.receive("board.delete");
    }

    // 보상 트랜잭션
    private void compensateMember(Long memberId) {
        log.info("compensate member id: {}", memberId);
        Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
        member.undelete();
    }
}