<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset='utf-8' />
  <script src='./js/index.global.js'></script>
  <script>
    Date.prototype.yyyymmdd = function () {
      let yyyy = this.getFullYear();
      let mm = this.getMonth() + 1;
      let dd = this.getDate();
      return yyyy + '-' + ('0' + mm).slice(-2) + '-' + ('0' + dd).slice(-2);
    }
    Date.prototype.today = function () {
      let today = new Date();
      return today.yyyymmdd();
    }
    console.log(new Date().today())
    let eventData = "";

    document.addEventListener('DOMContentLoaded', function () {
      var calendarEl = document.getElementById('calendar');

      fetch('eventList.do')
        .then(resolve => resolve.json())
        .then(result => {
          // eventData 값을 할당.
          eventData = result;
          console.log('data ', eventData); // ajax는 콜백함수로 실행.
          // 오늘의 일정추가하기.
          let todayEvent = {
            title: '오늘의 일정',
            start: new Date().today(),
            url: 'today.do'
          }
          eventData.push(todayEvent);

          // 값을 할당한 후에 실행.
          var calendar = new FullCalendar.Calendar(calendarEl, {
            headerToolbar: {
              left: 'prev,next today',
              center: 'title',
              right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            initialDate: new Date(),
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectMirror: true,
            select: function (arg) {
              var title = prompt('일정을 등록하세요:');
              if (title) {
                console.log(arg);
                // 시작일정과 종료일정을 구분해서 넣어야함.
                let start = arg.startStr;
                let end = arg.endStr;
                // 시간일정까지 포함하려면 startStr에 있는 +09:00을 제거하고 추가.
                if (!arg.allDay) {
                  start = arg.startStr.substring(0, 19); // +09:00
                  end = arg.endStr.substring(0, 19);
                }
                // Ajax 호출.
                fetch('addEvent.do?title=' + title + '&start=' + start + '&end=' + end)
                  .then(resolve => resolve.json())
                  .then(result => {
                    if (result.retCode == 'OK') {
                      calendar.addEvent({
                        title: title,
                        start: arg.start,
                        end: arg.end,
                        allDay: arg.allDay
                      })
                    } // 정상등록되면 화면에 추가된 일정을 넣어줌.
                  })
              } // end of if.
              calendar.unselect()
            },
            eventClick: function (arg) {
              if (arg.event.url) {
                return;
              }
              if (confirm('일정을 삭제하겠습니까?')) {
                let title = arg.event.title;
                let start = arg.event.startStr;
                let end = arg.event.endStr;
                if (!arg.event.allDay) {
                  start = arg.event.startStr.substring(0, 19);
                  end = arg.event.endStr.substring(0, 19);
                }
                // Ajax 호출.
                fetch('removeEvent.do?title=' + title + '&start=' + start + '&end=' + end)
                  .then(resolve => resolve.json())
                  .then(result => {
                    if (result.retCode == 'OK') {
                      arg.event.remove(); // 화면에서 지우기.
                    }
                  })
              }
            },
            editable: true,
            dayMaxEvents: true, // allow "more" link when too many events
            events: eventData
          }); // calendar 생성.

          calendar.render();

        })
        .catch(err => console.log(err));

    });
  </script>
  <style>
    body {
      margin: 40px 10px;
      padding: 0;
      font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
      font-size: 14px;
    }

    #calendar {
      max-width: 1100px;
      margin: 0 auto;
    }
  </style>
</head>

<body>

  <div id='calendar'></div>

</body>

</html>