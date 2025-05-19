package com.example.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RequiredArgsConstructor
@Log4j2
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long insertReview(ReviewDTO reviewDTO) {
        Review newReview = dtoToEntity(reviewDTO);
        reviewRepository.save(newReview);
        return newReview.getRno();
    }

    public ReviewDTO getReview(Long rno) {
        Review review = reviewRepository.findById(rno).get();
        return entityToDTO(review);
    }

    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        // dto => entity
        Review review = reviewRepository.findById(reviewDTO.getRno()).orElseThrow();
        // 수정
        review.changeGrade(reviewDTO.getGrade());
        review.changeText(reviewDTO.getText());
        review = reviewRepository.save(review);

        return entityToDTO(review);

    }

    public void removeReply(Long rno) {
        reviewRepository.deleteById(rno);
    }

    public List<ReviewDTO> getReplies(Long mno) {

        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        List<ReviewDTO> list = result.stream().map(review -> entityToDTO(review)).collect(Collectors.toList());
        return list;

    }

    private ReviewDTO entityToDTO(Review review) {
        // Review => ReviewDTO

        ReviewDTO reviewDTO = ReviewDTO.builder()
                .rno(review.getRno())
                .grade(review.getGrade())
                .text(review.getText())
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())
                // 멤버정보
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .build();

        return reviewDTO;
    }

    private Review dtoToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .rno(reviewDTO.getRno())
                .text(reviewDTO.getText())
                .grade(reviewDTO.getGrade())
                // 멤버 정보
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                // 영화 정보
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .build();
        return review;
    }

}
