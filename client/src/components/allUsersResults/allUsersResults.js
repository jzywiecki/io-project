import React, { useState, useEffect } from "react";
import ResultsExport from "../resultsExport/resultsExport";
import { useParams } from "react-router-dom";
import daysMap from '../common'
import "./allUsersResults.css";

// const url = "http://localhost:8080"
const url = ""

function AllUsersResults() {
    const {roomId} = useParams();
    const [results, setResults] = useState([]);

    useEffect(() => {
        const fetchResults = async () => {
            try {
                const res = await fetch(
                    url+`/api/result/get-results/${roomId}`,
                    {
                        method: "GET",
                        headers: {
                            "Content-Type": "application/json",
                            "Access-Control-Allow-Origin": "*",
                        },
                    },
                );
                const data = await res.json();

                console.log(data);

                setResults(data);
            } catch (error) {
                console.error(error);
            }
        };

        fetchResults();
    }, [roomId]);

    if (results.length === 0) {
        return null;
    }

    const resultsArray = results.results
        .sort((a, b) => {
            // Sort by day
            const dayA = daysMap[a.result.day];
            const dayB = daysMap[b.result.day];
            if (dayA < dayB) return -1;
            if (dayA > dayB) return 1;

            // Sort by startTime
            const startTimeA = a.result.startTime;
            const startTimeB = b.result.startTime;
            if (startTimeA < startTimeB) return -1;
            if (startTimeA > startTimeB) return 1;

            return 0;
        })

    return (
        <div className="all-users-results">
            <h1>Wyniki - {results.roomName}</h1>
            <div className="description">{results.roomDescription}</div>
            
            <table>
                <thead>
                    <tr>
                        <th>Imię</th>
                        <th>Nazwisko</th>
                        <th>Email</th>
                        <th>Dzień</th>
                        <th>Godzina</th>
                    </tr>
                </thead>
                <tbody>
                    {resultsArray
                        .map((result, i) => (
                            <tr key={i}>
                                <td>{result.firstName}</td>
                                <td>{result.lastName}</td>
                                <td>{result.email}</td>
                                <td>{daysMap[result.result.day]}</td>
                                <td>
                                    {result.result.startTime[0].toString().padStart(2, '0')}:
                                    {result.result.startTime[1].toString().padStart(2, '0')}- 
                                    {result.result.endTime[0].toString().padStart(2, '0')}:
                                    {result.result.endTime[1].toString().padStart(2, '0')}
                                </td>

                            </tr>
                        ))}
                </tbody>
            </table>
            <ResultsExport results={resultsArray}/>
        </div>
    );

}
export default AllUsersResults;
