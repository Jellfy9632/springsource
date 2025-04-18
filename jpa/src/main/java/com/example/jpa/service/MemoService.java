package com.example.jpa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.jpa.dto.MemoDTO;
import com.example.jpa.entity.Memo;
import com.example.jpa.repository.MemoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemoService {
    // Repository 메소드 호출한 후 결과
    private final MemoRepository memoRepository;

    public List<MemoDTO> getList() {

        List<Memo> list = memoRepository.findAll();
        // Memo => MemoDTO 옮기기
        // List<MemoDTO> memos = new ArrayList<>();
        // for (Memo memo : list) {
        // MemoDTO dto = MemoDTO.builder()
        // .mno(memo.getMno())
        // .memoText(memo.getMemoText())
        // .build();
        // memos.add(dto);
        // }

        // list.stream().forEach(memo -> System.out.println(memo));

        List<MemoDTO> memos = list.stream().map(memo -> entityToDto(memo)).collect(Collectors.toList());

        return memos;
    }

    public MemoDTO getRow(Long mno) {
        Memo memo = memoRepository.findById(mno).orElseThrow(EntityNotFoundException::new);
        // entity => dto
        MemoDTO dto = entityToDto(memo);
        return dto;

    }

    public Long memoUpdate(MemoDTO dto) {
        Memo memo = memoRepository.findById(dto.getMno()).orElseThrow(EntityNotFoundException::new);
        memo.changeMemoText(dto.getMemoText());

        // update 실행 => 수정된 Memo return
        memo = memoRepository.save(memo);
        return memo.getMno();
    }

    private MemoDTO entityToDto(Memo memo) {

        // builder 패턴을 사용하는 방식
        MemoDTO dto = MemoDTO.builder()
                .mno(memo.getMno())
                .memoText(memo.getMemoText())
                .createdDate(memo.getCreatedDate())
                .updatedDate(memo.getUpdatedDate())
                .build();

        // new를 사용하는 방식
        // MemoDTO dto = new MemoDTO();
        // dto.setMno(memo.getMno());
        // dto.setMemoText(memo.getMemoText());
        return dto;

    }

    private Memo dtoToEntity(MemoDTO memoDTO) {

        Memo memo = Memo.builder()
                .mno(memoDTO.getMno())
                .memoText(memoDTO.getMemoText())
                .build();
        return memo;

    }

    public void memoDelete(Long mno) {
        memoRepository.deleteById(mno);
    }

    public Long memoCreate(MemoDTO dto) {
        // 새로 입력할때는 memo는 MemoDTO에 저장
        Memo memo = dtoToEntity(dto);

        memo = memoRepository.save(memo);
        return memo.getMno();
        // MemoDTO => Memo 변환

    }
}
