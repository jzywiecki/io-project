import { useEffect, useState } from "react";
import { confirmEmail } from "../helpers/loginApi";
import { useParams } from "react-router-dom";
const ConfirmPage=()=>{
    const {token}=useParams()
    const [message,setMessage]=useState(null)
    const [isPositive,SetIsPositive]=useState(null)
    useEffect(()=>{
        const getConfirmation=async()=>{
            try{
                let response=await confirmEmail(token)
                if(response.status===200){
                    console.log(response.data)
                    setMessage("Twoje konto zostało pomyślnie potwierdzone")
                    SetIsPositive(true)
                    console.log(isPositive)
                }else{
                    setMessage("Link wygasł. Zajerestruj się ponownie");
                    SetIsPositive(false)
                }
            }catch(err){
                setMessage("Link wygasł. Zajerestruj się ponownie");
                SetIsPositive(false)
                console.log(isPositive)
            }
            
        }
        getConfirmation();
    },[])
    
    return(<div className={"flex w-full h-screen justify-center items-center text-3xl"}>
        <div className={(isPositive==true?"text-green-500":"text-red-600") +" text-center"}>
            {message===null?"Loading...":message}
        </div>
    </div>)
}
export default ConfirmPage;