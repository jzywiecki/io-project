import axios from "axios"

// const url="http://localhost:8080"
const url="/api/room"

export const sendMail=(roomId)=>{
    return axios.get(url+`/send-results/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}