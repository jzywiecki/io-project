import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import React, { useEffect } from 'react';
import { useParams } from "react-router-dom";
import { getAvailableTermsInRoomByRoomId } from "../helpers/roomApi";


const serverUrl = "http://localhost:8080";


const VotingPage=()=>{
    const {roomId,userId} = useParams();
    const [availableTerms, setAvailableTerms] = useState([]);
    const [votingStatus, setVotingStatus] = useState([]);


    useEffect(() => {
        const getAvailableTerms = async () => {
            try {
                const response = await getAvailableTermsInRoomByRoomId(roomId, userId);
                const data = response.data;

                const termList = [];
                data.forEach((term) => {
                    var startTimeToReturn = new Date();
                    var endTimeToReturn = new Date();

                    const startTime = term.startTime
                    const endTime = term.endTime
                    const startHour = startTime.slice(0,2);
                    const endHour = endTime.slice(0,2);
                    const endMinute = endTime.slice(3,5);
                    const startMinute = startTime.slice(3,5);

                    startTimeToReturn.setHours(startHour);
                    startTimeToReturn.setMinutes(startMinute);
                    endTimeToReturn.setHours(endHour);
                    endTimeToReturn.setMinutes(endMinute);
            
                    
                    const actualTermId = term.id;
                
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
                        id: actualTermId,
                        day: day,
                        startTime: startTimeToReturn,
                        endTime: endTimeToReturn,
                    });
                });
                
                setAvailableTerms(termList);

            } catch (error) {
                console.error(error);
            }
        };
        getAvailableTerms();
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

    console.log("elo:")
    console.log(availableTerms);


    const [pickedTerms,setPickedTerms] = useState([])
    return(<div className={"ClassSchedulerPage p-5" + " flex flex-col justify-center h-screen"}>
        {roomId !== null&&<h1 className="text-center text-3xl font-bold absolute top-5 w-full">Wybierz terminy do głosowania.</h1>}
        {roomId !== null&&<Calendar terms={availableTerms} setPickedTerms={setPickedTerms}/>}
        {roomId !== null && <Button className="mt-5 w-1/2 justify-self-center" onClick={() => { sendTerms(pickedTerms) }}>Wyślij</Button>}
        {roomId !== null && <div className="text-center">{votingStatus}</div>}
    </div>)
}

export default VotingPage;