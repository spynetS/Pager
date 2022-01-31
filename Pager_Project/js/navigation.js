
class Component
{
    constructor(props)
    {

        this.props={

        }
    }
}


class Page1 
{
    
    constructor(props)
    {
        this.props = props;
        console.log(props);
    }



    render()
    {
        return('<div><h1>'+this.props.name+'</h1></div>');

    }
}

class Page2
{
    
    constructor(props)
    {
        this.props = props;
        this.state ={
            number:1
        }
    }

    test()
    {
        this.state.number += 1;
    }

    render()
    {
        let p = new Page1({name:"wtf"});
        document.write('<div><h2>'+this.state.number+'</h2><button onclick="test()" >increase</button></div>')

//        return('<div><h2>'+this.state.number+'</h2><button onclick="test()" >increase</button></div>');
            
    }
}