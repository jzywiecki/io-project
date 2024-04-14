import { useNavigate } from "react-router-dom"

const LogoutButton=({className,...props})=>{
    const currentPath = window.location.pathname;
    const navigate=useNavigate()
    console.log(currentPath)
    const onClickHandler=()=>{
        localStorage.setItem("token","");navigate("/")
    }
    return (<>
    <i className={"fa fa-sign-out text-2xl cursor-pointer "+className} onClick={onClickHandler}></i>
    </>)
}

export default LogoutButton;