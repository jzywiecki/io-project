import { set } from "lodash";
import { createContext, useEffect, useState } from "react";

export const loginContext=createContext();

function LoginContext(props){
    useEffect(()=>{
        console.log("sieam")
    },[])
    const {children}=props
    const [role, setRole]=useState(null)
    const [isLogoutAlert,setIsLogoutAlert]=useState(false)
    return(<loginContext.Provider value={{role:role,setRole:setRole,isLogoutAlert:isLogoutAlert,setIsLogoutAlert:setIsLogoutAlert}}>
        {children}
    </loginContext.Provider>)
}

export default LoginContext;