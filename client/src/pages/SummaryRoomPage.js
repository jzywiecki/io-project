import { useEffect, useState } from "react";
import { getRoomById } from "../helpers/roomApi";
import { Div } from "../ui/div";
import { useParams } from "react-router-dom";
import Calendar from "../components/Calendar";
const SummaryRoomPage=()=>{
    const {roomId}=useParams()
    const [room,setRoom]=useState(null)
    const [isAlert,setIsAlert] = useState(false)
    useEffect(()=>{
        const getRoomDetails=async()=>{
            try{
                let response=await getRoomById(roomId);
                setRoom(response.data)
                console.log(response)
            }catch(err){
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
                    <span className="font-bold">Name:&nbsp;</span> 
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
        </>
        )}
        {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
            Spróbuj ponownie później
        </div>}
    </div>)
}

export default SummaryRoomPage;