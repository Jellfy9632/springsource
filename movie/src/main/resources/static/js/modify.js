// 삭제 클릭 시 removeForm 전송
document.querySelector(".move").addEventListener("click", (e) => {
  // a 태그 기능 중지
  e.preventDefault();

  document.querySelector("#removeForm").submit();
});
