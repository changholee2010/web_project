/**
 * replyBoard.js
 * replyService에 정의된 메소드를 통해서 기능 실행.
 */

let pagination;

// DOM 요소를 다 읽어들인다음에 코드실행.
document.addEventListener("DOMContentLoaded", function(e) {
	/*------------------------------
	   이벤트(댓글등록)
	------------------------------*/
	// 댓글등록.
	document.querySelector('#addReply').addEventListener('click', addReplyFnc);

	// 페이지링크 클릭.
	document.querySelectorAll('ul.pagination a').forEach(aTag => {
		aTag.addEventListener('click', showReplyListFnc);
	})

	pagination = document.querySelector('ul.pagination');

	//댓글과 페이지리스트 보여주기.
	showReplyListAndPagingList();

}) // end of DOMContentLoaded.

let page = 1; // 페이지변경될때마다 사용해야함.

/*------------------
  댓글정보 -> li 생성하는 함수.
-------------------*/
function makeLi(reply = {}) {
	let cloned = document.querySelector('#template').cloneNode(true); // 복제.
	cloned.style.display = 'block'; // display속성도 복사되기 때문에 block으로 변경.
	cloned.setAttribute('data-rno', reply.replyNo);
	cloned.querySelector('span').innerHTML = reply.replyNo;
	cloned.querySelector('span:nth-of-type(2)').innerHTML = reply.reply;
	cloned.querySelector('span:nth-of-type(3)').innerHTML = reply.replyer;
	cloned.querySelector('button').addEventListener('click', deleteLiFnc);
	console.log(cloned);
	return cloned;
}

/*----------------------
  함수: deleteLiFnc
  기능: 버튼이 포함된 row 삭제. (ajax포함)
----------------------*/
function deleteLiFnc(e) {

	// SweetAlert code.
	const swalWithBootstrapButtons = Swal.mixin({
		customClass: {
			confirmButton: "btn btn-success",
			cancelButton: "btn btn-danger"
		},
		buttonsStyling: false
	});
	swalWithBootstrapButtons.fire({
		title: "Are you sure?",
		text: "You won't be able to revert this!",
		icon: "warning",
		showCancelButton: true,
		confirmButtonText: "Yes, delete it!",
		cancelButtonText: "No, cancel!",
		reverseButtons: true
	}).then((result) => {
		if (result.isConfirmed) { // 삭제OK일 경우?
			// 삭제처리코드.
			let rno = e.target.parentElement.parentElement.dataset.rno;
			svc.removeReply(rno, // 삭제글번호
				function(result) {
					if (result.retCode == 'OK') {
						swalWithBootstrapButtons.fire({ // 정삭적으로 삭제완료된 경우.
							title: "Deleted!",
							text: "정상적으로 삭제되었습니다.",
							icon: "success"
						});
						// 화면에서 삭제처리.
						//e.target.parentElement.parentElement.remove();
						showReplyListAndPagingList();
					} else {
						Swal.fire({
							title: "Fail!",
							text: "Your file has been deleted.",
							icon: "error"
						});
					}
				},
				function(err) {
					console.log(err);
				}
			);

		} else if (result.dismiss === Swal.DismissReason.cancel) {
			swalWithBootstrapButtons.fire({
				title: "Cancelled",
				text: "삭제 취소했습니다!",
				icon: "error"
			});
		}
	});

}

/*--------------------
   댓글등록 이벤트핸들러.
   함수: addReplyFnc
   기능: 입력값 등록. (ajax포함)
--------------------*/
function addReplyFnc(e) {
	// bno, replyer, reply 값이 있는지 확인.
	let reply = document.querySelector('#reply').value;
	// 로그인정보, 댓글정보가 있는지 확인하고 처리를 진행.
	if (!writer || !reply) {
		alert('필수입력값 입력');
		return;
	}
	// 입력값정보.
	let param = {
		bno: bno,
		replyer: writer,
		reply: reply
	}
	// svc객체의 addReply메소드 호출.
	svc.addReply(param,
		function(result) {
			if (result.retCode == 'OK') {
				let newReply = result.retVal; // 신규로 입력된 댓글정보.
				Swal.fire({
					title: "성공!",
					text: "등록이 처리되었습니다!",
					icon: "success"
				});
				// 댓글추가.
				//document.querySelector('div.content ul').appendChild(makeLi(newReply));
				page = 1;
				showReplyListAndPagingList();
			} else {
				alert('등록처리 중 예외발생');
			}
			// 입력값 비워주기.
			document.querySelector('#reply').value = '';
		},
		function(err) {
			console.log(err);
		}
	);
}

/*--------------------
   링크클릭시 댓글목록 새로출력.
   함수: showReplyListFnc
--------------------*/
function showReplyListFnc(e) {

	page = e.target.dataset.page; // 페이지번호.

	// 함수호출로 대체함.
	showReplyListAndPagingList();
}

/*---------------------
   댓글갯수를 활용해서 페이지리스트 생성.
   함수: showPagingListFnc
---------------------*/
function showPagingListFnc() {
	svc.replyPagingCount(bno, // 글번호.
		makePagingList, // 성공했을때 실행할 콜백함수. callback
		function(err) {
			console.log(err);
		}
	)
};

// 정상처리 실행할 콜백함수.
function makePagingList(result) {
	pagination.innerHTML = ''; // 기존 페이지 리스트 지우기.

	let totalCnt = result.totalCount;
	// 페이지목록 만들기.
	let startPage, endPage, realEnd; // 첫페이지 ~ 마지막페이지
	let prev, next; // 이전페이지, 이후페이지

	endPage = Math.ceil(page / 5) * 5;
	startPage = endPage - 4; // 6 ~ 10
	realEnd = Math.ceil(totalCnt / 5);

	endPage = endPage > realEnd ? realEnd : endPage;
	prev = startPage > 1;
	next = endPage < realEnd;

	// ??

	// 이전 페이지 생성.
	let li = document.createElement('li');
	li.className = 'page-item';
	let aTag = document.createElement('a');
	aTag.className = 'page-link';
	aTag.innerHTML = 'Previous';
	li.appendChild(aTag);
	if (prev) {
		aTag.setAttribute('href', '#');
		aTag.setAttribute('data-page', startPage - 1);
	} else {
		li.classList.add('disabled');
	}
	pagination.appendChild(li);

	// li 생성. 페이지 범위에 들어갈...
	for (let p = startPage; p <= endPage; p++) {
		let li = document.createElement('li');
		li.className = 'page-item';
		let aTag = document.createElement('a');
		aTag.className = 'page-link';
		aTag.setAttribute('href', '#');
		aTag.setAttribute('data-page', p);
		aTag.innerHTML = p;
		li.appendChild(aTag); // <li class="page-item"><a class="page-link" href="#">1</a></li>
		if (p == page) { // 현재페이지 스타일 변경.
			li.classList.add('active');
			li.setAttribute('aria-current', 'page')
		}
		pagination.appendChild(li);
	}

	// 이후 페이지 생성.
	li = document.createElement('li');
	li.className = 'page-item';
	aTag = document.createElement('a');
	aTag.className = 'page-link';
	aTag.innerHTML = 'Next';
	li.appendChild(aTag);
	if (next) {
		aTag.setAttribute('href', '#');
		aTag.setAttribute('data-page', endPage + 1);
	} else {
		li.classList.add('disabled');
	}
	pagination.appendChild(li);

	// 이벤트 등록..
	document.querySelectorAll('ul.pagination a').forEach(aTag => {
		aTag.addEventListener('click', showReplyListFnc);
	})
}

/******************
 * 댓글목록 출력. 함수로 변경. showReplyListAndPagingList
 *******************/
function showReplyListAndPagingList() {
	svc.replyList({ bno, page }, // 원본글번호.
		function(result) {
			// 기존목록을 삭제하고 새로 그려주기.
			document.querySelectorAll('div.content li').forEach((item, indx) => {
				if (indx > 2) {
					item.remove();
				}
			})
			// 목록 출력.
			result.forEach(reply => {
				document.querySelector('div.content ul').appendChild(makeLi(reply));
			});
			showPagingListFnc();
		}, // 성공처리 됐을 때 실행함수.
		function(err) {
			console.log(err);
		} // 실패했을 때 실행함수.
	);
}