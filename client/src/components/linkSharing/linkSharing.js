import React, { useState } from 'react';
import { Div } from "../../ui/div"
import './linkSharing.css'

function Sharing({roomID}) {
    const [isLinkVisible, setIsLinkVisible] = useState(false);
    const buttonTextContent = "Udostępnij";

    const handleSharing = () => {
        setIsLinkVisible(true);
    }

    return (
        <div className="link-sharing flex items-center flex-col justify-center">
            <button onClick={handleSharing} className="link-sharing-button">
                {buttonTextContent}
            </button>
            <Div className='flex link-sharing-div' hidden={!isLinkVisible}>
                <div>Link do wybierania preferencji:&nbsp;</div>
                <div className="font-bold">
                    {new URL(window.location.href).origin + "/enroll/" + roomID} 
                </div>
            </Div>
        </div>
    );
}

export default Sharing;
