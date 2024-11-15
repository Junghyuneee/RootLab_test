// 비밀번호 설정 초기값 설정 (모두 체크된 상태)
let setting = {
    "number": true, //숫자 포함 여부
    "symbol": true, //특수문자 포함 여부
    "capital": true, //대문자 포함 여부
    "small": true, //소문자 포함 여부
    "removeSimilar": true, //유사 문자 제거 여부
    "autoCopy": true, //비밀번호 자동 복사 여부
    "length": 20 // 비밀번호 길이
};

//비밀번호를 표시할 HTML 요소 선택
const pwoutput = document.getElementById("password");
let chars; //비밀번호에 사용할 문자들을 저장할 변수

//초기화 함수
function init() {
	//설정을 로컬 스토리지에서 불러옴(만약 있다면)
	//사용자의 이전 선택이 반영
    //const storage = localStorage.getItem("pw-setting");
	
	//로컬 스토리지의 설정을 'setting' 객체로 업데이트
	//사용자가 체크한 데이터를 객체로 업데이트
    //if(storage) {
    //    setting = JSON.parse(storage) //JSON 문자열을 자바스크입트 객체로 변환
    //};
	
	// 설정에 따라 체크박스 상태 초기화
	// 배열 리터럴
	// 로컬 스토리지 설정이 있더라도 모든 체크박스를 강제로 체크
    [...document.querySelectorAll("input[type=checkbox]")].forEach(input => {
        input.checked = setting[input.id]
		setting[input.id] = input.checked; //설정이 없는 경우 체크된 상태로 설정
    });
	
	//비밀번호 길이 입력 필드 초기화
    document.getElementById("pwlength").value = setting.length;
	
	//문자 생성 함수 호출하여 'chars' 변수 업데이트
	//사용자가 체크한 데이터를 객체로 업데이트
    //updateChars()
}

//비밀번호에 사용할 문자들을 생성하는 함수
function createChars() {
    let tmp = "";
	
	//설정에 따라 숫자, 특수문자, 대문자, 소문자를 추가
    if (setting.number) {
        tmp = tmp + "0123456789"
    }
    if (setting.symbol) {
        tmp = tmp + "!@#$%^&*()-_=+"
    }
    if (setting.capital) {
        tmp = tmp + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    }
    if (setting.small) {
        tmp = tmp + "abcdefghijklmnopqrstuvwxyz"
    }
	//설정에 따라 유사 문자를 제거(0, 1, o, i, 등)
	//정규표현식
	// /.../: 슬래시로 둘러싼 부분은 정규 표현식의 범위를 나타냄
	//| : '또는'을 의미
	//01 : 0 또는 1
	//g: 'global'을 의미, 문자열 전체에서 해당 패턴과 일치하는 모든 부분을 찾음. 만약 g가 없다면 첫번째 일치 항목만 찾음
    if (setting.removeSimilar) {
        tmp = tmp.replace(/01|i|I|O/g, "")
    }

    return tmp; //사용가능한 문자들을 반환
}

//char 변수에 사용가능한 물자들을 업데이트하는 함수
function updateChars() {
    chars = createChars()
}

//설정을 로컬 스토리지에 저장하는 함수
function saveSetting() {
    localStorage.setItem("pw-setting", JSON.stringify(setting)) //자바스크립트 객체(setting)를 JSON 문자열로 반환
}

//초기화 함수 호출
init(),

//비밀번호 생성 버튼의 클릭 이벤트 리스너
document.getElementById("psbtn").addEventListener("click", () => {
	
	//updqteChars 함수를 호출하여 chars가 항상 초기화되도록 보장
	updateChars();
	
	//chars가 유효한지 확인
	if(!chars || chars.length === 0){
		alert("문자열이 초기화되지 않았습니다. 설정을 확인하세요.");
		return; //오류 발생 시 함수 중단
	}
	
	const length = chars.length;
	    let password = "";
		
	//설정된 길이에 따라 랜덤하게 문자를 선택해 비밀번호 생성
    for (let i = 0; i < setting.length; i++) {
        const random = Math.floor(Math.random() * length);
        const tmp = chars.charAt(random);
        password = password + tmp
    }

	//생성된 비밀번호를 HTMl 요소에 표시
    pwoutput.innerText = password;
	
	//생성된 비밀번호 암호화(솔팅 전)
	const encryptedGeneratedPasswordPlain = encryptPasswordPlain(password);
	document.getElementById("encryptedGeneratedPasswordPlain").textContent = encryptedGeneratedPasswordPlain;
	
	//생성된 비밀번호 암호화(솔팅 후)
	const encyrptedGeneratedPasswordSalted = encryptPassword(password);
	document.getElementById("encryptedGeneratedPasswordSalted").textContent = encyrptedGeneratedPasswordSalted;
	
	//자동 복사 설정이 활성화되어 있으면 비밀번호를 자동으로 복사
    if (setting.autoCopy) {
        const select = document.createRange()
        select.selectNode(pwoutput),
        window.getSelection().removeAllRanges(),
        window.getSelection().addRange(select),
        document.execCommand("copy");
    }
});

//설정 변경 시 체크박스 상태 업데이트 및 저장하는 이벤트 리스너
[...document.querySelectorAll(".settings")].forEach(a => {
    a.addEventListener("click", () => {
        const target = a.querySelector("input");
		
		//체크박스 상태를 setting 객체에 반영
        setting[target.id] = target.checked;
        saveSetting(); //로컬 스토리지에 저장
        updateChars(); //chars 업데이트
    })
}),

//비밀번호 길이 변경 시 업데이트 및 저장하는 이벤트 리스너
document.getElementById("pwlength").addEventListener("change", function() {
    setting.length = this.value;
    saveSetting();
});

//비밀번호 암호화 함수(솔팅 전)
function encryptPasswordPlain(password){
	return CryptoJS.SHA256(password).toString(CryptoJS.enc.Base64);
}

//비밀번호 암호화 함수(솔팅 후)
function encryptPassword(password){
	const salt = CryptoJS.lib.WordArray.random(128 / 8).toString(CryptoJS.enc.Base64);
	const saltedPassword = password + salt;
	return CryptoJS.SHA256(saltedPassword).toString(CryptoJS.enc.BAse64);
}

// 비밀번호 유효성 검증 함수
function validatePassword(){
	const userPassword = document.getElementById("userPassword").value; // 입력된 비밀번호
	const generatedPassword = pwoutput.innerText; // 생성된 비밀번호
	
	//비밀번호가 입력되지 않았다면 유효성 검증을 수행하지 않고 경고 메시지 표시
	if(!userPassword){
		alert("비밀번호를 입력해주세요.");
		return; //함수 종료
	}
	
	//입력된 비밀번호와 생성된 비밀번호가 일치하는지 확인
	if(userPassword === generatedPassword){
		alert("비밀번호가 일치합니다!"); //일치할 때 알림
	} else{
		alert("비밀번호가 일치하지 않습니다."); //일치하지 않을 때 알림
	}
	
	//솔팅 전후 암호화된 비밀전호 표시
	document.getElementById("encryptedUserPasswordPlain").textContent = encryptPasswordPlain(userPassword);
	document.getElementById("encryptedUserPasswordSalted").textContent = encryptPassword(userPassword);
}

// 유효성 검증 버튼에 이벤트 리스너 추가
document.getElementById("validateBtn").addEventListener("click", validatePassword);