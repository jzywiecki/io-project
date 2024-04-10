import axios from "axios"
import { forEach } from "lodash"

export const getVotingPage=(roomId, userId)=>{
    return axios.get(`/api/vote/get-voting-page/${roomId}/${userId}`)
}

export const savePreferences = (roomId, userId, userPreferences) => {    
    return axios.post(`/api/vote/save-preferences/${roomId}/${userId}`, userPreferences) ;
}
