package com.example.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.dto.ReplyDTO;
import com.example.board.entity.Reply;
import com.example.board.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Log4j2
@RestController
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/new")
    public Long postReply(@RequestBody ReplyDTO dto) {
        log.info("rno 요청{}", dto);
        return replyService.create(dto);
    }

    @GetMapping("/board/{bno}")
    public List<ReplyDTO> list(@PathVariable Long bno) {
        log.info("bno 댓글요청{}", bno);
        return replyService.getList(bno);
    }

    @GetMapping("/{rno}")
    public ReplyDTO getReply(@PathVariable Long rno) {
        log.info("rno 요청{}", rno);
        return replyService.get(rno);
    }

    @PutMapping("/{rno}")
    public Long putReply(@PathVariable Long rno, @RequestBody ReplyDTO dto) {
        log.info("rno 요청{}", rno);
        return replyService.update(dto);

    }

    @DeleteMapping("/{rno}")

    public Long deleteReply(@PathVariable Long rno) {
        log.info("rno 요청{}", rno);
        replyService.delete(rno);
        return rno;
    }
}
