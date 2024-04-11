import { CardDescription, Card } from "../ui/card";
import "./preferencesTable/preferencesTable.css";

const NoEditTerm=({term ,minHour, roomPreferences})=>{

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

    let numberOfVotes = 0;
    for (let user in roomPreferences.userPreferencesMap) { 
        let terms = roomPreferences.userPreferencesMap[user].selectedTerms;
        for (let t in terms) {
            if (terms[t] === term.id) {
                numberOfVotes++;
            }   
        }
    }

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
                className={`bg-emerald-300 flex h-full w-full flex-col items-center justify-center rounded font-medium z-2 hint`}
            >
                <CardDescription className={`flex justify-between cursor-default`}>
                    <span className="">{startHour}:{startMinute==0?"00":startMinute}</span>
                    <span className="text-md">-</span>
                    <span className="">{endHour}:{endMinute==0?"00":endMinute}</span>
                </CardDescription>
                <CardDescription className={`cursor-default`} style={{position:"absolute", left:"5%", bottom:"5%"}}>
                    {(
                    <>
                    {
                        numberOfVotes === 1 ? `${numberOfVotes} głos` :
                        numberOfVotes > 1 && numberOfVotes < 5 ? `${numberOfVotes} głosy` :
                        numberOfVotes > 4 ? `${numberOfVotes} głosów` : ``
                    }
                    </>
                    )}
                </CardDescription>
                {numberOfVotes>0?
                <div className="hint-text">
                    {Object.keys(roomPreferences.userPreferencesMap).map(
                        (user) => {
                            if (roomPreferences.userPreferencesMap[user].selectedTerms.includes(term.id)) {
                                return (
                                    <div key={user}>
                                        {   
                                            roomPreferences.users.map(u => {
                                                if (u.userId == user) {
                                                  return (
                                                    <span key={u.userId}>
                                                      {u.firstName} {u.lastName}{" "}
                                                      <span style={{ color: "#aaa" }}>{u.email}</span>
                                                    </span>
                                                  );
                                                }
                                                return null;
                                            })
                                            
                                        }
                                    </div>
                                );
                            } else {
                                return null;
                            }
                        }
                    )}
                </div>
                :<></>}
            </Card>
        </div>
    </>)
}

export default NoEditTerm;