import { useState } from "react"
import { useEffect } from "react"
import { getRoomList } from "../helpers/roomApi"
import { Div } from "../ui/div"
import { useNavigate } from "react-router-dom"
const RoomListPage=()=>{
    const [roomList,setRoomList]=useState([])
    const navigate = useNavigate();
    useEffect(()=>{
        const getRoomListFunction=async()=>{
            let response = await getRoomList()
            console.log(response)
            setRoomList(response.data)
        }
        getRoomListFunction();
        return ()=>{};
    },[])
    return (<div className="h-screen">
        <h1 className="text-center font-bold text-3xl w-full my-3">Room List</h1>
        <div className="RoomListPage flex flex-col items-center h-fit justify-center">
            {roomList.map(room=>(<Div key={room.id} className="flex justify-between hover:bg-slate-100 cursor-default" onClick={()=>{navigate(`/room/${room.id}`)}}>
                <div>Name:{room.name}</div>
                <div>id:{room.id}</div>
            </Div>))}
        </div>
    </div>
   )
}

export default RoomListPage;