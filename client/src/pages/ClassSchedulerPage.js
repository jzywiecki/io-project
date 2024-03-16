import Calendar from "../components/Calendar";
import { useState } from "react";
const ClassSchedulerPage=()=>{
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
    return(<div className="ClassSchedulerPage">
        <Calendar terms={terms} setPickedTerms={setPickedTerms}/>
        <button className="mt-5 ml-5 p-2 bg-cyan-500 rounded-md text-white" onClick={()=>{console.log(pickedTerms)}}>Submit</button>
    </div>)
}

export default ClassSchedulerPage;