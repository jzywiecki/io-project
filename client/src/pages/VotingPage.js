import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import React, { useEffect } from 'react';


const serverUrl = "http://localhost:8080";


const ClassSchedulerPage=({roomId, userId})=>{
    const [availableTerms, setAvailableTerms] = useState([]);
    const [votingStatus, setVotingStatus] = useState([]);


    useEffect(() => {
    const availableTermsFetch = async () => {
        try {
            const res = await fetch(
                `${serverUrl}/api/room/get-terms-in-room/${roomId}`,
                {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                        },
                    },
                );
                const data = await res.json();
                console.log(data);

                const termList = [];
                data.forEach((term) => {
                    const actualRoomId = term.id;

                    const [hours, minutes, seconds] = term.startTime.split(":").map(Number);
                    const start = new Date();
                    start.setHours(hours);
                    start.setMinutes(minutes);
                    start.setSeconds(seconds);
                
                    const [endHours, endMinutes, endSeconds] = term.endTime.split(":").map(Number);
                    const end = new Date();
                    end.setHours(endHours);
                    end.setMinutes(endMinutes);
                    end.setSeconds(endSeconds);
                
                    var day;
                    switch (term.day) {
                        case "MONDAY": day = 0; break;
                        case "TUESDAY": day = 1; break;
                        case "WEDNESDAY": day = 2; break;
                        case "THURSDAY": day = 3; break;
                        case "FRIDAY": day = 4; break;
                        default: day = 0;
                    }

                    termList.push({
                        id: actualRoomId,
                        day: day,
                        startTime: start,
                        endTime: end,
                    });
                });
                
                setAvailableTerms(termList);

            } catch (error) {
                console.error(error);
            }
        };
        availableTermsFetch();
    }, []);

    const sendTerms = async(terms)=>{

        setVotingStatus("Trwa wysyłanie głosów...")

        const votesData = {
            user_id: userId,
            room_id: roomId,
            terms_id: []
        };

        terms.forEach(term => {
            votesData.terms_id.push(term.id);
        });

        const requestOptions = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(votesData)
          };

        fetch(serverUrl + "/api/vote/new-votes", requestOptions)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
               return response.json();
            })
            .then(data => {
                console.log('Sukces:', data);
                setVotingStatus("Głosowanie zakończone pomyślnie :)");
            })
            .catch(error => {
                console.error('Błąd:', error);
                setVotingStatus("Ups... błąd podczas głosowania :(");
            });
    }


    const [pickedTerms,setPickedTerms] = useState([])
    return(<div className={"ClassSchedulerPage p-5" + " flex flex-col justify-center h-screen"}>
        {roomId !== null&&<h1 className="text-center text-3xl font-bold absolute top-5 w-full">Wybierz terminy do głosowania.</h1>}
        {roomId !== null&&<Calendar terms={availableTerms} setPickedTerms={setPickedTerms}/>}
        {roomId !== null && <Button className="mt-5 w-1/2 justify-self-center" onClick={() => { sendTerms(pickedTerms) }}>Wyślij</Button>}
        {roomId !== null && <div className="text-center">{votingStatus}</div>}
    </div>)
}

export default ClassSchedulerPage;