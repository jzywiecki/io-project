import axios from "axios"

const url="/api/room"
// const url="http://localhost:8080/api/room"

export const addRoom=(room)=>{
    return axios.post(url, room,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const setTermsInRoom=(terms,roomId)=>{
    return axios.post(url+`/${roomId}/terms`, terms,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const getRoomList=()=>{
    console.log(localStorage.getItem("token"))
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

export const getRoomPreferencesById=(id)=>{
    return axios.get(url+`/get-preferences/${id}`)
}

export const stopVoting=(roomId)=>{
    return axios.post(url+`/stop-voting/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const getUserRooms=()=>{
    return axios.get(url+`/get-user-rooms`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const getRoomStatus=(roomId)=>{
    return axios.get(url+`/isFinished/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }})
}