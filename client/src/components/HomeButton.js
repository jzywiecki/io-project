import { useNavigate } from "react-router-dom"

const HomeButton=({className,...props})=>{
    const currentPath = window.location.pathname;
    const navigate=useNavigate()
    console.log(currentPath)
    const onClickHandler=()=>{
        navigate("/")
    }
    return (<>
    {currentPath!=="/"&&<i className={"fa fa-home text-2xl cursor-pointer "+className} onClick={onClickHandler}></i>}
    </>)
}

export default HomeButton;