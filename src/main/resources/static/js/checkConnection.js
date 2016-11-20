var maxToRun = 4;
var running = 0;
var requestQueue = [];

function queueRequest(hn, hp, re) {
    if (running < maxToRun) {
        checkUrlAjaxy(hn, hp, re);
    } else {
        requestQueue.push({hostName: hn, hostPort: hp, resultElement: re});
    }
}

function pollQueue() {
    while (running < maxToRun && requestQueue.length > 0) {
        var requestObj = requestQueue.shift();
        checkUrlAjaxy(requestObj.hostName, requestObj.hostPort, requestObj.resultElement);
    }
}


function checkUrlAjaxy(hostName, hostPort, resultElement) {
    running++;
    resultElement.html('<img src="processing.gif" width="20px"/>');
    $.ajax({

        // The URL for the request
        url: "testconn",

        // The data to send (will be converted to a query string)
        data: {
            host: hostName,
            port: hostPort
        },

        // Whether this is a POST or GET request
        type: "GET",

        // The type of data we expect back
        dataType: "json"
    })

    // Code to run if the request succeeds (is done);
    // The response is passed to the function
        .done(function (json) {
            if (json.success) {
                resultElement.html('<span style="color: green">OK!</span>');
            } else if (json.warning) {
                resultElement.html('<span style="color: yellow">WARN! ' + json.message + '</span>');
            } else {
                resultElement.html('<span style="color: red">ERROR! ' + json.message + '</span>');
            }
            //$.each(json, function(i, field){
            //    resultElement.append(field + " ")
            //});

        })
        // Code to run if the request fails; the raw request and
        // status codes are passed to the function
        .fail(function (xhr, status, errorThrown) {
            resultElement.html('<img src="failed.png" width="20px"/> ' + errorThrown);
            console.log("Error: " + errorThrown);
            console.log("Status: " + status);
            console.dir(xhr);
        })
        // Code to run regardless of success or failure;
        .always(function (xhr, status) {
            running--;
            pollQueue();
        });
}