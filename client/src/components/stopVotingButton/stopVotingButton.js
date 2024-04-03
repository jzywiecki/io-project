import './stopVotingButton.css';
import React from 'react';

function StopVotingButton({ roomId }) {
    return (
        <div className="stop-voting">
            <button className="stop-voting-button" onClick={() => handleStopVoting(roomId)}>Zakończ głosowanie wcześniej</button>
        </div>
    );
}

function handleStopVoting(roomId) {
    fetch(`http://localhost:8080/api/room/stop-voting/${roomId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
    });
    
}
export default StopVotingButton;
