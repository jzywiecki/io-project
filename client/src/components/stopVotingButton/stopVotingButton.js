import './stopVotingButton.css';
import React, { useState, useEffect } from 'react';
import { getRoomById, stopVoting } from '../../helpers/roomApi';

function StopVotingButton({ roomId }) {
    const [maxTerms, setMaxTerms] = useState(0);
    const [selectedTerms, setSelectedTerms] = useState(0);
    const [buttonText, setButtonText] = useState("Zakończ głosowanie wcześniej");
    
    useEffect(() => {
        getRoomById(roomId).then((response) => {
            console.log(response.data);
            setMaxTerms(response.data.terms.length);
        });
    }, [roomId]);

    const handleStopVoting = () => {
        stopVoting(roomId, selectedTerms);
        setButtonText("Zatrzymano głosowanie!");
        console.log(roomId, selectedTerms)
    }

    const handleSelectChange = (e) => {
        setSelectedTerms(parseInt(e.target.value));
    }

    return (
        <div className="stop-voting">
            {maxTerms > 0 && (
                <>
                    <label htmlFor="maxTerms">Potrzebna ilość grup:</label>
                    <select value={selectedTerms} onChange={handleSelectChange}>
                        {[...Array(maxTerms).keys()].map((i) => (
                            <option key={i+1} value={i+1}>{i+1}</option>
                        ))}
                            <option key={"all"} value = {-1}>Dowolna</option>
                    </select>
                </>
            )}
            <button className="stop-voting-button" onClick={handleStopVoting}>
                {buttonText}
            </button>
        </div>
    );
}

export default StopVotingButton;
