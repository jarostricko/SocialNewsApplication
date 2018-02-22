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
    $.getJSON("/creds", function (result) {
      initFB(result.fbAppId);
      getNext();
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
        if (timeLimit !== 0) {
            if (nextPostObject.postType === "youtube#video") {
                clearInterval(interval);
                setTimeout(switchDivsWithYoutube, 1000);
            } else {
                clearInterval(interval);
                FB.XFBML.parse(document.getElementById("video"));
                setTimeout(switchDivsWithVideo, 1000);
            }
            setTimeout(function () {
                console.log("I was waiting: " + timeLimit + " ms.");
                startTimer();
            }, timeLimit)
        } else {
            setTimeout(switchDivs, 1000);
        }
    }, timer)

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
        console.log("displaying div 1");
    } else {
        divs[0].style.display = "none";
        divs[1].style.display = "block";
        console.log("displaying div 2");
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
function showPost(nextPostObject) {
    var postId = nextPostObject.postId;
    var postUrl = nextPostObject.postUrl;
    var postType = nextPostObject.postType;
    var timeLimit = nextPostObject.timeLimit;
    var postHeight = nextPostObject.postHeight;
    var postWidth = nextPostObject.postWidth;
    var video = nextPostObject.video;
    var htmlIframe = nextPostObject.iframeHtml;
    var divToShow;
    if (video) {
        if (postType === "youtube#video") {
            divToShow = document.getElementById("youtube");

            var ytplayer = document.getElementById("ytplayer");
            ytplayer.setAttribute("src","https://www.youtube.com/embed/" + postId +
                "?autoplay=1&origin=http://localhost:8080/&cc_load_policy=0&cc_lang_pref=en&controls=0&mute=1");
            return timeLimit;
            //return 10000;

        } else {
            divToShow = document.getElementById("fb_video");
            divToShow.setAttribute("data-href", "https://www.facebook.com/video.php?v=" + postId);

            if (postHeight > screen.availHeight) divToShow.setAttribute("data-height", screen.availHeight - 50);
            else divToShow.setAttribute("data-height", postHeight - 40);
            //divToShow.setAttribute("data-height", screen.availHeight - 50);
            divToShow.setAttribute("data-width", screen.availWidth-50);

            console.log("return, video: " + timeLimit);
            console.log("width: " + postWidth + " Avail: " + screen.availWidth);
            console.log("height: " + postHeight + " Avail: " + screen.availHeight);
            return timeLimit;
            //return 10000;
        }
    } else {
        divToShow = getHiddenPostDiv();
        $(divToShow).find(".fb-post").attr("data-href", postUrl);
        console.log("return 0");
        FB.XFBML.parse(divToShow);
        return 0;
    }

}
function getNext() {
    var timeLimit = 0;
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            nextPostObject = JSON.parse(this.responseText);
            timeLimit = showPost(nextPostObject);
        }
    };
    xhttp.open("get", "/nextpost", false);
    xhttp.send();
    console.log("timeLimit:" + timeLimit);
    return timeLimit;
}




