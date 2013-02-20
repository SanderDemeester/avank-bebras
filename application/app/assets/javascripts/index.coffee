# ->
    $.get "/bars",(data) ->
        #.each data,(index,bar) ->
            $("#bar").append $("<li>").text bar.name
