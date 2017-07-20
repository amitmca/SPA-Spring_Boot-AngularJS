$(document).ready(function () {

    $("#user-form").submit(function (event) {

        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

function fire_ajax_submit() {


    $("#btn-create").prop("disabled", true);

    $.ajax({
        type: "POST",
        contentType: "text/html",
        url: "/add",
        data : {'username' : $("#username").val(), 'password' : $("#password").val()},
        cache: false,
        timeout: 600000,
        success: function (data) {

            var json = "<h4>Create Account Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
            $('#feedback').html(json);

            console.log("SUCCESS : ", data);
            $("#btn-create").prop("disabled", false);

        },
        error: function (e) {

            var json = "<h4>Create Account Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-create").prop("disabled", false);

        }
    });

}