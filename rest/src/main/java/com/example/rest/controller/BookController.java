package com.example.rest.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.rest.dto.BookDTO;
import com.example.rest.dto.PageRequestDTO;
import com.example.rest.dto.PageResultDTO;
import com.example.rest.service.BookService;

import jakarta.validation.Valid;

// 일반 컨트롤러 + REST
// 데이터만 내보내기 : ResponseEntity or @ResponseBody

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @ResponseBody
    @PostMapping("/create")
    public Long postCreate(@RequestBody BookDTO dto) {
        log.info("도서작성 요청");
        Long code = bookService.insert(dto);
        return code;
    }

    @GetMapping("/list")
    public ResponseEntity<PageResultDTO<BookDTO>> getList(PageRequestDTO pageRequestDTO, Model model) {
        log.info("book list 요청{}", pageRequestDTO);

        PageResultDTO<BookDTO> pageResultDTO = bookService.readAll(pageRequestDTO);
        return new ResponseEntity<>(pageResultDTO, HttpStatus.OK);
    }

    // @ResponseBody
    // @GetMapping({ "/read", "/modify" })
    // public BookDTO getRead(Long code, PageRequestDTO pageRequestDTO, Model model)
    // {
    // log.info("book get 요청 {}", code);
    // BookDTO dto = bookService.read(code);
    // return dto;

    // }

    @ResponseBody
    @GetMapping({ "/read/{code}", "/modify{code}" })
    public BookDTO getRead(@PathVariable Long code, PageRequestDTO pageRequestDTO, Model model) {
        log.info("book get 요청 {}", code);
        BookDTO dto = bookService.read(code);
        return dto;

    }

    @ResponseBody
    @PutMapping("/modify")
    public Long postModify(@RequestBody BookDTO dto) {
        log.info("book modify 요청 {}", dto);
        // 서비스 호출
        bookService.modify(dto);

        return dto.getCode();

    }

    @DeleteMapping("/remove/{code}")
    public ResponseEntity<Long> postRemove(@PathVariable Long code, PageRequestDTO pageRequestDTO) {

        log.info("book remove 요청 {}", code);
        bookService.remove(code);
        // 서비스 호출

        return new ResponseEntity<Long>(code, HttpStatus.OK);
    }

}
