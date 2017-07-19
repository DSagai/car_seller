var req;
var csrf;

function init() {
    csrf = document.getElementsByName("_csrf")[0].value;
}

/**
 * method returns initialized XMLHttpRequest
 * @returns XMLHttpRequest
 */
function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
}

/**
 * response catcher for removeImageRequest.
 */
function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            parseResult(req.responseText);
        } else {
            alert("Unable remove image. Try again later.")
        }
    }
}

function parseResult(response) {
    var elem = document.getElementById("result");
    elem.innerHTML = response;
}

function filterButtonClick() {
    var url = "getAdvList";
    req = initRequest();
    var data = new FormData();
    data.append("searchParams", prepareJSON());
    data.append("_csrf", csrf);
    data.dataType = "json";
    req.open("POST", url, true);
    req.onreadystatechange = callback;
    req.send(data);
}

function prepareJSON() {
    var searchParams = document.getElementsByClassName("searchParam");
    var paramArray = new Array();

    for (i = 0; i < searchParams.length; i++) {
        if (searchParams[i].value != "") {
            var prefix = searchParams[i].hasAttribute("prefix") ? searchParams[i].getAttribute("prefix") : "";
            var suffix = searchParams[i].hasAttribute("suffix") ? searchParams[i].getAttribute("suffix") : "";
            var searchParam = {
                fieldName : searchParams[i].getAttribute("name"),
                restriction : searchParams[i].getAttribute("restriction"),
                value : prefix + searchParams[i].value + suffix
            };
            paramArray.push(searchParam);
        }
    }

    return JSON.stringify(paramArray);
}