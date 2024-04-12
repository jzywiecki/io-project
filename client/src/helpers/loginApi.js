import axios from "axios"
const url="/api/auth"
// const url="http://localhost:8080/api/auth"

export const confirmEmail=(token)=>{
    return axios.get(url+`/email-confirmation/${token}`)
}

export const loginUser=(body)=>{
    return axios.post(url+`/login`,body)
}

export const registerUser=(body)=>{
    return axios.post(url+"/register",body);
}
// export const