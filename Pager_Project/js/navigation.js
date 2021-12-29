function getCookie(cname) {
    let name = cname + "=";
    let decodedCookie = decodeURIComponent(document.cookie);
    let ca = decodedCookie.split(';');
    for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
        c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
        return c.substring(name.length, c.length);
        }
    }
    return "";
}
function changePage(index,display){
    document.cookie="page="+index;
    var pages = []
    var arrayLength = pages.length;
    if(pages.includes(index.toString()))
    {
        for(var i = 0;i<arrayLength;i++)
        {
            if(pages[i]===index)
            {
                document.getElementById(pages[i]).style.display = display;
            }
            else if(pages[i]==index)
            {
                document.getElementById(pages[i]).style.display = display;
            }
            else
            {
                document.getElementById(pages[i]).style.display = "none";
            }
        }
    }
}
function changeMasterPage(index,display){
    
    if(index.toString().includes("main"))
    {
        document.cookie="masterpage="+index;
    }
    else if(index >0)
    {
        document.cookie="masterpage=main"+index;
        index = "main"+index.toString();
    }
    else
    {
        document.cookie="masterpage=main0";
        index = "main0";
    }
    var mainpages = []
    var arrayLength1 = mainpages.length;
    if(mainpages.includes(index.toString()))
    { 
        for(var i = 0;i<arrayLength1;i++)
        {
            if(mainpages[i]===index)
            {
                document.getElementById(mainpages[i]).style.display = display;
            }
            else
            {
                document.getElementById(mainpages[i]).style.display = "none";
            }
        }
    }
}

function reload()
{
    var pageindex = getCookie("page");
    var masterpageindex = getCookie("masterpage");
    changePage(pageindex);
    changeMasterPage(masterpageindex);
}