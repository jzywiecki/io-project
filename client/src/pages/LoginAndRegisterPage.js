import { useEffect, useState } from "react";
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterForm";

const LoginAndRegisterPage=()=>{
    const [isLogin,setIsLogin]=useState(true)
    return(<div className="LoginAndRegisterPage">
        {isLogin?<LoginForm setIsLogin={setIsLogin}/>:<RegisterForm setIsLogin={setIsLogin}/>}
    </div>)
}

export default LoginAndRegisterPage;