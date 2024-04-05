import axios from "axios"
import { forEach } from "lodash"

const url="http://localhost:8080/api/vote"

export const vote=(userPreferences)=>{
    return axios.post(url + `/new-votes`, userPreferences)
}


export const getUserVotedTermsInRoom=(roomId, userId)=>{
    return axios.get(url + `/get-user-votes/${roomId}/${userId}`)
}


export const getVotingPage=(roomId, userId)=>{
    return axios.get(url + `/get-voting-page/${roomId}/${userId}`)
}

export const savePreferences = (roomId, userId, userPreferences) => {
    let dataBackendFormat = {
        selectedTerms: [],
        comments: []
    }

    for (const [key, _] of userPreferences.termWithIdIsSelected.entries()) {
        if (userPreferences.termWithIdIsSelected.get(key)) {
            const parsedKey = parseInt(key, 10);
            dataBackendFormat.selectedTerms.push(parsedKey);
        }
    }

    for (const [key, value] of userPreferences.termWithIdComments.entries()) {
        if (userPreferences.termWithIdComments.get(key) === "") {
            continue;
        }
        dataBackendFormat.comments.push({termId: key, comment: value});
    }
    
    return axios.post(url + `/save-preferences/${roomId}/${userId}`, dataBackendFormat) ;
}
