// When the document has finished loading:
$(document).ready(function(){
    d3.csv("wordCount.csv", function(data) {
        var count = [];
        var words = [];
        for (var i = 0 ; i < data.length; i++) {
            count[i] = parseInt(data[i]["Count"]);
            words[i]= data[i]["Word"];
        }
        displayWordCountChart(words, count);
    });
      displayReplyChart();
      displayUserMessageChart();
      displayUsernameChart();
      displayKeywordChart();
});
function displayReplyChart() {
    var svg = d3.select("#replies"),
    margin = 20,
    diameter = +svg.attr("width"),
    g = svg.append("g").attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

    var color = d3.scaleLinear()
        .domain([-1, 5])
        .range(["hsl(152,80%,80%)", "hsl(228,30%,40%)"])
        .interpolate(d3.interpolateHcl);

    var pack = d3.pack()
        .size([diameter - margin, diameter - margin])
        .padding(2);

    d3.csv("replies.csv", function(error, data) {
      if (error) throw error;
      var root = {name: "Replies", children: []};
      for (var i = 0 ; i < data.length; i++) {
          if(data[i]['replies'].length > 2 ) {
              var name = data[i]['tweet_id'];
              var replies = data[i]["replies"].substring(1, data[i]["replies"].length-1).split(",");
              var children = [];
              for (var reply of replies) {
                  children.push({name:reply.trim(), size:500});
              }
              root['children'].push({name, children});
          }
      }
      console.log("Replies " + root['children'].length);
      root = d3.hierarchy(root)
          .sum(function(d) { return d.size; })
          .sort(function(a, b) { return b.value - a.value; });

      var focus = root,
          nodes = pack(root).descendants(),
          view;

      var circle = g.selectAll("circle")
        .data(nodes)
        .enter().append("circle")
          .attr("class", function(d) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
          .style("fill", function(d) { return d.children ? color(d.depth) : null; })
          .on("click", function(d) { if (focus !== d) zoom(d), d3.event.stopPropagation(); });

      var text = g.selectAll("text")
        .data(nodes)
        .enter().append("text")
          .attr("class", "label")
          .style("fill-opacity", function(d) { return d.parent === root ? 1 : 0; })
          .style("display", function(d) { return d.parent === root ? "inline" : "none"; })
          .text(function(d) { return d.data.name; });

      var node = g.selectAll("circle,text");

      svg
          .style("background", color(-1))
          .on("click", function() { zoom(root); });

      zoomTo([root.x, root.y, root.r * 2 + margin]);

      function zoom(d) {
        var focus0 = focus; focus = d;

        var transition = d3.transition()
            .duration(d3.event.altKey ? 7500 : 750)
            .tween("zoom", function(d) {
              var i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);
              return function(t) { zoomTo(i(t)); };
            });

        transition.selectAll("text")
          .filter(function(d) { return d.parent === focus || this.style.display === "inline"; })
            .style("fill-opacity", function(d) { return d.parent === focus ? 1 : 0; })
            .on("start", function(d) { if (d.parent === focus) this.style.display = "inline"; })
            .on("end", function(d) { if (d.parent !== focus) this.style.display = "none"; });
      }

      function zoomTo(v) {
        var k = diameter / v[2]; view = v;
        node.attr("transform", function(d) { return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")"; });
        circle.attr("r", function(d) { return d.r * k; });
      }
    });

}
function displayUsernameChart() {
    var psv = d3.dsvFormat("\t");
    var count = [0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
    var total = 0;
    d3.request("screenames.txt")
      .mimeType("text/plain")
      .response(function(xhr) { return psv.parse(xhr.responseText) })
      .get(function(data) {
          data[0]["screen_names"] = data[0]["screen_names"].substring(1, data[0]["screen_names"].length-1).split(",");
          total = data[0]['count'];
          for(var name of data[0]["screen_names"]) {
              switch (name.trim().charAt(0).toUpperCase()) {
                case 'A':
                    count[0]++;
                    break;
                case 'B':
                    count[1]++;
                    break;
                case 'C':
                    count[2]++;
                    break;
                case 'D':
                    count[3]++;
                    break;
                case 'E':
                    count[4]++;
                    break;
                case 'F':
                    count[5]++;
                    break;
                case 'G':
                    count[6]++;
                    break;
                case 'H':
                    count[7]++;
                    break;
                case 'I':
                    count[8]++;
                    break;
                case 'J':
                    count[9]++;
                    break;
                case 'K':
                    count[10]++;
                    break;
                case 'L':
                    count[11]++;
                    break;
                case 'M':
                    count[12]++;
                    break;
                case 'N':
                    count[13]++;
                    break;
                case 'O':
                    count[14]++;
                    break;
                case 'P':
                    count[15]++;
                    break;
                case 'Q':
                    count[16]++;
                    break;
                case 'R':
                    count[17]++;
                    break;
                case 'S':
                    count[18]++;
                    break;
                case 'T':
                    count[19]++;
                    break;
                case 'U':
                    count[20]++;
                    break;
                case 'V':
                    count[21]++;
                    break;
                case 'W':
                    count[22]++;
                    break;
                case 'X':
                    count[23]++;
                    break;
                case 'Y':
                    count[24]++;
                    break;
                case 'Z':
                    count[25]++;
                    break;
                default:
                    count[26]++;

              }
          }
          new Chart(document.getElementById("usernames"), {
            type: 'bar',
            data: {
              labels: ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                        'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
                        'Y', 'Z', '##'],
              datasets: [
                {
                  label: "Username Count",
                  backgroundColor: ["#A569BD", "#F4D03F", "#3498DB", "#C0392B", "#52BE80",
                                    "#85929E", "#F08080", "#ff5733", "#7aff33", "#33ffa2",
                                    "#33c1ff", "#be33ff", "#ff3368", "#88d675", "#abf6d7",
                                    "#C70039", "#5f7a83", "#e6a8fa", "#396c46", "#881616",
                                    "#ff6f6f", "#eeff6f", "#54a732", "#7d8f76", "#900C3F",
                                    "#127e6f", "#d6914f"],
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
                text: 'Number Unique Scree names ( Total: ' + total + ')'
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
      });
}
function displayUserMessageChart() {
    var svg = d3.select("#messages"),
    margin = 20,
    diameter = +svg.attr("width"),
    g = svg.append("g").attr("transform", "translate(" + diameter / 2 + "," + diameter / 2 + ")");

    var color = d3.scaleLinear()
        .domain([-1, 5])
        .range(["hsl(26, 65%, 88%)", "hsl(10, 74%, 55%)"])
        .interpolate(d3.interpolateHcl);

    var pack = d3.pack()
        .size([diameter - margin, diameter - margin])
        .padding(2);

    d3.csv("messageCount.csv", function(error, data) {
      if (error) throw error;
      var root = {name: "Messages", children: []};
      for (var i = 0; i < data.length; i++) {
          var temp = data[i]["messages"].split("-");
          var name = data[i]['screen_name'] + " - " + temp[0];
          var messages = temp[1].substring(1, temp[1].length-1).split(",");
          if (messages.length > 3){
              var children = [];
              for (var message of messages) {
                  children.push({name:message.trim(), size:100});
              }
              root['children'].push({name, children});
          }

      }
      console.log("Messages " + root['children'].length);
      root = d3.hierarchy(root)
          .sum(function(d) { return d.size; })
          .sort(function(a, b) { return b.value - a.value; });

      var focus = root,
          nodes = pack(root).descendants(),
          view;

      var circle = g.selectAll("circle")
        .data(nodes)
        .enter().append("circle")
          .attr("class", function(d) { return d.parent ? d.children ? "node" : "node node--leaf" : "node node--root"; })
          .style("fill", function(d) { return d.children ? color(d.depth) : null; })
          .on("click", function(d) { if (focus !== d) zoom(d), d3.event.stopPropagation(); });

      var text = g.selectAll("text")
        .data(nodes)
        .enter().append("text")
          .attr("class", "label")
          .style("fill-opacity", function(d) { return d.parent === root ? 1 : 0; })
          .style("display", function(d) { return d.parent === root ? "inline" : "none"; })
          .text(function(d) { return d.data.name; });

      var node = g.selectAll("circle,text");

      svg
          .style("background", color(-1))
          .on("click", function() { zoom(root); });

      zoomTo([root.x, root.y, root.r * 2 + margin]);

      function zoom(d) {
        var focus0 = focus; focus = d;

        var transition = d3.transition()
            .duration(d3.event.altKey ? 7500 : 750)
            .tween("zoom", function(d) {
              var i = d3.interpolateZoom(view, [focus.x, focus.y, focus.r * 2 + margin]);
              return function(t) { zoomTo(i(t)); };
            });

        transition.selectAll("text")
          .filter(function(d) { return d.parent === focus || this.style.display === "inline"; })
            .style("fill-opacity", function(d) { return d.parent === focus ? 1 : 0; })
            .on("start", function(d) { if (d.parent === focus) this.style.display = "inline"; })
            .on("end", function(d) { if (d.parent !== focus) this.style.display = "none"; });
      }

      function zoomTo(v) {
        var k = diameter / v[2]; view = v;
        node.attr("transform", function(d) { return "translate(" + (d.x - v[0]) * k + "," + (d.y - v[1]) * k + ")"; });
        circle.attr("r", function(d) { return d.r * k; });
      }
    });

}

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
function displayKeywordChart() {
    var count = [0, 0, 0, 0, 0, 0];
    var total = 0;
    d3.csv("keywordCount.csv", function(error, data) {
      if (error) throw error;
      for (var i = 0; i < data.length; i++) {
          var dataCount = parseInt(data[i]['Count']);
          if (dataCount <= 20) {
             count[0]++;
         }
         else if(dataCount <= 40) {
             count[1]++;
         }
         else if(dataCount <= 60) {
             count[2]++;
         }
         else if(dataCount <= 80) {
             count[3]++;
         }
         else if(dataCount <= 100) {
             count[4]++;
         }
         else{
              count[5]++;
         }
     }

          new Chart(document.getElementById("keyword_count"), {
            type: 'bar',
            data: {
              labels: ['20 or less', '21-40', '41-60', '61-80', '81-100', 'more that 100'],
              datasets: [
                {
                  label: "Username Count",
                  backgroundColor: ["#ff6f6f", "#eeff6f", "#54a732", "#7d8f76", "#900C3F",
                                    "#127e6f", "#d6914f"],
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
                text: 'Keyword Count in Tweets'
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
                        fontSize: 15
                    }
                }]
              }
            }
          });
      });
}
