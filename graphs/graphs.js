// When the document has finished loading:
$(document).ready(function(){
    d3.csv("word_count.csv", function(data) {
        var count = [];
        var words = [];
        for (var i = 0 ; i < data.length; i++) {
            count[i] = parseInt(data[i]["Count"]);
            words[i]= data[i]["Word"];
        }
        displayWordCountChart(words, count);
    });
});


function displayWordCountChart(words, count) {
  new Chart(document.getElementById("word_occurence_count"), {
    type: 'bar',
    data: {
      labels: words,
      datasets: [
        {
          label: "Words",
          backgroundColor: ["#A569BD", "#F4D03F", "#3498DB", "#C0392B", "#52BE80",
                            "#85929E", "#F08080"],
          data: count
        }
      ]
    },
    options: {
      responsive: false,
      maintainAspectRatio: true,
      legend: {
        display: false
      },
      tooltips: {
        display: false
      },
      title: {
        display: true,
        fontSize: 30,
        text: 'Word Ocurrences Count'
      },
      scales: {
        yAxes: [{
                 ticks: {
                     beginAtZero: true,
                     steps: 10,
                     stepValue: 5,

                 }
        }],
        xAxes: [{
            ticks: {
                fontSize: 20
            }
        }]
      }
    }
  });
}
