function solution(new_id) {
    let processLog = []; // 각 단계의 결과를 저장할 배열

    // 1단계: 대문자를 소문자로 치환
    new_id = new_id.toLowerCase();
    processLog.push(`1단계: 대문자를 소문자로 치환 → ${new_id}`);

    // 2단계: 허용된 문자만 남기기 (허용되지 않은 문자 확인 및 경고 추가)
    const isAllowedChar = (char) => {
        return (
            (char >= 'a' && char <= 'z') ||
            (char >= '0' && char <= '9') ||
            char === '-' ||
            char === '_' ||
            char === '.'
        );
    };

    let filtered = ''; // 허용할 문자를 저장할 변수
    let invalidCharacters = ''; // 허용되지 않은 문자를 저장할 변수
    
    for (const char of new_id) {
        if (isAllowedChar(char)) {
            filtered += char; // 허용할 문자 저장
        } else {
            invalidCharacters += char; // 허용되지 않은 문자 저장
        }
    }

    if (invalidCharacters.length > 0) {
        alert(`허용되지 않은 문자가 포함되어 있습니다: ${invalidCharacters}`);
    }
    new_id = filtered;
    processLog.push(`2단계: 허용되지 않은 문자 제거 → ${new_id}`);

    // 3단계: 연속된 마침표를 하나로 변경
    let compressed = '';
    for (let i = 0; i < new_id.length; i++) {
        if (!(new_id[i] === '.' && new_id[i - 1] === '.')) {
            compressed += new_id[i];
        }
    }
    new_id = compressed;
    processLog.push(`3단계: 연속된 마침표를 하나로 변경 → ${new_id}`);

    // 4단계: 마침표가 처음이나 끝에 있다면 제거
    if (new_id.startsWith('.')) new_id = new_id.slice(1);
    if (new_id.endsWith('.')) new_id = new_id.slice(0, -1);
    processLog.push(`4단계: 처음과 끝의 마침표 제거 → ${new_id}`);

    // 5단계: 빈 문자열이라면 'a' 대입
    if (new_id === '') new_id = 'a';
    processLog.push(`5단계: 빈 문자열일 경우 'a' 추가 → ${new_id}`);

    // 6단계: 16자 이상이면 첫 15자만 남기고, 끝 마침표 제거
    if (new_id.length >= 16) {
        new_id = new_id.slice(0, 15);
        if (new_id.endsWith('.')) new_id = new_id.slice(0, -1);
    }
    processLog.push(`6단계: 길이 제한 (최대 15자) 및 끝 마침표 제거 → ${new_id}`);

    // 7단계: 길이가 2자 이하라면 마지막 문자를 반복해서 추가
    while (new_id.length < 3) {
        new_id += new_id[new_id.length - 1];
    }
    processLog.push(`7단계: 길이 최소화 (최소 3자) → ${new_id}`);

    return { result: new_id, log: processLog };
}

// HTML에서 버튼 클릭 시 호출하는 함수
function handleIdRecommendation() {
    const input = document.getElementById('new_id').value.trim();
    const { result, log } = solution(input);

    // 결과 출력
    let logHtml = log.map((step) => `<li>${step}</li>`).join('');
    document.getElementById('result').innerHTML = `
        <p>추천 아이디: <strong>${result}</strong></p>
        <ul>${logHtml}</ul>
    `;
}
