import { useForm } from "react-hook-form";
import { Button } from "../ui/button";
import { addRoom } from "../helpers/roomApi";
const RoomForm=({setRoomId,setAlert})=>{
    const { handleSubmit, register, formState: { errors } } = useForm();

    const submit=async(values)=>{
        console.log(values)
        try{
            let response = await addRoom(values)
            console.log(response)
            if(response.data.id !== undefined){
                setRoomId(response.data.id)
            }
        }catch(err){
            setAlert(true)
        }
        
    }

    return(<div className="RoomFormPage">
        <div className="BookForm flex justify-center items-center">
            <form className="lg:w-1/3 md:w-1/2 w-3/4 flex justify-center flex-col my-20 p-5 bg-white rounded text-left" onSubmit={handleSubmit(submit)}>
                <h2 className="text-center font-bold mb-3">Formularz pokoju</h2>
                <label className="form-label text-mb" >Nazwa Przedmiotu:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="name" {...register("name",{
                    required:{
                    value:true,
                    message:"Pole wymagane"
                }})}></input>
                {errors.name&&<p className="text-red-600">{errors.name.message}</p>}

                <label className="form-label text-mb">Opis:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="description" {...register("description",{
                    required:{
                    value:false
                }})}></input>

                <h2 className="text-center">Ostateczny Termin</h2>

                <label className="form-label text-mb">Data:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="deadlineDate" type="date" min={new Date().toISOString().split('T')[0]} {...register("deadlineDate",{
                    required:{
                    value:true,
                    message:"Pole wymagane"
                }})}></input>
                {errors.deadlinedate&&<p className="text-red-600">{errors.deadlinedate.message}</p>}

                <label className="form-label text-mb">Godzina:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="deadlineTime" type="time" {...register("deadlineTime",{
                    required:{
                        value:true,
                        message:"Pole wymagane"
                    }})}
                />
                {errors.deadlineTime&&<p className="text-red-600">{errors.deadlineTime.message}</p>}

                <Button className="p-2 bg-sky-500 rounded w-full self-center text-white" type='submit'>Dodaj</Button>
            </form>
        </div> 
    </div>)
}

export default RoomForm