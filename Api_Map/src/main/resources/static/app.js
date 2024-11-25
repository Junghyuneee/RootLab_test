
// 지도 생성
const mapContainer = document.getElementById('map');
const mapOption = {
    center: new kakao.maps.LatLng(35.8535979, 128.5797492), // 루트랩 좌표 
    level: 2 // 지도 확대 레벨
};
const map = new kakao.maps.Map(mapContainer, mapOption);


// 지도에 마커를 생성하고 표시한다
const marker = new kakao.maps.Marker({
    position: new kakao.maps.LatLng(35.8535979, 128.5797492), // 마커 루트랩 좌표
    map: map // 마커를 표시할 지도 객체
});
		
// 마커에 클릭 이벤트를 등록한다
kakao.maps.event.addListener(marker, 'click', function() {
	const newPosition = new kakao.maps.LatLng(35.8569029, 128.4615145); // 집 좌표 
	map.setCenter(newPosition); // 맵 이동
	map.setLevel(8); // 지도 확대
	
	marker.setPosition(newPosition); // 마커 이동
    alert('마커를 클릭했습니다! 지도가 새로운 위치로 이동합니다.');
});
		
// daegu.json 비동기 데이터
fetch('./daegu.json')
	.then(response => response.json()) //비동기 요청
	.then(daeguGeoJSON => { // 성공 시 JSON 데이터 파싱하여 daeguGeoJSON으로 전달
	    // GeoJSON의 다각형 좌표 추출 및 폴리곤 생성
	    const polygons = [];
	    const coordinates = daeguGeoJSON.geometry.coordinates;
		
		// 반복문 forEach
	    coordinates.forEach(multiPolygon => {
	        multiPolygon.forEach(polygon => {
	            const path = polygon.map(coord => new kakao.maps.LatLng(coord[1], coord[0])); // 좌표 변환
	
	            const kakaoPolygon = new kakao.maps.Polygon({
	                path: path,
	                strokeWeight: 2, // 선 두께
	                strokeColor: '#004c80', // 선 색깔
	                strokeOpacity: 0.8, // 선 투명도
	                fillColor: '#0099FF', // 채움 색깔
	                fillOpacity: 0.5 // 채움 투명도
	            });
	
	            kakaoPolygon.setMap(map); // 폴리곤을 지도에 추가
	            polygons.push(kakaoPolygon); // 배열에 저장
	        });
	    });
	
	    // 폴리곤 클릭 이벤트
	    polygons.forEach(polygon => {
	        kakao.maps.event.addListener(polygon, 'click', function () {
	            alert('클릭한 영역은 ' + daeguGeoJSON.properties.adm_nm + '입니다.');
	        });
	    });
	})
    .catch(error => console.error('Error loading JSON:', error));
