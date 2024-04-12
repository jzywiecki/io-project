import { useContext, useEffect, useState } from "react";
import { loginUser } from "../helpers/loginApi";
import { Button } from "../ui/button";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { loginContext } from "../contexts/Login.context";
const LoginForm=({setIsLogin})=>{
    const { handleSubmit, register, formState: { errors } } = useForm();
    const [message,setMessage]=useState(null)
    const navigate=useNavigate()
    const {isLogoutAlert,isEmailAlert}=useContext(loginContext)
    console.log(isLogoutAlert);
    const submit=async(values)=>{
        console.log(values)
        try{
            let response=await loginUser(values)
            console.log(response.data)
            localStorage.setItem("token",response.data.token)
            localStorage.setItem("role",response.data.role)
            if(response.status===200){
                navigate("/")
            }
        }catch(err){
            console.log(err)
            setMessage("Złe hasło lub email")
        }
    }
    return(<div className="LoginForm flex justify-center h-screen items-center flex-col">
        <form className="lg:w-1/3 md:w-1/2 w-3/4 flex justify-center flex-col p-5 bg-white rounded text-left" onSubmit={handleSubmit(submit)}>
                <h2 className="text-center font-bold mb-3 text-2xl">Formularz Logowania</h2>
                
                <label className="form-label text-mb" >Email:</label>
                <input type="email" className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="email" {...register("email",{
                    required:{
                    value:true,
                    message:"Pole wymagane"},
                    pattern:{
                        value:/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                        message:"Nieprawidłowy email"
                }})}></input>
                {errors.email&&<p className="text-red-600">{errors.email.message}</p>}

                <label className="form-label text-mb">Hasło:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" type="password" name="password" {...register("password",{
                    required:{
                    value:true,
                    message:"Pole wymagane"},
                    minLength:{
                    value:8,
                    message:"Minimum 8 znaków"
                }})}></input>
                {errors.password&&<p className="text-red-600">{errors.password.message}</p>}
                {message&&<h2 className="text-red-600">{message}</h2>}
                <Button className="p-2 bg-sky-500 rounded w-full self-center text-white my-2" type='submit'>Zaloguj</Button>
                <div className="text-center">
                    Nie masz konta? <span className="underline text-cyan-500 cursor-pointer hover:text-cyan-700" onClick={()=>{setIsLogin(false)}}> Zajerestruj się</span>
                </div>
                
            </form>
            {isLogoutAlert&&<div className="alert alert-danger w-full flex text-center absolute top-0 justify-center">
                Sesja wygasła
            </div>}
            {isEmailAlert&&<div className="text-center alert alert-success absolute w-full top-0">
                    Na twój email został wyslany link potwierdzający twoje konto
            </div>}
    </div>)
}

export default LoginForm;