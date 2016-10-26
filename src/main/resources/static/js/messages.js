'use strict';

var messages = JSON.parse(localStorage.getItem("messages"));

if (messages) {
    var table = $("#message-table").removeClass("hide");
    messages.forEach(function (item, index) {
        var tbody = table.find('tbody');
        var row = $('<tr>');
        row.append($('<td>').text(item.user));
        row.append($('<td>').text(item.content));
        var date = new Date(item.time * 1000);
        row.append($('<td>').text(date.getMonth() + 1 + "/" + date.getDate() + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()));
        tbody.prepend(row);
    });
}

/*Mark messages as seen*/
$("#unreadMessages").text("");
