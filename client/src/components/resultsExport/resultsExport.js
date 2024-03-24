import React from 'react';
import "./resultsExport.css";
import daysMap from '../common'

function ResultsExport({results}) {

    const CSVSeparator = ',';
    const buttonTextContent = "Eksportuj do CSV";

    const generateFileContent = () => {
        const header = "imie,nazwisko,email,dzien,godzina\n";
        const content = results
            .map((result) => (
                result.firstName + CSVSeparator +
                result.lastName + CSVSeparator +
                result.email + CSVSeparator +
                daysMap[result.result.day] + CSVSeparator +
                result.result.startTime + '-' + result.result.endTime
            ));

        return header + content.join('\n');
    }

    const handleExport = () => {
        const file = new Blob([generateFileContent()], {type: 'text/plain'});
        const element = document.createElement("a");
        element.href = URL.createObjectURL(file);
        element.download = "results.csv";
        document.body.appendChild(element);
        element.click();
        document.body.removeChild(element);
    };

    return (
        <div className="results-export">
            <button onClick={handleExport} className="results-export-button">
                {buttonTextContent}
            </button>
        </div>
    );
}
export default ResultsExport;
