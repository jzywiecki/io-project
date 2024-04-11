import axios from "axios"

// const url="http://localhost:8080/api/vote"
const url = "/api/vote"

export const getVotingPage=(roomId, userId)=>{
    return axios.get(url+`/get-voting-page/${roomId}/${userId}`)
}

export const savePreferences = (roomId, userId, userPreferences) => {    
    return axios.post(url+`/save-preferences/${roomId}/${userId}`, userPreferences) ;
}
