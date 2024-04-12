import axios from "axios"
// const url="http://localhost:8080"
const url=""
export const getTeacherResult=(roomId)=>{
    return axios.get(url+`/api/result/teacher/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}

export const getUserResult=(roomId)=>{
    return axios.get(url+`/api/result/user/${roomId}`,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}