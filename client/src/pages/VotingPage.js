import Calendar from "../components/Calendar";
import { useState, useEffect, useRef } from "react";
import { Button } from "../ui/button";
import React from 'react';
import { useParams } from "react-router-dom";
import { getVotingPage } from "../helpers/voteApi";
import { getTermFromDto } from "../helpers/common";
import { savePreferences } from "../helpers/voteApi";
import { prepareUserPreferences } from "../helpers/common";


const VotingPage = () => {
    const { roomId, userId } = useParams();
    const [availableTerms, setAvailableTerms] = useState([]);
    const [votingStatus, setVotingStatus] = useState("");
    const [isAlert, setIsAlert] = useState(false);
    const termWithIdIsSelectedRef = useRef(null);
    const termWithIdCommentsRef = useRef(null);

    useEffect(() => {
        const getVotingPageData = async () => {
            try {
                const response = await getVotingPage(roomId, userId);
                const data = response.data;
                const availableTermsData = data.availableTerms.map((termDto) => getTermFromDto(termDto));

                setAvailableTerms(availableTermsData);
                termWithIdIsSelectedRef.current = new Map(Object.entries(data.termWithIdIsSelected));
                termWithIdCommentsRef.current = new Map(Object.entries(data.termWithIdComments));
            } catch (error) {
                console.error('Error while fetching voting page data:', error);
                setIsAlert(true);
            }
        };
        getVotingPageData();
    }, [roomId, userId]);

    const sendTerms = async () => {
        setVotingStatus("Trwa wysyłanie głosów...");

        try {
            const response = await savePreferences(roomId, userId, 
                prepareUserPreferences(termWithIdIsSelectedRef.current, termWithIdCommentsRef.current)
            );

            if (response.status !== 200) {
                throw new Error('Network response was not 200');
            }

            setVotingStatus("Głosowanie zakończone pomyślnie.");
        } catch (error) {
            console.error('Error:', error);
            setVotingStatus("Ups... błąd podczas głosowania :(");
        }
    }

    return (
        <div className="ClassSchedulerPage p-5 flex flex-col justify-center h-screen">
            {roomId && !isAlert && <h1 className="text-center text-3xl font-bold absolute top-5 w-full">Wybierz terminy do głosowania.</h1>}
            {roomId && !isAlert && <Calendar votingTerms={availableTerms} termWithIdIsSelected={termWithIdIsSelectedRef} termWithIdComments={termWithIdCommentsRef} />}
            {roomId && !isAlert && <Button className="mt-5 w-1/2 justify-self-center" onClick={() => sendTerms()}>Zapisz preferencje</Button>}
            {roomId && !isAlert && <div className="text-center">{votingStatus}</div>}
            {isAlert&&<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
            Nie udało się pobrać zawartości. Spróbuj ponownie później. </div>}
        </div>
    );
};

export default VotingPage;
