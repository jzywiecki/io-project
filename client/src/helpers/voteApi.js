import axios from "axios"
import { forEach } from "lodash"

export const getVotingPage=(roomId, userId)=>{
    return axios.get(`/api/get-voting-page/${roomId}/${userId}`)
}

export const savePreferences = (roomId, userId, userPreferences) => {    
    return axios.post(`/api/save-preferences/${roomId}/${userId}`, userPreferences) ;
}
