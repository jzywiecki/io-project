import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import RoomForm from "../components/RoomForm";
import { setTermsInRoom } from "../helpers/roomApi";
import { useNavigate } from "react-router-dom";
import PrivateRoute from "../components/PrivateRoute";
const ClassSchedulerPage=()=>{
    const [roomId,setRoomId] = useState(null)
    const [isAlert,setIsAlert] = useState(false)
    const navigate = useNavigate()
    const addZero=(time)=>{
        if(time<10){
            return `0${time}`
        }
        return time
    }
    const sendTerms = async(terms)=>{
        let termsToSend = terms.map(term=>({day:term.day,startTime:`${addZero(term.startTime.getHours())}:${addZero(term.startTime.getMinutes())}`,endTime:`${addZero(term.endTime.getHours())}:${addZero(term.endTime.getMinutes())}`}))
        console.log(termsToSend)
        try{
            let response = await setTermsInRoom(termsToSend, roomId)
            if(response.status===200){
                navigate(`/room/${roomId}`)
            }
        }catch(err){
            setIsAlert(true)
        }
    }
    let terms = Array.from({ length: 5 }, (_, i) => {
        const dayTerms = [];
        let startTime = new Date();
        startTime.setHours(8, 0, 0, 0);
        let endTime = new Date();
        endTime.setHours(9, 30, 0, 0);
        let endWhileTime = new Date();
        endWhileTime.setHours(18, 30, 0, 0);
        
        while (startTime <= endWhileTime) {
            dayTerms.push({ day: i, startTime: new Date(startTime), endTime: new Date(endTime) });
            startTime.setHours(startTime.getHours() + 1);
            startTime.setMinutes(startTime.getMinutes() + 45);
            endTime.setHours(endTime.getHours() + 1);
            endTime.setMinutes(endTime.getMinutes() + 45);
        }
        
        return dayTerms;
    });
    terms = terms.flat(1)
    console.log(terms)
    const [pickedTerms,setPickedTerms] = useState([])
    return(<PrivateRoute>
        <div className={"ClassSchedulerPage p-5" + roomId !== null?"flex flex-col justify-center h-screen":""}>
            {roomId === null&&<RoomForm setRoomId={setRoomId} setAlert={setIsAlert}/>}
            {roomId !==null&&<h1 className="text-center text-3xl font-bold absolute top-5 w-full">Wybierz terminy</h1>}
            {roomId !== null&&<Calendar terms={terms} setPickedTerms={setPickedTerms}/>}
            {roomId !== null&&<Button className="mt-5 w-1/2 justify-self-center" onClick={()=>{sendTerms(pickedTerms)}}>Wyślij</Button>}
            {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
                Spróbuj ponownie później
            </div>}
        </div>
    </PrivateRoute>)
}

export default ClassSchedulerPage;