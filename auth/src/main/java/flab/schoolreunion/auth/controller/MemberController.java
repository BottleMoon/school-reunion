package flab.schoolreunion.auth.controller;

import flab.schoolreunion.auth.dto.MemberDeleteResponse;
import flab.schoolreunion.auth.service.MemberService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @DeleteMapping("{id}")
    public MemberDeleteResponse deleteMember(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }
}
