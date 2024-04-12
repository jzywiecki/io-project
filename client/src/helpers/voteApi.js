import axios from "axios"

// const url="http://localhost:8080"
const url=""

export const getVotingPage=(roomId)=>{
    return axios.get(url+`/api/vote/voting-page/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const savePreferences = (roomId, userPreferences) => {    
    return axios.post(url+`/api/vote/save-preferences/${roomId}`, userPreferences,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    }) ;
}
