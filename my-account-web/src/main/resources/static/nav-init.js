window.onload = function(){
    activeLeftMenu();
};

function activeLeftMenu() {
    var href = window.location.pathname;
    href = href && href != "/" ? href : "/home";

    $("#sidebar li").removeClass("active");
    var ativeA = $("#sidebar li").find("a[href='" + href + "']");

    if(!ativeA || ativeA.length != 1) {
        return;
    }

    ativeA = ativeA[0];
    $(ativeA).parent('li').addClass("active");
}