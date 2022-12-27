'use strict'

let time, count, deltaMaxAsk, deltaMinAsk, deltaMaxBid, deltaMinBid, countFirst, timeFirst, countSecond, timeSecond
let obj1 = document.getElementById("block_id1");
let obj2 = document.getElementById("block_id2");

document.querySelector('.activity').addEventListener('click', function(ev) {
    obj1.style.display == "block" ? obj1.style.display = "none" : obj1.style.display = "block";
    obj2.style.display = "none";
});
document.querySelector('.passivity').addEventListener('click', function(ev) {
    obj1.style.display = "none";
    obj2.style.display == "block" ? obj2.style.display = "none" : obj2.style.display = "block";
});

function writeValues() {
  var settings = JSON.stringify({
  pattern: obj1.style.display == "block" ? "activity" : "passivity",
  time: document.getElementById("time").value,
  count: document.getElementById("count").value,
  deltaMaxAsk: document.getElementById("deltaMaxAsk").value,
  deltaMinAsk: document.getElementById("deltaMinAsk").value,
  deltaMaxBid: document.getElementById("deltaMaxBid").value,
  deltaMinBid: document.getElementById("deltaMinBid").value,
  countFirst: document.getElementById("countFirst").value,
  timeFirst: document.getElementById("timeFirst").value,
  countSecond: document.getElementById("countSecond").value,
  timeSecond: document.getElementById("timeSecond").value
  });

  $.ajax({
      url: '/api/v1/ea/params',
      method: 'put',
      dataType: 'json',
      contentType: "application/json; charset=utf-8",
      data: settings,
      statusCode: {
        200: function() {
          alert("Настройки установлены");
        },
        404: function() {
          alert("Настройки не установлены");
        }
      }
  });
  document.querySelectorAll('input')
    .forEach(function (input) {
        input.value = "";
    });
    getSetting();
}

function getSetting() {
  $.get('/api/v1/ea/params', {}, function(response){
    var i;
      for(i in response){
      let element = response[i];
      document.getElementById('containerId').querySelectorAll('input')
      .forEach(function (input) {
        if (input.className == element.name) {
          input.value = element.value
        }
      });
     }
  });
}     


