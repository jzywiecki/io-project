import { useNavigate } from "react-router-dom"

const LogoutButton=({className,...props})=>{
    const currentPath = window.location.pathname;
    const navigate=useNavigate()
    console.log(currentPath)
    return (<>
    {currentPath!="/login"&&<i className={"fa fa-sign-out text-2xl "+className} onClick={()=>{localStorage.setItem("token","");navigate("/")}}></i>}
    </>)
}

export default LogoutButton;