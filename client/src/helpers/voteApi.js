import axios from "axios"

const url="http://localhost:8080/api/vote/new-votes"

export const vote=(userPreferences)=>{
    return axios.post(url, userPreferences)
}