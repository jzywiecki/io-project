import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import RoomForm from "../components/RoomForm";
import { setTermsInRoom } from "../helpers/roomApi";
const ClassSchedulerPage=()=>{
    const [roomId,setRoomId] = useState(null)
    const [isAlert,setIsAlert] = useState(false)
    const addZero=(time)=>{
        if(time<10){
            return `0${time}`
        }
        return time
    }
    const sendTerms = async(terms)=>{
        let termsToSend = terms.map(term=>({day:term.day,startTime:`${addZero(term.startTime.getHours())}:${addZero(term.startTime.getMinutes())}`,endTime:`${addZero(term.endTime.getHours())}:${addZero(term.endTime.getMinutes())}`}))
        console.log(termsToSend)
        let response = await setTermsInRoom(termsToSend, roomId)
        if(response.status==200){
            setIsAlert(true)
            setRoomId(null)
            setTimeout(()=>{
                setIsAlert(false)
            },1000)
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
    return(<div className={"ClassSchedulerPage p-5"+roomId !== null?"flex flex-col item-center":""}>
        {roomId === null&&<RoomForm setRoomId={setRoomId}/>}
        {roomId !== null&&<Calendar terms={terms} setPickedTerms={setPickedTerms}/>}
        {roomId !== null&&<Button className="mt-5 w-1/2 self-center" onClick={()=>{sendTerms(pickedTerms)}}>Wyślij</Button>}
        {isAlert&&<div className="alert alert-success w-fit flex text-center absolute right-3 bottom-0" role="alert">
            Success
        </div>}
    </div>)
}

export default ClassSchedulerPage;