var hostName=window.location.origin+"/app";
  console.log(hostName);

 function makeHttpCall(url,type,data,e,tableId){
  if(type==""|| type==null || type==undefined){
   type="GET";
  }
  if(data==""|| data==null || data==undefined){
    data={};
   }
   var response="";
   const button = e.target
   $.ajax({
      url: hostName+"/"+url,
      type: type,
      data: data,
      async: false,
      beforeSend: function () {
      $(button).attr("disabled","disabled");
      },
      success: function (genericResponse) {
       paintTable(tableId,genericResponse.result);
      },
      error: function (jqXHR) {
           alert(jqXHR.responseJSON.message);
      },
      complete: function () {
      $(button).attr("disabled",false);
      }
 });
 return response;
 }



   function getUrlList(e){
     var response = makeHttpCall("getUrlList","GET",$("#checkUrls").serialize(),e,$("#unProcessedTable"));
 }


 function makeCallAndRefresh(e){
    var response = makeHttpCall("makeCallAndRefresh","GET",$("#checkUrls").serialize(),e);
    var unProcessedTable = $("#unProcessedTable");
    unProcessedTable.empty();
    var processedTable = $("#processedTable");
    processedTable.empty();
    var unProcessedArray = [];
    var processedArray = [];
    if(response!="" && response.result.length != 0  && typeof response.result =='object'){

    $.each(response.result, function(i, item) {
    if(item.status==false){
    var tr = "<tr>" + "<td>" + item.url + "</td>" +"</tr>";
    unProcessedTable.append(tr);
    unProcessedArray.push(item.url);
   }else{
   var tr = "<tr>" + "<td>" + item.url + "</td>" +"</tr>";
      processedTable.append(tr);
      processedArray.push(item.url);
     }
   });
	}
 }

 function paintTable(tableId,data,clearTable){
   if(data == null || data == undefined || Array.isArray(data) == false){
      return;

   }else{
   var columnNames = [];
   $.each(data[0], function( key, value ) {
           var item = {};
           item.data = key;
           item.title = key.toUpperCase();
           columnNames.push(item);
   });

   $(tableId).DataTable({
     "processing": false,
     "serverSide": false,
     "scrollY": true,
     "scrollX": true,
     "scrollCollapse": true,
     "fixedColumns": true,
     "bDestroy": true,
     "data":  data,
     "columns": columnNames

   		});
       }
  }



  function isJsonString(str) {
      var isJson = true;
      try {
        if(str == null || str == number ){
            isJson = false;
            return isJson;
        }
        JSON.parse(str);
      } catch (e) {
          isJson = false;
      }
      return isJson;
  }


   function editDetails(id){

   }

     $( document ).ready(function() {
      $("#checkUrls").on("submit", function (e) {
          e.preventDefault();
      });

      $("#checkUrlButton").on("click", function (e) {
          getUrlList(e);
      });

      $("#refreshCacheButton").on("click", function (e) {
          makeCallAndRefresh(e);
      });

     });


