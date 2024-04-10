import './stopVotingButton.css';
import React from 'react';
import { stopVoting } from '../../helpers/roomApi'

function StopVotingButton({ roomId }) {
    return (
        <div className="stop-voting">
            <button className="stop-voting-button" 
                    onClick={() => {stopVoting(roomId)}}>
            Zakończ głosowanie wcześniej
            </button>
        </div>
    );
}
export default StopVotingButton;