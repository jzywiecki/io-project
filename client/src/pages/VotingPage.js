import Calendar from "../components/Calendar";
import { useState } from "react";
import { Button } from "../ui/button";
import React, { useEffect } from 'react';
import { useParams } from "react-router-dom";
import { getAvailableTermsInRoomByRoomId } from "../helpers/roomApi";
import { getTermFromDto } from "../helpers/common";
import { vote, getUserVotedTermsInRoom } from "../helpers/voteApi";

const serverUrl = "http://localhost:8080";


const VotingPage=()=>{
    const {roomId,userId} = useParams();
    const [availableTerms, setAvailableTerms] = useState([]);
    const [votingStatus, setVotingStatus] = useState([]);
    const [pickedTerms,setPickedTerms] = useState([])


    useEffect(() => {

        const getAvailableTerms = async () => {
            try {
                const response = await getAvailableTermsInRoomByRoomId(roomId, userId);
                const data = response.data;

                const termList = [];

                data.forEach((termDto) => {
                    const term = getTermFromDto(termDto);

                    termList.push({
                        id: termDto.id,
                        day: term.day,
                        startTime: term.startTime,
                        endTime: term.endTime,
                    });
                });
                
                setAvailableTerms(termList);

            } catch (error) {
                console.log("Error: during get available terms in room.")
                console.error(error);
            }
        };

        const getPreviousPreferences = async () => {
            try {
                const response = await getUserVotedTermsInRoom(roomId, userId);
                const data = response.data;

                const termList = [];

                data.forEach((termDto) => {
                    const term = getTermFromDto(termDto);

                    termList.push({
                        id: termDto.id,
                        day: term.day,
                        startTime: term.startTime,
                        endTime: term.endTime,
                    });
                });

                console.log("Previous preferences: ");
                console.log(termList);
                setPickedTerms(termList);
            } catch (error) {
                console.log("Error: during get previous voted terms in room.")
                console.error(error);
            }
        }

        getAvailableTerms();
        getPreviousPreferences();
    }, []);

    const sendTerms = async(terms)=>{

        setVotingStatus("Trwa wysyłanie głosów...")
        console.log("userID: " + userId);
        console.log("roomID: " + roomId);

        const userPreferencesRequestBody = {
            userId: userId,
            roomId: roomId,
            termsId: []
        };

        terms.forEach(term => {
            userPreferencesRequestBody.termsId.push(term.id);
        });

        try {
            const response = await vote(userPreferencesRequestBody);
        
            if (response.status !== 200) {
                throw new Error('Network response was not 200');
            }
        
            setVotingStatus("Głosowanie zakończone pomyślnie.");
        } catch (error) {
            console.error('Error:', error);
            setVotingStatus("Ups... błąd podczas głosowania :(");
        }
    }

    const  isTermPicked = (term) => {
        if (!pickedTerms) {
            return false;
        }
        return pickedTerms.some((item) => item.id === term.id);
    };

    return(<div className={"ClassSchedulerPage p-5" + " flex flex-col justify-center h-screen"}>
        {roomId !== null&&<h1 className="text-center text-3xl font-bold absolute top-5 w-full">Wybierz terminy do głosowania.</h1>}
        {roomId !== null&&<Calendar terms={availableTerms} setPickedTerms={setPickedTerms} isTermPicked={isTermPicked} />}
        {roomId !== null && <Button className="mt-5 w-1/2 justify-self-center" onClick={() => { sendTerms(pickedTerms) }}>Wyślij</Button>}
        {roomId !== null && <div className="text-center">{votingStatus}</div>}
    </div>)
}

export default VotingPage;