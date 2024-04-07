import { useState } from 'react';
import { Card, CardDescription } from '../ui/card'; 
import { isEqual } from 'lodash';
const Term = ({
    term,
    minHour,
    setPickedTerms
}) => {
    const startTime = term.startTime
    const endTime = term.endTime
    const startHour = startTime.getHours();
    const endHour = endTime.getHours();
    const endMinute = endTime.getMinutes();
    const startMinute = startTime.getMinutes();

    const hour = term.startTime.getHours();
    let minute = term.startTime.getMinutes();
    if (minute < 15) {
        minute = 0;
    } else if (minute < 30) {
        minute = 1;
    } else if (minute < 45) {
        minute = 2;
    } else if (minute < 60) {
        minute = 3;
    }

    const duration = 6;
    const column = term.day+3;

    const [color, setColor] = useState('bg-white');

    return (
        <>
            <div
                className={`z-[2] mx-4 my-1`}
                style={{
                    gridRow: 1 + (hour - minHour) * 4 + minute,
                    gridRowEnd:
                        1 + (hour - minHour) * 4 + minute + duration,
                    gridColumn: column,
                }}
                onClick={()=>{
                    setColor(prev=>prev==='bg-emerald-300'?"bg-white":"bg-emerald-300");
                    setPickedTerms(prev=>prev.some(item =>isEqual(item,term))?prev.filter(item =>!isEqual(item,term)):[...prev,term])
                }}   
            >
                <Card
                    className={`${color} flex h-full w-full flex-col items-center justify-center rounded font-medium z-2`}
                >
                    <CardDescription className={`flex justify-between cursor-default`}>
                        <span className="">{startHour}:{startMinute==0?"00":startMinute}</span>
                        <span className="text-md">-</span>
                        <span className="">{endHour}:{endMinute==0?"00":endMinute}</span>
                    </CardDescription>
                </Card>
            </div>
        </>
    );
};

export default Term;