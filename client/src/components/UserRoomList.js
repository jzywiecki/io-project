import { useNavigate } from "react-router-dom"
import { Div } from "../ui/div"
import { useContext, useEffect, useState } from "react"
import { getUserRooms } from "../helpers/roomApi"
import { checkAfterResponse } from "../helpers/common"
import { loginContext } from "../contexts/Login.context"
const UserRoomList=(props)=>{
    const [isAlert,setIsAlert]=useState(false)
    const [roomList,setRoomList]=useState([])
    const {setIsLogoutAlert}=useContext(loginContext)
    const navigate=useNavigate()
    useEffect(()=>{
        const getRooms=async()=>{
            try{
                let response = await getUserRooms()
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
        getRooms()
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
            </div>
            {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
                Spróbuj ponownie później
            </div>}
        </div>)
}

export default UserRoomList;