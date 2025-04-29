package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;
import com.example.book.dto.PageRequestDTO;
import com.example.book.dto.PageResultDTO;
import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("dto") BookDTO dto, PageRequestDTO pageRequestDTO) {
        log.info("도서작성 폼 요청");

    }

    @PostMapping("/create")
    public String postCreate(@ModelAttribute("dto") @Valid BookDTO dto, BindingResult result, RedirectAttributes rttr) {
        log.info("도서작성 요청");
        if (result.hasErrors()) {
            return "/book/create";
        }
        Long code = bookService.insert(dto);
        // 서비스 호출

        // ?code=2030 => 화면단 ${param.code}
        // rttr.addAttribute(code, 2030);

        // 세션이용 (주소줄에 따라가지않음)
        // rttr.addFlashAttribute(null, rttr);

        rttr.addFlashAttribute("code", code);
        return "redirect:/book/list";
    }

    @GetMapping("/list")
    public void getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("book list 요청{}", pageRequestDTO);

        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        model.addAttribute("result", pageResultDTO);
    }

    @GetMapping({ "/read", "/modify" })

    public void getRead(Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get 요청 {}", code);
        BookDTO dto = bookService.read(code);
        model.addAttribute("dto", dto);

    }

    @PostMapping("/modify")
    public String postModify(BookDTO dto, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {
        log.info("book modify 요청 {}", dto);
        // 서비스 호출
        bookService.modify(dto);
        // read로 가기
        rttr.addAttribute("code", dto.getCode());
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/read";

    }

    @PostMapping("/remove")
    public String postRemove(Long code, PageRequestDTO pageRequestDTO, RedirectAttributes rttr) {

        log.info("book remove 요청 {}", code);
        bookService.remove(code);
        // 서비스 호출
        rttr.addAttribute("page", pageRequestDTO.getPage());
        rttr.addAttribute("size", pageRequestDTO.getSize());
        rttr.addAttribute("type", pageRequestDTO.getType());
        rttr.addAttribute("keyword", pageRequestDTO.getKeyword());
        return "redirect:/book/list";
    }

}
