$(document).ready(function () {
    if (document.location.pathname === '/journal') {
        let dateSet = createDateSet();
        $('#datepicker').datepicker({
            beforeShowDay: function (d) {
                if (dateSet.has(formatDate(d))) {
                    return {classes: "haveMarks", tooltip: "Have marks"}
                }
            }
        }).on('changeDate', function (e) {
            let date = formatDate(e.date);
            getMarksByDate(date);
        });
    }
    if (document.location.href.indexOf('/journal/new/') > -1) {
        let subject = $('#addMarkSubject');
        addTeacher(subject.val());
        subject.change(function () {
            addTeacher(subject.val());
        });
    }
    if (document.location.href.indexOf('/journal/students/') > -1) {
        let cell = $('td[mark=true]');
        let row;
        let column;
        let subjectId;
        cell.dblclick(function (e) {
            if ($(this).children().length > 0) return;
            $('#marks select').remove();
            $('#marks button').remove();
            row = e.target.parentNode.rowIndex;
            column = e.target.cellIndex;
            subjectId = document.getElementById('marks')
                .rows[0].cells[column]
                .getAttribute('subject_id');
            editMark(e, subjectId);
        });
        $(document).on('click',
            '#saveMarkButton',
            function () {
                saveMark(row, column);
            });
    }
});

function addTeacher(subjectId) {
    $.ajax({
        method: "POST",
        url: "/api/teachers/subject",
        data: {subjectId: subjectId},

        success: function (response) {
            let data = '';
            $.each(response, function (idx) {
                data += '<option value=' + response[idx].id + '>'
                    + response[idx].firstName + ' '
                    + response[idx].lastName + '</option>';
            });
            $('#addMarkTeacher').html(data);
        }
    });


}

function editMark(e, subjectId) {
    let cell = e.target;
    cell.innerHTML = '<select class="selectMarks">' +
        '<option value="1">1</option>'
        + '<option value="2">2</option>'
        + '<option value="3">3</option>'
        + '<option value="4">4</option>'
        + '<option value="5">5</option></select>'
        + '<select id="addMarkTeacher" ></select>'
        + '<button type="button" id="saveMarkButton" ' +
        'class="btn btn-primary btn-sm">OK</button>';
    addTeacher(subjectId);


}

function getMarksBySubject(subjectId) {
    $.ajax({
        method: "POST",
        url: "/api/marks/subject",
        data: {subjectId: subjectId},
        success: function (response) {
            drawTableBody(response);
        },
        error: function () {
            errorHandler();
        }

    });
}

function getMarksByDate(date) {
    $.ajax({
        method: "POST",
        url: "/api/marks/date",
        data: {date: date},
        success: function (response) {
            drawTableBody(response);
        },
        error: function () {
            errorHandler();
        }
    });
}

function getMarksByTeacher(teacherId) {
    $.ajax({
        method: "POST",
        url: "/api/marks/teacher",
        data: {teacherId: teacherId},
        success: function (response) {
            drawTableBody(response);
        },
        error: function () {
            errorHandler();
        }
    });
}

function saveMark(row, column) {
    let marks = document.getElementById('marks');
    let subjectId = marks.rows[0].cells[column]
        .getAttribute('subject_id');
    let studentId = $('#student').attr('student_id');
    let created = marks.rows[row].cells[1].innerHTML;
    let value = $('.selectMarks').eq(0).val();
    let teacherId = $('#addMarkTeacher').val();
    $.post({
        url: "/journal/students/" + studentId,
        data: {
            subjectId: subjectId,
            teacherId: teacherId,
            created: created,
            value: value
        },
        success: function () {
            location.reload();
        },
        error: function () {
            errorHandler();
        }
    });
}

function drawTableBody(data) {
    let temp = '';
    $.each(data, function (idx, value) {
        let mark = value.value;
        let color = mark < 3 ?
            'btn btn-danger' : mark > 3 ?
                'btn btn-success' : 'btn btn-warning';
        let cell = "<a class='"
            + color
            + "' href='/journal/edit/"
            + value.id + "'>"
            + mark + "</a>";

        temp += '<tr><td>' + (idx + 1) + '</td><td>'
            + value.created + '</td><td>'
            + value.student.lastName + ' '
            + value.student.firstName + '</td><td>'
            + value.subject.title + '</td><td>'
            + value.teacher.lastName + ' '
            + value.teacher.firstName + '</td><td>'
            + cell + '</td></tr>'
    });
    $('#journal tbody').html(temp);
}

function errorHandler() {
    setTimeout(() => location.reload(), 1500);
    $('#error_msg')
        .html('Sorry, '
            + $('#username').text()
            + ', access denied!')
        .show().hide(6000);
}

function formatDate(date) {
    let d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

function createDateSet() {
    let rows = document.getElementById('journal').rows;
    let dateSet = new Set();
    for (let i = 1; i < rows.length; i++) {
        dateSet.add(rows[i].cells[1].innerText);
    }
    return dateSet;
}
