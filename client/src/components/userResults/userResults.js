import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import "./userResults.css";

// const url = "http://localhost:8080"
const url = ""

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
  const {roomId, userId} = useParams();
  const [results, setResults] = useState([]);

  useEffect(() => {
    const fetchResults = async () => {
      try {
        const res = await fetch(
          url+`/api/result/get-results/${roomId}/${userId}`,
          {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "Access-Control-Allow-Origin": "*",
            },
          },
        );
        const data = await res.json();

        data.result.day = daysMap[data.result.day];
        console.log(data);

        setResults(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchResults();
  }, [roomId, userId]);

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
