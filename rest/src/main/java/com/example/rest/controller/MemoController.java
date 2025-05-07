package com.example.rest.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.rest.dto.MemoDTO;
import com.example.rest.service.MemoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
// Rest 컨트롤러에서는 매핑방식이 늘어남
// 1) Get 2) Post 3) Put/Patch : 수정 4) Delete : 삭제
@RestController
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/list")
    public List<MemoDTO> getList(Model model) {
        List<MemoDTO> list = memoService.getList();
        return list;
    }

    @GetMapping(value = { "/read", "/update" })
    public MemoDTO getRow(Long mno, Model model) {
        log.info("조회요청 {}", mno);
        MemoDTO dto = memoService.getRow(mno);
        return dto;

    }
    // memo 수정 : /memo/update?mno=3

    @PutMapping("/update")
    public Long postUpdate(@RequestBody MemoDTO dto) {
        log.info("메모 수정{}", dto);

        // 수정요청
        Long mno = memoService.memoUpdate(dto);
        return mno;
    }

    @GetMapping("/new")
    public void getNew() {
        log.info("새 메모 작성 폼 요청");
    }

    @PostMapping("/new")
    public Long postNew(@RequestBody MemoDTO dto) {
        // 사용자 입력값 가져오기
        log.info("새 메모 작성{}", dto);
        Long mno = memoService.memoCreate(dto);

        return mno;

    }

    // memo 삭제 : /memo/remove/3
    @DeleteMapping("/remove/{mno}")
    public Long getRemove(@PathVariable Long mno) {
        log.info("memo 삭제요청{}", mno);
        memoService.memoDelete(mno);
        return mno;
    }

}
