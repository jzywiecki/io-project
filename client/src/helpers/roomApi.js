import axios from "axios"

const url="/api/room"

export const addRoom=(room)=>{
    return axios.post(url, room)
}

export const setTermsInRoom=(terms,roomId)=>{
    return axios.put(url+`/${roomId}/terms`, terms)
}

export const getRoomList=()=>{
    return axios.get(url)
}

export const getRoomById=(id)=>{
    return axios.get(url+`/${id}`)
}

export const getRoomVotesSummaryById=(id)=>{
    return axios.get(url+`/get-preferences/${id}`)
}
