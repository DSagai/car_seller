var index = 1;

function init() {
    showImage(index);
}


function putNextImage(increment) {
    index += increment;
    showImage(index);
}

function showImage(i) {
    var i;
    var images = document.getElementsByClassName("albumItem");
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
