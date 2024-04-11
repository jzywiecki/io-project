import axios from "axios"

const url="/api/room"

export const addRoom=(room)=>{
    return axios.post(url, room,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const setTermsInRoom=(terms,roomId)=>{
    return axios.put(url+`/${roomId}/terms`, terms,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const getRoomList=()=>{
    return axios.get(url,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const getRoomById=(id)=>{
    return axios.get(url+`/${id}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}