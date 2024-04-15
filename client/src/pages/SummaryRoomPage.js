import React, { useContext } from "react";
import { useEffect, useState } from "react";
import { getRoomById } from "../helpers/roomApi";
import { getRoomPreferencesById } from "../helpers/roomApi";
import { Div } from "../ui/div";
import { useParams } from "react-router-dom";
import Calendar from "../components/Calendar";
import Table from "../components/preferencesTable/preferencesTable";
import { checkAfterResponse } from "../helpers/common";
import { useNavigate } from "react-router-dom";
import { loginContext } from "../contexts/Login.context";
import { Button } from "bootstrap";
import StopVotingButton from "../components/stopVotingButton/stopVotingButton";


const SummaryRoomPage=()=>{
    const navigate=useNavigate()
    const {roomId}=useParams()
    const [room,setRoom]=useState(null)
    const [roomPreferences,setRoomPreferences]=useState(null)
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
    }, [])
    useEffect(()=>{
        const getRoomPreferences=async()=>{
            try{
                let response=await getRoomPreferencesById(roomId);
                setRoomPreferences(response.data)
            }catch(err){
                setIsAlert(true)
            }
        }
        getRoomPreferences()
        return ()=>{}
    }, [])


    return(<div className="SummaryRoomPage flex items-center flex-col justify-center h-screen">
        {!isAlert&&((room===null||roomPreferences===null)?<div>loading</div>:
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
            <Div className='flex justify-between style items-center'>
                <div>
                    <span className="font-bold">Link do wybierania preferencji:&nbsp;</span>
                    {new URL(window.location.href).origin + "/enroll/" + roomId}
                </div>
                <button onClick={() => {
                    const textarea = document.createElement('textarea');
                    textarea.value = new URL(window.location.href).origin + "/enroll/" + roomId;
                    document.body.appendChild(textarea);
                    textarea.select();
                    document.execCommand('copy');
                    document.body.removeChild(textarea);
                }} className="copy-button">
                    Copy
                </button>
            </Div>
            <Calendar roomPreferences={roomPreferences} noEditTerms={room.terms} className="w-3/4"/>
            {Object.keys(roomPreferences.userPreferencesMap).length>0?<Table terms={room.terms} roomPreferences={roomPreferences}/>:<></>}
        </>
        )}
        {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
            Spróbuj ponownie później
        </div>}
        <StopVotingButton roomId={roomId}/> 
        <button
                onClick={() => navigate("/results/" + roomId)}
                style={{ backgroundColor: "green", color: "white", padding: "10px 20px", borderRadius: "5px", marginTop: "20px" }}
            >
                Wyniki
            </button>
    </div>)
}

export default SummaryRoomPage;