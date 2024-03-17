import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import RoomForm from "../components/RoomForm";
const ClassSchedulerPage=()=>{
    const [isForm,setIsForm] = useState(true)
    let terms = Array.from({ length: 5 }, (_, i) => {
        const dayTerms = [];
        let startTime = new Date();
        startTime.setHours(8, 0, 0, 0);
        let endTime = new Date();
        endTime.setHours(9, 30, 0, 0);
        let endWhileTime = new Date();
        endWhileTime.setHours(18, 30, 0, 0);
        
        while (startTime <= endWhileTime) {
            dayTerms.push({ day: i + 1, startTime: new Date(startTime), endTime: new Date(endTime) });
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
    return(<div className="ClassSchedulerPage flex item-center flex-col">
        {isForm&&<RoomForm/>}
        {!isForm&&<Calendar terms={terms} setPickedTerms={setPickedTerms}/>}
        {!isForm&&<Button className="mt-5 w-1/2 self-center" onClick={()=>{console.log(pickedTerms)}}>Submit</Button>}
    </div>)
}

export default ClassSchedulerPage;