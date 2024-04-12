import React, { useContext } from "react";
import { useEffect, useState } from "react";
import { getRoomById } from "../helpers/roomApi";
import { Div } from "../ui/div";
import { useParams } from "react-router-dom";
import Calendar from "../components/Calendar";
import Sharing from "../components/linkSharing/linkSharing"
import { checkAfterResponse } from "../helpers/common";
import { useNavigate } from "react-router-dom";
import { loginContext } from "../contexts/Login.context";
const SummaryRoomPage=()=>{
    const navigate=useNavigate()
    const {roomId}=useParams()
    const [room,setRoom]=useState(null)
    const [isAlert,setIsAlert] = useState(false)
    const {setIsLogoutAlert}=useContext(loginContext)
    useEffect(()=>{
        const getRoomDetails=async()=>{
            try{
                let response=await getRoomById(roomId);
                setRoom(response.data)
                console.log(response)
            }catch(err){
                let redirect=checkAfterResponse(err)
                if(redirect==="/login"){
                    setIsLogoutAlert(true);
                }
                if(redirect){
                    navigate(redirect)
                }
                setIsAlert(true)
            }
        }
        getRoomDetails()
        return ()=>{}
    },[])


    return(<div className="SummaryRoomPage flex items-center flex-col justify-center h-screen">
        {!isAlert&&(room===null?<div>loading</div>:
        <>
            <Div className="flex justify-between">
                <div>
                    <span className="font-bold">Nazwa:&nbsp;</span> 
                    {room.name}
                </div>
                <div>
                    <span className="font-bold">id: </span> 
                    {room.id}
                </div>
            </Div>
            <Div className="flex">
                <div className="font-bold">
                    Deadline:&nbsp;
                </div>
                <div>
                     {room.deadlineDate} {room.deadlineTime}
                </div>
            </Div>
            <Div className='flex'>
                <div className="font-bold">
                    Opis:&nbsp;
                </div>
                {room.description===""?"brak":room.description}
            </Div>
            <Calendar noEditTerms={room.terms} className="w-3/4 h-full"/>
            <Sharing roomID={roomId}/>
        </>
        )}
        {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
            Spróbuj ponownie później
        </div>}
        
    </div>)
}

export default SummaryRoomPage;