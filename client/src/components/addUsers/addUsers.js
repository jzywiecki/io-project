import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Button } from "../../ui/button";
import { useNavigate } from 'react-router-dom';
import { setUsersInRoom } from '../../helpers/userApi';

function AddUsers({roomId}) {

    const { handleSubmit, register, formState: { errors } } = useForm();
    const [emails, setEmails] = useState([]);
    const navigate = useNavigate();

    const submit = (value) => {
        setEmails([...emails, value.email]);
    };

    const handleSend = async() => {
        console.log(emails);
        try{
            let response = await setUsersInRoom(emails, roomId);
            if (response.status===200) {
                navigate(`/room/${roomId}`);
                console.log("yey");
            }
        }catch(err){
            //setIsAlert(true)
        }
    };

    const handleRemoveEmail = (index) => {
        const newEmails = [...emails];
        newEmails.splice(index, 1);
        setEmails(newEmails);
    };

    return (
        <div className="RoomFormPage">
          <div className="BookForm flex justify-center items-center">
            <form className="lg:w-1/3 md:w-1/2 w-3/4 flex justify-center flex-col my-20 p-5 bg-white rounded text-left" onSubmit={handleSubmit(submit)}>
              <h1 className="text-center font-bold mb-3">Dodaj maile użytkowników</h1>
              <div className="self-center">
                {
                    emails.map((email, index) => (
                        <div className="flex justify-between style items-center" key={index}>
                            <div className="mr-5">{email}</div>
                            <button
                                type="button"
                                onClick={() => handleRemoveEmail(index)}
                                className="text-red-500"
                            >
                                Usuń email
                            </button>
                        </div>
                    ))
                }
                </div>
              <label className="form-label text-mb">Adres email:</label>
              <input
                className="mb-2 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                name="email"
                {...register("email", {
                  required: {
                    value: true,
                    message: "Pole wymagane"},
                    pattern:{
                        value:/^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/,
                        message:"Nieprawidłowy email"
                  },
                })}
              ></input>
              {errors.emails && <p className="text-red-600">{errors.emails.message}</p>}
      
              <Button className="p-2 bg-sky-500 rounded w-full self-center text-white mb-3" type="submit">Dodaj email</Button>
              <Button className="p-2 bg-sky-500 rounded w-full self-center text-white" type="button" onClick={handleSend}>Zakończ i wyślij</Button>
            </form>
          </div>
        </div>
      );
      
}
export default AddUsers;
