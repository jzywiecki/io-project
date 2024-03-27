import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import React, { useEffect } from 'react';
import { useParams } from "react-router-dom";
import { getAvailableTermsInRoomByRoomId } from "../helpers/roomApi";
import { getDayNumber } from "../helpers/common";

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
                    var startTime = new Date();
                    var endTime = new Date();

                    startTime.setHours(term.startTime[0]);
                    startTime.setMinutes(term.startTime[1]);
                    endTime.setHours(term.endTime[0]);
                    endTime.setMinutes(term.endTime[1]);

                    termList.push({
                        id: term.id,
                        day: getDayNumber(term.day),
                        startTime: startTime,
                        endTime: endTime,
                    });
                });
                
                setAvailableTerms(termList);

            } catch (error) {
                console.log("Error: during get available terms in room.")
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