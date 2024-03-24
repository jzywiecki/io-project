import React, { useState } from 'react';
import './linkSharing.css'

function Sharing({roomID}) {
    const buttonTextContent = "Udostępnij";

    const [link, setLink] = useState("");
    const [linkInfo, setlinkInfo] = useState("");

    const handleSharing = () => {
        setlinkInfo("Link do wybierania preferencji: ")
        const currentURL = new URL(window.location.href).origin;
        const newLink = currentURL + "/enroll/" + roomID;
        setLink(newLink);
    }

    return (
        <div className="link-sharing">
            <button onClick={handleSharing} className="link-sharing-button">
                {buttonTextContent}
            </button>
            <p>{linkInfo}<strong>{link}</strong></p>
        </div>
    );
};

export default Sharing;
