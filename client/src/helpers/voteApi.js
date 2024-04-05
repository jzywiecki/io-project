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
    return axios.post(url + `/save-preferences/${roomId}/${userId}`, userPreferences) ;
}