import TeacherRoomList from "../components/TeacherRoomList";
import UserRoomList from "../components/UserRoomList";
import {loginContext} from "../contexts/Login.context";
import { useContext } from "react";
const RoomListPage=()=>{
    const {role}=useContext(loginContext)
    return(role==='teacher'?<TeacherRoomList/>:<UserRoomList/>)
}

export default RoomListPage;