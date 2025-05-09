// 댓글 삭제
// 삭제 버튼 클릭 시 data-rno 가져오기

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

const relpyListElement = document.querySelector(".replyList");
const replyForm = document.querySelector("#replyForm");

const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    console.log(res.data);

    const data = res.data;
    console.log("댓글 수", data.length);

    // 댓글갯수수정
    relpyListElement.previousElementSibling.querySelector("span").innerHTML = data.length;

    let result = "";
    data.forEach((reply) => {
      result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno}>`;
      result += `<div class="p-3">`;
      result += `<img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px"/></div>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div>${reply.replyer}</div>`;
      result += `<div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;
      result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
      result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      result += `</div></div>`;
    });

    relpyListElement.innerHTML = result;
  });
};

document.querySelector(".replyList").addEventListener("click", (e) => {
  // 어느 버튼의 이벤트인가
  console.log(e.target);
  const btn = e.target;

  // rno 가져오기
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log(rno);

  //삭제버튼 or 수정버튼

  if (btn.classList.contains("btn-outline-danger")) {
    //삭제

    if (!confirm("정말로 삭제하시겠습니까?")) {
      return;
    }

    axios.delete(`/replies/${rno}`).then((res) => {
      console.log(res.data);

      // 댓글 다시 불러오기
      replyList();
      // 댓글개수 수정
    });
  } else if (btn.classList.contains("btn-outline-success")) {
    //수정
    axios.get(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      const data = res.data;
      // replyForm 안에 보여주기
      replyForm.rno.value = data.rno;
      replyForm.replyer.value = data.replyer;
      replyForm.text.value = data.text;
    });
  }
});
replyForm.addEventListener("submit", (e) => {
  e.preventDefault();

  const form = e.target;
  const rno = form.rno.value;

  if (rno) {
    //수정
    axios
      .put(`/replies/${rno}`, form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert("댓글 수정완료");
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";
        replyList();
      });
  } else {
    //삽입
    axios
      .post(`/replies/new`, form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert("댓글 작성완료");
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";
        replyList();
      });
  }
});

// 페이지 로드시
replyList();
