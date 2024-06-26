import React, { useState, useEffect } from "react";
import ResultsExport from "../resultsExport/resultsExport";
import { useNavigate, useParams } from "react-router-dom";
import daysMap from '../common'
import "./allUsersResults.css";
import { getTeacherResult } from "../../helpers/resultApi";
import { checkAfterResponse } from "../../helpers/common";
import { sendMail } from "../../helpers/mailApi";






function AllUsersResults() {
    const {roomId} = useParams();
    const [results, setResults] = useState([]);
    const [isMailSent, setIsMailSent] = useState(false);

    function handleSendEmail(roomId) {
        sendMail(roomId)
        setIsMailSent(true); // Ustaw stan na true po wysłaniu maila
    }

    const navigate=useNavigate()
    useEffect(() => {
        const fetchResults = async () => {
            try {
                const res = await getTeacherResult(roomId)
                const data = res.data
                console.log(data);

                setResults(data);
            } catch (error) {
                let redirect=checkAfterResponse(error)
                if(redirect==="/login"){
                    localStorage.setItem("redirect",`/results/${roomId}`)
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
            <button
                onClick={() => handleSendEmail(roomId)}
                style={{ backgroundColor: isMailSent ? "green" : "blue", color: "white", padding: "10px 20px", borderRadius: "5px", marginTop: "20px" }}
                disabled={isMailSent} // Zablokuj przycisk, jeśli mail został już wysłany
            >
                {isMailSent ? "Wysłano" : "Wyślij maila z wynikami"}
            </button>
        </div>
    );

}
export default AllUsersResults;
