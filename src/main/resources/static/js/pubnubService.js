'use strict';

var pubnub = PUBNUB.init({
    publish_key: 'demo',
    subscribe_key: 'demo',
    error: function (error) {
        console.log('Error:', error);
    }
});

function storeMessage(message) {
    var messages = JSON.parse(localStorage.getItem("messages"));

    if (messages) {
        messages.push(message);
    } else {
        messages = [message];
    }

    localStorage.setItem("messages", JSON.stringify(messages));

    if (location.pathname != "/messages") {
        var number = 1;
        var unreadMessages = $("#unreadMessages");

        if (unreadMessages.text()) {
            number = parseInt(unreadMessages.text()) + number;
        }

        unreadMessages.text(number);
    } else {
        var table = $("#message-table");
        table.removeClass("hide");
        var tbody = table.find('tbody');
        var row = $('<tr>');
        row.append($('<td>').text(message.user));
        row.append($('<td>').text(message.content));
        var date = new Date(message.time * 1000);
        row.append($('<td>').text(date.getMonth() + 1 + "/" + date.getDate() + "/" + date.getFullYear() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds()));
        tbody.prepend(row);
    }
}

function init() {
    var channel = $("#channel").html();

    if (channel) {
        pubnub.subscribe({
            channel: channel,
            message: function (message, envelope, channelOrGroup, time, channel) {
                storeMessage(message);
            }
        })
    }
}

init();

