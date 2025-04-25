// 삭제버튼이 클릭이 되면 actionForm submit

document.querySelector(".btn-danger").addEventListener("click", () => {
  //actionForm 찾기

  const actionForm = document.querySelector("#actionForm");
  actionForm.submit();
});
