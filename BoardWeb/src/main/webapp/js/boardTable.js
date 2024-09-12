/**
 * boardTable.js
 */

const table = new DataTable('#example', {
	ajax: 'replyTable.do?bno=' + bno,
	columnDefs: [
		{
			render: (data, type, row) => '<button onclick="deleteRow(event, ' + row.replyNo + ')">삭제</button>',
			targets: 4
		},
		//{ visible: false, targets: [3] }
	],
	columns: [
		{ data: 'replyNo' },
		{ data: 'reply' },
		{ data: 'replyer' },
		{ data: 'replyDate' }
	],
	lengthMenu: [
		[5, 10, 20, -1],
		[5, 10, 20, 'All']
	],
	order: {
		idx: 0,
		dir: 'desc'
	}
});

function deleteRow(e, rno) {
	//console.log(e.target.parentElement.parentElement.firstChild.innerHTML); // button상위상위.첫번째자식요소.html
	//delNum = e.target.parentElement.parentElement.firstChild.innerHTML;
	console.log(e);
	if (e.target.parentElement.parentElement.classList.contains('selected')) {
		e.stopPropagation(); // 상위요소로의 이벤트 차단.
	}
	delNum = rno;
	// Ajax.
	fetch('removeReply.do?rno=' + delNum)
		.then(resolve => resolve.json())
		.then(result => {
			if (result.retCode == 'OK') {
				alert('삭제완료!');
				table.row('.selected').remove().draw(false); // datatable 함수.
			} else {
				alert('예외 발생');
			}
		})
		.catch(err => console.log(err));
}


let delNum = '';
// 삭제기능 구현.
document.querySelector('#delReply').addEventListener('click', function() {
	// Ajax 호출.
	if (!delNum) {
		alert('삭제할 댓글을 선택하세요.');
		return;
	}
	// Ajax.
	fetch('removeReply.do?rno=' + delNum)
		.then(resolve => resolve.json())
		.then(result => {
			if (result.retCode == 'OK') {
				alert('삭제완료!');
				table.row('.selected').remove().draw(false); // datatable 함수.
			} else {
				alert('예외 발생');
			}
		})
		.catch(err => console.log(err));
});

// row 선택.
table.on('click', 'tbody tr', (e) => {

	delNum = e.currentTarget.firstChild.innerHTML; //삭제할 댓글번호 저장.
	let classList = e.currentTarget.classList;

	if (classList.contains('selected')) {
		classList.remove('selected');
		delNum = ''; // 해제.
	}
	else {
		table.rows('.selected').nodes().each((row) => row.classList.remove('selected'));
		classList.add('selected');
	}
});




// addReply에 클릭.
// replyNo: 111, reply: test, replyer: user01, replyDate: 2023.11.11
document.querySelector('#addReply').addEventListener('click', addNewRow);

// 화면에 데이터 추가하는 함수.
function addNewRow() {
	// 로그인, 댓글 정보 없으면 종료.
	let reply = document.querySelector('#reply').value;
	if (!writer || !reply) {
		alert('필수입력 항목을 확인하세요.')
		return;
	}

	fetch('addReply.do', {
		method: 'post',
		headers: {
			'Content-Type': 'application/x-www-form-urlencoded'
		},
		body: 'bno=' + bno + '&reply=' + reply + '&replyer=' + writer
	})
		.then(resolve => resolve.json())
		.then(result => {
			// 정상처리 or 예외
			if (result.retCode == 'OK') {
				let newValue = result.retVal;
				table.row
					.add({
						replyNo: newValue.replyNo,
						reply: newValue.reply,
						replyer: newValue.replyer,
						replyDate: newValue.replyDate
					})
					.draw(false);

				// 댓글입력 초기화.
				document.querySelector('#reply').value = '';
			} else {
				alert('예외 발생!!');
			}
		})
		.catch(err => console.log(err));

}// end of addNewRow()

