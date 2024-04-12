import { set } from "lodash";
import { createContext, useEffect, useState } from "react";

export const loginContext=createContext();

function LoginContext(props){
    const {children}=props
    const [isLogoutAlert,setIsLogoutAlert]=useState(false)
    const [isEmailAlert,setIsEmailAlert]=useState(false)
    return(<loginContext.Provider value={{isLogoutAlert:isLogoutAlert,setIsLogoutAlert:setIsLogoutAlert,isEmailAlert:isEmailAlert,setIsEmailAlert:setIsEmailAlert}}>
        {children}
    </loginContext.Provider>)
}

export default LoginContext;