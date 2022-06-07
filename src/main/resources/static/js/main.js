$(function(){

      $('.update_img').on('click', function(){
        $.get('/api/giphy', {}, function(response){
        document.getElementById("image_id").src = response.url;
        });
      });

});