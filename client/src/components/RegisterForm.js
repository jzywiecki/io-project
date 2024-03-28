import { values } from "lodash";
import { Button } from "../ui/button";
import { useForm } from "react-hook-form"
const RegisterForm=({setIsLogin})=>{
    const { handleSubmit, register, formState: { errors }, watch } = useForm();
    const submit=(values)=>{
        console.log(values)
    }
    return(<div className="RegisterForm flex flex-col justify-center h-screen items-center">
            
            <form className="lg:w-1/3 md:w-1/2 w-3/4 flex justify-center flex-col p-5 bg-white rounded text-left relative" onSubmit={handleSubmit(submit)}>
                <div className="absolute top-11 left-11 text-3xl cursor-pointer" onClick={()=>{setIsLogin(true)}}>⬅</div>
                <h2 className="text-center font-bold mb-3 text-2xl">Formularz Logowania</h2>
                <label className="form-label text-mb" >Imie:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="firstName" {...register("firstName",{
                    required:{
                    value:true,
                    message:"Pole wymagane"
                }})}></input>
                {errors.firstName&&<p className="text-red-600">{errors.firstName.message}</p>}

                <label className="form-label text-mb" >Nazwisko:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="lastName" {...register("lastName",{
                    required:{
                    value:true,
                    message:"Pole wymagane"
                }})}></input>
                {errors.lastName&&<p className="text-red-600">{errors.lastName.message}</p>}

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
                <input type="password" className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="password" {...register("password",{
                    required:{
                    value:true,
                    message:"Pole wymagane"},
                    minLength:{
                    value:8,
                    message:"Minimum 8 znaków"
                }})}></input>
                {errors.password&&<p className="text-red-600">{errors.password.message}</p>}

                <label className="form-label text-mb">Powtórz hasło:</label>
                <input type="password" className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="repeatPassword" {...register("repeatPassword",{
                    required:{
                    value:true,
                    message:"Pole wymagane"},
                    minLength:{
                    value:8,
                    message:"Minimum 8 znaków"},
                    validate: (val) => {
                        if (watch('password') !== val) {
                          return "Hasła się róźnią";
                        }
                }})}></input>
                {errors.repeatPassword&&<p className="text-red-600">{errors.repeatPassword.message}</p>}

                <Button className="p-2 bg-sky-500 rounded w-full self-center text-white my-2" type='submit'>Zajerestruj</Button>
            </form>
    </div>)
}

export default RegisterForm;