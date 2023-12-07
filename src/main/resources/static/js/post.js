// postObject 객체 선언, let 키워드는 선언하는 키워드
let postObject =
{
	init: function()	// init: 함수는 postObject 객체가 초기화되어 처음 사용될 준비가 될 때 호출되는 함수,
	{
		let _this = this;	// let으로 _this라는 변수에 this를 대입. this는 postObject 객체.

		// #btn-save 버튼에 click 이벤트가 발생하면 insertUser() 함수 호출
		$("#btn-insert").on("click", () => {	// jQuery를 사용하여 "#btn-insert"라는 HTML 요소에 클릭 이벤트 설정
			_this.insertPost();		// 클릭되면 insertPost 함수 호출.
		});
		$("#btn-update").on("click", () => {	// jQuery를 사용하여 "#btn-insert"라는 HTML 요소에 클릭 이벤트 설정
			_this.updatePost();		// 클릭되면 insertPost 함수 호출.
		});
		$("#btn-delete").on("click", () => {	// jQuery를 사용하여 "#btn-insert"라는 HTML 요소에 클릭 이벤트 설정
			_this.deletePost();		// 클릭되면 insertPost 함수 호출.
		});
	},

	insertPost: function() {	// 회원가입 요청됨 이라는 알림창을 띄우는 함수.
		alert("포스트 등록 요청됨");
		// 사용자가 입력한 값(title, content) 추출
		let post = {	// user 객체 선언
			title: $("#title").val(),		// $().val() 메소드는 선택한 HTML 요소의 값을 반환하는 jQuery 메소드
			content: $("#content").val()	// #title, #content 전부 각각 해당 id를 가진
			// HTML 요소를 선택하는 selectors 다? 그냥 값 반환해주기 위한 id 정도로 생각하자.
		}
		// Ajax를 이용한 비동기 호출
		// done() 함수 : 요청 처리에 성공했을 때 실행될 코드
		// fail() 함수 : 요청 처리에 실패했을 때 실행될 코드
		$.ajax({
			type: "POST",	// 요청 방식
			url: "/post",	// 요청 경로
			data: JSON.stringify(post),	// user 객체를 JSON 형식으로 변환
			// HTTP의 body에 설정되는 데이터 마임타임
			contentType: "application/json; charset=utf-8"
			// 응답으로 들어온 JSON 데이터를 response로 받는다.
		}).done(function(response) {
			// 응답 메세지를 콘솔에 출력하고 메인 페이지로 이동
			let status = response["status"];
			if(status == 200)
			{
				let message = response["data"];
				alert(message);
			    location = "/";
			}
			else
			{
				let warn = "";
				let errors = response["data"];
				if(errors.title != null) warn = warn + errors.title + "\n";
				if(errors.content != null) warn = warn + errors.content;
				alert(warn);
			}

			// 에러 발생 시 error로 에러 정보를 받는다.
		}).fail(function(error) {
			// 에러 메세지를 알림창에 출력
			let message = error["data"];
			alert("에러 발생 : " + error);
		});
	},
	updatePost: function() {
		alert("포스트 수정 요청됨");

		let post = {
			id: $("#id").val(),
			title: $("#title").val(),
			content: $("#content").val()
		}

		$.ajax({
			type: "PUT",
			url: "/post",
			data: JSON.stringify(post),
			contentType: "application/json; charset=utf-8"
		}).done(function(response) {
			let message = response["data"];
			alert(message);
			location = "/";
		}).fail(function(error) {
			let message = error["data"];
			alert("에러 발생 : " + error);
		});
	},

		deletePost: function() {
		alert("포스트 삭제 요청됨");

		let id = $("#id").text();

		// 아래는 AJAX 요청 코드입니다.
		// 이 코드는 updatePost 메서드 안에 있어야 합니다.
		$.ajax({
			type: "DELETE",
			url: "/post/" + id,
			contentType: "application/json; charset=utf-8"
		}).done(function(response) {
			let message = response["data"];
			alert(message);
			location = "/";
		}).fail(function(error) {
			let message = error.responseJSON ? error.responseJSON.message : error.responseText;
			alert("에러 발생 : " + error);
		});
	},
}

// userObject 객체의 init() 함수 호출
postObject.init();	// userObject.init() 함수를 호출하여 초기화를 진행한 뒤, btn-save 요소의 클릭 이벤트 진행 -> 회원가입 요청됨 알림창 띄우기.
