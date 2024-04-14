import React, { useState } from "react";
import { useForm } from "react-hook-form";
import { Button } from "../../ui/button";
import { useNavigate } from 'react-router-dom';
import { setUsersInRoom } from '../../helpers/userApi';

function AddUsers({roomId}) {

    const { handleSubmit, register, formState: { errors } } = useForm();
    const [emails, setEmails] = useState([]);
    const [alert,setAlert] = useState(null);
    const navigate = useNavigate();

    const submit = (value) => {
        if(!emails.includes(value.email)) {
          setEmails([...emails, value.email]);
        }
        document.querySelector('.EmailInput').value = '';
    };

    const handleSend = async() => {
        //if (emails.length === 0) {
        //  setAlert("Nie wprowadzono żadych emailów");
        //  return;
        //}
        try{
            let response = await setUsersInRoom(emails, roomId);
            if (response.status===200) {
                navigate(`/room/${roomId}`);
                console.log("yey");
            }
        }catch(err){
          setAlert("Spróbuj ponownie później");
        }
    };

    const handleRemoveEmail = (index) => {
        const newEmails = [...emails];
        newEmails.splice(index, 1);
        setEmails(newEmails);
    };

    return (
        alert!==null?<div className="alert alert-danger w-fit flex text-center absolute right-3 bottom-0" role="alert">
          {alert}
        </div>:
        <>
        <div className="RoomFormPage">
          <div className="BookForm flex justify-center items-center">
            <form className="lg:w-1/3 md:w-1/2 w-3/4 flex justify-center flex-col my-20 p-5 bg-white rounded text-left" onSubmit={handleSubmit(submit)}>
              <h1 className="text-center font-bold mb-3">Emaile użytkowników:</h1>
              <div className="self-center mb-3">
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
              {errors.email && <p className="text-red-600">{errors.email.message}</p>}
              <div className="mb-5 mt-2 flex justify-between style items-center">
                <input
                  placeholder="example@student.agh.edu.pl"
                  className="EmailInput mr-5 shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
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
                <button
                    type="submit"
                    className="text-green-500 text-nowrap"
                >
                    Dodaj email
                </button>
              </div>
              <Button className="p-2 bg-sky-500 rounded w-full self-center text-white" type="button" onClick={handleSend}>Zakończ i wyślij</Button>
            </form>
          </div>
        </div>
        </>
      );
      
}
export default AddUsers;
