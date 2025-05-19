// 등록버튼을 누르면 form submit 중지
// li 태그 정보수집 후 form에 추가
// form 전송
document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();
  // li정보수집
  const output = document.querySelectorAll(".uploadResult li");

  // 속성 : . or getAttribute()
  // data- : dataset

  let result = "";
  output.forEach((obj, idx) => {
    console.log(obj.dataset.uuid);
    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}"/>`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}"/>`;
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}"/>`;
  });

  //폼에 추가
  e.target.insertAdjacentHTML("beforeend", result);
  e.target.submit();
});
