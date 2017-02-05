var height = window.innerHeight;
var width = window.innerWidth;
var pForm;
var pSearch;
var pCon;
var pText;

function onLoadFunction(){
    $("#advanced").hide();
    $("#manage").hide();
}

function optionFunction(){
    $("#advanced").show(1000);
    $("#manage").show(1000);
    $("#setting").hide(1000);
}

function manageFunction(){
    $("#advanced").hide(1000);
    $("#manage").hide(1000);
    $("#setting").show(1000);
    window.open("manage.html");
}

function advancedFunction(){
    $("#advanced").hide(1000);
    $("#manage").hide(1000);
    $("#setting").show(1000);
    window.open("searchplus.html");
}

function changeFunction(){
    if ($("#selectt").val() == 'insert'){
        $("#selectt").val('search');
        $("#labelChange").html("Search");
        $("#insert").hide();
        $("#iUpload").hide();
    }
    else{
        $("#selectt").val('insert');
        $("#labelChange").html("Insert");
        $("#insert").css("visibility","visible");
        $("#iUpload").css("visibility","visible");
        $("#insert").show();
        $("#iUpload").show();
    }        
}


function myFunction(){
    if ($("#select").val() == 'eng'){
        $("#select").slideUp(800,function(){
          $("#select").css("background-color", "#d9534f");
          $("#select").css("border-color","#d43f3a");
          $("#select").val('vie');
          $("#labell").html("V");  
          $("#select").slideDown(800);       
        });
    }
    else{
        $("#select").slideUp(800,function(){
          $("#select").css("background-color", "#428bca");
          $("#select").css("border-color","#357ebd");
        $("#select").val('eng');
        $("#labell").html("E"); 
        $("#select").slideDown(800);       
        });
    }        
}

function Edit(obj){
    $(obj).replaceWith(function () {
      return '<h3 class="navbar-form navbar-search" id="remove"><div class="input-group"><input type="text" onblur="deleteFunction()" id="editValue" style="text-align: center; color: white; background-color:rgba(0, 0, 0, 0.3); border:0" value="'+$(this).text()+'" name="'+$(this).text()+'"><div class="input-group-btn"><button type="button" class="btn btn-success btn-sm" id="upload" onclick="editFunction()" value="edit"><span class="glyphicon glyphicon-circle-arrow-up" id="icUpload"></span></button></div></div></h3>';
    });
    $("#editValue").focus();
}
                        
function insertUpload(){
        $.ajax({
                url : "ServerEngine",
                type : "POST",
                data : {
                        search : $("#search").val(),
                        meaning : $("#insert").val(),
                        cmd : 'insert',
                        mean : $("#select").val(),
                },
                success : function(data) {
                         response(data);
                }
        })     
}

function deleteFunction(){
    var checkk = confirm('Dữ liệu chưa được cập nhật, bạn có muốn cập nhật không ?');
    if(checkk == true){editFunction();}
    else {    var xx = $("#editValue").attr('name');
    $("#remove").remove();
    $("#con").append('<li ondblclick="Edit(this)"><h3>'+xx +"</h1></li>");
    }
}

function editFunction(){
        $("#upload").attr("src" , "loading.gif");
        $.ajax({
                url : "ServerEngine",
                type : "POST",
                data : {
                        search : $("#search").val(),
                        oldMean: $("#editValue").attr('name'),
                        meaning : $("#editValue").val(),
                        mean : $("#select").val(),
                        cmd : 'edit',
                },
                success : function(data) {
                         response(data);
                         console.log("aa");
                },
                error : function(data) {
                    var xx = $("#editValue").val();
                        $("#remove").remove();
                        $("#con").append('<li ondblclick="Edit(this)"><h3>'+xx +"</h1></li>");
                },
        })
}
// $("#eng").hover(function(){
//     $("#eng").css("background-color", "green");
//     }, function(){
//     $("#eng").css("background-color", "green");
// });

// $("#vie").hover(function(){
//     $(this).css("background-color", "red");
//     }, function(){
//     $(this).css("background-color", "red");
// });

$(document).ready(function() {
        $(function() {
                $("#search").autocomplete({     
                        source : function(request, response) {
                                $.ajax({
                                        url : "ServerEngine",
                                        type : "POST",
                                       
                                        data : {
                                                search : request.term,
                                                cmd : 'autocomplete',
                                                mean : $("#select").val(),
                                        },
                                        success : function(data) {
                                                 response(data);
                                        },
                                        error : function(data){
                                            var rs = jQuery.parseJSON('[{"label" : "' + request.term + '"}]');
                                            response(rs);
                                        }
                                })
                        },
                        // focus: function( event, ui ) {
                        //         $( "#search" ).val( ui.item.search );
                        //         return false;
                        // },
                        select: function( event, ui){
                                $( "#search" ).val( ui.item.label);
                                        $.ajax({
                                                url : "ServerEngine",
                                                type : "POST",
                                                data : {
                                                        search : $("#search").val(),
                                                        cmd : 'search',
                                                        mean : $("#select").val(),
                                                },
                                                success : function(data) {
                                                        // var response = jQuery.parseJSON(data);
                                                        // $("#result").val(data[0].mean); 
                                                        $("#con").html("");  
                                                        $(data).each(function(index, item){
                                                                $("#con").append('<li ondblclick="Edit(this)"><h3>'+item.mean +"</h1></li>");
                                                        })
                                                }
                                        })  
                        }
                });
        });
});

