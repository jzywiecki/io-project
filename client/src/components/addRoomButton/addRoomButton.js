import React from 'react';
import { useNavigate } from 'react-router-dom';
import "./addRoomButton.css";

function AddRoomButton() {

    const navigate = useNavigate();
    const buttonTextContent = "Dodaj zajęcia";

    const handleClick = () => {
        navigate("/addRoom");
    };

    return (
        <div className="add-room-div">
            <button onClick={handleClick} className="add-room-button">
                {buttonTextContent}
            </button>
        </div>
    );
}
export default AddRoomButton;
