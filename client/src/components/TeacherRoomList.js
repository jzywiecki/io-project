import React, { useContext } from 'react';
import { useState } from "react"
import { useEffect } from "react"
import { getRoomList } from "../helpers/roomApi"
import { Div } from "../ui/div"
import { useNavigate } from "react-router-dom"
import AddRoomButton from "../components/addRoomButton/addRoomButton";
import { checkAfterResponse } from '../helpers/common';
import { loginContext } from '../contexts/Login.context';

const TeacherRoomList=()=>{
        const [roomList,setRoomList]=useState([])
        const [isAlert,setIsAlert] = useState(false)
        const navigate = useNavigate();
        const {setIsLogoutAlert}=useContext(loginContext)
        useEffect(()=>{
            const getRoomListFunction=async()=>{
                try{
                    let response = await getRoomList()
                    console.log(response)
                    if(response.status===200){
                        setRoomList(response.data)
                    }else{
                        setIsAlert(true)
                    }
                }catch(err){
                    console.log(err)
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
            getRoomListFunction();
            return ()=>{};
        },[])
        return (<div className="h-screen">
            <h1 className="text-center font-bold text-3xl w-full my-3">Lista zajęć</h1>
            <div className="RoomListPage flex flex-col items-center h-fit justify-center">
                {roomList.map(room=>(<Div key={room.id} className="flex justify-between hover:bg-slate-100 cursor-default" onClick={()=>{navigate(`/room/${room.id}`)}}>
                    <div>
                        <span className="font-bold">Nazwa:&nbsp;</span> 
                        {room.name}
                    </div>
                    <div>
                        <span className="font-bold">id: </span> 
                        {room.id}
                    </div>
                </Div>))}
                <AddRoomButton/>
            </div>
            {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
                Spróbuj ponownie później
            </div>}
        </div>
       )
}

export default TeacherRoomList;