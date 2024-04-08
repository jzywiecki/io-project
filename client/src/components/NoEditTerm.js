import { getRoomVotesSummaryById } from "../helpers/roomApi";
import { useEffect, useState } from "react";
import { CardDescription, Card } from "../ui/card";

const NoEditTerm=({term ,minHour, roomID})=>{
    const [roomSummaryVotes,setRoomSummaryVotes]=useState(null)
    useEffect(()=>{
        const getRoomSummaryVotes=async()=>{
            try{
                let response=await getRoomVotesSummaryById(roomID);
                setRoomSummaryVotes(response.data)
            }catch(err){}
        }
        getRoomSummaryVotes()
        return ()=>{}
    },[])

    const convertDayOfWeekToNumber=(dayOfWeekString)=>{
        const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        const normalizedDayOfWeekString = dayOfWeekString.toUpperCase();
        const index = days.findIndex(day => day.toUpperCase() === normalizedDayOfWeekString);
        return index !== -1 ? index : -1;
    }

    const startTime = term.startTime
    const endTime = term.endTime
    const startHour = startTime.slice(0,2);
    const endHour = endTime.slice(0,2);
    const endMinute = endTime.slice(3,5);
    const startMinute = startTime.slice(3,5);

    const hour = startHour;
    let minute = startMinute;
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
    const column = convertDayOfWeekToNumber(term.day)+2;

    return(<>
        <div
            className={`z-[2] mx-4 my-1`}
            style={{
                gridRow: 1 + (hour - minHour) * 4 + minute,
                gridRowEnd:
                    1 + (hour - minHour) * 4 + minute + duration,
                gridColumn: column,
            }}  
        >
            <Card
                className={`bg-emerald-300 flex h-full w-full flex-col items-center justify-center rounded font-medium z-2`}
            >
                <CardDescription className={`flex justify-between cursor-default`}>
                    <span className="">{startHour}:{startMinute==0?"00":startMinute}</span>
                    <span className="text-md">-</span>
                    <span className="">{endHour}:{endMinute==0?"00":endMinute}</span>
                </CardDescription>
                <CardDescription>
                    {(roomSummaryVotes===null?<div>loading</div>:
                    <>
                    {roomSummaryVotes.termVotesCount[term.id] === 1 ? `${roomSummaryVotes.termVotesCount[term.id]} głos` :
                        roomSummaryVotes.termVotesCount[term.id] > 1 && roomSummaryVotes.termVotesCount[term.id] < 5 ? `${roomSummaryVotes.termVotesCount[term.id]} głosy` :
                        roomSummaryVotes.termVotesCount[term.id] > 4 ? `${roomSummaryVotes.termVotesCount[term.id]} głosów` : ``
                    }
                    </>
                    )}
                </CardDescription>
            </Card>
        </div>
    </>)
}

export default NoEditTerm;