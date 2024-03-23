import React, { useState, useEffect } from "react";
import "./allUsersResults.css";

const serverUrl = "http://localhost:8080";

const daysMap = {
    MONDAY: "Poniedziałek",
    TUESDAY: "Wtorek",
    WEDNESDAY: "Środa",
    THURSDAY: "Czwartek",
    FRIDAY: "Piątek",
    SATURDAY: "Sobota",
    SUNDAY: "Niedziela",
    };

function AllUsersResults({ roomId }) {
    const [results, setResults] = useState([]);

    useEffect(() => {
        const fetchResults = async () => {
            try {
                const res = await fetch(
                    `${serverUrl}/api/result/get-results/${roomId}`,
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

    return (
        <div className="all-users-results">
            <h2>Wyniki - {results.roomName}</h2>
            <div className="description">{results.roomDescription}</div>
            <div className="total-votes">{results.totalVotes} wyników</div>
            
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
                    {results.results
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
                        .map((result, i) => (
                            <tr key={i}>
                                <td>{result.firstName}</td>
                                <td>{result.lastName}</td>
                                <td>{result.email}</td>
                                <td>{daysMap[result.result.day]}</td>
                                <td>{result.result.startTime}-{result.result.endTime}</td>
                            </tr>
                        ))}
                </tbody>
            </table>
        </div>
    );

}
export default AllUsersResults;
