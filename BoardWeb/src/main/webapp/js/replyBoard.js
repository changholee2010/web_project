/**
 * replyBoard.js
 * replyService에 정의된 메소드를 통해서 기능 실행.
 */

/*------------------------------
   이벤트(댓글등록)
------------------------------*/
document.querySelector('#addReply').addEventListener('click', addReplyFnc);



/******************
 * 댓글목록 그리기.
 *******************/
svc.replyList(bno, // 원본글번호.
	function(result) {
		console.log(result);
		result.forEach(reply => {
			document.querySelector('div.content ul').appendChild(makeLi(reply));
		});
	}, // 성공처리 됐을 때 실행함수.
	function(err) {
		console.log(err);
	} // 실패했을 때 실행함수.
);

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
						e.target.parentElement.parentElement.remove();
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
				document.querySelector('div.content ul').appendChild(makeLi(newReply));
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

