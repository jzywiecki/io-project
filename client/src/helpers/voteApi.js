import axios from "axios"
import { forEach } from "lodash"

export const getVotingPage=(roomId)=>{
    return axios.get(`/api/vote/voting-page/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const savePreferences = (roomId, userPreferences) => {    
    return axios.post(`/api/vote/save-preferences/${roomId}}`, userPreferences,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    }) ;
}
