function changePage(index){
var pages = ["0","1"]
var arrayLength = pages.length;
for(var i = 0;i<arrayLength;i++)
                {
                    if(pages[i]==index)
                    {
                        document.getElementById(pages[i]).style.display = "block";
                    }
                    else
                    {
                        document.getElementById(pages[i]).style.display = "none";
                    }
                }
        }