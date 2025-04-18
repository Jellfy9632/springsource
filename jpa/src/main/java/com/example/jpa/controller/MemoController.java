package com.example.jpa.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.service.MemoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {
    // 서비스 메소드 호출
    // 데이터가 전송된다면 전송된 데이터를 Model에 담기
    private final MemoService memoService;
    // 주소 설계

    // 전체 memo 조회 : /memo/list
    @GetMapping("/list")
    public void getList(Model model) {
        List<MemoDTO> list = memoService.getList();
        model.addAttribute("list", list);
    }

    // 특정 memo 조회 : /memo/read?mno=3

    @GetMapping(value = { "/read", "/update" })
    public void getRow(Long mno, Model model) {
        log.info("조회요청 {}", mno);
        MemoDTO dto = memoService.getRow(mno);
        model.addAttribute("dto", dto);

    }
    // memo 수정 : /memo/update?mno=3

    @PostMapping("/update")
    public String postUpdate(MemoDTO dto, RedirectAttributes rttr) {
        log.info("메모 수정{}", dto);

        // 수정요청
        Long mno = memoService.memoUpdate(dto);
        // 수정완료시 read 화면 이동
        rttr.addAttribute("mno", mno);
        return "redirect:/memo/read";
    }

    // memo 추가 : /memo/new
    @GetMapping("/new")
    public void getNew() {
        log.info("새 메모 작성 폼 요청");
    }
    // memo 삭제 : /memo/remove?mno=3

    @PostMapping("/new")
    public String postNew(MemoDTO dto, RedirectAttributes rttr) {
        // 사용자 입력값 가져오기
        log.info("새 메모 작성{}", dto);
        // 가져온걸로 새로만들기
        Long mno = memoService.memoCreate(dto);

        rttr.addFlashAttribute("msg", mno);
        return "redirect:/memo/list";

    }

    @GetMapping("/remove")
    public String getRemove(Long mno) {
        log.info("memo 삭제요청{}", mno);
        memoService.memoDelete(mno);
        return "redirect:/memo/list";
    }

}
