/**
 * boardTable.js
 */
new DataTable('#example', {
	ajax: 'replyTable.do?bno=' + bno,
	columns: [
		{ data: 'replyNo' },
		{ data: 'reply' },
		{ data: 'replyer' },
		{ data: 'replyDate' }
	]
});