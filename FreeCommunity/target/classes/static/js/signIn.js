localStorage.setItem("confirmed", false);

// 이름 유효성 검사
function validateName() {
  const name = document.getElementById("name").value.trim();
  const errorName = document.getElementById("errorName");

  if (name === "") {
    errorName.innerHTML = "<p class='fieldError'>닉네임을 입력하세요</p>";
    return false;
  }

  errorName.innerHTML = "";
  return true;
}

// 이메일 유효성 검사
function validateEmail() {
  const emailInput = document.getElementById("emailInput").value.trim();
  const emailSelect = document.getElementById("emailSelect").value.trim();
  const email = emailInput + "@" + emailSelect;
  const errorEmail = document.getElementById("errorEmail");
  if (emailInput.length < 1) {
    errorEmail.innerHTML = "<p class='fieldError'>이메일을 입력하세요</p>";
    return false;
  }
  const regex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
  if (!regex.test(email) || emailInput.length < 3) {
    errorEmail.innerHTML =
      "<p class='fieldError'>이메일 형식이 올바르지 않습니다</p>";
    return false;
  }
  errorEmail.innerHTML = "";
  return true;
}

// 비밀번호 유효성 검사
function validatePassword() {
  const password = document.getElementById("password").value.trim();
  const errorPassword = document.getElementById("errorPassword");
  if (password === "") {
    errorPassword.innerHTML = "<p class='fieldError'>비밀번호를 입력하세요</p>";
    return false;
  }
  if (password.length < 8) {
    errorPassword.innerHTML =
      "<p class='fieldError'>비밀번호는 8자 이상이어야 합니다</p>";
    return false;
  }
  if (password.length > 16) {
    errorPassword.innerHTML =
      "<p class='fieldError'>비밀번호는 16자 이하이어야 합니다</p>";
    return false;
  }
  errorPassword.innerHTML = "";
  return true;
}

// 비밀번호 확인 유효성 검사
function validatePasswordCheck() {
  const password = document.getElementById("password").value.trim();
  const passwordCheck = document.getElementById("passwordCheck").value.trim();
  const errorPasswordCheck = document.getElementById("errorPasswordCheck");
  if (passwordCheck === "") {
    errorPasswordCheck.innerHTML =
      "<p class='fieldError'>비밀번호 확인을 입력하세요</p>";
    return false;
  }
  if (password !== passwordCheck) {
    errorPasswordCheck.innerHTML =
      "<p class='fieldError'>비밀번호가 일치하지 않습니다</p>";
    return false;
  }
  errorPasswordCheck.innerHTML = "";
  return true;
}

// 모든 유효성 검사
function validateAll() {
  const confirmed = sessionStorage.getItem("confirmed");
  const result =
    validateName() &&
    validateEmail() &&
    validatePassword() &&
    validatePasswordCheck() &&
    confirmed;

  if (result) {
    ocument.getElementById("signIn").submit();
  }

  alert("입력을 확인해주세요");
}

// 포커스 이탈 시 유효성 검사
document.addEventListener("focusout", function (event) {
  const element = event.target;
  switch (element.id) {
    case "name":
      validateName();
      break;
    case "emailInput":
      validateEmail();
      break;
    case "password":
      validatePassword();
      break;
    case "passwordCheck":
      validatePasswordCheck();
      break;
  }
});

function sendConfirmToken() {
  if (!validateEmail()) {
    alert("이메일을 먼저 입력해주세요");
    return;
  }
  // 인증 번호 입력 폼 표시
  document.querySelector(".emailCheck").style.display = "block";
  const emailInput = document.getElementById("emailInput").value.trim();
  const emailSelect = document.getElementById("emailSelect").value.trim();
  const email = emailInput + "@" + emailSelect;
  // 정보 요청
  const formData = new FormData();
  formData.append("mail", email);

  fetch("/validateDuplicateEmail", {
    method: "POST",
    body: formData,
  }).then((response) => {
    if (response.status === 409) {
      alert("이미 가입한 이메일 입니다");
    } else if (response.status === 200) {
      alert("인증번호가 발송되었습니다");
      // 인증번호를 localStorage에 저장
      localStorage.setItem("confirmToken", response.text());
    } else {
      alert("인증번호 발송에 실패하였습니다");
    }
  });
}

function confirmEmailToken() {
  // 입력한 인증 번호
  let confirmToken1 = document.getElementById("confirmToken").value;
  // 저장된 인증 번호
  let confirmToken2 = localStorage.getItem("confirmToken");

  confirmed = confirmToken1 == confirmToken2;
  // 인증 번호 일치 여부 확인
  if (confirmed) {
    // 인증 성공 알림
    alert("인증되었습니다");
    // 인증 번호 입력 폼 숨김
    document.getElementsByClassName("emailCheck").style.display = "none";
    localStorage.setItem("confirmed", true);
    localStorage.removeItem("confirmToken");
  } else {
    // 인증 실패 알림
    alert("다시 확인해주세요");
  }
}
