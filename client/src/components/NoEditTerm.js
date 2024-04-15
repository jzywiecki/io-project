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
    let numberOfComments = 0;
    for (let user in roomPreferences.userPreferencesMap) { 
        let terms = roomPreferences.userPreferencesMap[user].selectedTerms;
        for (let t in terms) {
            if (terms[t] === term.id) {
                numberOfVotes++;
            }   
        }
        let comments = roomPreferences.userPreferencesMap[user].comments;
        for (let c in comments) {
            if (comments[c].termId === term.id) {
                numberOfComments++;
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
            className={`z-[2] mx-4 my-1 hover:z-10`}
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
                    <span className="">{startHour}:{startMinute===0?"00":startMinute}</span>
                    <span className="text-md">-</span>
                    <span className="">{endHour}:{endMinute===0?"00":endMinute}</span>
                </CardDescription>
                <CardDescription className={`cursor-default`} style={{position:"absolute", left:"5%", top:"5%"}}>
                    {(numberOfVotes>0)?(<strong>{numberOfVotes} &#10003;</strong>):(<></>)}
                </CardDescription>
                <CardDescription className={`cursor-default`} style={{position:"absolute", left:"5%", bottom:"5%"}}>
                    {(numberOfComments>0)?(<strong>{numberOfComments} ?</strong>):(<></>)}
                </CardDescription>
                {numberOfVotes>0?
                <div className="hint-text">
                    {Object.keys(roomPreferences.userPreferencesMap).map(
                        (user) => {
                            let preferences = roomPreferences.userPreferencesMap[user];
                            let comments = roomPreferences.userPreferencesMap[user].comments;
                            let comment_value = null;
                            for (let comment in comments) {
                                if (comments[comment].termId === term.id) {
                                    comment_value = comments[comment].comment;
                                }
                            }
                            if (preferences.selectedTerms.includes(term.id) || comment_value!=null) {
                                return (
                                    <div key={user}>
                                        {   
                                            roomPreferences.users.map(u => {
                                                if (u.userId === parseInt(user)) {
                                                    if (preferences.selectedTerms.includes(term.id) && comment_value!=null) {
                                                        return (
                                                            <span key={u.userId}>
                                                                <strong>&#10003;</strong> + <strong>?</strong>{" "}
                                                                {u.firstName} {u.lastName}{" "}
                                                                <span style={{ color: "#aaa" }}>{u.email}</span>
                                                            </span>
                                                        );
                                                    }
                                                    if (preferences.selectedTerms.includes(term.id)) {
                                                        return (
                                                            <span key={u.userId}>
                                                                <strong>&#10003;</strong>{" "}
                                                                {u.firstName} {u.lastName}{" "}
                                                                <span style={{ color: "#aaa" }}>{u.email}</span>
                                                            </span>
                                                        );
                                                    }
                                                    if (comment_value!=null) {
                                                        return (
                                                            <span key={u.userId}>
                                                                <strong>?</strong>{" "}
                                                                {u.firstName} {u.lastName}{" "}
                                                                <span style={{ color: "#aaa" }}>{u.email}</span>
                                                            </span>
                                                        );
                                                    }
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