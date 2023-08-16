   var hostName=window.location.origin+"/app";
  console.log(hostName);

   function getUrlList(){

                $.ajax({
                   url: hostName+"/getUrlList",
                   type: "GET",
                   data: $("#checkUrls").serialize(),
           beforeSend: function () {
           $("#checkUrlButton").attr("disabled","disabled");
                         },
         success: function (response) {
           var unProcessedTable = $("#unProcessed");
           unProcessedTable.empty();
           var processedTable = $("#processed");
           processedTable.empty();
           var unProcessedArray = [];
            if(response.result.length != 0  && typeof response.result =='object'){
          	$.each(response.result, function(i, item) {
                var tr = "<tr>" + "<td>" + item.url + "</td>" +"</tr>";
          				unProcessedTable.append(tr);
          				unProcessedArray.push(item.url);
          	});
             }
             },
         error: function (jqXHR) {
          alert(jqXHR.responseJSON.message);

         },
         complete: function () {
             $("#checkUrlButton").attr("disabled",false);

        }
});

 }



   function makeCallAndRefresh(){

                $.ajax({
                   url: hostName+"/makeCallAndRefresh",
                   type: "GET",
                   data: $("#checkUrls").serialize(),
           beforeSend: function () {
        $("#refreshCacheButton").attr("disabled","disabled");
                         },
         success: function (response) {
           var unProcessedTable = $("#unProcessed");
           unProcessedTable.empty();
           var processedTable = $("#processed");
           processedTable.empty();
           var unProcessedArray = [];
           var processedArray = [];
            if(response.result.length != 0  && typeof response.result =='object'){
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
             },
         error: function (jqXHR) {
          alert(jqXHR.responseJSON.message);

         },
         complete: function () {
         $("#refreshCacheButton").attr("disabled",false);

        }
});

 }



     $( document ).ready(function() {
      $("#checkUrls").on("submit", function (e) {
          e.preventDefault();
      });

      $("#checkUrlButton").on("click", function (e) {
          getUrlList();
      });

      $("#refreshCacheButton").on("click", function (e) {
          makeCallAndRefresh();
      });

     });