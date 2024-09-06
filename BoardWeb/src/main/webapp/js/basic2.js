/**
 * basic2.js
 */
let students = [
	{ sno: 111, sname: '김민지', score: 90 },
	{ sno: 112, sname: '홍길동', score: 80 },
	{ sno: 113, sname: '박문수', score: 70 }
]

let table = document.createElement('table');
table.setAttribute('border', '2');

for (let student of students) {
	let tr = document.createElement('tr');

	for (let prop in student) {
		let td = document.createElement('td');
		td.innerHTML = student[prop];
		tr.appendChild(td); // 하위요소를 상위요소에 부착.
		// console.log('속성: ' + prop + ', 값: ' + student[prop]);
	}
	table.appendChild(tr);
	//console.log("============================")
}
document.querySelector('#show').appendChild(table);