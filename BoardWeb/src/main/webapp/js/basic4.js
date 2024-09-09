/**
 * basic4.js
 */
console.log(json); // 문자열.
let data = JSON.parse(json); // 문자열 -> 객체 변경.
console.log(data); // 객체
console.clear();

let result = data.filter(function(item, idx, ary) {
	if (item.salary >= 8000 && item.gender == 'Female') {
		//console.log(item);
		return true;
	}
});
console.log(result);

//검색클릭 이벤트.
document.getElementById('searchBtn').addEventListener('click', function(e) {
	let salary = document.getElementById('salary').value;
	let gender = document.getElementById('gender').value;
	console.log(salary, gender)
	let result = data.filter(function(item, idx, ary) {
		if (item.salary >= salary && item.gender == gender) {
			return true;
		}
	});
	console.log(result);
})

