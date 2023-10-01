var hostName=window.location.origin+"/app";
  console.log(hostName);

 function makeHttpCall(url,type,data,e){
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
      beforeSend: function () {
      $(button).attr("disabled","disabled");
      },
      success: function (genericResponse) {
       response=genericResponse;
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
     var response = makeHttpCall("getUrlList","GET",$("#checkUrls").serialize(),e);
     var unProcessedTable = $("#unProcessed");
     unProcessedTable.empty();
     var processedTable = $("#processed");
     processedTable.empty();
     var unProcessedArray = [];
     if(response!="" && response.result.length != 0  && typeof response.result =='object'){
     $.each(response.result, function(i, item) {
       var tr = "<tr>" + "<td>" + item.url + "</td>" +"</tr>";
     	unProcessedTable.append(tr);
     	unProcessedArray.push(item.url);
     	});
     }
 }


 function makeCallAndRefresh(e){
 var response = makeHttpCall("makeCallAndRefresh","GET",$("#checkUrls").serialize(),e);
  var unProcessedTable = $("#unProcessed");
  unProcessedTable.empty();
  var processedTable = $("#processed");
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