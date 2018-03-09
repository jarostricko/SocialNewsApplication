/**
 * Created by Jaro on 02.02.2018.
 */
$(document).ready(function () {
    /**---------------------------------------------------------------------------------------------------
     * Prepare data on PAGE START
     */
    var config = {
        fbNumberOfPostsToFetch: 0, ytNumberOfPostsToFetch: 0, oldMaxDays: 0,
        newestFirst: false, mostPopularFirst: false,
        facebookPostsOn: false, facebookVideosOn: false,
        youtubeBasedOnChannelsOn: false, youtubeBasedOnQueryTermsOn: false,
        videoOrder: "", postTime: 0
    };
    $.getJSON("/config", function (result) {
        $("#fbTimeTd").find("input").val(result.postTime);
        $("#fbNumberTd").find("input").val(result.fbNumberOfPostsToFetch);
        $("#ytNumberTd").find("input").val(result.ytNumberOfPostsToFetch);
        $("#maxDaysTd").find("input").val(result.oldMaxDays);
        $("#orderTd").find("#selectOrder").val(result.videoOrder);
        if (result.newestFirst) $("#byDateRadio").prop('checked', true);
        else $("#byDateRadio").prop('checked', false);
        if (result.mostPopularFirst) $("#byPopularityRadio").prop('checked', true);
        else $("#byPopularityRadio").prop('checked', false);
        if (result.facebookPostsOn) $("#fbPostOnCh").prop('checked', true);
        else $("#fbPostOnCh").prop('checked', false);
        if (result.facebookVideosOn) $("#fbVideoOnCh").prop('checked', true);
        else $("#fbVideoOnCh").prop('checked', false);
        if (result.youtubeBasedOnChannelsOn) $("#ytchonCh").prop('checked', true);
        else $("#ytchonCh").prop('checked', false);
        if (result.youtubeBasedOnQueryTermsOn) $("#ytkonCh").prop('checked', true);
        else $("#ytkonCh").prop('checked', false);

        config.postTime = result.postTime;
        config.fbNumberOfPostsToFetch = result.fbNumberOfPostsToFetch;
        config.ytNumberOfPostsToFetch = result.ytNumberOfPostsToFetch;
        config.oldMaxDays = result.oldMaxDays;
        config.videoOrder = result.videoOrder;
        config.newestFirst = result.newestFirst;
        config.mostPopularFirst = result.mostPopularFirst;
        config.facebookPostsOn = result.facebookPostsOn;
        config.facebookVideosOn = result.facebookVideosOn;
        config.youtubeBasedOnChannelsOn = result.youtubeBasedOnChannelsOn;
        config.youtubeBasedOnQueryTermsOn = result.youtubeBasedOnQueryTermsOn;
    });

    /**-----------------------------------------------------------------------------------------------
     * Getting FACEBOOK pages.
     */
    $("#fbpagerefresh,#fbPageTabBtn").click(function () {
        $("#fbpagestablebody").empty();
        $.getJSON("/fbpage", function (result) {
            $.each(result, function (i, field) {
                $("#fbpagestablebody").append("<tr><td class='idColumns'>" + result[i].id +
                    "</td><td class=\"editableColumns\" inputtype='text' id='fbpagenametd'>" + result[i].pageName +
                    "</td><td class=\"editableColumns\" inputtype='text' id='fbpageidtd'>" + result[i].pageId +
                    "</td><td class=\"editableColumns\" inputtype='checkbox' id='fbpageactivetd'>" + result[i].active +
                    "</td><td>" + "<button id='btEditFb' type='button' class='btn btn-warning btn-sm'>Edit</button> " +
                    "<button style='display: none' id='btSaveFb' type='button' class='btn btn-danger btn-sm'>Save</button>" +
                    "<button id='btDeleteFb' type='button' class='btn btn-dark btn-sm'>Delete</button></td></tr>")
            })
        })
    });

    /**------------------------------------------------------------------------------------------
     * Getting YOUTUBE channels.
     */
    $("#ytchannelrefresh,#ytChannelTabBtn").click(function () {
        $("#ytchanneltablebody").empty();
        $.getJSON("/ytchannel", function (result) {
            $.each(result, function (i, field) {
                $("#ytchanneltablebody").append("<tr><td class='idColumns'>" + result[i].id +
                    "</td><td class=\"editableColumns\" inputtype='text' id='ytchnametd'>" + result[i].channelName +
                    "</td><td class=\"editableColumns\" inputtype='text' id='ytchidtd'>" + result[i].channelId +
                    "</td><td class=\"editableColumns\" inputtype='text' id='ytchkeywordstd'>" + result[i].keywords +
                    "</td><td class=\"editableColumns\" inputtype='checkbox' id='ytchactivetd'>" + result[i].active +
                    "</td><td>" + "<button id='btEditYt' type='button' class='btn btn-warning btn-sm'>Edit</button> " +
                    "<button style='display: none' id='btSaveYt' type='button' class='btn btn-danger btn-sm'>Save</button>" +
                    "<button id='btDeleteYt' type='button' class='btn btn-dark btn-sm'>Delete</button></td></tr>")
            })
        })
    });
    /**--------------------------------------------------------------------------------------------------------------
     * Getting CREDENTIALS data.
     */
    $("#credentialsrefresh,#credentialsTabBtn").click(function () {
        $.getJSON("/creds", function (result) {
            $("#appIdTd").html(result.fbAppId);
            $("#appSecTd").html(result.fbAppSecret);
            $("#apiKeyTd").html(result.youTubeApiKey);
        })
    });

    /**----------------------------------------------------------------------------------------------------------------------------------
     * Get VOTES RESULTS
     */
    $("#votesTabBtn").click(function () {
        $("#postVotesTableBody").empty();
        $("#videoVotesTableBody").empty();
        $("#ytVideoVotesTableBody").empty();
        $.getJSON("/getVotes",function (result) {
            $.each(result, function (i, field) {
                if (result[i].postType === "fb#post"){
                    $("#postVotesTableBody").append("<tr>" +
                        "<td>"+ result[i].postId +"</td>" +
                        "<td>"+ result[i].liked +"</td>" +
                        "<td>"+ result[i].disliked +"</td>" +
                        "</tr>")
                }else {
                    if (result[i].postType === "fb#video"){
                        $("#videoVotesTableBody").append("<tr>" +
                            "<td>"+ result[i].postId +"</td>" +
                            "<td>"+ result[i].liked +"</td>" +
                            "<td>"+ result[i].disliked +"</td>" +
                            "</tr>")
                    }
                    else {
                        $("#ytVideoVotesTableBody").append("<tr>" +
                            "<td>"+ result[i].postId +"</td>" +
                            "<td>"+ result[i].liked +"</td>" +
                            "<td>"+ result[i].disliked +"</td>" +
                            "</tr>")
                    }
                }
            })
        })
    });

    /**--------------------------------------------------------------------------------------------------------------
     * Getting CONFIGURATION data.
     */
    $("#configrefresh,#confTabBtn").click(function () {
        $.getJSON("/config", function (result) {
            $("#fbTimeTd").find("input").val(result.postTime);
            $("#fbNumberTd").find("input").val(result.fbNumberOfPostsToFetch);
            $("#ytNumberTd").find("input").val(result.ytNumberOfPostsToFetch);
            $("#maxDaysTd").find("input").val(result.oldMaxDays);
            $("#orderTd").find("#selectOrder").val(result.videoOrder);
            if (result.newestFirst) $("#byDateRadio").prop('checked', true);
            else $("#byDateRadio").prop('checked', false);
            if (result.mostPopularFirst) $("#byPopularityRadio").prop('checked', true);
            else $("#byPopularityRadio").prop('checked', false);
            if (result.facebookPostsOn) $("#fbPostOnCh").prop('checked', true);
            else $("#fbPostOnCh").prop('checked', false);
            if (result.facebookVideosOn) $("#fbVideoOnCh").prop('checked', true);
            else $("#fbVideoOnCh").prop('checked', false);
            if (result.youtubeBasedOnChannelsOn) $("#ytchonCh").prop('checked', true);
            else $("#ytchonCh").prop('checked', false);
            if (result.youtubeBasedOnQueryTermsOn) $("#ytkonCh").prop('checked', true);
            else $("#ytkonCh").prop('checked', false);

            config.postTime = result.postTime;
            config.fbNumberOfPostsToFetch = result.fbNumberOfPostsToFetch;
            config.ytNumberOfPostsToFetch = result.ytNumberOfPostsToFetch;
            config.oldMaxDays = result.oldMaxDays;
            config.videoOrder = result.videoOrder;
            config.newestFirst = result.newestFirst;
            config.mostPopularFirst = result.mostPopularFirst;
            config.facebookPostsOn = result.facebookPostsOn;
            config.facebookVideosOn = result.facebookVideosOn;
            config.youtubeBasedOnChannelsOn = result.youtubeBasedOnChannelsOn;
            config.youtubeBasedOnQueryTermsOn = result.youtubeBasedOnQueryTermsOn;
        });
    });

    /**-----------------------------------------------------------------------------------------------------
     * Edit CONFIGURATION
     */
    $("body").on("click", "button.editConf", function () {
        var td = $(this).parents('tr').find('td.editableColumns');
        var id = $(td)[0].getAttribute("id");
        if (id === 'fbTimeTd' || id === 'fbNumberTd' || id === 'ytNumberTd' || id === 'maxDaysTd') {
            $(td).find('button').each(function () {
                $(this).prop('disabled', false);
            });
            $(td).find('input').prop('disabled', false);
        } else if (id === 'orderTd') {
            $('#selectOrder').prop('disabled', false);
        } else {
            $(td).find('input').each(function () {
                $(this).prop('disabled', false);
            });
        }
        $(this).parents('tr').find("button.saveConf").show();
        $(this).hide();
    });

    /**------------------------------------------------------------------------------------------------
     * Save CONFIGURATION.
     */
    $("body").on("click", "button.saveConf", function () {
        var saveBtn = this;
        var td = $(this).parents('tr').find('td.editableColumns');
        var id = $(td)[0].getAttribute("id");
        switch (id) {
            case 'fbTimeTd':
                config.postTime = $(td).find('input').val();
                break;
            case 'fbNumberTd':
                config.fbNumberOfPostsToFetch = $(td).find('input').val();
                break;
            case 'ytNumberTd':
                config.ytNumberOfPostsToFetch = $(td).find('input').val();
                break;
            case 'maxDaysTd':
                config.oldMaxDays = $(td).find('input').val();
                break;
            case 'orderTd':
                config.videoOrder = $(td).find('select').val();
                break;
            case 'sortByTd':
                if ($("#byDateRadio").is(":checked")) {
                    config.newestFirst = true;
                    config.mostPopularFirst = false;
                }else {
                    config.newestFirst = false;
                    config.mostPopularFirst = true;
                }
                break;
            case 'fbPostOnTd':
                if ($("#fbPostOnCh").is(":checked")) config.facebookPostsOn = true;
                else config.facebookPostsOn = false;
                break;
            case 'fbVideoOnTd':
                if ($("#fbVideoOnCh").is(":checked")) config.facebookVideosOn = true;
                else config.facebookVideosOn = false;
                break;
            case 'ytchonTd':
                if ($("#ytchonCh").is(":checked")) config.youtubeBasedOnChannelsOn = true;
                else config.youtubeBasedOnChannelsOn = false;
                break;
            case 'ytkonTd':
                if ($("#ytkonCh").is(":checked")) config.youtubeBasedOnQueryTermsOn = true;
                else config.youtubeBasedOnQueryTermsOn = false;
                break;
        }
        $.ajax({
            url: "/config",
            type: "POST",
            data: JSON.stringify(config),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                alert("Configuration was edited. Status: " + status);

                if (id === 'fbTimeTd' || id === 'fbNumberTd' || id === 'ytNumberTd' || id === 'maxDaysTd') {
                    $(td).find('button').each(function () {
                        $(this).prop('disabled', true);
                    });
                    $(td).find('input').prop('disabled', true);
                } else if (id === 'orderTd') {
                    $('#selectOrder').prop('disabled', true);
                } else {
                    $(td).find('input').each(function () {
                        $(this).prop('disabled', true);
                    });
                }
                $(saveBtn).parents('tr').find("button.editConf").show();
                $(saveBtn).hide();
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });

    });

    /**----------------------------------------------------------------------------------------------
     * Edit FACEBOOK page
     */
    $("body").on("click", "#btEditFb", function () {
        $(this).parents('tr').find('td.editableColumns').each(function () {
            var html = $(this).html();
            if (this.getAttribute("inputtype") === 'text') {
                var input = $('<input class="editableColumnsStyle" type="text" />');
                input.val(html);
            }
            else {
                if (html === "true") var input = $('<input class="editableColumnsStyle" type="checkbox" checked />');
                else var input = $('<input class="editableColumnsStyle" type="checkbox" />');
            }
            $(this).html(input);
        });
        $(this).hide();
        $(this).parents('tr').find("#btSaveFb").show();
    });

    /**- ------------------------------------------------------------------------------------------------
     * Edit YOUTUBE channel.
     */
    $("body").on("click", "#btEditYt", function () {
        $(this).parents('tr').find('td.editableColumns').each(function () {
            var html = $(this).html();
            if (this.getAttribute("inputtype") === 'text') {
                var input = $('<input class="editableColumnsStyle" type="text" />');
                input.val(html);
            }
            else {
                if (html === "true") var input = $('<input class="editableColumnsStyle" type="checkbox" checked />');
                else var input = $('<input class="editableColumnsStyle" type="checkbox" />');
            }
            $(this).html(input);
        });
        $(this).hide();
        $(this).parents('tr').find("#btSaveYt").show();
    });


    /**-----------------------------------------------------------------------------------------------------
     * Save FACEBOOK page.
     */
    $("body").on("click", "#btSaveFb", function () {
        var saveBtn = this;
        var page = {
            id: $(this).parents('tr').find('.idColumns').html(),
            pageName: $(this).parents('tr').find('#fbpagenametd').find('input').val(),
            pageId: $(this).parents('tr').find('#fbpageidtd').find('input').val(),
            active: false
        };
        if ($(this).parents('tr').find('#fbpageactivetd').find('input').is(":checked")) {
            page.active = true;
        }
        $.ajax({
            url: "/fbpage",
            type: "POST",
            data: JSON.stringify(page),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                alert("Facebook page was edited. Status: " + status);
                $(saveBtn).parents('tr').find('#fbpagenametd').html(page.pageName);
                $(saveBtn).parents('tr').find('#fbpageidtd').html(page.pageId);
                $(saveBtn).parents('tr').find('#fbpageactivetd').html(page.active.toString());
                $(saveBtn).hide();
                $(saveBtn).parents('tr').find("#btEditFb").show();
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });

    /**-------------------------------------------------------------------------------------------------------------
     * Save YOUTUBE channel.
     */
    $("body").on("click", "#btSaveYt", function () {
        var saveBtn = this;
        var channel = {
            id: $(this).parents('tr').find('.idColumns').html(),
            channelName: $(this).parents('tr').find('#ytchnametd').find('input').val(),
            channelId: $(this).parents('tr').find('#ytchidtd').find('input').val(),
            keywords: $(this).parents('tr').find('#ytchkeywordstd').find('input').val(),
            active: false
        };
        if ($(this).parents('tr').find('#ytchactivetd').find('input').is(":checked")) {
            channel.active = true;
        }
        $.ajax({
            url: "/ytchannel",
            type: "POST",
            data: JSON.stringify(channel),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                alert("YouTuba channel was edited. Status: " + status);
                $(saveBtn).parents('tr').find('#ytchnametd').html(channel.channelName);
                $(saveBtn).parents('tr').find('#ytchidtd').html(channel.channelId);
                $(saveBtn).parents('tr').find('#ytchkeywordstd').html(channel.keywords);
                $(saveBtn).parents('tr').find('#ytchactivetd').html(channel.active.toString());
                $(saveBtn).hide();
                $(saveBtn).parents('tr').find("#btEditYt").show();
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });

    /**------------------------------------------------------------------------------------------------------
     * Delete FACEBOOK page.
     */
    $("body").on("click", "#btDeleteFb", function () {
        var delButton = this;
        var page = {
            id: $(this).parents('tr').find('.idColumns').html(),
            pageName: "",
            pageId: "",
            active: false
        };
        $.ajax({
            url: "/fbpage",
            type: "DELETE",
            data: JSON.stringify(page),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                alert("Facebook page was Deleted. Status: " + status);
                $(delButton).parents('tr').remove();
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });

    /**------------------------------------------------------------------------------------------------------
     * Delete YOUTUBE channel.
     */
    $("body").on("click", "#btDeleteYt", function () {
        var delButton = this;
        var channel = {
            id: $(this).parents('tr').find('.idColumns').html(),
            channelName: "",
            channelId: "",
            keywords: "",
            active: false
        };
        $.ajax({
            url: "/ytchannel",
            type: "DELETE",
            data: JSON.stringify(channel),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                alert("YouTube channel was Deleted. Status: " + status);
                $(delButton).parents('tr').remove();
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });

    /**-----------------------------------------------------------------------------------------------------------
     * Create new FACEBOOK page.
     */
    $("#insertfbp").click(function () {
        var page = {
            pageName: $("#fbnameinput").val(),
            pageId: $("#fbidinput").val(),
            active: false
        };
        if ($("#fbactiveinput").is(":checked")) {
            page.active = true;
        }
        $.ajax({
            url: "/fbpage",
            type: "PUT",
            data: JSON.stringify(page),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                $("#fbnameinput").val("");
                $("#fbidinput").val("");
                alert("Facebook page was uploaded. Status: " + status);
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });

    /**-----------------------------------------------------------------------------------------------------------
     * Create new YOUTUBE channel.
     */
    $("#insertytch").click(function () {
        var channel = {
            channelName: $("#ytnameinput").val(),
            channelId: $("#ytidinput").val(),
            keywords: $("#ytkeyinput").val(),
            active: false
        };
        if ($("#ytactiveinput").is(":checked")) {
            channel.active = true;
        }
        $.ajax({
            url: "/ytchannel",
            type: "PUT",
            data: JSON.stringify(channel),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                $("#ytnameinput").val("");
                $("#ytidinput").val("");
                $("#ytkeyinput").val("")
                alert("YouTube channel was uploaded. Status: " + status);
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });


    /**------------------------------------------------------------------------------------------------------------------
     * Edit CREDENTIALS data.
     */
    $("body").on("click", "button.editCred", function () {
        var tdEl = $(this).parents('tr').find('td.editableColumns')
        var html = $(tdEl).html();
        var input = $('<input class="editableColumnsStyle" type="text" />');
        input.val(html);
        $(tdEl).html(input);
        $(this).hide();
        $(this).parent().find("button.saveCred").show();
    });

    /**---------------------------------------------------------------------------------------------------------------------
     * Save CREDENTIALS data.
     */
    $("body").on("click", "button.saveCred", function () {
        let saveBtn = this;
        var appID = "";
        var appSec = "";
        var apiKey = "";
        let appIdEl = $("#appIdTd");
        let appSecEl = $("#appSecTd");
        let apiKeyEl = $("#apiKeyTd");

        var currantClass = $(saveBtn).parents('tr')[0].getAttribute("class");
        switch (currantClass) {
            case "appId" :
                appID = appIdEl.find("input").val();
                appSec = appSecEl.html();
                apiKey = apiKeyEl.html();
                break;
            case "appSec" :
                appID = appIdEl.html();
                appSec = appSecEl.find("input").val();
                apiKey = apiKeyEl.html();
                break;
            case "apiKey" :
                appID = appIdEl.html();
                appSec = appSecEl.html();
                apiKey = apiKeyEl.find("input").val();
                break;
        }
        var creds = {
            fbAppId: appID,
            fbAppSecret: appSec,
            youTubeApiKey: apiKey
        };
        $.ajax({
            url: "/creds",
            type: "POST",
            data: JSON.stringify(creds),
            contentType: "application/json; charset=utf-8",
            success: function (data, status) {
                alert("Credentials was edited. Status: " + status);
                $("#appIdTd").html(creds.fbAppId);
                $("#appSecTd").html(creds.fbAppSecret);
                $("#apiKeyTd").html(creds.youTubeApiKey);
                $(saveBtn).hide();
                $(saveBtn).parent().find("button.editCred").show();
            },
            failure: function () {
                alert("Server problem, try later.");
            }
        });
    });
    /**--------------------------------------------------------------------------------------------------------------------
     * Spinner days FUNCTION.
     */
    $(function () {
        var action;
        $(".number-spinner button").mousedown(function () {
            btn = $(this);
            input = btn.closest('.number-spinner').find('input');
            btn.closest('.number-spinner').find('button').prop("disabled", false);

            if (btn.attr('data-dir') == 'up') {
                action = setInterval(function () {
                    if (input.attr('max') == undefined || parseInt(input.val()) < parseInt(input.attr('max'))) {
                        input.val(parseInt(input.val()) + 1);
                    } else {
                        btn.prop("disabled", true);
                        clearInterval(action);
                    }
                }, 50);
            } else {
                action = setInterval(function () {
                    if (input.attr('min') == undefined || parseInt(input.val()) > parseInt(input.attr('min'))) {
                        input.val(parseInt(input.val()) - 1);
                    } else {
                        btn.prop("disabled", true);
                        clearInterval(action);
                    }
                }, 50);
            }
        }).mouseup(function () {
            clearInterval(action);
        });
    });


});