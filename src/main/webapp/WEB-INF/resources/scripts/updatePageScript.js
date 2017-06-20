//slideshow part

var index = 1;
var advId;
var req;
var csrf;
var imageContainer;

/**
 * function runs when adv-update form starts.
 * Assignment of the function is to init script variables and launch service functions.
 */
function init() {

    var fileInput = document.getElementById("fileUploader");
    fileInput.addEventListener("change", addImageRequest);
    advId = fileInput.getAttribute("advId");
    csrf = document.getElementsByName("_csrf")[0].value;
    imageContainer = document.getElementById("imageContainer");
    // for (i = 0; i < imageList.length; i++) {
    loadImageListRequest();
}


/**
 * method puts new img element on the form.
 * @param id
 */
function addImageLink(id) {
    var img = document.createElement("img");
    var div = document.createElement("div");
    var delButton = document.createElement("button");

    img.setAttribute("src","albumPhoto.jpg?id=" + id);

    delButton.setAttribute("imgId", id);
    delButton.setAttribute("onClick", "removeImageClick(this)");
    delButton.setAttribute("class", "w3-button w3-white w3-display-middle");
    delButton.innerHTML = "Remove image";

    div.setAttribute("class", "albumItem");
    div.setAttribute("id", "imgId" + id);
    div.style.display = "none";

    div.appendChild(delButton);
    div.appendChild(img);
    imageContainer.appendChild(div);
}


function removeImageClick(button) {
    if (confirm("Do you really want to remove this image?") == true) {
        removeImageRequest(button.getAttribute("imgId"));
    }
}

/**
 * request for show next image.
 * @param increment - shows increment direction.
 */
function iterateNextImage(increment) {
    index += increment;
    showImage(index);
}

/**
 * method shows image with requested index.
 * @param i - index of image after adding increment.
 */
function showImage(i) {
    var i;
    var images = document.getElementsByClassName("albumItem");
    if (images.length > 0) {
        if (i > images.length) {
            index = 1;
        } else if (i < 1) {
            index = images.length;
        }
        for (i = 0; i < images.length; i++) {
            images[i].style.display = "none";
        }
        images[index - 1].style.display = "block";
    }
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
 * function sends to server request for list containing id's of
 * albumItems connected to the current advertisement.
 */
function loadImageListRequest() {
    var url = "get-images-list";
    var params = "id=" + advId + "&_csrf=" + csrf;
    req = initRequest();
    req.open("post", url, true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.onreadystatechange = callbackLoadImages;
    req.send(params);
}

/**
 * function sends to server remove image request.
 * @param id of album item.
 */
function removeImageRequest(id) {
    var url = "remove-album-item";
    var params = "id=" + id + "&_csrf=" + csrf;
    req = initRequest();
    req.open("post", url, true);
    req.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    req.onreadystatechange = callbackRemoveImage;
    req.send(params);
}

/**
 * function try to upload new albumItem.
 * @param event - change of input file element.
 */
function addImageRequest(event) {
    var url = "upload-file";
    var file = event.target.files[0];
    req = initRequest();
    var data = new FormData();
    data.append("picture",file, file.name);
    data.append("id", advId);
    data.append("_csrf", csrf);

    req.open("POST", url, true);
    req.onreadystatechange = callbackUpload;
    req.send(data);
}

/**
 * response catcher for removeImageRequest.
 */
function callbackRemoveImage() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            removeImage(req.responseText);
        } else {
            alert("Unable remove image. Try again later.")
        }
    }
}

/**
 * method removes image from the form, after response was received,
 * @param id of albumItem.
 */
function removeImage(id) {
    var removable = document.getElementById("imgId"+id);
    imageContainer.removeChild(removable);
    showImage(index);
}

/**
 * response catcher for loadImageListRequest.
 */
function callbackLoadImages() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            showImagesList(req.responseText);
        } else {
            alert("Unable load image list.");
        }
    }
}

/**
 * process output of list of albumItems.
 * @param responseText
 * @returns {boolean}
 */
function showImagesList(responseText) {
    if (responseText == null) {
        return false;
    } else {
        var images = JSON.parse(responseText);
        if (images.length > 0) {
            for (i = 0; i < images.length; i++){
                addImageLink(images[i]);
            }
        }
    }
    showImage(index);
}

/**
 * response catcher for addImageRequest.
 */
function callbackUpload() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            addImageLink(req.responseText);
            showImage(index);
        }
    }
}


