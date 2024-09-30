<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<head>
  <meta charset='utf-8' />
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
    integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
  <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
    integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous">
  </script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js"
    integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous">
  </script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js"
    integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous">
  </script>

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
    let calendar;

    document.addEventListener('DOMContentLoaded', function () {
      var calendarEl = document.getElementById('calendar');

      fetch('eventList.do')
        .then(resolve => resolve.json())
        .then(result => {
          // eventData 값을 할당.
          eventData = result;
          // 오늘의 일정추가하기.
          let todayEvent = {
            title: '오늘의 일정',
            start: new Date().today(),
            url: 'today.do'
          }
          eventData.push(todayEvent);

          // 값을 할당한 후에 실행.
          calendar = new FullCalendar.Calendar(calendarEl, {
            headerToolbar: {
              left: 'prev,next today',
              center: 'title',
              right: 'dayGridMonth,timeGridWeek,timeGridDay'
            },
            initialDate: '2024-10-01',
            navLinks: true, // can click day/week names to navigate views
            selectable: true,
            selectMirror: true,
            select: function (arg) {
              //var title = prompt('일정을 등록하세요:');
              // if (title) {
              modalOpen(arg);
              // } // end of if.
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

      let modalEx = document.querySelector('#modalEx');
      let modalTitle = document.querySelector('#modalTitle');
      let modalArgs = null;

    });

    // 모달창 열기.
    function modalOpen(args) {
      modalArgs = args;
      // modal 보여주기.
      modalEx.classList.add('show');
      modalEx.style.display = 'block';
      modalEx.style.paddingLeft = '17px';
      let backDrop = document.createElement('div');
      backDrop.className = 'modal-backdrop fade show';
      modalEx.after(backDrop);
      document.querySelector('body').classList.add('modal-open');
      document.querySelector('body').style.paddingRight = '17px';

      // 일자지정.
      document.querySelector('#modalStart').value = args.startStr;
      document.querySelector('#modalEnd').value = args.endStr;
      modalTitle.value = '';
      modalTitle.focus();
    }

    // 모달창 닫기.
    function modalClose() {
      modalEx.classList.remove('show');
      modalEx.style.display = 'none';
      document.querySelector('body').classList.remove('modal-open');
      document.querySelector('body').style.paddingRight = '';
      document.querySelector('div.modal-backdrop').remove();
    }

    // 모달 저장.
    function modalSave() {
      // 시작일정과 종료일정을 구분해서 넣어야함.
      let start = modalArgs.startStr;
      let end = modalArgs.endStr;
      // 시간일정까지 포함하려면 startStr에 있는 +09:00을 제거하고 추가.
      if (!modalArgs.allDay) {
        start = modalArgs.startStr.substring(0, 19); // +09:00
        end = modalArgs.endStr.substring(0, 19);
      }
      // Ajax 호출.
      fetch('addEvent.do?title=' + modalTitle.value + '&start=' + start + '&end=' + end)
        .then(resolve => resolve.json())
        .then(result => {
          if (result.retCode == 'OK') {
            calendar.addEvent({
              title: modalTitle.value,
              start: modalArgs.start,
              end: modalArgs.end,
              allDay: modalArgs.allDay
            })
            modalClose();
          } // 정상등록되면 화면에 추가된 일정을 넣어줌.
        })
    }

    // change event.
    // 처음선택한 후에 날짜를 변경할 경우에...
    function startChange(e) {
      modalArgs.start = new Date(e.target.value);
      modalArgs.startStr = e.target.value;
    }

    function endChange(e) {
      modalArgs.end = new Date(e.target.value);
      modalArgs.endStr = e.target.value;
    }
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

  <!-- Button trigger modal -->
  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalEx">
    Launch demo modal
  </button>

  <!-- Modal -->
  <div class="modal fade" id="modalEx" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLongTitle">일정을 등록하세요.</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close" onclick="modalClose()">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <table class="table">
            <tr>
              <th>타이틀</th>
              <td><input type="text" class="form-control" id="modalTitle"></td>
            </tr>
            <tr>
              <th>시작일시</th>
              <td><input type="date" id="modalStart" onchange="startChange(event)"></td>
            </tr>
            <tr>
              <th>종료일시</th>
              <td><input type="date" id="modalEnd" onchange="endChange(event)"></td>
            </tr>
          </table>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="modalClose()">Close</button>
          <button type="button" class="btn btn-primary" id="modalSave" onclick="modalSave()">Save changes</button>
        </div>
      </div>
    </div>
  </div>
  <!-- <div class="modal-backdrop fade show"></div> -->
</body>

</html>