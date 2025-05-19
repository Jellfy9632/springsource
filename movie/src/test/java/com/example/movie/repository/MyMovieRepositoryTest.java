package com.example.movie.repository;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.movie.entity.Member;
import com.example.movie.entity.MemberRole;
import com.example.movie.entity.Movie;
import com.example.movie.entity.MovieImage;
import com.example.movie.entity.Review;

@SpringBootTest
public class MyMovieRepositoryTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReviewRepository reviewRepository;

    // 영화 삽입
    @Test
    public void insertMovie() {
        IntStream.rangeClosed(0, 100).forEach(i -> {
            Movie movie = Movie.builder()
                    .title("Movie" + i)
                    .build();
            movieRepository.save(movie);

            // 임의의 이미지
            int count = (int) (Math.random() * 5) + 1;
            for (int j = 0; j < count; j++) {
                MovieImage movieImage = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .ord(j)
                        .imgName("test" + j + ".jpg")
                        .movie(movie)
                        .build();
                // movie.addImage(movieImage);
                movieImageRepository.save(movieImage);
            }
        });
    }

    @Test
    public void memberInsertTest() {
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password(passwordEncoder.encode("1111"))
                    .memberRole(MemberRole.MEMBER)
                    .nickname("viewer" + i)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    public void reviewInsertTest() {
        // 리뷰 200개 / 영화 100 무작위로 추출 / 멤버 무작위 추출

        IntStream.rangeClosed(1, 200).forEach(i -> {

            // 무작위 변수 생성
            long midRan = ((int) (Math.random() * 20) + 1);
            long mnoRan = ((int) (Math.random() * 100) + 1);

            // 멤버 무작위 추출
            Review review = Review.builder()
                    .grade((int) (Math.random() * 5) + 1)
                    .text("Movie Review Text" + i)
                    .member(Member.builder().mid(midRan).build())
                    .movie(Movie.builder().mno(mnoRan).build())
                    .build();
            reviewRepository.save(review);

        });
    }

    @Test
    public void listTest() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Object[]> result = movieImageRepository.getTotalList(null, null, pageable);
        for (Object[] objects : result) {
            // [Movie(mno=100, title=Movie99), MovieImage(inum=314,
            // uuid=75bb9df4-929a-4cef-9028-0abc15fc0faa, imgName=test0.jpg, path=null,
            // ord=0), 1, 2.0]
            System.out.println(Arrays.toString(objects));
        }

    }
}
