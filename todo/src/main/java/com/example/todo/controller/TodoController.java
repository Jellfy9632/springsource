package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/list")
    public void getList(@RequestParam(defaultValue = "0") boolean completed, Model model) {
        log.info("미완료 todo 가져오기{}", completed);
        List<ToDoDTO> todos = todoService.list(completed);
        model.addAttribute("todos", todos);
        // 어떤 목록을 보여줄 것인가
        model.addAttribute("completed", completed);
    }

    @GetMapping("/read")
    public void getRead(Long id, Model model) {
        log.info("조회 {}", id);

        ToDoDTO dto = todoService.read(id);
        model.addAttribute("dto", dto);
    }

    @PostMapping("/modify")
    public String postCompleted(ToDoDTO dto, RedirectAttributes rttr) {
        log.info("수정{}", dto);
        Long id = todoService.changeCompleted(dto);

        rttr.addAttribute("id", id);
        return "redirect:/todo/read";
    }

    @GetMapping("/remove")
    public String getRemove(Long id) {
        todoService.remove(id);
        return "redirect:/todo/list";
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("todo 작성 폼 요청");

    }

    @PostMapping("/create")
    public String postCreate(ToDoDTO dto, RedirectAttributes rttr) {
        log.info("todo 입력 {} ", dto);

        Long id = todoService.create(dto);

        // read 로 이동

        rttr.addAttribute("id", id);
        return "redirect:/todo/read";

    }
}
