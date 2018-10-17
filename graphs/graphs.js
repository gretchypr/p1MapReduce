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
    var psv = d3.dsvFormat("\t");
    d3.request("screenames.txt")
      .mimeType("text/plain")
      .response(function(xhr) { return psv.parse(xhr.responseText) })
      .get(function(data) {
          var children = [];
          data[0]["screen_names"] = data[0]["screen_names"].substring(1, data[0]["screen_names"].length-1).split(",");
          index = 0;
          for(var child of data[0]["screen_names"]) {
              children[index] = {name: child, parent: data[0]["count"]};
              index++;
          }
          var treeData = [{name: data[0]["count"], parent: null, children: children}];
      });
      displayReplyChart();
    //  displayUserMessageChart();
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
                  children.push({name:reply.trim(), size:3416});
              }
              root['children'].push({name, children});
          }
      }
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
      for (var i = 0 ; i < data.length; i++) {

          var name = data[i]['screen_name'];
          var temp = data[i]["messages"].split("-");
          var messages = temp[1].substring(1, temp.length-1).split(",");
          var children = [];
          for (var message of messages) {
              children.push({name:message.trim(), size:1000});
          }
          root['children'].push({name, children});

      }
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
