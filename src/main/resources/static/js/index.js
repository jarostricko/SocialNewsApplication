/**
 * Created by Jaro on 04.02.2018.
 */
/**----------------------------------------------------------------------------------------------------------
 * Used variables.
 *
 */
var timer = 7000;
var nextPostObject;

/**-------------------------------------------------------------------------------------------------------------
 *  On page Ready method.
 */
$(document).ready(function () {
    $.getJSON("/config", function (result) {
        timer = result.postTime * 1000;
    });

    $.getJSON("/creds", function (result) {
        initFB(result.fbAppId);
        //getNext();
        startTimer();
    });
    $("#post2").hide();
    $("#video").hide();
    $("#youtube").hide();
});

function initFB(fbAppId) {
    FB.init({
        fbAppId: fbAppId,
        autoLogAppEvents: true,
        xfbml: true,
        version: 'v2.10'
    });
    //FB.AppEvents.logPageView();
}

function startTimer() {
    var interval = setInterval(function () {
        var timeLimit = getNext();
        if (nextPostObject.video) {
            if (nextPostObject.postType === "youtube#video") {
                clearInterval(interval);
                setTimeout(switchDivsWithYoutube, 1000);
            } else {
                clearInterval(interval);
                //FB.XFBML.parse(document.getElementById("video"));
                setTimeout(switchDivsWithVideo, 1000);
            }
            setTimeout(function () {
                startTimer();
            }, timeLimit)
        } else {
            setTimeout(switchDivs, 1000);
        }
    }, timer);
}

function switchDivsWithVideo() {
    $("#youtube").hide();
    $("#post1").hide();
    $("#post2").hide();

    $("#video").show();
}

function switchDivsWithYoutube() {
    $("#post1").hide();
    $("#post2").hide();
    $("#video").hide();

    $("#youtube").show();
}

function switchDivs() {
    $("#video").hide();
    $("#youtube").hide();
    var divs = document.getElementsByClassName("post");
    if (divs[0].style.display === "none") {
        divs[1].style.display = "none";
        divs[0].style.display = "block";
    } else {
        divs[0].style.display = "none";
        divs[1].style.display = "block";
    }
}

function getHiddenPostDiv() {
    var divs = document.getElementsByClassName("post");
    for (var i = 0; i < divs.length; i = i + 1) {
        if (divs[i].style.display === "none") {
            return divs[i];
        }
    }

}

function showPost() {
    var divToShow;
    if (nextPostObject.video) {
        if (nextPostObject.postType === "youtube#video") {
            divToShow = document.getElementById("youtube");

            var ytplayer = document.getElementById("ytplayer");
            ytplayer.setAttribute("src", "https://www.youtube.com/embed/" + nextPostObject.postId +
                "?autoplay=1&origin=http://localhost:8080/&cc_load_policy=0&cc_lang_pref=en&controls=0&mute=1");
            return nextPostObject.timeLimit;

        } else {
            divToShow = document.getElementById("fb_video");
            divToShow.setAttribute("data-href", "https://www.facebook.com/video.php?v=" + nextPostObject.postId);

            divToShow.setAttribute("data-width", screen.availWidth - 50);
            //if (nextPostObject.postHeight >= screen.availHeight) divToShow.setAttribute("data-height", screen.availHeight - 50);
            //else divToShow.setAttribute("data-height", nextPostObject.postHeight - 40);
            divToShow.setAttribute("data-height", screen.availHeight - 80);

            FB.XFBML.parse(document.getElementById("video"));
            return nextPostObject.timeLimit;
        }
    } else {
        divToShow = getHiddenPostDiv();
        $(divToShow).find(".fb-post").attr("data-href", nextPostObject.postUrl);
        FB.XFBML.parse(divToShow);
        //divToShow.style.transform = "scale(1.3,1.3)";
        return nextPostObject.timeLimit;
    }

}

function getNext() {
    var timeLimit = 0;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            nextPostObject = JSON.parse(this.responseText);
            timeLimit = showPost();
        }
    };
    xhttp.open("get", "/nextpost", false);
    xhttp.send();
    console.log("timeLimit: " + timeLimit / 1000 + " seconds, type: " + nextPostObject.postType);
    return timeLimit;
}




