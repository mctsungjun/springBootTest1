package com.myjob.members.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.myjob.members.dto.MemberDto;
import com.myjob.members.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {

    //생성자 주입
    private final MemberService memberService;
    //회원가입 페이지 출력 요청

    @GetMapping("/member/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDto dto){
        System.out.println("memberDto:" +dto);
        memberService.save(dto);
        return "login";
    }
    @GetMapping("/member/login")
    public String loginFrom(){
        return "login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDto dto ,HttpSession session){
        System.out.println("memberDto: " +dto);
        MemberDto loginResult = memberService.login(dto);
        if(loginResult !=null){
            //login 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail());
            return "main";
        }else{
            //login 실패
            return "login";
        }
        

    }

    @GetMapping("/member/")
    public String findAll(Model model){
        List<MemberDto> meberDtoList = memberService.findAll();
        // 어떠한 html로 가져갈 데이터가 있다면 model사용
        model.addAttribute("memberList", meberDtoList);
        return "list";
        
    }
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model){
        MemberDto memberDto = memberService.findById(id);
        model.addAttribute("member", memberDto);
        return "detail";
    }

    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        String myEmail = (String)session.getAttribute("loginEmail");
        MemberDto memberDto = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDto);
        return "update";
    }

    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDto memberDto){
        memberService.update(memberDto);
        return "redirect:/member/" +memberDto.getId();
    }

    @GetMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable Long id){
        memberService.deleteMember(id);
        return "redirect:/member/";
    }
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "index";
    }
}
