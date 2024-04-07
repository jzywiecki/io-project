import axios from "axios"
import { forEach } from "lodash"

export const getVotingPage=(roomId, userId)=>{
    return axios.get(`/get-voting-page/${roomId}/${userId}`)
}

export const savePreferences = (roomId, userId, userPreferences) => {    
    return axios.post(`/save-preferences/${roomId}/${userId}`, userPreferences) ;
}
