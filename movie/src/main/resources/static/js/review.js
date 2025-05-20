// 날짜 포맷함수
const formatDate = (str) => {
  const date = new Date(str);

  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

// 리뷰 영역 가져오기
const reviewDiv = document.querySelector(".reviewList");
const reviewForm = document.querySelector("#reviewForm");
const reviewCnt = document.querySelector(".review-cnt");

const reviewList = () => {
  // 리뷰 가져오기
  axios.get(`/reviews/${mno}/all`).then((res) => {
    console.log(res.data);

    const data = res.data;
    reviewCnt.innerHTML = data.length;

    let result = "";
    data.forEach((review) => {
      result += `<div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno=${review.rno} data-email=${review.email}>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div><span class = "font-smibold">${review.text}</span></div>`;
      result += `<div class="small text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
      result += `평점 : <span class = "grade">${review.grade}</span><div class = "starrr"></div></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(review.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;

      if (loginUser == review.email) {
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }
      result += `</div></div>`;
    });
    reviewDiv.innerHTML = result;
  });
};
reviewList();

// 리뷰 삭제 및 수정

reviewDiv.addEventListener("click", (e) => {
  const btn = e.target;
  // rno 가져오기
  const rno = btn.closest(".review-row").dataset.rno;
  console.log(rno);
  // 리뷰 작성자 가져오기
  const email = btn.closest(".review-row").dataset.email;

  //삭제버튼 or 수정버튼

  if (btn.classList.contains("btn-outline-danger")) {
    //삭제

    if (!confirm("정말로 삭제하시겠습니까?")) {
      return;
    }
    axios
      .delete(`/reviews/${mno}/${rno}`, {
        //data: { email: email },
        headers: {
          //"Content-Type": "application/json",
          "X-CSRF-TOKEN": csrf,
        },
      })
      .then((res) => {
        console.log(res.data);
        // 리뷰 다시 불러오기
        reviewList();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    //수정
    // 리뷰 하나 가져오기
    axios.get(`/reviews/${mno}/${rno}`).then((res) => {
      console.log(res.data);
      const data = res.data;
      // reviewForm 안에 보여주기
      reviewForm.rno.value = data.rno;
      reviewForm.nickname.value = data.nickname;
      // 멤버 아이디
      reviewForm.mid.value = data.mid;
      reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
      reviewForm.text.value = data.text;
    });
  }
});

if (reviewForm) {
  reviewForm.addEventListener("submit", (e) => {
    e.preventDefault();

    const form = e.target;
    const rno = form.rno.value;

    form.grade.value = grade;

    if (rno) {
      //수정
      axios
        .put(`/reviews/${mno}/${rno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data);
          alert("리뷰 수정완료");
          // form 기존내용 지우기
          reviewForm.rno.value = "";
          reviewForm.mid.value = "";
          reviewForm.nickname.value = "";
          reviewForm.text.value = "";
          reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

          reviewList();
        });
    } else {
      //삽입
      axios
        .post(`/reviews/${mno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data);
          alert("댓글 작성완료");
          // form 기존내용 지우기
          reviewForm.rno.value = "";
          reviewForm.text.value = "";
          reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();
          reviewList();
        });
    }
  });
}

reviewList();
