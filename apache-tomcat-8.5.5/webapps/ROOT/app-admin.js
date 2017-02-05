        var height = window.innerHeight;
        var width = window.innerWidth;
        var pForm;
        var pSearch;
        var pCon;
        var pText;
// window.onload = function(){
// //         pForm = document.getElementById('form');
//         pForm1= document.getElementById('form1');
// //         pDesign = document.getElementById('design');
//         pText = document.getElementById('text'); 
//         // pForm1.style.width =  pText.style.width;
//         pSearch= document.getElementById('search');
//         pCon = document.getElementById('con');
//         // pCon.style.left = pSearch.style.left;
// //         // pDesign.style.height = height/9 + 'px';     
// //         // pDesign.style.width = height/9 + 'px';   
// //         // pDesign.style.left = '0 px';
// //         // pDesign.style.top = height*8/9 + 'px';
// //         pForm.style.left = width/3 + 'px';
// //         pForm.style.top = height/4 + 'px';
// //         pForm.style.width = width/3 + 'px';
// //         pForm.style.height = height/4 + 'px';
// //         pSearch.style.width =  width/3 + 'px';

// //         pText.style.height = height/14 + 'px';
// //         pText.style.width =  width/3 + 'px';
// //         //pText.style.fontSize =  height*width/14000 + 'px';
// //         //pText.style.textAlign = "center";
// };
// $( window ).resize(function() {
        // pForm1.style.width =  pText.style.width;
        // pCon.style.left = pSearch.style.left;
//         var rHeight = window.innerHeight;
//         var rWidth = window.innerWidth;
//         // pDesign.style.height = height/9 + 'px';     
//         // pDesign.style.width = height/9 + 'px';   
//         // pDesign.style.left = '0 px';
//         // pDesign.style.top = height*8/9 + 'px';
//         pForm.style.left = width/3 + 'px';
//         pForm.style.top = height/4 + 'px';
//         pForm.style.width = width/3 + 'px';
//         pForm.style.height = height/4 + 'px';
//         pSearch.style.width =  width/3 + 'px';
//         //pSearch.style.width =  width/3 + 'px';
//         pText.style.height = height/14 + 'px';
//         pText.style.width =  width/3 + 'px';
//         //pText.style.fontSize =  height/12 + 'px';
//         //pText.style.textAlign = "center";
// });

function myFunction(){
        var x = $( "#select option:selected" ).css("background-color");
        var y = $("#select").css("background");
        console.log(y);
        $("#select:focus").css("background-color", x);

}

function Edit(obj){
    $(obj).replaceWith(function () {
      return '<h3><input style="text-align: center; color: white; background-color:rgba(0, 0, 0, 0.3); border:0" value="'+$(this).text()+'"></input><input type="image" src="upload.png" style="background-color: transparent; width: 5%"  name="" ></h3>';
    });
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
                                        url : "HelloWorld3",
                                        type : "POST",
                                        data : {
                                                search : request.term,
                                                cmd : 'autocomplete',
                                                mean : $("#select").val(),
                                        },
                                        success : function(data) {

                                                 response(data);
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
                                                url : "HelloWorld3",
                                                type : "POST",
                                                data : {
                                                        search : $( "#search" ).val(),
                                                        cmd : 'search',
                                                        mean : $("#select").val(),
                                                },
                                                success : function(data) {
                                                        // var response = jQuery.parseJSON(data);
                                                        // $("#result").val(data[0].mean); 
                                                        $("#con").html("");  
                                                        $(data).each(function(index, item){
                                                                $("#con").append("<li ondblclick=\"Edit(this)\"><h3>"+item.mean +"</h1></li>");
                                                        })
                                                }
                                        })  
                                return false;
                        }
                });
        });
});

