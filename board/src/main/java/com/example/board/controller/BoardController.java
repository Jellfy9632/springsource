package com.example.board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.service.BoardService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequestMapping("/board")
@Log4j2
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") BoardDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("글 작성 폼 요청");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String postCreate(@ModelAttribute("dto") @Valid BoardDTO dto, BindingResult result,
            PageRequestDTO pageRequestDTO,
            RedirectAttributes rttr) {
        log.info("글 작성 요청 {}", dto);

        // 유효성 검증후 결과 확인
        if (result.hasErrors()) {
            return "/board/create";

        }
        boardService.create(dto);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/board/list";
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/list")
    public void getList(Model model, PageRequestDTO pageRequestDTO) {
        log.info("List 요청{}", pageRequestDTO);

        PageResultDTO<BoardDTO> result = boardService.getList(pageRequestDTO);
        model.addAttribute("result", result);
    }

    @GetMapping({ "/read", "/modify" })
    public void getRead(Long bno, PageRequestDTO pageRequestDTO, Model model) {
        log.info("get {}", bno);

        BoardDTO dto = boardService.getRow(bno);
        model.addAttribute("dto", dto);
    }

    @PreAuthorize("authentication.name == #dto.email")
    @PostMapping("/modify")
    public String postModify(BoardDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("수정{}{}", dto, pageRequestDTO);

        Long bno = boardService.update(dto);
        // 수정 완료 후 read로 이동
        rttr.addAttribute("bno", bno);
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/board/read";
    }

    // 로그인 사용자 == 작성자 여부 확인
    @PreAuthorize("authentication.name == #email")
    @PostMapping({ "/remove" })
    public String getRemove(Long bno, String email, PageRequestDTO pageRequestDTO, Model model,
            RedirectAttributes rttr) {
        log.info("remove {}", bno);

        boardService.delete(bno);

        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());

        return "redirect:/board/list";

    }

}
