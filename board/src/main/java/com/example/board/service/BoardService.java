package com.example.board.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.PageRequestDTO;
import com.example.board.dto.PageResultDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class BoardService {

        private final BoardRepository boardRepository;
        private final ReplyRepository replyRepository;

        public Long create(BoardDTO dto) {
                // dto => entity 변경
                Board board = dtoToEntity(dto);
                // 저장
                Board newBoard = boardRepository.save(board);
                return newBoard.getBno();
        }

        @Transactional // Reply, BoardTBL 두개의 테이블 접근 => 한꺼번에 처리
        public void delete(Long bno) {
                // 연관관계 데이터 정리 => 댓글
                // SQL : 댓글 선 삭제 후 게시글 삭제 or 댓글 부모를 null로 변경 후 삭제

                // 댓글 삭제: 1) bno로 댓글 찾기 2) 삭제

                replyRepository.deleteByBoardBno(bno);
                boardRepository.deleteById(bno);
        }

        // 수정
        public Long update(BoardDTO dto) {
                // 수정할 대상 찾기
                Board board = boardRepository.findById(dto.getBno()).orElseThrow();
                // 내용 업데이트
                board.changeTitle(dto.getTitle());
                board.changeContent(dto.getContent());
                // 저장
                boardRepository.save(board);

                return board.getBno();
        }

        public BoardDTO getRow(Long bno) {
                Object[] entity = boardRepository.getBoardByBno(bno);

                // [Board(bno=99, title=board_title99, content=board_content99),
                // Member(email=user9@gmail.com, password=1111, name=USER9), 3]
                return entityToDto((Board) entity[0], (Member) entity[1],
                                (Long) entity[2]);
        }

        public PageResultDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {
                Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                                Sort.by("bno").descending());
                Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(),
                                pageable);
                // Function<T, R> T=>R로 변환
                Function<Object[], BoardDTO> fn = (entity -> entityToDto((Board) entity[0], (Member) entity[1],
                                (Long) entity[2]));
                List<BoardDTO> dtoList = result.stream().map(fn).collect(Collectors.toList());
                Long totalCount = result.getTotalElements();

                PageResultDTO<BoardDTO> pageResultDTO = PageResultDTO.<BoardDTO>withAll()
                                .dtoList(dtoList)
                                .totalCount(totalCount)
                                .pageRequestDTO(pageRequestDTO)
                                .build();
                return pageResultDTO;
        }

        private BoardDTO entityToDto(Board board, Member member, Long replyCount) {
                BoardDTO dto = BoardDTO.builder()
                                .bno(board.getBno())
                                .title(board.getTitle())
                                .content(board.getContent())
                                .createdDate(board.getCreatedDate())
                                .email(member.getEmail())
                                .name(member.getName())
                                .replyCount(replyCount)
                                .build();
                return dto;
        }

        private Board dtoToEntity(BoardDTO dto) {
                Board board = Board.builder()
                                .bno(dto.getBno())
                                .title(dto.getTitle())
                                .content(dto.getContent())
                                .member(Member.builder().email(dto.getEmail()).build())
                                .build();
                return board;
        }

}
