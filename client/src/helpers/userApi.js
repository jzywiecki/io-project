import axios from "axios"

const url="/api/user"
//const url="http://localhost:8080/api/user"

export const setUsersInRoom=(users,roomId)=>{
    return axios.post(url+`/${roomId}/users`, users,{
        headers:{
            'Authorization': 'Bearer ' + localStorage.getItem("token")
        }
    })
}
