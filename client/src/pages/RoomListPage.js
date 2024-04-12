import TeacherRoomList from "../components/TeacherRoomList";
import UserRoomList from "../components/UserRoomList";
import {loginContext} from "../contexts/Login.context";
import { useContext } from "react";
const RoomListPage=()=>{
    console.log(localStorage.getItem("role"))
    console.log(localStorage.getItem("token"))
    const {role}=useContext(loginContext)
    return(localStorage.getItem("role")==='TEACHER'?<TeacherRoomList/>:<UserRoomList/>)
}

export default RoomListPage;