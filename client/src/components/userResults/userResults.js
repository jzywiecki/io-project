import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import "./userResults.css";
import { checkAfterResponse } from "../../helpers/common";
import { getUserResult } from "../../helpers/resultApi";

const daysMap = {
  MONDAY: "Poniedziałek",
  TUESDAY: "Wtorek",
  WEDNESDAY: "Środa",
  THURSDAY: "Czwartek",
  FRIDAY: "Piątek",
  SATURDAY: "Sobota",
  SUNDAY: "Niedziela",
};

function Results() {
  const {roomId} = useParams();
  const [results, setResults] = useState([]);
  const navigate=useNavigate()
  useEffect(() => {
    const fetchResults = async () => {
      try {
        const res = await getUserResult(roomId);
        const data = res.data

        data.result.day = daysMap[data.result.day];
        console.log(data);

        setResults(data);
      } catch (error) {
        let redirect=checkAfterResponse(error)
        if(redirect==="/login"){
            localStorage.setItem("redirect",`/room/${roomId}`)
        }
        if(redirect){
            navigate(redirect)
        }
        console.log(error)
      }
    };

    fetchResults();
  }, [roomId]);

  if (results.length === 0) {
    return null;
  }

  return (
    <div className="results">
      <div className="results-body">
        <h1 className="results-title">Wyniki wyboru</h1>
        <p>
          <strong>Nazwa przedmiotu:</strong> {results.roomName}
        </p>
        <p>
          <strong>Opis:</strong> {results.roomDescription}
        </p>
        <p>
          <strong>Otrzymany termin:</strong> {results.result.day},{" "}
          {results.result.startTime[0]}:{results.result.startTime[1]} - {results.result.endTime[0]}:{results.result.endTime[1]}
        </p>
      </div>
    </div>
  );
}

export default Results;
