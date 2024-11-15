$(document).ready(function () {
    // FullCalendar 초기화
    const calendarEl = document.getElementById('calendar');
    const calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth', // 월간 달력 보기
        locale: 'ko', // 한국어 설정
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events: [], // 초기 이벤트 비우기

        // 날짜 클릭 시 모달 열기
        dateClick: function(info) {
            const clickedDate = info.date;  // 클릭한 날짜 정보 가져오기

            // 클릭한 날짜를 시작일로 설정
            $("#modalStartDate").datepicker("setDate", clickedDate);
            // 종료일은 빈칸으로 설정하거나 시작일과 동일하게 설정
            $("#modalEndDate").val("");
            $("#modalEvent").val(''); // 일정 내용 초기화
            $("#eventModal").dialog("open"); // 모달 열기
        }
    });

    // 캘린더 렌더링
    calendar.render();

    // FullCalendar의 모든 날짜 셀에 커서 스타일 추가
    addCursorStyleToDates();

    // 달력 페이지를 넘기거나 변경할 때마다 커서 스타일을 갱신
    calendar.on('datesSet', function() {
        addCursorStyleToDates();  // 커서 스타일을 다시 추가
    });

    // jQuery UI Dialog 모달 초기화
    $("#eventModal").dialog({
        autoOpen: false, // 페이지 로드 시 자동으로 열리지 않도록 설정
        modal: true, // 배경 클릭 시 모달 외부 닫기 방지
        width: 400, // 모달 너비 설정
        buttons: {
            "닫기": function () {
                $(this).dialog("close"); // 닫기 버튼 클릭 시 모달 닫기
            }
        }
    });

    // 시작일과 종료일에 datepicker 추가
    $("#modalStartDate, #modalEndDate").datepicker({
        dateFormat: "yy-mm-dd", // 날짜 형식을 YYYY-MM-DD로 설정
		changeMonth: true, //월 선택을 셀렉트박스로 변경
		changeYear: true, //년도 선택을 셀렉트박스로 변경
		yearRange: "1990:2100", //년도 범위 설정
		buttonImage: "http://jqueryui.com/resources/demos/datepicker/images/calendar.gif",
		buttonImagaeOnly: true, //버튼 이미지만 표시되도록 설정
		showOn: "button" //버튼 클릭시 datepicker 열기
    });

    // "일정 추가" 버튼 클릭 시 모달 열기
    $("#addEventButton").on("click", function () {
        const today = new Date(); // 오늘 날짜로 기본값 설정
        $("#modalStartDate").datepicker("setDate", today);
        $("#modalEndDate").val(""); // 종료일은 비워 둠
        $("#modalEvent").val(''); // 일정 내용 초기화
        $("#eventModal").dialog("open"); // 모달 열기
    });

    // 등록 버튼 클릭 시 일정 추가
    $("#saveEvent").on("click", function () {
        const startDate = $("#modalStartDate").val(); // 모달에서 시작일 가져오기
        const endDate = $("#modalEndDate").val(); // 모달에서 종료일 가져오기
        const eventText = $("#modalEvent").val(); // 모달에서 일정 내용 가져오기

        // 유효성 검사: 일정 내용이 입력되지 않았을 경우 경고 메시지 출력
        if (!eventText) {
            alert("일정을 입력하세요.");
            return;
        }
        
        // 유효성 검사: 종료일이 선택되지 않았을 경우 경고 메시지 출력
        if (!endDate) {
            alert("종료일을 선택하세요.");
            return;
        }

        // FullCalendar에 이벤트 추가
        calendar.addEvent({
            title: eventText,
            start: startDate,
            end: endDate,
            allDay: true // 하루 종일 일정으로 설정
        });

        // 일정 등록 완료 메시지 표시
        alert("일정 등록이 완료되었습니다.");

        // 모달 닫기 및 입력 필드 초기화
        $("#eventModal").dialog("close");
    });

    // 날짜 셀에 커서 스타일을 추가하는 함수
    function addCursorStyleToDates() {
        $(".fc-day").css("cursor", "pointer"); // 날짜 셀에 커서 스타일 추가
    }
});
