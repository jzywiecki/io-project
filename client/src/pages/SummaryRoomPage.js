import { useEffect, useState } from "react";
import { getRoomById } from "../helpers/roomApi";
import { Div } from "../ui/div";
import { useParams } from "react-router-dom";
import Calendar from "../components/Calendar";
const SummaryRoomPage=()=>{
    const {roomId}=useParams()
    const [room,setRoom]=useState(null)
    useEffect(()=>{
        const getRoomDetails=async()=>{
            let response=await getRoomById(roomId);
            setRoom(response.data)
            console.log(response)
        }
        getRoomDetails()
        return ()=>{}
    },[])
    return(<div className="SummaryRoomPage flex items-center flex-col justify-center h-screen">
        {room===null?<div>loading</div>:
        <>
            <Div className="flex justify-between">
                <div>Name: {room.name}</div>
                <div>id: {room.id}</div>
            </Div>
            <Div className="flex justify-between">
                <div>
                    DeadlineDay: {room.deadlineDate}
                </div>
                <div>
                    DeadlineTime: {room.deadlineTime}
                </div>
            </Div>
            <Div className="text-center">
                Description: {room.description===""?"brak":room.description}
            </Div>
            <Calendar noEditTerms={room.terms} className="w-screen h-full"/>
        </>
        }
    </div>)
}

export default SummaryRoomPage;