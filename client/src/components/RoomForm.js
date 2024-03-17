import { useForm } from "react-hook-form";
import { Button } from "../ui/button";

const RoomForm=(props)=>{
    const { handleSubmit, register, formState: { errors } } = useForm();

    const submit=(values)=>{
        console.log(values)
    }

    return(<div className="RoomFormPage">
        <div className="BookForm flex justify-center items-center">
            <form className="lg:w-1/3 md:w-1/2 w-3/4 flex justify-center flex-col my-20 p-5 bg-white rounded text-left" onSubmit={handleSubmit(submit)}>
                <h2 className="text-center font-bold">Formularz pokoju</h2>
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
                {/* {errors.author&&<p className="text-red-600">{errors.author.message}</p>} */}

                <h2 className="text-center">Ostateczny Termin</h2>

                <label className="form-label text-mb">Data:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="date" type="date" min={new Date()} {...register("date",{
                    required:{
                    value:true,
                    message:"Pole wymagane"
                }})}></input>
                {errors.date&&<p className="text-red-600">{errors.date.message}</p>}

                <label className="form-label text-mb">Godzina:</label>
                <input className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" name="time" type="time" {...register("time",{
                    required:{
                        value:true,
                        message:"Pole wymagane"
                    }})}
                />
                {errors.time&&<p className="text-red-600">{errors.time.message}</p>}

                <Button className="p-2 bg-sky-500 rounded w-full self-center text-white" type='submit'>submit</Button>
            </form>
        </div> 
    </div>)
}

export default RoomForm